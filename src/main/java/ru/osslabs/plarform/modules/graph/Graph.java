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
/* ----------
 * Graph.java
 * ----------
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * Original Author:  Barak Naveh
 * Contributor(s):   John V. Sichi
 *                   Christian Hammer
 *
 * $Id$
 *
 * Changes
 * -------
 * 24-Jul-2003 : Initial revision (BN);
 * 06-Nov-2003 : Change edge sharing semantics (JVS);
 * 11-Mar-2004 : Made generic (CH);
 * 07-May-2006 : Changed from List<Edge> to Set<Edge> (JVS);
 * 28-May-2006 : Moved connectivity info from edge to graph (JVS);
 *
 */
package ru.osslabs.plarform.modules.graph;

import java.util.Collection;
import java.util.Set;
import java.util.function.BiFunction;


/**
 * The root interface in the graph hierarchy. A mathematical graph-theory graph
 * object <tt>G(V extends Vertex, E extend Edge)</tt> contains a set <tt>V</tt> of vertices and a set <tt>
 * E</tt> of edges. Each edge e=(v1,v2) in E connects vertex v1 to vertex v2.
 * for more information about graphs and their related definitions see <a
 * href="http://mathworld.wolfram.com/Graph.html">
 * http://mathworld.wolfram.com/Graph.html</a>.
 * <p/>
 * <p>This library generally follows the terminology found at: <a
 * href="http://mathworld.wolfram.com/topics/GraphTheory.html">
 * http://mathworld.wolfram.com/topics/GraphTheory.html</a>. Implementation of
 * this interface can provide simple-graphs, multigraphs, pseudographs etc. The
 * package <code>org.jgrapht.graph</code> provides a gallery of abstract and
 * concrete graph implementations.</p>
 * <p/>
 * <p>This library works best when vertices represent arbitrary objects and
 * edges represent the relationships between them. Vertex and edge instances may
 * be shared by more than one graph.</p>
 * <p/>
 * <p>Through generics, a graph can be typed to specific classes for vertices
 * <code>V</code> and edges <code>E&lt;T&gt;</code>. Such a graph can contain
 * vertices of type <code>V</code> and all sub-types and Edges of type <code>
 * E</code> and all sub-types.</p>
 * <p/>
 * <p>For guidelines on vertex and edge classes, see <a
 * href="https://github.com/jgrapht/jgrapht/wiki/EqualsAndHashCode">this wiki
 * page</a>.
 *
 * @author Barak Naveh
 * @since Jul 14, 2003
 */
public interface Graph<V, E extends Edge<V>> {


    /**
     * Creates a new edge in this graph, going from the source vertex to the
     * target vertex, and returns the created edge. Some graphs do not allow
     * edge-multiplicity. In such cases, if the graph already contains an edge
     * from the specified source to the specified target, than this method does
     * not change the graph and returns <code>null</code>.
     * <p/>
     * <p>The source and target vertices must already be contained in this
     * graph. If they are not found in graph IllegalArgumentException is
     * thrown.</p>
     * <p/>
     *
     * @param edge            edge to be added to this graph.
     * @return <code>this</code>
     * @throws IllegalArgumentException if source or target vertices are not
     *                                  found in the graph.
     * @throws NullPointerException     if any of the specified vertices is <code>
     *                                  null</code>.
     */
    Graph<V, E> addEdge(E edge);

    /**
     * Return instance object of edge which is in instance graph. This method
     * can use in scenarios where E1 == E2, but E contain fields don't uses in
     * hashcode and equals
     *
     * @param edge instance edge for search
     * @return instance edge contains in graph
     */
    E getEdge(E edge);

    /**
     * Returns the edge factory using which this graph creates new edges. The
     * edge factory is defined when the graph is constructed and must not be
     * modified.
     *
     * @return the edge factory using which this graph creates new edges.
     */
    BiFunction<V, V, E> getEdgeFactory();

