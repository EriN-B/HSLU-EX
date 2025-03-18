package ch.hslu.E1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FibTest {

    @Test
    public void testFiboRec1Base() {
        assertEquals(1, Fib.fiboRect1(1));
    }

    @Test
    public void testFiboRec1() {
        assertEquals( 55, Fib.fiboRect1(10));
    }

    @Test
    public void testFiboRec2Basis() {
        assertEquals(1, Fib.fiboRec2(1));
        assertEquals(55, Fib.fiboRec2(10));
        assertEquals(6765, Fib.fiboRec2(20));
    }
}
