package ru.osslabs.graph.impl

import spock.lang.Specification

/**
 * Created by ikuchmin on 16.03.16.
 */
class BreadthFirstIteratorTest extends Specification {
    def "iterator should not works with graphs which haven\'t vertices"() {
        when:
        BreadthFirstIterator<String, ExEdge<String>> iterator = new BreadthFirstIterator<>(new DirectedGraphImpl<>({ s, t -> new ExEdge<>(s, t) }))

        then:
        thrown IllegalArgumentException
    }

    def "iterator should works with directed and an undirected graphs"() {
        given:
        def graph = new DirectedGraphImpl<>({ s, t -> new ExEdge<>(s, t) })

        when:
        def wrapperGraph = new BreadthFirstIterator<>(graph.addVertex('v1'))

        then:
        wrapperGraph != null
    }

    def "collectVertices function should saves edges but modify vertex"() {
        given:
        BreadthFirstIterator<String, ExEdge<String>> dfsGraph = new BreadthFirstIterator<>(
                new DirectedGraphImpl<>({ s, t -> new ExEdge<>(s, t) })
                        .addVertex('v1').addVertex('v2').addVertex('v3')
                        .addEdge('v1', 'v2').addEdge('v1', 'v3'))

        when:
        def newGraph = dfsGraph.collectVertices(
                'v1',
                new DirectedGraphImpl<>({ s, t -> new ExEdge<>(s, t) }),
                { parent, vertex -> vertex + '_suffix' })

        then:
        newGraph.vertices.containsAll(['v1_suffix', 'v2_suffix', 'v3_suffix'])
        newGraph.edgesOf('v1_suffix').containsAll([
                new ExEdge<>('v1_suffix', 'v2_suffix'),
                new ExEdge<>('v1_suffix', 'v3_suffix')])
    }

    def "collectVertices should created spanning tree and resolves cycles"() {
        given:
        BreadthFirstIterator<String, ExEdge<String>> dfsGraph = new BreadthFirstIterator<>(
                new DirectedGraphImpl<>({ s, t -> new ExEdge<>(s, t) })
                        .addVertex('v1').addVertex('v2').addVertex('v3')
                        .addEdge('v1', 'v2').addEdge('v1', 'v3').addEdge('v2', 'v3'))

        when:
        def newGraph = dfsGraph.collectVertices('v1',
                new DirectedGraphImpl<>({ s, t -> new ExEdge<>(s, t) }),
                {parent, vertex -> vertex} )

        then:
        newGraph.vertices.containsAll(['v1', 'v2', 'v3'])
        newGraph.edgesOf('v1').containsAll([
                new ExEdge<>('v1', 'v2'),
                new ExEdge<>('v1', 'v3')])
    }
}
