package ru.osslabs.plarform.modules.graph.impl

import ru.osslabs.plarform.modules.graph.DirectedGraph

/**
 * Created by ikuchmin on 09.03.16.
 */
class DirectedGraphImplTest extends AbstractDirectedGraphTest {

    def directedGraphImpl = new DirectedGraphImpl<String, ExEdge<String>>(ExEdge.metaClass.&invokeConstructor)

    @Override
    DirectedGraph getDirectedGraph() {
        return directedGraphImpl
    }

    def "graph can not be created without edge factory"() {
        when:
        new DirectedGraphImpl<String, ExEdge<String>>(null)

        then:
        thrown IllegalArgumentException
    }
}
