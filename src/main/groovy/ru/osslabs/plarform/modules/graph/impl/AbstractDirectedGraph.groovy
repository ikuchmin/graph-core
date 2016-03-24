package ru.osslabs.plarform.modules.graph.impl

import groovy.transform.CompileStatic
import ru.osslabs.plarform.modules.graph.DirectedGraph
import ru.osslabs.plarform.modules.graph.Edge
import ru.osslabs.plarform.modules.graph.Graph
import ru.osslabs.plarform.modules.graph.GraphMap

import static java.lang.String.format
import static java.util.Arrays.asList

/**
 * Created by ikuchmin on 03.03.16.
 */
@CompileStatic
abstract class AbstractDirectedGraph<V, E extends Edge<V>> extends AbstractGraph<V, E> implements DirectedGraph<V, E> {

    @Override
    public DirectedGraph<V, E> addEdge(E edge) {
        if (edge == null) throw new IllegalArgumentException("Edge equals null");

        Optional<DirectedEdgeContainer<V, E>> src = graphMap().get(edge.getSource());
        Optional<DirectedEdgeContainer<V, E>> trg = graphMap().get(edge.getTarget());

        if (src.isPresent() && trg.isPresent()) {
            src.get().addOutgoingEdge(edge);
            trg.get().addIncomingEdge(edge);
        } else
            throw new IllegalArgumentException(format("Vertex not found in graph. Source: %s, Target: %s",
                    edge.getSource(), edge.getTarget()));

        return this;
    }

    // FIXME: Bug in groovy https://issues.apache.org/jira/browse/GROOVY-7799
//    @Override
//    public DirectedGraph<V, E> addEdges(E... edges) {
//        return addEdges(asList(edges))
//    }

    @Override
    public DirectedGraph<V, E> addEdges(Collection<E> edges) {
        if (edges.contains(null)) throw new IllegalArgumentException("Collection of edges can not contains null edge")
        edges.each(this.&addEdge);
        return this;
    }

    @Override
    public DirectedGraph<V, E> addEdge(V sourceVertex, V targetVertex) {
        if (sourceVertex == null) throw new IllegalArgumentException("Source vertex equals null");
        if (targetVertex == null) throw new IllegalArgumentException("Source vertex equals null");

        return addEdge(edgeFactory().apply(sourceVertex, targetVertex));
    }

    @Override
    public DirectedGraph<V, E> addVertex(V vertex) {
        if (vertex == null) throw new IllegalArgumentException("Vertex equals null");
        if (graphMap().containsKey(vertex)) throw new IllegalArgumentException(format("Vertex with name %s has in graph. This is collision", vertex));

        graphMap().put(vertex, new DirectedEdgeContainer<>(vertex));

        return this;
    }

    @Override
    public DirectedGraph<V, E> addVertices(V... vertices) {
        return addVertices(asList(vertices))
    }

    @Override
    public DirectedGraph<V, E> addVertices(Collection<V> vertices) {
        if (vertices.contains(null)) throw new IllegalArgumentException("Collection of vertices can not contains null vertices")

        vertices.each(this.&addVertex)
        return this
    }


//    @Override
//    @SuppressWarnings("unchecked")
//    public Graph<V, E> addGraph(Graph<? extends V, ? extends E> sourceGraph) {
//        if (this == sourceGraph) return this;
//        if (!(sourceGraph instanceof DirectedGraphImpl))
//            throw new IllegalArgumentException("Source graph isn't implementing DirectedGraphImpl");
//
//        DirectedGraphImpl<V, E> that = (DirectedGraphImpl<V, E>) sourceGraph;
//
//        for (V key : vertices.keySet())
//            if (vertices.keySet().contains(key))
//                throw new IllegalArgumentException("This graph and source graph aren't contains some general vertices");
//
//        vertices.putAll(that.vertices);
//        return this;
//    }

    @Override
    public Graph<V, E> addGraph(Graph<? extends V, ? extends E> sourceGraph) {
        return null;
    }

    @Override
    public boolean containsEdge(E e) {
        throw new UnsupportedOperationException("Operation isn't supported");
    }

    @Override
    public boolean containsVertex(V v) {
        return graphMap().containsKey(v);
    }

    @Override
    List<Boolean> containsVertices(V... vertices) {
        return containsVertices(asList(vertices))
    }

    @Override
    List<Boolean> containsVertices(List<V> vertices) {
        if (vertices == null) return [false]

        return vertices.collect(graphMap().&containsKey)
    }

    List<Boolean> containsVertices(V vertex, List<V> vertices, Closure<Collection<V>> fnVertices) {
        Optional<DirectedEdgeContainer<V, E>> directedEdge = graphMap().get(vertex);

        if (!directedEdge.isPresent()) {
            def arr = new Boolean[vertices.size()]
            Arrays.fill(arr, false)
            return arr.toList()
        }

        return vertices.collect(fnVertices(directedEdge.get()).&contains)
    }

    @Override
    public List<Boolean> containsOutgoingVertices(V vertex, V... vertices) {
        return containsOutgoingVertices(vertex, asList(vertices));
    }

    @Override
    public List<Boolean> containsOutgoingVertices(V vertex, List<V> vertices) {
        return containsVertices(vertex, vertices, { DirectedEdgeContainer<V, E> container ->
            container.getUnmodifiableOutgoingVertices() })
    }

    @Override
    public List<Boolean> containsIncomingVertices(V vertex, V... vertices) {
        return containsIncomingVertices(vertex, asList(vertices));
    }

    @Override
    public List<Boolean> containsIncomingVertices(V vertex, List<V> vertices) {
        return containsVertices(vertex, vertices, { DirectedEdgeContainer<V, E> container ->
            container.getUnmodifiableIncomingVertices() })
    }


    @Override
    public Collection<E> edgesOf(V vertex) {
        return outgoingEdgesOf(vertex);
    }

    @Override
    public Collection<E> incomingEdgesOf(V vertex) {
        def edge = graphMap().get(vertex)
        if (!edge.isPresent()) return []
        return edge.get().unmodifiableIncomingEdges

    }

    @Override
    public Collection<E> outgoingEdgesOf(V vertex) {
        def edge = graphMap().get(vertex)
        if (!edge.isPresent()) return []
        return edge.get().unmodifiableOutgoingEdges
    }

    @Override
    public Collection<V> vertexSet() {
        return graphMap().keySet();
    }

    protected abstract GraphMap<V, DirectedEdgeContainer<V, E>> graphMap();
}

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