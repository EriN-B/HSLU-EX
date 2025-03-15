package ch.hslu.E4;

public class HashTableArray<T> implements HashTable<T> {
    private T[] elements;

    public HashTableArray() {
        this.elements = (T[]) new Object[10];
    }

    @Override
    public void put(T element) {
        elements[getIndex(element)] = element;
    }

    @Override
    public T get(T element) {
        return elements[getIndex(element)];
    }

    @Override
    public T remove(T element) {
        int index = getIndex(element);
        T result = elements[index];
        elements[index] = null;
        return result;
    }

    @Override
    public boolean contains(T element) {
        return elements[getIndex(element)] != null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("[");
        for(int i = 0; i<elements.length; i++){
            str.append((elements[i] != null) ? elements[i].toString() : "null");
            str.append(",");
        }
        str.append("]");
        return str.toString();
    }

    private int getIndex(T element) {
        return Math.abs(element.hashCode()) % elements.length;
    }
}
