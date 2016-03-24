package ru.osslabs.plarform.modules.graph.impl

import ru.osslabs.plarform.modules.graph.Graph
import spock.lang.Ignore
import spock.lang.Specification

/**
 * Created by ikuchmin on 09.03.16.
 */
class DirectedGraphImplTest extends Specification {

    def directedGraph = new DirectedGraphImpl<String, ExEdge<String>>(ExEdge.metaClass.&invokeConstructor)

    def "graph can not be created without edge factory"() {
        when:
        new DirectedGraphImpl<String, ExEdge<String>>()

        then:
        thrown IllegalArgumentException
    }

    def "graph should have capability add vertices"() {
        when:
        directedGraph.addVertices('v1', 'v2')

        then:
        directedGraph.containsVertex('v1')
        directedGraph.containsVertex('v2')
    }

    def "graph should have capability add edges on vertices"() {
        given:
        directedGraph.addVertex('v1').addVertex('v2').addVertex('v3')

        when:
        directedGraph.addEdge('v1', 'v2').addEdge('v1', 'v3')

        then:
        directedGraph.containsOutgoingVertices('v1', 'v2', 'v3')
        directedGraph.containsIncomingVertices('v2', 'v1')
        directedGraph.containsIncomingVertices('v3', 'v1')

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

    def "graph should have capability add edges with on edges"() {
        given:
        directedGraph.addVertex('v1').addVertex('v2').addVertex('v3')
        def e1 = ['v1', 'v2'] as ExEdge
        def e2 = ['v1', 'v3'] as ExEdge
        def e3 = ['v1', 'v4'] as ExEdge
        def e4 = ['v4', 'v1'] as ExEdge

        when:
        directedGraph.addEdge(e1).addEdge(e2)

        then:
        directedGraph.containsOutgoingVertices('v1', 'v2', 'v3')
        directedGraph.containsIncomingVertices('v2', 'v1')
        directedGraph.containsIncomingVertices('v3', 'v1')

        when:
        directedGraph.addEdge(e3)

        then:
        thrown IllegalArgumentException

        when:
        directedGraph.addEdge(e4)

        then:
        thrown IllegalArgumentException

        when:
        directedGraph.addEdge(null)

        then:
        thrown IllegalArgumentException

    }

    def "directed graph have directed edges"() {
        given:
        directedGraph.addVertices('v1', 'v2')
        when:
        directedGraph.addEdge(new ExEdge<String>('v1', 'v2'))

        then:
        directedGraph.outgoingEdgesOf('v1')
                .contains(new ExEdge<String>(source: 'v1', target: 'v2'))

        !directedGraph.incomingEdgesOf('v1')
                .contains(new ExEdge<String>(source: 'v1', target: 'v2'))
    }

    def "if vertex was added to graph that would it contains in graph"() {
        given:
        def graph = new DirectedGraphImpl<String, ExEdge<String>>({ source, target ->
            new ExEdge<String>(source: source, target: target)
        })

        when:
        directedGraph.addVertex('v1')

        then:
        directedGraph.containsVertex('v1')

    }

    @Ignore("TODO")
    def "if edge was added to graph that would it contains in graph"() {
        given:
        def graph = new DirectedGraphImpl<String, ExEdge<String>>({ source, target ->
            new ExEdge<String>(source: source, target: target)
        }).addVertex('v1').addVertex('v2')

        when:
        directedGraph.addEdge(new ExEdge<String>('v1', 'v2'))

        then:
        directedGraph.containsEdge(new ExEdge<String>('v1', 'v2'))
    }

    def "two some graph can be merged"() {
        given:
        directedGraph.addVertices('v1', 'v2', 'v3').addEdge('v1', 'v2')

        def source = new DirectedGraphImpl<>(ExEdge.metaClass.&invokeConstructor)
                .addVertices('v3', 'v4').addEdge('v3', 'v4')

        when:
        directedGraph.addGraph(source)

        then:
        directedGraph.containsVertex('v1')
        directedGraph.containsVertex('v2')
        directedGraph.containsVertex('v3')
        directedGraph.outgoingEdgesOf('v1')
                .contains(new ExEdge<String>(source: 'v1', target: 'v2'))
        directedGraph.incomingEdgesOf('v2')
                .contains(new ExEdge<String>(source: 'v1', target: 'v2'))
        directedGraph.outgoingEdgesOf('v3')
                .contains(new ExEdge<String>(source: 'v3', target: 'v4'))
        directedGraph.incomingEdgesOf('v4')
                .contains(new ExEdge<String>(source: 'v3', target: 'v4'))
    }

    def "if two graph have some general vertices that they would can not be merged"() {
        given:
        def graph = new DirectedGraphImpl<String, ExEdge<String>>({ source, target ->
            new ExEdge<String>(source: source, target: target)
        }).addVertex('v1').addVertex('v2').addEdge('v1', 'v2')
        def source = new DirectedGraphImpl<String, ExEdge<String>>({ source, target ->
            new ExEdge<String>(source: source, target: target)
        }).addVertex('v1').addVertex('v4')

        when:
        directedGraph.addGraph(source)

        then:
        thrown IllegalArgumentException
    }

    def "if two graph aren't implementing DirectedGraphImpl that they whould can not be merged"() {
        given:
        def graph = new DirectedGraphImpl<String, ExEdge<String>>({ source, target ->
            new ExEdge<String>(source: source, target: target)
        }).addVertex('v1').addVertex('v2').addEdge('v1', 'v2')
        def source = Mock(Graph)

        when:
        directedGraph.addGraph(source)

        then:
        thrown IllegalArgumentException
    }

    def "vertexSet was returned from graph should be unmodifiable"() {
        given:
        def graph = new DirectedGraphImpl<String, ExEdge<String>>({ source, target ->
            new ExEdge<String>(source: source, target: target)
        }).addVertex('v1').addVertex('v2').addEdge('v1', 'v2')

        when:
        directedGraph.vertexSet().add('v3')

        then:
        thrown UnsupportedOperationException

    }

    def "graph has capability for test outgoing vertices for some vertex"() {
        given:
        directedGraph.addVertex('v1').addVertex('v2').addVertex('v3')
                .addEdge('v1', 'v2').addEdge('v1', 'v3')

        expect:
        directedGraph.containsOutgoingVertices('v1', 'v2', 'v3')
        directedGraph.containsIncomingVertices('v2', 'v1')
        directedGraph.containsIncomingVertices('v3', 'v1')
    }
}
