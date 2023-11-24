import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Various test cases for AVL homework
 * 
 * @author Srithan Nalluri
 * @version 1.0
 */
public class AVLTests {

    private static final int TIMEOUT = 300;
    private AVL<Integer> tree;

    @Before
    public void setup() {
        tree = new AVL<>();
    }


    //Constructor tests

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullInput() {
        tree = new AVL<>(null);
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor1() {
        List<Integer> aList = new ArrayList<>();
        aList.add(50);
        aList.add(30);
        aList.add(60);
        aList.add(10);
        aList.add(70);
        aList.add(90);
        aList.add(80);

        tree = new AVL<>(aList);

        assertEquals(7, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 50, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());
        AVLNode<Integer> node30 = root.getLeft();
        assertEquals((Integer) 30, node30.getData());
        assertEquals(1, node30.getHeight());
        assertEquals(1, node30.getBalanceFactor());
        AVLNode<Integer> node10 = node30.getLeft();
        assertEquals((Integer) 10, node10.getData());
        assertEquals(0, node10.getHeight());
        assertEquals(0, node10.getBalanceFactor());
        AVLNode<Integer> node70 = root.getRight();
        assertEquals((Integer) 70, node70.getData());
        assertEquals(2, node70.getHeight());
        assertEquals(-1, node70.getBalanceFactor());
        AVLNode<Integer> node60 = node70.getLeft();
        assertEquals((Integer) 60, node60.getData());
        assertEquals(0, node60.getHeight());
        assertEquals(0, node60.getBalanceFactor());
        AVLNode<Integer> node90 = node70.getRight();
        assertEquals((Integer) 90, node90.getData());
        assertEquals(1, node90.getHeight());
        assertEquals(1, node90.getBalanceFactor());
        AVLNode<Integer> node80 = node90.getLeft();
        assertEquals((Integer) 80, node80.getData());
        assertEquals(0, node80.getHeight());
        assertEquals(0, node80.getBalanceFactor());
    }


    //Add Tests
    @Test(timeout = TIMEOUT)
    public void testBasicLeftRotate() {
        tree.add(0);
        tree.add(1);
        tree.add(2);

        assertEquals(3, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 1, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 2, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testBasicLeftRightRotate() {

        tree.add(2);
        tree.add(0);
        tree.add(1);

        assertEquals(3, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 1, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 0, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 2, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testBasicRightLeftRotate() {

        tree.add(5);
        tree.add(8);
        tree.add(6);

        assertEquals(3, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 6, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> left = root.getLeft();
        assertEquals((Integer) 5, left.getData());
        assertEquals(0, left.getHeight());
        assertEquals(0, left.getBalanceFactor());
        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 8, right.getData());
        assertEquals(0, right.getHeight());
        assertEquals(0, right.getBalanceFactor());
    }


    @Test(timeout = TIMEOUT)
    public void testAddWithLeftRotation() {
        tree.add(10);
        tree.add(7);
        tree.add(12);
        tree.add(6);
        tree.add(8);
        tree.add(5);

        assertEquals(6, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 7, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> node6 = root.getLeft();
        assertEquals((Integer) 6, node6.getData());
        assertEquals(1, node6.getHeight());
        assertEquals(1, node6.getBalanceFactor());
        AVLNode<Integer> node5 = node6.getLeft();
        assertEquals((Integer) 5, node5.getData());
        assertEquals(0, node5.getHeight());
        assertEquals(0, node5.getBalanceFactor());
        AVLNode<Integer> node10 = root.getRight();
        assertEquals((Integer) 10, node10.getData());
        assertEquals(1, node10.getHeight());
        assertEquals(0, node10.getBalanceFactor());
        AVLNode<Integer> node8 = node10.getLeft();
        assertEquals((Integer) 8, node8.getData());
        assertEquals(0, node8.getHeight());
        assertEquals(0, node8.getBalanceFactor());
        AVLNode<Integer> node12 = node10.getRight();
        assertEquals((Integer) 12, node12.getData());
        assertEquals(0, node12.getHeight());
        assertEquals(0, node12.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddWithRightRotation() {
        tree.add(1);
        tree.add(0);
        tree.add(3);
        tree.add(2);
        tree.add(4);
        tree.add(5);

        assertEquals(6, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 3, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> node1 = root.getLeft();
        assertEquals((Integer) 1, node1.getData());
        assertEquals(1, node1.getHeight());
        assertEquals(0, node1.getBalanceFactor());
        AVLNode<Integer> node0 = node1.getLeft();
        assertEquals((Integer) 0, node0.getData());
        assertEquals(0, node0.getHeight());
        assertEquals(0, node0.getBalanceFactor());
        AVLNode<Integer> node2 = node1.getRight();
        assertEquals((Integer) 2, node2.getData());
        assertEquals(0, node2.getHeight());
        assertEquals(0, node2.getBalanceFactor());
        AVLNode<Integer> node4 = root.getRight();
        assertEquals((Integer) 4, node4.getData());
        assertEquals(1, node4.getHeight());
        assertEquals(-1, node4.getBalanceFactor());
        AVLNode<Integer> node5 = node4.getRight();
        assertEquals((Integer) 5, node5.getData());
        assertEquals(0, node5.getHeight());
        assertEquals(0, node5.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddWithLeftRightRotation() {
        tree.add(20);
        tree.add(15);
        tree.add(22);
        tree.add(14);
        tree.add(16);
        tree.add(18);

        assertEquals(6, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 16, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> node15 = root.getLeft();
        assertEquals((Integer) 15, node15.getData());
        assertEquals(1, node15.getHeight());
        assertEquals(1, node15.getBalanceFactor());
        AVLNode<Integer> node14 = node15.getLeft();
        assertEquals((Integer) 14, node14.getData());
        assertEquals(0, node14.getHeight());
        assertEquals(0, node14.getBalanceFactor());
        AVLNode<Integer> node20 = root.getRight();
        assertEquals((Integer) 20, node20.getData());
        assertEquals(1, node20.getHeight());
        assertEquals(0, node20.getBalanceFactor());
        AVLNode<Integer> node18 = node20.getLeft();
        assertEquals((Integer) 18, node18.getData());
        assertEquals(0, node18.getHeight());
        assertEquals(0, node18.getBalanceFactor());
        AVLNode<Integer> node22 = node20.getRight();
        assertEquals((Integer) 22, node22.getData());
        assertEquals(0, node22.getHeight());
        assertEquals(0, node22.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddWithRightLeftRotation() {
        tree.add(50);
        tree.add(40);
        tree.add(80);
        tree.add(70);
        tree.add(90);
        tree.add(60);

        assertEquals(6, tree.size());

        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 70, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> node50 = root.getLeft();
        assertEquals((Integer) 50, node50.getData());
        assertEquals(1, node50.getHeight());
        assertEquals(0, node50.getBalanceFactor());
        AVLNode<Integer> node40 = node50.getLeft();
        assertEquals((Integer) 40, node40.getData());
        assertEquals(0, node40.getHeight());
        assertEquals(0, node40.getBalanceFactor());
        AVLNode<Integer> node60 = node50.getRight();
        assertEquals((Integer) 60, node60.getData());
        assertEquals(0, node60.getHeight());
        assertEquals(0, node60.getBalanceFactor());
        AVLNode<Integer> node80 = root.getRight();
        assertEquals((Integer) 80, node80.getData());
        assertEquals(1, node80.getHeight());
        assertEquals(-1, node80.getBalanceFactor());
        AVLNode<Integer> node90 = node80.getRight();
        assertEquals((Integer) 90, node90.getData());
        assertEquals(0, node90.getHeight());
        assertEquals(0, node90.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAdd1() {
        tree.add(60);
        tree.add(24);
        tree.add(66);
        tree.add(20);
        tree.add(30);
        tree.add(62);
        tree.add(70);
        tree.add(21);
        tree.add(61);
        tree.add(71);

        assertEquals(10, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 60, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        AVLNode<Integer> node24 = root.getLeft();
        assertEquals((Integer) 24, node24.getData());
        assertEquals(2, node24.getHeight());
        assertEquals(1, node24.getBalanceFactor());

        AVLNode<Integer> node20 = node24.getLeft();
        assertEquals((Integer) 20, node20.getData());
        assertEquals(1, node20.getHeight());
        assertEquals(-1, node20.getBalanceFactor());

        AVLNode<Integer> node21 = node20.getRight();
        assertEquals((Integer) 21, node21.getData());
        assertEquals(0, node21.getHeight());
        assertEquals(0, node21.getBalanceFactor());

        AVLNode<Integer> node30 = node24.getRight();
        assertEquals((Integer) 30, node30.getData());
        assertEquals(0, node30.getHeight());
        assertEquals(0, node30.getBalanceFactor());

        AVLNode<Integer> node66 = root.getRight();
        assertEquals((Integer) 66, node66.getData());
        assertEquals(2, node66.getHeight());
        assertEquals(0, node66.getBalanceFactor());

        AVLNode<Integer> node62 = node66.getLeft();
        assertEquals((Integer) 62, node62.getData());
        assertEquals(1, node62.getHeight());
        assertEquals(1, node62.getBalanceFactor());

        AVLNode<Integer> node61 = node62.getLeft();
        assertEquals((Integer) 61, node61.getData());
        assertEquals(0, node61.getHeight());
        assertEquals(0, node61.getBalanceFactor());

        AVLNode<Integer> node70 = node66.getRight();
        assertEquals((Integer) 70, node70.getData());
        assertEquals(1, node70.getHeight());
        assertEquals(-1, node70.getBalanceFactor());

        AVLNode<Integer> node71 = node70.getRight();
        assertEquals((Integer) 71, node71.getData());
        assertEquals(0, node71.getHeight());
        assertEquals(0, node71.getBalanceFactor());

    }

    @Test(timeout = TIMEOUT)
    public void testAdd2() {
        tree.add(4);
        tree.add(2);
        tree.add(7);
        tree.add(1);
        tree.add(3);
        tree.add(6);
        tree.add(9);
        tree.add(8);

        assertEquals(8, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());
        AVLNode<Integer> node2 = root.getLeft();
        assertEquals((Integer) 2, node2.getData());
        assertEquals(1, node2.getHeight());
        assertEquals(0, node2.getBalanceFactor());
        AVLNode<Integer> node1 = node2.getLeft();
        assertEquals((Integer) 1, node1.getData());
        assertEquals(0, node1.getHeight());
        assertEquals(0, node1.getBalanceFactor());
        AVLNode<Integer> node3 = node2.getRight();
        assertEquals((Integer) 3, node3.getData());
        assertEquals(0, node3.getHeight());
        assertEquals(0, node3.getBalanceFactor());
        AVLNode<Integer> node7 = root.getRight();
        assertEquals((Integer) 7, node7.getData());
        assertEquals(2, node7.getHeight());
        assertEquals(-1, node7.getBalanceFactor());
        AVLNode<Integer> node6 = node7.getLeft();
        assertEquals((Integer) 6, node6.getData());
        assertEquals(0, node6.getHeight());
        assertEquals(0, node6.getBalanceFactor());
        AVLNode<Integer> node9 = node7.getRight();
        assertEquals((Integer) 9, node9.getData());
        assertEquals(1, node9.getHeight());
        assertEquals(1, node9.getBalanceFactor());
        AVLNode<Integer> node8 = node9.getLeft();
        assertEquals((Integer) 8, node8.getData());
        assertEquals(0, node8.getHeight());
        assertEquals(0, node8.getBalanceFactor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullInput() {
        tree.add(20);
        tree.add(10);
        tree.add(30);
        tree.add(null);
        tree.add(35);
    }



    //Remove tests
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveWithNullInput() {
        tree.add(20);
        tree.add(10);
        tree.add(30);
        tree.add(35);
        tree.add(25);
        tree.remove(null);
        tree.remove(30);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveNonExistentElement() {
        tree.add(20);
        tree.add(10);
        tree.add(30);
        tree.add(35);
        tree.add(25);
        tree.remove(50);
        tree.remove(30);
    }

    @Test(timeout = TIMEOUT)
    public void testRemove1() {
        tree.add(20);
        tree.add(14);
        tree.add(26);
        tree.add(12);
        tree.add(15);
        tree.add(23);
        tree.remove(23);
        tree.remove(20);

        assertEquals(4, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 15, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(1, root.getBalanceFactor());
        AVLNode<Integer> node14 = root.getLeft();
        assertEquals((Integer) 14, node14.getData());
        assertEquals(1, node14.getHeight());
        assertEquals(1, node14.getBalanceFactor());
        AVLNode<Integer> node12 = node14.getLeft();
        assertEquals((Integer) 12, node12.getData());
        assertEquals(0, node12.getHeight());
        assertEquals(0, node12.getBalanceFactor());
        AVLNode<Integer> node26 = root.getRight();
        assertEquals((Integer) 26, node26.getData());
        assertEquals(0, node26.getHeight());
        assertEquals(0, node26.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveWithLeftRotation() {
        tree.add(9);
        tree.add(6);
        tree.add(12);
        tree.add(11);
        tree.add(15);
        tree.remove(6);

        assertEquals(4, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 12, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(1, root.getBalanceFactor());
        AVLNode<Integer> node9 = root.getLeft();
        assertEquals((Integer) 9, node9.getData());
        assertEquals(1, node9.getHeight());
        assertEquals(-1, node9.getBalanceFactor());
        AVLNode<Integer> node11 = node9.getRight();
        assertEquals((Integer) 11, node11.getData());
        assertEquals(0, node11.getHeight());
        assertEquals(0, node11.getBalanceFactor());
        AVLNode<Integer> node15 = root.getRight();
        assertEquals((Integer) 15, node15.getData());
        assertEquals(0, node15.getHeight());
        assertEquals(0, node15.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveWithRightRotation() {
        tree.add(15);
        tree.add(10);
        tree.add(17);
        tree.add(9);
        tree.add(12);
        tree.remove(17);

        assertEquals(4, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 10, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());
        AVLNode<Integer> node9 = root.getLeft();
        assertEquals((Integer) 9, node9.getData());
        assertEquals(0, node9.getHeight());
        assertEquals(0, node9.getBalanceFactor());
        AVLNode<Integer> node15 = root.getRight();
        assertEquals((Integer) 15, node15.getData());
        assertEquals(1, node15.getHeight());
        assertEquals(1, node15.getBalanceFactor());
        AVLNode<Integer> node12 = node15.getLeft();
        assertEquals((Integer) 12, node12.getData());
        assertEquals(0, node12.getHeight());
        assertEquals(0, node12.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveWithLeftRightRotation() {
        tree.add(20);
        tree.add(15);
        tree.add(22);
        tree.add(14);
        tree.add(17);
        tree.add(21);
        tree.add(18);
        tree.remove(22);

        assertEquals(6, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 17, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> node15 = root.getLeft();
        assertEquals((Integer) 15, node15.getData());
        assertEquals(1, node15.getHeight());
        assertEquals(1, node15.getBalanceFactor());
        AVLNode<Integer> node14 = node15.getLeft();
        assertEquals((Integer) 14, node14.getData());
        assertEquals(0, node14.getHeight());
        assertEquals(0, node14.getBalanceFactor());
        AVLNode<Integer> node20 = root.getRight();
        assertEquals((Integer) 20, node20.getData());
        assertEquals(1, node20.getHeight());
        assertEquals(0, node20.getBalanceFactor());
        AVLNode<Integer> node18 = node20.getLeft();
        assertEquals((Integer) 18, node18.getData());
        assertEquals(0, node18.getHeight());
        assertEquals(0, node18.getBalanceFactor());
        AVLNode<Integer> node21 = node20.getRight();
        assertEquals((Integer) 21, node21.getData());
        assertEquals(0, node21.getHeight());
        assertEquals(0, node21.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveWithRightLeftRotation() {
        tree.add(50);
        tree.add(45);
        tree.add(70);
        tree.add(47);
        tree.add(65);
        tree.add(72);
        tree.add(60);
        tree.remove(45);

        assertEquals(6, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 65, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> node50 = root.getLeft();
        assertEquals((Integer) 50, node50.getData());
        assertEquals(1, node50.getHeight());
        assertEquals(0, node50.getBalanceFactor());
        AVLNode<Integer> node47 = node50.getLeft();
        assertEquals((Integer) 47, node47.getData());
        assertEquals(0, node47.getHeight());
        assertEquals(0, node47.getBalanceFactor());
        AVLNode<Integer> node60 = node50.getRight();
        assertEquals((Integer) 60, node60.getData());
        assertEquals(0, node60.getHeight());
        assertEquals(0, node60.getBalanceFactor());
        AVLNode<Integer> node70 = root.getRight();
        assertEquals((Integer) 70, node70.getData());
        assertEquals(1, node70.getHeight());
        assertEquals(-1, node70.getBalanceFactor());
        AVLNode<Integer> node72 = node70.getRight();
        assertEquals((Integer) 72, node72.getData());
        assertEquals(0, node72.getHeight());
        assertEquals(0, node72.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testSimpleRemoveLeafNode() {
        tree.add(3);
        tree.add(2);
        tree.add(4);
        tree.add(1);
        tree.remove(1);

        assertEquals(3, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 3, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> node2 = root.getLeft();
        assertEquals((Integer) 2, node2.getData());
        assertEquals(0, node2.getHeight());
        assertEquals(0, node2.getBalanceFactor());
        AVLNode<Integer> node4 = root.getRight();
        assertEquals((Integer) 4, node4.getData());
        assertEquals(0, node4.getHeight());
        assertEquals(0, node4.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveNodeWithOneLeftChild() {
        tree.add(3);
        tree.add(2);
        tree.add(4);
        tree.add(1);
        tree.remove(2);


        assertEquals(3, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 3, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> node1 = root.getLeft();
        assertEquals((Integer) 1, node1.getData());
        assertEquals(0, node1.getHeight());
        assertEquals(0, node1.getBalanceFactor());
        AVLNode<Integer> node4 = root.getRight();
        assertEquals((Integer) 4, node4.getData());
        assertEquals(0, node4.getHeight());
        assertEquals(0, node4.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveNodeWithOneRightChild() {
        tree.add(5);
        tree.add(3);
        tree.add(6);
        tree.add(4);
        tree.remove(3);


        assertEquals(3, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 5, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        AVLNode<Integer> node4 = root.getLeft();
        assertEquals((Integer) 4, node4.getData());
        assertEquals(0, node4.getHeight());
        assertEquals(0, node4.getBalanceFactor());
        AVLNode<Integer> node6 = root.getRight();
        assertEquals((Integer) 6, node6.getData());
        assertEquals(0, node6.getHeight());
        assertEquals(0, node6.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveNodeWithTwoChildren() {
        tree.add(3);
        tree.add(1);
        tree.add(5);
        tree.add(4);
        tree.add(6);
        tree.remove(5);

        assertEquals(4, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 3, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());
        AVLNode<Integer> node1 = root.getLeft();
        assertEquals((Integer) 1, node1.getData());
        assertEquals(0, node1.getHeight());
        assertEquals(0, node1.getBalanceFactor());
        AVLNode<Integer> node4 = root.getRight();
        assertEquals((Integer) 4, node4.getData());
        assertEquals(1, node4.getHeight());
        assertEquals(-1, node4.getBalanceFactor());
        AVLNode<Integer> node6 = node4.getRight();
        assertEquals((Integer) 6, node6.getData());
        assertEquals(0, node6.getHeight());
        assertEquals(0, node6.getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testMultipleRemove() {
        tree.add(4);
        tree.add(2);
        tree.add(7);
        tree.add(1);
        tree.add(3);
        tree.add(6);
        tree.add(9);
        tree.add(8);
        tree.remove(2);
        tree.remove(6);
        tree.remove(4);

        assertEquals(5, tree.size());
        AVLNode<Integer> root = tree.getRoot();
        assertEquals((Integer) 3, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());
        AVLNode<Integer> node1 = root.getLeft();
        assertEquals((Integer) 1, node1.getData());
        assertEquals(0, node1.getHeight());
        assertEquals(0, node1.getBalanceFactor());
        AVLNode<Integer> node8 = root.getRight();
        assertEquals((Integer) 8, node8.getData());
        assertEquals(1, node8.getHeight());
        assertEquals(0, node8.getBalanceFactor());
        AVLNode<Integer> node7 = node8.getLeft();
        assertEquals((Integer) 7, node7.getData());
        assertEquals(0, node7.getHeight());
        assertEquals(0, node7.getBalanceFactor());
        AVLNode<Integer> node9 = node8.getRight();
        assertEquals((Integer) 9, node9.getData());
        assertEquals(0, node9.getHeight());
        assertEquals(0, node9.getBalanceFactor());
    }

    //Get tests
    @Test(expected = IllegalArgumentException.class)
    public void testGetWithNullInput() {
        tree.add(10);
        tree.add(8);
        tree.add(12);
        tree.add(6);
        tree.add(14);
        tree.get(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetWithNonExistentElement() {
        tree.add(20);
        tree.add(15);
        tree.add(25);
        tree.add(10);
        tree.add(30);
        tree.get(100);
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        tree.add(100);
        tree.add(200);
        tree.add(50);
        tree.add(40);
        tree.add(300);
        assertEquals((Integer) 100, tree.get(100));
        assertEquals((Integer) 200, tree.get(200));
        assertEquals((Integer) 50, tree.get(50));
        assertEquals((Integer) 40, tree.get(40));
        assertEquals((Integer) 300, tree.get(300));
    }


    //Contains tests

    @Test(expected = IllegalArgumentException.class)
    public void testContainsWithNullInput() {
        tree.add(10);
        tree.add(8);
        tree.add(12);
        tree.contains(null);
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        tree.add(20);
        tree.add(18);
        tree.add(25);
        tree.add(5);
        tree.add(30);
        tree.add(27);
        tree.add(100);
        tree.add(1);
        assertTrue(tree.contains(20));
        assertTrue(tree.contains(18));
        assertTrue(tree.contains(25));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(30));
        assertTrue(tree.contains(27));
        assertTrue(tree.contains(100));
        assertTrue(tree.contains(1));
        assertFalse(tree.contains(200));
    }

    //Height tests
    @Test(timeout = TIMEOUT)
    public void testHeightWithNullRoot() {
        AVLNode<Integer> root = tree.getRoot();
        assertEquals(-1, tree.height());
    }

    @Test(timeout = TIMEOUT)
    public void testHeightOfPerfectlyBalancedTree() {
        tree.add(30);
        tree.add(27);
        tree.add(35);
        tree.add(26);
        tree.add(28);
        tree.add(33);
        tree.add(37);
        assertEquals(2, tree.height());
    }

    @Test(timeout = TIMEOUT)
    public void testHeight() {
        tree.add(50);
        tree.add(40);
        tree.add(60);
        tree.add(30);
        tree.add(42);
        tree.add(55);
        tree.add(65);
        tree.add(53);
        assertEquals(3, tree.height());
    }


    //Clear test
    @Test(timeout = TIMEOUT)
    public void testClear() {
        tree.add(200);
        tree.add(100);
        tree.add(300);
        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }


    //Predecessor tests
    @Test(timeout = TIMEOUT)
    public void testBasicPredecessor() {
        tree.add(5);
        tree.add(3);
        tree.add(7);
        tree.add(1);
        tree.add(4);

        assertEquals((Integer) 4, tree.predecessor(5));
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessor() {
        tree.add(71);
        tree.add(65);
        tree.add(75);
        tree.add(60);
        tree.add(68);
        tree.add(72);
        tree.add(77);
        tree.add(50);
        tree.add(62);
        tree.add(66);
        tree.add(70);
        tree.add(73);
        tree.add(67);

        assertEquals((Integer) 70, tree.predecessor(71));
        assertEquals((Integer) 67, tree.predecessor(68));
        assertEquals((Integer) 62, tree.predecessor(65));
        assertEquals((Integer) 50, tree.predecessor(60));
        assertEquals((Integer) 73, tree.predecessor(75));
        assertEquals((Integer) 71, tree.predecessor(72));
        assertEquals((Integer) 72, tree.predecessor(73));
        assertEquals((Integer) 75, tree.predecessor(77));
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessorWithoutLeftChild() {
        tree.add(10);
        tree.add(11);

        assertNull(tree.predecessor(10));
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessorWithoutLeftChild2() {
        tree.add(20);
        tree.add(18);
        tree.add(22);
        tree.add(16);
        tree.add(19);

        assertNull(tree.predecessor(16));
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessorWithoutLeftChild3() {
        tree.add(50);
        tree.add(30);
        tree.add(70);
        tree.add(20);
        tree.add(40);
        tree.add(60);
        tree.add(80);
        tree.add(75);

        assertEquals((Integer) 70, tree.predecessor(75));
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessorWithoutLeftChild4() {
        tree.add(25);
        tree.add(20);
        tree.add(28);
        tree.add(18);
        tree.add(22);
        tree.add(21);

        assertEquals((Integer) 20, tree.predecessor(21));
    }

    @Test(expected = NoSuchElementException.class)
    public void testPredecessorNonExistentNode() {
        tree.add(10);
        tree.add(8);
        tree.add(12);
        tree.add(6);
        tree.add(9);
        tree.add(14);

        tree.predecessor(20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPredecessorNullInput() {
        tree.add(5);
        tree.add(3);
        tree.add(7);

        tree.predecessor(null);
    }



    //Max deepest node tests

    @Test(timeout = TIMEOUT)
    public void testMaxDeepestNodeEmptyTree() {
        assertNull(tree.maxDeepestNode());
    }

    @Test(timeout = TIMEOUT)
    public void testMaxDeepestNode1() {
        tree.add(4);
        tree.add(2);
        tree.add(6);
        tree.add(1);
        tree.add(3);
        tree.add(5);
        tree.add(7);

        assertEquals((Integer) 7, tree.maxDeepestNode());
    }

    @Test(timeout = TIMEOUT)
    public void testMaxDeepestNode2() {
        tree.add(5);
        tree.add(3);
        tree.add(7);
        tree.add(2);
        tree.add(4);
        tree.add(6);
        tree.add(8);

        assertEquals((Integer) 8, tree.maxDeepestNode());
    }

    @Test(timeout = TIMEOUT)
    public void testMaxDeepestNode3() {
        tree.add(10);
        tree.add(8);
        tree.add(14);
        tree.add(7);
        tree.add(9);
        tree.add(12);
        tree.add(16);
        tree.add(13);

        assertEquals((Integer) 13, tree.maxDeepestNode());
    }

    @Test(timeout = TIMEOUT)
    public void testMaxDeepestNode4() {
        tree.add(12);
        tree.add(10);
        tree.add(14);
        tree.add(8);
        tree.add(11);
        tree.add(13);
        tree.add(6);

        assertEquals((Integer) 6, tree.maxDeepestNode());
    }

    @Test(timeout = TIMEOUT)
    public void testMaxDeepestNodeWith2LeafNodes() {
        tree.add(20);
        tree.add(15);
        tree.add(25);
        tree.add(12);
        tree.add(17);
        tree.add(22);
        tree.add(26);
        tree.add(16);
        tree.add(18);

        assertEquals((Integer) 18, tree.maxDeepestNode());
    }
}