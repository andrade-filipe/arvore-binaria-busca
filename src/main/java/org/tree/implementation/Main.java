package org.tree.implementation;

import org.tree.implementation.adapter.Tree;
import org.tree.implementation.avl.AVL;
import org.tree.implementation.bst.BinarySearchTree;
import org.tree.implementation.redblack.RedBlack;

public class Main {
    public static void main(String[] args) {
        System.out.println("ÁRVORE BST NORMAL");
        var bst = getBinarySearchTree();

        bst.printTree();
        bst.traverse();

        System.out.println("****************************");
        System.out.println("ÁRVORE AVL");
        var avl = getAvlTree();

        avl.printTree();
        avl.traverse();

        System.out.println("****************************");
        System.out.println("ÁRVORE RED-BLACK");
        var redBlackTree = getRedBlackTree();

        redBlackTree.printTree();
        redBlackTree.traverse();
    }

    private static Tree<String> getAvlTree() {
        Tree<String> avlTree = new AVL<>();
        avlTree
            .insert("E")
            .insert("F")
            .insert("G")
            .insert("A")
            .insert("B")
            .insert("C")
            .insert("D");
        return avlTree;
    }

    private static Tree<String> getRedBlackTree() {
        Tree<String> redBlackTree = new RedBlack<>();
        redBlackTree
            .insert("E")
            .insert("F")
            .insert("G")
            .insert("A")
            .insert("B")
            .insert("C")
            .insert("D");
        return redBlackTree;
    }

    private static Tree<String> getBinarySearchTree() {
        Tree<String> binarySearchTree = new BinarySearchTree<>();

        binarySearchTree
            .insert("E")
            .insert("F")
            .insert("G")
            .insert("A")
            .insert("B")
            .insert("C")
            .insert("D");
        return binarySearchTree;
    }
}
