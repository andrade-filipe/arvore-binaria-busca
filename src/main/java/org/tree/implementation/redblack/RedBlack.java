package org.tree.implementation.redblack;

import org.tree.implementation.adapter.Tree;

import java.util.ArrayList;

import static org.tree.implementation.redblack.Color.BLACK;
import static org.tree.implementation.redblack.Color.RED;

public class RedBlack<T extends Comparable<T>> implements Tree<T> {
    private Node<T> root;
    private final ArrayList<T> LRN_POS_ORDEM = new ArrayList<>();
    private final ArrayList<T> NLR_PRE_ORDEM = new ArrayList<>();
    private final ArrayList<T> LNR_IN_ORDEM = new ArrayList<>();

    @Override
    public Tree<T> insert(T data) {
        Node<T> node = new Node<>(data);
        root = insert(root, node);
        recolorAndRotate(node);
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
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void printTree() {
        System.out.println(treeStringBuilder(root));
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


    private Node<T> insert(Node<T> node, Node<T> nodeToInsert) {
        if (node == null) {
            return nodeToInsert;
        }
        if (nodeToInsert.getData().compareTo(node.getData()) < 0) {
            node.setLeftChild(insert(node.getLeftChild(), nodeToInsert));
            node.getLeftChild().setParent(node);
        } else if (nodeToInsert.getData().compareTo(node.getData()) > 0) {
            node.setRightChild(insert(node.getRightChild(), nodeToInsert));
            node.getRightChild().setParent(node);
        }
        return node;
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

    private Node<T> delete(T data, Node<T> node) throws RuntimeException {
        //TODO:Método de deletar nós na árvore red-black
        return new Node<T>((T)"Method not implemented");
    }

    private void recolorAndRotate(Node<T> node) {
        Node<T> parent = node.getParent();
        if (node != root && parent.getColor() == RED) {
            Node<T> grandParent = node.getParent().getParent();
            Node<T> uncle = parent.isLeftChild() ?
                grandParent.getRightChild() : grandParent.getLeftChild();
            if (uncle != null && uncle.getColor() == RED) { // Recoloring
                handleRecoloring(parent, uncle, grandParent);
            } else if (parent.isLeftChild()) { // Left-Left or Left-Right situation
                handleLeftSituations(node, parent, grandParent);
            } else if (!parent.isLeftChild()) { // Right-Right or Right-Left situation
                handleRightSituations(node, parent, grandParent);
            }
        }
        root.setColor(BLACK); // Color the root node black
    }

    private void handleRightSituations(Node<T> node, Node<T> parent, Node<T> grandParent) {
        if (node.isLeftChild()) {
            rotateRight(parent);
        }
        parent.flipColor();
        grandParent.flipColor();
        rotateLeft(grandParent);
        recolorAndRotate(node.isLeftChild() ? grandParent : parent);
    }

    private void handleLeftSituations(Node<T> node, Node<T> parent, Node<T> grandParent) {
        if (!node.isLeftChild()) {
            rotateLeft(parent);
        }
        parent.flipColor();
        grandParent.flipColor();
        rotateRight(grandParent);
        recolorAndRotate(node.isLeftChild() ? parent : grandParent);
    }

    private void handleRecoloring(Node<T> parent, Node<T> uncle, Node<T> grandParent) {
        uncle.flipColor();
        parent.flipColor();
        grandParent.flipColor();
        recolorAndRotate(grandParent);
    }

    private void rotateRight(Node<T> node) {
        Node<T> leftNode = node.getLeftChild();
        node.setLeftChild(leftNode.getRightChild());
        if (node.getLeftChild() != null) {
            node.getLeftChild().setParent(node);
        }
        leftNode.setRightChild(node);
        leftNode.setParent(node.getParent());
        updateChildrenOfParentNode(node, leftNode);
        node.setParent(leftNode);
    }

    private void rotateLeft(Node<T> node) {
        Node<T> rightNode = node.getRightChild();
        node.setRightChild(rightNode.getLeftChild());
        if (node.getRightChild() != null) {
            node.getRightChild().setParent(node);
        }
        rightNode.setLeftChild(node);
        rightNode.setParent(node.getParent());
        updateChildrenOfParentNode(node, rightNode);
        node.setParent(rightNode);
    }

    private void updateChildrenOfParentNode(Node<T> node, Node<T> tempNode) {
        if (node.getParent() == null) {
            root = tempNode;
        } else if (node.isLeftChild()) {
            node.getParent().setLeftChild(tempNode);
        } else {
            node.getParent().setRightChild(tempNode);
        }
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


    private String treeStringBuilder(Node<T> root) {
        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(root.getData());
        sb.append("[");
        sb.append(root.getColor());
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
            sb.append(node.getColor());
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
