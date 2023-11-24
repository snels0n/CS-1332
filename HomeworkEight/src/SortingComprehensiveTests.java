import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

import java.util.Comparator;
import java.util.Random;
import java.util.ArrayList;

/**
 * @author Ashwin Mudaliar
 * @version 1.3.2
 */
public class SortingComprehensiveTests {

    private final static int TIMEOUT = 200;
    
    private Random rand = new Random();
    private Comparator<TestClass> testComp = new Comparator<TestClass>() {

        @Override
        public int compare(TestClass t1, TestClass t2) {

            // Special Cases for Testing
            if ((t1.data == 101 && t2.data == 102) || (t2.data == 101 && t1.data == 102)) {
                return 0;
            }

            // General Cases
            if (t1.data < t2.data) {
                return -1;
            } else if (t1.data > t2.data) {
                return 1;
            } else {
                return 0;
            }

        }

    };

    private TestClass[] buildTestArray(int[] values) {

        TestClass[] ret = new TestClass[values.length];

        for (int k = 0; k < values.length; k++) {
            ret[k] = new TestClass(values[k]);
        }

        return ret;

    }

    // testing error handling

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testInsertionArrayNull() {
        Sorting.insertionSort(null, testComp);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testInsertionComparatorNull() {

        int[] vals = {14, 15, 16};
        TestClass[] expected = buildTestArray(vals);

        Sorting.insertionSort(expected, null);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testCocktailArrayNull() {
        Sorting.cocktailSort(null, testComp);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testCocktailComparatorNull() {

        int[] vals = {14, 15, 16};
        TestClass[] expected = buildTestArray(vals);

        Sorting.cocktailSort(expected, null);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testMergeArrayNull() {
        Sorting.mergeSort(null, testComp);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testMergeComparatorNull() {

        int[] vals = {14, 15, 16};
        TestClass[] expected = buildTestArray(vals);

        Sorting.mergeSort(expected, null);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testQuickArrayNull() {
        Sorting.quickSort(null, testComp, rand);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testQuickComparatorNull() {

        int[] vals = {14, 15, 16};
        TestClass[] expected = buildTestArray(vals);

        Sorting.quickSort(expected, null, rand);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testQuickRandNull() {

        int[] vals = {14, 15, 16};
        TestClass[] expected = buildTestArray(vals);

        Sorting.quickSort(expected, testComp, null);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testRadixArrayNull() {
        Sorting.lsdRadixSort(null);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testHeapArrayNull() {
        Sorting.heapSort(null);
    }

    // actual tests

    @Test(timeout = TIMEOUT)
    public void testInsertionSimple() {

        int[] vals = {11,18,3,4,8,25,78,1,6,90};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {1, 3, 4, 6, 8, 11, 18, 25, 78, 90};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.insertionSort(test, testComp);
        assertArrayEquals("Array must be sorted properly", expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void testInsertionStability() {

        String message = "Insertion sort implementation must preserve stability";

        int[] vals = {9, 8, 2, 3, 5, 7, 102, 101, 67, 34};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {2, 3, 5, 7, 8, 9, 34, 67, 102, 101};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.insertionSort(test, testComp);
        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void testInsertionIsEmpty() {

        String message = "Checks that you don't do anything weird";

        int[] vals = {};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.insertionSort(test, testComp);
        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void testInsertionOneElement() {

        String message = "Checks that you don't do anything weird";

        int[] vals = {98};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {98};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.insertionSort(test, testComp);
        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void testInsertionTwoElements() {

        String message = "Checks that you don't do anything weird";

        int[] vals = {99, 98};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {98, 99};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.insertionSort(test, testComp);
        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortSimple() {

        String message = "Simple cocktail sort test";

        int[] vals = {11,18,3,4,8,25,78,1,6,90};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {1, 3, 4, 6, 8, 11, 18, 25, 78, 90};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.cocktailSort(test, testComp);
        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortStability() {

        String message = "Cocktail sort implementation must preserve stability";

        int[] vals = {9, 8, 2, 3, 5, 7, 102, 101, 67, 34};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {2, 3, 5, 7, 8, 9, 34, 67, 102, 101};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.cocktailSort(test, testComp);
        assertArrayEquals(message, expected, test);        

    }

    @Test(timeout = TIMEOUT)
    public void testCocktailIsEmpty() {

        String message = "Checks that you don't do anything weird";

        int[] vals = {};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.insertionSort(test, testComp);
        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void testCocktailOneElement() {

        String message = "Checks that you don't do anything weird";

        int[] vals = {98};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {98};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.insertionSort(test, testComp);
        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void testCocktailTwoElements() {

        String message = "Checks that you don't do anything weird";

        int[] vals = {99, 98};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {98, 99};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.insertionSort(test, testComp);
        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortSimple() {

        String message = "Simple cocktail sort test";

        int[] vals = {11,18,3,4,8,25,78,1,6,90};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {1, 3, 4, 6, 8, 11, 18, 25, 78, 90};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.mergeSort(test, testComp);
        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortOddNumberOfElements() {

        String message = "Cocktail sort test with an odd number of elements";

        int[] vals = {11,18,3,4,8,25,37,78,1,6,90};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {1, 3, 4, 6, 8, 11, 18, 25, 37, 78, 90};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.mergeSort(test, testComp);
        assertArrayEquals(message, expected, test);

    }


    @Test(timeout = TIMEOUT)
    public void testMergeSortStability() {

        String message = "Cocktail sort implementation must preserve stability";

        int[] vals = {9, 8, 2, 3, 5, 7, 102, 101, 67, 34};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {2, 3, 5, 7, 8, 9, 34, 67, 102, 101};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.mergeSort(test, testComp);
        assertArrayEquals(message, expected, test);        

    }

    @Test(timeout = TIMEOUT)
    public void testMergeIsEmpty() {

        String message = "Checks that you don't do anything weird";

        int[] vals = {};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.mergeSort(test, testComp);
        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void testMergeOneElement() {

        String message = "Checks that you don't do anything weird";

        int[] vals = {98};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {98};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.mergeSort(test, testComp);
        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void testMergeTwoElements() {

        String message = "Checks that you don't do anything weird";

        int[] vals = {99, 98};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {98, 99};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.mergeSort(test, testComp);
        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void testHeapSimple() {

        String message = "Simple test for heap sort";
        int[] test = {11,18,3,4,8,25,78,1,6,90};
        ArrayList<Integer> arr = new ArrayList<Integer>();

        for (int k = 0; k < test.length; k++) {
            arr.add(k, test[k]);
        }

        int[] expected = {1, 3, 4, 6, 8, 11, 18, 25, 78, 90};
        assertArrayEquals(message, expected, Sorting.heapSort(arr));

    }

    @Test(timeout = TIMEOUT)
    public void testHeapEmpty() {

        String message = "Test if heap sort handles";

        int[] test = {};
        ArrayList<Integer> arr = new ArrayList<Integer>();

        for (int k = 0; k < test.length; k++) {
            arr.add(k, test[k]);
        }

        int[] expected = {};

        assertArrayEquals(message, expected, Sorting.heapSort(arr));

    }

    @Test(timeout = TIMEOUT)
    public void LSDRadixSimple() {

        String message = "Simple test for LSDRadix sort";
        int[] test = {11,18,3,4,8,25,78,1,6,90};

        int[] expected = {1, 3, 4, 6, 8, 11, 18, 25, 78, 90};
        Sorting.lsdRadixSort(test);

        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void LSDLargeElement() {

        String message = "handles a large element";
        int[] test = {34, 12, 3, 9, 123457, 76, 23, 85, 2};
        int[] expected = {2, 3, 9, 12, 23, 34, 76, 85, 123457};

        Sorting.lsdRadixSort(test);

        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void LSDMostDigitsIsNegative() {

        String message = "handles case where most digits is negative number";
        int[] test = {34, 12, 3, 9, 1234, 76, 23, 85, 2, -45673};
        int[] expected = {-45673, 2, 3, 9, 12, 23, 34, 76, 85, 1234};

        Sorting.lsdRadixSort(test);

        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void LSDAllNegative() {

        String message = "handles case where all digits are negative";
        int[] test = {-4, -7, -9, -123, -45, -89, -23, -8, -768};
        int[] expected = {-768, -123, -89, -45, -23, -9, -8, -7, -4};

        Sorting.lsdRadixSort(test);

        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void quickSimple() {

        String message = "Simple test for Quick Sort";

        int[] vals = {11,18,3,4,8,25,78,1,6,90};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {1, 3, 4, 6, 8, 11, 18, 25, 78, 90};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.quickSort(test, testComp, rand);
        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void quickIsEmpty() {

        String message = "Test that you don't do anything weird";

        int[] vals = {};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.quickSort(test, testComp, rand);
        assertArrayEquals(message, expected, test);

    }

    @Test(timeout = TIMEOUT)
    public void quickOneElement() {

        String message = "Test that you dont do anything weird";

        int[] vals = {14};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {14};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.quickSort(test, testComp, rand);
        assertArrayEquals(message, expected, test);

    }

     @Test(timeout = TIMEOUT)
    public void quickTwoElement() {

        String message = "Test that you dont do anything weird";

        int[] vals = {14, -14};
        TestClass[] test = buildTestArray(vals);

        int[] expectedVals = {-14, 14};
        TestClass[] expected = buildTestArray(expectedVals);

        Sorting.quickSort(test, testComp, rand);
        assertArrayEquals(message, expected, test);

    }

}

class TestClass implements Comparator<TestClass>, Comparable<TestClass> {

    int data;

    public TestClass(int dataIn) {

        this.data = dataIn;

    }

    @Override
    public int compareTo(TestClass t) {

        return this.data = t.data;

    }

    @Override
    public int compare(TestClass t1, TestClass t2) {

        // Special Cases for Testing
        if ((t1.data == 101 && t2.data == 102) || (t2.data == 101 && t1.data == 102)) {
            return 0;
        }

        // General Cases
        if (t1.data < t2.data) {
            return -1;
        } else if (t1.data > t2.data) {
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    public boolean equals(Object o) {

        if (o.getClass().equals(TestClass.class)) {

            return ((TestClass) o).data == this.data;

        } else {
            return false;
        }
        
    }

    @Override
    public String toString() {

        return "Test Class: " + this.data;

    }

}