package ru.osslabs.plarform.modules.graph.impl;

import ru.osslabs.plarform.modules.graph.Edge;

import java.util.Collections;
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
    protected static class DirectedEdgeContainer<VV, EE extends Edge<VV>> {
        private final VV vertex;
        private final Map<EE, Object> incoming = new ConcurrentHashMap<>();
        private final Map<EE, Object> outgoing = new ConcurrentHashMap<>();

        // Dummy value to associate with an Object in the backing Map
        private static final Object PRESENT = new Object();

        DirectedEdgeContainer(VV vertex) {
            this.vertex = vertex;
        }

        public Set<EE> getUnmodifiableIncomingEdges() {
            return Collections.unmodifiableSet(incoming.keySet());
        }

        public Set<EE> getUnmodifiableOutgoingEdges() {
            return Collections.unmodifiableSet(outgoing.keySet());
        }

        public void addIncomingEdge(EE edge) {
            incoming.put(edge, PRESENT);
        }

        public void addOutgoingEdge(EE edge) {
            outgoing.put(edge, PRESENT);
        }

        public VV getVertex() {
            return vertex;
        }
    }
}
