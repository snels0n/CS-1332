import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * CS 1332 HW 7
 * ArcWand tests for AVL
 *
 * @author Robert Zhu
 * @version 1.0
 */
public class ArcWandAVLTests {

    private static final int TIMEOUT = 200;
    private AVL<Integer> tree;
    private String expected;

    /**
     * Helper method that prints a pretty representation of an AVL tree,
     * including height and balance factor.
     *
     * @param tree the AVL tree to print
     */
    public static void printTree(AVL<Integer> tree) {
        System.out.println(toString(tree));
    }
    private void p() {
        printTree(tree);
    }
    private void p(String s) {
        System.out.println(s);
        printTree(tree);
    }

    /** 
     * Helper method that returns a pretty representation of an AVL tree,
     * including height and balance factor.
     *
     * @param tree the AVL tree to print
     */
    public static String toString(AVL<Integer> tree) {
        StringBuilder sb = new StringBuilder();
        sb.append("tree:\n");
        toStringHelper(tree.getRoot(), "", true, sb);
        return sb.toString();
    }
    private static void toStringHelper(AVLNode<Integer> root, String indent, boolean last, StringBuilder sb) {
        if (root != null) {
            sb.append(indent);
            if (last) {
                sb.append("R----");
                indent += "     ";
            } else {
                sb.append("L----");
                indent += "|    ";
            }

            String hb = "(" + root.getHeight() + "," + root.getBalanceFactor() + ")";
            sb.append(root.getData() + hb + "\n");
            toStringHelper(root.getLeft(), indent, false, sb);
            toStringHelper(root.getRight(), indent, true, sb);
        }
    }

    /**
     * Helper method to print out the expected and actual trees when an
     * assertion fails.
     *
     * @param expected the expected tree
     * @param actual the actual tree
     */
    public static void debugAssertEquals(String expected, String actual) {
        if (!expected.equals(actual)) {
            System.out.println("Expected:");
            System.out.println(expected);
            System.out.println("Actual:");
            System.out.println(actual);
        }
        assertEquals(expected, actual);
    }
    public void debugAssertEquals(String expected) {
        debugAssertEquals(expected, toString(tree));
    }
    public void debugAssertEquals(int expected, int actual) {
        if (expected != actual) {
            p("Expected: " + expected + ", Actual: " + actual);
        }
        assertEquals(expected, actual);
    }


    @Before
    public void setup() {
        tree = new AVL<>();
    }

