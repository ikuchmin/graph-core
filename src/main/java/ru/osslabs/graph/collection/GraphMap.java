package ru.osslabs.graph.collection;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Created by ikuchmin on 23.03.16.
 */
public interface GraphMap<K, V, GM extends GraphMap<K, V, GM>> extends Iterable<Map.Entry<K, V>> /*extends Map<K, V> */{

    Optional<V> get(K key);

    GM put(K key, V value);

    boolean containsKey(K key);

    boolean containsAllKey(Collection<? extends K> keys);

    boolean containsValue(V value);

    boolean containsAllValue(Collection<? extends V> values);

    Collection<K> keys();

    Collection<V> values();

//    GM putAll(GraphMap<? extends K, ? extends V, ? super GM> source);

    GM putAll(GM source);

    Collection<Map.Entry<K, V>> entries();
}