package Graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class Graph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("Negative number of vertices");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag<?>[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }

    public Graph(In in) {
        this(in.readInt());
        int edges = in.readInt();
        if (edges < 0) throw new IllegalArgumentException("Negative number of edges");
        for (int i = 0; i < edges; i++)
            addEdge(in.readInt(), in.readInt());
    }

    public int V() { return V; }
    public int E() { return E; }

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public String toString() {
        StringBuilder s = new StringBuilder(V + " vertices, " + E + " edges\n");
        for (int v = 0; v < V; v++) {
            s.append(v).append(": ");
            for (Integer w : adj[v]) s.append(w).append(" ");
            s.append('\n');
        }
        return s.toString();
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) throw new IllegalArgumentException("Invalid vertex: " + v);
    }
}
