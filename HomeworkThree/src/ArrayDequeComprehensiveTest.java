import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ashwin Amitabh Mudaliar
 * @version 1.0.1
 */

public class ArrayDequeComprehensiveTest {
    
    private static ArrayDeque<String> list;
    private static String[] test;
    private static final int TIMEOUT = 200;

    private static final int NUM_TEST_ELEMENTS = 10;

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void addFirstDataIsNull() {

        list.addFirst(null);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void addLastDataIsNull() {

        list.addLast(null);

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void removeFirstSizeIsZero() {

        list.removeFirst();

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void removeLastSizeIsZero() {

        list.removeLast();

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void getFirstSizeIsZero() {

        list.getFirst();

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void getLastSizeIsZero() {

        list.getLast();

    }

    @Before
    public void setUp() {

        list = new ArrayDeque<>();
        test = new String[1];

    }

    private static void populateListByBack(ArrayDeque<String> list, int num_elements, String[] test) {

        for(int k = 0; k < num_elements; k++) {

            list.addLast(Integer.toString(k));
            test[k] = Integer.toString(k); 

        }

    }

    private static void populateListByFront(ArrayDeque<String> list, int num_elements, String[] test) {

        for(int k = num_elements - 1; k >=0; k--) {

            list.addFirst(Integer.toString(k));
            test[k] = Integer.toString(k); 

        }

    }

    @Test(timeout = TIMEOUT)
    public void testAddLastNoResize() {

        test = new String[11];

        populateListByBack(list, NUM_TEST_ELEMENTS, test); // front should be at 0

        assertArrayEquals(list.getBackingArray(), test);

    }

    @Test(timeout = TIMEOUT)
    public void testAddLastOneResize() {

        test = new String[22];

        populateListByBack(list, 22, test);

        assertEquals(22, list.size());
        assertArrayEquals(list.getBackingArray(), test);

    }

    @Test(timeout = TIMEOUT)
    public void testAddLastTwoResize() {

        test = new String[44];

        populateListByBack(list, 44, test);

        assertEquals(44, list.size());
        assertArrayEquals(list.getBackingArray(), test);

    }

    @Test(timeout = TIMEOUT)
    public void testAddLastWithFrontIndex() {

        list.addFirst("I LOVE CS");
        test = new String[11];
        test[10] = "I LOVE CS";

        populateListByBack(list, 10, test);

        assertEquals(list.size(), 11);
        assertArrayEquals(list.getBackingArray(), test);

    }

    @Test(timeout = TIMEOUT)
    public void testAddLastWithFrontIndexOneResize() {

        list.addFirst("I LOVE CS");
        test = new String[22];

        populateListByBack(list, 21, test);

        for(int k = 1; k < test.length; k++) {

            test[k] = Integer.toString(k - 1);

        }

        test[0] = "I LOVE CS";

        assertEquals(list.size(), 22);

        assertArrayEquals(list.getBackingArray(), test);

    }

    @Test(timeout = TIMEOUT)
    public void testAddFrontNoResize() {

        test = new String[11];

        populateListByFront(list, 11, test);

        assertEquals(list.size(), 11);
        assertArrayEquals(list.getBackingArray(), test);

    }

    @Test(timeout = TIMEOUT)
    public void testAddFrontOneResize() {

        test = new String[22]; // too lazy to change the method

        populateListByFront(list, 22, test);

        String[] asserter = {"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", 
        "20", "21", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        assertEquals(list.size(), 22);
        assertArrayEquals(list.getBackingArray(), asserter);

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFront() {

        test = new String[11];

        populateListByBack(list, 11, test);

        for(int k = 0; k < test.length; k++) {

            assertEquals(list.removeFirst(), test[k]);

        }

        assertArrayEquals(new String[11], list.getBackingArray());
        assertEquals(list.size(), 0);

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFrontWithFrontIndex() {

        test = new String[11];

        list.addFirst("I LOVE CS1332");

        populateListByBack(list, 10, test);

        assertEquals("I LOVE CS1332", list.removeFirst());

        for(int k = 1; k < test.length; k++) {

            assertEquals(list.removeFirst(), Integer.toString(k-1));

        }

        assertArrayEquals(new String[11], list.getBackingArray());
        assertEquals(list.size(), 0);

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLast() {

        test = new String[11];

        populateListByBack(list, 11, test);

        for(int k = 0; k < test.length; k++) {

            assertEquals(list.removeLast(), test[test.length - 1 - k]);

        }

        assertArrayEquals(new String[11], list.getBackingArray());
        assertEquals(list.size(), 0);

    }
    
     @Test(timeout = TIMEOUT)
    public void testRemoveLastWithFrontIndex() {

        test = new String[11];

        list.addFirst("I LOVE CS1332");

        populateListByBack(list, 10, test);

        for(int k = 1; k < test.length; k++) {

            assertEquals(list.removeLast(), Integer.toString(test.length - k - 1));

        }

        assertEquals(list.removeLast(), "I LOVE CS1332");

        assertArrayEquals(new String[11], list.getBackingArray());
        assertEquals(list.size(), 0);

    }

}