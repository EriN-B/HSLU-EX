package ch.hslu.E2;


public class LinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public int getSize(){
        return size;
    }

    public Node<T> getHead(){
        return head;
    }

    public Node<T> getTail(){
        return tail;
    }

    public void push(T value){
        if(value == null){
            return;
        }

        Node<T> node = new Node<>(value);

        if(head == null){
            head = tail = node;
        }else{
            node.setNext(head);
            head.setPrev(node);
            node.setPrev(null);
            head = node;
        }
        size++;
    }

    public T pop(){
        T res = head.getValue();
        remove(res);
        return res;
    }

    public void remove(T value){
        if(head == null){
            return;
        }

        if(head.getValue().equals(value)){
            removeHead();
            size--;
            return;
        }else if(tail.getValue().equals(value)){
            removeTail();
            size--;
            return;
        }

        Node<T> node = findNodeByValue(value, head);

        if(node == null){
            return;
        }

        Node<T> prev = node.getPrev();
        Node<T> next = node.getNext();

        prev.setNext(next);
        next.setPrev(prev);
        size--;
    }

    public boolean contains(T value){
        return findNodeByValue(value, head) != null;
    }

    /**
     *
     * @param value - value to be searched for
     * @param node - starting node traversing search
     * @return - Node with the value searched for
     */
    private Node<T> findNodeByValue(T value, Node<T> node){
        if(node.getValue().equals(value)){
            return node;
        }

        if(node.getNext() == null){
            return null;
        }

        return findNodeByValue(value, node.getNext());
    }

    private void removeHead(){
        head = head.getNext();

        if (head != null) {
            head.setPrev(null);
        } else {
            tail = null;
        }
    }

    private void removeTail(){
        tail = tail.getPrev();

        if (tail != null) {
            tail.setNext(null);
        } else {
            head = null;
        }
    }
}
