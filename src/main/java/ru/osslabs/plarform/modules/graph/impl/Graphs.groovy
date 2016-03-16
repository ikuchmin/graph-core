package ru.osslabs.plarform.modules.graph.impl

import groovy.transform.CompileStatic
import ru.osslabs.plarform.modules.graph.DirectedGraph
import ru.osslabs.plarform.modules.graph.Edge
import ru.osslabs.plarform.modules.graph.Graph

/**
 * Created by ikuchmin on 15.03.16.
 */
@CompileStatic
class Graphs {
    public static <V, E extends Edge<V>> Graph<V, E> unmodifiable(Graph<V, E> graph) {
        return new UnmodifiableGraph<>(graph);
    }

    public static <V, E extends Edge<V>> DirectedGraph<V, E> unmodifiable(DirectedGraph<V, E> directedGraph) {
        return new UnmodifiableDirectedGraph<>(directedGraph);
    }
}
