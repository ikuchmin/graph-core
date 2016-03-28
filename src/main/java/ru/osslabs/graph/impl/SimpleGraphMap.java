package ru.osslabs.graph.impl;

import lombok.experimental.Delegate;
import ru.osslabs.graph.GraphMap;

import java.util.*;

/**
 * Created by ikuchmin on 23.03.16.
 */
public class SimpleGraphMap<K, V> implements GraphMap<K, V> {
    protected final Map<K, V> innerMap = new HashMap<>();

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(innerMap.get(key));
    }

    @Override
    public GraphMap<K, V> put(K key, V value) {
        innerMap.put(key, value);
        return this;
    }

    @Override
    public boolean containsKey(K key) {
        return innerMap.containsKey(key);
    }

    @Override
    public Set<K> keySet() {
        return innerMap.keySet();
    }

    @Override
    public GraphMap<K, V> putAll(GraphMap<? extends K, ? extends V> source) {
        if (!(source instanceof SimpleGraphMap)) throw new IllegalArgumentException("Method works only with source instance of SimpleGraphMap");

        innerMap.putAll(((SimpleGraphMap)source).innerMap);

        return this;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return innerMap.entrySet();
    }

    public Iterator<Map.Entry<K, V>> iterator() {
        return innerMap.entrySet().iterator();
    }


}
