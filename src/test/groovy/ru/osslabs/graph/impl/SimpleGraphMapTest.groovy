package ru.osslabs.graph.impl

import spock.lang.Specification

/**
 * Created by ikuchmin on 24.03.16.
 */
class SimpleGraphMapTest extends Specification {

    def "graph map should have iterator for walk on key set"() {
        given:
        def graphMap = new SimpleGraphMap<String, String>().put('a1', 'v1').put('a2', 'v2')

        when:
        def expectMap = graphMap.collect { it.key.startsWith('a') }

        then:
        expectMap.size() == 2
    }
}
