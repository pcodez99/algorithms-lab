package dataStructure.BinarySearchTree;

public class BST<K extends Comparable<K>, V> {

    public class BSTNode {
        private K key;
        private V value;
        private BSTNode left, right;
        private int size;

        public BSTNode(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
            this.left = null;
            this.right = null;
        }
    }

    private BSTNode root; 



    public void insert(K key, V value) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (value == null) {
            throw new IllegalArgumentException("calls put() with a null value");
        }

        root = put(root, key, value);
    }

    private BSTNode put(BSTNode x, K key, V value) {
        if(x == null) return new BSTNode(key, value, 0);
        int cmp = key.compareTo(x.key);
        if(cmp < 0) x.left = put(x.left, key, value);
        else if(cmp > 0) x.right = put(x.right, key, value);
        else x.value = value;
        x.size++;
        return x;
    }

    public V getValue(K key) {
        if(key == null) throw new IllegalArgumentException("calls getValue() with a null key");
        return get(root, key).value;
    }

    public int getSize(K key) {
        if(key == null) throw new IllegalArgumentException("calls getValue() with a null key");
        return get(root, key).size;
    }

    private BSTNode get(BSTNode x, K key) {
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0) return get(x.left, key);
        else if(cmp > 0) return get(x.right, key);
        return x;
    }

    public void InOrder() {
        InOrder(root);
    }

    private void InOrder(BSTNode x) {
        if(x == null) return;
        InOrder(x.left);
        System.out.println(x.value);
        InOrder(x.right);
    }

    public V GetMin() {
        return min(root).value;
    }

    private BSTNode min(BSTNode x) {
        if(x.left == null) return x;
        return min(x.left);
    }

    public void deleteKey(K key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");

        root = delete(root, key);
    }

    private BSTNode delete(BSTNode x, K key) {
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

            // Due figli
            BSTNode t = x;

            // Successore: minimo del sottoalbero destro
            x = min(t.right);

            // Rimuovo il successore dalla sua posizione originale
            x.right = deleteMin(t.right);

            // Mantengo il sottoalbero sinistro
            x.left = t.left;
        }

        return x;
    }

    private BSTNode deleteMin(BSTNode x) {
        if (x.left == null) {
            return x.right;
        }

        x.left = deleteMin(x.left);

        return x;
    }

    public int getHowMuchGreatestNode(K g) {
        return getHowMuchGreatestNode(root, g);
    }

    private int getHowMuchGreatestNode(BSTNode x, K key) {
        if(x == null) return 0;
        int cmp = key.compareTo(x.key);
        if(cmp < 0) return getHowMuchGreatestNode(x.left, key);
        else if(cmp > 0) return getHowMuchGreatestNode(x.right, key);
        else {
            //abbiamo trovato il nodo
            return sum(x.right, key, 0);
        }
    }

    private int sum(BSTNode x, K key, int sum) {
        if(x == null) return sum;
        return sum(x.right, key, sum + 1);
    }
}
