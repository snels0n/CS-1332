import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertArrayEquals;
/**
 * CS 1332 HW 2
 * ArcWand tests for DoublyLinkedList
 *
 * @author Robert Zhu
 * @version 1.1
 */
public class ArcWandDoublyLinkedListTests {

    private static final int TIMEOUT = 200;
    private DoublyLinkedList<TestClass> list;

	// For testing Big O
	/**
	 * By default, testing Big O is disabled, as it slows down testing.
	 * Furthermore, it is unreliable. If you fail a test, try restarting your
	 * computer and running tests multiple times. A Big O test failure is only
	 * worth investigating if it consistently fails.
	 *
	 * Set TESTRUNTIME to true to enable Big O testing.
	 */
	private static final boolean TESTRUNTIME = false;
    private static final int LONGTIMEOUT = 5000; // milliseconds
	private static final int TRIALS = 75000;
	private static final double THRESHOLD = 0.5; // percentage of first time

    private static class TestClass {
        private int id;

        public TestClass(int id) { this.id = id; }
        public TestClass() { this(0); }

        public int getId() { return id; }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof TestClass)) {
                return false;
            } else {
                TestClass other = (TestClass) o;
                return this.id == other.id;
            }
        }

		@Override
		public String toString() {
			return "TestClass(" + id + ")";
		}
    }

    private static class Tester extends TestClass {
        private double value;

		@Override
		public String toString() {
			return "Tester(" + getId() + ", " + value + ")";
		}
    }

    private void definitelyEqual(Object[] expected, DoublyLinkedList<TestClass> actual) {
		assertEquals(expected.length, actual.size());

		DoublyLinkedListNode<TestClass> curr = actual.getHead();
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

		assertArrayEquals(expected, actual.toArray());
	}

	private Object[] generateTestClassSequence(int start, int end, int step) {
		int len = Math.abs((end - start) / step);
		Object[] out = new Object[len];
		for (int i = 0; i < len; i++) {
			out[i] = new TestClass(start + i * step);
		}
		return out;
	}
	private Object[] generateTestClassSequence(int start, int end) {
		return generateTestClassSequence(start, end, 1);
	}
	private Object[] generateTestClassSequence(int end) {
		return generateTestClassSequence(0, end, 1);
	}

    @Before
    public void setUp() {
        list = new DoublyLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor() {
        assertNull(list.getHead());
        assertNull(list.getTail());
        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void test_addAtIndex_Negative_Index() {
        list.addAtIndex(-1, new TestClass());
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void test_addAtIndex_Large_Index() {
        list.addAtIndex(100, new TestClass());
    }

    @Test(timeout = TIMEOUT)
    public void test_addAtIndex_Size() {
        for (int i = 0; i < 5; i++) {
            list.addAtIndex(list.size(), new TestClass(i));
        }
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void test_addAtIndex_Large_Index_of_large_list() {
        for (int i = 0; i < 5; i++) {
            list.addAtIndex(list.size(), new TestClass(i));
        }

        list.addAtIndex(1000, new TestClass());
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void test_addAtIndex_Size_plus_One() {
        list.addAtIndex(0, new TestClass());

        list.addAtIndex(2, new TestClass());
    }

    @Test(timeout = TIMEOUT)
    public void test_addAtIndex_Descriptive_Error_Message_Negative_Index() {
        int index = -1;
        try {
            list.addAtIndex(index, new TestClass());
        } catch (IndexOutOfBoundsException e) {
            assertTrue(e.getMessage().contains("negative")
                    || e.getMessage().contains("zero")
                    || e.getMessage().contains("<")
                    || e.getMessage().contains(">")
                    || e.getMessage().contains(index + ""));
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_addAtIndex_Descriptive_Error_Message_Large_Index() {
        int index = 10000;
        try {
            list.addAtIndex(index, new TestClass());
        } catch (IndexOutOfBoundsException e) {
            assertTrue(e.getMessage().contains("<")
                    || e.getMessage().contains(">")
                    || e.getMessage().contains(index + "")
                    || e.getMessage().contains(list.size() + ""));
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_addAtIndex_Null_Data() {
        list.addAtIndex(0, null);
    }

    @Test(timeout = TIMEOUT, expected = ClassCastException.class)
    public void test_addAtIndex_Binding_Error() {
        DoublyLinkedList<Tester> subList = new DoublyLinkedList<>();
        subList.addAtIndex(0, (Tester) new TestClass());
    }

    @Test(timeout = TIMEOUT)
    public void test_addAtIndex_Inserts_at_Zero() {
        for (int i = 0; i < 50; i++) {
            list.addAtIndex(0, new TestClass(i));
			definitelyEqual(generateTestClassSequence(i, -1, -1), list);
        }

		assertEquals(50, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void test_addAtIndex_Inserts_at_Size() {
		for (int i = 0; i < 50; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
			definitelyEqual(generateTestClassSequence(i+1), list);
		}

		assertEquals(50, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void test_addAtIndex_Inserts_in_First_Half() {
		for (int i = 15; i >= 0; i--) {
			list.addAtIndex(0, new TestClass(i));
		}

		list.addAtIndex(4, new TestClass(16));
		list.addAtIndex(5, new TestClass(17));
		list.addAtIndex(6, new TestClass(18));
		list.addAtIndex(7, new TestClass(19));
		definitelyEqual(new Object[] {
			new TestClass(0), new TestClass(1), new TestClass(2), new TestClass(3),
			new TestClass(16), new TestClass(17), new TestClass(18), new TestClass(19),
			new TestClass(4), new TestClass(5), new TestClass(6), new TestClass(7),
			new TestClass(8), new TestClass(9), new TestClass(10), new TestClass(11),
			new TestClass(12), new TestClass(13), new TestClass(14), new TestClass(15)
		}, list);

		assertEquals(20, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void test_addAtIndex_Inserts_in_Second_Half() {
		for (int i = 15; i >= 0; i--) {
			list.addAtIndex(0, new TestClass(i));
		}

		list.addAtIndex(12, new TestClass(16));
		list.addAtIndex(13, new TestClass(17));
		list.addAtIndex(14, new TestClass(18));
		list.addAtIndex(15, new TestClass(19));
		definitelyEqual(new Object[] {
			new TestClass(0), new TestClass(1), new TestClass(2), new TestClass(3),
			new TestClass(4), new TestClass(5), new TestClass(6), new TestClass(7),
			new TestClass(8), new TestClass(9), new TestClass(10), new TestClass(11),
			new TestClass(16), new TestClass(17), new TestClass(18), new TestClass(19),
			new TestClass(12), new TestClass(13), new TestClass(14), new TestClass(15)
		}, list);

		assertEquals(20, list.size());
    }

	@Test(timeout = TIMEOUT)
	public void test_addAtIndex_Inserts_in_Middle() {
		for (int i = 0; i < 16; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		list.addAtIndex(8, new TestClass(16));
		list.addAtIndex(8, new TestClass(17));
		list.addAtIndex(9, new TestClass(18));
		list.addAtIndex(9, new TestClass(19));
		definitelyEqual(new Object[] {
			new TestClass(0), new TestClass(1), new TestClass(2), new TestClass(3),
			new TestClass(4), new TestClass(5), new TestClass(6), new TestClass(7),
			new TestClass(17), new TestClass(19), new TestClass(18), new TestClass(16),
			new TestClass(8), new TestClass(9), new TestClass(10), new TestClass(11),
			new TestClass(12), new TestClass(13), new TestClass(14), new TestClass(15)
		}, list);
	}

	@Test(timeout = LONGTIMEOUT)
	public void test_addAtIndex_Insertion_at_Zero_is_Faster_than_in_Middle() {
		if (!TESTRUNTIME) return;

		long startTime, endTime;
		long zeroInsertionTime, middleInsertionTime;

		// Time inserting at zero
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(0, new TestClass(i));
		}
		endTime = System.nanoTime();
		zeroInsertionTime = endTime - startTime;

		// Time inserting in middle
		list = new DoublyLinkedList<>();
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size() / 2, new TestClass(i));
		}
		endTime = System.nanoTime();
		middleInsertionTime = endTime - startTime;

		assertTrue("Inserting at zero should be faster than inserting in the middle, but it wasn't\nZero: " + zeroInsertionTime + ", Middle: " + middleInsertionTime,
			zeroInsertionTime < middleInsertionTime);
	}

	@Test(timeout = LONGTIMEOUT)
	public void test_addAtIndex_Insertion_at_Size_is_Faster_than_in_Middle() {
		if (!TESTRUNTIME) return;

		long startTime, endTime;
		long sizeInsertionTime, middleInsertionTime;

		// Time inserting at size
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}
		endTime = System.nanoTime();
		sizeInsertionTime = endTime - startTime;

		// Time inserting in middle
		list = new DoublyLinkedList<>();
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size() / 2, new TestClass(i));
		}
		endTime = System.nanoTime();
		middleInsertionTime = endTime - startTime;

		assertTrue("Inserting at size should be faster than inserting in the middle, but it wasn't\nSize: " + sizeInsertionTime + ", Middle: " + middleInsertionTime,
			sizeInsertionTime < middleInsertionTime);
	}

	@Test(timeout = LONGTIMEOUT)
	public void test_addAtIndex_Insertion_in_First_half_As_Fast_As_in_Second_half() {
		if (!TESTRUNTIME) return;

		long startTime, endTime;
		long firstHalfInsertionTime, secondHalfInsertionTime;

		// Time inserting in first half
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size() / 4, new TestClass(i));
		}
		endTime = System.nanoTime();
		firstHalfInsertionTime = endTime - startTime;

		// Time inserting in second half
		list = new DoublyLinkedList<>();
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size() * 3 / 4, new TestClass(i));
		}
		endTime = System.nanoTime();
		secondHalfInsertionTime = endTime - startTime;

		assertTrue("Inserting in first half should be as fast as inserting in second half, but it wasn't.\nFirst half: " + firstHalfInsertionTime + ", Second half: " + secondHalfInsertionTime,
			Math.abs(firstHalfInsertionTime - secondHalfInsertionTime) < firstHalfInsertionTime * THRESHOLD);
	}

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_addToFront_Null_Data() {
        list.addToFront(null);
    }

    @Test(timeout = TIMEOUT, expected = ClassCastException.class)
    public void test_addToFront_Binding_Error() {
        DoublyLinkedList<Tester> subList = new DoublyLinkedList<>();
        subList.addToFront((Tester) new TestClass());
    }

    @Test(timeout = TIMEOUT)
    public void test_addToFront_Inserts() {
        for (int i = 0; i < 50; i++) {
            list.addAtIndex(0, new TestClass(i));
			definitelyEqual(generateTestClassSequence(i, -1, -1), list);
        }

		assertEquals(50, list.size());
    }

	@Test(timeout = LONGTIMEOUT)
	public void test_addToFront_Insertion_at_Zero_Faster_than_in_Middle() {
		if (!TESTRUNTIME) return;

		long startTime, endTime;
		long zeroInsertionTime, middleInsertionTime;

		// Time inserting at zero
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.addToFront(new TestClass(i));
		}
		endTime = System.nanoTime();
		zeroInsertionTime = endTime - startTime;

		// Time inserting in middle
		list = new DoublyLinkedList<>();
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size() / 2, new TestClass(i));
		}
		endTime = System.nanoTime();
		middleInsertionTime = endTime - startTime;

		assertTrue("Inserting at zero should be faster than inserting in the middle, but it wasn't\nZero: " + zeroInsertionTime + ", Middle: " + middleInsertionTime,
			zeroInsertionTime < middleInsertionTime);
	}

	@Test(timeout = LONGTIMEOUT)
	public void test_addToFront_Insertion_at_Zero_As_Fast_As_at_Size() {
		if (!TESTRUNTIME) return;

		long startTime, endTime;
		long zeroInsertionTime, sizeInsertionTime;

		// Time inserting at zero
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.addToFront(new TestClass(i));
		}
		endTime = System.nanoTime();
		zeroInsertionTime = endTime - startTime;

		// Time inserting at size
		list = new DoublyLinkedList<>();
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}
		endTime = System.nanoTime();
		sizeInsertionTime = endTime - startTime;

		assertTrue("Inserting at zero should be as fast as inserting at size, but it wasn't.\nZero: " + zeroInsertionTime + ", Size: " + sizeInsertionTime,
			Math.abs(zeroInsertionTime - sizeInsertionTime) < zeroInsertionTime * THRESHOLD);
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void test_addToBack_Null_Data() {
		list.addToBack(null);
	}

	@Test(timeout = TIMEOUT, expected = ClassCastException.class)
	public void test_addToBack_Binding_Error() {
		DoublyLinkedList<Tester> subList = new DoublyLinkedList<>();
		subList.addToBack((Tester) new TestClass());
	}

	@Test(timeout = TIMEOUT)
	public void test_addToBack_Inserts() {
		for (int i = 0; i < 50; i++) {
			list.addToBack(new TestClass(i));

			definitelyEqual(generateTestClassSequence(i+1), list);
		}

		assertEquals(50, list.size());
	}

	@Test(timeout = LONGTIMEOUT)
	public void test_addToBack_Insertion_at_Size_Faster_than_in_Middle() {
		if (!TESTRUNTIME) return;

		long startTime, endTime;
		long sizeInsertionTime, middleInsertionTime;

		// Time inserting at size
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.addToBack(new TestClass(i));
		}
		endTime = System.nanoTime();
		sizeInsertionTime = endTime - startTime;

		// Time inserting in middle
		list = new DoublyLinkedList<>();
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size() / 2, new TestClass(i));
		}
		endTime = System.nanoTime();
		middleInsertionTime = endTime - startTime;

		assertTrue("Inserting at size should be faster than inserting in the middle, but it wasn't\nSize: " + sizeInsertionTime + ", Middle: " + middleInsertionTime,
			sizeInsertionTime < middleInsertionTime);
	}

	@Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
	public void test_removeAtIndex_Negative_Index() {
		list.removeAtIndex(-1);
	}

	@Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
	public void test_removeAtIndex_Large_Index() {
		list.removeAtIndex(100);
	}

	@Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
	public void test_removeAtIndex_Size() {
		for (int i = 0; i < 5; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		list.removeAtIndex(list.size());
	}

	@Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
	public void test_removeAtIndex_Large_Index_of_large_list() {
		for (int i = 0; i < 5; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		list.removeAtIndex(1000);
	}

	@Test(timeout = TIMEOUT)
	public void test_removeAtIndex_Descriptive_Error_Message_Negative_Index() {
		int index = -1;
		try {
			list.removeAtIndex(index);
		} catch (IndexOutOfBoundsException e) {
			assertTrue(e.getMessage().contains("negative")
					|| e.getMessage().contains("zero")
					|| e.getMessage().contains("<")
					|| e.getMessage().contains(">")
					|| e.getMessage().contains(index + ""));
		}
	}

	@Test(timeout = TIMEOUT)
	public void test_removeAtIndex_Descriptive_Error_Message_Large_Index() {
		int index = 10000;
		try {
			list.removeAtIndex(index);
		} catch (IndexOutOfBoundsException e) {
			assertTrue(e.getMessage().contains("<")
					|| e.getMessage().contains(">")
					|| e.getMessage().contains(index + "")
					|| e.getMessage().contains(list.size() + ""));
		}
	}

	@Test(timeout = TIMEOUT)
	public void test_removeAtIndex_Removes_to_Empty() {
		for (int i = 0; i < 5; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		for (int i = 4; i >= 0; i--) {
			list.removeAtIndex(list.size() - 1);
			definitelyEqual(generateTestClassSequence(0, i), list);
		}
	}

	@Test(timeout = TIMEOUT)
	public void test_removeAtIndex_Removes_at_Zero() {
		for (int i = 0; i < 5; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		for (int i = 0; i < 5; i++) {
			list.removeAtIndex(0);
			definitelyEqual(generateTestClassSequence(i + 1, 5), list);
		}
	}

	@Test(timeout = TIMEOUT)
	public void test_removeAtIndex_Removes_at_Size_minus_One() {
		for (int i = 0; i < 5; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		for (int i = 4; i >= 0; i--) {
			list.removeAtIndex(list.size() - 1);
			definitelyEqual(generateTestClassSequence(0, i), list);
		}
	}

	@Test(timeout = TIMEOUT)
	public void test_removeAtIndex_Removes_in_First_Half() {
		for (int i = 0; i < 20; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		for (int i = 0; i < 5; i++) {
			list.removeAtIndex(3);
		}
		definitelyEqual(new Object[] {
			new TestClass(0), new TestClass(1), new TestClass(2), new TestClass(8),
			new TestClass(9), new TestClass(10), new TestClass(11), new TestClass(12),
			new TestClass(13), new TestClass(14), new TestClass(15), new TestClass(16),
			new TestClass(17), new TestClass(18), new TestClass(19)
		}, list);

		assertEquals(15, list.size());
	}

	@Test(timeout = TIMEOUT)
	public void test_removeAtIndex_Removes_in_Second_Half() {
		for (int i = 0; i < 20; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		for (int i = 0; i < 5; i++) {
			list.removeAtIndex(13);
		}
		definitelyEqual(new Object[] {
			new TestClass(0), new TestClass(1), new TestClass(2), new TestClass(3),
			new TestClass(4), new TestClass(5), new TestClass(6), new TestClass(7),
			new TestClass(8), new TestClass(9), new TestClass(10), new TestClass(11),
			new TestClass(12), new TestClass(18), new TestClass(19)
		}, list);

		assertEquals(15, list.size());
	}

	@Test(timeout = TIMEOUT)
	public void test_removeAtIndex_Removes_in_Middle() {
		for (int i = 0; i < 20; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		for (int i = 0; i < 5; i++) {
			list.removeAtIndex(list.size() / 2);
		}
		definitelyEqual(new Object[] {
			new TestClass(0), new TestClass(1), new TestClass(2), new TestClass(3),
			new TestClass(4), new TestClass(5), new TestClass(6), new TestClass(7),
			new TestClass(13), new TestClass(14), new TestClass(15), new TestClass(16),
			new TestClass(17), new TestClass(18), new TestClass(19)
		}, list);

		assertEquals(15, list.size());
	}

	@Test(timeout = LONGTIMEOUT)
	public void test_removeAtIndex_Removes_at_Zero_Faster_than_in_Middle() {
		if (!TESTRUNTIME) return;

		long startTime, endTime;
		long zeroRemovalTime, middleRemovalTime;

		// Setup list
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}
		
		// Time removing at zero
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.removeAtIndex(0);
		}
		endTime = System.nanoTime();
		zeroRemovalTime = endTime - startTime;

		// Reset list
		list = new DoublyLinkedList<>();
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		// Time removing in middle
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.removeAtIndex(list.size() / 2);
		}
		endTime = System.nanoTime();
		middleRemovalTime = endTime - startTime;

		assertTrue("Removing at zero should be faster than removing in the middle, but it wasn't\nZero: " + zeroRemovalTime + ", Middle: " + middleRemovalTime,
			zeroRemovalTime < middleRemovalTime);
	}

	@Test(timeout = LONGTIMEOUT)
	public void test_removeAtIndex_Removes_at_Size_Faster_than_in_Middle() {
		if (!TESTRUNTIME) return;

		long startTime, endTime;
		long sizeRemovalTime, middleRemovalTime;

		// Setup list
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		// Time removing at size
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.removeAtIndex(list.size() - 1);
		}
		endTime = System.nanoTime();
		sizeRemovalTime = endTime - startTime;

		// Reset list
		list = new DoublyLinkedList<>();
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		// Time removing in middle
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.removeAtIndex(list.size() / 2);
		}
		endTime = System.nanoTime();
		middleRemovalTime = endTime - startTime;

		assertTrue("Removing at size should be faster than removing in the middle, but it wasn't\nSize: " + sizeRemovalTime + ", Middle: " + middleRemovalTime,
			sizeRemovalTime < middleRemovalTime);
	}

	@Test(timeout = LONGTIMEOUT)
	public void test_removeAtIndex_Removing_in_First_half_As_Fast_As_in_Second_half() {
		if (!TESTRUNTIME) return;

		long startTime, endTime;
		long firstHalfRemovalTime, secondHalfRemovalTime;

		// Setup list
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		// Time removing in first half
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.removeAtIndex(list.size() / 4);
		}
		endTime = System.nanoTime();
		firstHalfRemovalTime = endTime - startTime;

		// Reset list
		list = new DoublyLinkedList<>();
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		// Time removing in second half
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.removeAtIndex(list.size() * 3 / 4);
		}
		endTime = System.nanoTime();
		secondHalfRemovalTime = endTime - startTime;

		assertTrue("Removing in first half should be as fast as removing in second half, but it wasn't.\nFirst half: " + firstHalfRemovalTime + ", Second half: " + secondHalfRemovalTime,
			Math.abs(firstHalfRemovalTime - secondHalfRemovalTime) < firstHalfRemovalTime * THRESHOLD);
	}

	@Test(timeout = TIMEOUT)
	public void test_removeAtIndex_Returns_useable_String() {
		DoublyLinkedList<String> stringList = new DoublyLinkedList<>();
		stringList.addAtIndex(0, "Catppuccin");

		String removed = stringList.removeAtIndex(0);
		assertEquals("Catppuccin", removed);
		assertEquals("Cappuccino", removed.substring(0, 10).replace("t", "") + "o");
	}

	@Test(timeout = TIMEOUT)
	public void test_removeAtIndex_Returns_Integer_dynamically_binded() {
		DoublyLinkedList<Number> numberList = new DoublyLinkedList<>();
		numberList.addAtIndex(0, 42);

		Number removed = numberList.removeAtIndex(0);
		assertEquals(42, removed);
		assertEquals(21, removed.intValue() / 2);
		assertTrue(removed instanceof Number);
		assertTrue(removed instanceof Integer);
	}

	@Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
	public void test_removeFromFront_Empty() {
		list.removeFromFront();
	}

	@Test(timeout = TIMEOUT)
	public void test_removeFromFront_Removes_at_Zero() {
		for (int i = 0; i < 5; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		for (int i = 0; i < 5; i++) {
			list.removeFromFront();
			definitelyEqual(generateTestClassSequence(i + 1, 5), list);
		}
	}

	@Test(timeout = LONGTIMEOUT)
	public void test_removeFromFront_Remove_at_Zero_Faster_than_in_Middle() {
		if (!TESTRUNTIME) return;

		long startTime, endTime;
		long zeroRemovalTime, middleRemovalTime;

		// Setup list
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		// Time removing at zero
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.removeFromFront();
		}
		endTime = System.nanoTime();
		zeroRemovalTime = endTime - startTime;

		// Reset list
		list = new DoublyLinkedList<>();
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		// Time removing in middle
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.removeAtIndex(list.size() / 2);
		}
		endTime = System.nanoTime();
		middleRemovalTime = endTime - startTime;

		assertTrue("Removing at zero should be faster than removing in the middle, but it wasn't\nZero: " + zeroRemovalTime + ", Middle: " + middleRemovalTime,
			zeroRemovalTime < middleRemovalTime);
	}

	@Test(timeout = LONGTIMEOUT)
	public void test_removeFromFront_Remove_at_Zero_As_Fast_As_at_Size() {
		if (!TESTRUNTIME) return;

		long startTime, endTime;
		long zeroRemovalTime, sizeRemovalTime;

		// Setup list
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		// Time removing at zero
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.removeFromFront();
		}
		endTime = System.nanoTime();
		zeroRemovalTime = endTime - startTime;

		// Reset list
		list = new DoublyLinkedList<>();
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		// Time removing at size
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.removeAtIndex(list.size() - 1);
		}
		endTime = System.nanoTime();
		sizeRemovalTime = endTime - startTime;

		assertTrue("Removing at zero should be as fast as removing at size, but it wasn't.\nZero: " + zeroRemovalTime + ", Size: " + sizeRemovalTime,
			Math.abs(zeroRemovalTime - sizeRemovalTime) < zeroRemovalTime * THRESHOLD);
	}

	@Test(timeout = TIMEOUT)
	public void test_removeFromFront_Returns_useable_String() {
		DoublyLinkedList<String> stringList = new DoublyLinkedList<>();
		stringList.addAtIndex(0, "Catppuccin");

		String removed = stringList.removeFromFront();
		assertEquals("Catppuccin", removed);
		assertEquals("Cappuccino", removed.substring(0, 10).replace("t", "") + "o");
	}

	@Test(timeout = TIMEOUT)
	public void test_removeFromFront_Returns_Integer_dynamically_binded() {
		DoublyLinkedList<Number> numberList = new DoublyLinkedList<>();
		numberList.addAtIndex(0, 42);

		Number removed = numberList.removeFromFront();
		assertEquals(42, removed);
		assertEquals(21, removed.intValue() / 2);
		assertTrue(removed instanceof Number);
		assertTrue(removed instanceof Integer);
	}

	@Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
	public void test_removeFromBack_Empty() {
		list.removeFromBack();
	}

	@Test(timeout = TIMEOUT)
	public void test_removeFromBack_Removes_at_Size_minus_One() {
		for (int i = 0; i < 5; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		for (int i = 4; i > 0; i--) {
			list.removeFromBack();
			definitelyEqual(generateTestClassSequence(0, i), list);
		}
	}

	@Test(timeout = LONGTIMEOUT)
	public void test_removeFromBack_Remove_at_Size_Faster_than_in_Middle() {
		if (!TESTRUNTIME) return;

		long startTime, endTime;
		long sizeRemovalTime, middleRemovalTime;

		// Setup list
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		// Time removing at size
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.removeFromBack();
		}
		endTime = System.nanoTime();
		sizeRemovalTime = endTime - startTime;

		// Reset list
		list = new DoublyLinkedList<>();
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		// Time removing in middle
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.removeAtIndex(list.size() / 2);
		}
		endTime = System.nanoTime();
		middleRemovalTime = endTime - startTime;

		assertTrue("Removing at size should be faster than removing in the middle, but it wasn't\nSize: " + sizeRemovalTime + ", Middle: " + middleRemovalTime,
			sizeRemovalTime < middleRemovalTime);
	}

	@Test(timeout = LONGTIMEOUT)
	public void test_removeFromBack_Remove_at_Size_As_Fast_As_at_Zero() {
		if (!TESTRUNTIME) return;

		long startTime, endTime;
		long sizeRemovalTime, zeroRemovalTime;

		// Setup list
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		// Time removing at size
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.removeFromBack();
		}
		endTime = System.nanoTime();
		sizeRemovalTime = endTime - startTime;

		// Reset list
		list = new DoublyLinkedList<>();
		for (int i = 0; i < TRIALS; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		// Time removing at zero
		startTime = System.nanoTime();
		for (int i = 0; i < TRIALS; i++) {
			list.removeFromFront();
		}
		endTime = System.nanoTime();
		zeroRemovalTime = endTime - startTime;

		assertTrue("Removing at size should be as fast as removing at zero, but it wasn't.\nSize: " + sizeRemovalTime + ", Zero: " + zeroRemovalTime,
			Math.abs(sizeRemovalTime - zeroRemovalTime) < sizeRemovalTime * THRESHOLD);
	}

	@Test(timeout = TIMEOUT)
	public void test_removeFromBack_Returns_useable_String() {
		DoublyLinkedList<String> stringList = new DoublyLinkedList<>();
		stringList.addAtIndex(0, "Catppuccin");

		String removed = stringList.removeFromBack();
		assertEquals("Catppuccin", removed);
		assertEquals("Cappuccino", removed.substring(0, 10).replace("t", "") + "o");
	}

	@Test(timeout = TIMEOUT)
	public void test_removeFromBack_Returns_Integer_dynamically_binded() {
		DoublyLinkedList<Number> numberList = new DoublyLinkedList<>();
		numberList.addAtIndex(0, 42);

		Number removed = numberList.removeFromBack();
		assertEquals(42, removed);
		assertEquals(21, removed.intValue() / 2);
		assertTrue(removed instanceof Number);
		assertTrue(removed instanceof Integer);
	}

	@Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
	public void test_get_Negative_Index() {
		list.get(-1);
	}

	@Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
	public void test_get_Large_Index() {
		list.get(100);
	}

	@Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
	public void test_get_Size() {
		for (int i = 0; i < 5; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		list.get(list.size());
	}

	@Test(timeout = TIMEOUT)
	public void test_get_Size_minus_One() {
		for (int i = 0; i < 5; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		assertEquals(new TestClass(4), list.get(list.size() - 1));
	}

	@Test(timeout = TIMEOUT)
	public void test_get_Returns_useable_String() {
		DoublyLinkedList<String> stringList = new DoublyLinkedList<>();
		stringList.addAtIndex(0, "Catppuccin");

		String removed = stringList.get(0);
		assertEquals("Catppuccin", removed);
		assertEquals("Cappuccino", removed.substring(0, 10).replace("t", "") + "o");
	}

	@Test(timeout = TIMEOUT)
	public void test_get_Returns_Integer_dynamically_binded() {
		DoublyLinkedList<Number> numberList = new DoublyLinkedList<>();
		numberList.addAtIndex(0, 42);

		Number removed = numberList.get(0);
		assertEquals(42, removed);
		assertEquals(21, removed.intValue() / 2);
		assertTrue(removed instanceof Number);
		assertTrue(removed instanceof Integer);
	}

	@Test(timeout = TIMEOUT)
	public void test_isEmpty_Empty() {
		assertTrue(list.isEmpty());
	}

	@Test(timeout = TIMEOUT)
	public void test_isEmpty_Not_Empty() {
		list.addAtIndex(0, new TestClass());

		assertFalse(list.isEmpty());
	}

	@Test(timeout = TIMEOUT)
	public void test_clear() {
		for (int i = 0; i < 5; i++) {
			list.addAtIndex(list.size(), new TestClass());
		}

		list.clear();

		assertNull(list.getHead());
		assertNull(list.getTail());
		assertEquals(0, list.size());
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void test_removeLastOccurence_Null() {
		list.removeLastOccurrence(null);
	}

	@Test(timeout = TIMEOUT, expected = ClassCastException.class)
	public void test_removeLastOccurence_Binding_Error() {
		DoublyLinkedList<Tester> subList = new DoublyLinkedList<>();
		subList.removeLastOccurrence((Tester) new TestClass());
	}

	@Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
	public void test_removeLastOccurence_Empty() {
		list.removeLastOccurrence(new TestClass());
	}

	@Test(timeout = TIMEOUT)
	public void test_removeLastOccurence_Removes() {
		for (int i = 0; i < 5; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		list.removeLastOccurrence(new TestClass(2));
		definitelyEqual(new Object[] {
			new TestClass(0), new TestClass(1), new TestClass(3), new TestClass(4)
		}, list);
	}

	@Test(timeout = TIMEOUT)
	public void test_removeLastOccurence_Removes_Only_Last() {
		for (int i = 0; i < 5; i++) {
			list.addAtIndex(list.size(), new TestClass(i % 3));
		}

		list.removeLastOccurrence(new TestClass(1));
		definitelyEqual(new Object[] {
			new TestClass(0), new TestClass(1), new TestClass(2), new TestClass(0)
		}, list);
	}
    
    @Test(timeout = TIMEOUT)
    public void test_removeLastOccurence_Removes_All() {
        for (int i = 0; i < 5; i++) {
            list.addAtIndex(list.size(), new TestClass(i % 3));
        }

        list.removeLastOccurrence(new TestClass(0));
        definitelyEqual(new Object[] {
            new TestClass(0), new TestClass(1), new TestClass(2), new TestClass(1)
        }, list);

        list.removeLastOccurrence(new TestClass(1));
        definitelyEqual(new Object[] {
            new TestClass(0), new TestClass(1), new TestClass(2)
        }, list);

        list.removeLastOccurrence(new TestClass(2));
        definitelyEqual(new Object[] {
            new TestClass(0), new TestClass(1)
        }, list);

        list.removeLastOccurrence(new TestClass(0));
        definitelyEqual(new Object[] {
            new TestClass(1)
        }, list);

        list.removeLastOccurrence(new TestClass(1));
        definitelyEqual(new Object[] {}, list);
    }

	@Test(timeout = TIMEOUT)
	public void test_removeLastOccurence_Returns_useable_String() {
		DoublyLinkedList<String> stringList = new DoublyLinkedList<>();
		stringList.addAtIndex(0, "Catppuccin");

		String removed = stringList.removeLastOccurrence("Catppuccin");
		assertEquals("Catppuccin", removed);
		assertEquals("Cappuccino", removed.substring(0, 10).replace("t", "") + "o");
	}

	@Test(timeout = TIMEOUT)
	public void test_removeLastOccurence_Returns_Integer_dynamically_binded() {
		DoublyLinkedList<Number> numberList = new DoublyLinkedList<>();
		numberList.addAtIndex(0, 42);

		Number removed = numberList.removeLastOccurrence(42);
		assertEquals(42, removed);
		assertEquals(21, removed.intValue() / 2);
		assertTrue(removed instanceof Number);
		assertTrue(removed instanceof Integer);
	}

	@Test(timeout = TIMEOUT)
	public void test_toArray() {
		for (int i = 0; i < 5; i++) {
			list.addAtIndex(list.size(), new TestClass(i));
		}

		definitelyEqual(new Object[] {
			new TestClass(0), new TestClass(1), new TestClass(2), new TestClass(3), new TestClass(4) 
		}, list);
	}
}
