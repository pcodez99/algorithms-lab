package dataStructure;

import dataStructure.BinarySearchTree.BST;

public class Main {
    public static void main(String[] args) {
        BST<Integer, String> a = new BST<Integer, String>();

        a.insert(3, "Tre");
        a.insert(2, "Due");
        a.insert(10, "Dieci");

        for(int i = 0; i < 10; a.insert(10+i++, "MAGGIORE"));

        System.out.println(a.getValue(10));
        System.out.println(a.getValue(3));
        System.out.println(a.getValue(2));

        System.out.println("Minimo: " + a.GetMin());

        a.deleteKey(2);

        System.out.println("Minimo: " + a.GetMin());

        System.out.println("Quanto valori più grandi: " + a.getHowMuchGreatestNode(10));
    }
}
