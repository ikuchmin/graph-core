package ru.osslabs.plarform.modules.graph.impl;


import ru.osslabs.plarform.modules.graph.DirectedGraph;
import ru.osslabs.plarform.modules.graph.Edge;
import ru.osslabs.plarform.modules.graph.Graph;

import java.util.Set;

/**
 * A directed graph that cannot be modified.
 *
 * @see UnmodifiableGraph
 */
public class UnmodifiableDirectedGraph<V, E extends Edge<V>>
        extends UnmodifiableGraph<V, E>
        implements DirectedGraph<V, E> {

    private static final long serialVersionUID = 3978701783725913906L;

    /**
     * The graph to which operations are delegated.
     */
    private Graph<V, E> delegate;


    /**
     * Creates a new unmodifiable directed graph based on the specified backing
     * graph.
     *
     * @param g the backing graph on which an unmodifiable graph is to be
     *          created.
     */
    public UnmodifiableDirectedGraph(DirectedGraph<V, E> g) {
        super(g);
    }

    @Override
    public Set<E> incomingEdgesOf(V vertex) {
        return null;
    }

    @Override
    public Set<E> outgoingEdgesOf(V vertex) {
        return null;
    }
}
