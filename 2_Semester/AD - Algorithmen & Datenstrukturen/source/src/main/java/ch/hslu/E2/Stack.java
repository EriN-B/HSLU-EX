package ch.hslu.E2;

public class Stack<T> implements StackArray<T>{
    private T[] stack;
    private int top;

    public Stack(int capacity){
        if(capacity <= 0){
            throw new IllegalArgumentException("Capacity must me greater than 0");
        }

        stack = (T[]) new Object[capacity];
        top = 0;
    }

    @Override
    public void push(T value) {
    }

    @Override
    public void pop() {
    }

    @Override
    public void size() {

    }
}
