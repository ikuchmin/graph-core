package ru.osslabs.plarform.modules.graph.impl;

import java.io.Serializable;
import java.util.*;

/**
 * Created by ikuchmin on 14.03.16.
 */
public class Graphs {
    public static <K, V> Map<K, V> identity(Map<? extends K, ? extends V> map) { return new UnmodifiableMap<>(map); }
    public static <T> List<T> identity(List<? extends T> list) { return new UnmodifiableList(list); }

    public void m1() {
        List<Integer> in = new ArrayList<>();
        List<Number> out = this.identity(in);
    }

    private static class UnmodifiableMap<K,V> extends AbstractMap<K, V> implements Serializable {
        private static final long serialVersionUID = -1034234728574286014L;

        private final Map<? extends K, ? extends V> m;

        UnmodifiableMap(Map<? extends K, ? extends V> m) {
            if (m == null)
                throw new NullPointerException();
            this.m = m;
        }

        @Override
        public Set<Entry<K, V>> entrySet() {
            return null;
        }
    }

    static class UnmodifiableList<E> extends AbstractList<E>
            implements List<E> {
        private static final long serialVersionUID = -283967356065247728L;

        final List<? extends E> list;

        UnmodifiableList(List<? extends E> list) {
//            super(list);
            this.list = list;
        }

        @Override
        public E get(int index) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }
    }
}
