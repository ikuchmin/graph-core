package ru.osslabs.graph.collection;

import java.util.*;

/**
 * Created by ikuchmin on 23.03.16.
 */
public class SimpleGraphMap<K, V> extends AbstractGraphMap<K,V,SimpleGraphMap<K,V>> {
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
    public boolean containsValue(V value) {
        return value == null || innerMap.containsValue(value);
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
    public SimpleGraphMap<K, V> putAll(SimpleGraphMap<K, V> source) {
        innerMap.putAll(source.innerMap);

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
