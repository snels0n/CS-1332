import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * This is a more comprehensive set of tests that supplements DequeStudentTest.
 *
 * @author Alexander Gualino
 * @version 1.0
 */
public class ExhaustiveTests {
    private static final int TIMEOUT = 200;

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testArrayDequeAddFirstNullData() {
        new ArrayDeque<>().addFirst(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testArrayDequeAddLastNullData() {
        new ArrayDeque<>().addLast(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayDequeRemoveFirstEmptyList() {
        new ArrayDeque<>().removeFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayDequeRemoveLastEmptyList() {
        new ArrayDeque<>().removeLast();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayDequeGetFirstEmptyList() {
        new ArrayDeque<>().getFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayDequeGetLastEmptyList() {
        new ArrayDeque<>().getLast();
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeResizing() {
        var q = new ArrayDeque<>();
        var p = new ArrayDeque<>();

        for (int i = 0; i < ArrayDeque.INITIAL_CAPACITY; i++) {
            q.addLast(i);
            p.addFirst(i);
        }

        assertEquals(ArrayDeque.INITIAL_CAPACITY, q.getBackingArray().length);
        assertEquals(ArrayDeque.INITIAL_CAPACITY, p.getBackingArray().length);
        q.addLast(ArrayDeque.INITIAL_CAPACITY);
        p.addFirst(ArrayDeque.INITIAL_CAPACITY);
        assertEquals(2 * ArrayDeque.INITIAL_CAPACITY, q.getBackingArray().length);
        assertEquals(2 * ArrayDeque.INITIAL_CAPACITY, p.getBackingArray().length);

        for (int i = 0; i <= ArrayDeque.INITIAL_CAPACITY; i++) {
            // FIFO ordering
            assertEquals(i, q.getFirst());
            assertEquals(i, p.getLast());
            assertEquals(i, q.removeFirst());
            assertEquals(i, p.removeLast());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeResizingZipped() {
        var q = new ArrayDeque<>();
        var p = new ArrayDeque<>();
        var flip = true;

        for (var i = 0; i < ArrayDeque.INITIAL_CAPACITY; i++) {
            if (flip) {
                q.addLast(i);
                p.addFirst(i);
            } else {
                q.addFirst(i);
                p.addLast(i);
            }

            flip = !flip;
        }

        assertEquals(ArrayDeque.INITIAL_CAPACITY, q.getBackingArray().length);
        assertEquals(ArrayDeque.INITIAL_CAPACITY, p.getBackingArray().length);
        if (flip) {
            q.addLast(ArrayDeque.INITIAL_CAPACITY);
            p.addFirst(ArrayDeque.INITIAL_CAPACITY);
        } else {
            q.addFirst(ArrayDeque.INITIAL_CAPACITY);
            p.addLast(ArrayDeque.INITIAL_CAPACITY);
        }
        assertEquals(2 * ArrayDeque.INITIAL_CAPACITY, q.getBackingArray().length);
        assertEquals(2 * ArrayDeque.INITIAL_CAPACITY, p.getBackingArray().length);

        for (var i = ArrayDeque.INITIAL_CAPACITY; i >= 0; i--) {
            // LIFO ordering
            if (flip) {
                assertEquals(i, q.getLast());
                assertEquals(i, q.removeLast());
                assertEquals(i, p.getFirst());
                assertEquals(i, p.removeFirst());
            } else {
                assertEquals(i, q.getFirst());
                assertEquals(i, q.removeFirst());
                assertEquals(i, p.getLast());
                assertEquals(i, p.removeLast());
            }

            assertEquals(i, p.size());
            assertEquals(i, q.size());

            flip = !flip;
        }
    }

    // above tests, copy-pasted for LinkedDeque (TODO: better parametrization?)
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLinkedDequeAddFirstNullData() {
        new LinkedDeque<>().addFirst(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLinkedDequeAddLastNullData() {
        new LinkedDeque<>().addLast(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedDequeRemoveFirstEmptyList() {
        new LinkedDeque<>().removeFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedDequeRemoveLastEmptyList() {
        new LinkedDeque<>().removeLast();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedDequeGetFirstEmptyList() {
        new LinkedDeque<>().getFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedDequeGetLastEmptyList() {
        new LinkedDeque<>().getLast();
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeResizing() {
        var q = new LinkedDeque<>();
        var p = new LinkedDeque<>();

        for (int i = 0; i < ArrayDeque.INITIAL_CAPACITY; i++) {
            q.addLast(i);
            p.addFirst(i);
        }

        q.addLast(ArrayDeque.INITIAL_CAPACITY);
        p.addFirst(ArrayDeque.INITIAL_CAPACITY);

        for (int i = 0; i <= ArrayDeque.INITIAL_CAPACITY; i++) {
            // FIFO ordering
            assertEquals(i, q.getFirst());
            assertEquals(i, p.getLast());
            assertEquals(i, q.removeFirst());
            assertEquals(i, p.removeLast());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeResizingZipped() {
        var q = new LinkedDeque<>();
        var p = new LinkedDeque<>();
        var flip = true;

        for (var i = 0; i < ArrayDeque.INITIAL_CAPACITY; i++) {
            if (flip) {
                q.addLast(i);
                p.addFirst(i);
            } else {
                q.addFirst(i);
                p.addLast(i);
            }

            flip = !flip;
        }

        if (flip) {
            q.addLast(ArrayDeque.INITIAL_CAPACITY);
            p.addFirst(ArrayDeque.INITIAL_CAPACITY);
        } else {
            q.addFirst(ArrayDeque.INITIAL_CAPACITY);
            p.addLast(ArrayDeque.INITIAL_CAPACITY);
        }

        for (var i = ArrayDeque.INITIAL_CAPACITY; i >= 0; i--) {
            // LIFO ordering
            if (flip) {
                assertEquals(i, q.getLast());
                assertEquals(i, q.removeLast());
                assertEquals(i, p.getFirst());
                assertEquals(i, p.removeFirst());
            } else {
                assertEquals(i, q.getFirst());
                assertEquals(i, q.removeFirst());
                assertEquals(i, p.getLast());
                assertEquals(i, p.removeLast());
            }

            assertEquals(i, p.size());
            assertEquals(i, q.size());

            flip = !flip;
        }
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeEndsGetReset() {
        var q = new LinkedDeque<>();
        q.addFirst(1);
        q.removeFirst();
        assertNull(q.getHead());
        assertNull(q.getTail());

        q.addLast(1);
        q.removeLast();
        assertNull(q.getHead());
        assertNull(q.getTail());
    }
}
