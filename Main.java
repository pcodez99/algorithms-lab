import Graph.Edge;
import Graph.EdgeWeightedGraph;
import Graph.MST.Kruskal.Kruskal;

// ab:4    ah:8    bh:11   bc:8
// ci:2    cd:7    cf:4
// de:9    df:14   ef:10
// fg:2    gh:1    gi:6    hi:7

public class Main {

    public static void main(String[] args){
        EdgeWeightedGraph G = new EdgeWeightedGraph(9);
        // Vertici: a=0, b=1, c=2, d=3, e=4, f=5, g=6, h=7, i=8.
        G.addEdge(new Edge(0, 1, 4));
        G.addEdge(new Edge(0, 7, 8));
        G.addEdge(new Edge(1, 7, 11));
        G.addEdge(new Edge(1, 2, 8));
        G.addEdge(new Edge(2, 8, 2));
        G.addEdge(new Edge(2, 3, 7));
        G.addEdge(new Edge(2, 5, 4));
        G.addEdge(new Edge(3, 4, 9));
        G.addEdge(new Edge(3, 5, 14));
        G.addEdge(new Edge(4, 5, 10));
        G.addEdge(new Edge(5, 6, 2));
        G.addEdge(new Edge(6, 7, 1));
        G.addEdge(new Edge(6, 8, 6));
        G.addEdge(new Edge(7, 8, 7));

        Kruskal T = new Kruskal(G);

        System.out.println(T.peso());
    }
}
