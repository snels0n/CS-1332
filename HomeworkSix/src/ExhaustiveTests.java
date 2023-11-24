import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Basic additional tests for HashMap.
 *
 * @author Alexander Gualino
 * @version 1.0
 */
public class ExhaustiveTests {
    private static final int TIMEOUT = 200;

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutNullKey() {
        new QuadraticProbingHashMap<>().put(null, 0);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutNullValue() {
        new QuadraticProbingHashMap<>().put(0, null);
    }

    @Test(timeout = TIMEOUT)
    public void testResizingOnAdd() {
        var hm = new QuadraticProbingHashMap<Integer, Integer>(100);

        assertEquals(100, hm.getTable().length);

        // not sure why MAX_LOAD_FACTOR is private...
        var i = 0;
        for (; i < 67; i++) {
            hm.put(i, i);
        }
        assertEquals(100, hm.getTable().length);

        hm.put(i, i);
        assertEquals(201, hm.getTable().length);
    }

    @Test(timeout = TIMEOUT)
    public void testAddGetContainsAndRemoveNegativeHashcode() {
        var evilObject1 = new Object() {
            @Override
            public int hashCode() {
                return -42;
            }
        };
        var hm = new QuadraticProbingHashMap<>();
        hm.put(evilObject1, 42);

        assertSame(evilObject1, hm.getTable()[Math.abs(-42 % QuadraticProbingHashMap.INITIAL_CAPACITY)].getKey());
        // ensure that that math.abs isn't forgotten elsewhere
        var evilObject2 = new Object() {
            @Override
            public int hashCode() {
                return -42;
            }
        };
        assertNull(hm.put(evilObject2, 43));
        // reference: @650
        assertSame(evilObject2, hm.getTable()[(Math.abs(-42 % QuadraticProbingHashMap.INITIAL_CAPACITY) + 1) % QuadraticProbingHashMap.INITIAL_CAPACITY].getKey());

        assertTrue(hm.containsKey(evilObject2));
        assertTrue(hm.containsKey(evilObject1));

        assertEquals(43, hm.get(evilObject2));
        assertEquals(42, hm.get(evilObject1));

        assertEquals(43, hm.remove(evilObject2));
        assertEquals(42, hm.remove(evilObject1));
    }

    @Test(timeout = TIMEOUT)
    public void testReplaceUponTooManyDeletes() {
        var hm = new QuadraticProbingHashMap<Integer, Integer>();
        for (var i = 0; i < QuadraticProbingHashMap.INITIAL_CAPACITY; i++) {
            assertNull(hm.put(i, i));
            assertEquals(i, (int) hm.remove(i));
        }
        assertEquals(QuadraticProbingHashMap.INITIAL_CAPACITY, hm.getTable().length);

        // reference: @617
        assertNull(hm.put(QuadraticProbingHashMap.INITIAL_CAPACITY, QuadraticProbingHashMap.INITIAL_CAPACITY));
        assertEquals(QuadraticProbingHashMap.INITIAL_CAPACITY, hm.getTable().length);
        assertNotNull(hm.getTable()[0]);
        assertFalse(hm.getTable()[0].isRemoved());
    }

    @Test(timeout = TIMEOUT)
    public void testResizeUponLoop() {
        // used a Python script to find these values :^)

        // min_hit = float("inf")
        // when = 0
        // for capacity in range(1, 30):
        //     hits = set()
        //     for offset in range(capacity):
        //         hits.add((offset ** 2) % capacity)
        //     if min_hit > len(hits) and 0.67 * capacity > len(hits) + 1:
        //         min_hit = len(hits)
        //         when = capacity

        // final results:
        //  capacity of 8 only needs 3 to make a loop
        var hm = new QuadraticProbingHashMap<Integer, Integer>(8);
        hm.put(0, 1);
        hm.put(8, 2);
        hm.put(16, 3);

        // now there's a loop. I just need to add once more and it should resize.
        assertEquals(8, hm.getTable().length);
        hm.put(24, 4);
        assertEquals(17, hm.getTable().length);
        assertTrue(hm.containsKey(0));
        assertTrue(hm.containsKey(8));
        assertTrue(hm.containsKey(16));
        assertTrue(hm.containsKey(24));
        assertFalse(hm.containsKey(32));
    }

    @Test(timeout = TIMEOUT)
    public void testPutCanOverwriteDeletedElement() {
        var hm = new QuadraticProbingHashMap<Integer, Integer>();
        assertNull(hm.put(1, 1));
        assertEquals(1, (int) hm.remove(1));
        assertNotNull(hm.getTable()[1]);
        assertTrue(hm.getTable()[1].isRemoved());
        assertNull(hm.put(QuadraticProbingHashMap.INITIAL_CAPACITY + 1, 2));
        assertNotNull(hm.getTable()[1]);
        assertFalse(hm.getTable()[1].isRemoved());
        assertEquals(QuadraticProbingHashMap.INITIAL_CAPACITY + 1, (int) hm.getTable()[1].getKey());
        assertEquals(2, (int) hm.getTable()[1].getValue());
        assertEquals(1, hm.size());
    }

    @Test(timeout = TIMEOUT)
    public void testPutReturnsNullForDeletedElement() {
        var hm = new QuadraticProbingHashMap<Integer, Integer>();
        assertNull(hm.put(1, 1));
        hm.remove(1);
        assertNull(hm.put(1, 1));
    }

    @Test(timeout = TIMEOUT)
    public void testPutReturnsOldElement() {
        var hm = new QuadraticProbingHashMap<Integer, Integer>();
        assertNull(hm.put(1, 1));
        assertEquals(1, (int) hm.put(1, 2));
        assertEquals(2, (int) hm.get(1));
    }

    @Test(timeout = TIMEOUT)
    public void testPutReplacesFirstDeletedElement() {
        var hm = new QuadraticProbingHashMap<Integer, Integer>();
        hm.put(1, 1);
        hm.put(2, 2);
        hm.remove(1);
        hm.remove(2);
        assertNull(hm.put(QuadraticProbingHashMap.INITIAL_CAPACITY + 1, 1));
        assertEquals(QuadraticProbingHashMap.INITIAL_CAPACITY + 1, (int) hm.getTable()[1].getKey());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNullKey() {
        new QuadraticProbingHashMap<>().remove(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveDataNotInHashMap() {
        var hm = new QuadraticProbingHashMap<Integer, Integer>();
        hm.put(1, 1);
        hm.remove(QuadraticProbingHashMap.INITIAL_CAPACITY + 1);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveCanGiveUp() {
        var hm = new QuadraticProbingHashMap<Integer, Integer>();
        for (var i = 0; i < QuadraticProbingHashMap.INITIAL_CAPACITY; i++) {
            hm.put(i, i);
            hm.remove(i);
        }

        hm.remove(1);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetNullKey() {
        new QuadraticProbingHashMap<>().get(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetDataNotInHashMap() {
        var hm = new QuadraticProbingHashMap<Integer, Integer>();
        hm.put(1, 1);
        hm.get(QuadraticProbingHashMap.INITIAL_CAPACITY + 1);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetCanGiveUp() {
        var hm = new QuadraticProbingHashMap<Integer, Integer>();
        for (var i = 0; i < QuadraticProbingHashMap.INITIAL_CAPACITY; i++) {
            hm.put(i, i);
            hm.remove(i);
        }

        hm.get(1);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testContainsWithNullKey() {
        new QuadraticProbingHashMap<>().containsKey(null);
    }

    @Test(timeout = TIMEOUT)
    public void testKeySetAndValuesDiscardRemovedValues() {
        var hm = new QuadraticProbingHashMap<Integer, Integer>();
        hm.put(1, 1);
        hm.remove(1);
        assertEquals(0, hm.keySet().size());
        assertEquals(0, hm.values().size());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testResizeToZero() {
        var hm = new QuadraticProbingHashMap<Integer, Integer>();
        hm.put(1, 1);
        hm.resizeBackingTable(0);
    }

    @Test(timeout = TIMEOUT)
    public void testShrinkToFit() {
        var hm = new QuadraticProbingHashMap<Integer, Integer>();
        hm.put(1, 1);
        hm.resizeBackingTable(1);
        assertEquals(1, hm.getTable().length);
        assertEquals(1, (int) hm.getTable()[0].getValue());
    }

    @Test(timeout = TIMEOUT)
    public void testResizeCleansOldElements() {
        var hm = new QuadraticProbingHashMap<Integer, Integer>();
        hm.put(1, 1);
        hm.remove(1);
        hm.resizeBackingTable(1);
        assertNull(hm.getTable()[0]);
    }

    @Test(timeout = TIMEOUT)
    public void testResizeWithEvilObject() {
        var hm = new QuadraticProbingHashMap<Object, Integer>();
        var zeroObject = new Object() {
            @Override
            public int hashCode() {
                return 0;
            }
        };
        hm.put(zeroObject, 41);
        var evilObject = new Object() {
            @Override
            public int hashCode() {
                return -41;
            }
        };
        var evilObject2 = new Object() {
            @Override
            public int hashCode() {
                return -41;
            }
        };
        hm.put(evilObject, 42);
        hm.put(evilObject2, 43);
        hm.resizeBackingTable(4);
        assertEquals(41, (int) hm.getTable()[0].getValue());
        assertEquals(42, (int) hm.getTable()[1].getValue());
        assertEquals(43, (int) hm.getTable()[2].getValue());
    }
}
