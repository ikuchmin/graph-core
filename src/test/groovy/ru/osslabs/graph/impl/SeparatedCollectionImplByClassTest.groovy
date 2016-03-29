package ru.osslabs.graph.impl

import ru.osslabs.graph.collection.GraphMap
import ru.osslabs.graph.collection.GraphMapTest
import ru.osslabs.graph.collection.SeparatedCollection
import ru.osslabs.graph.collection.SeparatedCollectionImpl
import ru.osslabs.graph.collection.SeparatedCollectionTest
import spock.lang.Specification

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by ikuchmin on 21.03.16.
 */
class SeparatedCollectionImplByClassTest extends SeparatedCollectionTest {
    SeparatedCollectionImpl<Class<?>, Number, Object> separatedCollection = SeparatedCollectionImpl.byClass(Integer, BigInteger)

    public static PRESENT = new Object();

    @Override
    SeparatedCollection getGraphMap() {
        return separatedCollection
    }

    def "collection should put element into separated views"() {
        given:
        def ai = new AtomicInteger(256) // new AtomicInteger(259) != new AtomicInteger(259)
        def ad = new AtomicBoolean(true) // new AtomicBoolean(true) != new AtomicBoolean(true)
        def al = new AtomicLong(257) // new AtomicLong(257) != new AtomicLong(257)

        when:
        separatedCollection
                .put(new Integer(12), PRESENT).put(new Integer(24), PRESENT).put(new Integer(42), PRESENT)
                .put(new BigInteger(242), PRESENT).put(new BigInteger(240), PRESENT).put(new BigInteger(249), PRESENT)
                .put(ai, PRESENT).put(ad, PRESENT).put(al, PRESENT)
        then:
        separatedCollection.innerMap[Integer].keySet().containsAll(12, 24, 42)
        separatedCollection.innerMap[BigInteger].keySet().containsAll(new BigInteger(242), new BigInteger(240), new BigInteger(249))
        separatedCollection.innerMap[Object].keySet().containsAll(ai, ad, al)
    }

    def "if key contains in collection it should result true"() {
        given:
        separatedCollection
                .put(12, PRESENT).put(15, PRESENT).put(18, PRESENT)

        expect:
        [12, 15, 18].find(separatedCollection.&containsKey)
    }

    def "if element contains in collection it should result true"() {
        given:
        separatedCollection.put(12, PRESENT)

        expect:
        separatedCollection.containsValue(PRESENT)
    }

    def "put function with two arguments should comes correct arguments"() {
        expect:
        separatedCollection.put(k, v).get(k) == Optional.of(v)

        where:
        k                  | v
        12                 | 12
        new BigInteger(24) | 24
        new BigDecimal(48) | 48

    }

    def "put function with three arguments should comes correct arguments"() {
        expect:
        separatedCollection.put(c, k, v).get(k, c) == Optional.of(v)

        where:
        k                  | v  | c
        12                 | 12 | Integer
        new BigInteger(24) | 24 | BigInteger
        new BigDecimal(60) | 60 | BigInteger

    }

    def "if category hasn't in collection that it should trow exception"() {
        when:
        separatedCollection.put(BigDecimal, 12, PRESENT)

        then:
        thrown IllegalArgumentException

        when:
        separatedCollection.put(null, 12, PRESENT)

        then:
        thrown IllegalArgumentException
    }

    def "collection is allowing put concurent keys"() {
        given:
        separatedCollection.put(12, PRESENT)

        when:
        separatedCollection.put(12, "integer")

        then:
        separatedCollection.get(12) == Optional.of("integer")

    }

    def "if any of argument put functions equal null it should throw exception"() {
        when:
        separatedCollection.put(Integer, null, PRESENT)

        then:
        thrown IllegalArgumentException

        when:
        separatedCollection.put(Integer, 12, null)

        then:
        thrown IllegalArgumentException

        when:
        separatedCollection.put(null, PRESENT)

        then:
        thrown IllegalArgumentException

        when:
        separatedCollection.put(12, null)

        then:
        thrown IllegalArgumentException
    }

    def "collection should return value if key contains in collection"() {
        given:
        separatedCollection.put(12, 12)
                .put(new BigInteger(4567), PRESENT)
                .put(new BigInteger(12), '12')

        expect:
        separatedCollection.get(12) == Optional.of(12)
        separatedCollection.get(12, Integer) == Optional.of(12)
        !separatedCollection.get(25).isPresent()
    }

    def "collection should has method for testing contain key and value in collection by category"() {
        given:
        separatedCollection.put(12, PRESENT)

        expect:
        separatedCollection.containsKeyByCategory(12, Integer)
        !separatedCollection.containsKeyByCategory(24, Integer)
        !separatedCollection.containsKeyByCategory(12, BigInteger)
        separatedCollection.containsValueByCategory(PRESENT, Integer)
        !separatedCollection.containsValueByCategory("value", Integer)
        !separatedCollection.containsValueByCategory(PRESENT, BigInteger)

    }

    def "if category doesn't present in graph that it put value in Object class category"() {
        given:
        separatedCollection.put(new BigDecimal(12), 'bigDecimial')

        expect:
        separatedCollection.get(new BigDecimal(12)) == Optional.of('bigDecimial')
    }

    def "if graph hasn't vertex that it should return Optional.empty"() {
        expect:
        separatedCollection.get(12) == Optional.empty()
        separatedCollection.get(12, Integer) == Optional.empty()
    }

    def "if object has in collection but category in get isn't correct collection return emptu object"() {
        given:
        separatedCollection.put(22, "integer")

        expect:
        separatedCollection.get(12, BigInteger) == Optional.empty()
    }

    def "get functions in collection should comes null arguments"() {
        expect:
        separatedCollection.get(null) == Optional.empty()
        separatedCollection.get(null, BigInteger) == Optional.empty()
        separatedCollection.get(12, null) == Optional.empty()
        separatedCollection.get(null, null) == Optional.empty()
    }

    def "get entries should return entries by type depends on category argument"() {
        given:
        separatedCollection.put(12, PRESENT)
        separatedCollection.put(new BigInteger(12), PRESENT)
        separatedCollection.put(new BigDecimal(12), PRESENT)

        expect:
        separatedCollection.entriesByCategory(Integer).toList() == [12]
        separatedCollection.entriesByCategory(BigInteger).toList() == [new BigInteger(12)]
        separatedCollection.entriesByCategory(Object).toList() == [new BigDecimal(12)]
//        separatedCollection.entriesByCategory(BigDecimal).toList() == [new BigDecimal(12)] // It is choose entries by category where category use not exactly. Collection may be return function for get category by category and category by key

    }
}
