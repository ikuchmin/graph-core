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
}
