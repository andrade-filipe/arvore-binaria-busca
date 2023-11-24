package org.tree.implementation;

import org.tree.implementation.avl.AVL;
import org.tree.implementation.redblack.RedBlack;
import org.tree.implementation.adapter.Tree;

public class Main {
    public static void main(String[] args) {
        System.out.println("ÁRVORE RED-BLACK");
        var redBlackTree = getRedBlackTree();

        redBlackTree.printTree();
        redBlackTree.traverse();

        System.out.println("ÁRVORE AVL");
        var avl= getAvlTree();

        avl.printTree();
        avl.traverse();
    }

    private static Tree<String> getAvlTree(){
        Tree<String> avlTree = new AVL<>();
        avlTree
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
            .insert("L")
            .insert("M")
            .insert("N");
        return avlTree;
    }

    private static Tree<String> getRedBlackTree(){
        Tree<String> redBlackTree = new RedBlack<>();
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
                .insert("L")
                .insert("M")
                .insert("N");
        return redBlackTree;
    }
}
