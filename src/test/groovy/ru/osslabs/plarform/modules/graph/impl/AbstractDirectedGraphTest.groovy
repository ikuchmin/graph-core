package ru.osslabs.plarform.modules.graph.impl

import ru.osslabs.plarform.modules.graph.Edge
import ru.osslabs.plarform.modules.graph.GraphMap
import spock.lang.Ignore
import spock.lang.Specification

import java.util.function.BiFunction

/**
 * Created by ikuchmin on 23.03.16.
 */
class AbstractDirectedGraphTest extends Specification {

    def directedGraph = new StubDirectClass(ExEdge.metaClass.&invokeConstructor)

    def "graph should is capability add vertex"() {
        when:
        directedGraph.addVertex('v1')

        then:
        directedGraph.containsVertex('v1')
    }

    def "null can not added in graph"() {
        when:
        directedGraph.addVertex(null)

        then:
        thrown IllegalArgumentException
    }

    def "graph should is capability add collection of vertex"() {
        when:
        directedGraph.addVertices('v1', 'v2', 'v3')

        then:
        directedGraph.containsVertex('v1')
        directedGraph.containsVertex('v2')
        directedGraph.containsVertex('v3')

        when:
        directedGraph.addVertices('v4', null, 'v5')

        then:
        thrown IllegalArgumentException
        !directedGraph.containsVertex('v4')
        !directedGraph.containsVertex('v5')

        when:
        directedGraph.addVertices(['v6', 'v7', 'v8'])

        then:
        directedGraph.containsVertex('v6')
        directedGraph.containsVertex('v7')
        directedGraph.containsVertex('v8')

        when:
        directedGraph.addVertices('v9', null, 'v10')

        then:
        thrown IllegalArgumentException
        !directedGraph.containsVertex('v9')
        !directedGraph.containsVertex('v10')
    }

    def "if graph contains vertex that method 'contains' should return true otherwise else"() {
        given:
        directedGraph.addVertices('v1', 'v2', 'v3')

        expect:
        directedGraph.containsVertex('v1')
        !directedGraph.containsVertex('v4')
        !directedGraph.containsVertex(null)
        directedGraph.containsVertices(null) == [false]
        directedGraph.containsVertices(null, 'v2') == [false, true]
        directedGraph.containsVertices('v2', 'v3') == [true, true]
        directedGraph.containsVertices('v2', 'v3', 'v4') == [true, true, false]
        directedGraph.containsVertices('v2', 'v3', null) == [true, true, false]
    }

    def "graph should is capability add edge of vertices"() {
        given:
        directedGraph.addVertices('v1', 'v2')

        when:
        directedGraph.addEdge('v1', 'v2')

        then:
        directedGraph.containsOutgoingVertices('v1', 'v2') == [true]
        directedGraph.containsIncomingVertices('v2', 'v1') == [true]

        when:
        directedGraph.addEdge('v1', 'v4')

        then:
        thrown IllegalArgumentException

        when:
        directedGraph.addEdge(null, 'v2')

        then:
        thrown IllegalArgumentException

        when:
        directedGraph.addEdge('v1', null)

        then:
        thrown IllegalArgumentException

        when:
        directedGraph.addEdge(null, null)

        then:
        thrown IllegalArgumentException
    }

    def "graph should is capability add edge"() {
        given:
        directedGraph.addVertices('v1', 'v2')

        when:
        directedGraph.addEdge(new ExEdge<>('v1', 'v2'))

        then:
        directedGraph.containsOutgoingVertices('v1', 'v2') == [true]
        directedGraph.containsIncomingVertices('v2', 'v1') == [true]

        when:
        directedGraph.addEdge(null)

        then:
        thrown IllegalArgumentException
    }

    @Ignore("It is bug in groovy https://issues.apache.org/jira/browse/GROOVY-7799")
    def "graph should is capability add edges of vertices as varargs"() {
        given:
        directedGraph.addVertices('v1', 'v2', 'v3', 'v4', 'v5')

        when:
        directedGraph.addEdges(
                new ExEdge<>('v1', 'v2'),
                new ExEdge<>('v1', 'v3'))

        then:
        directedGraph.containsOutgoingVertices('v1', 'v2', 'v3')
        directedGraph.containsIncomingVertices('v2', 'v1')
        directedGraph.containsIncomingVertices('v3', 'v1')

        when:
        directedGraph.addEdges(
                new ExEdge<>('v1', 'v4'),
                null,
                new ExEdge<>('v1', 'v5'))

        then:
        thrown IllegalArgumentException
    }

