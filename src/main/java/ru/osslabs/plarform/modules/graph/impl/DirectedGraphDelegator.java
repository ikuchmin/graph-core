package ru.osslabs.plarform.modules.graph.impl;

import ru.osslabs.plarform.modules.graph.DirectedGraph;
import ru.osslabs.plarform.modules.graph.Edge;
import ru.osslabs.plarform.modules.graph.Graph;

import java.util.Set;

/**
 * Created by ikuchmin on 14.03.16.
 */
public class DirectedGraphDelegator<V, E extends Edge<V>>
        extends GraphDelegator<V, E>
        implements DirectedGraph<V, E> {

    private static final long serialVersionUID = 3257005445222191425L;

    /**
     * The graph to which operations are delegated.
     */
    private DirectedGraph<V, E> delegate;

    /**
     * Constructor for DirectedGraphDelegator.
     *
     * @param g the backing graph (the delegate).
     * @throws IllegalArgumentException iff <code>g==null</code>
     */
    public DirectedGraphDelegator(DirectedGraph<V, E> g) {
        super(g);
        if (g == null) {
            throw new IllegalArgumentException("g must not be null.");
        }

        this.delegate = g;
    }

    @Override
    public Set<E> incomingEdgesOf(V vertex) {
        return delegate.incomingEdgesOf(vertex);
    }

    @Override
    public Set<E> outgoingEdgesOf(V vertex) {
        return delegate.outgoingEdgesOf(vertex);
    }
}
