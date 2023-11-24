import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ashwin Mudaliar
 * @version 1.1
 * 
 * quote of the week: "UMiami sucks"
 * 
 */

public class QuadraticProbingHashMapComprehensiveTests {

    QuadraticProbingHashMap<TestClass, Integer> map;

    private static final int TIMEOUT = 200;

    @Before()
    public void setUp() {

        this.map = new QuadraticProbingHashMap<>();

    }

    private QuadraticProbingMapEntry[] populateMapNoResize(int startingVal, int num_elements) {

        if (num_elements > 8 || num_elements < 0) {
            throw new java.lang.IllegalArgumentException("Bounds for No Resize w load factor of 0.67");
        }

        int length = 13;

        QuadraticProbingMapEntry[] expected = new QuadraticProbingMapEntry[length];

        for (int k = 0; k < num_elements; k++) {

            TestClass key = new TestClass(startingVal + k);

            map.put(key, startingVal + k);
            expected[Math.abs(key.hashCode() % expected.length)] = new QuadraticProbingMapEntry<TestClass, Integer>(key,
                    startingVal + k);

        }

        return expected;

    }

    private QuadraticProbingMapEntry[] populateMapProbingNoResize(int startingVal, int num_elements) {

        if (num_elements > 8 || num_elements < 0) {
            throw new java.lang.IllegalArgumentException("Bounds for No Resize w load factor of 0.67");
        }

        if (startingVal + num_elements > -69) {
            throw new java.lang.IllegalArgumentException("Values do not output the proper hash code");
        }

        int length = 13;
        QuadraticProbingMapEntry[] expected = new QuadraticProbingMapEntry[length];

        for (int k = 0; k < num_elements; k++) {

            TestClass key = new TestClass(startingVal + k);

            int original = Math.abs(key.hashCode() % expected.length);

            map.put(key, startingVal + k);
            expected[((k * k) + original) % length] = new QuadraticProbingMapEntry<TestClass, Integer>(key,
                    startingVal + k);

        }

        return expected;

    }