    def "graph should is capability add collection of edge"() {
        given:
        directedGraph.addVertices('v1', 'v2', 'v3', 'v4', 'v5', 'v6')

        when:
        directedGraph.addEdges([new ExEdge<>('v1', 'v2'),
                                new ExEdge<>('v1', 'v3'),
                                new ExEdge<>('v2', 'v3')])

        then:
        directedGraph.containsOutgoingVertices('v1', 'v2', 'v3') == [true, true]
        directedGraph.containsOutgoingVertices('v2', 'v3') == [true]
        directedGraph.containsIncomingVertices('v2', 'v1') == [true]
        directedGraph.containsIncomingVertices('v3', 'v1', 'v2') == [true, true]

        when:
        directedGraph.addEdges([new ExEdge<>('v4', 'v5'),
                                null,
                                new ExEdge<>('v4', 'v6')])
        then:
        thrown IllegalArgumentException
        directedGraph.containsOutgoingVertices('v4', 'v5', 'v6') == [false, false]
        directedGraph.containsIncomingVertices('v5', 'v4') == [false]
        directedGraph.containsIncomingVertices('v6', 'v4') == [false]
    }

    def "graph has capability for test outgoing and incoming vertices for some vertex"() {
        given:
        directedGraph.addVertex('v1').addVertex('v2').addVertex('v3')
                .addEdge('v1', 'v2').addEdge('v1', 'v3')

        expect:
        directedGraph.containsOutgoingVertices('v1', 'v2', 'v3', 'v4', null) == [true, true, false, false]
        directedGraph.containsIncomingVertices('v2', 'v1') == [true]
        directedGraph.containsIncomingVertices('v3', 'v1') == [true]
    }

    def "graph hasn't capability test contains edge"() {
        given:
        directedGraph.addVertices('v1', 'v2').addEdge('v1', 'v2')

        when:
        directedGraph.containsEdge(new ExEdge<>('v1', 'v2'))

        then:
        thrown UnsupportedOperationException
    }

    def "graph should return collection of edges by vertex"() {
        given:
        def e1 = new ExEdge<>('v1', 'v2')
        def e2 = new ExEdge<>('v1', 'v3')

        directedGraph.addVertices('v1', 'v2', 'v3')
                .addEdge(e1).addEdge(e2)

        expect:
        directedGraph.edgesOf('v1').containsAll(e1, e2)
        directedGraph.edgesOf('v2').empty
        directedGraph.edgesOf('v3').empty
    }

    def "graph should return collection of outgoing edges"() {
        given:
        def e1 = new ExEdge<>('v1', 'v2')
        def e2 = new ExEdge<>('v1', 'v3')

        directedGraph.addVertices('v1', 'v2', 'v3')
                .addEdge(e1).addEdge(e2)

        expect:
        directedGraph.outgoingEdgesOf('v1').containsAll(e1, e2)
        directedGraph.outgoingEdgesOf('v2').empty
        directedGraph.outgoingEdgesOf('v3').empty
    }

    def "graph should return collection of incoming edges"() {
        given:
        def e1 = new ExEdge<>('v1', 'v2')
        def e2 = new ExEdge<>('v1', 'v3')

        directedGraph.addVertices('v1', 'v2', 'v3')
                .addEdge(e1).addEdge(e2)

        expect:
        directedGraph.incomingEdgesOf('v1').empty
        directedGraph.incomingEdgesOf('v2').containsAll(e1)
        directedGraph.incomingEdgesOf('v3').containsAll(e2)

    }

    def "if vertices aren't in graph or argument equals null that edgeOf methods should return empty list"() {
        expect:
        directedGraph.edgesOf('v4') == []
        directedGraph.edgesOf(null) == []
        directedGraph.incomingEdgesOf('v4') == []
        directedGraph.incomingEdgesOf(null) == []
        directedGraph.outgoingEdgesOf('v4') == []
        directedGraph.outgoingEdgesOf(null) == []
    }

    def "graph should is capability return all vertices"() {
        given:
        directedGraph.addVertices('v1', 'v2', 'v3')

        expect:
        directedGraph.vertexSet().containsAll('v1', 'v2', 'v3')

    }

    def "if source graph and target graph have equals vertices they should be merged"() {
        given:
        directedGraph.addVertices('v1', 'v2', 'v3')
        def source = new StubDirectClass(ExEdge.metaClass.&invokeConstructor)
                .addVertices('v2', 'v3', 'v4')

        when:
        directedGraph.addGraph(source)

        then:
        directedGraph.containsVertices('v1', 'v2', 'v3', 'v4')

    }

//    def "graph should is capability add another directed graph into"() {
//        given:
//        directedGraph.addVertices('v1', 'v2', 'v3').addEdge()
//        def source = new StubDirectClass(ExEdge.metaClass.&invokeConstructor)
//
//
//    }
//
//    def "if another source graph has equals vertices with this graph that vertices should be merged"() {
//
//
//    }

    class StubDirectClass<V, E extends Edge<V>> extends AbstractDirectedGraph<V, E> {

        final GraphMap<V, DirectedEdgeContainer<V, E>> vertices = new SimpleGraphMap<>()
        BiFunction<V, V, E> edgeFactory

        StubDirectClass(BiFunction<V, V, E> edgeFactory) {
            this.edgeFactory = edgeFactory
        }

        @Override
        BiFunction<V, V, E> edgeFactory() {
            return this.edgeFactory
        }

        @Override
        protected GraphMap<V, DirectedEdgeContainer<V, E>> graphMap() {
            return vertices
        }
    }
}
