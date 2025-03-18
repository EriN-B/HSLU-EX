package ch.hslu.E2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayStackTest {

    @Test
    public void testStackIsEmptyOnInit() {
        ArrayStack<String> stack = new ArrayStack<>(5);
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    public void testPushAndSize() {
        ArrayStack<String> stack = new ArrayStack<>(5);
        stack.push("A");
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
    }

    @Test
    public void testPushAndPop() {
        ArrayStack<String> stack = new ArrayStack<>(5);
        stack.push("A");
        stack.push("B");
        assertEquals("B", stack.pop());
        assertEquals("A", stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testStackFullCondition() {
        ArrayStack<String> stack = new ArrayStack<>(2);
        stack.push("A");
        stack.push("B");
        assertTrue(stack.isFull());
        assertThrows(IllegalStateException.class, () -> stack.push("C"));
    }

    @Test
    public void testStackEmptyCondition() {
        ArrayStack<String> stack = new ArrayStack<>(2);
        assertThrows(IllegalStateException.class, stack::pop);
    }
}
