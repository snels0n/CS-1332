import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ashwin Mudaliar
 * @version 1.1
 * 
 * Quote: "gps said its my turn"
 * 
 */

public class AVLComprehensiveTests {

    private static final int TIMEOUT = 400;
    
    AVL<Integer> tree;
    AVL<TestClass> testClassTree;

    @Before
    public void setup() {

        this.tree = new AVL<Integer>();
        this.testClassTree = new AVL<TestClass>();

    }

    // Test throws correct errors

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testConstructorIsNull() {

        this.tree = new AVL<>(null);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testConstructorElementIsNull() {

        ArrayList<Integer> arr = new ArrayList<>();

        arr.add(5);
        arr.add(8);
        arr.add(9);
        arr.add(null);

        tree = new AVL<>(arr);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testAddDataIsNull() {

        tree.add(null);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testRemoveDataIsNull() {

        tree.remove(null);

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testRemoveDataDoesNotExist() {

        tree.add(56);
        tree.add(7);
        tree.add(78);

        tree.remove(90);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testGetDataIsNull() {

        tree.get(null);

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testGetDataDoesNotExist() {

        tree.add(56);
        tree.add(7);
        tree.add(78);

        tree.get(90);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testContainsDataIsNull() {

        tree.contains(null);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testPredecessorDataIsNull() {

        tree.predecessor(null);

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testPredecessorDataDoesNotExist() {

        tree.add(56);
        tree.add(7);
        tree.add(78);

        tree.predecessor(90);

    }

    // helper methods

    private void populateTree(int[] arr) {

        for (int x : arr) {
            tree.add(x);
        }

    }

    public List<Integer> levelorder() {

        AVLNode<Integer> root = tree.getRoot();

        LinkedList<Integer> arr = new LinkedList<>();
        LinkedList<AVLNode<Integer>> q = new LinkedList<>();

        if (root == null) {
            return null;
        }

        q.addLast(root);

        while (q.size() != 0) {

            AVLNode<Integer> curr = q.removeFirst();
            arr.addLast(curr.getData());

            if (curr.getLeft() != null) {
                q.addLast(curr.getLeft());
            }

            if (curr.getRight() != null) {
                q.addLast(curr.getRight());
            }

        }

        return arr;

    }

    public int[] listToArray(List<Integer> list) {

        int[] arr = new int[list.size()];

        for (int k = 0; k < list.size(); k++) {

            arr[k] = list.get(k);

        }

        return arr;

    }

    @Test(timeout = TIMEOUT)
    public void testAddLeftRotation() {

        int[] add = {10, 9, 12, 8, 11, 13};
        populateTree(add);

        tree.add(5);

        int[] expected = {10, 8, 12, 5, 9, 11, 13};
        assertArrayEquals(expected, listToArray(levelorder()));
        assertEquals(expected.length, tree.size());

    }

    @Test(timeout = TIMEOUT)
    public void testAddRightRotation() {

        int[] add = {25, 20, 28, 10, 21, 26, 29, 31};
        populateTree(add);

        tree.add(32);

        int[] expected = {25, 20, 28, 10, 21, 26, 31, 29, 32};
        assertArrayEquals(expected, listToArray(levelorder()));
        assertEquals(expected.length, tree.size());

    }

    @Test(timeout = TIMEOUT)
    public void testAddRightLeftRotation() {

        int[] add = {40, 30, 50, 20, 35, 45, 56, 28};
        populateTree(add);

        tree.add(24);

        int[] expected = {40, 30, 50, 24, 35, 45, 56, 20, 28};
        assertArrayEquals(expected, listToArray(levelorder()));
        assertEquals(expected.length, tree.size());

    }

    @Test(timeout = TIMEOUT)
    public void testAddLeftRightRotation() {

        int[] add = {40, 30, 50, 20, 35, 45, 56, 51};
        populateTree(add);

        tree.add(53);

        int[] expected = {40, 30, 50, 20, 35, 45, 53, 51, 56};
        assertArrayEquals(expected, listToArray(levelorder()));
        assertEquals(expected.length, tree.size());

    }

    @Test(timeout = TIMEOUT)
    public void testAddWithSubtrees() {

        int[] add = {38, 18, 48, 14, 24};
        populateTree(add);

        tree.add(13);

        int[] expected = {18, 14, 38, 13, 24, 48};
        assertArrayEquals(expected, listToArray(levelorder()));
        assertEquals(expected.length, tree.size());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRightLeftRotationOneChild() {

        int[] add = {35, 19, 59, 23, 27, 37, 71, 26, 51, 66};
        populateTree(add);

        tree.remove(23);

        int[] expected = {35, 26, 59, 19, 27, 37, 71, 51, 66};
        assertArrayEquals(expected, listToArray(levelorder()));
        assertEquals(expected.length, tree.size());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLeftRightNoChild() {

        int[] add = {70, 50, 80, 30, 75, 90, 77};
        populateTree(add);

        tree.remove(90);

        int[] expected = {70, 50, 77, 30, 75, 80};
        assertArrayEquals(expected, listToArray(levelorder()));
        assertEquals(expected.length, tree.size());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLeftRotationPredecessor() {

        int[] add = {33, 18, 48, 14, 24, 38, 13};
        populateTree(add);

        tree.remove(33);

        int[] expected = {24, 14, 48, 13, 18, 38};
        assertArrayEquals(expected, listToArray(levelorder()));
        assertEquals(expected.length, tree.size());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLeftRightPredecessor() {

        int[] add = {94, 76, 101, 63, 81, 118, 68};
        populateTree(add);

        tree.remove(94);

        int[] expected = {81, 68, 101, 63, 76, 118};
        assertArrayEquals(expected, listToArray(levelorder()));
        assertEquals(expected.length, tree.size());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRemoveNoChildLeftRotation() {

        int[] add = {30, 25, 35, 15, 27, 32, 10};
        populateTree(add);

        tree.remove(27);

        int[] expected = {30, 15, 35, 10, 25, 32};
        assertArrayEquals(expected, listToArray(levelorder()));
        assertEquals(expected.length, tree.size());

    }

    /**
     * This is a pretty challenging test, here's where I got the beginning and final tree from:
     * https://courses.cs.washington.edu/courses/cse373/12sp/exams/final-12sp-solution.pdf
     */
    @Test(timeout = TIMEOUT)
    public void testRemoveMultipleRotations() {

        int[] add = {5, 3, 10, 2, 4, 7, 11, 1, 6, 9, 12, 8};
        populateTree(add);

        tree.remove(5);

        System.out.println(levelorder());

        int[] expected = {7, 4, 10, 2, 6, 9, 11, 1, 3, 8, 12};
        assertArrayEquals(expected, listToArray(levelorder()));
        assertEquals(expected.length, tree.size());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRightLeftRotationWithSubtree() {

        int[] add = {36, 23, 53, 28, 41, 70, 38, 44};
        populateTree(add);

        tree.remove(36);

        int[] expected = {41, 28, 53, 23, 38, 44, 70};
        assertArrayEquals(expected, listToArray(levelorder()));
        assertEquals(expected.length, tree.size());

    }

    // remove w/ multiple nodes + subtrees

    @Test(timeout = TIMEOUT)
    public void testRemoveLLImbalance() {

        int[] add = {100, 90, 110, 80, 95, 105, 70, 85, 92, 97};
        populateTree(add);

        tree.remove(105);

        int[] expected = {90, 80, 100, 70, 85, 95, 110, 92, 97};
        assertArrayEquals(expected, listToArray(levelorder()));
        assertEquals(expected.length, tree.size());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRLImbalance() {

        int[] add = {30, 20, 40, 10, 35, 60, 32, 37};
        populateTree(add);

        tree.remove(10);

        int[] expected = {35, 30, 40, 20, 32, 37, 60};
        assertArrayEquals(expected, listToArray(levelorder()));
        assertEquals(expected.length, tree.size());
        
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRRImbalance() {

        int[] add = {30, 20, 40, 10, 35, 50, 32, 37, 45, 55};
        populateTree(add);

        tree.remove(10);

        int[] expected = {40, 30, 50, 20, 35, 45, 55, 32, 37};
        assertArrayEquals(expected, listToArray(levelorder()));
        assertEquals(expected.length, tree.size());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLRImbalance() {

        int[] add = {70, 50, 80, 40, 60, 90, 55, 65};
        populateTree(add);

        tree.remove(90);

        int[] expected = {60, 50, 70, 40, 55, 65, 80};
        assertArrayEquals(expected, listToArray(levelorder()));
        assertEquals(expected.length, tree.size());

    }

    @Test(timeout = TIMEOUT)
    public void testGet() {

        testClassTree.add(new TestClass(20));
        testClassTree.add(new TestClass(10));
        testClassTree.add(new TestClass(30));
        testClassTree.add(new TestClass(-400));
        testClassTree.add(new TestClass(15));
        testClassTree.add(new TestClass(25));
        testClassTree.add(new TestClass(35));

        assertEquals(new TestClass(-350), testClassTree.get(new TestClass(-400)));
        assertEquals(new TestClass(-400), testClassTree.get(new TestClass(-350)));

    }

    @Test(timeout = TIMEOUT)
    public void testContains() {

        testClassTree.add(new TestClass(20));
        testClassTree.add(new TestClass(10));
        testClassTree.add(new TestClass(30));
        testClassTree.add(new TestClass(-400));
        testClassTree.add(new TestClass(15));
        testClassTree.add(new TestClass(25));
        testClassTree.add(new TestClass(35));

        assertEquals(true, testClassTree.contains(new TestClass(-350)));
        assertEquals(false, testClassTree.contains(new TestClass(56)));

    }

    @Test(timeout = TIMEOUT)
    public void testHeightSimple() {

        int[] add = {70, 50, 80, 40, 60, 90, 55, 65};
        populateTree(add);

        assertEquals(3, tree.height());

    }

    @Test(timeout = TIMEOUT)
    public void testHeightRemoveRecalc() {

        int[] add = {70, 50, 80, 40, 60, 90, 55, 65};
        populateTree(add);

        assertEquals(3, tree.height());

        tree.remove(90);
        assertEquals(2, tree.height());

    }

    @Test(timeout = TIMEOUT)
    public void testHeightAddNoRecalc() {
        int[] add = {40, 30, 50, 20, 35, 45, 56, 28};
        populateTree(add);

        assertEquals(3, tree.height());

        tree.add(24);

        assertEquals(3, tree.height());
    }

    @Test(timeout = TIMEOUT)
    public void testHeightAddRecalc() {

        int[] add = {30, 20};
        populateTree(add);

        assertEquals(1, tree.height());
        tree.add(10);
        tree.add(25);

        assertEquals(2, tree.height());

    }

    @Test(timeout = TIMEOUT)
    public void testClear() {

        int[] add = {67, 24, 12, 56, 8, 3, 7, 24, 246, 468, 69};
        populateTree(add);

        tree.clear();

        assertEquals(0, tree.size());
        assertEquals(null, tree.getRoot());

    }

    @Test(timeout = TIMEOUT)
    public void testPredecessor() {

        int[] add = {5, 3, 10, 2, 4, 7, 11, 1, 6, 9, 12, 8};
        populateTree(add);

        assertEquals((Integer) 4, tree.predecessor(5));
        assertEquals((Integer) 9, tree.predecessor(10));
        assertEquals((Integer) 7, tree.predecessor(8));
        assertEquals(null, tree.predecessor(1));
        assertEquals((Integer) 11, tree.predecessor(12));
        assertEquals((Integer) 5, tree.predecessor(6));

    }

    @Test(timeout = TIMEOUT)
    public void testMaxDeepestNode1() {

        int[] add = {5, 3, 10, 2, 4, 7, 11, 1, 6, 9, 12, 8};
        populateTree(add);

        assertEquals((Integer) 8, tree.maxDeepestNode());

    }

    @Test(timeout = TIMEOUT)
    public void testMaxDeeptestNode2() {

        int[] add = {5, 3, 10, 2, 4, 7, 11, 1, 6, 9, 12};
        populateTree(add);

        assertEquals((Integer) 12, tree.maxDeepestNode());

    }

    @Test(timeout = TIMEOUT)
    public void testMaxDeepestNode3() {

        int[] add = {40, 30, 50, 20, 35, 45, 56, 28};
        populateTree(add);

        assertEquals((Integer) 28, tree.maxDeepestNode());

    }

    @Test(timeout = TIMEOUT)
    public void testMaxDeepestNode4() {

        int[] add = {28};
        populateTree(add);

        assertEquals((Integer) 28, tree.maxDeepestNode());

    }

    @Test(timeout = TIMEOUT)
    public void testMaxDeepestNode5() {

        int[] add = {32, 28};
        populateTree(add);

        assertEquals((Integer) 28, tree.maxDeepestNode());

    }
    
    @Test(timeout = TIMEOUT)
    public void testMaxDeepestNode6() {

        int[] add = {32, 28, 89};
        populateTree(add);

        assertEquals((Integer) 89, tree.maxDeepestNode());

    }

    @Test(timeout = TIMEOUT)
    public void testPredecessorOnlyRoot() {

        tree.add(89);
        assertEquals(null, tree.predecessor(89));

    }

    @Test(timeout = TIMEOUT)
    public void testMaxDeepestNodeEmptyTree() {

        tree.clear();
        assertEquals(null, tree.maxDeepestNode());

    }

}

class TestClass implements Comparable<TestClass> {

    int data;

    public TestClass(int dataIn) {
        data = dataIn;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || !o.getClass().equals(TestClass.class)) {
            return false;
        } else {

            if ((data == -400 && ((TestClass) o).data == -350) || (data == -350 && ((TestClass) o).data == -400)) {
                return true;
            }

            return data == ((TestClass) o).data;

        }

    }

    @Override
    public int compareTo(TestClass o) {
        if ((data == -400 && ((TestClass) o).data == -350) || (data == -350 && ((TestClass) o).data == -400)) {
                return 0;
            }

            return data - ((TestClass) o).data;
    }

}
