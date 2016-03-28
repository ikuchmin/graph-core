package ru.osslabs.graph.impl;

import ru.osslabs.graph.Edge;
import ru.osslabs.graph.GraphMap;

import java.util.function.BiFunction;

/**
 * Created by ikuchmin on 03.03.16.
 */
public class DirectedGraphImpl<V, E extends Edge<V>> extends AbstractDirectedGraph<V, E> {

    private final BiFunction<V, V, E> edgeFactory;
    private final GraphMap<V, DirectedEdgeContainer<V, E>> vertices = new SimpleGraphMap<>();

    public DirectedGraphImpl(BiFunction<V, V, E> edgeFactory) {
        if (edgeFactory == null) throw new IllegalArgumentException("Graph can not be created without edge factory");

        this.edgeFactory = edgeFactory;
    }

    @Override
    protected GraphMap<V, DirectedEdgeContainer<V, E>> getGraphMap() {
        return vertices;
    }

    @Override
    public BiFunction<V, V, E> getEdgeFactory() {
        return edgeFactory;
    }
}
