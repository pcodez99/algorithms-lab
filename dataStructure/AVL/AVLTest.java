package dataStructure.AVL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class AVLTest {
    public static void main(String[] args) {
        for (int[] keys : new int[][] {{30, 20, 10}, {10, 20, 30}, {30, 10, 20}, {10, 30, 20}}) {
            AVL<Integer, String> tree = new AVL<>();
            for (int key : keys) tree.insert(key, "value");
            assert tree.getSize(20) == 3;
            assert tree.getHeight(20) == 2;
            assert tree.getHeight(10) == 1;
            assert tree.getHeight(30) == 1;
        }

        int[][] deletionCases = {{4, 2, 6, 1, 3}, {2, 1, 4, 3, 5}, {5, 2, 7, 4}, {2, 1, 5, 3}};
        int[] removed = {6, 1, 7, 1};
        int[] newRoots = {2, 4, 4, 3};
        for (int i = 0; i < deletionCases.length; i++) {
            AVL<Integer, String> t = new AVL<>();
            for (int key : deletionCases[i]) t.insert(key, "value");
            t.deleteKey(removed[i]);
            assert t.getSize(newRoots[i]) == deletionCases[i].length - 1;
            for (int key : deletionCases[i]) {
                if (key != removed[i]) assert Math.abs(t.getBalanceFactor(key)) <= 1;
            }
        }

        AVL<Integer, String> successorCase = new AVL<>();
        for (int key : new int[] {8, 4, 12, 2, 6, 10, 14, 11}) successorCase.insert(key, "v" + key);
        successorCase.deleteKey(8);
        assert successorCase.getSize(10) == 7;
        assert successorCase.getValue(10).equals("v10");

        AVL<Integer, String> tree = new AVL<>();
        Set<Integer> keys = new TreeSet<>();
        Random random = new Random(1);
        for (int i = 0; i < 200; i++) {
            int key = random.nextInt(100);
            tree.insert(key, "v" + key);
            keys.add(key);
            int largestSubtree = 0;
            for (int inserted : keys) {
                assert Math.abs(tree.getBalanceFactor(inserted)) <= 1;
                largestSubtree = Math.max(largestSubtree, tree.getSize(inserted));
            }
            assert largestSubtree == keys.size();
        }

        ArrayList<Integer> deletionOrder = new ArrayList<>(keys);
        Collections.shuffle(deletionOrder, new Random(2));
        for (int removedKey : deletionOrder) {
            tree.deleteKey(removedKey);
            keys.remove(removedKey);
            int largestSubtree = 0;
            for (int remaining : keys) {
                assert tree.getValue(remaining).equals("v" + remaining);
                assert Math.abs(tree.getBalanceFactor(remaining)) <= 1;
                largestSubtree = Math.max(largestSubtree, tree.getSize(remaining));
            }
            assert largestSubtree == keys.size();
        }
    }
}
