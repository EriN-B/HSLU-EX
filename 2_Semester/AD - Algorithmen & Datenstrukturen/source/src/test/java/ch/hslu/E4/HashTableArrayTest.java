package ch.hslu.E4;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class HashTableArrayTest {

    @Test
    void testPutAndGet() {
        HashTableArray<String> hashTable = new HashTableArray<>();
        hashTable.put("Test");
        assertEquals("Test", hashTable.get("Test"));
    }

    @Test
    void testRemove() {
        HashTableArray<String> hashTable = new HashTableArray<>();
        hashTable.put("RemoveMe");
        assertEquals("RemoveMe", hashTable.remove("RemoveMe"));
        assertNull(hashTable.get("RemoveMe"));
    }

    @Test
    void testContains() {
        HashTableArray<String> hashTable = new HashTableArray<>();
        hashTable.put("Exists");
        assertTrue(hashTable.contains("Exists"));
        assertFalse(hashTable.contains("NotExists"));
    }

    @Test
    void testIsEmpty() {
        HashTableArray<String> hashTable = new HashTableArray<>();
        assertFalse(hashTable.isEmpty());
    }

    @Test
    void testToString() {
        HashTableArray<String> hashTable = new HashTableArray<>();
        hashTable.put("A");
        String result = hashTable.toString();
        assertTrue(result.contains("A"));
    }
}
