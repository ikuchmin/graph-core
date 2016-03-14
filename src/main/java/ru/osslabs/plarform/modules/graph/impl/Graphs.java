package ru.osslabs.plarform.modules.graph.impl;

import ru.osslabs.plarform.modules.graph.DirectedGraph;
import ru.osslabs.plarform.modules.graph.Edge;
import ru.osslabs.plarform.modules.graph.Graph;

/**
 * Created by ikuchmin on 14.03.16.
 */
public class Graphs {

    public <V, E extends Edge<V>> Graph<V, E> unmodifiable(Graph<V, E> graph) {
        return new UnmodifiableGraph<>(graph);
    }

    public <V, E extends Edge<V>> DirectedGraph<V, E> unmodifiable(DirectedGraph<V, E> directedGraph) {

    }
}
