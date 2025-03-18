package ch.hslu.self;

import java.util.List;

public class Node<T>{
    private Node<T> parent;
    private List<Node<T>> children;
    private T data;

    public Node(Node<T> parent, List<Node<T>> child){
        this.parent = parent;
        this.children = child;
    }

    public Node(T data){
        this.data = data;
    }

    public Node<T> getParent(){
        return parent;
    }

    public List<Node<T>> getChildren(){
        return children;
    }

    public void addChild(Node<T> child){
        this.children.add(child);
    }
}