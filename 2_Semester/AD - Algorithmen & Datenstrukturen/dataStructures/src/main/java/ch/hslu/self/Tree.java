package ch.hslu.self;

public class Tree<T> {

    private Node<T> root;

    public Tree(T data) {
        root = new Node<T>(data);
    }

    public Node<T> getRoot() {
        return root;
    }
}
