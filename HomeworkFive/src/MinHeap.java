import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
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
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        this.backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Argument cannot be null!");
        }
        this.backingArray = (T[]) new Comparable[data.size() * 2 + 1];
        this.size = data.size();
        for (int i = 0; i < size; ++i) {
            if (data.get(i) != null) {
                this.backingArray[i + 1] = data.get(i);
            } else {
                throw new IllegalArgumentException("Cannot insert null elements into structure!");
            }
        }
        for (int i = size / 2; i >= 1; --i) {
            downHeap(i);
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into structure!");
        } else if (size == backingArray.length - 1) {
            T[] newArr = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i <= size; ++i) {
                newArr[i] = backingArray[i];
            }
            backingArray = newArr;
        }
        backingArray[++size] = data;
        int currIdx = size;
        while (currIdx / 2 != 0 && backingArray[currIdx].compareTo(backingArray[currIdx / 2]) < 0) {
            T temp = backingArray[currIdx / 2];
            backingArray[currIdx / 2] = backingArray[currIdx];
            backingArray[currIdx] = temp;
            currIdx /= 2;
        }
    }

    /**
     * Private helper method to perform down-heap on a given heap element.
     *
     * @param currIdx the index of the element in the backing array.
     */
    private void downHeap(int currIdx) {
        while (true) {
            if (currIdx > size / 2 || backingArray[currIdx * 2] == null) {
                break;
            }
            if (backingArray[currIdx * 2 + 1] == null || backingArray[currIdx * 2].compareTo(backingArray[currIdx * 2 + 1]) < 0) {
                if (backingArray[currIdx * 2].compareTo(backingArray[currIdx]) < 0) {
                    T temp = backingArray[currIdx * 2];
                    backingArray[currIdx * 2] = backingArray[currIdx];
                    backingArray[currIdx] = temp;
                    currIdx = currIdx * 2;
                } else {
                    break;
                }
            } else {
                if (backingArray[currIdx * 2 + 1].compareTo(backingArray[currIdx]) < 0) {
                    T temp = backingArray[currIdx * 2 + 1];
                    backingArray[currIdx * 2 + 1] = backingArray[currIdx];
                    backingArray[currIdx] = temp;
                    currIdx = currIdx * 2 + 1;
                } else {
                    break;
                }
            }
        }
    }
    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The structure is empty!");
        }
        T removed = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size--] = null;
        downHeap(1);
        return removed;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty!");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        this.backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
