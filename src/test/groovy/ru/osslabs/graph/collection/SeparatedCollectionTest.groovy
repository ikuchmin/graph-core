package ru.osslabs.graph.collection

/**
 * Created by ikuchmin on 29.03.16.
 */
abstract class SeparatedCollectionTest extends GraphMapTest {
    abstract SeparatedCollection getGraphMap();

    def "contains value functions in collection should comes null arguments"() {
        given:
        graphMap.put(12, PRESENT)

        expect:
        !graphMap.containsValueByCategory(null, Integer)
        !graphMap.containsValueByCategory(PRESENT, null)
        !graphMap.containsValueByCategory(null, null)
    }

    def "empty set is contain in set by category. For details read graph theory"() {
        graphMap.containsKeyByCategory(null, Integer)
        graphMap.containsKeyByCategory(null, null)
    }

    def "if key in contains doesn't equals null and category equals null"() {
        !graphMap.containsKeyByCategory(12, null)
    }

    def "graph is capability put another graph map into"() {
        given:
        graphMap.put('a1', 'v1').put('a2', 'v2')
        def source = SeparatedCollectionImpl.byClass(Integer, BigInteger).put('a3', 'v3')

        when:
        graphMap.putAll(source)

        then:
        graphMap.containsAllKey(['a1', 'a2', 'a3'])
        graphMap.containsAllValue(['v1', 'v2', 'v3'])
    }

    def "if source and target graph map have different category by value that in target graph map shouldn't adding new category"() {
        given:
        graphMap.put('a1', 'v1').put('a2', 'v2')
        def source = SeparatedCollectionImpl.byClass(String, BigInteger).put('a3', 'v3')

        when:
        graphMap.putAll(source)

        then:
        graphMap.containsKeyByCategory('a3', Object)
        graphMap.containsValueByCategory('v3', Object)
        graphMap.containsAllKey(['a1', 'a2', 'a3'])
        graphMap.containsAllValue(['v1', 'v2', 'v3'])
    }
}
