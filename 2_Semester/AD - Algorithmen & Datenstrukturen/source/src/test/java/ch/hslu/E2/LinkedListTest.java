package ch.hslu.E2;

import ch.hslu.E0.Wagon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTest {

    @Test
    public void setup(){
        LinkedList<Integer> list = new LinkedList<>();;
        list.push(1);
        list.push(2);
        list.push(3);
        list.push(5);
        System.out.println("HEAD " + list.getHead());
        System.out.println("TAIL " + list.getTail());
    }

    @Test
    public void LinkedList(){
        LinkedList<Integer> list = new LinkedList<>();;
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test
    public void LinkedListPushFirst(){
        LinkedList<Integer> list = new LinkedList<>();;
        list.push(1);
        assertEquals(1, list.getHead().getValue());
        assertEquals(1, list.getTail().getValue());
    }

    @Test
    public void LinkedListPushValues(){
        LinkedList<Integer> list = new LinkedList<>();;
        list.push(1);
        list.push(2);
        list.push(3);
        list.push(5);
        assertEquals(5, list.getHead().getValue());
        assertEquals(1, list.getTail().getValue());
    }

    @Test
    public void LinkedListRemoveHeadValue(){
        LinkedList<Integer> list = new LinkedList<>();;
        list.push(1);
        list.push(2);
        list.push(3);
        list.push(5);

        list.remove(1);
        assertEquals(5, list.getHead().getValue());
    }

    @Test
    public void LinkedListRemoveTailValue(){
        LinkedList<Integer> list = new LinkedList<>();;
        list.push(1);
        list.push(2);
        list.push(3);
        list.push(5);

        list.remove(5);
        assertEquals(1, list.getTail().getValue());
        assertEquals(3, list.getSize());
    }

    @Test
    public void LinkedListRemoveValue(){
        LinkedList<Integer> list = new LinkedList<>();;
        list.push(1);
        list.push(2);
        list.push(3);
        list.push(5);

        assertEquals(4, list.getSize());
        list.remove(2);
        assertEquals(3, list.getHead().getNext().getValue());
        assertEquals(3, list.getSize());

    }

    @Test
    public void LinkedListEmpty(){
        LinkedList<Integer> list = new LinkedList<>();;
        assertEquals(0, list.getSize());
        list.remove(12);
        assertEquals(0, list.getSize());
    }

    @Test
    public void LinkedListEmptyAfterDeletion(){
        LinkedList<Integer> list = new LinkedList<>();;
        list.push(1);
        list.remove(1);
        assertEquals(0, list.getSize());
    }

    @Test
    public void LinkedListContainsWInteger(){
        LinkedList<Integer> list = new LinkedList<>();;
        list.push(1);
        list.push(2);
        list.push(3);

        assertTrue(list.contains(1));
        assertTrue(list.contains(2));
        assertTrue(list.contains(3));
        assertFalse(list.contains(4));
    }

    @Test
    public void LinkedListContainsWagon(){
        Wagon wg1 = new Wagon(1,"111111");
        Wagon wg2 = new Wagon(2,"222222");
        Wagon wg3 = new Wagon(3,"333333");
        Wagon wg4 = new Wagon(4,"444444");
        LinkedList<Wagon> list = new LinkedList<>();;
        list.push(wg1);
        list.push(wg2);
        list.push(wg3);

        assertTrue(list.contains(wg1));
        assertFalse(list.contains(wg4));
    }

    @Test
    public void LinkedListRemoveValueWagon(){
        Wagon wg1 = new Wagon(1,"111111");
        Wagon wg2 = new Wagon(2,"222222");
        Wagon wg3 = new Wagon(3,"333333");
        LinkedList<Wagon> list = new LinkedList<>();;
        list.push(wg1);
        list.push(wg2);
        list.push(wg3);

        assertEquals(3, list.getSize());
        list.remove(wg2);
        assertEquals(wg3, list.getHead().getValue());
        assertEquals(2, list.getSize());

    }

    @Test
    public void LinkedListPop(){
        Wagon wg1 = new Wagon(1,"111111");
        Wagon wg2 = new Wagon(2,"222222");
        Wagon wg3 = new Wagon(3,"333333");
        LinkedList<Wagon> list = new LinkedList<>();;
        list.push(wg1);
        list.push(wg2);
        list.push(wg3);

        assertEquals(3, list.getSize());
        assertEquals(wg3, list.pop());
        assertEquals(2, list.getSize());
        assertEquals(wg2, list.getHead().getValue());

    }
}
