package ch.hslu.E2;

import java.util.Iterator;

public class LinkedList<T> implements Iterable<T> {
    private Node<T> head;
    private int size;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public void add(T data) {
        if (!contains(data)) {
            Node<T> newNode = new Node<>(data);
            newNode.next = head;
            head = newNode;
            size++;
        }
    }

    public boolean contains(T data) {
        for (Node<T> current = head; current != null; current = current.next) {
            if (current.data.equals(data)) return true;
        }
        return false;
    }

    public T removeFirst() {
        if (head == null) throw new IllegalStateException("List is empty");
        T data = head.data;
        head = head.next;
        size--;
        return data;
    }

    public boolean remove(T data) {
        if (head == null) return false;
        if (head.data.equals(data)) {
            head = head.next;
            size--;
            return true;
        }
        for (Node<T> current = head; current.next != null; current = current.next) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> current = head;
            @Override public boolean hasNext() { return current != null; }
            @Override public T next() { T data = current.data; current = current.next; return data; }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Node<T> current = head; current != null; current = current.next) {
            sb.append(current.data).append(current.next != null ? " -> " : "");
        }
        return sb.append("]").toString();
    }

    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; this.next = null; }
    }
}
