package ch.hslu.E2;

/**
 * A generic stack, implemented using arrays
 */
public class ArrayStack<T> implements StackInterface<T> {
    private T[] stack;
    private int top;
    private int capacity;

    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        this.capacity = capacity;
        this.stack = (T[]) new Object[capacity];
        this.top = -1;
    }

    @Override
    public void push(T item) {
        if (isFull()) {
            throw new IllegalStateException("Stack is full");
        }
        stack[++top] = item;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack[top--];
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack[top];
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    @Override
    public boolean isFull() {
        return top == capacity - 1;
    }

    @Override
    public int size() {
        return top + 1;
    }
}
