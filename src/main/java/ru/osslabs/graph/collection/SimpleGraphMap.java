package ru.osslabs.graph.collection;

import java.util.*;

/**
 * Created by ikuchmin on 23.03.16.
 */
public class SimpleGraphMap<K, V> implements GraphMap<K, V, SimpleGraphMap<K, V>> {
    protected final Map<K, V> innerMap = new HashMap<>();

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(innerMap.get(key));
    }

    @Override
    public SimpleGraphMap<K, V> put(K key, V value) {
        innerMap.put(key, value);
        return this;
    }

    @Override
    public boolean containsKey(K key) {
        return key == null || innerMap.containsKey(key);
    }

    @Override
    public boolean containsAllKey(Collection<? extends K> keys) {
        return keys == null || keys.stream()
                .map(this::containsKey)
                .reduce(true, (acc, k) -> acc && k);
    }

    @Override
    public boolean containsValue(V value) {
        return innerMap.containsValue(value);
    }

    @Override
    public boolean containsAllValue(Collection<? extends V> values) {
        return innerMap.values().containsAll(values);
    }

    @Override
    public Set<K> keys() {
        return innerMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return innerMap.values();
    }

    @Override
    public SimpleGraphMap<K, V> putAll(GraphMap<? extends K, ? extends V, ? super SimpleGraphMap<K, V>> source) {
        if (!(source instanceof SimpleGraphMap)) throw new IllegalArgumentException("Method works only with source instance of SimpleGraphMap");

        innerMap.putAll(((SimpleGraphMap)source).innerMap);

        return this;
    }

    @Override
    public Set<Map.Entry<K, V>> entries() {
        return innerMap.entrySet();
    }

    public Iterator<Map.Entry<K, V>> iterator() {
        return innerMap.entrySet().iterator();
    }


}
