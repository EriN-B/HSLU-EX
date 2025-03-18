package ch.hslu.E2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListTest {

    @Test
    public void testAddFirstAndSize() {
        LinkedList<Integer> list = new LinkedList<>();
        assertEquals(0, list.size());
        list.add(10);
        assertEquals(1, list.size());
        list.add(20);
        assertEquals(2, list.size());
    }

    @Test
    public void testContains() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        assertTrue(list.contains(10));
        assertTrue(list.contains(20));
        assertFalse(list.contains(30));
    }

    @Test
    public void testRemoveFirst() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        assertEquals(20, list.removeFirst());
        assertEquals(1, list.size());
        assertEquals(10, list.removeFirst());
        assertEquals(0, list.size());
    }

    @Test
    public void testRemoveElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);

        assertTrue(list.remove(20));
        assertFalse(list.contains(20));
        assertEquals(2, list.size());

        assertFalse(list.remove(40));
    }
}
