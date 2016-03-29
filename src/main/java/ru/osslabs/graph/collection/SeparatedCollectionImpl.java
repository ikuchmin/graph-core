package ru.osslabs.graph.collection;

import javaslang.Function2;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by ikuchmin on 21.03.16.
 */
public class SeparatedCollectionImpl<C, K, V> implements SeparatedCollection<C, K, V, SeparatedCollectionImpl<C, K, V>> {

    protected final Map<C, Map<K, V>> innerMap = new HashMap<>();
    protected final Set<CompositeKey<K, C>> keys = new HashSet<>();
    protected final Function<K, C> chooseCategoryByKey;

    /**
     * All function in constructor shouldn't return null
     *
     * @param chooseCategoryByKey
     * @param categories
     */
    @SafeVarargs
    public SeparatedCollectionImpl(Function2<C[], K, C> chooseCategoryByKey,
                                   C... categories) {
        this.chooseCategoryByKey = Function2.of(chooseCategoryByKey).apply(categories);

        for (C category : categories) {
            this.innerMap.put(category, new HashMap<>());
        }
    }

    public static <K, V> SeparatedCollectionImpl<Class<?>, K, V> byClass(Class<?>... categories) {

        Function2<Class<?>[], K, Class<?>> classChooseFunctionByKey =
                (classes, key) -> {
                    for (Class<?> cls : classes) {
                        if (cls == key.getClass()) return cls;
                    }
                    return Object.class;
                };

        Class<?>[] categoriesWithObject = Arrays.copyOf(categories, categories.length + 1);
        categoriesWithObject[categoriesWithObject.length - 1] = Object.class;
        return new SeparatedCollectionImpl<>(classChooseFunctionByKey, categoriesWithObject);
    }

    @Override
    public SeparatedCollectionImpl<C, K, V> put(K key, V value) {
        if (key == null || value == null)
            throw new IllegalArgumentException(format("Key or value equals null. Key: %s, Value: %s", key, value));

        return put(chooseCategoryByKey.apply(key), key, value);
    }

    @Override
    public SeparatedCollectionImpl<C, K, V> put(C category, K key, V value) {
        if (key == null || value == null)
            throw new IllegalArgumentException(format("Key or value equals null. Key: %s, Value: %s", key, value));
        if (!innerMap.containsKey(category))
            throw new IllegalArgumentException(format("Category with name %s not found", category));

        innerMap.get(category).put(key, value);
        keys.add(new CompositeKey<>(key, category));

        return this;
    }

    @Override
    public Optional<V> get(K key) {
        if (key == null) return Optional.empty();

        return get(key, chooseCategoryByKey.apply(key));
    }

    @Override
    public Optional<V> get(K key, C category) {
        if (category == null) return Optional.empty();
        if (!innerMap.containsKey(category)) return Optional.empty();

        return Optional.ofNullable(innerMap
                .get(category)
                .get(key));
    }


    @Override
    public boolean containsKey(K key) {
        return key == null || keys.contains(new CompositeKey<>(key, chooseCategoryByKey.apply(key)));

    }

    @Override
    public boolean containsAllKey(Collection<? extends K> keys) {
        return keys == null || keys.stream()
                .map(this::containsKey)
                .reduce(true, (acc, k) -> acc && k);
    }

    @Override
    public List<K> keys() {
        return innerMap.values().stream()
                .flatMap(m -> m.keySet().stream())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public SeparatedCollectionImpl<C, K, V> putAll(GraphMap<? extends K, ? extends V, ? super SeparatedCollectionImpl<C, K, V>> source) {
        if (!(source instanceof SeparatedCollectionImpl)) throw new IllegalArgumentException("Method works only with source instance of SimpleGraphMap");

        innerMap.putAll(((SeparatedCollectionImpl)source).innerMap);

        return this;
    }

    @Override
    public Collection<Map.Entry<K, V>> entries() {
        return innerMap.values().stream()
                .flatMap(m -> m.entrySet().stream())
                .collect(toSet());
    }

    @Override
    public boolean containsKeyByCategory(K key, C category) {
        return key == null || category != null && keys.contains(new CompositeKey<>(key, category));

    }

    @Override
    public boolean containsValueByCategory(V value, C category) {
        return value != null && category != null
                && innerMap.containsKey(category)
                && innerMap.get(category).values().stream()
                .filter(value::equals).findAny().isPresent();
    }

    @Override
    public Collection<K> entriesByCategory(C category) {
        if (category == null) return Collections.emptyList();
        if (!innerMap.containsKey(category)) return Collections.emptyList();

        return innerMap.get(category).keySet();
    }

    @Override
    public boolean containsValue(V value) {
        return value != null && innerMap.values().stream()
                .flatMap(v -> v.values().stream())
                .filter(value::equals)
                .findAny().isPresent();
    }

    @Override
    public boolean containsAllValue(Collection<? extends V> keys) {
        return false;
    }

    public Function<K, C> getChooseCategoryByKey() {
        return chooseCategoryByKey;
    }

    protected <R> R objectNotNull(Supplier<R> firstSupplier, Supplier<R> secondSupplier) {
        R val = firstSupplier.get();
        if (val != null) {
            return val;
        } else {
            return secondSupplier.get();
        }
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return innerMap.values().stream()
                .flatMap(m -> m.entrySet().stream())
                .iterator();
    }

    // Class is not class TT, it is class for category
    protected class CompositeKey<TT, CC> {
        TT key;
        CC cls;

        public CompositeKey(TT key, CC cls) {
            this.key = key;
            this.cls = cls;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CompositeKey)) return false;
            CompositeKey<?, ?> that = (CompositeKey<?, ?>) o;
            return Objects.equals(key, that.key) &&
                    Objects.equals(cls, that.cls);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, cls);
        }
    }
}
