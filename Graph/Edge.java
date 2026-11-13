package Graph;

public class Edge implements Comparable<Edge> {
    private final int v, w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        if (v < 0 || w < 0) throw new IllegalArgumentException("Negative vertex");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("NaN weight");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() { return weight; }
    public int either() { return v; }

    public int other(int vertex) {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    public String toString() { return v + "-" + w + " " + weight; }
}
