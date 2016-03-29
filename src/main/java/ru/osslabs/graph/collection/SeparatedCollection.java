package ru.osslabs.graph.collection;


import java.util.Collection;
import java.util.Optional;

/**
 * Created by ikuchmin on 21.03.16.
 */
public interface SeparatedCollection<C, K, V, GM extends SeparatedCollection<C, K, V, GM>> extends GraphMap<K, V, GM> {

    SeparatedCollection<C, K, V, GM> put(C category, K key, V value);

    Optional<V> get(K key, C category);

    boolean containsKeyByCategory(K key, C category);

    boolean containsValueByCategory(V value, C category);

    Collection<K> entriesByCategory(C category);
}
