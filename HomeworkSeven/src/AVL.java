import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be inserted into structure!");
        }
        for (T datum : data) {
            if (datum == null) {
                throw new IllegalArgumentException("Null data cannot be inserted into structure!");
            }
            this.add(datum);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data!");
        }
        root = addH(data, root);
    }

    /**
     * Private recursive helper method for add method.
     * @param data the data to add to tree
     * @param node the current node
     * @return the current node for pointer reinforcement
     */
    private AVLNode<T> addH(T data, AVLNode<T> node) {
        if (node == null) {
            ++size;
            node = new AVLNode<>(data);
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addH(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addH(data, node.getRight()));
        }
        node = balance(node);
        return node;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data!");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = removeH(data, root, dummy);
        return dummy.getData();
    }

    /**
     * Private recursive helper method for remove.
     * @param data the data that is being removed from the tree
     * @param node the current node
     * @param dummy a dummy node to return the removed data
     * @return The current node for pointer reinforcement
     */
    private AVLNode<T> removeH(T data, AVLNode<T> node, AVLNode<T> dummy) {
        if (node == null) {
            throw new NoSuchElementException("Data not in structure!");
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeH(data, node.getLeft(), dummy));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeH(data, node.getRight(), dummy));
        } else {
            dummy.setData(node.getData());
            --size;
            if (node.getLeft() != null && node.getRight() != null) {
                AVLNode<T> dummy2 = new AVLNode<>(null);
                node.setLeft(remPredecessor(node.getLeft(), dummy2));
                node.setData(dummy2.getData());
            } else if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if (node.getLeft() != null) {
                return node.getLeft();
            } else if (node.getRight() != null) {
                return node.getRight();
            }
        }
        node = balance(node);
        return node;
    }

    /**
     * Private recursive helper method that removes predecessor in 2-child remove case
     * @param curr the current node
     * @param dummy2 dummy node used to pass the predecessors data to calling function
     * @return the current node for pointer reinforcement
     */
    private AVLNode<T> remPredecessor(AVLNode<T> curr, AVLNode<T> dummy2) {
        if (curr.getRight() == null) {
            dummy2.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(remPredecessor(curr.getRight(), dummy2));
            curr = balance(curr);
            return curr;
        }
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Inputted data cannot be null!");
        }
        return getH(data, root);
    }

    /**
     * Private recursive helper method for get.
     * @param data the data that is being searched for
     * @param curr the current node
     * @return the data of the node once its found
     */
    private T getH(T data, AVLNode<T> curr) {
        if (curr == null) {
            throw new NoSuchElementException("Desired data not in structure!");
        } else if (data.equals(curr.getData())) {
            return curr.getData();
        } else if (data.compareTo(curr.getData()) < 0) {
            return getH(data, curr.getLeft());
        } else {
            return getH(data, curr.getRight());
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Inputted data cannot be null!");
        }
        return containsH(data, root);
    }

    /**
     * Private helper method for contains.
     * @param data the data that is being searched for
     * @param curr the current node that is being checked
     * @return whether or not the data is in the tree
     */
    private boolean containsH(T data, AVLNode<T> curr) {
        if (curr == null) {
            return false;
        } else if (data.equals(curr.getData())) {
            return true;
        } else if (data.compareTo(curr.getData()) < 0) {
            return containsH(data, curr.getLeft());
        } else {
            return containsH(data, curr.getRight());
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return (root == null ? -1 : root.getHeight());
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     *
     * Should be recursive.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot input null!");
        }
        return predecessorH(data, root);
    }

    /**
     * Private helper method to return the predecessor of a piece of data.
     * @param data the data that is being found
     * @param curr the current node
     * @return the value of the predecessor once its found
     */
    private T predecessorH(T data, AVLNode<T> curr) {
        if (curr == null) {
            throw new NoSuchElementException("Data not in tree!");
        }

        T toRet = null;
        if (data.compareTo(curr.getData()) > 0) {
            toRet = predecessorH(data, curr.getRight());
        } else if (data.compareTo(curr.getData()) < 0) {
            toRet = predecessorH(data, curr.getLeft());
        } else if (data.compareTo(curr.getData()) == 0) {
            return findPred(curr.getLeft());
        }
        if (toRet != null) {
            return toRet;
        } else if (curr.getData().compareTo(data) < 0) {
            return curr.getData();
        } else {
            return null;
        }
    }

    /**
     * Private helper method for finding the predecessor of a node with a left subtree
     * @param curr the current node being checked
     * @return the predecessor once its found
     */
    private T findPred(AVLNode<T> curr) {
        if (curr != null) {
            if (curr.getRight() == null) {
                return curr.getData();
            } else {
                return findPred(curr.getRight());
            }
        } else {
            return null;
        }
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     *
     * Should be recursive.
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        return (root == null ? null : maxDeepestNodeH(root));
    }

    /**
     * Private recursive helper method for maxDeepestNode.
     * @param curr the current node
     * @return the data at the deepest node
     */
    private T maxDeepestNodeH(AVLNode<T> curr) {
        if (curr.getLeft() == null && curr.getRight() == null) {
            return curr.getData();
        } else if (curr.getBalanceFactor() == 0 || curr.getBalanceFactor() < 0) {
            return maxDeepestNodeH(curr.getRight());
        } else {
            return maxDeepestNodeH(curr.getLeft());
        }
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Private helper method to update the height and balance factor of a node.
     * @param curr The Node to be updated.
     */
    private void update(AVLNode<T> curr) {
        if (curr != null) {
            int leftH = curr.getLeft() == null ? -1 : curr.getLeft().getHeight();
            int rightH = curr.getRight() == null ? -1 : curr.getRight().getHeight();
            curr.setHeight(Math.max(leftH, rightH) + 1);
            curr.setBalanceFactor(leftH - rightH);
        }
    }

    /**
     * Rotates an AVL node
     * @param toRotate the node to be rotated
     * @return a new node at the position of toRotate
     */
    private AVLNode<T> rightRotation(AVLNode<T> toRotate) {
        AVLNode<T> newParent = toRotate.getLeft();
        toRotate.setLeft(newParent.getRight());
        newParent.setRight(toRotate);
        update(toRotate);
        update(newParent);
        return newParent;
    }

    /**
     * Rotates an AVL node
     * @param toRotate the node to be rotated
     * @return a new node at the position of toRotate
     */
    private AVLNode<T> leftRotation(AVLNode<T> toRotate) {
        AVLNode<T> newParent = toRotate.getRight();
        toRotate.setRight(newParent.getLeft());
        newParent.setLeft(toRotate);
        update(toRotate);
        update(newParent);
        return newParent;
    }

    /**
     * Balances an AVL node based on BF
     * @param curr the node that is being checked
     * @return a node based on pointer reinforcement
     */
    private AVLNode<T> balance(AVLNode<T> curr) {
        update(curr);
        if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rightRotation(curr.getRight()));
            }
            curr = leftRotation(curr);
        } else if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(leftRotation(curr.getLeft()));
            }
            curr = rightRotation(curr);
        }
        return curr;
    }
}
