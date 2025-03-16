package ch.hslu.E4;

public interface HashTable<T> {

    public void put(T element);

    public T get(T element);

    public T remove(T element);

    public boolean contains(T element);

    public int size();

    public boolean isEmpty();
}
