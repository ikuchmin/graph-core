package ru.osslabs.graph.impl

import ru.osslabs.graph.DirectedGraph
import ru.osslabs.graph.Edge
import ru.osslabs.graph.Graph
import spock.lang.Ignore
import spock.lang.Specification

/**
 * Created by ikuchmin on 23.03.16.
 */
abstract class AbstractDirectedGraphTest extends Specification {

//    def directedGraph = new StubDirectClass(ExEdge.metaClass.&invokeConstructor)
    abstract DirectedGraph getDirectedGraph()
    abstract Edge edgeFactory(String v1, String v2);
    abstract List verticesFactory(List<String> vertices);
    abstract Graph graphFactory();

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
        directedGraph.containsVertices('v2') == [true]
        directedGraph.containsVertices('v2', 'v3') == [true, true]
        directedGraph.containsVertices('v2', 'v3', 'v4') == [true, true, false]
        directedGraph.containsVertices(['v2']) == [true]
        directedGraph.containsVertices(['v2', 'v3']) == [true, true]
        directedGraph.containsVertices(['v2', 'v3', 'v4']) == [true, true, false]

        directedGraph.containsVertex(null)
        directedGraph.containsVertices(null) == [true]
        directedGraph.containsVertices(null, 'v2') == [true, true]
        directedGraph.containsVertices('v2', 'v3', null) == [true, true, true]
        directedGraph.containsVertices([]) == [true]
        directedGraph.containsVertices([null]) == [true]
        directedGraph.containsVertices([null, 'v2']) == [true, true]
        directedGraph.containsVertices(['v2', 'v3', null]) == [true, true, true]
        directedGraph.containsVertices(['v2', 'v3', 'v4', null]) == [true, true, false, true]


    }

    def "if graph contains vertex that method 'containsAll' should return true otherwise else"() {
        given:
        directedGraph.addVertices('v1', 'v2', 'v3')

        expect:
        directedGraph.containsAllVertices('v1')
        directedGraph.containsAllVertices('v1', 'v2')
        !directedGraph.containsAllVertices('v1', 'v4')
        directedGraph.containsAllVertices(['v1'])
        directedGraph.containsAllVertices(['v1', 'v2'])
        !directedGraph.containsAllVertices(['v1', 'v4'])

        directedGraph.containsAllVertices([])
        directedGraph.containsAllVertices(null)
        directedGraph.containsAllVertices('v1', null)
        directedGraph.containsAllVertices([null])
        directedGraph.containsAllVertices(['v1', null])
        !directedGraph.containsAllVertices(['v4', null])

    }

    def "if graph contains edge that method 'containsAll' should return true otherwise else"() {
        given:
        def e1 = edgeFactory('v1', 'v2')
        def e2 = edgeFactory('v1', 'v3')
        def e3 = edgeFactory('v1', 'v4')

        directedGraph.addVertices('v1', 'v2', 'v3')
                .addEdge(e1).addEdge(e2)


        expect:
        directedGraph.containsAllEdges(e1)
        directedGraph.containsAllEdges(e1, e2)
        !directedGraph.containsAllEdges(e1, e3)
        directedGraph.containsAllEdges([e1])
        directedGraph.containsAllEdges([e1, e2])
        !directedGraph.containsAllEdges([e1, e3])

        directedGraph.containsAllEdges([])
        directedGraph.containsAllEdges(null)
        directedGraph.containsAllEdges(e1, null)
        directedGraph.containsAllEdges([null])
        directedGraph.containsAllEdges([e1, null])
        !directedGraph.containsAllEdges([e3, null])

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
        directedGraph.addEdge(edgeFactory('v1', 'v2'))

        then:
        directedGraph.containsOutgoingVertices('v1', 'v2') == [true]
        directedGraph.containsIncomingVertices('v2', 'v1') == [true]

        when:
        directedGraph.addEdge(null)

        then:
        thrown IllegalArgumentException
    }

    def "if edge is in graph that graph should throw exception"() {
        given:
        def e1 = edgeFactory('v3', 'v4')
        directedGraph.addVertices('v1', 'v2', 'v3', 'v4')
                .addEdge('v1', 'v2').addEdge(e1)

        when:
        directedGraph.addEdge('v1', 'v2')

        then:
        thrown IllegalArgumentException

        when:
        directedGraph.addEdge(e1)

        then:
        thrown IllegalArgumentException
    }

    @Ignore("It is bug in groovy https://issues.apache.org/jira/browse/GROOVY-7799")
    def "graph should is capability add edges of vertices as varargs"() {
        given:
        directedGraph.addVertices('v1', 'v2', 'v3', 'v4', 'v5')

        when:
        directedGraph.addEdges(
                edgeFactory('v1', 'v2'),
                edgeFactory('v1', 'v3'))

        then:
        directedGraph.containsOutgoingVertices('v1', 'v2', 'v3')
        directedGraph.containsIncomingVertices('v2', 'v1')
        directedGraph.containsIncomingVertices('v3', 'v1')

        when:
        directedGraph.addEdges(
                edgeFactory('v1', 'v4'),
                null,
                edgeFactory('v1', 'v5'))

        then:
        thrown IllegalArgumentException
    }

    def "graph should is capability add collection of edge"() {
        given:
        directedGraph.addVertices('v1', 'v2', 'v3', 'v4', 'v5', 'v6')

        when:
        directedGraph.addEdges([edgeFactory('v1', 'v2'),
                                edgeFactory('v1', 'v3'),
                                edgeFactory('v2', 'v3')])

        then:
        directedGraph.containsOutgoingVertices('v1', 'v2', 'v3') == [true, true]
        directedGraph.containsOutgoingVertices('v2', 'v3') == [true]
        directedGraph.containsIncomingVertices('v2', 'v1') == [true]
        directedGraph.containsIncomingVertices('v3', 'v1', 'v2') == [true, true]

        when:
        directedGraph.addEdges([edgeFactory('v4', 'v5'),
                                null,
                                edgeFactory('v4', 'v6')])
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

    def "if edge was added to graph that would it contains in graph"() {
        given:
        directedGraph.addVertex('v1').addVertex('v2')

        when:
        directedGraph.addEdge('v1', 'v2')

        then:
        directedGraph.containsEdge(edgeFactory('v1', 'v2'))
    }


    def "graph should return collection of edges by vertex"() {
        given:
        def e1 = edgeFactory('v1', 'v2')
        def e2 = edgeFactory('v1', 'v3')

        directedGraph.addVertices('v1', 'v2', 'v3')
                .addEdge(e1).addEdge(e2)

        expect:
        directedGraph.edgesOf('v1').containsAll(e1, e2)
        directedGraph.edgesOf('v2').empty
        directedGraph.edgesOf('v3').empty
    }

    def "graph should return collection of outgoing edges"() {
        given:
        def e1 = edgeFactory('v1', 'v2')
        def e2 = edgeFactory('v1', 'v3')

        directedGraph.addVertices('v1', 'v2', 'v3')
                .addEdge(e1).addEdge(e2)

        expect:
        directedGraph.outgoingEdgesOf('v1').toList() == [e1, e2]
        directedGraph.outgoingEdgesOf('v2').empty
        directedGraph.outgoingEdgesOf('v3').empty
    }

    def "graph should return collection of outgoing vertices"() {
        given:
        def e1 = edgeFactory('v1', 'v2')
        def e2 = edgeFactory('v1', 'v3')

        directedGraph.addVertices('v1', 'v2', 'v3')
                .addEdge(e1).addEdge(e2)

        expect:
        directedGraph.outgoingVerticesOf('v1').toList() == verticesFactory(['v2', 'v3'])
        directedGraph.outgoingVerticesOf('v2').empty
        directedGraph.outgoingVerticesOf('v3').empty
    }

    def "graph should return collection of incoming edges"() {
        given:
        def e1 = edgeFactory('v1', 'v2')
        def e2 = edgeFactory('v1', 'v3')

        directedGraph.addVertices('v1', 'v2', 'v3')
                .addEdge(e1).addEdge(e2)

        expect:
        directedGraph.incomingEdgesOf('v1').empty
        directedGraph.incomingEdgesOf('v2').toList() == [e1]
        directedGraph.incomingEdgesOf('v3').toList() == [e2]
    }

    def "graph should return collection of incoming vertices"() {
        given:
        def e1 = edgeFactory('v1', 'v2')
        def e2 = edgeFactory('v1', 'v3')

        directedGraph.addVertices('v1', 'v2', 'v3')
                .addEdge(e1).addEdge(e2)

        expect:
        directedGraph.incomingVerticesOf('v1').empty
        directedGraph.incomingVerticesOf('v2').toList() == verticesFactory(['v1'])
        directedGraph.incomingVerticesOf('v3').toList() == verticesFactory(['v1'])

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
        directedGraph.vertices.toList() == verticesFactory(['v1', 'v2', 'v3'])

    }

    def "graph should is capability return all edges"() {
        given:
        directedGraph.addVertices('v1', 'v2', 'v3')
                .addEdge('v1', 'v2').addEdge('v1', 'v3')

        expect:
        directedGraph.edges.toList() == [edgeFactory('v1', 'v2'), edgeFactory('v1', 'v3')]

    }

    def "collection of vertex was returned from graph should be unmodifiable"() {
        given:
        directedGraph.addVertices('v1', 'v2')

        when:
        directedGraph.vertices.add('v3')

        then:
        thrown UnsupportedOperationException

    }


    def "graph should is capability add another directed graph into"() {
        given:
        directedGraph.addVertices('v1', 'v2', 'v3')
        def source = graphFactory().addVertices('v4', 'v5', 'v6')

        when:
        directedGraph.addGraph(source)

        then:
        directedGraph.containsVertices('v1', 'v2', 'v3', 'v4', 'v5', 'v6') == [true, true, true, true, true, true]

    }

    def "if source graph and target graph have equals vertices they should be merged"() {
        given:
        directedGraph.addVertices('v1', 'v2', 'v3')
        def source = graphFactory()
                .addVertices('v2', 'v3', 'v4')

        when:
        directedGraph.addGraph(source)

        then:
        directedGraph.containsVertices('v1', 'v2', 'v3', 'v4') == [true, true, true, true]

    }

    def "all edges from source graph should be added to target graph"() {
        given:
        directedGraph.addVertices('v1', 'v2', 'v3')
                .addEdge('v1', 'v2').addEdge('v1', 'v3')
        def source = graphFactory()
                .addVertices('v2', 'v3', 'v4')
                .addEdge('v2', 'v3').addEdge('v2', 'v4')

        when:
        directedGraph.addGraph(source)

        then:
        directedGraph.containsVertices('v1', 'v2', 'v3', 'v4').every(true.&equals)
        directedGraph.containsOutgoingVertices('v1', 'v2', 'v3').every(true.&equals)
        directedGraph.containsOutgoingVertices('v2', 'v3', 'v4').every(true.&equals)
        directedGraph.containsOutgoingVertices('v3', 'v2') == [false]
        directedGraph.containsOutgoingVertices('v4', 'v2') == [false]
    }

    def "if two graph have some equality vertices or edges that target graph should resolve conflicts"() {
        given:
        directedGraph.addVertices('v1', 'v2', 'v3')
                .addEdge('v1', 'v2').addEdge('v1', 'v3')
        def source = graphFactory()
                .addVertices('v1', 'v2', 'v3', 'v4')
                .addEdge('v1', 'v2').addEdge('v2', 'v3').addEdge('v2', 'v4')

        when:
        directedGraph.addGraph(source)

        then:
        directedGraph.containsVertices('v1', 'v2', 'v3', 'v4').every(true.&equals)
        directedGraph.containsOutgoingVertices('v1', 'v2', 'v3').every(true.&equals)
        directedGraph.containsOutgoingVertices('v2', 'v3', 'v4').every(true.&equals)
        directedGraph.containsOutgoingVertices('v3', 'v2') == [false]
        directedGraph.containsOutgoingVertices('v4', 'v2') == [false]

    }

    def "if source graph equals null that method addGraph should return target graph"() {
        expect:
        directedGraph.addGraph(null) == directedGraph
    }

    def "graph is capability test some graph on it graph is part this graph"() {
        given:
        directedGraph.addVertices('v1', 'v2', 'v3')
                .addEdge('v1', 'v2').addEdge('v1', 'v3')

        expect:
        directedGraph.containsGraph(graphFactory()
                .addVertex('v1'))
        !directedGraph.containsGraph(graphFactory()
                .addVertex('v4'))
        directedGraph.containsGraph(graphFactory()
                .addVertices('v1', 'v2').addEdge('v1', 'v2'))
        !directedGraph.containsGraph(graphFactory()
                .addVertices('v2', 'v3').addEdge('v2', 'v3'))

    }
}
