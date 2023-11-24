import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.NoSuchElementException;

public class DoublyLinkedListTest {
    private static final int TIMEOUT = 200;
    private DoublyLinkedList<Integer> list;

    @Before
    public void setUp() {
        list = new DoublyLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFront() {
        list.addToFront(1);
        assertEquals(1, (int) list.get(0));
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBack() {
        list.addToBack(2);
        assertEquals(2, (int) list.get(0));
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndex() {
        list.addToFront(1);
        list.addAtIndex(1, 2);
        assertEquals(2, (int) list.get(1));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront() {
        list.addToFront(1);
        list.addToFront(2);
        assertEquals(2, (int) list.removeFromFront());
        assertEquals(1, (int) list.get(0));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack() {
        list.addToFront(1);
        list.addToFront(2);
        assertEquals(1, (int) list.removeFromBack());
        assertEquals(2, (int) list.get(0));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndex() {
        list.addToFront(1);
        list.addToFront(2);
        list.addToFront(3);
        assertEquals(2, (int) list.removeAtIndex(1));
        assertEquals(3, (int) list.get(0));
        assertEquals(1, (int) list.get(1));
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        list.addToFront(1);
        list.addToFront(2);
        list.addToFront(3);
        assertEquals(2, (int) list.get(1));
    }

    @Test(timeout = TIMEOUT)
    public void testGetFromHeadHalf() {
        for (int i = 1; i <= 10; i++) {
            list.addToBack(i);
        }

        // Test the first half of the list
        for (int i = 0; i < 5; i++) {
            assertEquals(i + 1, (int) list.get(i));
        }
    }

    @Test(timeout = TIMEOUT)
    public void testGetFromTailHalf() {
        for (int i = 1; i <= 10; i++) {
            list.addToBack(i);
        }

        // Test the second half of the list
        for (int i = 5; i < 10; i++) {
            assertEquals(i + 1, (int) list.get(i));
        }
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        list.addToFront(1);
        list.addToFront(2);
        list.addToFront(3);
        list.clear();
        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccurrence() {
        list.addToFront(1);
        list.addToFront(2);
        list.addToFront(3);
        list.addToFront(2);
        assertEquals(2, (int) list.removeLastOccurrence(2));
        assertEquals(1, (int) list.get(2));
        assertEquals(2, (int) list.removeLastOccurrence(2));
        assertEquals(3, (int) list.get(0));
        assertEquals(1, (int) list.get(1));
    }

    @Test(timeout = TIMEOUT)
    public void testToArray() {
        list.addToFront(1);
        list.addToFront(2);
        list.addToFront(3);
        Object[] array = list.toArray();
        assertEquals(3, array.length);
        assertEquals(3, (int) array[0]);
        assertEquals(2, (int) array[1]);
        assertEquals(1, (int) array[2]);
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndexWithInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.addAtIndex(-1, 5));
        assertThrows(IndexOutOfBoundsException.class, () -> list.addAtIndex(1, 5));
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndexWithNullData() {
        assertThrows(IllegalArgumentException.class, () -> list.addAtIndex(0, null));
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFrontWithNullData() {
        assertThrows(IllegalArgumentException.class, () -> list.addToFront(null));
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBackWithNullData() {
        assertThrows(IllegalArgumentException.class, () -> list.addToBack(null));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndexWithInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAtIndex(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAtIndex(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAtIndex(1));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFrontWithEmptyList() {
        assertThrows(NoSuchElementException.class, () -> list.removeFromFront());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBackWithEmptyList() {
        assertThrows(NoSuchElementException.class, () -> list.removeFromBack());
    }

    @Test(timeout = TIMEOUT)
    public void testGetWithInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccurrenceWithNullData() {
        assertThrows(IllegalArgumentException.class, () -> list.removeLastOccurrence(null));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccurrenceWithDataNotFound() {
        list.addToFront(1);
        list.addToFront(2);
        list.addToFront(3);
        assertThrows(NoSuchElementException.class, () -> list.removeLastOccurrence(4));
    }
}