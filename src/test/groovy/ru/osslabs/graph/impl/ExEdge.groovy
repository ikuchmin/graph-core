package ru.osslabs.graph.impl

import groovy.transform.Canonical
import ru.osslabs.graph.Edge

/**
 * Created by ikuchmin on 14.03.16.
 */
@Canonical
class ExEdge<T> implements Edge<T> {
    T source
    T target

    ExEdge(List<T> args) {
        this(args[0], args[1])
    }

    ExEdge(Map<String, T> args) {
        this(args.source, args.target)
    }

    ExEdge(T source, T target) {
        this.source = source
        this.target = target
    }

    @Override
    T getSource() {
        return source
    }

    @Override
    T getTarget() {
        return target
    }
}
