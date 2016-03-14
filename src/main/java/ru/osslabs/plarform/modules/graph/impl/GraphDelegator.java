/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
/* -------------------
 * GraphDelegator.java
 * -------------------
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * Original Author:  Barak Naveh
 * Contributor(s):   Christian Hammer
 *
 * $Id$
 *
 * Changes
 * -------
 * 24-Jul-2003 : Initial revision (BN);
 * 11-Mar-2004 : Made generic (CH);
 * 07-May-2006 : Changed from List<Edge> to Set<Edge> (JVS);
 * 28-May-2006 : Moved connectivity info from edge to graph (JVS);
 *
 */
package ru.osslabs.plarform.modules.graph.impl;

import ru.osslabs.plarform.modules.graph.Edge;
import ru.osslabs.plarform.modules.graph.Graph;

import java.io.Serializable;
import java.util.Set;
import java.util.function.BiFunction;


/**
 * A graph backed by the the graph specified at the constructor, which delegates
 * all its methods to the backing graph. Operations on this graph "pass through"
 * to the to the backing graph. Any modification made to this graph or the
 * backing graph is reflected by the other.
 * <p>
 * <p>This graph does <i>not</i> pass the hashCode and equals operations through
 * to the backing graph, but relies on <tt>Object</tt>'s <tt>equals</tt> and
 * <tt>hashCode</tt> methods.</p>
 * <p>
 * <p>This class is mostly used as a base for extending subclasses.</p>
 *
 */
public class GraphDelegator<V, E extends Edge<V>>
        extends AbstractGraph<V, E>
        implements Graph<V, E>, Serializable {

    private static final long serialVersionUID = 3257005445226181425L;


    /**
     * The graph to which operations are delegated.
     */
    private Graph<V, E> delegate;


    /**
     * Constructor for GraphDelegator.
     *
     * @param g the backing graph (the delegate).
     * @throws IllegalArgumentException iff <code>g==null</code>
     */
    public GraphDelegator(Graph<V, E> g) {
        super();

        if (g == null) {
            throw new IllegalArgumentException("g must not be null.");
        }

        this.delegate = g;
    }

    @Override
    public Graph<V, E> addEdge(E edge) {
        return delegate.addEdge(edge);
    }

    @Override
    public BiFunction<V, V, E> getEdgeFactory() {
        return delegate.getEdgeFactory();
    }

    @Override
    public Graph<V, E> addEdge(V sourceVertex, V targetVertex) {
        return delegate.addEdge(sourceVertex, targetVertex);
    }

    @Override
    public Graph<V, E> addVertex(V v) {
        return delegate.addVertex(v);
    }

    @Override
    public Graph<V, E> addGraph(Graph<? extends V, ? extends E> sourceGraph) {
        return delegate.addGraph(sourceGraph);
    }

    @Override
    public boolean containsEdge(E e) {
        return delegate.containsEdge(e);
    }

    @Override
    public boolean containsVertex(V v) {
        return delegate.containsVertex(v);
    }

    @Override
    public Set<E> edgesOf(V vertex) {
        return delegate.edgesOf(vertex);
    }

    @Override
    public Set<V> vertexSet() {
        return delegate.vertexSet();
    }
}

// End GraphDelegator.java
