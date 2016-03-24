package ru.osslabs.plarform.modules.graph.impl

import groovy.transform.CompileStatic
import ru.osslabs.plarform.modules.graph.GraphMap

/**
 * Created by ikuchmin on 23.03.16.
 */
@CompileStatic
class SimpleGraphMap<K, V> implements GraphMap<K, V> {
    @Delegate(interfaces = false, includes = ['containsKey', 'keySet', 'entrySet'])
    protected final Map<K, V> map = new HashMap<>()

    @Override
    Optional<V> get(K key) {
        return Optional.ofNullable(map.get(key))
    }

    @Override
    GraphMap<K, V> put(K key, V value) {
        map.put(key, value)
        return this
    }

    @Override
    GraphMap<K, V> putAll(GraphMap<? extends K, ? extends V> source) {
        if (!source instanceof SimpleGraphMap) throw new IllegalArgumentException("Method works only with source instance of SimpleGraphMap")

        map.putAll(((SimpleGraphMap)source).map)

        return this
    }

    Iterator<Map.Entry<K, V>> iterator() {
        return map.entrySet().iterator()
    }
}
