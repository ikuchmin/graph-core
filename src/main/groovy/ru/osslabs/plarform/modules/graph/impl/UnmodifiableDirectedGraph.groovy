package ru.osslabs.plarform.modules.graph.impl

import groovy.transform.CompileStatic
import groovy.transform.Immutable
import ru.osslabs.plarform.modules.graph.DirectedGraph
import ru.osslabs.plarform.modules.graph.Edge
import ru.osslabs.plarform.modules.graph.Graph

/**
 * Created by ikuchmin on 14.03.16.
 */
@CompileStatic
class UnmodifiableDirectedGraph<V, E extends Edge<V>>
        extends UnmodifiableGraph<V, E>
        implements DirectedGraph<V, E> {

    @Delegate
    private DirectedGraph<V, E> directedGraph;

    UnmodifiableDirectedGraph(DirectedGraph<V, E> directedGraph) {
        super(directedGraph)
        this.directedGraph = directedGraph
    }
}
