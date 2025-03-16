package ch.hslu.E4;

import static org.junit.jupiter.api.Assertions.*;

import ch.hslu.E4.HashTable.HashTableArray;
import org.junit.jupiter.api.Test;

public class HashTableArrayTest {

    @Test
    public void testInsertionAndRetrieval() {
        HashTableArray<Integer> table = new HashTableArray<>(10);
        table.put(42);
        assertEquals(42, table.get(42));
    }

    @Test
    public void testSizeAndRemove() {
        HashTableArray<String> table = new HashTableArray<>(10);
        table.put("Hello");
        table.put("World");
        assertEquals(2, table.size());

        table.remove("Hello");
        assertFalse(table.contains("Hello"));
        assertEquals(1, table.size());
    }

    @Test
    public void testCollisionHandling() {
        HashTableArray<Integer> table = new HashTableArray<>(10);
        int num1 = 10;
        int num2 = 20;
        table.put(num1);
        table.put(num2);

        assertEquals(num1, table.get(num1));
        assertEquals(num2, table.get(num2));
    }

    @Test
    public void testIsFull() {
        HashTableArray<Integer> table = new HashTableArray<>(3);
        table.put(1);
        table.put(2);
        table.put(3);
        assertTrue(table.isFull());
    }

    @Test
    public void testFullTablePreventsInsertion() {
        HashTableArray<Integer> table = new HashTableArray<>(2);
        table.put(1);
        table.put(2);
        assertThrows(IllegalStateException.class, () -> table.put(3));
    }

    @Test
    public void testTombstoneEffect() {
        HashTableArray<Integer> table = new HashTableArray<>(5);
        table.put(1);
        table.put(2);
        table.put(3);
        table.remove(2);

        assertNull(table.get(2));
        table.put(4);
        assertEquals(4, table.get(4));
        System.out.println(table);
    }
}
