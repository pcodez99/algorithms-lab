package hashing;

import edu.princeton.cs.algs4.SequentialSearchST;

public class SeparateChainingHashST<K, V> {
    private int N, M;
    private SequentialSearchST<K, V>[]st;

    public SeparateChainingHashST(int M) {
        this.M = M;
        st = (SequentialSearchST<K, V> []) new SequentialSearchST[M];
        for(int i = 0; i < M; i++) {
            st[i] = new SequentialSearchST<>();
        }
    }

    private int hash(K k){
        return (k.hashCode() & 0x7fffffff) % M;
    }

    public V getValue(K k) {
        return st[hash(k)].get(k);
    }

    public void put(K key, V val) { 
        st[hash(key)].put(key, val); 
    }
}
