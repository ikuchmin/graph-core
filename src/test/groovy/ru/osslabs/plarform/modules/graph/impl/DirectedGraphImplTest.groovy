package ru.osslabs.plarform.modules.graph.impl

import ru.osslabs.plarform.modules.graph.Graph
import spock.lang.Ignore
import spock.lang.Specification

/**
 * Created by ikuchmin on 09.03.16.
 */
class DirectedGraphImplTest extends Specification {

    def "graph should have capability add vertices"() {
        given:
        def graph = new DirectedGraphImpl<String, ExEdge<String>>({source, target ->
            new Expando(source: source, target: target)
        }).addVertex('v1')

        when:
        graph.addVertex('v2')

        then:
        graph.vertices.containsKey('v1')
        graph.vertices.containsKey('v2')
    }

    def "graph should have capability add edges"() {
        given:
        def graph = new DirectedGraphImpl<String, ExEdge<String>>({source, target ->
            new ExEdge<String>(source: source, target: target)
        }).addVertex('v1').addVertex('v2')

        when:
        graph.addEdge('v1', 'v2')
        println graph.vertices.v1
                .unmodifiableOutgoingEdges

        then:
        graph.vertices.v1
                .unmodifiableOutgoingEdges
                .contains(new ExEdge<String>(source: 'v1', target: 'v2'))

        when:
        graph.addEdge(new ExEdge<String>(source: 'v1', target: 'v2'))

        then:
        graph.vertices.v1
                .unmodifiableOutgoingEdges
                .contains(new ExEdge<String>(source: 'v1', target: 'v2'))
    }

    def "directed graph have directed edges"() {
        given:
        def graph = new DirectedGraphImpl<String, ExEdge<String>>({source, target ->
            new ExEdge<String>(source: source, target: target)
        }).addVertex('v1').addVertex('v2')

        when:
        graph.addEdge(new ExEdge<String>('v1', 'v2'))

        then:
        graph.vertices.v1
                .unmodifiableOutgoingEdges
                .contains(new ExEdge<String>(source: 'v1', target: 'v2'))

        !graph.vertices.v1
                .unmodifiableIncomingEdges
                .contains(new ExEdge<String>(source: 'v1', target: 'v2'))
    }

    def "if vertex was added to graph that would it contains in graph"() {
        given:
        def graph = new DirectedGraphImpl<String, ExEdge<String>>({source, target ->
            new ExEdge<String>(source: source, target: target)
        })

        when:
        graph.addVertex('v1')

        then:
        graph.containsVertex('v1')

    }

    @Ignore("TODO")
    def "if edge was added to graph that would it contains in graph"() {
        given:
        def graph = new DirectedGraphImpl<String, ExEdge<String>>({source, target ->
            new ExEdge<String>(source: source, target: target)
        }).addVertex('v1').addVertex('v2')

        when:
        graph.addEdge(new ExEdge<String>('v1', 'v2'))

        then:
        graph.containsEdge(new ExEdge<String>('v1', 'v2'))
    }

    def "two some graph can be merged"() {
        given:
        def graph = new DirectedGraphImpl<String, ExEdge<String>>({source, target ->
            new ExEdge<String>(source: source, target: target)
        }).addVertex('v1').addVertex('v2').addEdge('v1', 'v2')
        def source = new DirectedGraphImpl<String, ExEdge<String>>({source, target ->
            new ExEdge<String>(source: source, target: target)
        }).addVertex('v3').addVertex('v4').addEdge('v3', 'v4')

        when:
        graph.addGraph(source)

        then:
        graph.vertices.containsKey('v1')
        graph.vertices.containsKey('v2')
        graph.vertices.containsKey('v3')
        graph.vertices.v1
                .unmodifiableOutgoingEdges
                .contains(new ExEdge<String>(source: 'v1', target: 'v2'))
        graph.vertices.v2
                .unmodifiableIncomingEdges
                .contains(new ExEdge<String>(source: 'v1', target: 'v2'))
        graph.vertices.v3
                .unmodifiableOutgoingEdges
                .contains(new ExEdge<String>(source: 'v3', target: 'v4'))
        graph.vertices.v4
                .unmodifiableIncomingEdges
                .contains(new ExEdge<String>(source: 'v3', target: 'v4'))
    }

    def "if two graph have some general vertices that they would can not be merged"() {
        given:
        def graph = new DirectedGraphImpl<String, ExEdge<String>>({source, target ->
            new ExEdge<String>(source: source, target: target)
        }).addVertex('v1').addVertex('v2').addEdge('v1', 'v2')
        def source = new DirectedGraphImpl<String, ExEdge<String>>({source, target ->
            new ExEdge<String>(source: source, target: target)
        }).addVertex('v1').addVertex('v4')

        when:
        graph.addGraph(source)

        then:
        thrown IllegalArgumentException
    }

    def "if two graph aren't implementing DirectedGraphImpl that they whould can not be merged"() {
        given:
        def graph = new DirectedGraphImpl<String, ExEdge<String>>({source, target ->
            new ExEdge<String>(source: source, target: target)
        }).addVertex('v1').addVertex('v2').addEdge('v1', 'v2')
        def source = Mock(Graph)

        when:
        graph.addGraph(source)

        then:
        thrown IllegalArgumentException
    }

    def "vertexSet was returned from graph should be unmodifiable"() {
        given:
        def graph = new DirectedGraphImpl<String, ExEdge<String>>({source, target ->
            new ExEdge<String>(source: source, target: target)
        }).addVertex('v1').addVertex('v2').addEdge('v1', 'v2')

        when:
        graph.vertexSet().add('v3')

        then:
        thrown UnsupportedOperationException

    }

    def "graph has capability for test outgoing vertices for some vertex"() {
        given:
        def graph = new DirectedGraphImpl<String, ExEdge<String>>(ExEdge.metaClass.&invokeConstructor)
        graph.addVertex('v1').addVertex('v2').addVertex('v3')
                .addEdge('v1', 'v2').addEdge('v1', 'v3')

        expect:
        graph.containsOutgoingVertices('v1', 'v2', 'v3')
        graph.containsIncomingVertices('v2', 'v1')
        graph.containsIncomingVertices('v3', 'v1')
    }
}
