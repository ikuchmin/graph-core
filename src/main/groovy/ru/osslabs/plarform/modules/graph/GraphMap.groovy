package ru.osslabs.plarform.modules.graph;

import java.util.Optional;

/**
 * Created by ikuchmin on 23.03.16.
 */
public interface GraphMap<K, V> extends Iterable<Map.Entry<K, V>> /*extends Map<K, V> */{

    Optional<V> get(K key);

    GraphMap<K, V> put(K key, V value);

    boolean containsKey(K key);

    Set<K> keySet();

    GraphMap<K, V> putAll(GraphMap<? extends K, ? extends V> m);

    Set<Map.Entry<K, V>> entrySet();
}