package Graph;

import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Locale;

public class GraphTest {
    public static void main(String[] args) {
        testLists();
        testMatrices();
        testInput();
        System.out.println("All graph tests passed");
    }

    private static void testLists() {
        Graph g = new Graph(3);
        g.addEdge(0, 1);
        g.addEdge(0, 1);
        g.addEdge(1, 1);
        assert g.V() == 3 && g.E() == 3;
        assert list(g.adj(0)).equals(List.of(1, 1));
        assert list(g.adj(1)).equals(List.of(1, 1, 0, 0));
        assert list(g.adj(2)).isEmpty();
        expect(IllegalArgumentException.class, () -> g.addEdge(0, 3));
        assert g.E() == 3 && list(g.adj(0)).size() == 2;
        expect(IllegalArgumentException.class, () -> g.adj(-1));
        expect(IllegalArgumentException.class, () -> new Graph(-1));
        assert new Graph(0).E() == 0;

        Digraph d = new Digraph(3);
        d.addEdge(0, 1);
        d.addEdge(0, 1);
        d.addEdge(1, 1);
        assert d.E() == 3 && list(d.adj(1)).equals(List.of(1));
        Digraph r = d.reverse();
        assert r.V() == 3 && r.E() == 3;
        assert list(r.adj(0)).isEmpty();
        assert list(r.adj(1)).equals(List.of(1, 0, 0));
        expect(IllegalArgumentException.class, () -> d.addEdge(0, 3));
        assert d.E() == 3 && list(d.adj(0)).size() == 2;

        Edge e = new Edge(0, 1, -2);
        Edge loop = new Edge(1, 1, 0);
        assert e.either() == 0 && e.other(0) == 1 && e.other(1) == 0;
        assert e.weight() == -2 && e.compareTo(loop) < 0;
        expect(IllegalArgumentException.class, () -> e.other(2));
        expect(IllegalArgumentException.class, () -> new Edge(-1, 0, 1));
        expect(IllegalArgumentException.class, () -> new Edge(0, 1, Double.NaN));
        EdgeWeightedGraph w = new EdgeWeightedGraph(3);
        w.addEdge(e);
        w.addEdge(e);
        w.addEdge(loop);
        w.addEdge(loop);
        assert w.V() == 3 && w.E() == 4;
        assert list(w.adj(1)).size() == 6;
        assert list(w.edges()).size() == w.E();
        assert list(w.edges()).stream().filter(edge -> edge == loop).count() == 2;
        expect(IllegalArgumentException.class, () -> w.addEdge(new Edge(0, 3, 1)));
        assert w.E() == 4 && list(w.adj(0)).size() == 2;
        assert list(new EdgeWeightedGraph(0).edges()).isEmpty();

        DirectedEdge de = new DirectedEdge(0, 1, -2);
        assert de.from() == 0 && de.to() == 1 && de.weight() == -2;
        expect(IllegalArgumentException.class, () -> new DirectedEdge(0, 1, Double.NaN));
        EdgeWeightedDigraph wd = new EdgeWeightedDigraph(3);
        wd.addEdge(de);
        wd.addEdge(de);
        wd.addEdge(new DirectedEdge(1, 1, 0));
        assert wd.V() == 3 && wd.E() == 3;
        assert wd.indegree(1) == 3 && wd.indegree(0) == 0;
        assert list(wd.adj(1)).size() == 1 && list(wd.edges()).size() == 3;
        expect(IllegalArgumentException.class, () -> wd.addEdge(new DirectedEdge(0, 3, 1)));
        assert wd.E() == 3 && list(wd.adj(0)).size() == 2;
        assert list(new EdgeWeightedDigraph(0).edges()).isEmpty();
    }

