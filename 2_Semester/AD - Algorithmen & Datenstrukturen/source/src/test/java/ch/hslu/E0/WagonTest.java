package ch.hslu.E0;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WagonTest {

    @Test
    public void testWagonId() {
        Wagon wagon = new Wagon(50, "S9");
        assertEquals(wagon.getId(), "S9");
    }

    @Test
    public void testWagonSeats() {
        Wagon wagon = new Wagon(50, "S9");
        assertEquals(wagon.getSeats(), 50);
    }

    @Test
    public void testSubsequentWagon() {
        Wagon wagon = new Wagon(50, "S9", new Wagon(100, "S10"));
        assertEquals(wagon.getSubsequentWagon().getId(), "S10");
    }

    @Test
    public void testSubsequentWagonNull() {
        Wagon wagon = new Wagon(50, "S9");
        assertNull(wagon.getSubsequentWagon());
    }

    @Test
    public void testSameWagons() {
        Wagon wagon1 = new Wagon(50, "S9");
        Wagon wagon2 = new Wagon(100, "S9");
        assertEquals(wagon1, wagon2);
        assertEquals(wagon1.hashCode(), wagon2.hashCode());
    }
}
