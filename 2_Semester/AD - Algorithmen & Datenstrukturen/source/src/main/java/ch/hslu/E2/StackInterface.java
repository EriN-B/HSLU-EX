package ch.hslu.E2;

/**
 * A generic interface for a stack
 * @param <T> Type of stack
 */
public interface StackInterface<T> {
    void push(T item);
    T pop();
    T peek();
    boolean isEmpty();
    boolean isFull();
    int size();
}