    /**
     * Creates a new edge in this graph, going from the source vertex to the
     * target vertex, and returns the created edge. Some graphs do not allow
     * edge-multiplicity. In such cases, if the graph already contains an edge
     * from the specified source to the specified target, than this method does
     * not change the graph and returns <code>null</code>.
     *
     * <p>The source and target vertices must already be contained in this
     * graph. If they are not found in graph IllegalArgumentException is
     * thrown.</p>
     *
     * <p>This method creates the new edge <code>e</code> using this graph's
     * <code>BiFunction</code>. For the new edge to be added <code>e</code>
     * must <i>not</i> be equal to any other edge the graph (even if the graph
     * allows edge-multiplicity). More formally, the graph must not contain any
     * edge <code>e2</code> such that <code>e2.equals(e)</code>. If such <code>
     * e2</code> is found then the newly created edge <code>e</code> is
     * abandoned, the method leaves this graph unchanged returns <code>
     * null</code>.</p>
     *
     * @param sourceVertex source vertex of the edge.
     * @param targetVertex target vertex of the edge.
     *
     * @return The newly created edge if added to the graph, otherwise <code>
     * null</code>.
     *
     * @throws IllegalArgumentException if source or target vertices are not
     * found in the graph.
     * @throws NullPointerException if any of the specified vertices is <code>
     * null</code>.
     *
     * @see #getEdgeFactory()
     */
    Graph<V, E> addEdge(V sourceVertex, V targetVertex);

    /**
     * Adds the specified vertex to this graph if not already present. More
     * formally, adds the specified vertex, <code>v</code>, to this graph if
     * this graph contains no vertex <code>u</code> such that <code>
     * u.equals(v)</code>. If this graph already contains such vertex, the call
     * leaves this graph unchanged and returns <tt>false</tt>. In combination
     * with the restriction on constructors, this ensures that graphs never
     * contain duplicate vertices.
     *
     * @param v vertex to be added to this graph.
     * @return <code>this</code>
     * @throws NullPointerException if the specified vertex is <code>
     *                              null</code>.
     */
    Graph<V, E> addVertex(V v);

    /**
     * Return instance object of vertex which is in instance graph. This method
     * can use in scenarios where V1 == V2, but V contain fields don't uses in
     * hashcode and equals
     *
     * @param vertex instance vertex for search
     * @return instance edge contains in graph
     */
    V getVertex(V vertex);

    /**
     * Adds all the vertices and all the edges of the {@code sourceGraph} to the
     * graph being built.
     *
     * @return this object
     *
     */
    Graph<V, E> addGraph(Graph<? extends V, ? extends E> sourceGraph);

    /**
     * Returns <tt>true</tt> if this graph contains the specified edge. More
     * formally, returns <tt>true</tt> if and only if this graph contains an
     * edge <code>e2</code> such that <code>e.equals(e2)</code>. If the
     * specified edge is <code>null</code> returns <code>false</code>.
     *
     * @param e edge whose presence in this graph is to be tested.
     * @return <tt>true</tt> if this graph contains the specified edge.
     */
    boolean containsEdge(E e);

    /**
     * Returns <tt>true</tt> if this graph contains the specified vertex. More
     * formally, returns <tt>true</tt> if and only if this graph contains a
     * vertex <code>u</code> such that <code>u.equals(v)</code>. If the
     * specified vertex is <code>null</code> returns <code>false</code>.
     *
     * @param v vertex whose presence in this graph is to be tested.
     * @return <tt>true</tt> if this graph contains the specified vertex.
     */
    boolean containsVertex(V v);

    /**
     * Returns a set of all edges touching the specified vertex. If no edges are
     * touching the specified vertex returns an empty set.
     *
     * @param vertex the vertex for which a set of touching edges is to be
     *               returned.
     * @return a set of all edges touching the specified vertex.
     * @throws IllegalArgumentException if vertex is not found in the graph.
     * @throws NullPointerException     if vertex is <code>null</code>.
     */
    Set<E> edgesOf(V vertex);

    /**
     * Returns a set of the vertices contained in this graph. The set is backed
     * by the graph, so changes to the graph are reflected in the set. If the
     * graph is modified while an iteration over the set is in progress, the
     * results of the iteration are undefined.
     *
     * <p>The graph implementation may maintain a particular set ordering (e.g.
     * via {@link java.util.LinkedHashSet}) for deterministic iteration, but
     * this is not required. It is the responsibility of callers who rely on
     * this behavior to only use graph implementations which support it.</p>
     *
     * @return a set view of the vertices contained in this graph.
     */
    Set<V> vertexSet();


}
