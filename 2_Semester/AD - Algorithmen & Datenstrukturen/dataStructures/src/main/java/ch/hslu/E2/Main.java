package ch.hslu.E2;


public class Main {
    public static void main(String[] args) {
        ArrayStack<String> stack = new ArrayStack<>(3);
        stack.push("a");
        stack.push("b");
        stack.push("c");

        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}
