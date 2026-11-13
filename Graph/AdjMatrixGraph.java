package Graph;

import java.util.ArrayList;

public class AdjMatrixGraph {
    private final int V;
    private int E;
    private final int[][] adj;

    public AdjMatrixGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Negative number of vertices");
        this.V = V;
        adj = new int[V][V];
    }

    public int V() { return V; }
    public int E() { return E; }

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        if (!adjacent(v, w)) E++;
        adj[v][w] = 1;
        adj[w][v] = 1;
    }

    public boolean adjacent(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return adj[v][w] == 1;
    }

    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        ArrayList<Integer> neighbours = new ArrayList<>();
        for (int w = 0; w < V; w++)
            if (adjacent(v, w)) neighbours.add(w);
        return neighbours;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) throw new IllegalArgumentException("Invalid vertex: " + v);
    }
}
