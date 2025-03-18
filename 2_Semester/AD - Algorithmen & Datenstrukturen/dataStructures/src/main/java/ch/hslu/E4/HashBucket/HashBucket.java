package ch.hslu.E4.HashBucket;

import ch.hslu.E2.LinkedList;
import ch.hslu.E4.HashTable;

public class HashBucket<T> implements HashTable<T> {
    private LinkedList<T>[] buckets;
    private final int capacity;
    private int size;

    public HashBucket(int capacity) {
        this.capacity = capacity;
        buckets = new LinkedList[capacity];
        size = 0;
        for(int i = 0; i < capacity; i++){
            buckets[i] = new LinkedList<>();
        }
    }

    @Override
    public void put(T element) {
        int index = getBucketIndex(element);

        if(!buckets[index].contains(element)){
            buckets[index].add(element);
            size++;
        }
    }

    @Override
    public T get(T element) {
        int index = getBucketIndex(element);

        for(T item : buckets[index]){
            if(item.equals(element)){
                return item;
            }
        }

        return null;
    }

    @Override
    public T remove(T element) {
        int index = getBucketIndex(element);

        if(buckets[index].remove(element)){
            size--;
            return element;
        };
        return null;
    }

    @Override
    public boolean contains(T element) {
        int index = getBucketIndex(element);

        return buckets[index].contains(element);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        for (int i = 0; i < buckets.length; i++) {
            str.append(i).append(": ").append(buckets[i].toString()).append("\n");
        }
        str.append("]");
        return str.toString();
    }

    private int getBucketIndex(T element) {
        return Math.abs(element.hashCode()) % capacity;
    }
}
