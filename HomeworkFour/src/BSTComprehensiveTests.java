import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ashwin Mudaliar
 * @version 1.1.0
 * 
 * Quote of the week: "I want google internship"
 * 
 */

public class BSTComprehensiveTests {

    private static final int TIMEOUT = 200;
    private BST<Integer> tree;

    private ArrayList<Integer> preorderChecker;
    private ArrayList<Integer> postorderChecker;
    private ArrayList<Integer> inorderChecker;
    private ArrayList<Integer> levelorderChecker;

    @Before
    public void setup() {

        tree = new BST<>();

        preorderChecker = setupPre();
        postorderChecker = setupPost();
        inorderChecker = setupIn();
        levelorderChecker = setupLevel();

    }

    private void populateDegenerate(BST<Integer> tree, int num_elements, ArrayList<Integer> test) {

        for (int k = 0; k < num_elements; k++) {

            tree.add(k);
            test.add(test.size(), k);

        }

    }

    private ArrayList<Integer> setupPre() {

        int[] arr = { 30, 20, 10, 5, 13, 25, 21, 26, 80, 70, 60, 75, 90, 85, 100 };

        ArrayList<Integer> test = new ArrayList<>();

        for (int k : arr) {
            test.add(test.size(), k);
        }

        return test;

    }

    private ArrayList<Integer> setupLevel() {

        int[] arr = { 30, 20, 80, 10, 25, 70, 90, 5, 13, 21, 26, 60, 75, 85, 100 };

        ArrayList<Integer> test = new ArrayList<>();

        for (int k : arr) {
            test.add(test.size(), k);
        }

        return test;

    }

    private ArrayList<Integer> setupPost() {

        int[] arr = { 5, 13, 10, 21, 26, 25, 20, 60, 75, 70, 85, 100, 90, 80, 30 };

        ArrayList<Integer> test = new ArrayList<>();

        for (int k : arr) {
            test.add(test.size(), k);
        }

        return test;

    }

    private ArrayList<Integer> setupIn() {

        int[] arr = { 5, 10, 13, 20, 21, 25, 26, 30, 60, 70, 75, 80, 85, 90, 100 };

        ArrayList<Integer> test = new ArrayList<>();

        for (int k : arr) {
            test.add(test.size(), k);
        }

        return test;

    }

    private void rDegerateToList(BSTNode<Integer> curr, ArrayList<Integer> arr) {

        if (curr == null) {
            return;
        } else {
            arr.add(curr.getData());
            rDegerateToList(curr.getRight(), arr);
        }

    }

    private void populateBalancedTree(BST<Integer> tree, ArrayList<Integer> test) {

        tree.add(30);
        tree.add(20);
        tree.add(80);
        tree.add(10);
        tree.add(25);
        tree.add(70);
        tree.add(90);
        tree.add(5);
        tree.add(13);
        tree.add(21);
        tree.add(26);
        tree.add(60);
        tree.add(75);
        tree.add(85);
        tree.add(100);

        // this is pretty useless ngl
        test.add(test.size(), 30);
        test.add(test.size(), 20);
        test.add(test.size(), 80);
        test.add(test.size(), 10);
        test.add(test.size(), 25);
        test.add(test.size(), 70);
        test.add(test.size(), 90);
        test.add(test.size(), 5);
        test.add(test.size(), 13);
        test.add(test.size(), 21);
        test.add(test.size(), 26);
        test.add(test.size(), 60);
        test.add(test.size(), 75);
        test.add(test.size(), 85);
        test.add(test.size(), 100);

    }

