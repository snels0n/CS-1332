import org.junit.Test;

import java.util.*;
import java.util.function.Function;

import static org.junit.Assert.*;

/**
 * A basic stress test and other additional tests for AVL.
 *
 * @author Alexander Gualino
 * @version 1.1
 */
public class ExhaustiveTests {
    private static final int TIMEOUT = 200;

    // basic stress test
    @Test(timeout = TIMEOUT)
    public void stressTestInvariants() {
        var tree = new AVL<Integer>();
        var rand = new Random();
        var seed = rand.nextLong();
        System.out.println("seed is " + seed + "L; replace `rand.nextLong()` with that to reproduce this test run");
        rand.setSeed(seed);

        var duplicates = new HashSet<Integer>();
        for (var i = 0; i < 100; i++) {
            var startSize = tree.size();
            var data = rand.nextInt();
            tree.add(data);

            if (!duplicates.contains(data)) {
                // adding non-duplicate things to the tree should increase the size
                assertEquals(startSize + 1, tree.size());
            }

            duplicates.add(data);
        }

        // try removing a few elements (this is deterministic based on seed, I think)
        var count = 0;
        for (var duplicate : duplicates) {
            count++;
            var startSize = tree.size();
            tree.remove(duplicate);
            assertEquals(startSize - 1, tree.size());
            if (count == 15) {
                break;
            }
        }

        duplicates.clear();

        this.checkInvariants(tree);
    }

    private <T extends Comparable<? super T>> void checkInvariants(AVL<T> tree) {
        // invariants checked:
        //   - BF of every node should be left - right
        //   - BF should be -1, 0, 1
        //   - height should be max(left.height, right.height) + 1
        //   - left should be < and right should be >

        var q = new ArrayDeque<AVLNode<T>>();
        assertNotNull(tree.getRoot());
        q.add(tree.getRoot());

        while (!q.isEmpty()) {
            var element = q.remove();
            assertTrue(element.getBalanceFactor() >= -1);
            assertTrue(element.getBalanceFactor() <= 1);

            if (element.getLeft() != null) {
                assertTrue(element.getData().compareTo(element.getLeft().getData()) > 0);
            }

            if (element.getRight() != null) {
                assertTrue(element.getData().compareTo(element.getRight().getData()) < 0);
            }

            // yes, this is mainly just checking you call `update` everywhere....
            var leftHeight = element.getLeft() == null ? -1 : element.getLeft().getHeight();
            var rightHeight = element.getRight() == null ? -1 : element.getRight().getHeight();
            assertEquals(leftHeight - rightHeight, element.getBalanceFactor());
            assertEquals(Math.max(leftHeight, rightHeight) + 1, element.getHeight());

            if (element.getLeft() != null) {
                q.add(element.getLeft());
            }
            if (element.getRight() != null) {
                q.add(element.getRight());
            }
        }
    }