    private static void testMatrices() {
        AdjMatrixGraph g = new AdjMatrixGraph(3);
        g.addEdge(0, 1);
        g.addEdge(1, 0);
        g.addEdge(1, 1);
        assert g.V() == 3 && g.E() == 2;
        assert g.adjacent(0, 1) && g.adjacent(1, 0) && !g.adjacent(0, 2);
        assert list(g.adj(1)).equals(List.of(0, 1));
        expect(IllegalArgumentException.class, () -> g.addEdge(0, 3));
        assert g.E() == 2;
        expect(IllegalArgumentException.class, () -> new AdjMatrixGraph(-1));
        assert new AdjMatrixGraph(0).V() == 0;

        AdjMatrixDigraph d = new AdjMatrixDigraph(3);
        d.addEdge(0, 1);
        d.addEdge(0, 1);
        d.addEdge(1, 1);
        assert d.E() == 2 && d.adjacent(0, 1) && !d.adjacent(1, 0);
        assert list(d.adj(1)).equals(List.of(1));
        expect(IllegalArgumentException.class, () -> d.adj(3));

        AdjMatrixEdgeWeightedGraph w = new AdjMatrixEdgeWeightedGraph(3);
        w.addEdge(0, 1, 0);
        w.addEdge(1, 0, -2);
        w.addEdge(1, 1, 3);
        assert w.E() == 2 && w.weight(0, 1) == -2 && w.weight(1, 0) == -2;
        assert w.weight(0, 2) == Double.POSITIVE_INFINITY;
        assert !w.adjacent(0, 2) && list(w.adj(1)).equals(List.of(0, 1));
        expect(IllegalArgumentException.class, () -> w.addEdge(0, 2, Double.NaN));
        expect(IllegalArgumentException.class, () -> w.addEdge(0, 2, Double.POSITIVE_INFINITY));
        assert w.E() == 2 && !w.adjacent(0, 2);

        AdjMatrixEdgeWeightedDigraph wd = new AdjMatrixEdgeWeightedDigraph(3);
        wd.addEdge(0, 1, 0);
        wd.addEdge(0, 1, -2);
        wd.addEdge(1, 1, 3);
        assert wd.E() == 2 && wd.weight(0, 1) == -2;
        assert !wd.adjacent(1, 0) && wd.weight(1, 0) == Double.POSITIVE_INFINITY;
        assert list(wd.adj(1)).equals(List.of(1));
        expect(IllegalArgumentException.class, () -> wd.addEdge(0, 3, 1));
        expect(IllegalArgumentException.class, () -> wd.addEdge(0, 2, Double.NEGATIVE_INFINITY));
        assert wd.E() == 2;
    }

    private static void testInput() {
        Graph g = new Graph(input("3 2 0 1 1 2"));
        assert g.V() == 3 && g.E() == 2 && list(g.adj(1)).equals(List.of(2, 0));
        Digraph d = new Digraph(input("3 2 0 1 1 2"));
        assert d.E() == 2 && list(d.adj(1)).equals(List.of(2));
        EdgeWeightedGraph w = new EdgeWeightedGraph(input("3 2 0 1 0 1 2 -2.5"));
        assert w.E() == 2 && list(w.adj(2)).get(0).weight() == -2.5;
        EdgeWeightedDigraph wd = new EdgeWeightedDigraph(input("3 2 0 1 0 1 2 -2.5"));
        assert wd.E() == 2 && wd.indegree(2) == 1 && list(wd.adj(2)).isEmpty();
        expect(IllegalArgumentException.class, () -> new Graph(input("3 -1")));
        expect(IllegalArgumentException.class, () -> new Digraph(input("3 1 0 3")));
        expect(java.util.NoSuchElementException.class, () -> new Graph(input("3 1 0")));
    }

    private static In input(String text) {
        return new In(new Scanner(text).useLocale(Locale.US));
    }

    private static <T> List<T> list(Iterable<T> items) {
        List<T> result = new ArrayList<>();
        for (T item : items) result.add(item);
        return result;
    }

    private static void expect(Class<? extends RuntimeException> type, Runnable operation) {
        try {
            operation.run();
        } catch (RuntimeException exception) {
            assert type.isInstance(exception) : exception;
            return;
        }
        throw new AssertionError("Expected " + type.getSimpleName());
    }
}
