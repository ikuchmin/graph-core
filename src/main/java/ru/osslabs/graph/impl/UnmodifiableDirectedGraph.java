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
public class UnmodifiableDirectedGraph<V, E extends Edge<V>>
        extends UnmodifiableGraph<V, E>
        implements DirectedGraph<V, E> {

    private DirectedGraph<V, E> directedGraph;

    public UnmodifiableDirectedGraph(DirectedGraph<V, E> directedGraph) {
        super(directedGraph);
        this.directedGraph = directedGraph;
    }

    // boilerplate
    @Override
    public Collection<E> incomingEdgesOf(V vertex) {
        return directedGraph.incomingEdgesOf(vertex);
    }

    @Override
    public Collection<E> outgoingEdgesOf(V vertex) {
        return directedGraph.outgoingEdgesOf(vertex);
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
    public BiFunction<V, V, E> getEdgeFactory() {
        return directedGraph.getEdgeFactory();
    }

    @Override
    public boolean containsEdge(E e) {
        return directedGraph.containsEdge(e);
    }

    @Override
    public boolean containsVertex(V v) {
        return directedGraph.containsVertex(v);
    }

    @Override
    public List<Boolean> containsVertices(V... v) {
        return directedGraph.containsVertices(v);
    }

    @Override
    public List<Boolean> containsVertices(List<? extends V> v) {
        return directedGraph.containsVertices(v);
    }

    @Override
    public Collection<E> edgesOf(V vertex) {
        return directedGraph.edgesOf(vertex);
    }

    @Override
    public Collection<V> getVertices() {
        return directedGraph.getVertices();
    }
}
