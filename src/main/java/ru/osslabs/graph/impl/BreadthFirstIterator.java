package ru.osslabs.graph.impl;

import ru.osslabs.graph.Edge;
import ru.osslabs.graph.Graph;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static ru.osslabs.graph.impl.BreadthFirstIterator.VisitColor.GRAY;
import static ru.osslabs.graph.impl.BreadthFirstIterator.VisitColor.WHITE;

/**
 * Created by ikuchmin on 16.03.16.
 */
public class BreadthFirstIterator<V, E extends Edge<V>, G extends Graph<V, E, G>> implements Graph<V, E, G>, Iterable<E> {
//    @Delegate(types = {GraphReadOperations.class, GraphModifyOperations.class})
    private final Graph<V, E, G> graph;

    private final V startVertex;

    public BreadthFirstIterator(Graph<V, E, G> graph, V startVertex) {
        this.graph = graph;
        this.startVertex = startVertex;
    }

    // TODO: support ConcurrentModificationException if graph modified
    @Override
    public Iterator<E> iterator() {
        return new Itr(startVertex);
    }

    /**
     * Creates a {@link Spliterator} over the elements described by this
     * {@code Iterable}.
     *
     * @return a {@code Spliterator} over the elements described by this
     * {@code Iterable}.
     * @implSpec The default implementation creates an
     * <em><a href="Spliterator.html#binding">early-binding</a></em>
     * spliterator from the iterable's {@code Iterator}.  The spliterator
     * inherits the <em>fail-fast</em> properties of the iterable's iterator.
     * @implNote The default implementation should usually be overridden.  The
     * spliterator returned by the default implementation has poor splitting
     * capabilities, is unsized, and does not report any spliterator
     * characteristics. Implementing classes can nearly always provide a
     * better implementation.
     * @since 1.8
     */
    public Spliterator<E> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }

    /**
     * Returns a sequential {@code Stream} with this collection as its source.
     * <p>
     * <p>This method should be overridden when the {@link #spliterator()}
     * method cannot return a spliterator that is {@code IMMUTABLE},
     * {@code CONCURRENT}, or <em>late-binding</em>. (See {@link #spliterator()}
     * for details.)
     *
     * @return a sequential {@code Stream} over the elements in this collection
     * @implSpec The default implementation creates a sequential {@code Stream} from the
     * collection's {@code Spliterator}.
     * @since 1.8
     */
    Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    public <R, EE extends Edge<R>, G extends Graph<R, EE, G>> G collectVertices(G collector,
                                                                             BiFunction<Optional<R>, V, R> mapper) {

        Map<V, Optional<R>> mappedVertices = new HashMap<>();

        BiFunction<Optional<R>, V, R> innerFn = (parent, target) -> {
            R mappedVertex = mapper.apply(parent, target);

            collector.addVertex(mappedVertex);
            if (parent.isPresent()) collector.addEdge(parent.get(), mappedVertex);

            // cache
            mappedVertices.put(target, Optional.ofNullable(mappedVertex));

            return mappedVertex;
        };

        innerFn.apply(Optional.empty(), startVertex);
        for (Edge<V> e : this)
            innerFn.apply(mappedVertices.get(e.getSource()), e.getTarget());

        return collector;
    }

    private class Itr implements Iterator<E> {

        Queue<E> queue = new LinkedList<>();
        Map<V, VisitColor> seen = new HashMap<>();

        Itr(V startVertex) {
            seen.put(startVertex, WHITE);
            encounterVertex(startVertex);
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public E next() {
            E res = queue.poll();
            encounterVertex(res.getTarget());
            return res;
        }

        private V encounterVertex(V target) {
            if (seen.put(target, GRAY) == WHITE)
                graph.edgesOf(target).stream()
                        .filter(e -> !seen.containsKey(e.getTarget()))
                        .forEach(e -> {
                            seen.put(e.getTarget(), WHITE);
                            queue.offer(e);
                        });
            else
                throw new RuntimeException("Algorithm has collision. Please check implementation");
            return target;
        }
    }

    enum VisitColor {
        WHITE, GRAY
    }

    //boilerplate
    @Override
    public G addEdge(E edge) {
        return graph.addEdge(edge);
    }

    @Override
    public G addEdges(Collection<? extends E> edges) {
        return graph.addEdges(edges);
    }

    @Override
    public G addEdge(V sourceVertex, V targetVertex) {
        return graph.addEdge(sourceVertex, targetVertex);
    }

    @Override
    public G addVertex(V v) {
        return graph.addVertex(v);
    }

    @Override
    public G addVertices(V... vertices) {
        return graph.addVertices(vertices);
    }

    @Override
    public G addVertices(Collection<? extends V> vertices) {
        return graph.addVertices(vertices);
    }

    @Override
    public G addGraph(Graph<V, E, G> sourceGraph) {
        return graph.addGraph(sourceGraph);
    }

    @Override
    public BiFunction<V, V, E> getEdgeFactory() {
        return graph.getEdgeFactory();
    }

    @Override
    public boolean containsVertex(V vertex) {
        return graph.containsVertex(vertex);
    }

    @Override
    public boolean containsAllVertices(V... vertex) {
        return graph.containsAllVertices(vertex);
    }

    @Override
    public boolean containsAllVertices(Collection<? extends V> vertices) {
        return graph.containsAllVertices(vertices);
    }

    @Override
    public List<Boolean> containsVertices(V... vertices) {
        return graph.containsVertices(vertices);
    }

    @Override
    public List<Boolean> containsVertices(List<? extends V> vertices) {
        return graph.containsVertices(vertices);
    }

    @Override
    public boolean containsEdge(E e) {
        return graph.containsEdge(e);
    }

    @Override
    public boolean containsAllEdges(E... edges) {
        return graph.containsAllEdges(edges);
    }

    @Override
    public boolean containsAllEdges(Collection<? extends E> edges) {
        return graph.containsAllEdges(edges);
    }

    @Override
    public boolean containsGraph(Graph<V, E, G> graph) {
        return this.graph.containsGraph(graph);
    }

    @Override
    public Collection<E> edgesOf(V vertex) {
        return graph.edgesOf(vertex);
    }

    @Override
    public Collection<E> getEdges() {
        return graph.getEdges();
    }

    @Override
    public Collection<V> getVertices() {
        return graph.getVertices();
    }
}
