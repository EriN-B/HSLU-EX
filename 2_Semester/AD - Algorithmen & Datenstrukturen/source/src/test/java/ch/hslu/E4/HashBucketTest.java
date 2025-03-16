package ch.hslu.E4;

import static org.junit.jupiter.api.Assertions.*;

import ch.hslu.E4.HashBucket.HashBucket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HashBucketTest {
    private HashBucket<Integer> hashBucket;

    @BeforeEach
    public void setup() {
        hashBucket = new HashBucket<>(5);
    }

    @Test
    public void testInsertionAndRetrieval() {
        hashBucket.put(10);
        hashBucket.put(20);
        assertEquals(10, hashBucket.get(10));
        assertEquals(20, hashBucket.get(20));
    }

    @Test
    public void testContains() {
        hashBucket.put(30);
        assertTrue(hashBucket.contains(30));
        assertFalse(hashBucket.contains(40));
    }

    @Test
    public void testRemove() {
        hashBucket.put(50);
        assertTrue(hashBucket.contains(50));
        assertEquals(50, hashBucket.remove(50));
        assertFalse(hashBucket.contains(50));
    }

    @Test
    public void testRemoveNonExistent() {
        assertNull(hashBucket.remove(99));
    }

    @Test
    public void testSize() {
        hashBucket.put(1);
        hashBucket.put(2);
        hashBucket.put(3);
        assertEquals(3, hashBucket.size());
        hashBucket.remove(2);
        assertEquals(2, hashBucket.size());
    }

    @Test
    public void testEmptyAndFull() {
        assertTrue(hashBucket.isEmpty());
        hashBucket.put(5);
        assertFalse(hashBucket.isEmpty());
    }

    @Test
    public void testBucketCollisions() {
        hashBucket.put(1);
        hashBucket.put(6);
        assertTrue(hashBucket.contains(1));
        assertTrue(hashBucket.contains(6));
    }

    @Test
    public void testBucketStringRepresentation() {
        hashBucket.put(2);
        hashBucket.put(7);
        String output = hashBucket.toString();
        assertTrue(output.contains("2"));
        assertTrue(output.contains("7"));
    }
}
