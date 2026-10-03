package dataStructure.AVL;

public class AVL<K extends Comparable<K>, V> {

    public class TNode {
        private K key;
        private V value;
        private TNode left, right;
        private int size;
        private int height;

        public TNode(K key, V value, int size, int height) {
            this.key = key;
            this.value = value;
            this.size = size;
            this.height = height;
            this.left = null;
            this.right = null;
        }
    }

    private TNode root;

    public void insert(K key, V value) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (value == null) {
            throw new IllegalArgumentException("calls put() with a null value");
        }

        root = put(root, key, value);
    }

    private TNode put(TNode x, K key, V value) {
        if (x == null) return new TNode(key, value, 1, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, value);
        else if (cmp > 0) x.right = put(x.right, key, value);
        else x.value = value;
        return rebalance(x);
    }

    private TNode rebalance(TNode x) {
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));

        int factor = balanceFactor(x);
        if (factor > 1) {
            if (balanceFactor(x.left) < 0) x.left = rotateLeft(x.left);
            return rotateRight(x);
        }
        if (factor < -1) {
            if (balanceFactor(x.right) > 0) x.right = rotateRight(x.right);
            return rotateLeft(x);
        }
        return x;
    }

    private TNode rotateRight(TNode v) {

        TNode u = v.left;
        TNode B = u.right;

        // Rotazione
        u.right = v;
        v.left = B;

        // Aggiorno le altezze
        v.size = 1 + size(v.left) + size(v.right);
        v.height = 1 + Math.max(height(v.left), height(v.right));
        u.size = 1 + size(u.left) + size(u.right);
        u.height = 1 + Math.max(height(u.left), height(u.right));

        return u;
    }

    private TNode rotateLeft(TNode v) {
        TNode u = v.right;
        v.right = u.left;
        u.left = v;
        v.size = 1 + size(v.left) + size(v.right);
        v.height = 1 + Math.max(height(v.left), height(v.right));
        u.size = 1 + size(u.left) + size(u.right);
        u.height = 1 + Math.max(height(u.left), height(u.right));
        return u;
    }

    public V getValue(K key) {
        if (key == null) throw new IllegalArgumentException("calls getValue() with a null key");
        return get(root, key).value;
    }

    public int getSize(K key) {
        if (key == null) throw new IllegalArgumentException("calls getValue() with a null key");
        return get(root, key).size;
    }

    public int getHeight(K key) {
        return get(root, key).height;
    }

    public int getBalanceFactor(K key) {
        TNode x = get(root, key);

        if (x == null) return 0;

        return balanceFactor(x);
    }

    private TNode get(TNode x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        return x;
    }

    private int height(TNode x) {
        if (x == null) return 0;
        return x.height;
    }

    private int size(TNode x) {
        return x == null ? 0 : x.size;
    }

    private int balanceFactor(TNode x) {
        if (x == null) return 0;

        return height(x.left) - height(x.right);
    }

    public void InOrder() {
        InOrder(root);
    }

    private void InOrder(TNode x) {
        if (x == null) return;
        InOrder(x.left);
        System.out.println(x.key + ": ");
        InOrder(x.right);
    }

    public V GetMin() {
        return min(root).value;
    }

    private TNode min(TNode x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public void deleteKey(K key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");

        root = delete(root, key);
    }

    private TNode delete(TNode x, K key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);

        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            // Nessun figlio destro
            if (x.right == null) return x.left;

            // Nessun figlio sinistro
            if (x.left == null) return x.right;

            // Due figli: copia il successore e rimuovilo risalendo dal suo ramo.
            TNode successor = min(x.right);
            x.key = successor.key;
            x.value = successor.value;
            x.right = delete(x.right, successor.key);
        }

        return rebalance(x);
    }

    public int getHowMuchGreatestNode(K g) {
        return getHowMuchGreatestNode(root, g);
    }

    private int getHowMuchGreatestNode(TNode x, K key) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return getHowMuchGreatestNode(x.left, key);
        else if (cmp > 0) return getHowMuchGreatestNode(x.right, key);
        else {
            // Abbiamo trovato il nodo
            return sum(x.right, key, 0);
        }
    }

    private int sum(TNode x, K key, int sum) {
        if (x == null) return sum;
        return sum(x.right, key, sum + 1);
    }

    public void printTree() {
        printTree(root, "");
    }

    private void printTree(TNode node, String path) {
        if (node == null) return;
        System.out.println(path + "\t" + node.key + "\t" + node.size + "\t" + node.height + "\t" + node.value);
        printTree(node.left, path + "L");
        printTree(node.right, path + "R");
    }
}
