import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
            if (data == null) {
                throw new IllegalArgumentException("Null data cannot be inserted "
                        + "into structure!");
            }
            for (T datum : data) {
                if (datum == null) {
                    throw new IllegalArgumentException("Null data cannot be "
                            + "inserted into structure!");
                }
                this.add(datum);
            }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null "
                    + "data to structure!");
        }
        root = addH(data, root);
    }

    /**
     * Private helper method for add().
     *
     * @param data the data to be added to the BST
     * @param node the current position in BST
     * @return current node or a new Node to enable pointer reinforcement
     */
    private BSTNode<T> addH(T data, BSTNode<T> node) {
        if (node == null) {
            ++size;
            return new BSTNode<>(data);
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addH(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addH(data, node.getRight()));
        }
        return node;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data!");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = removeH(data, root, dummy);
        return dummy.getData();
    }

    /**
     * Private helper method for remove().
     *
     * @param data the data to be removed
     * @param node the current node being checked
     * @param dummy a dummy variable used to pass the data to the wrapper method
     * @return the current node in order to enable pointer reinforcement
     */
    private BSTNode<T> removeH(T data, BSTNode<T> node, BSTNode<T> dummy) {
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
                BSTNode<T> dummy2 = new BSTNode<T>(null);
                node.setRight(remSuccessor(node.getRight(), dummy2));
                node.setData(dummy2.getData());
            } else if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if (node.getLeft() != null) {
                return node.getLeft();
            } else if (node.getRight() != null) {
                return node.getRight();
            }
        }
        return node;
    }

    /**
     * Additional private helper method for remove that remove's the successor.
     *
     * @param node the current node being checked for successor
     * @param dummy2 a dummy variable used to pass the successors data to the
     *               removed node
     * @return either the current node or the successors child for pointer
     *         reinforcement
     */
    private BSTNode<T> remSuccessor(BSTNode<T> node, BSTNode<T> dummy2) {
        if (node.getLeft() == null) {
            dummy2.setData(node.getData());
            return node.getRight();
        } else {
            node.setLeft(remSuccessor(node.getLeft(), dummy2));
            return node;
        }
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
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
     * Private helper method for get() method.
     *
     * @param data the data to search for
     * @param node the node currently being checked
     * @return the data in the tree equal to the parameter
     * @throws java.util.NoSuchElementException if the data is not in tree
     */
    private T getH(T data, BSTNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("Desired data not in structure!");
        } else if (data.equals(node.getData())) {
            return node.getData();
        } else if (data.compareTo(node.getData()) < 0) {
            return getH(data, node.getLeft());
        } else {
            return getH(data, node.getRight());
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
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
     * Private helper method for contains().
     *
     * @param data the data to search for
     * @param node the current node being checked
     * @return true, if the data is found, false if not
     */
    private boolean containsH(T data, BSTNode<T> node) {
        if (node == null) {
            return false;
        } else if (data.equals(node.getData())) {
            return true;
        } else if (data.compareTo(node.getData()) < 0) {
            return containsH(data, node.getLeft());
        } else {
            return containsH(data, node.getRight());
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> traversal = new ArrayList<>(size);
        preorderH(traversal, root);
        return traversal;
    }

    /**
     * Private helper method for preorder().
     *
     * @param traversal list to add values to
     * @param node current node in the traversal
     */
    private void preorderH(ArrayList<T> traversal, BSTNode<T> node) {
        if (node != null) {
            traversal.add(node.getData());
            preorderH(traversal, node.getLeft());
            preorderH(traversal, node.getRight());
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> traversal = new ArrayList<>(size);
        inorderH(traversal, root);
        return traversal;
    }

    /**
     * Private helper method for inorder().
     *
     * @param traversal list to add values to
     * @param node current node in the traversal
     */
    private void inorderH(ArrayList<T> traversal, BSTNode<T> node) {
        if (node != null) {
            inorderH(traversal, node.getLeft());
            traversal.add(node.getData());
            inorderH(traversal, node.getRight());
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> traversal = new ArrayList<>(size);
        postorderH(traversal, root);
        return traversal;
    }

    /**
     * Private helper method for postorder().
     *
     * @param traversal list to add data to
     * @param node current node in the traversal
     */
    private void postorderH(ArrayList<T> traversal, BSTNode<T> node) {
        if (node != null) {
            postorderH(traversal, node.getLeft());
            postorderH(traversal, node.getRight());
            traversal.add(node.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        ArrayList<T> traversal = new ArrayList<>(size);
        LinkedList<BSTNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!(queue.isEmpty())) {
            BSTNode<T> node = queue.remove();
            if (node != null) {
                traversal.add(node.getData());
                queue.add(node.getLeft());
                queue.add(node.getRight());
            }
        }
        return traversal;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightH(root);
    }

    /**
     * Private helper for height().
     *
     * @param node current node being checked
     * @return height of current node
     */
    private int heightH(BSTNode<T> node) {
        if (node != null) {
            return 1 + Math.max(heightH(node.getLeft()), heightH(node.getRight()));
        } else {
            return -1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * This must be done recursively.
     * 
     * A good way to start is by finding the deepest common ancestor (DCA) of both data
     * and add it to the list. You will most likely have to split off and
     * traverse the tree for each piece of data adding to the list in such a
     * way that it will return the path in the correct order without requiring any
     * list manipulation later. One way to accomplish this (after adding the DCA
     * to the list) is to then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. 
     *
     * Please note that there is no relationship between the data parameters 
     * in that they may not belong to the same branch. 
     * 
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use considering the Big-O efficiency of the list
     * operations.
     *
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     * 
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        }
        LinkedList<T> path = new LinkedList<>();
        BSTNode<T> deepestCA = findDCA(data1, data2, root);
        findPathBetweenH(data1, deepestCA, path, true);
        path.removeLast();
        findPathBetweenH(data2, deepestCA, path, false);
        return path;
    }

    /**
     * Private helper for finding the deepest comment ancestor of two pieces of data.
     *
     * @param data1 first data to look for DCA
     * @param data2 second data to look for DCA
     * @param node the current node being checked
     * @return the deepest common ancestor of the two nodes.
     */
    private BSTNode<T> findDCA(T data1, T data2, BSTNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("Argument is not in tree!");
        } else if (node.getData().compareTo(data1) > 0 && node.getData().compareTo(data2) > 0) {
            return findDCA(data1, data2, node.getLeft());
        } else if (node.getData().compareTo(data1) < 0 && node.getData().compareTo(data2) < 0) {
            return findDCA(data1, data2, node.getRight());
        } else {
            return node;
        }
    }

    /**
     * Private helper method for findPathBetweenH(). Adds nodes in path to list
     * @param data the data that is being traversed to
     * @param node the current node being checked
     * @param path the list to add the path to
     * @param front whether or not to add to the front of the path or not
     */
    private void findPathBetweenH(T data, BSTNode<T> node, LinkedList<T> path, boolean front) {
        if (node == null) {
            throw new NoSuchElementException("Argument is not in tree!");
        } else {
            if (front) {
                path.addFirst(node.getData());
            } else {
                path.addLast(node.getData());
            }
        }
        if (node.getData().compareTo(data) > 0) {
            findPathBetweenH(data, node.getLeft(), path, front);
        } else if (node.getData().compareTo(data) < 0) {
            findPathBetweenH(data, node.getRight(), path, front);
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
    public BSTNode<T> getRoot() {
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
}
