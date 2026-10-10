package dataStructure;

import java.util.Random;

import dataStructure.RedBlabkBST.RedBlackBST;

public class Main {
    public static void main(String[] args) {

        RedBlackBST<Integer, String> a = new RedBlackBST<Integer, String>();

        Random random = new Random();
        for(int i = 0; i < 10000; i++) {
            a.insert(random.nextInt(1000001), "TXT");
        }

        a.printTree();

    }
}
