package ru.osslabs.plarform.modules.graph.impl;

import ru.osslabs.plarform.modules.graph.Edge;

import java.util.*;
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
        private final Map<VV, EE> incoming = new HashMap<>();
        private final Map<VV, EE> outgoing = new HashMap<>();

        public DirectedEdgeContainer(VV vertex) {
            this.vertex = vertex;
        }

        public Collection<VV> getUnmodifiableIncomingVertices() {
            return Collections.unmodifiableSet(incoming.keySet());
        }

        public Collection<VV> getUnmodifiableOutgoingVertices() {
            return Collections.unmodifiableSet(outgoing.keySet());
        }

        public Collection<EE> getUnmodifiableIncomingEdges() {
            return Collections.unmodifiableCollection(incoming.values());
        }

        public Collection<EE> getUnmodifiableOutgoingEdges() {
            return Collections.unmodifiableCollection(outgoing.values());
        }

        public void addIncomingEdge(EE edge) {
            incoming.put(edge.getSource(), edge);
        }

        public void addOutgoingEdge(EE edge) {
            outgoing.put(edge.getTarget(), edge);
        }

        public VV getVertex() {
            return vertex;
        }
    }
}
