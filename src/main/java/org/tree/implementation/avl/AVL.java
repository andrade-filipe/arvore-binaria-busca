package org.tree.implementation.avl;

import org.tree.implementation.adapter.Tree;

import java.util.ArrayList;

public class AVL<T extends Comparable<T>> implements Tree<T> {
    private final ArrayList<T> LRN_POS_ORDEM = new ArrayList<>();
    private final ArrayList<T> NLR_PRE_ORDEM = new ArrayList<>();
    private final ArrayList<T> LNR_IN_ORDEM = new ArrayList<>();
    private Node<T> root;

    @Override
    public Tree<T> insert(T data) {
        root = insert(data, root);
        return this;
    }

    @Override
    public void delete(T data) {
        root = delete(data, root);
    }

    @Override
    public T get(T data) {
        return get(root, data);
    }

    @Override
    public void traverse() {
        nlr(root);
        System.out.println("Pré-Ordem(NLR): " + this.NLR_PRE_ORDEM);
        lnr(root);
        System.out.println("In-Ordem(LNR): " + this.LNR_IN_ORDEM);
        lrn(root);
        System.out.println("Pós-Ordem(LRN): " + this.LRN_POS_ORDEM);
    }

    @Override
    public void printTree() {
        System.out.println(treeStringBuilder(root));
    }

    @Override
    public T getMax() {
        if (isEmpty()) {
            return null;
        }
        return getMax(root);
    }

    @Override
    public T getMin() {
        if (isEmpty()) {
            return null;
        }
        return getMin(root);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    private Node<T> insert(T data, Node<T> node) {
        if (node == null) {
            return new Node<>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeftChild(insert(data, node.getLeftChild()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRightChild(insert(data, node.getRightChild()));
        } else {
            return node;
        }
        updateHeight(node);
        return applyRotation(node);
    }

    private T get(Node node, T data) {
        int compareKey = data.compareTo((T) node.getData());
        if (compareKey < 0) {
            return get(node.getLeftChild(), data);
        } else if (compareKey > 0) {
            return get(node.getRightChild(), data);
        } else {
            return (T) node.getData();
        }
    }

    private Node<T> delete(T data, Node<T> node) {
        if (node == null) {
            return null;
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeftChild(delete(data, node.getLeftChild()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRightChild(delete(data, node.getRightChild()));
        } else {
            // One Child or Leaf Node (no children)
            if (node.getLeftChild() == null) {
                return node.getRightChild();
            } else if (node.getRightChild() == null) {
                return node.getLeftChild();
            }
            // Two Children
            node.setData(getMax(node.getLeftChild()));
            node.setLeftChild(delete(node.getData(), node.getLeftChild()));
        }
        updateHeight(node);
        return applyRotation(node);
    }

    private void updateHeight(Node<T> node) {
        int maxHeight = Math.max(
            height(node.getLeftChild()),
            height(node.getRightChild())
        );
        node.setHeight(maxHeight + 1);
    }

    private Node<T> applyRotation(Node<T> node) {
        int balance = balance(node);
        if (balance > 1) {
            if (balance(node.getLeftChild()) < 0) {
                node.setLeftChild(rotateLeft(node.getLeftChild()));
            }
            return rotateRight(node);
        }
        if (balance < -1) {
            if (balance(node.getRightChild()) > 0) {
                node.setRightChild(rotateRight(node.getRightChild()));
            }
            return rotateLeft(node);
        }
        return node;
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> leftNode = node.getLeftChild();
        Node<T> centerNode = leftNode.getRightChild();
        leftNode.setRightChild(node);
        node.setLeftChild(centerNode);
        updateHeight(node);
        updateHeight(leftNode);
        return leftNode;
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> rightNode = node.getRightChild();
        Node<T> centerNode = rightNode.getLeftChild();
        rightNode.setLeftChild(node);
        node.setRightChild(centerNode);
        updateHeight(node);
        updateHeight(rightNode);
        return rightNode;
    }

    private int balance(Node<T> node) {
        return node != null ? height(node.getLeftChild()) - height(node.getRightChild()) : 0;
    }

    private int height(Node<T> node) {
        return node != null ? node.getHeight() : 0;
    }

    private T getMax(Node<T> node) {
        if (node.getRightChild() != null) {
            return getMax(node.getRightChild());
        }
        return node.getData();
    }

    private T getMin(Node<T> node) {
        if (node.getLeftChild() != null) {
            return getMin(node.getLeftChild());
        }
        return node.getData();
    }

    /*Imprimir Árvore*/
    private String treeStringBuilder(Node<T> root) {
        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(root.getData());
        sb.append("[");
        sb.append(balance(root));
        sb.append("]");

        String pointerRight = "└──";
        String pointerLeft = (root.getRightChild() != null) ? "├──" : "└──";

        treeStringBuilder(sb, "", pointerLeft, root.getLeftChild(), root.getRightChild() != null);
        treeStringBuilder(sb, "", pointerRight, root.getRightChild(), false);

        return sb.toString();
    }

    private void treeStringBuilder(StringBuilder sb,
                                   String padding,
                                   String pointer,
                                   Node<T> node,
                                   boolean hasRight) {
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.getData());
            sb.append("[");
            sb.append(balance(node));
            sb.append("]");

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRight) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerForRight = "└──";
            String pointerForLeft = (node.getRightChild() != null) ? "├──" : "└──";

            treeStringBuilder(sb, paddingForBoth, pointerForLeft, node.getLeftChild(), node.getRightChild() != null);
            treeStringBuilder(sb, paddingForBoth, pointerForRight, node.getRightChild(), false);
        }
    }

    /*TRAVESSIAS*/
    private void nlr(Node node) { //PRE-ORDEM
        if (node == null) {
            return;
        }

        this.NLR_PRE_ORDEM.add((T) node.getData());

        //left subtree
        nlr(node.getLeftChild());

        //right subtree
        nlr(node.getRightChild());

    }

    private void lrn(Node node) { //POS-ORDEM
        if (node == null) {
            return;
        }
        //left subtree
        lrn(node.getLeftChild());

        //right subtree
        lrn(node.getRightChild());

        this.LRN_POS_ORDEM.add((T) node.getData());
    }

    private void lnr(Node node) { //IN-ORDEM
        if (node == null) {
            return;
        }
        //left subtree
        lnr(node.getLeftChild());

        this.LNR_IN_ORDEM.add((T) node.getData());

        //right subtree
        lnr(node.getRightChild());
    }
}
