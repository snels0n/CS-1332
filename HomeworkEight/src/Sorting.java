import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Sam Nelson
 * @version 1.0
 * @userid snelson73
 * @GTID 903754732
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null!");
        }

        for (int i = 1; i < arr.length; ++i) {
            if (arr[i] == null) {
                break;
            }
            T key = arr[i];
            int j = i - 1;

            while (j >= 0 && comparator.compare(key, arr[j]) < 0) {
                arr[j  + 1] = arr[j];
                arr[j] = key;
                j -= 1;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot pass in null arguments!");
        }

        int startInd = 0;
        int endInd = arr.length - 1;
        int bound = endInd;
        boolean swapMade = true;

        while (swapMade) {
            bound = endInd;
            swapMade = false;
            for (int i = startInd; i < bound; ++i) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapMade = true;
                    endInd = i;
                }
            }
            if (swapMade) {
                swapMade = false;
                bound = startInd;
                for (int i = endInd; i > bound; --i) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T temp = arr[i - 1];
                        arr[i - 1] = arr[i];
                        arr[i] = temp;
                        swapMade = true;
                        startInd = i;
                    }
                }
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot pass in null arguments!");
        }

        if (arr.length <= 1) {
            return;
        }

        int midIndex = arr.length / 2;

        T[] left = (T[]) new Object[midIndex];
        T[] right = (T[]) new Object[arr.length - midIndex];

        for (int i = 0; i < midIndex; ++i) {
            left[i] = arr[i];
        }

        for (int i = midIndex; i < arr.length; ++i) {
            right[i - midIndex] = arr[i];
        }

        mergeSort(left, comparator);
        mergeSort(right, comparator);

        int i = 0;
        int j = 0;
        int curr = 0;

        while (i < midIndex && j < arr.length - midIndex) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[curr++] = left[i++];
            } else {
                arr[curr++] = right[j++];
            }
        }

        while (i < left.length) {
            arr[curr++] = left[i++];
        }
        while (j < right.length) {
            arr[curr++] = right[j++];
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Cannot pass in null arguments!");
        }

        quickSortH(arr, 0, arr.length - 1, comparator, rand);
    }

    /**
     * Recursive helper for quickSort.
     * @param arr array that needs to be sorted
     * @param start the starting index of that current partition
     * @param end the ending index of the current partition
     * @param comparator the comparator used to compare objects
     * @param rand the random object used to generate random data
     * @param <T> the type of data in the array.
     */
    private static <T> void quickSortH(T[] arr, int start, int end, Comparator<T> comparator, Random rand) {
        if (end - start < 1) {
            return;
        }

        int pivotIdx = rand.nextInt(end - start + 1) + start;
        T pivotVal = arr[pivotIdx];
        arr[pivotIdx] = arr[start];
        arr[start] = pivotVal;
        int i = start + 1;
        int j = end;

        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotVal) <= 0) {
                ++i;
            }
            while (j >= i && comparator.compare(arr[j], pivotVal) >= 0) {
                --j;
            }
            if (i <= j) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                ++i;
                --j;
            }
        }

        arr[start] = arr[j];
        arr[j] = pivotVal;
        quickSortH(arr, start, j - 1, comparator, rand);
        quickSortH(arr, j + 1, end, comparator, rand);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot pass in null arguments!");
        }

        ArrayList<Integer>[] buckets = (ArrayList<Integer>[]) new ArrayList[19];

        int mod = 10;
        int div = 1;
        boolean cont = true;

        while (cont) {
            cont = false;
            for (int num : arr) {
                int currDigit = num / div;
                if (currDigit / 10 != 0) {
                    cont = true;
                }
                if (buckets[currDigit % mod + 9] == null) {
                    buckets[currDigit % mod + 9] = new ArrayList<>();
                }
                buckets[currDigit % mod + 9].add(num);
            }
            int arrIdx = 0;
            for (ArrayList<Integer> bucket : buckets) {
                if (bucket != null) {
                    for (int num : bucket) {
                        arr[arrIdx++] = num;
                    }
                    bucket.clear();
                }
            }
            div *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data cannot be null!");
        }

        PriorityQueue<Integer> heap = new PriorityQueue<>(data);
        int[] toRet = new int[data.size()];
        for (int i = 0; i < data.size(); ++i) {
            toRet[i] = heap.remove();
        }
        return toRet;
    }
}
