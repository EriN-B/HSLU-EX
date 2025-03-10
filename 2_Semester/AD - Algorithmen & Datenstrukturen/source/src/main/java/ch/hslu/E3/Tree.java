package ch.hslu.E3;

public class Tree<T extends Comparable<T>> implements BinarySearchTree<T>{
    private Node<T> root;
    private int size;

    @Override
    public void insert(T value) {

    }

    @Override
    public void remove(T value) {

    }

    @Override
    public void contains(T value) {

    }

    @Override
    public T min() {
        return null;
    }

    @Override
    public T max() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
