package ru.osslabs.plarform.modules.graph;

import java.util.Collection;
import java.util.List;
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
    Collection<E> incomingEdgesOf(V vertex);

    /**
     * Returns a set of all edges outgoing from the specified vertex.
     *
     * @param vertex the vertex for which the list of outgoing edges to be
     * returned.
     *
     * @return a set of all edges outgoing from the specified vertex.
     */
    Collection<E> outgoingEdgesOf(V vertex);

    List<Boolean> containsOutgoingVertices(V vertex, V... vertices);

    List<Boolean> containsOutgoingVertices(V vertex, List<V> vertices);

    List<Boolean> containsIncomingVertices(V vertex, V... vertices);

    List<Boolean> containsIncomingVertices(V vertex, List<V> vertices);
}
