package org.tree.implementation;

import org.tree.implementation.avl.AVLTree;
import org.tree.implementation.redblack.RedBlackTree;
import org.tree.implementation.redblack.Tree;

public class Main {
    public static void main(String[] args) {

        var redBlackTree = getRedBlackTree();

        System.out.println("ÁRVORE RED-BLACK");
        redBlackTree.printTree();

        var BBST = getAvlTree();

        System.out.println("ÁRVORE AVL");
        BBST.printTree();
    }

    private static AVLTree<String, Integer> getAvlTree() {
        AVLTree<String, Integer> BBST = new AVLTree<>();
        BBST.put("A", 1);
        BBST.put("B", 2);
        BBST.put("C", 3);
        BBST.put("D", 4);
        BBST.put("E", 5);
        BBST.put("F", 6);
        BBST.put("G", 7);
        BBST.put("H", 8);
        BBST.put("I", 9);
        BBST.put("J", 10);
        BBST.put("K", 11);
        BBST.put("L", 12);
        return BBST;
    }

    private static Tree<String> getRedBlackTree(){
        Tree<String> redBlackTree = new RedBlackTree<>();
        redBlackTree
                .insert("A")
                .insert("B")
                .insert("C")
                .insert("D")
                .insert("E")
                .insert("F")
                .insert("G")
                .insert("H")
                .insert("I")
                .insert("J")
                .insert("K")
                .insert("L");
        return redBlackTree;
    }

}
