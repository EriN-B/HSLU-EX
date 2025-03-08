package ch.hslu.E2;

public class Node<T> {
    private Node<T> prev;
    private Node<T> next;
    private T value;

    @Override
    public int hashCode(){
        return value.hashCode();
    }

    public Node(final T value){
        this.value = value;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
