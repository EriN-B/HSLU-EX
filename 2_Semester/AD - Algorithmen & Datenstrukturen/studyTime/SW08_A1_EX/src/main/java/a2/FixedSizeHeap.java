package a2;

import java.util.Arrays;

public class FixedSizeHeap implements IntegerHeap {

    private int insertionIndex;
    private int[] elements;

    public FixedSizeHeap(final int size) {
        elements = new int[size];
    }

    public FixedSizeHeap(int[] elements){

    }

    /**
     * Sorts the heap
     */
    public void sort(){
        int originalSize = insertionIndex;

        for (int i = originalSize - 1; i > 0; i--) {
            int max = extractMax();
            elements[i] = max;
        }

        insertionIndex = originalSize;
        System.out.println(Arrays.toString(elements));
    }


    @Override
    public int extractMax() {
        int max = elements[0];
        insertionIndex--;

        elements[0] = elements[insertionIndex];
        elements[insertionIndex] = 0;

        int currentIndex = 0;

        while (true) {

            int leftNode = (currentIndex * 2) + 1;
            int rightNode = (currentIndex * 2) + 2;
            int nextNode = currentIndex;

            if(leftNode < insertionIndex && elements[leftNode] > elements[currentIndex]) nextNode = leftNode;
            if(rightNode < insertionIndex && elements[rightNode] > elements[currentIndex]) nextNode = rightNode;

            if(nextNode == currentIndex) break;

            int temp = elements[currentIndex];
            elements[currentIndex] = elements[nextNode];
            elements[nextNode] = temp;

            currentIndex = nextNode;
        }

        System.out.println(Arrays.toString(elements));
        return max;
    }

    @Override
    public void insert(int value) throws IllegalStateException {
        if (insertionIndex >= elements.length) {
            throw new IllegalStateException("heap is full");
        }

        elements[insertionIndex] = value;

        int currentIndex = insertionIndex;
        int parent = (currentIndex - 1) / 2;

        while (currentIndex > 0 && elements[parent] < elements[currentIndex]) {
            int temp = elements[currentIndex];
            elements[currentIndex] = elements[parent];
            elements[parent] = temp;

            currentIndex = parent;
            parent = (currentIndex - 1) / 2;
        }


        insertionIndex++;
    }

    @Override
    public int size() {
        return insertionIndex;
    }

    @Override
    public int peekMax() {
        return 0;
    }

    @Override
    public int capacity() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }
}
