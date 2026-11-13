package Graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private Bag<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Negative number of vertices");
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag<?>[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Edge>();
    }

    public EdgeWeightedGraph(In in) {
        this(in.readInt());
        int edges = in.readInt();
        if (edges < 0) throw new IllegalArgumentException("Negative number of edges");
        for (int i = 0; i < edges; i++)
            addEdge(new Edge(in.readInt(), in.readInt(), in.readDouble()));
    }

    public int V() { return V; }
    public int E() { return E; }

    public void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public Iterable<Edge> edges() {
        Bag<Edge> b = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj[v]) {
                if (e.other(v) > v) b.add(e);
                else if (e.other(v) == v) {
                    // Ogni self-loop compare due volte nella stessa lista.
                    if (selfLoops % 2 == 0) b.add(e);
                    selfLoops++;
                }
            }
        }
        return b;
    }

    public String toString() {
        StringBuilder s = new StringBuilder(V + " vertices, " + E + " edges\n");
        for (int v = 0; v < V; v++) {
            s.append(v).append(": ");
            for (Edge w : adj[v]) s.append(w).append(" ");
            s.append('\n');
        }
        return s.toString();
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) throw new IllegalArgumentException("Invalid vertex: " + v);
    }
}
