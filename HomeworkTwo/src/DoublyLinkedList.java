import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + " is "
            + "invalid for the list with bounds of zero and " + size);
        } else if (data == null) {
            throw new IllegalArgumentException("Inserted data can't be null!");
        }

        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            // Creates new Node
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
            // Decides whether to start with head or tail of list
            DoublyLinkedListNode<T> curr;
            if (index < size / 2) {
                // Initializes current Node and advances it to one before the desired index
                curr = head;
                for (int i = 0; i < index - 1; ++i) {
                    curr = curr.getNext();
                }
                // Sets the new Nodes previous to curr (one before index) and next to curr's next
                newNode.setPrevious(curr);
                newNode.setNext(curr.getNext());
                // Sets the Node after the new Node's previous to the new Node
                curr.getNext().setPrevious(newNode);
                // Sets curr's next to be the new Node
                curr.setNext(newNode);
            } else {
                curr = tail;
                for (int i = size - 1; i > index; --i) {
                    curr = curr.getPrevious();
                }
                newNode.setNext(curr);
                newNode.setPrevious(curr.getPrevious());

                curr.getPrevious().setNext(newNode);

                curr.setPrevious(newNode);
            }
            ++size;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Inserted data can't be null!");
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
        if (size == 0) {
            tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrevious(newNode);
        }
        head = newNode;

        ++size;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Inserted data can't be null!");
        }

        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
        if (size == 0) {
            head = newNode;
        } else {
            newNode.setPrevious(tail);
            tail.setNext(newNode);
        }
        tail = newNode;

        ++size;
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " is "
                    + "invalid for the list with bounds of zero and "
                    + (size - 1));
        }

        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else if (index < size / 2) {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index - 1; ++i) {
                curr = curr.getNext();
            }
            T toReturn = curr.getNext().getData();
            // curr.getNext().getNext().setPrevious(curr);
            curr.setNext(curr.getNext().getNext());
            // curr.setNext(curr.getNext().getNext());
            curr.getNext().setPrevious(curr);
            --size;
            return toReturn;
        } else {
            DoublyLinkedListNode<T> curr = tail;
            for (int i = size - 1; i > index + 1; --i) {
                curr = curr.getPrevious();
            }
            T toReturn = curr.getPrevious().getData();
            // curr.getPrevious().getPrevious().setNext(curr);
            curr.setPrevious(curr.getPrevious().getPrevious());
            // curr.setPrevious(curr.getPrevious().getPrevious());
            curr.getPrevious().setNext(curr);
            --size;
            return toReturn;
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty!");
        }

        T toReturn = head.getData();
        head = head.getNext();
        if (size > 1) {
            head.setPrevious(null);
        }
        --size;
        if (size == 0) {
            tail = null;
            head = null;
        }
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

        T toReturn = tail.getData();
        tail = tail.getPrevious();
        if (size > 1) {
            tail.setNext(null);
        }
        --size;
        if (size == 0) {
            head = null;
            tail = null;
        }
        return toReturn;
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("You inputted index: " + index
            + " which is out of the bounds of the data structure!");
        }

        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            DoublyLinkedListNode<T> curr;
            if (index < size / 2) {
                curr = head;
                for (int i = 0; i < index; ++i) {
                    curr = curr.getNext();
                }
            } else {
                curr = tail;
                for (int i = size - 1; i > index; --i) {
                    curr = curr.getPrevious();
                }
            }
            return curr.getData();
        }

    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        } else if (size == 0) {
            throw new NoSuchElementException("The list is empty!");
        }

        if (tail.getData().equals(data)) {
            return removeFromBack();
        } else {
            DoublyLinkedListNode<T> curr = tail;
            while (curr.getPrevious() != null) {
                if (curr.getPrevious().getData().equals(data)) {
                    T toRemove = curr.getPrevious().getData();
                    if (curr.getPrevious() == head) {
                        head = curr;
                    }
                    curr.setPrevious(curr.getPrevious().getPrevious());
                    if (curr.getPrevious() != null) {
                        curr.getPrevious().setNext(curr);
                    }
                    --size;
                    if (size == 0) {
                        head = null;
                        tail = null;
                    }
                    return toRemove;
                } else {
                    curr = curr.getPrevious();
                }
            }
            if (head.getData().equals(data)) {
                return removeFromFront();
            }
            throw new NoSuchElementException("Data not found in list!");
        }
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] toReturn = new Object[size];
        DoublyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; ++i) {
            toReturn[i] = curr.getData();
            curr = curr.getNext();
        }
        return toReturn;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
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
        // DO NOT MODIFY!
        return size;
    }
}
