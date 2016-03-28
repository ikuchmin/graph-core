package ru.osslabs.graph.impl;

import ru.osslabs.graph.DirectedGraph;
import ru.osslabs.graph.Edge;
import ru.osslabs.graph.Graph;
import ru.osslabs.graph.GraphMap;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.asList;

/**
 * Created by ikuchmin on 03.03.16.
 */
public abstract class AbstractDirectedGraph<V, E extends Edge<V>> extends AbstractGraph<V, E> implements DirectedGraph<V, E> {

    @Override
    public DirectedGraph<V, E> addEdge(E edge) {
        if (edge == null) throw new IllegalArgumentException("Edge equals null");

        Optional<DirectedEdgeContainer<V, E>> src = getGraphMap().get(edge.getSource());
        Optional<DirectedEdgeContainer<V, E>> trg = getGraphMap().get(edge.getTarget());

        if (src.isPresent() && src.get().getOutgoingEdges().contains(edge))
            throw new IllegalArgumentException(format("Edge (%s) contains in graph. This is collision.", edge));
        if (!src.isPresent() || !trg.isPresent())
            throw new IllegalArgumentException(format("Vertex not found in graph. Source: %s, Target: %s",
                    edge.getSource(), edge.getTarget()));

        src.get().addOutgoingEdge(edge);
        trg.get().addIncomingEdge(edge);

        return this;
    }

    // FIXME: Bug in groovy https://issues.apache.org/jira/browse/GROOVY-7799
//    @Override
//    public DirectedGraph<V, E> addEdges(E... edges) {
//        return addEdges(asList(edges))
//    }

    @Override
    public DirectedGraph<V, E> addEdges(Collection<? extends E> edges) {
        if (edges.contains(null)) throw new IllegalArgumentException("Collection of edges can not contains null edge");
        edges.forEach(this::addEdge);
        return this;
    }

    @Override
    public DirectedGraph<V, E> addEdge(V sourceVertex, V targetVertex) {
        if (sourceVertex == null) throw new IllegalArgumentException("Source vertex equals null");
        if (targetVertex == null) throw new IllegalArgumentException("Source vertex equals null");

        return addEdge(getEdgeFactory().apply(sourceVertex, targetVertex));
    }

    @Override
    public DirectedGraph<V, E> addVertex(V vertex) {
        if (vertex == null) throw new IllegalArgumentException("Vertex equals null");
        if (getGraphMap().containsKey(vertex))
            throw new IllegalArgumentException(format("Vertex with name %s has in graph. This is collision", vertex));

        getGraphMap().put(vertex, new DirectedEdgeContainer<>(vertex));

        return this;
    }

    @Override
    public DirectedGraph<V, E> addVertices(V... vertices) {
        return addVertices(asList(vertices));
    }

    @Override
    public DirectedGraph<V, E> addVertices(Collection<? extends V> vertices) {
        if (vertices.contains(null))
            throw new IllegalArgumentException("Collection of getVertices can not contains null getVertices");

        vertices.forEach(this::addVertex);
        return this;
    }

    @Override
    public Graph<V, E> addGraph(Graph<V, E> sourceGraph) {
        Collection<V> srcGraphVertices = sourceGraph.getVertices();
        srcGraphVertices.stream()
                .filter(v -> !containsVertex(v))
                .forEach(this::addVertex);


        srcGraphVertices.stream()
                .flatMap(vertex -> sourceGraph.edgesOf(vertex).stream())
                .filter(e -> !containsEdge(e))
                .forEach(this::addEdge);

        return this;
    }

    @Override
    public boolean containsEdge(E e) {
        return edgesOf(e.getSource()).contains(e);
    }

    @Override
    public boolean containsVertex(V v) {
        return getGraphMap().containsKey(v);
    }

    @Override
    public List<Boolean> containsVertices(V... vertices) {
        return containsVertices(asList(vertices));
    }

    @Override
    public List<Boolean> containsVertices(List<? extends V> vertices) {
        if (vertices == null) return Collections.singletonList(false);

        return vertices.stream().map(getGraphMap()::containsKey).collect(Collectors.toList());
    }

    List<Boolean> containsVertices(V vertex, List<? extends V> vertices,
                                   Function<DirectedEdgeContainer<V, E>, Collection<V>> fnVertices) {
        Optional<DirectedEdgeContainer<V, E>> directedEdge = getGraphMap().get(vertex);

        if (!directedEdge.isPresent()) {
            Boolean[] arr = new Boolean[vertices.size()];
            Arrays.fill(arr, false);
            return Arrays.asList(arr);
        }

        return vertices.stream()
                .map(fnVertices.apply(directedEdge.get())::contains)
                .collect(Collectors.toList());
    }

    @Override
    public List<Boolean> containsOutgoingVertices(V vertex, V... vertices) {
        return containsOutgoingVertices(vertex, asList(vertices));
    }

    @Override
    public List<Boolean> containsOutgoingVertices(V vertex, List<? extends V> vertices) {
        return containsVertices(vertex, vertices, DirectedEdgeContainer::getOutgoingVertices);
    }

    @Override
    public List<Boolean> containsIncomingVertices(V vertex, V... vertices) {
        return containsIncomingVertices(vertex, asList(vertices));
    }

    @Override
    public List<Boolean> containsIncomingVertices(V vertex, List<? extends V> vertices) {
        return containsVertices(vertex, vertices, DirectedEdgeContainer::getIncomingVertices);
    }


    @Override
    public Collection<E> edgesOf(V vertex) {
        return outgoingEdgesOf(vertex);
    }

    @Override
    public Collection<E> incomingEdgesOf(V vertex) {
        Optional<DirectedEdgeContainer<V, E>> edge = getGraphMap().get(vertex);
        if (!edge.isPresent()) return Collections.emptyList();
        return edge.get().getIncomingEdges();

    }

    @Override
    public Collection<E> outgoingEdgesOf(V vertex) {
        Optional<DirectedEdgeContainer<V, E>> edge = getGraphMap().get(vertex);
        if (!edge.isPresent()) return Collections.emptyList();
        return edge.get().getOutgoingEdges();
    }

    @Override
    public Collection<V> getVertices() {
        return getGraphMap().keySet();
    }

    protected abstract GraphMap<V, DirectedEdgeContainer<V, E>> getGraphMap();
}