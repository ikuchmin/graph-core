package ru.osslabs.graph.impl;

import ru.osslabs.graph.DirectedGraph;
import ru.osslabs.graph.Edge;
import ru.osslabs.graph.Graph;
import ru.osslabs.graph.collection.GraphMap;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * Created by ikuchmin on 03.03.16.
 */
public abstract class AbstractDirectedGraph<V, E extends Edge<V>, G extends DirectedGraph<V, E, G>,
        GM extends GraphMap<V, DirectedEdgeContainer<V, E>, GM>>
        extends AbstractGraph<V, E, G> implements DirectedGraph<V, E, G> {

    @Override
    @SuppressWarnings("unchecked")
    public G addEdge(E edge) {
        if (edge == null) throw new IllegalArgumentException("Edge equals null");

        Optional<DirectedEdgeContainer<V, E>> src = getGraphMap().get(edge.getSource());
        Optional<DirectedEdgeContainer<V, E>> trg = getGraphMap().get(edge.getTarget());

        if (src.isPresent() && src.get().getOutgoingEdges().contains(edge))
            throw new IllegalArgumentException(format("Edge (%s) contains in graph. This is collision.", edge));
        if (!src.isPresent() || !trg.isPresent())
            throw new IllegalArgumentException(format("Vertex not found in graph. Source: %s, Target: %s",
                    edge.getSource(), edge.getTarget()));

        src.get().addOutgoingEdge(edge);
        trg.get().addIncomingEdge(edge);

        return (G) this;
    }

    // FIXME: Bug in groovy https://issues.apache.org/jira/browse/GROOVY-7799
//    @Override
//    public DirectedGraph<V, E> addEdges(E... edges) {
//        return addEdges(asList(edges))
//    }

    @Override
    @SuppressWarnings("unchecked")
    public G addEdges(Collection<? extends E> edges) {
        if (edges.contains(null)) throw new IllegalArgumentException("Collection of edges can not contains null edge");
        edges.forEach(this::addEdge);
        return (G) this;
    }

    @Override
    public G addEdge(V sourceVertex, V targetVertex) {
        if (sourceVertex == null) throw new IllegalArgumentException("Source vertex equals null");
        if (targetVertex == null) throw new IllegalArgumentException("Target vertex equals null");

        return addEdge(getEdgeFactory().apply(sourceVertex, targetVertex));
    }

    @Override
    public G addEdge(List<?> sourceVertex, List<?> targetVertex) {
        if (sourceVertex == null) throw new IllegalArgumentException("Source equals null");
        if (sourceVertex.isEmpty()) throw new IllegalArgumentException("List args in source param is empty");
        if (sourceVertex.size() > 1) throw new IllegalArgumentException("List args in source param cannot has elements greater-than one");

        if (targetVertex == null) throw new IllegalArgumentException("Target equals null");
        if (targetVertex.isEmpty()) throw new IllegalArgumentException("List args in target param is empty");
        if (targetVertex.size() > 1) throw new IllegalArgumentException("List args in target param cannot has elements greater-than one");

        return addEdge((V) sourceVertex.get(0), (V) targetVertex.get(0));
    }

    @Override
    @SuppressWarnings("unchecked")
    public G addVertex(V vertex) {
        if (vertex == null) throw new IllegalArgumentException("Vertex equals null");
        if (getGraphMap().containsKey(vertex))
            throw new IllegalArgumentException(format("Vertex with name %s has in graph. This is collision", vertex));

        getGraphMap().put(vertex, new DirectedEdgeContainer<>(vertex));

        return (G) this;
    }

    @Override
    public G addVertex(List<?> args) {
        if (args == null) throw new IllegalArgumentException("Args equals null");
        if (args.isEmpty()) throw new IllegalArgumentException("List args is empty");
        if (args.size() > 1) throw new IllegalArgumentException("List args cannot has elements greater-than one");

        return this.addVertex((V) args.get(0)); // it is not a bug
    }

    @Override
    public G addVertices(V... vertices) {
        return addVertices(asList(vertices));
    }

    @Override
    @SuppressWarnings("unchecked")
    public G addVertices(Collection<? extends V> vertices) {
        if (vertices.contains(null))
            throw new IllegalArgumentException("Collection of getVertices can not contains null getVertices");

        vertices.forEach(this::addVertex);
        return (G) this;
    }

    // TODO: If you try use wildcard ? extends V, ? extends E that you get exception. I don't why.
//    @Override
    @SuppressWarnings("unchecked")
    public G addGraph(Graph<V, E, G> sourceGraph) {
        if (sourceGraph == null) return (G) this;
        Collection<? extends V> srcGraphVertices = sourceGraph.getVertices();
        srcGraphVertices.stream()
                .filter(v -> !containsVertex(v))
                .forEach(this::addVertex);


        srcGraphVertices.stream()
                .flatMap(vertex -> sourceGraph.edgesOf(vertex).stream())
                .filter(e -> !containsEdge(e))
                .forEach(this::addEdge);

        return (G) this;
    }

    @Override
    public boolean containsEdge(E e) {
        return edgesOf(e.getSource()).contains(e);
    }

    @Override
    public boolean containsVertex(V v) {
        return getGraphMap().containsKey(v);
    }

    @Override
    public boolean containsAllVertices(V... vertices) {
        return containsAllVertices(asList(vertices));
    }

    @Override
    public boolean containsAllVertices(Collection<? extends V> vertices) {
        return getGraphMap().containsAllKey(vertices);
    }

    @Override
    public List<Boolean> containsVertices(V... vertices) {
        return containsVertices(asList(vertices));
    }

    @Override
    public List<Boolean> containsVertices(List<? extends V> vertices) {
        if (vertices == null || vertices.isEmpty()) return Collections.singletonList(true);

        return vertices.stream().map(getGraphMap()::containsKey).collect(toList());
    }

    protected List<Boolean> containsVertices(V vertex, List<? extends V> vertices,
                                             Function<DirectedEdgeContainer<V, E>, Collection<V>> fnVertices) {
        Optional<DirectedEdgeContainer<V, E>> directedEdge = getGraphMap().get(vertex);

        if (!directedEdge.isPresent()) return vertices.parallelStream().map(v -> false).collect(toList());

        return vertices.stream()
                .map(fnVertices.apply(directedEdge.get())::contains)
                .collect(toList());
    }

    @Override
    public boolean containsGraph(Graph<V, E, G> graph) {
        return containsAllVertices(graph.getVertices()) && containsAllEdges(graph.getEdges());
    }

    @Override
    public boolean containsAllEdges(E... edges) {
        return containsAllEdges(asList(edges));
    }

    @Override
    public boolean containsAllEdges(Collection<? extends E> edges) {
        if (edges == null || edges.isEmpty()) return true;

        Collection<? extends E> edg = edges;
        if (edges.contains(null)) edg = edges.stream()
                .filter(e -> e != null)
                .collect(Collectors.toList());

        return getEdges().containsAll(edg);
    }

    @Override
    public List<Boolean> containsOutgoingVertices(V vertex, V... vertices) {
        return containsOutgoingVertices(vertex, asList(vertices));
    }

    @Override
    public List<Boolean> containsOutgoingVertices(V vertex, List<? extends V> vertices) {
        return containsVertices(vertex, vertices, DirectedEdgeContainer::getOutgoingVertices);
    }

    @Override
    public List<Boolean> containsIncomingVertices(V vertex, V... vertices) {
        return containsIncomingVertices(vertex, asList(vertices));
    }

    @Override
    public List<Boolean> containsIncomingVertices(V vertex, List<? extends V> vertices) {
        return containsVertices(vertex, vertices, DirectedEdgeContainer::getIncomingVertices);
    }


    @Override
    public Collection<E> edgesOf(V vertex) {
        return outgoingEdgesOf(vertex);
    }

    @Override
    public Collection<E> getEdges() {
        return getGraphMap().values().stream()
                .flatMap(e -> e.getOutgoingEdges().stream())
                .collect(Collectors.toList());
    }

    ;

    @Override
    public Collection<E> incomingEdgesOf(V vertex) {
        Optional<DirectedEdgeContainer<V, E>> edge = getGraphMap().get(vertex);
        if (!edge.isPresent()) return Collections.emptyList();
        return edge.get().getIncomingEdges();

    }

    @Override
    public Collection<V> incomingVerticesOf(V vertex) {
        Optional<DirectedEdgeContainer<V, E>> edge = getGraphMap().get(vertex);
        if (!edge.isPresent()) return Collections.emptyList();
        return edge.get().getIncomingVertices();
    }

    @Override
    public Collection<E> outgoingEdgesOf(V vertex) {
        Optional<DirectedEdgeContainer<V, E>> edge = getGraphMap().get(vertex);
        if (!edge.isPresent()) return Collections.emptyList();
        return edge.get().getOutgoingEdges();
    }

    @Override
    public Collection<V> outgoingVerticesOf(V vertex) {
        Optional<DirectedEdgeContainer<V, E>> edge = getGraphMap().get(vertex);
        if (!edge.isPresent()) return Collections.emptyList();
        return edge.get().getOutgoingVertices();
    }

    @Override
    public Collection<V> getVertices() {
        return getGraphMap().keys();
    }

    protected abstract GM getGraphMap();
}