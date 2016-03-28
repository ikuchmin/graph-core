package ru.osslabs.graph.impl;

import ru.osslabs.graph.Edge;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A container for vertex edges.
 */
public class DirectedEdgeContainer<VV, EE extends Edge<VV>> {
    private final VV vertex;
    private final Map<VV, EE> incoming = new HashMap<>();
    private final Map<VV, EE> outgoing = new HashMap<>();

    public DirectedEdgeContainer(VV vertex) {
        this.vertex = vertex;
    }

    public Collection<VV> getIncomingVertices() {
        return incoming.keySet();
    }

    public Collection<VV> getOutgoingVertices() {
        return outgoing.keySet();
    }

    public Collection<EE> getIncomingEdges() {
        return incoming.values();
    }

    public Collection<EE> getOutgoingEdges() {
        return outgoing.values();
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
