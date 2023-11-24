import java.util.NoSuchElementException;
/**
 * Your implementation of an ArrayList.
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
public class ArrayListV2<T> {

    /**
     * The initial capacity of the ArrayList.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayListV2() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Adds the element to the specified index.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Inputted data can't be null!");
        } else if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("You put in index " + index
            + " which isn't in the range of the array: 0 and " + size);
        }

        if (size == backingArray.length) {
            T[] newArray = (T[]) new Object[backingArray.length * 2];
            for (int i = size - 1; i >= index; --i) {
                newArray[i + 1] = backingArray[i];
            }
            for (int i = index - 1; i >= 0; --i) {
                newArray[i] = backingArray[i];
            }

            backingArray = newArray;
        } else {
            for (int i = size - 1; i >= index; --i) {
                backingArray[i + 1] = backingArray[i];
            }
        }

        backingArray[index] = data; // Adds in data to the ArrayList at index.
        ++size;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }

        addAtIndex(0,data);
        // Updates ArrayList capacity if necessary
//        if (size == backingArray.length) {
//            T[] newArray = (T[]) new Object[backingArray.length * 2];
//            for (int i = 0; i < backingArray.length; ++i) {
//                newArray[i] = backingArray[i];
//            }
//            backingArray = newArray;
//        }
//
//        // Moves over every element in the list to the right.
//        for (int i = size - 1; i >= 0; --i) {
//            backingArray[i + 1] = backingArray[i];
//        }
//
//        // Adds the data to the ArrayList at the front.
//        backingArray[0] = data;
//        ++size;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }

        addAtIndex(size, data);
//        if (size == backingArray.length) {
//            T[] newArray = (T[]) new Object[backingArray.length * 2];
//            for (int i = 0; i < backingArray.length; ++i) {
//                newArray[i] = backingArray[i];
//            }
//            backingArray = newArray;
//        }
//
//        backingArray[size] = data;
//        ++size;
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        int maxIndex = size - 1;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("You inputted index " + index
                    + " which isn't in the array bounds of zero : " + maxIndex);
        }

        if (index == 0) {
            return this.removeFromFront();
        } else if (index == size - 1) {
            return this.removeFromBack();
        }

        T toReturn = backingArray[index];
        for (int i = index; i < size - 1; ++i) {
            backingArray[i] = backingArray[i + 1];
        }
        backingArray[size - 1] = null;
        --size;
        return toReturn;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("This list is empty!");
        }

        T toReturn = backingArray[0];
        for (int i = 0; i < size - 1; ++i) {
            backingArray[i] = backingArray[i + 1];
        }
        backingArray[size - 1] = null;
        --size;
        return toReturn;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty!");
        }
        T toReturn = backingArray[size - 1];
        backingArray[size - 1] = null;
        --size;
        return toReturn;
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        int maxIndex = size - 1;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("You inputted index " + index
            + " which is out of bounds for the array with bounds of zero to "
            + maxIndex);
        }
        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the list.
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
     * Returns the size of the list.
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