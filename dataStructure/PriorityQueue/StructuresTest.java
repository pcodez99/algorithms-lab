package dataStructure.PriorityQueue;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

import dataStructure.UnionFind.UnionFind;

public class StructuresTest {
    public static void main(String[] args) {
        testPriorityQueue();
        testUnionFind();
        System.out.println("All tests passed");
    }

    private static void testPriorityQueue() {
        IndexMinPQ<Integer> pq = new IndexMinPQ<>(20);
        Integer[] expected = new Integer[20];
        Random random = new Random(1);
        for (int step = 0; step < 5000; step++) {
            int k = random.nextInt(expected.length);
            int value = random.nextInt(10);
            switch (random.nextInt(4)) {
                case 0:
                    if (expected[k] == null) {
                        pq.insert(k, value);
                        expected[k] = value;
                    }
                    break;
                case 1:
                    if (expected[k] != null) {
                        pq.change(k, value);
                        expected[k] = value;
                    }
                    break;
                case 2:
                    if (expected[k] != null) {
                        pq.delete(k);
                        expected[k] = null;
                    }
                    break;
                default:
                    if (!pq.isEmpty()) {
                        int min = Arrays.stream(expected).filter(v -> v != null)
                                .mapToInt(Integer::intValue).min().orElseThrow();
                        int removed = pq.delMin();
                        assert expected[removed] != null && expected[removed] == min;
                        expected[removed] = null;
                    }
            }
            int size = 0;
            Integer min = null;
            for (int i = 0; i < expected.length; i++) {
                assert pq.contains(i) == (expected[i] != null);
                if (expected[i] != null) {
                    size++;
                    if (min == null || expected[i] < min) min = expected[i];
                }
            }
            assert pq.size() == size;
            assert pq.isEmpty() == (size == 0);
            if (min != null) {
                assert pq.min().equals(min);
                assert expected[pq.minIndex()].equals(min);
            }
        }
        while (!pq.isEmpty()) pq.delMin();
        expect(NoSuchElementException.class, () -> pq.min());
        expect(NoSuchElementException.class, () -> pq.minIndex());
        expect(NoSuchElementException.class, () -> pq.delMin());
        expect(NoSuchElementException.class, () -> pq.change(0, 1));
        expect(NoSuchElementException.class, () -> pq.delete(0));
        expect(IllegalArgumentException.class, () -> pq.contains(-1));
        expect(IllegalArgumentException.class, () -> pq.insert(20, 1));
        expect(IllegalArgumentException.class, () -> new IndexMinPQ<Integer>(-1));
        expect(IllegalArgumentException.class, () -> new IndexMinPQ<Integer>(0).insert(0, 1));
        expect(NullPointerException.class, () -> pq.insert(0, null));
        for (int i = 0; i < 20; i++) pq.insert(i, i);
        expect(IllegalArgumentException.class, () -> pq.insert(0, 2));
        expect(NullPointerException.class, () -> pq.change(0, null));
        for (int i = 0; i < 20; i++) assert pq.delMin() == i;
    }

    private static void testUnionFind() {
        UnionFind uf = new UnionFind(20);
        int[] component = new int[20];
        for (int i = 0; i < component.length; i++) component[i] = i;
        Random random = new Random(2);
        for (int step = 0; step < 100; step++) {
            int p = random.nextInt(20);
            int q = random.nextInt(20);
            int old = component[p];
            int replacement = component[q];
            uf.union(p, q);
            for (int i = 0; i < 20; i++) {
                if (component[i] == old) component[i] = replacement;
            }
            assert uf.count() == Arrays.stream(component).distinct().count();
            for (int i = 0; i < 20; i++) {
                assert uf.find(uf.find(i)) == uf.find(i);
                for (int j = 0; j < 20; j++) {
                    assert uf.connected(i, j) == (component[i] == component[j]);
                }
            }
        }
        assert new UnionFind(0).count() == 0;
        expect(IllegalArgumentException.class, () -> new UnionFind(-1));
        expect(IllegalArgumentException.class, () -> uf.find(-1));
        expect(IllegalArgumentException.class, () -> uf.union(0, 20));
        expect(IllegalArgumentException.class, () -> uf.connected(20, 0));
    }

    private static void expect(Class<? extends RuntimeException> type, Runnable operation) {
        try {
            operation.run();
        } catch (RuntimeException exception) {
            assert type.isInstance(exception) : exception;
            return;
        }
        throw new AssertionError("Expected " + type.getSimpleName());
    }
}
