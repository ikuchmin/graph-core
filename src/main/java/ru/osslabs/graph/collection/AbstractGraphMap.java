package ru.osslabs.graph.collection;

import lombok.ToString;

import java.util.Collection;

/**
 * Created by ikuchmin on 30.03.16.
 */
@ToString
public abstract class AbstractGraphMap<K, V, GM extends GraphMap<K, V, GM>> implements GraphMap<K, V, GM> {
    @Override
    public boolean containsAllKey(Collection<? extends K> keys) {
        return keys == null || keys.stream()
                .map(this::containsKey)
                .reduce(true, (acc, k) -> acc && k);
    }

    @Override
    public boolean containsAllValue(Collection<? extends V> values) {
        return values == null || values.stream()
                .map(this::containsValue)
                .reduce(true, (acc, k) -> acc && k);
    }
}
