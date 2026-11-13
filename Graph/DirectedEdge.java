package Graph;

public class DirectedEdge {
    private final int v;
    private final int w;
    private final double weight;

    public DirectedEdge(int v, int w, double weight) {
        if (v < 0 || w < 0) throw new IllegalArgumentException("Negative vertex");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("NaN weight");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int from() { return v; }
    public int to() { return w; }
    public double weight() { return weight; }
    public String toString() { return v + "->" + w + " " + weight; }
}
