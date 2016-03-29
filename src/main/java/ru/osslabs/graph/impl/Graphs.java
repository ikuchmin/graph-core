package ru.osslabs.graph.impl;

import ru.osslabs.graph.DirectedGraph;
import ru.osslabs.graph.Edge;
import ru.osslabs.graph.Graph;

/**
 * Created by ikuchmin on 15.03.16.
 */
public class Graphs {
    public static <V, E extends Edge<V>, G extends Graph<V, E, G>> Graph<V, E, G> unmodifiable(Graph<V, E, G> graph) {
        return new UnmodifiableGraph<>(graph);
    }

    public static <V, E extends Edge<V>, G extends DirectedGraph<V, E, G>> DirectedGraph<V, E, G> unmodifiable(DirectedGraph<V, E, G> directedGraph) {
        return new UnmodifiableDirectedGraph<>(directedGraph);
    }
}