    private QuadraticProbingMapEntry[] populateMapOneResize(int startingVal, int num_elements) {

        if (num_elements > 18 || num_elements < 0) {
            throw new java.lang.IllegalArgumentException("Bounds for One Resize");
        }

        int length = 27;

        QuadraticProbingMapEntry[] expected = new QuadraticProbingMapEntry[length];

        for (int k = 0; k < num_elements; k++) {

            TestClass key = new TestClass(startingVal + k);

            map.put(key, startingVal + k);
            expected[Math.abs(key.hashCode() % expected.length)] = new QuadraticProbingMapEntry<TestClass, Integer>(key,
                    startingVal + k);

        }

        return expected;

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testPutKeyIsNull() {

        map.put(null, 56);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testPutValueIsNull() {

        map.put(new TestClass(67), null);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testRemoveKeyIsNull() {

        map.remove(null);

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testRemoveKeyDoesNotExist() {

        map.remove(new TestClass(5));

        map.put(new TestClass(7), 56);
        map.remove(new TestClass(107));

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testGetKeyIsNull() {

        map.get(null);

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testGetKeyDoesNotExist() {

        map.get(new TestClass(0));

        map.put(new TestClass(7), 56);
        map.get(new TestClass(107));

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testContainsKeyIsNull() {

        map.containsKey(null);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testResizeLengthLessThanSize() {

        populateMapNoResize(5, 5);

        map.resizeBackingTable(4);

    }

    @Test(timeout = TIMEOUT)
    public void testPutNoResize() {

        QuadraticProbingMapEntry[] expected = populateMapNoResize(0, 8);

        assertEquals(8, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testPutNoResizeNegativeKey() {

        QuadraticProbingMapEntry[] expected = populateMapNoResize(-56, 8);

        assertEquals(8, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testPutOneResize() {

        QuadraticProbingMapEntry[] expected = populateMapOneResize(0, 9);

        assertEquals(9, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testPutOneResizeV2() {

        QuadraticProbingMapEntry[] expected = populateMapOneResize(75, 17);

        assertEquals(17, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testPutOneResizeNegative() {

        QuadraticProbingMapEntry[] expected = populateMapNoResize(-56, 8);

        assertEquals(8, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testPutSimpleProbing() {

        QuadraticProbingMapEntry[] expected = populateMapProbingNoResize(-110, 4);

        assertEquals(4, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testPutProbingNoResize() {

        QuadraticProbingMapEntry[] expected = populateMapProbingNoResize(-76, 6);

        assertEquals(map.INITIAL_CAPACITY, map.getTable().length);
        assertEquals(6, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testPutProbingOneResize() {

        QuadraticProbingMapEntry[] expected = populateMapOneResize(-79, 8);

        assertEquals(8, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testPutKeyExists() {

        QuadraticProbingMapEntry[] expected = populateMapOneResize(5, 8);

        assertEquals((Integer) 5, map.put(new TestClass(5), 67));
        expected[15] = new QuadraticProbingMapEntry<TestClass, Integer>(new TestClass(5), 67);

        System.out.println(Arrays.toString(map.getTable()));

        assertEquals(8, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveSimple() {

        QuadraticProbingMapEntry[] expected = populateMapNoResize(5, 8);

        assertEquals((Integer) 7, map.remove(new TestClass(7)));
        expected[4].setRemoved(true);

        assertEquals(7, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveProbing() {

        QuadraticProbingMapEntry[] expected = populateMapProbingNoResize(-76, 6);

        assertEquals((Integer) (-74), map.remove(new TestClass(-74)));
        expected[9].setRemoved(true);

        assertEquals(5, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void testRemoveInfiniteProbing() {

        QuadraticProbingMapEntry[] expected = populateMapOneResize(-79, 7);

        map.remove(new TestClass(-70));

    }

    @Test(timeout = TIMEOUT)
    public void testPutElementIsRemoved() {

        QuadraticProbingMapEntry[] expected = populateMapNoResize(5, 8);

        assertEquals((Integer) 6, map.remove(new TestClass(6)));
        assertEquals(null, map.put(new TestClass(19), 19));

        expected[3] = new QuadraticProbingMapEntry<TestClass, Integer>(new TestClass(19), 19);

        assertEquals(8, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testPutElementIsRemovedKeysEqual() {

        QuadraticProbingMapEntry[] expected = populateMapNoResize(5, 8);

        assertEquals((Integer) 6, map.remove(new TestClass(6)));
        assertEquals(null, map.put(new TestClass(6), 19));

        expected[3] = new QuadraticProbingMapEntry<TestClass, Integer>(new TestClass(6), 19);

        assertEquals(8, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testPutElementIsRemovedProbing() {

        QuadraticProbingMapEntry[] expected = populateMapProbingNoResize(-76, 6);

        assertEquals((Integer) (-74), map.remove(new TestClass(-74)));
        assertEquals(null, map.put(new TestClass(-74), 89));

        expected[9] = new QuadraticProbingMapEntry<TestClass, Integer>(new TestClass(-74), 89);

        assertEquals(6, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testTriggerResizeInfiniteProbing() {

        QuadraticProbingMapEntry[] expected = populateMapProbingNoResize(-76, 7);

        assertEquals((Integer) (-74), map.remove(new TestClass(-74)));
        assertEquals(null, map.put(new TestClass(-74), 89));

        expected[9] = new QuadraticProbingMapEntry<TestClass, Integer>(new TestClass(-74), 89);

        assertEquals(13, map.getTable().length);

        map.put(new TestClass(-69), -69);

        assertEquals(27, map.getTable().length);

    }

    @Test(timeout = TIMEOUT)
    public void testContainsKey() {

        populateMapNoResize(5, 7);

        map.put(new TestClass(456), 67);

        assertEquals(false, map.containsKey(new TestClass(78)));
        assertEquals(true, map.containsKey(new TestClass(-456)));

    }

    @Test(timeout = TIMEOUT)
    public void testKeySet() {

        populateMapOneResize(0, 18);

        for (int k = 0; k < 18; k += 2) {

            map.remove(new TestClass(k));

        }

        HashSet<TestClass> expected = new HashSet<>();
        for (int k = 1; k < 18; k += 2) {

            expected.add(new TestClass(k));

        }

        assertEquals(expected, map.keySet());

    }

    @Test(timeout = TIMEOUT)
    public void testValues() {

        QuadraticProbingMapEntry[] arr = populateMapOneResize(0, 18);

        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(map.getTable()));

        for (int k = 0; k < 18; k += 2) {

            map.remove(new TestClass(k));

        }

        List<Integer> expected = new LinkedList<>();
        for (int k = 0; k < arr.length; k++) {

            if (arr[k] != null && (Integer) (arr[k].getValue()) % 2 != 0) {
                expected.add(expected.size(), (Integer) (arr[k].getValue()));
            }

        }

        System.out.println(map.values());

        assertEquals(expected, map.values());

    }

    @Test(timeout = TIMEOUT)
    public void testResizeSmallerTableLength13() {

        QuadraticProbingMapEntry[] expected = this.populateMapNoResizeCustom(7, 8, 11);

        map.resizeBackingTable(11);
        System.out.println(Arrays.toString(map.getTable()));

        assertEquals(8, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    private QuadraticProbingMapEntry[] populateMapNoResizeCustom(int startingVal, int num_elements, int length) {

        if (num_elements > 8 || num_elements < 0) {
            throw new java.lang.IllegalArgumentException("Bounds for No Resize w load factor of 0.67");
        }

        QuadraticProbingMapEntry[] expected = new QuadraticProbingMapEntry[length];

        for (int k = 0; k < num_elements; k++) {

            TestClass key = new TestClass(startingVal + k);

            map.put(key, startingVal + k);
            expected[Math.abs(key.hashCode() % expected.length)] = new QuadraticProbingMapEntry<TestClass, Integer>(key,
                    startingVal + k);

        }

        return expected;

    }

    @Test(timeout = TIMEOUT)
    public void testResizeLarger() {

        QuadraticProbingMapEntry[] expected = this.populateMapNoResizeCustom(7, 8, 24);

        map.resizeBackingTable(24);

        assertEquals(8, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    @Test(timeout = TIMEOUT)
    public void testResizeSmallerProbing() {

        QuadraticProbingMapEntry[] expected = this.populateMapProbingNoResizeCustom(-178, 3, 10);

        expected[6].setKey(new TestClass(-176));
        expected[6].setValue(-176);

        expected[9].setKey(new TestClass(-177));
        expected[9].setValue(-177);

        map.resizeBackingTable(10);

        System.out.println(Arrays.toString(expected));
        System.out.println(Arrays.toString(map.getTable()));

        assertEquals(3, map.size());
        assertArrayEquals(expected, map.getTable());

    }

    private QuadraticProbingMapEntry[] populateMapProbingNoResizeCustom(int startingVal, int num_elements, int length) {

        if (num_elements > 8 || num_elements < 0) {
            throw new java.lang.IllegalArgumentException("Bounds for No Resize w load factor of 0.67");
        }

        if (startingVal + num_elements > -69) {
            throw new java.lang.IllegalArgumentException("Values do not output the proper hash code");
        }

        QuadraticProbingMapEntry[] expected = new QuadraticProbingMapEntry[length];

        for (int k = 0; k < num_elements; k++) {

            TestClass key = new TestClass(startingVal + k);

            int original = Math.abs(key.hashCode() % expected.length);

            map.put(key, startingVal + k);
            expected[((k * k) + original) % length] = new QuadraticProbingMapEntry<TestClass, Integer>(key,
                    startingVal + k);

        }

        return expected;

    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void testGetWithRemoved() {

        populateMapNoResize(6, 8);

        map.remove(new TestClass(7));
        map.get(new TestClass(7));
        
    }

    @Test(timeout = TIMEOUT)
    public void testGetWithProbing() {

        populateMapProbingNoResize(-78, 6);

        assertEquals((Integer) (-73), map.get(new TestClass(-73)));

    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void testGetWithProbingDNE() {
        populateMapProbingNoResize(-78, 6);

        map.get(new TestClass(-71));
    }

    @Test(timeout = TIMEOUT)
    public void testProbingComplex() {

        QuadraticProbingMapEntry[] expected = populateMapNoResize(6, 7);

        map.put(new TestClass(19), 19);
        expected[12] = new QuadraticProbingMapEntry<TestClass,Integer>(new TestClass(19), 19);

        System.out.println(Arrays.toString(map.getTable()));

        assertArrayEquals(expected, map.getTable());
        assertEquals((Integer) 19, map.get(new TestClass(19)));

    }


}

class TestClass {

    int data;

    public TestClass(int dataIn) {
        this.data = dataIn;
    }

    @Override
    public int hashCode() {

        if (data <= -69 && data >= -79) { // one resize logic
            return 5 + (13 * (data + 79));
        } else if (data <= -100 && data >= -110) {
            return 7;
        } else if (Math.abs(data) == 456) {
            return 1;
        } else if (data <= -169 && data >= -179) {
            return 5 + (10 * (data + 179));
        }

        return data + 10;

    }

    @Override
    public boolean equals(Object o) {

        if (o == null || !o.getClass().equals(TestClass.class)) {
            return false;
        } else {

            if (data == -456 && ((TestClass) o).data == 456 || data == 456 && ((TestClass) o).data == -456) {
                return true;
            }

            return data == ((TestClass) o).data;

        }

    }

    @Override
    public String toString() {

        return "Test Class: " + data;

    }

}