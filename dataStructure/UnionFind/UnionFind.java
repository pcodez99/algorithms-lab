package dataStructure.UnionFind;

public class UnionFind {
    private final int[] id;
    private final int[] sz;
    private int count;

    public UnionFind(int n) {
        if (n < 0) throw new IllegalArgumentException("Negative number of elements");
        id = new int[n];
        sz = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    public int find(int p) {
        if (p < 0 || p >= id.length) throw new IllegalArgumentException("Invalid index: " + p);
        while (p != id[p]) p = id[p];
        return p;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        // Collega la radice dell'albero più piccolo a quella del più grande.
        if (sz[rootP] < sz[rootQ]) {
            id[rootP] = rootQ;
            sz[rootQ] += sz[rootP];
        } else {
            id[rootQ] = rootP;
            sz[rootP] += sz[rootQ];
        }
        count--;
    }

    public int count() {
        return count;
    }
}
