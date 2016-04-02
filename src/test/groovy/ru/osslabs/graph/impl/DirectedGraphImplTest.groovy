package ru.osslabs.graph.impl

import ru.osslabs.graph.DirectedGraph
import ru.osslabs.graph.Edge
import ru.osslabs.graph.Graph
import ru.osslabs.graph.collection.SimpleGraphMap

import java.util.function.BiFunction

/**
 * Created by ikuchmin on 09.03.16.
 */
class DirectedGraphImplTest extends AbstractDirectedGraphTest {

    def directedGraphImpl = new DirectedGraphImpl<String, ExEdge<String>>(ExEdge.metaClass.&invokeConstructor)

    @Override
    DirectedGraph getDirectedGraph() {
        return directedGraphImpl
    }

    @Override
    Edge edgeFactory(String v1, String v2) {
        return new ExEdge(v1, v2)
    }

    @Override
    List verticesFactory(List<String> vertices) {
        return vertices
    }

    @Override
    Graph graphFactory() {
        return new StubDirectClass(ExEdge.metaClass.&invokeConstructor)
    }

    def "graph can not be created without edge factory"() {
        when:
        new DirectedGraphImpl<String, ExEdge<String>>(null)

        then:
        thrown IllegalArgumentException
    }

    class StubDirectClass<V, E extends Edge<V>> extends AbstractDirectedGraph<V, E, StubDirectClass<V, E>, SimpleGraphMap<V, DirectedEdgeContainer<V, E>>> {

        final SimpleGraphMap<V, E> verticesMap = new SimpleGraphMap<>()
        BiFunction<V, V, E> edgeFactory

        StubDirectClass(BiFunction<V, V, E> edgeFactory) {
            this.edgeFactory = edgeFactory
        }

        @Override
        BiFunction<V, V, E> getEdgeFactory() {
            return this.edgeFactory
        }

        @Override
        protected SimpleGraphMap<V, E> getGraphMap() {
            return verticesMap
        }
    }
}
