package ch.hslu.E3;

import java.util.NoSuchElementException;

public class Tree<T extends Comparable<T>> implements BinarySearchTree<T> {

    @Override
    public String toString() {
        return buildString(root, 0);
    }

    private String buildString(Node<T> node, int level) {
        StringBuilder sb = new StringBuilder();
        if (node != null) {
            // Process right subtree first (it will appear at the top)
            sb.append(buildString(node.getRight(), level + 1));
            // Indentation for the current node
            for (int i = 0; i < level; i++) {
                sb.append("    ");
            }
            sb.append(node.getValue());
            sb.append("\n");
            // Process left subtree
            sb.append(buildString(node.getLeft(), level + 1));
        }
        return sb.toString();
    }

    private Node<T> root;
    private int size;

    public Tree(T value) {
        root = new Node<>(value);
        size++;
    }

    @Override
    public void insert(T value) {
        if(value == null){
            throw new IllegalArgumentException("Value should not be null");
        }

        insert(value, root);
    }

    @Override
    public void remove(T value) {
        if(value == null){
            throw new IllegalArgumentException("Value should not be null");
        }
    }

    private void remove(T value, Node<T> node){
        if(node == null){
            return;
        }

        
    }

    @Override
    public void search(T value) {
    }

    @Override
    public T min() {
        if (root == null) {
            throw new NoSuchElementException("Tree is empty");
        }
        Node<T> current = root;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current.getValue();
    }

    @Override
    public T max() {
        if (root == null) {
            throw new NoSuchElementException("Tree is empty");
        }
        Node<T> current = root;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current.getValue();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Node<T> root() {
        return root;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private void insert(T value, Node<T> node) {
        if (value.compareTo(node.getValue()) == 0) {
            return;
        }

        if (value.compareTo(node.getValue()) < 0) {
            if (node.getLeft() == null) {
                insertLeft(value, node);
                return;
            }
            insert(value, node.getLeft());
        }

        if (value.compareTo(node.getValue()) > 0) {
            if (node.getRight() == null) {
                insertRight(value, node);
                return;
            }
            insert(value, node.getRight());
        }
    }

    private void insertLeft(T value, Node<T> node) {
        node.setLeft(new Node<>(value));
        size++;
    }

    private void insertRight(T value, Node<T> node) {
        node.setRight(new Node<>(value));
        size++;
    }
}
