package dataStructure.PriorityQueue;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class IndexMinPQ<Item extends Comparable<Item>> {
    private final Item[] keys;
    private final int[] pq;
    private final int[] qp;
    private int n;

    @SuppressWarnings("unchecked")
    public IndexMinPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException("Negative capacity");
        keys = (Item[]) new Comparable<?>[maxN];
        pq = new int[maxN + 1];
        qp = new int[maxN];
        // qp[k] è la posizione di k nello heap; -1 indica un indice assente.
        Arrays.fill(qp, -1);
    }

    public void insert(int k, Item item) {
        if (contains(k)) throw new IllegalArgumentException("Index already present: " + k);
        Objects.requireNonNull(item, "Null item");
        keys[k] = item;
        pq[++n] = k;
        qp[k] = n;
        swim(n);
    }

    public void change(int k, Item item) {
        requirePresent(k);
        Objects.requireNonNull(item, "Null item");
        keys[k] = item;
        swim(qp[k]);
        sink(qp[k]);
    }

    public boolean contains(int k) {
        validateIndex(k);
        return qp[k] != -1;
    }

    public void delete(int k) {
        requirePresent(k);
        int position = qp[k];
        exchange(position, n--);
        if (position <= n) {
            int moved = pq[position];
            swim(position);
            sink(qp[moved]);
        }
        keys[k] = null;
        qp[k] = -1;
        pq[n + 1] = -1;
    }

    public Item min() {
        return keys[minIndex()];
    }

    public int minIndex() {
        if (isEmpty()) throw new NoSuchElementException("Empty priority queue");
        return pq[1];
    }

    public int delMin() {
        int k = minIndex();
        delete(k);
        return k;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void swim(int position) {
        while (position > 1 && greater(position / 2, position)) {
            exchange(position / 2, position);
            position /= 2;
        }
    }

    private void sink(int position) {
        while (position <= n / 2) {
            int child = 2 * position;
            if (child < n && greater(child, child + 1)) child++;
            if (!greater(position, child)) break;
            exchange(position, child);
            position = child;
        }
    }

    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    private void exchange(int i, int j) {
        int temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void validateIndex(int k) {
        if (k < 0 || k >= keys.length) throw new IllegalArgumentException("Invalid index: " + k);
    }

    private void requirePresent(int k) {
        if (!contains(k)) throw new NoSuchElementException("Index not present: " + k);
    }
}
