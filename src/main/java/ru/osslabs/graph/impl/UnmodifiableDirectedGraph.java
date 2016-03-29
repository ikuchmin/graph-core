package ru.osslabs.graph.impl;

import lombok.experimental.Delegate;
import ru.osslabs.graph.DirectedGraph;
import ru.osslabs.graph.Edge;
import ru.osslabs.graph.Graph;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;


/**
 * Created by ikuchmin on 14.03.16.
 */
public class UnmodifiableDirectedGraph<V, E extends Edge<V>, G extends DirectedGraph<V, E, G>>
        extends UnmodifiableGraph<V, E, G>
        implements DirectedGraph<V, E, G> {

    private DirectedGraph<V, E, G> directedGraph;

    public UnmodifiableDirectedGraph(DirectedGraph<V, E, G> directedGraph) {
        super(directedGraph);
        this.directedGraph = directedGraph;
    }

    // boilerplate

    @Override
    public Collection<E> incomingEdgesOf(V vertex) {
        return directedGraph.incomingEdgesOf(vertex);
    }

    @Override
    public Collection<V> incomingVerticesOf(V vertex) {
        return directedGraph.incomingVerticesOf(vertex);
    }

    @Override
    public Collection<E> outgoingEdgesOf(V vertex) {
        return directedGraph.outgoingEdgesOf(vertex);
    }

    @Override
    public Collection<V> outgoingVerticesOf(V vertex) {
        return directedGraph.outgoingVerticesOf(vertex);
    }

    @Override
    public List<Boolean> containsOutgoingVertices(V vertex, V... vertices) {
        return directedGraph.containsOutgoingVertices(vertex, vertices);
    }

    @Override
    public List<Boolean> containsOutgoingVertices(V vertex, List<? extends V> vertices) {
        return directedGraph.containsOutgoingVertices(vertex, vertices);
    }

    @Override
    public List<Boolean> containsIncomingVertices(V vertex, V... vertices) {
        return directedGraph.containsIncomingVertices(vertex, vertices);
    }

    @Override
    public List<Boolean> containsIncomingVertices(V vertex, List<? extends V> vertices) {
        return directedGraph.containsIncomingVertices(vertex, vertices);
    }

    @Override
    public boolean containsAllEdges(E... edges) {
        return directedGraph.containsAllEdges(edges);
    }
}
