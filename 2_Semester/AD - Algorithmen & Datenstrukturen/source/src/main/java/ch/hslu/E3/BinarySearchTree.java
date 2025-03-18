package ch.hslu.E3;

public interface BinarySearchTree<T extends Comparable<T>> {

    public void insert(T value);

    public void remove(T value);

    public void search(T value);

    public T min();

    public T max();

    public int size();

    public Node<T> root();

    public boolean isEmpty();
}
