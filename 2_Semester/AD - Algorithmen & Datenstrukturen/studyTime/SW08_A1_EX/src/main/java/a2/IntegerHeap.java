package a2;

public interface IntegerHeap {

    public int extractMax();

    public void insert(int value) throws Exception;

    public int size();

    public int peekMax();

    public int capacity();

    public boolean isEmpty();
}
