package ch.hslu.E3;

import org.junit.Test;
import static org.junit.Assert.*;

public class TreeTest {

    @Test
    public void testNodePositionsAfterInsert() {
        Tree<Integer> tree = new Tree<>(7);

        tree.insert(3);
        tree.insert(9);
        tree.insert(2);
        tree.insert(5);
        tree.insert(8);
        tree.insert(10);

        Node<Integer> root = tree.root();
        assertNotNull("Root should not be null", root);
        assertEquals("Root value should be 7", Integer.valueOf(7), root.getValue());

        Node<Integer> left = root.getLeft();
        Node<Integer> right = root.getRight();
        assertNotNull("Left child of root should not be null", left);
        assertNotNull("Right child of root should not be null", right);
        assertEquals("Left child of root should be 3", Integer.valueOf(3), left.getValue());
        assertEquals("Right child of root should be 9", Integer.valueOf(9), right.getValue());

        Node<Integer> leftLeft = left.getLeft();
        Node<Integer> leftRight = left.getRight();
        assertNotNull("Left child of node 3 should not be null", leftLeft);
        assertNotNull("Right child of node 3 should not be null", leftRight);
        assertEquals("Left child of node 3 should be 2", Integer.valueOf(2), leftLeft.getValue());
        assertEquals("Right child of node 3 should be 5", Integer.valueOf(5), leftRight.getValue());

        Node<Integer> rightLeft = right.getLeft();
        Node<Integer> rightRight = right.getRight();
        assertNotNull("Left child of node 9 should not be null", rightLeft);
        assertNotNull("Right child of node 9 should not be null", rightRight);
        assertEquals("Left child of node 9 should be 8", Integer.valueOf(8), rightLeft.getValue());
        assertEquals("Right child of node 9 should be 10", Integer.valueOf(10), rightRight.getValue());
    }
}
