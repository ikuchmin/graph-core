package ru.osslabs.graph.impl

import ru.osslabs.graph.collection.GraphMap
import ru.osslabs.graph.collection.GraphMapTest
import ru.osslabs.graph.collection.SimpleGraphMap
import spock.lang.Specification

/**
 * Created by ikuchmin on 24.03.16.
 */
class SimpleGraphMapTest extends GraphMapTest {

    SimpleGraphMap<String, String> simpleGraphMap = new SimpleGraphMap<>()

    @Override
    GraphMap getGraphMap() {
        return simpleGraphMap
    }
}