    @Test(timeout = TIMEOUT)
    public void testHeightRootIsNull() {

        tree.clear();

        boolean x = -1 == tree.height();

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void constructorNullElement() {

        ArrayList<Integer> c = new ArrayList<>();

        for (int k = 0; k < 10; k++) {

            c.add(k);

        }

        c.add(null);

        tree = new BST<>(c);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void constructorNullData() {

        tree = new BST<>(null);

    }

    @Test(timeout = TIMEOUT)
    public void constructorDegenerate() {

        ArrayList<Integer> test = new ArrayList<>();
        populateDegenerate(tree, 20, test);

        tree = new BST<>(test);

        ArrayList<Integer> other = new ArrayList<>();
        rDegerateToList(tree.getRoot(), other);

        assertEquals(test, other);

        assertEquals(tree.size(), 20);

    }

    @Test(timeout = TIMEOUT)
    public void addBalanced() {

        populateBalancedTree(tree, new ArrayList<Integer>());

        assertEquals(postorderChecker.size(), tree.size());

        assertEquals(inorderChecker, tree.inorder());
        assertEquals(preorderChecker, tree.preorder());
        assertEquals(postorderChecker, tree.postorder());

    }

    @Test(timeout = TIMEOUT)
    public void constructorBalanced() {

        ArrayList<Integer> test = new ArrayList<>();

        test.add(test.size(), 30);
        test.add(test.size(), 20);
        test.add(test.size(), 80);
        test.add(test.size(), 10);
        test.add(test.size(), 25);
        test.add(test.size(), 70);
        test.add(test.size(), 90);
        test.add(test.size(), 5);
        test.add(test.size(), 13);
        test.add(test.size(), 21);
        test.add(test.size(), 26);
        test.add(test.size(), 60);
        test.add(test.size(), 75);
        test.add(test.size(), 85);
        test.add(test.size(), 100);

        tree = new BST<>(test);

        assertEquals(postorderChecker.size(), tree.size());

        assertEquals(inorderChecker, tree.inorder());
        assertEquals(preorderChecker, tree.preorder());
        assertEquals(postorderChecker, tree.postorder());

    }

    @Test(timeout = TIMEOUT)
    public void testPreorderBalanced() {

        int[] arr = { 30, 20, 10, 5, 13, 25, 21, 26, 80, 70, 60, 75, 90, 85, 100 };

        populateBalancedTree(tree, new ArrayList<Integer>());

        ArrayList<Integer> test = new ArrayList<>();

        for (int k : arr) {
            test.add(test.size(), k);
        }

        assertEquals(test, tree.preorder());

    }

    @Test(timeout = TIMEOUT)
    public void testPostorderBalanced() {

        int[] arr = { 5, 13, 10, 21, 26, 25, 20, 60, 75, 70, 85, 100, 90, 80, 30 };

        populateBalancedTree(tree, new ArrayList<Integer>());

        ArrayList<Integer> test = new ArrayList<>();

        for (int k : arr) {
            test.add(test.size(), k);
        }

        assertEquals(test, tree.postorder());

    }

    @Test(timeout = TIMEOUT)
    public void testInorderBalanced() {

        int[] arr = { 5, 10, 13, 20, 21, 25, 26, 30, 60, 70, 75, 80, 85, 90, 100 };

        populateBalancedTree(tree, new ArrayList<Integer>());

        ArrayList<Integer> test = new ArrayList<>();

        for (int k : arr) {
            test.add(test.size(), k);
        }

        assertEquals(test, tree.inorder());

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void addNull() {

        tree.add(null);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void removeNull() {

        populateBalancedTree(tree, new ArrayList<Integer>());

        tree.remove(null);

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void removeElementDoesNotExist() {

        populateBalancedTree(tree, new ArrayList<Integer>());

        tree.remove(110);

        tree.remove(22);

        tree.remove(-6);

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveNoChildren() {

        populateBalancedTree(tree, new ArrayList<Integer>());

        boolean e = tree.remove(75) == 75;
        assertEquals(true, e);

        ArrayList<Integer> test = new ArrayList<>();
        int[] arr = { 5, 13, 10, 21, 26, 25, 20, 60, 70, 85, 100, 90, 80, 30 };

        for (int x : arr) {
            test.add(test.size(), x);
        }

        assertEquals(test, tree.postorder());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveOneChild() {

        populateBalancedTree(tree, new ArrayList<Integer>());

        tree.add(95);
        tree.add(97);

        boolean e = tree.remove(95) == 95;
        assertEquals(true, e);

        ArrayList<Integer> test = new ArrayList<>();
        int[] arr = { 5, 13, 10, 21, 26, 25, 20, 60, 75, 70, 85, 97, 100, 90, 80, 30 };

        for (int x : arr) {
            test.add(test.size(), x);
        }

        assertEquals(test, tree.postorder());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveTwoChildrenHeightOne() {

        populateBalancedTree(tree, new ArrayList<Integer>());

        boolean e = tree.remove(10) == 10;
        assertEquals(true, e);

        ArrayList<Integer> test = new ArrayList<>();
        int[] arr = { 5, 13, 21, 26, 25, 20, 60, 75, 70, 85, 100, 90, 80, 30 };

        for (int x : arr) {
            test.add(test.size(), x);
        }

        assertEquals(test, tree.postorder());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveTwoChildrenHeightTwo() {

        populateBalancedTree(tree, new ArrayList<Integer>());

        boolean e = tree.remove(20) == 20;
        assertEquals(true, e);

        ArrayList<Integer> test = new ArrayList<>();
        int[] arr = { 5, 13, 10, 26, 25, 21, 60, 75, 70, 85, 100, 90, 80, 30 };

        for (int x : arr) {
            test.add(test.size(), x);
        }

        assertEquals(test, tree.postorder());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRoot() {

        populateBalancedTree(tree, new ArrayList<Integer>());

        boolean e = tree.remove(30) == 30;
        assertEquals(true, e);

        ArrayList<Integer> test = new ArrayList<>();
        int[] arr = { 5, 13, 10, 21, 26, 25, 20, 75, 70, 85, 100, 90, 80, 60 };

        for (int x : arr) {
            test.add(test.size(), x);
        }

        boolean x = 60 == tree.getRoot().getData();
        assertEquals(true, x);

        assertEquals(test, tree.postorder());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveDegenerate() {

        ArrayList<Integer> other = new ArrayList<>();
        populateDegenerate(tree, 20, other);

        boolean x = tree.remove(6) == 6;
        assertEquals(true, x);

        other.remove(6);

        assertEquals(19, tree.size());

        ArrayList<Integer> test = new ArrayList<Integer>();
        rDegerateToList(tree.getRoot(), test);

        assertEquals(other, test);

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveMostlyDegenerateRoot() {

        ArrayList<Integer> other = new ArrayList<>();
        populateDegenerate(tree, 20, other);

        tree.add(-1);
        other.add(0, -1);

        boolean x = tree.remove(0) == 0;
        assertEquals(true, x);

        other.remove(1);

        assertEquals(20, tree.size());

        ArrayList<Integer> test = (ArrayList<Integer>) tree.inorder();

        assertEquals(other, test);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testGetDataIsNull() {

        populateBalancedTree(tree, new ArrayList<>());

        tree.get(null);

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testGetDataDoesNotExist() {

        populateBalancedTree(tree, new ArrayList<>());

        tree.get(63);

    }

    @Test(timeout = TIMEOUT)
    public void testGetRoot() {

        populateBalancedTree(tree, new ArrayList<>());

        boolean x = tree.get(30) == 30;
        assertEquals(true, x);

    }

    @Test(timeout = TIMEOUT)
    public void testGet() {

    populateBalancedTree(tree, new ArrayList<>());

        boolean x = tree.get(10) == 10;
        assertEquals(true, x);


    }

    @Test(timeout = TIMEOUT)
    public void testContainsTrue() {

        populateBalancedTree(tree, new ArrayList<Integer>());

        assertEquals(true, tree.contains(30));
        assertEquals(true, tree.contains(80));
        assertEquals(true, tree.contains(100));
        assertEquals(true, tree.contains(75));
        assertEquals(true, tree.contains(60));
        assertEquals(true, tree.contains(13));
        assertEquals(true, tree.contains(10));

    }

    @Test(timeout = TIMEOUT)
    public void testContainsFalse() {

        populateBalancedTree(tree, new ArrayList<Integer>());

        assertEquals(false, tree.contains(31));
        assertEquals(false, tree.contains(81));
        assertEquals(false, tree.contains(110));
        assertEquals(false, tree.contains(71));
        assertEquals(false, tree.contains(61));
        assertEquals(false, tree.contains(14));
        assertEquals(false, tree.contains(11));
        
    }

    @Test(timeout = TIMEOUT)
    public void testLevelorderBalanced() {

        populateBalancedTree(tree, new ArrayList<>());

        assertEquals(levelorderChecker, tree.levelorder());

    }

    @Test(timeout = TIMEOUT)
    public void testHeightBalanced() {

        populateBalancedTree(tree, new ArrayList<>());

        assertEquals(3, tree.height());

    }

    @Test(timeout = TIMEOUT)
    public void testHeightDegenerate() {

        int num_elements = (int) (Math.random() * 50) + 5;

        populateDegenerate(tree, num_elements, new ArrayList<>());

        assertEquals(num_elements - 1, tree.height());

    }

    @Test(timeout = TIMEOUT)
    public void testPathBetweenBalanced() {

        populateBalancedTree(tree, new ArrayList<>());

        ArrayList<Integer> test = new ArrayList<>();

        // 10 -> 70
        int[] a = {10, 20, 30, 80, 70};
        arrayToArrayList(a, test);

        assertEquals(test, tree.findPathBetween(10, 70));
        
        // 5 -> 13
        int[]  b = {5, 10, 13};
        arrayToArrayList(b, test);

        assertEquals(test, tree.findPathBetween(5, 13));

        // 100 -> 21
        int[] c = {100, 90, 80, 30, 20, 25, 21};
        arrayToArrayList(c, test);

        assertEquals(test, tree.findPathBetween(100, 21));

        // 30 -> 26
        int[] d = {30, 20, 25, 26};
        arrayToArrayList(d, test);

        assertEquals(test, tree.findPathBetween(30, 26));

        // 30 -> 75
        int[] e = {30, 80, 70, 75};
        arrayToArrayList(e, test);

        assertEquals(test, tree.findPathBetween(30, 75));

        // 85 -> 30
        int[] f = {85, 90, 80, 30};
        arrayToArrayList(f, test);

        assertEquals(test, tree.findPathBetween(85, 30));

        // 100 -> 30
        int[] g = {100, 90, 80, 30};
        arrayToArrayList(g, test);

        assertEquals(test, tree.findPathBetween(100, 30));

        // 100 -> 80
        int[] h = {100, 90, 80};
        arrayToArrayList(h, test);

        assertEquals(test, tree.findPathBetween(100, 80));

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void findPathBetweenRootIsNull() {
        tree.clear();

        tree.findPathBetween(10, 50);
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void findPathBetweenOnlyRoot() {

        tree.clear();
        tree.add(50);

        tree.findPathBetween(10, 60);
        tree.findPathBetween(40, 30);

    }

    @Test(timeout = TIMEOUT)
    public void findPathBetweenDegerate() {

        populateDegenerate(tree, 10, new ArrayList<>());

        ArrayList<Integer> test = new ArrayList<Integer>();

        // 8 -> 1
        int[] a = {8, 7, 6, 5, 4, 3, 2, 1};
        arrayToArrayList(a, test);

        assertEquals(test, tree.findPathBetween(8, 1));

        // 3 -> 9
        int[] b = {3, 4, 5, 6, 7, 8, 9};
        arrayToArrayList(b, test);

        assertEquals(test, tree.findPathBetween(3, 9));

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void findPathBetweenDegerateNoSuchElement() {

        populateDegenerate(tree, 10, new ArrayList<>());

        ArrayList<Integer> test = new ArrayList<Integer>();

        // 8 -> -6
        tree.findPathBetween(8, -6);

        // 3 -> 11
        tree.findPathBetween(3, 11);

    }

    private void arrayToArrayList(int[] arr, ArrayList<Integer> test) {

        test.clear();

        for (int x : arr) {
            test.add(test.size(), x);
        }

    }
}
