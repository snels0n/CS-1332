import org.junit.Test;

import java.util.*;
import java.util.function.BiConsumer;

import static org.junit.Assert.*;

/**
 * Mainly some property tests (kinda)!
 *
 * @author Alexander Gualino
 * @version 1.1
 */
public class ExhaustiveTests {
    private static final int TIMEOUT = 200;


    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testCocktailSortNullComparator() {
        Sorting.cocktailSort(new Integer[]{}, null);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortProperties() {
        for (var i = 0; i < 10; i++) {
            testAdaptive(Sorting::insertionSort);
        }

        for (var i = 0; i < 10; i++) {
            testStable(Sorting::insertionSort);
        }

        for (var i = 0; i < 20; i++) {
            testSorts(Sorting::insertionSort);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortProperties() {
        for (var i = 0; i < 10; i++) {
            testAdaptive(Sorting::cocktailSort);
        }

        for (var i = 0; i < 10; i++) {
            testStable(Sorting::cocktailSort);
        }

        for (var i = 0; i < 20; i++) {
            testSorts(Sorting::cocktailSort);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortProperties() {
        for (var i = 0; i < 10; i++) {
            testStable(Sorting::mergeSort);
        }

        for (var i = 0; i < 20; i++) {
            testSorts(Sorting::mergeSort);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSortProperties() {
        for (var i = 0; i < 20; i++) {
            testSorts((arr, comparator) -> Sorting.quickSort(arr, comparator, new Random()));
        }

        for (var i = 0; i < 10; i++) {
            // check the worst case
            var seed = new Random().nextLong();
            var rand = new Random(seed);

            var length = rand.nextInt(19) + 1;
            var item = rand.nextInt(10000) - 5000;
            var arr = new Integer[length];
            for (var j = 0; j < length; j++) {
                arr[j] = item;
                item += rand.nextInt(1000) + 1;
            }

            final int[] comparisons = {0};
            Sorting.quickSort(arr, (o1, o2) -> {
                comparisons[0]++;
                return o1.compareTo(o2);
            }, new Random() {
                @Override
                public int nextInt(int b) {
                    return 0;
                }
            });

            // if you fail this, and your actual is 1 less than the expected, just add a `-1` to the formula below.
            //  (it does not account for optimization for a 2-element array).
            assertEquals("failed on seed " + seed + "L", (length - 1) * (length + 2) / 2, comparisons[0]);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testLSDRadixSortProperties() {
        for (var i = 0; i < 20; i++) {
            testSorts((arr, comparator) -> {
                var newArr = Arrays.stream(arr).mapToInt(Integer::intValue).toArray();
                Sorting.lsdRadixSort(newArr);
                for (var j = 0; j < arr.length; j++) {
                    arr[j] = newArr[j];
                }
            });
        }
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSortProperties() {
        for (var i = 0; i < 20; i++) {
            testSorts((arr, comparator) -> {
                var result = Sorting.heapSort(List.of(arr));
                for (var j = 0; j < arr.length; j++) {
                    arr[j] = result[j];
                }
            });
        }
    }

    @Test(timeout = TIMEOUT)
    public void testLSDRadixSortEmpty() {
        var arr = new int[]{};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(new int[]{}, arr);
    }


    @Test(timeout = TIMEOUT)
    public void testLSDRadixSortOneElement() {
        var arr = new int[]{4};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(new int[]{4}, arr);
    }

    @Test(timeout = TIMEOUT)
    public void testLSDRadixSortBasicTwoElements() {
        var arr = new int[]{29, 30};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(new int[]{29, 30}, arr);
    }

    // todo: some quickSort tests cause I don't trust my implementation...

    private void testStable(BiConsumer<OrderCheckingInteger[], Comparator<OrderCheckingInteger>> sort) {
        var seed = new Random().nextLong();
        var rand = new Random(seed);

        var length = rand.nextInt(20);
        var arr = new OrderCheckingInteger[length * 2];
        for (var i = 0; i < length; i++) {
            var val = rand.nextInt();
            arr[i] = new OrderCheckingInteger(val, i);
            arr[i + length] = new OrderCheckingInteger(val, i + length);
        }

        // todo: shuffle?

        sort.accept(arr, Comparator.comparing(OrderCheckingInteger::val));
        if (arr.length == 0) {
            return;
        }

        for (var i = 0; i < arr.length; i += 2) {
            assertTrue("failed on seed " + seed + "L", arr[i].place() < arr[i + 1].place());
            assertEquals("failed on seed " + seed + "L", arr[i].val(), arr[i+1].val());
        }
    }

    private void testAdaptive(BiConsumer<Integer[], Comparator<Integer>> sort) {
        // adaptive means that there will only be N comparisons for a sorted array.
        var seed = new Random().nextLong();
        var rand = new Random(seed);

        var length = rand.nextInt(20);
        var item = rand.nextInt(10000) - 5000;
        var arr = new Integer[length];
        for (var i = 0; i < length; i++) {
            arr[i] = item;
            item += rand.nextInt(1000);
        }

        final int[] comparisons = {0};
        sort.accept(arr, (o1, o2) -> {
            comparisons[0]++;
            return o1.compareTo(o2);
        });

        if (length == 0) {
            return;
        }

        assertEquals("failed on seed " + seed + "L", length - 1, comparisons[0]);
    }

    private void testSorts(BiConsumer<Integer[], Comparator<Integer>> sort) {
        var seed = new Random().nextLong();
        var rand = new Random(seed);

        var length = rand.nextInt(20);
        var arr = new Integer[length];
        for (var i = 0; i < length; i++) {
            arr[i] = rand.nextInt();
        }

        sort.accept(arr, Comparator.naturalOrder());

        if (arr.length == 0) {
            return;
        }

        var last = arr[0];
        for (var i = 1; i < arr.length; i++) {
            assertTrue("failed on seed " + seed + "L", last <= arr[i]);
            last = arr[i];
        }
    }

    private static class OrderCheckingInteger {
        private final int val;
        private final int place;

        public OrderCheckingInteger(Integer value, int place) {
            this.val = value;
            this.place = place;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OrderCheckingInteger that = (OrderCheckingInteger) o;
            return val == that.val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }

        public Integer val() {
            return this.val;
        }

        public int place() {
            return this.place;
        }

        @Override
        public String toString() {
            return "OrderCheckingInteger{" +
                    "val=" + val +
                    ", place=" + place +
                    '}';
        }
    }
}
