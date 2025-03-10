package ch.hslu.E3;

public class Tree<T extends Comparable<T>> {
    private Node head;

    public Tree(T value) {
        this.head = new Node(value);
    }

    public void add(T value) {}
}
