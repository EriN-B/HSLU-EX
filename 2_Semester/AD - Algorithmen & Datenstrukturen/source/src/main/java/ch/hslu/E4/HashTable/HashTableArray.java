package ch.hslu.E4.HashTable;

public class HashTableArray<T> implements HashTable<T> {
    private T[] elements;
    private int size;
    private final int capacity;
    private int tombstoneCount;
    private final Object TOMBSTONE = new Object();

    @SuppressWarnings("unchecked")
    public HashTableArray(int capacity) {
        this.capacity = capacity;
        this.elements = (T[]) new Object[capacity];
        this.size = 0;
        this.tombstoneCount = 0;
    }

    @Override
    public void put(T element) {
        if (isFull()) {
            throw new IllegalStateException("HashTable is full");
        }

        if (contains(element)) {
            return;
        }

        int index = findInsertionIndex(element);
        if (index == -1) {
            throw new IllegalStateException("Element can not be inserted");
        }

        insertElement(index, element);
    }

    @Override
    public T get(T element) {
        int index = getIndex(element);
        return (index != -1) ? elements[index] : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T remove(T element) {
        int index = getIndex(element);
        if (index != -1) {
            T result = elements[index];
            elements[index] = (T) TOMBSTONE;
            size--;
            tombstoneCount++;
            return result;
        }
        return null;
    }

    @Override
    public boolean contains(T element) {
        return getIndex(element) != -1;
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
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (int i = 0; i < elements.length; i++) {
            str.append(elements[i] == null ? "null" : elements[i] == TOMBSTONE ? "<T>" : elements[i].toString());
            if (i < elements.length - 1) {
                str.append(", ");
            }
        }
        str.append("]");
        return str.toString();
    }

    public boolean isFull() {
        return size == capacity;
    }

    private int getAbsolutInsertionPoint(T element) {
        return Math.abs(element.hashCode()) % elements.length;
    }

    private int getIndex(T element) {
        int startIndex = getAbsolutInsertionPoint(element);
        for (int i = 0; i < capacity; i++) {
            int probeIndex = (startIndex + i) % capacity;
            if (elements[probeIndex] == null) {
                return -1;
            }
            if (elements[probeIndex] != TOMBSTONE && elements[probeIndex].equals(element)) {
                return probeIndex;
            }
        }
        return -1;
    }

    private int findInsertionIndex(T element) {
        int startIndex = getAbsolutInsertionPoint(element);

        for (int i = 0; i < capacity; i++) {
            int probeIndex = (startIndex + i) % capacity;
            if (elements[probeIndex] == null || elements[probeIndex] == TOMBSTONE) {
                return probeIndex;
            }
        }
        return -1;
    }

    private void insertElement(int index, T element) {
        if (elements[index] == TOMBSTONE) {
            tombstoneCount--;
        }

        elements[index] = element;
        size++;

        if (tombstoneCount > capacity / 4) {
            rehash();
        }
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        T[] oldElements = elements;
        elements = (T[]) new Object[capacity];
        size = 0;
        tombstoneCount = 0;

        for (T element : oldElements) {
            if (element != null && element != TOMBSTONE) {
                put(element);
            }
        }
    }
}
