package dataStructure;

import java.util.Random;

import dataStructure.AVL.AVL;

public class Main {
    public static void main(String[] args) {

        AVL<Integer, String> a = new AVL<Integer, String>();

        Random random = new Random();
        for(int i = 0; i < 10; i++) {
            a.insert(random.nextInt(51), "TXT");
        }

        if (args.length > 0 && args[0].equals("--tree")) a.printTree();

    }
}
