package ru.osslabs.plarform.modules.graph.impl;

import ru.osslabs.plarform.modules.graph.DirectedGraph;
import ru.osslabs.plarform.modules.graph.Edge;
import ru.osslabs.plarform.modules.graph.Graph;
import ru.osslabs.plarform.modules.graph.GraphMap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * Created by ikuchmin on 03.03.16.
 */
public class DirectedGraphImpl<V, E extends Edge<V>> extends AbstractDirectedGraph<V, E> {

    protected final BiFunction<V, V, E> edgeFactory;
    protected final GraphMap<V, DirectedEdgeContainer<V, E>> vertices = new SimpleGraphMap<>();

    public DirectedGraphImpl(BiFunction<V, V, E> edgeFactory) {
        if (edgeFactory == null) throw new IllegalArgumentException("Graph can not be created without edge factory")
        this.edgeFactory = edgeFactory;
    }

    @Override
    protected GraphMap<V, DirectedEdgeContainer<V, E>> graphMap() {
        return vertices;
    }

    @Override
    public BiFunction<V, V, E> edgeFactory() {
        return edgeFactory;
    }

    /**
     * Current implementation works only with DirectedGraphImpl. Source graph should
     * is implementing DirectedGraphImpl and isn't contains same vertices with
     * vertices this graph.
     *
     * @param sourceGraph source graph
     * @return this object
     *
     * @throws IllegalArgumentException if source graph isn't implementing
     * DirectedGraphImpl
     * @throws IllegalArgumentException if source graph and source graph
     * are contains some general vertices
     */
    @Override
    @SuppressWarnings("unchecked")
    public Graph<V, E> addGraph(Graph<? extends V, ? extends E> sourceGraph) {
        if (this == sourceGraph) return this;
        if (!(sourceGraph instanceof DirectedGraphImpl))
            throw new IllegalArgumentException("Source graph isn't implementing DirectedGraphImpl");

        DirectedGraphImpl<V, E> that = (DirectedGraphImpl<V, E>) sourceGraph;

        for (V key : vertices.keySet())
            if (vertices.keySet().contains(key))
                throw new IllegalArgumentException("This graph and source graph aren't contains some general vertices");

        vertices.putAll(that.vertices);
        return this;
    }
}
