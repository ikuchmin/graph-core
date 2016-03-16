package ru.osslabs.plarform.modules.graph.impl

import groovy.transform.Canonical
import ru.osslabs.plarform.modules.graph.Edge

/**
 * Created by ikuchmin on 14.03.16.
 */
@Canonical
class ExEdge<T> implements Edge<T> {
    T source
    T target

    @Override
    T getSource() {
        return source
    }

    @Override
    T getTarget() {
        return target
    }
}
