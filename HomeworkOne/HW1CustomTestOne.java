import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * Custom unit tests for CS 1332, HWK 1
 * These unit tests are based off the rubric and some fringe cases
 * 
 * Feel free to update this as you please
 * 
 * NOTE: Not completely hashed out, still looking for some more edge cases to test
 * 
 * @author Ashwin Mudaliar
 * @version 1.0
 */

public class HW1CustomTestOne {
    
    private static final int TIMEOUT = 200;
    private ArrayList<String> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addAtIndexOutOfBounds() {

        list.addAtIndex(list.size(), "test");

        list.addAtIndex(list.size() + 1, "test");

        list.addAtIndex(-1, "test");


    }

    @Test(expected = IllegalArgumentException.class)
    public void addAtIndexNull() {

        list.addAtIndex(0, null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void addToFrontNull() {

        list.addToFront(null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void addToBackNull() {

        list.addToBack(null);

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeAtIndexOutOfBounds() {

        list.removeAtIndex(list.size());

        list.removeAtIndex(list.size() + 1);

        list.removeAtIndex(-1);

    }

    @Test(expected = NoSuchElementException.class)
    public void removeFromFrontNoSuch() {

        list.clear();
        list.removeFromFront();

    }

    @Test(expected = NoSuchElementException.class)
    public void removeFromBackNoSuch() {

        list.clear();
        list.removeFromBack();

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getOutOfBounds() {

        list.get(list.size());

        list.get(list.size() + 1);

        list.get(-1);

    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndex() {

        String[] testData = new String[list.INITIAL_CAPACITY + 5];

        for (int k = 0; k < testData.length; k++) { // popoulate the array
            testData[k] = Integer.toString(k);
            list.addAtIndex(k, Integer.toString(k));
        }
        
        // check size
        assertEquals(list.INITIAL_CAPACITY + 5, list.size());
        
        // check elements are equal to each other
        for (int k = 0; k < testData.length; k++) {

            assertEquals(testData[k], list.get(k));

        }

    }

    @Test(timeout = TIMEOUT)
    public void testClear() {

        list.clear();

        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY], list.getBackingArray());

    }

    @Test(timeout = TIMEOUT)
    public void testAddToFront() {

        String[] testData = new String[list.INITIAL_CAPACITY + 5];

        for (int k = testData.length - 1; k >= 0; k--) { // popoulate the array
            testData[k] = Integer.toString(k);
            list.addToFront(Integer.toString(k));
        }

        // CHECK SIZE
        assertEquals(list.INITIAL_CAPACITY + 5, list.size());

        // check vals
        for (int k = 0; k < testData.length; k++) {

            assertEquals(testData[k], list.get(k));

        }

    }

    @Test(timeout = TIMEOUT)
    public void testAddToBack() {

        String[] testData = new String[list.INITIAL_CAPACITY + 5];

        for (int k = 0; k < testData.length; k++) { // populate the array
            testData[k] = Integer.toString(k);
            list.addToBack(Integer.toString(k));
        }

        // CHECK SIZE
        assertEquals(list.INITIAL_CAPACITY + 5, list.size());

        // check vals
        for (int k = 0; k < testData.length; k++) {

            assertEquals(testData[k], list.get(k));

        }

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndex() {

        for (int k = 0; k < list.INITIAL_CAPACITY + 5; k++) { // popoulate the array
            list.addToBack(Integer.toString(k));
        }

        String temp = list.removeAtIndex(5);

        assertEquals(temp, "5");

        boolean exists = false;

        for (int k = 0; k < list.size(); k++) {

            if (list.get(k).equals(temp)) {
                exists = true;
            }

        }

        assertEquals(false, exists);

    }

    @Test(timeout = TIMEOUT)
    public void testIsEmpty() {

        list.clear();

        assertEquals(true, list.isEmpty());

        list.addToFront("I love CS 1332!");

        assertEquals(false, list.isEmpty());

    }

}
