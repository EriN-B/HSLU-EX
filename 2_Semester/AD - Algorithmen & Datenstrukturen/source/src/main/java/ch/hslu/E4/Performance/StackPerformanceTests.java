package ch.hslu.E4.Performance;

import ch.hslu.E2.ArrayStack;

import java.util.Stack;
import java.util.Deque;
import java.util.ArrayDeque;

public class StackPerformanceTests {

    public static long javaStack(String[] testData) {
        Stack<String> stack = new Stack<>();
        return PerformanceUtils.measureTime(() -> {
            for (String item : testData) {
                stack.push(item);
            }
        });
    }

    public static long customStack(String[] testData) {
        ArrayStack<String> stack = new ArrayStack<>(testData.length);
        return PerformanceUtils.measureTime(() -> {
            for (String item : testData) {
                stack.push(item);
            }
        });
    }

    public static long arrayDeque(String[] testData) {
        Deque<String> stack = new ArrayDeque<>(testData.length);
        return PerformanceUtils.measureTime(() -> {
            for (String item : testData) {
                stack.push(item);
            }
        });
    }
}
