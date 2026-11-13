package Graph;

import java.util.ArrayList;
import java.util.Arrays;

public class AdjMatrixEdgeWeightedGraph {
    private final int V;
    private int E;
    private final double[][] adj;

    public AdjMatrixEdgeWeightedGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Negative number of vertices");
        this.V = V;
        adj = new double[V][V];
        for (double[] row : adj) Arrays.fill(row, Double.POSITIVE_INFINITY);
    }

    public int V() { return V; }
    public int E() { return E; }

    public void addEdge(int v, int w, double weight) {
        validateVertex(v);
        validateVertex(w);
        if (!Double.isFinite(weight)) throw new IllegalArgumentException("Non-finite weight");
        if (!adjacent(v, w)) E++;
        adj[v][w] = weight;
        adj[w][v] = weight;
    }

    public boolean adjacent(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return adj[v][w] != Double.POSITIVE_INFINITY;
    }

    public double weight(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return adj[v][w];
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
