package ru.osslabs.graph;

/**
 * Created by ikuchmin on 28.03.16.
 */
public interface Graph<V, E extends Edge<V>> extends GraphReadOperations<V, E>, GraphModifyOperations<V, E> {
}
