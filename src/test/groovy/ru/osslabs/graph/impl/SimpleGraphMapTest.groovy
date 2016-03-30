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

    def "graph is capability put another graph map into"() {
        given:
        graphMap.put('a1', 'v1').put('a2', 'v2')
        def source = new SimpleGraphMap().put('a3', 'v3')

        when:
        graphMap.putAll(source)

        then:
        graphMap.containsAllKey(['a1', 'a2', 'a3'])
        graphMap.containsAllValue(['v1', 'v2', 'v3'])
    }
}
