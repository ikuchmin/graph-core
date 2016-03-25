package ru.osslabs.plarform.modules.graph.impl

import groovy.transform.CompileStatic
import ru.osslabs.plarform.modules.graph.Edge
import ru.osslabs.plarform.modules.graph.Graph

import java.util.function.BiFunction
import java.util.stream.Stream
import java.util.stream.StreamSupport

import static ru.osslabs.plarform.modules.graph.impl.BreadthFirstIterator.Itr.VisitColor.GRAY
import static ru.osslabs.plarform.modules.graph.impl.BreadthFirstIterator.Itr.VisitColor.WHITE

/**
 * Created by ikuchmin on 16.03.16.
 */
@CompileStatic
class BreadthFirstIterator<V, E extends Edge<V>> implements Graph<V, E>, Iterable<E> {
    @Delegate
    private final Graph<V, E> graph;

    private final V startVertex

    BreadthFirstIterator(Graph<V, E> graph) {
        Objects.requireNonNull(graph)
        if (graph.getVertices().isEmpty()) throw new IllegalArgumentException('Graph should have one or more getVertices. Current graph haven\'t getVertices')

        this.graph = graph
        this.startVertex = graph.getVertices().first()
    }

    BreadthFirstIterator(Graph<V, E> graph, V startVertex) {
        this.graph = graph
        this.startVertex = startVertex
    }

    @Override
    // TODO: support ConcurrentModificationException if graph modified
    Iterator<E> iterator() {
        return new Itr(startVertex)
    }

    /**
     * Creates a {@link Spliterator} over the elements described by this
     * {@code Iterable}.
     *
     * @implSpec
     * The default implementation creates an
     * <em><a href="Spliterator.html#binding">early-binding</a></em>
     * spliterator from the iterable's {@code Iterator}.  The spliterator
     * inherits the <em>fail-fast</em> properties of the iterable's iterator.
     *
     * @implNote
     * The default implementation should usually be overridden.  The
     * spliterator returned by the default implementation has poor splitting
     * capabilities, is unsized, and does not report any spliterator
     * characteristics. Implementing classes can nearly always provide a
     * better implementation.
     *
     * @return a {@code Spliterator} over the elements described by this
     * {@code Iterable}.
     * @since 1.8
     */
    Spliterator<E> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }

    /**
     * Returns a sequential {@code Stream} with this collection as its source.
     *
     * <p>This method should be overridden when the {@link #spliterator()}
     * method cannot return a spliterator that is {@code IMMUTABLE},
     * {@code CONCURRENT}, or <em>late-binding</em>. (See {@link #spliterator()}
     * for details.)
     *
     * @implSpec
     * The default implementation creates a sequential {@code Stream} from the
     * collection's {@code Spliterator}.
     *
     * @return a sequential {@code Stream} over the elements in this collection
     * @since 1.8
     */
    Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    public <R, EE extends Edge<R>, G extends Graph<R, EE>> G collectVertices(V startVertex,
                                                                             G collector,
                                                                             BiFunction<Optional<R>, V, R> mapper) {

        Map<V, Optional<R>> mappedVertices = new HashMap<>()

        Closure<Optional<R>> innerFn = { Optional<R> parent, V target ->
            def mappedVertex = Optional.ofNullable(mapper.apply(parent, target))
            mappedVertices.put(target, mappedVertex)

            if (mappedVertex.isPresent()) {
                collector.addVertex(mappedVertex.get())
                if (parent.isPresent()) collector.addEdge(parent.get(), mappedVertex.get())
            }
            return mappedVertex
        }

        innerFn(Optional.empty(), startVertex)
        forEach { Edge<V> e -> innerFn(mappedVertices[e.source], e.target) }

        return collector
    }

    private class Itr implements Iterator<E> {

        Queue<E> queue = new LinkedList<>()
        Map<V, VisitColor> seen = new HashMap<>()

        Itr(V startVertex) {
            seen.put(startVertex, WHITE)
            encounterVertex(startVertex)
        }

        @Override
        boolean hasNext() {
            !queue.isEmpty()
        }

        @Override
        E next() {
            E res = queue.poll()
            encounterVertex(res.target)
            return res
        }

        def V encounterVertex(V target) {
            if (seen.put(target, GRAY) == WHITE)
                graph.edgesOf(target)
                        .findAll { e -> !seen.containsKey(e.target) }
                        .each { e -> seen.put(e.target, WHITE); queue.offer(e) }
            else {
                throw new RuntimeException("Algorithm has collision. Please check implementation")
            }
            return target
        }

        protected static enum VisitColor {
            WHITE, GRAY
        }
    }
}
