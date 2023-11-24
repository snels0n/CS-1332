import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/*
 * Cool set of tests for homework 1: ArrayLists. (supplements ArrayListStudentTest.java)
 *
 * @author Alexander Gualino
 * @version 1.0
 *
 * References:
 * - ArrayListStudentTest
 */
public class ExhaustiveTest {
    private static final int TIMEOUT = 200;

    // the following tests check the `throws` conditions are right
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexLowerBound() {
        new ArrayList<>().addAtIndex(-1, 0);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexUpperBound() {
        new ArrayList<>().addAtIndex(1, 0);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddAtIndexNullData() {
        new ArrayList<>().addAtIndex(0, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddToFrontNullData() {
        new ArrayList<>().addToFront(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddToBackNullData() {
        new ArrayList<>().addToBack(null);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexLowerBound() {
        new ArrayList<>().removeAtIndex(-1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexUpperBound() {
        new ArrayList<>().removeAtIndex(0);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFromFrontEmpty() {
        new ArrayList<>().removeFromFront();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFromBackEmpty() {
        new ArrayList<>().removeFromBack();
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetLowerBound() {
        new ArrayList<>().get(-1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetUpperBound() {
        new ArrayList<>().get(0);
    }

    // some exhaustive checks now
    @Test(timeout = TIMEOUT)
    public void testUnmodifiedInitialCapacity() {
        assertEquals(9, ArrayList.INITIAL_CAPACITY);
    }

    @Test(timeout = TIMEOUT)
    public void testInitialCapacityIsNotCopied() {
        assertEquals(ArrayList.INITIAL_CAPACITY, new ArrayList<>().getBackingArray().length);
        var a1 = new ArrayList<>();
        a1.addToBack("a");
        a1.clear();
        assertEquals(ArrayList.INITIAL_CAPACITY, a1.getBackingArray().length);

        // originally I had some wild reflection stuff here, but it doesn't work on Java 11 :(
    }

    @Test(timeout = TIMEOUT)
    public void testBackingArraySizeIsReset() {
        var a1 = new ArrayList<>();
        assertEquals(ArrayList.INITIAL_CAPACITY, a1.getBackingArray().length);

        for (int i = 0; i <= ArrayList.INITIAL_CAPACITY; i++) {
            a1.addToBack("a");
        }

        assertNotEquals(ArrayList.INITIAL_CAPACITY, a1.getBackingArray().length);
        a1.clear();
        assertEquals(ArrayList.INITIAL_CAPACITY, a1.getBackingArray().length);
    }

    @Test(timeout = TIMEOUT)
    public void testAddThenRemove() {
        var a1 = new ArrayList<>();
        for (int i = 0; i <= ArrayList.INITIAL_CAPACITY; i++) {
            a1.addToBack(i);
        }

        for (int i = ArrayList.INITIAL_CAPACITY; i >= 0; i--) {
            assertEquals(i, a1.removeFromBack());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndexOnBoundary() {
        var a1 = new ArrayList<>();
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY; i++) {
            a1.addToBack(i);
        }

        // I expect that the backing array can be completely saturated
        assertEquals(ArrayList.INITIAL_CAPACITY, a1.size());
        assertEquals(a1.getBackingArray()[ArrayList.INITIAL_CAPACITY - 1], ArrayList.INITIAL_CAPACITY - 1);
        assertEquals(ArrayList.INITIAL_CAPACITY, a1.getBackingArray().length);

        a1.removeFromBack();
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndexOnBoundary() {
        var a1 = new ArrayList<>();
        for (int i = 0; i < ArrayList.INITIAL_CAPACITY - 1; i++) {
            a1.addToBack(i);
        }

        assertEquals(ArrayList.INITIAL_CAPACITY - 1, a1.size());

        a1.addAtIndex(a1.size() - 1, 42);
        a1.addAtIndex(a1.size() - 1, 43);
    }

    @Test(timeout = TIMEOUT)
    public void testCapacityIncreaseTimes() {
        var a1 = new ArrayList<>();
        for (int i = 0; i <= ArrayList.INITIAL_CAPACITY; i++) {
            a1.addToBack(i);
        }

        assertEquals(ArrayList.INITIAL_CAPACITY * 2, a1.getBackingArray().length);
    }
}