    // exhaustive tests
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddNullData() {
        new AVL<>().add(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNullData() {
        new AVL<>().remove(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveMissingData() {
        var tree = new AVL<Integer>();
        tree.add(1);
        tree.remove(2);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetNullData() {
        new AVL<>().get(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetMissingData() {
        var tree = new AVL<Integer>();
        tree.add(1);
        tree.get(2);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testContainsNullData() {
        new AVL<>().contains(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPredecessorNullData() {
        new AVL<>().predecessor(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testPredecessorMissingData() {
        var tree = new AVL<Integer>();
        tree.add(1);
        tree.predecessor(2);
    }

    @Test(timeout = TIMEOUT)
    public void testSingleRotation() {
        // inverting all numbers = flipping tree horizontally
        for (var factor : new int[]{-1, 1}) {
            var tree = new AVL<Integer>();
            tree.add(factor * 3);
            tree.add(factor * 2);
            tree.add(factor);

            Function<AVLNode<Integer>, AVLNode<Integer>> l = factor == 1 ? AVLNode::getLeft : AVLNode::getRight;
            Function<AVLNode<Integer>, AVLNode<Integer>> r = factor == 1 ? AVLNode::getRight : AVLNode::getLeft;

            assertEquals(factor * 2, (int) tree.getRoot().getData());
            assertEquals(factor, (int) l.apply(tree.getRoot()).getData());
            assertEquals(factor * 3, (int) r.apply(tree.getRoot()).getData());
            this.checkInvariants(tree);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testDoubleRotation() {
        // (double rotation == right-left / left-right. I don't know the generic term.)
        for (var factor : new int[]{-1, 1}) {
            var tree = new AVL<Integer>();
            tree.add(factor * 3);
            tree.add(factor);
            tree.add(factor * 2);

            Function<AVLNode<Integer>, AVLNode<Integer>> l = factor == 1 ? AVLNode::getLeft : AVLNode::getRight;
            Function<AVLNode<Integer>, AVLNode<Integer>> r = factor == 1 ? AVLNode::getRight : AVLNode::getLeft;

            assertEquals(factor * 2, (int) tree.getRoot().getData());
            assertEquals(factor, (int) l.apply(tree.getRoot()).getData());
            assertEquals(factor * 3, (int) r.apply(tree.getRoot()).getData());
            this.checkInvariants(tree);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testDoubleRotationRemoval() {
        for (var factor : new int[]{-1, 1}) {
            var tree = new AVL<Integer>();
            tree.add(factor * 5);
            tree.add(factor);
            tree.add(factor * 6);
            tree.add(0);
            tree.add(factor * 3);
            tree.add(factor * 7);
            tree.add(factor * 2);
            tree.add(factor * 4);

            // shouldn't have rotated at this point
            tree.remove(factor * 6);

            Function<AVLNode<Integer>, AVLNode<Integer>> l = factor == 1 ? AVLNode::getLeft : AVLNode::getRight;
            Function<AVLNode<Integer>, AVLNode<Integer>> r = factor == 1 ? AVLNode::getRight : AVLNode::getLeft;

            assertEquals(factor * 3, (int) tree.getRoot().getData());
            assertEquals(factor, (int) l.apply(tree.getRoot()).getData());
            assertEquals(factor * 2, (int) r.apply(l.apply(tree.getRoot())).getData());

            assertEquals(factor * 5, (int) r.apply(tree.getRoot()).getData());
            assertEquals(factor * 4, (int) l.apply(r.apply(tree.getRoot())).getData());
            this.checkInvariants(tree);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testAddDuplicateData() {
        var tree = new AVL<Integer>();
        tree.add(1);
        assertEquals(1, tree.size());
        tree.add(1);
        assertEquals(1, tree.size());

        this.checkInvariants(tree);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRebalancesRemovedNode() {
        var tree = new AVL<Integer>();
        tree.add(2);
        tree.add(1);
        tree.add(3);
        tree.add(4);
        tree.remove(2);
        this.checkInvariants(tree);
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessorTraversal() {
        var tree = new AVL<Integer>();
        tree.add(2);
        tree.add(0);
        tree.add(3);
        tree.add(1);
        tree.remove(2);

        assertEquals(1, (int) tree.getRoot().getData());
        this.checkInvariants(tree);
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessorWithChild() {
        var tree = new AVL<Integer>();
        tree.add(4);
        tree.add(1);
        tree.add(5);
        tree.add(3);
        tree.add(0);
        tree.add(6);
        tree.add(2);

        tree.remove(4);
        assertEquals(2, (int) tree.getRoot().getLeft().getRight().getData());
        this.checkInvariants(tree);
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessorRebalancing() {
        var tree = new AVL<Integer>();
        tree.add(5);
        tree.add(3);
        tree.add(6);
        tree.add(2);
        tree.add(4);
        tree.add(7);
        tree.add(1);

        tree.remove(5);
        this.checkInvariants(tree);
    }

    @Test(timeout = TIMEOUT)
    public void testGetTwoLevels() {
        for (var factor : new int[]{-1, 1}) {
            var tree = new AVL<Integer>();
            tree.add(2 * factor);
            tree.add(factor);
            tree.add(3 * factor);
            tree.add(4 * factor);

            assertEquals(factor * 4, (int) tree.get(4 * factor));
            this.checkInvariants(tree);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        var tree = new AVL<Integer>();
        for (var i = 0; i < 10; i++) {
            tree.add(i);
        }

        assertTrue(tree.contains(9));
        assertFalse(tree.contains(10));
        assertTrue(tree.contains(0));
        assertFalse(tree.contains(-1));
        this.checkInvariants(tree);
    }

    @Test(timeout = TIMEOUT)
    public void testHeightOfEmptyTree() {
        assertEquals(-1, new AVL<>().height());
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessorSimple() {
        var tree = new AVL<Integer>();
        tree.add(1);
        tree.add(0);
        tree.add(3);
        tree.add(2);

        assertEquals(2, (int) tree.predecessor(3));
        this.checkInvariants(tree);
    }

    @Test(timeout = TIMEOUT)
    public void testDeepestNodeEmptyTree() {
        assertNull(new AVL<>().maxDeepestNode());
    }

    @Test(timeout = TIMEOUT)
    public void testDeepestNodeGoLeft() {
        var tree = new AVL<Integer>();
        tree.add(1);
        tree.add(0);
        assertEquals(0, (int) tree.maxDeepestNode());
        this.checkInvariants(tree);
    }
}
