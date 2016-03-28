package ru.osslabs.graph.impl;

import ru.osslabs.graph.Edge;
import ru.osslabs.graph.Graph;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by ikuchmin on 14.03.16.
 */

class UnmodifiableGraph<V, E extends Edge<V>> implements Graph<V, E> {
    private final Graph<V, E> graph;

    private static final String UNMODIFIABLE = "this graph is unmodifiable";

    public UnmodifiableGraph(Graph<V, E> graph) {
        this.graph = graph;
    }


    @Override
    public Graph<V, E> addEdge(E edge) {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    @Override
    public Graph<V, E> addEdges(Collection<? extends E> edges) {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    @Override
    public Graph<V, E> addEdge(V sourceVertex, V targetVertex) {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    @Override
    public Graph<V, E> addVertex(V v) {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    @Override
    public Graph<V, E> addVertices(V... vertices) {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    @Override
    public Graph<V, E> addVertices(Collection<? extends V> vertices) {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    @Override
    public Graph<V, E> addGraph(Graph<V, E> sourceGraph) {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    // boilerplate

    @Override
    public BiFunction<V, V, E> getEdgeFactory() {
        return graph.getEdgeFactory();
    }

    @Override
    public boolean containsEdge(E e) {
        return graph.containsEdge(e);
    }

    @Override
    public boolean containsVertex(V v) {
        return graph.containsVertex(v);
    }

    @Override
    public List<Boolean> containsVertices(V... v) {
        return graph.containsVertices(v);
    }

    @Override
    public List<Boolean> containsVertices(List<? extends V> v) {
        return graph.containsVertices(v);
    }

    @Override
    public Collection<E> edgesOf(V vertex) {
        return graph.edgesOf(vertex);
    }

    @Override
    public Collection<V> getVertices() {
        return graph.getVertices();
    }
}
