import org.junit.Test;

import static org.junit.Assert.*;
import java.util.NoSuchElementException;

public class DequeTest {
    private static final int TIMEOUT = 200;

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddFirstNullArray() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddLastNullArray() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFirstEmptyDequeArray() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.removeFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveLastEmptyDequeArray() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.removeLast();
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddFirstNullLinked() {
        LinkedDeque<Integer> deque = new LinkedDeque<>();
        deque.addFirst(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddLastNullLinked() {
        LinkedDeque<Integer> deque = new LinkedDeque<>();
        deque.addLast(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFirstEmptyDequeLinked() {
        LinkedDeque<Integer> deque = new LinkedDeque<>();
        deque.removeFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveLastEmptyDequeLinked() {
        LinkedDeque<Integer> deque = new LinkedDeque<>();
        deque.removeLast();
    }

    @Test(timeout = TIMEOUT)
    public void testAddFirstAndRemoveFirstArray() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        deque.addFirst(1);
        deque.addFirst(2);

        assertEquals(2, (int) deque.getFirst());
        assertEquals(1, (int) deque.getLast());
        assertEquals(2, deque.size());

        assertEquals(2, (int) deque.removeFirst());
        assertEquals(1, (int) deque.removeFirst());

        assertTrue(deque.size() == 0);
    }

    @Test(timeout = TIMEOUT)
    public void testAddLastAndRemoveLastArray() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        deque.addLast(1);
        deque.addLast(2);

        assertEquals(1, (int) deque.getFirst());
        assertEquals(2, (int) deque.getLast());
        assertEquals(2, deque.size());

        assertEquals(2, (int) deque.removeLast());
        assertEquals(1, (int) deque.removeLast());

        assertTrue(deque.size() == 0);
    }

    @Test(timeout = TIMEOUT)
    public void testAddFirstAndRemoveFirstLinked() {
        LinkedDeque<Integer> deque = new LinkedDeque<>();

        deque.addFirst(1);
        deque.addFirst(2);

        assertEquals(2, (int) deque.getFirst());
        assertEquals(1, (int) deque.getLast());
        assertEquals(2, deque.size());

        assertEquals(2, (int) deque.removeFirst());
        assertEquals(1, (int) deque.removeFirst());

        assertTrue(deque.size() == 0);
    }

    @Test(timeout = TIMEOUT)
    public void testAddLastAndRemoveLastLinked() {
        LinkedDeque<Integer> deque = new LinkedDeque<>();

        deque.addLast(1);
        deque.addLast(2);

        assertEquals(1, (int) deque.getFirst());
        assertEquals(2, (int) deque.getLast());
        assertEquals(2, deque.size());

        assertEquals(2, (int) deque.removeLast());
        assertEquals(1, (int) deque.removeLast());

        assertTrue(deque.size() == 0);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetFirstAndLastLinked() {
        LinkedDeque<Integer> deque = new LinkedDeque<>();

        deque.addFirst(1);
        deque.addLast(2);
        assertEquals(1, (int) deque.getFirst());
        assertEquals(2, (int) deque.getLast());

        deque.addFirst(3);
        deque.addLast(4);
        assertEquals(3, (int) deque.getFirst());
        assertEquals(4, (int) deque.getLast());

        deque.removeFirst();
        deque.removeLast();
        assertEquals(1, (int) deque.getFirst());
        assertEquals(2, (int) deque.getLast());

        deque.removeFirst();
        deque.removeLast();
        assertTrue(deque.size() == 0);

        deque.getFirst();
    }

    @Test(timeout = TIMEOUT)
    public void testResizeAndWrapArray() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < 15; i++) {
            deque.addLast(i);
        }

        assertEquals(15, deque.size());
        assertEquals(0, (int) deque.getFirst());
        assertEquals(14, (int) deque.getLast());
        for (int i = 0; i < 10; i++) {
            deque.removeFirst();
        }
        assertEquals(10, (int) deque.getFirst());
        for (int i = 0; i < 2; i++) {
            deque.removeLast();
        }
        assertEquals(12, (int) deque.getLast());
        for (int i = 20; i < 50; i++) {
            deque.addLast(i);
        }
        assertEquals(49, (int) deque.getLast());
        assertEquals(10, (int) deque.getFirst());
        assertEquals(deque.size(), 33);
    }

}