import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * CS 1332 HW 3
 * ArcWand tests for ArrayDeque and LinkedDeque
 *
 * @author Robert Zhu
 * @version 1.0
 */
public class ArcWandDequeTests {

    private static final int TIMEOUT = 200;
    private ArrayDeque<TestClass> array;
    private LinkedDeque<TestClass> linked;

	private class TestClass {
		private int value;
		private boolean flag;

		public TestClass(int value, boolean flag) {
			this.value = value;
			this.flag = flag;
		}
		public TestClass(int value) { this(value, false); }
		public TestClass(boolean flag) { this(0, flag); }

        public boolean getFlag() { return flag; }
		
		@Override
		public String toString() {
            if (flag) {
                return "FLAG";
            }
            return String.valueOf(value);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof TestClass)) { return false; }
            TestClass other = (TestClass) o;
            return value == other.value && flag == other.flag;
        }
	}

    /**
     * Given a head and tail pointer, ensures that the linked list is
     * completely equivalent to a given array
     */
    private void definitelyEqual(Object[] expected, LinkedDeque<TestClass> actual) {
        assertEquals(expected.length, actual.size());

        LinkedNode<TestClass> curr = actual.getHead();
        for (int i = 0; i < expected.length; i++) {
            if (!expected[i].equals(curr.getData())) {
				assertTrue("Expected " + expected[i] + " while iterating but got " + curr.getData(), false);
            }
            curr = curr.getNext();
        }

		curr = actual.getTail();
		for (int i = expected.length - 1; i >= 0; i--) {
			if (!expected[i].equals(curr.getData())) {
				assertTrue("Expected " + expected[i] + " while reverse iterating but got " + curr.getData(), false);
			}
			curr = curr.getPrevious();
		}
    }

    /**
     * Gets the length of a backing array without errors
     */
    private int getBackingArrayLength(ArrayDeque<TestClass> deque) {
        return ((Object[]) deque.getBackingArray()).length;
    }

    /**
     * Gets the element at the index of the backing array without errors
     */
    private TestClass getBackingArrayElement(ArrayDeque<TestClass> deque, int index) {
        return (TestClass) ((Object[]) deque.getBackingArray())[index];
    }

    @Before
    public void setup() {
        array = new ArrayDeque<>();
        linked = new LinkedDeque<>();
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_Constructor() {
        assertEquals(array.size(), 0);
        assertArrayEquals(new Object[11], array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_addFirst() {
        array.addFirst(new TestClass(1));
        array.addFirst(new TestClass(2));
        array.addFirst(new TestClass(3));
        array.addFirst(new TestClass(4));
        array.addFirst(new TestClass(5));
        array.addFirst(new TestClass(6));

        assertArrayEquals(new Object[] {
            null, null, null, null, null,
            new TestClass(6), new TestClass(5),
            new TestClass(4), new TestClass(3),
            new TestClass(2), new TestClass(1)
            }, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_addFirst_updates_Size() {
        array.addFirst(new TestClass(1));
        array.addFirst(new TestClass(2));
        array.addFirst(new TestClass(3));
        array.addFirst(new TestClass(4));
        array.addFirst(new TestClass(5));
        array.addFirst(new TestClass(6));

        assertEquals(6, array.size());
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_addFirst_Resizing() {
        for (int i = 0; i < 11; i++) {
            array.addFirst(new TestClass(i));
        }
        assertEquals(11, array.size());
        assertArrayEquals(new Object[] {
            new TestClass(10), new TestClass(9), new TestClass(8),
            new TestClass(7), new TestClass(6), new TestClass(5),
            new TestClass(4), new TestClass(3), new TestClass(2),
            new TestClass(1), new TestClass(0)
            }, array.getBackingArray());

        array.addFirst(new TestClass(11));
        assertEquals(12, array.size());
        assertArrayEquals(new Object[] {
            new TestClass(11), new TestClass(10), new TestClass(9),
            new TestClass(8), new TestClass(7), new TestClass(6),
            new TestClass(5), new TestClass(4), new TestClass(3),
            new TestClass(2), new TestClass(1), new TestClass(0),
            null, null, null, null, null, null, null, null, null, null
            }, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_Array_addFirst_Null() {
        array.addFirst(null);
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_addLast() {
        array.addLast(new TestClass(1));
        array.addLast(new TestClass(2));
        array.addLast(new TestClass(3));
        array.addLast(new TestClass(4));
        array.addLast(new TestClass(5));
        array.addLast(new TestClass(6));

        assertArrayEquals(new Object[] {
            new TestClass(1), new TestClass(2),
            new TestClass(3), new TestClass(4),
            new TestClass(5), new TestClass(6),
            null, null, null, null, null
            }, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_addLast_updates_Size() {
        array.addLast(new TestClass(1));
        array.addLast(new TestClass(2));
        array.addLast(new TestClass(3));
        array.addLast(new TestClass(4));
        array.addLast(new TestClass(5));
        array.addLast(new TestClass(6));

        assertEquals(6, array.size());
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_addLast_Resizing() {
        for (int i = 0; i < 11; i++) {
            array.addLast(new TestClass(i));
        }
        assertEquals(11, array.size());
        assertArrayEquals(new Object[] {
            new TestClass(0), new TestClass(1), new TestClass(2),
            new TestClass(3), new TestClass(4), new TestClass(5),
            new TestClass(6), new TestClass(7), new TestClass(8),
            new TestClass(9), new TestClass(10)
            }, array.getBackingArray());

        array.addLast(new TestClass(11));
        assertEquals(12, array.size());

        assertArrayEquals(new Object[] {
            new TestClass(0), new TestClass(1), new TestClass(2),
            new TestClass(3), new TestClass(4), new TestClass(5),
            new TestClass(6), new TestClass(7), new TestClass(8),
            new TestClass(9), new TestClass(10), new TestClass(11),
            null, null, null, null, null, null, null, null, null, null
            }, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_Array_addLast_Null() {
        array.addLast(null);
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_removeFirst() {
        array.addLast(new TestClass(1));
        array.addLast(new TestClass(2));
        array.addLast(new TestClass(3));
        array.addLast(new TestClass(4));
        array.addLast(new TestClass(5));
        array.addLast(new TestClass(6));

        assertEquals(new TestClass(1), array.removeFirst());
        assertEquals(new TestClass(2), array.removeFirst());
        assertEquals(new TestClass(3), array.removeFirst());

        assertArrayEquals(new Object[] {
            null, null, null, new TestClass(4),
            new TestClass(5), new TestClass(6),
            null, null, null, null, null
            }, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_removeFirst_updates_Size() {
        array.addLast(new TestClass(1));
        array.addLast(new TestClass(2));
        array.addLast(new TestClass(3));
        array.addLast(new TestClass(4));
        array.addLast(new TestClass(5));
        array.addLast(new TestClass(6));

        assertEquals(6, array.size());

        array.removeFirst();
        array.removeFirst();
        array.removeFirst();

        assertEquals(3, array.size());
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_Array_removeFirst_Empty() {
        array.removeFirst();
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_removeFirst_does_not_Resize_backing_array() {
        for (int i = 0; i < 11; i++) {
            array.addLast(new TestClass(i));
        }

        assertEquals(11, getBackingArrayLength(array));

        array.addLast(new TestClass(11));

        assertEquals(22, getBackingArrayLength(array));

        array.removeFirst();
        array.removeFirst();
        array.removeFirst();

        assertEquals(22, getBackingArrayLength(array));
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_removeFirst_sets_empty_spots_to_Null() {
        for (int i = 0; i < 11; i++) {
            array.addLast(new TestClass(i));
        }

        assertEquals(11, getBackingArrayLength(array));

        array.addLast(new TestClass(11));

        assertEquals(22, getBackingArrayLength(array));

        array.removeFirst();
        array.removeFirst();
        array.removeFirst();

        assertEquals(22, getBackingArrayLength(array));

        assertArrayEquals(new Object[] {
            null, null, null, new TestClass(3), new TestClass(4),
            new TestClass(5), new TestClass(6), new TestClass(7),
            new TestClass(8), new TestClass(9), new TestClass(10),
            new TestClass(11), null, null, null, null, null, null,
            null, null, null, null
            }, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_removeFirst_Returns_useable_String() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addLast("Linear algebra");

        assertEquals("Linear algebra sucks", deque.removeFirst() + " sucks");
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_removeLast() {
        array.addLast(new TestClass(1));
        array.addLast(new TestClass(2));
        array.addLast(new TestClass(3));
        array.addLast(new TestClass(4));
        array.addLast(new TestClass(5));
        array.addLast(new TestClass(6));

        assertEquals(new TestClass(6), array.removeLast());
        assertEquals(new TestClass(5), array.removeLast());
        assertEquals(new TestClass(4), array.removeLast());

        assertArrayEquals(new Object[] {
            new TestClass(1), new TestClass(2),
            new TestClass(3), null, null,
            null, null, null, null, null, null
            }, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_removeLast_updates_Size() {
        array.addLast(new TestClass(1));
        array.addLast(new TestClass(2));
        array.addLast(new TestClass(3));
        array.addLast(new TestClass(4));
        array.addLast(new TestClass(5));
        array.addLast(new TestClass(6));

        assertEquals(6, array.size());

        array.removeLast();
        array.removeLast();
        array.removeLast();

        assertEquals(3, array.size());
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_Array_removeLast_Empty() {
        array.removeLast();
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_removeLast_does_not_Resize_backing_array() {
        for (int i = 0; i < 11; i++) {
            array.addLast(new TestClass(i));
        }

        assertEquals(11, getBackingArrayLength(array));

        array.addLast(new TestClass(11));

        assertEquals(22, getBackingArrayLength(array));

        array.removeLast();
        array.removeLast();
        array.removeLast();

        assertEquals(22, getBackingArrayLength(array));
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_removeLast_sets_empty_spots_to_Null() {
        for (int i = 0; i < 11; i++) {
            array.addLast(new TestClass(i));
        }

        assertEquals(11, getBackingArrayLength(array));

        array.addLast(new TestClass(11));

        assertEquals(22, getBackingArrayLength(array));

        array.removeLast();
        array.removeLast();
        array.removeLast();

        assertEquals(22, getBackingArrayLength(array));

        assertArrayEquals(new Object[] {
            new TestClass(0), new TestClass(1), new TestClass(2),
            new TestClass(3), new TestClass(4), new TestClass(5),
            new TestClass(6), new TestClass(7), new TestClass(8),
            null, null, null, null, null, null, null, null, null,
            null, null, null, null
            }, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_removeLast_Returns_useable_String() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addLast("Linear algebra");

        assertEquals("Linear algebra sucks", deque.removeLast() + " sucks");
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_getFirst() {
        array.addLast(new TestClass(1));
        array.addLast(new TestClass(2));
        array.addLast(new TestClass(3));
        array.addLast(new TestClass(4));
        array.addLast(new TestClass(5));
        array.addLast(new TestClass(6));

        assertEquals(new TestClass(1), array.getFirst());
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_getFirst_resize() {
        for (int i = 0; i < 12; i++) {
            array.addLast(new TestClass(i));
        }

        assertEquals(new TestClass(0), array.getFirst());
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_getFirst_does_not_Remove_element() {
        array.addLast(new TestClass(1));
        array.addLast(new TestClass(2));
        array.addLast(new TestClass(3));

        assertEquals(new TestClass(1), array.getFirst());
        assertEquals(new TestClass(1), array.getFirst());
        assertEquals(new TestClass(1), array.getFirst());
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_Array_getFirst_Empty() {
        array.getFirst();
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_getLast() {
        array.addLast(new TestClass(1));
        array.addLast(new TestClass(2));
        array.addLast(new TestClass(3));
        array.addLast(new TestClass(4));
        array.addLast(new TestClass(5));
        array.addLast(new TestClass(6));

        assertEquals(new TestClass(6), array.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_getLast_resize() {
        for (int i = 0; i < 12; i++) {
            array.addLast(new TestClass(i));
        }

        assertEquals(new TestClass(11), array.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_getLast_does_not_Remove_element() {
        array.addLast(new TestClass(1));
        array.addLast(new TestClass(2));
        array.addLast(new TestClass(3));

        assertEquals(new TestClass(3), array.getLast());
        assertEquals(new TestClass(3), array.getLast());
        assertEquals(new TestClass(3), array.getLast());
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_Array_getLast_Empty() {
        array.getLast();
    }

    @Test(timeout = TIMEOUT)
    public void test_Array_ArrayDeque_Full_Send() {
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_Constructor() {
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_addFirst() {
        linked.addFirst(new TestClass(1));
        linked.addFirst(new TestClass(2));
        linked.addFirst(new TestClass(3));
        linked.addFirst(new TestClass(4));
        linked.addFirst(new TestClass(5));
        linked.addFirst(new TestClass(6));

        definitelyEqual(new Object[] {
            new TestClass(6), new TestClass(5),
            new TestClass(4), new TestClass(3),
            new TestClass(2), new TestClass(1)
            }, linked);
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_addFirst_updates_Size() {
        linked.addFirst(new TestClass(1));
        linked.addFirst(new TestClass(2));
        linked.addFirst(new TestClass(3));
        linked.addFirst(new TestClass(4));
        linked.addFirst(new TestClass(5));
        linked.addFirst(new TestClass(6));

        assertEquals(6, linked.size());
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_addFirst_from_Empty() {
        linked.addFirst(new TestClass(1));

        definitelyEqual(new Object[] {
            new TestClass(1)
            }, linked);
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_addFirst_from_One() {
        linked.addFirst(new TestClass(1));
        linked.addFirst(new TestClass(2));

        definitelyEqual(new Object[] {
            new TestClass(2), new TestClass(1)
            }, linked);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_Linked_addFirst_Null() {
        linked.addFirst(null);
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_addLast() {
        linked.addLast(new TestClass(1));
        linked.addLast(new TestClass(2));
        linked.addLast(new TestClass(3));
        linked.addLast(new TestClass(4));
        linked.addLast(new TestClass(5));
        linked.addLast(new TestClass(6));

        definitelyEqual(new Object[] {
            new TestClass(1), new TestClass(2),
            new TestClass(3), new TestClass(4),
            new TestClass(5), new TestClass(6)
            }, linked);
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_addLast_updates_Size() {
        linked.addLast(new TestClass(1));
        linked.addLast(new TestClass(2));
        linked.addLast(new TestClass(3));
        linked.addLast(new TestClass(4));
        linked.addLast(new TestClass(5));
        linked.addLast(new TestClass(6));

        assertEquals(6, linked.size());
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_addLast_from_Empty() {
        linked.addLast(new TestClass(1));

        definitelyEqual(new Object[] {
            new TestClass(1)
            }, linked);
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_addLast_from_One() {
        linked.addLast(new TestClass(1));
        linked.addLast(new TestClass(2));

        definitelyEqual(new Object[] {
            new TestClass(1), new TestClass(2)
            }, linked);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_Linked_addLast_Null() {
        linked.addLast(null);
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_removeFirst() {
        linked.addLast(new TestClass(1));
        linked.addLast(new TestClass(2));
        linked.addLast(new TestClass(3));
        linked.addLast(new TestClass(4));
        linked.addLast(new TestClass(5));
        linked.addLast(new TestClass(6));

        assertEquals(new TestClass(1), linked.removeFirst());
        assertEquals(new TestClass(2), linked.removeFirst());
        assertEquals(new TestClass(3), linked.removeFirst());

        definitelyEqual(new Object[] {
            new TestClass(4), new TestClass(5),
            new TestClass(6)
            }, linked);
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_removeFirst_updates_Size() {
        linked.addLast(new TestClass(1));
        linked.addLast(new TestClass(2));
        linked.addLast(new TestClass(3));
        linked.addLast(new TestClass(4));
        linked.addLast(new TestClass(5));
        linked.addLast(new TestClass(6));

        assertEquals(6, linked.size());

        linked.removeFirst();
        linked.removeFirst();
        linked.removeFirst();

        assertEquals(3, linked.size());
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_removeFirst_Returns_useable_String() {
        LinkedDeque<String> deque = new LinkedDeque<>();
        deque.addLast("Linear algebra");

        assertEquals("Linear algebra sucks", deque.removeFirst() + " sucks");
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_Linked_removeFirst_Empty() {
        linked.removeFirst();
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_removeLast() {
        linked.addLast(new TestClass(1));
        linked.addLast(new TestClass(2));
        linked.addLast(new TestClass(3));
        linked.addLast(new TestClass(4));
        linked.addLast(new TestClass(5));
        linked.addLast(new TestClass(6));

        assertEquals(new TestClass(6), linked.removeLast());
        assertEquals(new TestClass(5), linked.removeLast());
        assertEquals(new TestClass(4), linked.removeLast());

        definitelyEqual(new Object[] {
            new TestClass(1), new TestClass(2),
            new TestClass(3)
            }, linked);
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_removeLast_updates_Size() {
        linked.addLast(new TestClass(1));
        linked.addLast(new TestClass(2));
        linked.addLast(new TestClass(3));
        linked.addLast(new TestClass(4));
        linked.addLast(new TestClass(5));
        linked.addLast(new TestClass(6));

        assertEquals(6, linked.size());

        linked.removeLast();
        linked.removeLast();
        linked.removeLast();

        assertEquals(3, linked.size());
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_removeLast_Returns_useable_String() {
        LinkedDeque<String> deque = new LinkedDeque<>();
        deque.addLast("Linear algebra");

        assertEquals("Linear algebra sucks", deque.removeLast() + " sucks");
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_Linked_removeLast_Empty() {
        linked.removeLast();
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_getFirst() {
        linked.addLast(new TestClass(1));
        linked.addLast(new TestClass(2));
        linked.addLast(new TestClass(3));
        linked.addLast(new TestClass(4));
        linked.addLast(new TestClass(5));
        linked.addLast(new TestClass(6));

        assertEquals(new TestClass(1), linked.getFirst());
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_getFirst_does_not_Remove_element() {
        linked.addLast(new TestClass(1));
        linked.addLast(new TestClass(2));
        linked.addLast(new TestClass(3));

        assertEquals(new TestClass(1), linked.getFirst());
        assertEquals(new TestClass(1), linked.getFirst());
        assertEquals(new TestClass(1), linked.getFirst());
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_Linked_getFirst_Empty() {
        linked.getFirst();
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_getLast() {
        linked.addLast(new TestClass(1));
        linked.addLast(new TestClass(2));
        linked.addLast(new TestClass(3));
        linked.addLast(new TestClass(4));
        linked.addLast(new TestClass(5));
        linked.addLast(new TestClass(6));

        assertEquals(new TestClass(6), linked.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_getLast_does_not_Remove_element() {
        linked.addLast(new TestClass(1));
        linked.addLast(new TestClass(2));
        linked.addLast(new TestClass(3));

        assertEquals(new TestClass(3), linked.getLast());
        assertEquals(new TestClass(3), linked.getLast());
        assertEquals(new TestClass(3), linked.getLast());
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_Linked_getLast_Empty() {
        linked.getLast();
    }

    @Test(timeout = TIMEOUT)
    public void test_Linked_Full_Send() {
    }

}
