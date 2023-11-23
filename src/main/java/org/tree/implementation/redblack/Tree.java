package org.tree.implementation.redblack;

public interface Tree<T extends Comparable<T>> {

    Tree<T> insert(T data);

    void traverse();

    void printTree();

    T getMax();

    T getMin();

    boolean isEmpty();

}
