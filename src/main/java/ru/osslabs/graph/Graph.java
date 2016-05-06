package ru.osslabs.graph;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by ikuchmin on 28.03.16.
 */
public interface Graph<V, E extends Edge<V>, G extends Graph<V, E, G>>  {
    /**
     * Add edge in this graph, going from the source vertex to the
     * target vertex, and returns graph instance. Some graphs do not allow
     * edge-multiplicity. In such cases, if the graph already contains an edge
     * from the specified source to the specified target, than this method does
     * not change the graph and returns <code>null</code>.
     * <p/>
     * <p>The source and target getVertices must already be contained in this
     * graph. If they are not found in graph IllegalArgumentException is
     * thrown.</p>
     * <p/>
     *
     * @param edge edge to be added to this graph.
     * @return <code>this</code>
     * @throws IllegalArgumentException if source or target getVertices are not
     *                                  found in the graph.
     * @throws NullPointerException     if any of the specified getVertices is <code>
     *                                  null</code>.
     */
    G addEdge(E edge);

    /**
     * Add edges in this graph, going from the source vertex to the
     * target vertex, and returns graph instance. Some graphs do not allow
     * edge-multiplicity. In such cases, if the graph already contains an edge
     * from the specified source to the specified target, than this method does
     * not change the graph and returns <code>null</code>.
     * <p/>
     * <p>The source and target getVertices must already be contained in this
     * graph. If they are not found in graph IllegalArgumentException is
     * thrown.</p>
     * <p/>
     *
     * @param edges collection edge to be added to this graph.
     * @return <code>this</code>
     * @throws IllegalArgumentException if source or target getVertices are not
     *                                  found in the graph.
     * @throws NullPointerException     if any of the specified getVertices is <code>
     *                                  null</code>.
     */
    G addEdges(Collection<? extends E> edges);

    //    Graph<V, E> addEdges(E... edges);

    /**
     * Creates a new edge in this graph, going from the source vertex to the
     * target vertex, and returns the created edge. Some graphs do not allow
     * edge-multiplicity. In such cases, if the graph already contains an edge
     * from the specified source to the specified target, than this method does
     * not change the graph and returns <code>null</code>.
     * <p>
     * <p>The source and target getVertices must already be contained in this
     * graph. If they are not found in graph IllegalArgumentException is
     * thrown.</p>
     * <p>
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
     * @return The newly created edge if added to the graph, otherwise <code>
     * null</code>.
     * @throws IllegalArgumentException if source or target getVertices are not
     *                                  found in the graph.
     * @throws NullPointerException     if any of the specified getVertices is <code>
     *                                  null</code>.
     * @see #getEdgeFactory()
     */
    G addEdge(V sourceVertex, V targetVertex);

    G addEdge(List<?> sourceVertex, List<?> targetVertex); // Groovy like syntax

    /**
     * Adds the specified vertex to this graph if not already present. More
     * formally, adds the specified vertex, <code>v</code>, to this graph if
     * this graph contains no vertex <code>u</code> such that <code>
     * u.equals(v)</code>. If this graph already contains such vertex, the call
     * leaves this graph unchanged and returns <tt>false</tt>. In combination
     * with the restriction on constructors, this ensures that graphs never
     * contain duplicate getVertices.
     *
     * @param v vertex to be added to this graph.
     * @return <code>this</code>
     * @throws NullPointerException if the specified vertex is <code>
     *                              null</code>.
     */
    G addVertex(V v);

    G addVertex(List<?> args); // Groovy like syntax

    G addVertices(V... vertices);

    G addVertices(Collection<? extends V> vertices);

    /**
     * Adds all the getVertices and all the edges of the {@code sourceGraph} to the
     * graph being built.
     *
     * @return this object
     */
    G addGraph(Graph<V, E, G> sourceGraph);

    /**
     * Returns the edge factory using which this graph creates new edges. The
     * edge factory is defined when the graph is constructed and must not be
     * modified.
     *
     * @return the edge factory using which this graph creates new edges.
     */
    BiFunction<V, V, E> getEdgeFactory();

    /**
     * Returns <tt>true</tt> if this graph contains the specified vertex. More
     * formally, returns <tt>true</tt> if and only if this graph contains a
     * vertex <code>u</code> such that <code>u.equals(v)</code>. If the
     * specified vertex is <code>null</code> returns <code>false</code>.
     *
     * @param vertex whose presence in this graph is to be tested.
     * @return <tt>true</tt> if this graph contains the specified vertex.
     */
    boolean containsVertex(V vertex);

    boolean containsAllVertices(V... vertex);

    boolean containsAllVertices(Collection<? extends V> vertices);

    List<Boolean> containsVertices(V... vertices);

    List<Boolean> containsVertices(List<? extends V> vertices);

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

    boolean containsAllEdges(E... edges);

    boolean containsAllEdges(Collection<? extends E> edges);

    boolean containsGraph(Graph<V, E, G> graph);

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
    Collection<E> edgesOf(V vertex);

    Collection<E> getEdges();

    /**
     * Returns a set of the getVertices contained in this graph. The set is backed
     * by the graph, so changes to the graph are reflected in the set. If the
     * graph is modified while an iteration over the set is in progress, the
     * results of the iteration are undefined.
     * <p>
     * <p>The graph implementation may maintain a particular set ordering (e.g.
     * via {@link java.util.LinkedHashSet}) for deterministic iteration, but
     * this is not required. It is the responsibility of callers who rely on
     * this behavior to only use graph implementations which support it.</p>
     *
     * @return a set view of the getVertices contained in this graph.
     */
    Collection<V> getVertices();
}
