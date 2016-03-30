package ru.osslabs.graph.collection

import spock.lang.Ignore
import spock.lang.Specification

/**
 * Created by ikuchmin on 29.03.16.
 */
abstract class GraphMapTest extends Specification {
    abstract GraphMap getGraphMap();

    def "graph map is capability for putting kay/value"() {
        when:
        graphMap.put('a1', 'v1')

        then:
        graphMap.containsKey('a1')
        graphMap.containsValue('v1')

    }

    def "if graph map has entry that i can get value by key"() {
        given:
        graphMap.put('a1', 'v1')

        expect:
        graphMap.get('a1') == Optional.of('v1')
        graphMap.get('a2') == Optional.empty()
        graphMap.get(null) == Optional.empty()

    }

    def "if key is contain in graph map that contain functions should return true otherwise false"() {
        given:
        graphMap.put('a1', 'v1').put('a2', 'v2')

        expect:
        graphMap.containsKey('a1')
        graphMap.containsKey('a2')
        !graphMap.containsKey('a3')
    }

    def "if key is contain in graph map that containAll functions should return true otherwise false"() {
        given:
        graphMap.put('a1', 'v1').put('a2', 'v2')

        expect:
        graphMap.containsAllKey(['a1', 'a2'])
        !graphMap.containsAllKey(['a1', 'a3'])
    }

    def "if value is contain in graph map that contain functions should return true otherwise false"() {
        given:
        graphMap.put('a1', 'v1')

        expect:
        graphMap.containsValue('v1')
        !graphMap.containsValue('v2')
    }

    def "if value is contain in graph map that containAll functions should return true otherwise false"() {
        given:
        graphMap.put('a1', 'v1').put('a2', 'v2')

        expect:
        graphMap.containsAllValue(['v1', 'v2'])
        !graphMap.containsAllValue(['v1', 'v3'])
    }

    def "graph is capability return collection of key"() {
        given:
        graphMap.put('a1', 'v1').put('a2', 'v2')

        expect:
        graphMap.keys().toList() == ['a1', 'a2']
    }

    def "graph is capability return collection of value"() {
        given:
        graphMap.put('a1', 'v1').put('a2', 'v2')

        expect:
        graphMap.values().toList() == ['v1', 'v2']
    }

    def "graph is capability return collection of entries"() {
        given:
        graphMap.put('a1', 'v1').put('a2', 'v2')

        expect:
        graphMap.entries().toList() == [new MapEntry('a1', 'v1'), new MapEntry('a2', 'v2')]
    }

    @Ignore('TODO')
    def "graph is capability put another graph map into. Todo"() {
        given:
        graphMap.put('a1', 'v1').put('a2', 'v2')
        def source = new SimpleGraphMap().put('a3', 'v3')

        when:
        graphMap.putAll(source)

        then:
        graphMap.containsAllKey(['a1', 'a2', 'a3'])
        graphMap.containsAllValue(['v1', 'v2', 'v3'])
    }

    def "graph map should have iterator for walk on key set"() {
        given:
        graphMap.put('a1', 'v1').put('a2', 'v2')

        when:
        def expectMap = graphMap.collect { it.key.startsWith('a') }

        then:
        expectMap.toList() == [true, true]
    }

    def "empty set is contain in set. For details read graph theory"() {
        given:
        graphMap.put('a1', 'v1')

        expect:
        graphMap.containsKey(null)
        graphMap.containsValue(null)
        graphMap.containsAllKey([])
        graphMap.containsAllKey([null])
        graphMap.containsAllKey(['a1', null])
        graphMap.containsAllValue([])
        graphMap.containsAllValue([null])
        graphMap.containsAllValue(['v1', null])
    }
}
