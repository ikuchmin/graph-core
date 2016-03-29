package ru.osslabs.graph.impl;

import ru.osslabs.graph.Edge;
import ru.osslabs.graph.Graph;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by ikuchmin on 14.03.16.
 */

class UnmodifiableGraph<V, E extends Edge<V>, G extends Graph<V, E, G>> implements Graph<V, E, G> {
    private final Graph<V, E, G> graph;

    private static final String UNMODIFIABLE = "this graph is unmodifiable";

    public UnmodifiableGraph(Graph<V, E, G> graph) {
        this.graph = graph;
    }


    @Override
    public G addEdge(E edge) {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    @Override
    public G addEdges(Collection<? extends E> edges) {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    @Override
    public G addEdge(V sourceVertex, V targetVertex) {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    @Override
    public G addVertex(V v) {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    @Override
    public G addVertices(V... vertices) {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    @Override
    public G addVertices(Collection<? extends V> vertices) {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    @Override
    public G addGraph(Graph<V, E, G> sourceGraph) {
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
    public boolean containsAllEdges(E... edges) {
        return graph.containsAllEdges(edges);
    }

    @Override
    public boolean containsAllEdges(Collection<? extends E> edges) {
        return graph.containsAllEdges(edges);
    }

    @Override
    public boolean containsVertex(V vertex) {
        return graph.containsVertex(vertex);
    }

    @Override
    public boolean containsAllVertices(V... vertex) {
        return graph.containsAllVertices(vertex);
    }

    @Override
    public boolean containsAllVertices(Collection<? extends V> vertices) {
        return graph.containsAllVertices(vertices);
    }

    @Override
    public List<Boolean> containsVertices(V... vertices) {
        return graph.containsVertices(vertices);
    }

    @Override
    public List<Boolean> containsVertices(List<? extends V> vertices) {
        return graph.containsVertices(vertices);
    }

    @Override
    public boolean containGraph(Graph<V, E, G> graph) {
        return this.graph.containGraph(graph);
    }

    @Override
    public Collection<E> edgesOf(V vertex) {
        return graph.edgesOf(vertex);
    }

    @Override
    public Collection<E> getEdges() {
        return graph.getEdges();
    }

    @Override
    public Collection<V> getVertices() {
        return graph.getVertices();
    }
}
