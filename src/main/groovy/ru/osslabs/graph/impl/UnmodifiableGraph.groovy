package ru.osslabs.graph.impl

import groovy.transform.CompileStatic
import ru.osslabs.graph.Edge
import ru.osslabs.graph.Graph

/**
 * Created by ikuchmin on 14.03.16.
 */

@CompileStatic
class UnmodifiableGraph<V, E extends Edge<V>> implements Graph<V, E> {
    @Delegate
    private final Graph<V, E> graph

    private static final String UNMODIFIABLE = "this graph is unmodifiable";

    UnmodifiableGraph(Graph<V, E> graph) {
        this.graph = graph
    }

    @Override
    Graph<V, E> addEdge(E edge) {
        throw new UnsupportedOperationException(UNMODIFIABLE)
    }

    @Override
    Graph<V, E> addEdges(Collection<E> edges) {
        throw new UnsupportedOperationException(UNMODIFIABLE)
    }

    @Override
    Graph<V, E> addEdge(V sourceVertex, V targetVertex) {
        throw new UnsupportedOperationException(UNMODIFIABLE)
    }

    @Override
    Graph<V, E> addVertex(V v) {
        throw new UnsupportedOperationException(UNMODIFIABLE)
    }

    @Override
    Graph<V, E> addGraph(Graph<? extends V, ? extends E> sourceGraph) {
        throw new UnsupportedOperationException(UNMODIFIABLE)
    }
}
