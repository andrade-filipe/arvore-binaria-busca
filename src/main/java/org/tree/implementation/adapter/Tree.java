package org.tree.implementation.adapter;

public interface Tree<T extends Comparable<T>> {

    Tree<T> insert(T data);

    void delete(T data);

    T get(T data);

    void traverse();

    void printTree();

    T getMax();

    T getMin();

    boolean isEmpty();

}
