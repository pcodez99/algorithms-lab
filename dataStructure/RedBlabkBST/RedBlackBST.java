package dataStructure.RedBlabkBST;

public class RedBlackBST <K extends Comparable<K>, V> {

    private RBNode root;
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public class RBNode {
        private K key;
        private V value;
        private boolean isRed;
        private RBNode left, right;
        private int size;
        private int height;

        public RBNode(K key, V value, boolean isRed) {
            this.key = key;
            this.value = value;
            this.isRed = isRed;
            this.size = 1;
            this.height = 1;
        }
    }

    public V get(K key) {
        return get(root, key).value;
    }

    private RBNode get(RBNode x, K key) {
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0) return x = x.left;
        if(cmp > 1) return x = x.right;
        else return x;
    }

    public void insert(K key, V value) {
        root = insert(root, key, value);
        root.isRed = false; 
    }
    
    private RBNode insert(RBNode x, K key, V value) {
        if(x == null) {
            //facciamo l'inserimento
            return new RBNode(key, value, RED);
        }
        int cmp = key.compareTo(x.key);
        if(cmp < 0) x.left = insert(x.left, key, value);
        else if(cmp > 0) x.right = insert(x.right, key, value);
        else x.value = value;
        if(isRed(x.right) && !isRed(x.left)) x = rotateLeft(x);
        if(isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if(isRed(x.left) && isRed(x.right)) x = colorFlip(x);
        x.size = 1 + (x.left == null ? 0 : x.left.size) + (x.right == null ? 0 : x.right.size);
        x.height = 1 + Math.max(x.left == null ? 0 : x.left.height, x.right == null ? 0 : x.right.height);
        return x;
    }

    private boolean isRed(RBNode x) {
        return x == null ? false : x.isRed == true;
    }

    private RBNode rotateLeft(RBNode v) {
        RBNode u = v.right;
        v.right = u.left;
        u.left = v;
        u.isRed = v.isRed;
        v.isRed = RED;
        v.size = 1 + size(v.left) + size(v.right);
        v.height = 1 + Math.max(height(v.left), height(v.right));
        u.size = 1 + size(u.left) + size(u.right);
        u.height = 1 + Math.max(height(u.left), height(u.right));
        return u;
    }

    private RBNode rotateRight(RBNode v) {

        RBNode u = v.left;
        RBNode B = u.right;

        // Rotazione
        u.right = v;
        v.left = B;
        u.isRed = v.isRed;
        v.isRed = RED;

        // Aggiorno le altezze
        v.size = 1 + size(v.left) + size(v.right);
        v.height = 1 + Math.max(height(v.left), height(v.right));
        u.size = 1 + size(u.left) + size(u.right);
        u.height = 1 + Math.max(height(u.left), height(u.right));

        return u;
    }

    private RBNode colorFlip(RBNode x) {
        x.isRed = RED;
        x.left.isRed = BLACK;
        x.right.isRed = BLACK;
        return x;
    }

    private int size(RBNode x) {
        return x == null ? 0 : x.size;
    }

    private int height(RBNode x) {
        return x == null ? 0 : x.height;
    }

    public void InOrder() {
        InOrder(root);
    }

    private void InOrder(RBNode x) {
        if(x == null) return;
        InOrder(x.left);
        System.out.println(x.key);
        InOrder(x.right);
    }

    public void printTree() {
        printTree(root, "");
    }

    private void printTree(RBNode node, String path) {
        if (node == null) return;
        System.out.println(path + "\t" + node.key + "\t" + node.size + "\t" + node.height + "\t" + node.value + "\t" + node.isRed);
        printTree(node.left, path + "L");
        printTree(node.right, path + "R");
    }
}
