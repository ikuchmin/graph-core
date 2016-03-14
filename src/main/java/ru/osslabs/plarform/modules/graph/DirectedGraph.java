package ru.osslabs.plarform.modules.graph;

import java.util.Set;

/**
 * Created by ikuchmin on 03.03.16.
 */
public interface DirectedGraph<V, E extends Edge<V>> extends Graph<V, E> {

    /**
     * Returns a set of all edges incoming into the specified vertex.
     *
     * @param vertex the vertex for which the list of incoming edges to be
     * returned.
     *
     * @return a set of all edges incoming into the specified vertex.
     */
    Set<E> incomingEdgesOf(V vertex);

    /**
     * Returns a set of all edges outgoing from the specified vertex.
     *
     * @param vertex the vertex for which the list of outgoing edges to be
     * returned.
     *
     * @return a set of all edges outgoing from the specified vertex.
     */
    Set<E> outgoingEdgesOf(V vertex);
}
