package ch.hslu.E3;

public class Node<T> {
    private Node<T> right;
    private Node <T> left;
    private T value;

    public Node(T value){
        this.value = value;
    }

    public Node<T> getRight(){
        return right;
    }

    public Node<T> getLeft(){
        return left;
    }

    public T getValue(){
        return value;
    }

    public void setRight(Node<T> node){
        this.right = node;
    }

    public void setLeft(Node<T> node){
        this.left = node;
    }

    public void setValue(T value){
        this.value = value;
    }
}
