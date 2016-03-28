package ru.osslabs.graph;

import java.util.Collection;

/**
 * Created by ikuchmin on 28.03.16.
 */
public interface GraphModifyOperations<V, E extends Edge<V>> {

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
    Graph<V, E> addEdge(E edge);

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
    Graph<V, E> addEdges(Collection<? extends E> edges);

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
    Graph<V, E> addEdge(V sourceVertex, V targetVertex);

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
    Graph<V, E> addVertex(V v);

    Graph<V, E> addVertices(V... vertices);

    Graph<V, E> addVertices(Collection<? extends V> vertices);

    /**
     * Adds all the getVertices and all the edges of the {@code sourceGraph} to the
     * graph being built.
     *
     * @return this object
     */
    Graph<V, E> addGraph(Graph<V, E> sourceGraph);

}