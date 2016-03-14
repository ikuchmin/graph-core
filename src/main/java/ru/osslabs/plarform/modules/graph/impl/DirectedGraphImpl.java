package ru.osslabs.plarform.modules.graph.impl;

import ru.osslabs.plarform.modules.graph.DirectedGraph;
import ru.osslabs.plarform.modules.graph.Edge;
import ru.osslabs.plarform.modules.graph.Graph;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * Created by ikuchmin on 03.03.16.
 */
public class DirectedGraphImpl<V, E extends Edge<V>> extends AbstractBaseGraph<V, E> implements DirectedGraph<V, E> {

    protected final BiFunction<V, V, E> edgeFactory;
    protected final Map<V, DirectedEdgeContainer<V, E>> vertices = new ConcurrentHashMap<>();

    public DirectedGraphImpl(BiFunction<V, V, E> edgeFactory) {
        this.edgeFactory = edgeFactory;
    }

    @Override
    public E getEdge(E edge) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public Graph<V, E> addEdge(E edge) {
        Objects.requireNonNull(edge);
        DirectedEdgeContainer<V, E> src = vertices.get(edge.getSource());
        DirectedEdgeContainer<V, E> trg = vertices.get(edge.getTarget());

        if (src != null && trg != null) {
            src.addOutgoingEdge(edge);
            trg.addIncomingEdge(edge);
        } else
            throw new IllegalArgumentException(String.format("Vertex not found in graph. Source: %s, Target: %s",
                    src != null ? src.getVertex() : null,
                    trg != null ? trg.getVertex() : null));

        return this;
    }

    @Override
    public BiFunction<V, V, E> getEdgeFactory() {
        return edgeFactory;
    }

    @Override
    public Graph<V, E> addEdge(V sourceVertex, V targetVertex) {
        Objects.requireNonNull(sourceVertex);
        Objects.requireNonNull(targetVertex);

        E edge = edgeFactory.apply(sourceVertex, targetVertex);
        addEdge(edge);

        return this;
    }

    @Override
    public Graph<V, E> addVertex(V vertex) {
        vertices.put(vertex, new DirectedEdgeContainer<>(vertex));
        return this;
    }

    @Override
    public V getVertex(V vertex) {
        return vertices.get(vertex).getVertex();
    }

    /**
     * Current implementation works only with DirectedGraphImpl. Source graph should
     * is implementing DirectedGraphImpl and isn't contains same vertices with
     * vertices this graph.
     *
     * @param sourceGraph source graph
     * @return this object
     *
     * @throws IllegalArgumentException if source graph isn't implementing
     * DirectedGraphImpl
     * @throws IllegalArgumentException if source graph and source graph
     * are contains some general vertices
     */
    @Override
    @SuppressWarnings("unchecked")
    public Graph<V, E> addGraph(Graph<? extends V, ? extends E> sourceGraph) {
        if (this == sourceGraph) return this;
        if (!(sourceGraph instanceof DirectedGraphImpl))
            throw new IllegalArgumentException("Source graph isn't implementing DirectedGraphImpl");

        DirectedGraphImpl<V, E> that = (DirectedGraphImpl<V, E>) sourceGraph;

        for (V key : vertices.keySet())
            if (that.vertices.keySet().contains(key))
                throw new IllegalArgumentException("This graph and source graph aren't contains some general vertices");

        vertices.putAll(that.vertices);
        return this;
    }

    @Override
    public boolean containsEdge(E edge) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public boolean containsVertex(V vertex) {
        return vertices.containsKey(vertex);
    }

    @Override
    public Set<E> edgesOf(V vertex) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public Set<V> vertexSet() {
        return Collections.unmodifiableSet(vertices.keySet());
    }

    @Override
    public Set<E> incomingEdgesOf(V vertex) {
        return vertices.get(vertex).getUnmodifiableIncomingEdges();
    }

    @Override
    public Set<E> outgoingEdgesOf(V vertex) {
        return vertices.get(vertex).getUnmodifiableOutgoingEdges();
    }
}
