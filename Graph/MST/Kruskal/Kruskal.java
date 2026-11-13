package Graph.MST.Kruskal;

import Graph.Edge;
import Graph.EdgeWeightedGraph;
import dataStructure.PriorityQueue.IndexMinPQ;
import dataStructure.UnionFind.UnionFind;

public class Kruskal {
    private double peso = 0;
    private EdgeWeightedGraph T;

    public Kruskal(EdgeWeightedGraph G){
        T = new EdgeWeightedGraph(G.V());
        IndexMinPQ<Edge> PQ = new IndexMinPQ<Edge>(G.E());
        UnionFind UF = new UnionFind(G.V());

        int i = 0;
        for (Edge e : G.edges()) {
            PQ.insert(i++, e);
        }

        while(!PQ.isEmpty() && T.E() < G.V() - 1) {
            Edge min = PQ.min();
            PQ.delMin();

            int v = min.either();
            int w = min.other(v);

            if(!UF.connected(v, w)) {
                peso += min.weight();
                UF.union(v, w);
                T.addEdge(min);
            }
        }
    }

    public double peso() {
        return this.peso;
    }
}