    @Test(timeout = TIMEOUT)
    public void test_Noargs_Constructor() {
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_Constructor_Null_Collection() {
        List<Integer> list = null;
        tree = new AVL<>(list);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_Constructor_Collection_Containing_Null() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(null);
        list.add(3);
        tree = new AVL<>(list);
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor_Adds_Correctly() {
        // Add 1 through 7
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7);
        tree = new AVL<>(list);
        assertEquals(7, tree.size());

        expected = "tree:\n"
                + "R----4(2,0)\n"
                + "     L----2(1,0)\n"
                + "     |    L----1(0,0)\n"
                + "     |    R----3(0,0)\n"
                + "     R----6(1,0)\n"
                + "          L----5(0,0)\n"
                + "          R----7(0,0)\n";
        debugAssertEquals(expected);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_add_Null_Data() {
        tree.add(null);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_Add_to_Empty() {
        tree.add(1);

        assertEquals(1, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals(1, (int) root.getData());
        assertEquals(0, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertNull(root.getLeft());
        assertNull(root.getRight());
    }

    @Test(timeout = TIMEOUT)
    public void test_add_Increments_Size() {
        for (int i = 0; i < 10; i++) {
            tree.add(i);
            assertEquals(i + 1, tree.size());
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_add_Duplicate_does_not_change_Size() {
        tree.add(1);
        tree.add(1);
        assertEquals(1, tree.size());

        tree.add(2);
        tree.add(2);
        assertEquals(2, tree.size());
    }

    @Test(timeout = TIMEOUT)
    public void test_add_Duplicate_does_not_Add() {
        tree.add(1);
        tree.add(1);
        assertEquals(1, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals(1, (int) root.getData());
        assertEquals(0, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertNull(root.getLeft());
        assertNull(root.getRight());

        for (int i = 0; i < 10; i++) {
            tree.add(i/2);
        }

        String expected = "tree:\n"
                + "R----1(2,-1)\n"
                + "     L----0(0,0)\n"
                + "     R----3(1,0)\n"
                + "          L----2(0,0)\n"
                + "          R----4(0,0)\n";
        debugAssertEquals(expected);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_Correct() {
        for (int i : new int[] {15, 26, 32, 2, 13, 26, 21, 10, 8, 31}) {
            tree.add(i);
        }

        expected = "tree:\n"
                + "R----15(3,0)\n"
                + "     L----10(2,1)\n"
                + "     |    L----2(1,-1)\n"
                + "     |    |    R----8(0,0)\n"
                + "     |    R----13(0,0)\n"
                + "     R----26(2,-1)\n"
                + "          L----21(0,0)\n"
                + "          R----32(1,1)\n"
                + "               L----31(0,0)\n";
        debugAssertEquals(expected);

        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());

        for (int i : new int[] {15, 26, 32, 2, 13, 26, 21, 10, 8, 31}) {
            tree.add(i);
        }

        expected = "tree:\n"
                + "R----15(3,0)\n"
                + "     L----10(2,1)\n"
                + "     |    L----2(1,-1)\n"
                + "     |    |    R----8(0,0)\n"
                + "     |    R----13(0,0)\n"
                + "     R----26(2,-1)\n"
                + "          L----21(0,0)\n"
                + "          R----32(1,1)\n"
                + "               L----31(0,0)\n";
        debugAssertEquals(expected);

    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_remove_Null_Data() {
        tree.remove(null);
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_remove_Data_not_in_tree() {
        // Empty tree
        tree.remove(1);

        // Add 1 through 7
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7);
        tree = new AVL<>(list);

        // Remove 8
        tree.remove(8);
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_Decrements_size() {
        // Add 1 through 7
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7);
        tree = new AVL<>(list);

        // Remove 1 through 7
        for (int i = 1; i <= 7; i++) {
            tree.remove(i);
            debugAssertEquals(7 - i, tree.size());
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_No_Children() {
        for (int i : new int[] {15, 26, 32, 2, 13, 26, 21, 10, 8, 31}) {
            tree.add(i);
        }

        tree.remove(13);
        expected = "tree:\n"
                + "R----15(3,-1)\n"
                + "     L----8(1,0)\n"
                + "     |    L----2(0,0)\n"
                + "     |    R----10(0,0)\n"
                + "     R----26(2,-1)\n"
                + "          L----21(0,0)\n"
                + "          R----32(1,1)\n"
                + "               L----31(0,0)\n";
        debugAssertEquals(expected);

        tree.remove(21);
        expected = "tree:\n"
                + "R----15(2,0)\n"
                + "     L----8(1,0)\n"
                + "     |    L----2(0,0)\n"
                + "     |    R----10(0,0)\n"
                + "     R----31(1,0)\n"
                + "          L----26(0,0)\n"
                + "          R----32(0,0)\n";
        debugAssertEquals(expected);
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_One_Child() {
        for (int i : new int[] {15, 26, 32, 2, 13, 26, 21, 10, 8, 31}) {
            tree.add(i);
        }

        tree.remove(2);
        expected = "tree:\n"
                + "R----15(3,-1)\n"
                + "     L----10(1,0)\n"
                + "     |    L----8(0,0)\n"
                + "     |    R----13(0,0)\n"
                + "     R----26(2,-1)\n"
                + "          L----21(0,0)\n"
                + "          R----32(1,1)\n"
                + "               L----31(0,0)\n";
        debugAssertEquals(expected);

        tree.remove(32);
        expected = "tree:\n"
                + "R----15(2,0)\n"
                + "     L----10(1,0)\n"
                + "     |    L----8(0,0)\n"
                + "     |    R----13(0,0)\n"
                + "     R----26(1,0)\n"
                + "          L----21(0,0)\n"
                + "          R----31(0,0)\n";
        debugAssertEquals(expected);
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_Two_Children() {
        for (int i : new int[] {15, 26, 32, 2, 13, 26, 21, 10, 8, 31}) {
            tree.add(i);
        }

        tree.remove(10);
        expected = "tree:\n"
                + "R----15(3,-1)\n"
                + "     L----8(1,0)\n"
                + "     |    L----2(0,0)\n"
                + "     |    R----13(0,0)\n"
                + "     R----26(2,-1)\n"
                + "          L----21(0,0)\n"
                + "          R----32(1,1)\n"
                + "               L----31(0,0)\n";
        debugAssertEquals(expected);

        tree.remove(8);
        expected = "tree:\n"
                + "R----15(3,-1)\n"
                + "     L----2(1,-1)\n"
                + "     |    R----13(0,0)\n"
                + "     R----26(2,-1)\n"
                + "          L----21(0,0)\n"
                + "          R----32(1,1)\n"
                + "               L----31(0,0)\n";
        debugAssertEquals(expected);

        tree.remove(15);
        expected = "tree:\n"
                + "R----26(2,0)\n"
                + "     L----13(1,0)\n"
                + "     |    L----2(0,0)\n"
                + "     |    R----21(0,0)\n"
                + "     R----32(1,1)\n"
                + "          L----31(0,0)\n";
        debugAssertEquals(expected);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_predecessor_Null_Data() {
        tree.predecessor(null);
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_predecessor_Data_not_in_tree() {
        for (int i : new int[] {15, 26, 32, 2, 13, 26, 21, 10, 8, 31}) {
            tree.add(i);
        }

        assertNull(tree.predecessor(99));
    }

    @Test(timeout = TIMEOUT)
    public void test_predecessor_Has_Left_Subtree() {
        for (int i : new int[] {15, 26, 32, 2, 13, 26, 21, 10, 8, 31}) {
            tree.add(i);
        }

        debugAssertEquals(10, tree.predecessor(13).intValue());
        debugAssertEquals(21, tree.predecessor(26).intValue());
    }

    @Test(timeout = TIMEOUT)
    public void test_predecessor_Return_Parent() {
        for (int i : new int[] {15, 26, 32, 2, 13, 26, 21, 10, 8, 31}) {
            tree.add(i);
        }

        debugAssertEquals(10, tree.predecessor(13).intValue());
        debugAssertEquals(15, tree.predecessor(21).intValue());
    }

    @Test(timeout = TIMEOUT)
    public void test_maxDeepestNode() {
        for (int i : new int[] {15, 26, 32, 2, 13, 26, 21, 10, 8, 31}) {
            tree.add(i);
        }

        debugAssertEquals(31, tree.maxDeepestNode().intValue());
    }

}
