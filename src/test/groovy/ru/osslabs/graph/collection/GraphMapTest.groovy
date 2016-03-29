package ru.osslabs.graph.collection

import spock.lang.Specification

/**
 * Created by ikuchmin on 29.03.16.
 */
abstract class GraphMapTest extends Specification {
    abstract GraphMap getGraphMap();

    def "graph map should have iterator for walk on key set"() {
        given:
        graphMap.put('a1', 'v1').put('a2', 'v2')

        when:
        def expectMap = graphMap.collect { it.key.startsWith('a') }

        then:
        expectMap.size() == 2
    }

    def "if key equals null that graph should return true"() {
        given:
        graphMap.put('a1', 'v1').put('a2', 'v2')

        expect:
        graphMap.containsKey('a1')
        graphMap.containsKey('a2')
        !graphMap.containsKey('a3')
    }

    def "empty set is contain in set. For details read graph theory"() {
        given:
        graphMap.put('a1', 'v1')

        expect:
        graphMap.containsKey(null)
        graphMap.containsAllKey(null)
        graphMap.containsAllKey([])
        graphMap.containsAllKey(['a1', null])
    }
}
