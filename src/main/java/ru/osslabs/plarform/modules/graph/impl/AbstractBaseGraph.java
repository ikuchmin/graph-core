package ru.osslabs.plarform.modules.graph.impl;

import ru.osslabs.plarform.modules.graph.Edge;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ikuchmin on 03.03.16.
 */
public abstract class AbstractBaseGraph<V, E extends Edge<V>> extends AbstractGraph<V, E> {

    /**
     * A container for vertex edges.
     */
    public static class DirectedEdgeContainer<VV, EE extends Edge<VV>> {
        private final VV vertex;
        private final Set<EE> incoming = new HashSet<>();
        private final Set<EE> outgoing = new HashSet<>();

        public DirectedEdgeContainer(VV vertex) {
            this.vertex = vertex;
        }

        public Set<EE> getUnmodifiableIncomingEdges() {
            return Collections.unmodifiableSet(incoming);
        }

        public Set<EE> getUnmodifiableOutgoingEdges() {
            return Collections.unmodifiableSet(outgoing);
        }

        public void addIncomingEdge(EE edge) {
            incoming.add(edge);
        }

        public void addOutgoingEdge(EE edge) {
            outgoing.add(edge);
        }

        public VV getVertex() {
            return vertex;
        }
    }
}
