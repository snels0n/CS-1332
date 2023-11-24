import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * CS 1332 HW 6
 * ArcWand tests for Quadratic Probing Hash Map
 *
 * @author Robert Zhu
 * @version 1.0
 */
public class ArcWandHashMapTests {

    private static final int TIMEOUT = 200;
    private QuadraticProbingHashMap<Integer, String> map;
    private QuadraticProbingHashMap<Integer, TestClass> testMap;

    private class TestClass {
        public int i;
        public boolean b;

        public TestClass(int i, boolean b) {
            this.i = i;
            this.b = b;
        }
        public TestClass(int i) {
            this.i = i;
            this.b = false;
        }
        public TestClass() {
            this.i = 0;
            this.b = false;
        }
    }

    @Before
    public void setup() {
        map = new QuadraticProbingHashMap<>();
        testMap = new QuadraticProbingHashMap<>();
    }

    @Test(timeout = TIMEOUT)
    public void test_NoArg_Constructor() {
        assertEquals(0, map.size());
        assertEquals(QuadraticProbingHashMap.INITIAL_CAPACITY, map.getTable().length);
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor() {
        int len = 3;
        map = new QuadraticProbingHashMap<>(len);
        assertEquals(0, map.size());
        assertEquals(len, map.getTable().length);

        len = 6;
        map = new QuadraticProbingHashMap<>(len);
        assertEquals(0, map.size());
        assertEquals(len, map.getTable().length);

        len = 1242;
        map = new QuadraticProbingHashMap<>(len);
        assertEquals(0, map.size());
        assertEquals(len, map.getTable().length);

        len = 21;
        map = new QuadraticProbingHashMap<>(len);
        assertEquals(0, map.size());
        assertEquals(len, map.getTable().length);

        len = 9015;
        map = new QuadraticProbingHashMap<>(len);
        assertEquals(0, map.size());
        assertEquals(len, map.getTable().length);

        len = 811891;
        map = new QuadraticProbingHashMap<>(len);
        assertEquals(0, map.size());
        assertEquals(len, map.getTable().length);

    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_put_null_key() {
        map.put(null, "value");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_put_null_value() {
        map.put(1, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_put_null_key_and_value() {
        map.put(null, null);
    }

    @Test(timeout = TIMEOUT)
    public void test_put_updates_size() {
        map.put(1, "value");
        assertEquals(1, map.size());
    }

    @Test(timeout = TIMEOUT)
    public void test_put_resize_at_correct_load_factor() {
        map = new QuadraticProbingHashMap<>(100);

        // Should not resize at 66 or 67 elements
        for (int i = 0; i < 66; i++) {
            map.put(i, "value");
        }
        assertEquals(100, map.getTable().length);
        map.put(66, "value");
        assertEquals(100, map.getTable().length);

        // Should resize at 68 elements
        map.put(67, "value");
        assertEquals(201, map.getTable().length);
    }

    @Test(timeout = TIMEOUT)
    public void test_put_resize_without_checking_duplicate() {
        map = new QuadraticProbingHashMap<>(100);

        // Should not resize at 66 or 67 elements
        for (int i = 0; i < 66; i++) {
            map.put(i, "value");
        }
        assertEquals(100, map.getTable().length);
        map.put(66, "value");
        assertEquals(100, map.getTable().length);

        // Should resize at 68 elements despite duplicate
        map.put(1, "value");
        assertEquals(201, map.getTable().length);
    }

    @Test(timeout = TIMEOUT)
    public void test_put_resize_after_probing_table_length_times() {
        map = new QuadraticProbingHashMap<>(8);

        // Insert 3 colliding elements
        map.put(0, "value");
        map.put(8, "value");
        map.put(16, "value");
        assertEquals(3, map.size());
        assertEquals(8, map.getTable().length);

        // Inserting another colliding element should prompt resize
        map.put(24, "value");
        assertEquals(17, map.getTable().length);
    }

    @Test(timeout = TIMEOUT)
    public void test_put_resize_to_correct_length() {
        for (int i = 0; i < 8; i++) {
            map.put(i, "value");
        }
        assertEquals(13, map.getTable().length);

        // Inserting another colliding element should prompt resize
        map.put(8, "value");
        assertEquals(27, map.getTable().length);
    }

    @Test(timeout = TIMEOUT)
    public void test_put_return_null() {
        assertNull(map.put(1, "value"));
    }

    @Test(timeout = TIMEOUT)
    public void test_put_return_replaced_value() {
        map.put(1, "value");
        assertEquals("value", map.put(1, "new value"));
    }

    @Test(timeout = TIMEOUT)
    public void test_put_return_replaced_value_when_given_duplicate() {
        testMap.put(1, new TestClass(1));
        assertFalse(testMap.put(1, new TestClass(1, true)).b);
    }

    @Test(timeout = TIMEOUT)
    public void test_put_updates_duplicate_keys() {
        map.put(1, "value");
        map.put(1, "new value");
        assertEquals("new value", map.get(1));
    }

    @Test(timeout = TIMEOUT)
    public void test_put_updates_duplicate_value() {
        testMap.put(1, new TestClass(1));
        testMap.put(1, new TestClass(1, true));
        assertTrue(testMap.get(1).b);
    }

    @Test(timeout = TIMEOUT)
    public void test_put_probe_correct_indices() {
        map.put(1, "value");
        map.put(14, "value");
        map.put(27, "value");
        map.put(40, "value");
        map.put(53, "value");
        map.put(66, "value");
        map.put(79, "value");
        assertArrayEquals(new QuadraticProbingMapEntry[] {
            new QuadraticProbingMapEntry<>(66, "value"),
            new QuadraticProbingMapEntry<>(1, "value"),
            new QuadraticProbingMapEntry<>(14, "value"),
            null,
            new QuadraticProbingMapEntry<>(53, "value"),
            new QuadraticProbingMapEntry<>(27, "value"),
            null,
            null,
            null,
            null,
            new QuadraticProbingMapEntry<>(40, "value"),
            new QuadraticProbingMapEntry<>(79, "value"),
            null
        }, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void test_put_generally_correct() {
        map.put(1, "value");
        map.put(2, "value");
        map.put(3, "value");
        map.put(4, "value");
        map.put(5, "value");
        map.put(6, "value");
        assertArrayEquals(new QuadraticProbingMapEntry[] {
            null,
            new QuadraticProbingMapEntry<>(1, "value"),
            new QuadraticProbingMapEntry<>(2, "value"),
            new QuadraticProbingMapEntry<>(3, "value"),
            new QuadraticProbingMapEntry<>(4, "value"),
            new QuadraticProbingMapEntry<>(5, "value"),
            new QuadraticProbingMapEntry<>(6, "value"),
            null,
            null,
            null,
            null,
            null,
            null
        }, map.getTable());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_remove_null_key() {
        map.remove(null);
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_updates_size() {
        map.put(1, "value");
        map.remove(1);
        assertEquals(0, map.size());
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_remove_empty_map() {
        map.remove(1);
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_remove_key_not_in_map() {
        for (int i = 0; i < 8; i++) {
            map.put(i, "value");
        }

        map.remove(8);
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_probes_correctly_on_collision() {
        map.put(1, "value");
        map.put(14, "value");
        map.put(27, "value");
        map.put(40, "value");
        map.put(53, "value");
        map.put(66, "value");
        map.put(79, "value");

        map.remove(79);
        QuadraticProbingMapEntry<Integer, String> removed = new QuadraticProbingMapEntry<>(79, "value");
        removed.setRemoved(true);
        assertArrayEquals(new QuadraticProbingMapEntry[] {
            new QuadraticProbingMapEntry<>(66, "value"),
            new QuadraticProbingMapEntry<>(1, "value"),
            new QuadraticProbingMapEntry<>(14, "value"),
            null,
            new QuadraticProbingMapEntry<>(53, "value"),
            new QuadraticProbingMapEntry<>(27, "value"),
            null,
            null,
            null,
            null,
            new QuadraticProbingMapEntry<>(40, "value"),
            removed,
            null
        }, map.getTable());
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_remove_key_and_value_exist_but_marked_removed() {
        map.put(1, "value");
        map.put(14, "value");
        map.put(27, "value");
        map.put(40, "value");
        map.put(53, "value");
        map.put(66, "value");
        map.put(79, "value");

        map.remove(27);
        QuadraticProbingMapEntry<Integer, String> removed = new QuadraticProbingMapEntry<>(27, "value");
        removed.setRemoved(true);
        assertArrayEquals(new QuadraticProbingMapEntry[] {
            new QuadraticProbingMapEntry<>(66, "value"),
            new QuadraticProbingMapEntry<>(1, "value"),
            new QuadraticProbingMapEntry<>(14, "value"),
            null,
            new QuadraticProbingMapEntry<>(53, "value"),
            removed,
            null,
            null,
            null,
            null,
            new QuadraticProbingMapEntry<>(40, "value"),
            new QuadraticProbingMapEntry<>(79, "value"),
            null
        }, map.getTable());

        map.remove(27);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_get_null_key() {
        map.get(null);
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_get_empty_map() {
        map.get(1);
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_get_key_not_in_map() {
        for (int i = 0; i < 8; i++) {
            map.put(i, "value");
        }

        map.get(8);
    }

    @Test(timeout = TIMEOUT)
    public void test_get_correct_value() {
        map.put(1, "value");
        assertEquals("value", map.get(1));
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_get_DEL_key() {
        map.put(1, "value");
        map.remove(1);
        map.get(1);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_containsKey_null_key() {
        map.containsKey(null);
    }

    @Test(timeout = TIMEOUT)
    public void test_containsKey_empty_map() {
        assertFalse(map.containsKey(1));
    }

    @Test(timeout = TIMEOUT)
    public void test_containsKey_no_collisions() {
        for (int i = 0; i < 8; i++) {
            map.put(i, "value");
        }

        for (int i = 0; i < 8; i++) {
            assertTrue(map.containsKey(i));
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_containsKey_collisions_in_map_but_no_key() {
        map.put(1, "value");
        map.put(14, "value");
        map.put(27, "value");
        map.put(40, "value");
        map.put(53, "value");
        map.put(66, "value");

        assertFalse(map.containsKey(79));
    }

    @Test(timeout = TIMEOUT)
    public void test_containsKey_collisions_in_map_and_key_in_map() {
        map.put(1, "value");
        map.put(14, "value");
        map.put(27, "value");
        map.put(40, "value");
        map.put(53, "value");
        map.put(66, "value");
        map.put(79, "value");

        assertTrue(map.containsKey(1));
        assertTrue(map.containsKey(14));
        assertTrue(map.containsKey(27));
        assertTrue(map.containsKey(40));
        assertTrue(map.containsKey(53));
        assertTrue(map.containsKey(66));
        assertTrue(map.containsKey(79));
    }

    @Test(timeout = TIMEOUT)
    public void test_containsKey_DEL_key() {
        map.put(1, "value");
        map.put(14, "value");
        map.put(27, "value");
        map.put(40, "value");
        map.put(53, "value");
        map.put(66, "value");
        map.put(79, "value");

        map.remove(40);
        assertFalse(map.containsKey(40));
    }

    @Test(timeout = TIMEOUT)
    public void test_keySet() {
        map.put(1, "value");
        map.put(14, "value");
        map.put(27, "value");
        map.put(40, "value");
        map.put(53, "value");
        map.put(66, "value");
        map.put(79, "value");

        assertEquals(map.size(), map.keySet().size());

        Set<Integer> keySet = new HashSet<>();
        keySet.add(1);
        keySet.add(14);
        keySet.add(27);
        keySet.add(40);
        keySet.add(53);
        keySet.add(66);
        keySet.add(79);

        assertEquals(keySet, map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void test_values() {
        for (int i = 0; i < 8; i++) {
            map.put(i, "value" + i);
        }

        assertEquals(map.size(), map.values().size());
        List<String> values = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            values.add("value" + i);
        }
        assertEquals(values, map.values());
    }

    @Test(timeout = TIMEOUT)
    public void test_values_matches_get() {
        for (int i = 0; i < 8; i++) {
            map.put(i, "value" + i);
        }

        for (int i = 0; i < 8; i++) {
            assertEquals("value" + i, map.values().get(i));
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_resizeBackingTable_length_less_than_size() {
        map.put(1, "value");
        map.put(2, "value");
        map.put(3, "value");
        map.put(4, "value");

        map.resizeBackingTable(1);
    }

    @Test(timeout = TIMEOUT)
    public void test_resizeBackingTable_assigns_new_memory() {
        QuadraticProbingMapEntry<Integer, String>[] oldTable = map.getTable();
        map.resizeBackingTable(20);
        assertNotEquals(oldTable, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void test_resizeBackingTable_correctly_copies() {
        for (int i = 0; i < 8; i++) {
            map.put(i, "value");
        }

        map.resizeBackingTable(10);
        assertArrayEquals(new QuadraticProbingMapEntry[] {
            new QuadraticProbingMapEntry<>(0, "value"),
            new QuadraticProbingMapEntry<>(1, "value"),
            new QuadraticProbingMapEntry<>(2, "value"),
            new QuadraticProbingMapEntry<>(3, "value"),
            new QuadraticProbingMapEntry<>(4, "value"),
            new QuadraticProbingMapEntry<>(5, "value"),
            new QuadraticProbingMapEntry<>(6, "value"),
            new QuadraticProbingMapEntry<>(7, "value"),
            null,
            null
        }, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void test_resizeBackingTable_correctly_copies_with_collisions() {
        map.put(1, "value");
        map.put(14, "value");
        map.put(27, "value");
        map.put(40, "value");
        map.put(53, "value");
        map.put(66, "value");
        map.put(79, "value");

        map.resizeBackingTable(10);
        assertArrayEquals(new QuadraticProbingMapEntry[] {
            new QuadraticProbingMapEntry<>(40, "value"),
            new QuadraticProbingMapEntry<>(1, "value"),
            null,
            new QuadraticProbingMapEntry<>(53, "value"),
            new QuadraticProbingMapEntry<>(14, "value"),
            null,
            new QuadraticProbingMapEntry<>(66, "value"),
            new QuadraticProbingMapEntry<>(27, "value"),
            null,
            new QuadraticProbingMapEntry<>(79, "value")
        }, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void test_resizeBackingTable_removes_DEL() {
        map.put(1, "value");
        map.put(14, "value");
        map.put(27, "value");
        map.put(40, "value");
        map.put(53, "value");
        map.put(66, "value");
        map.put(79, "value");

        map.remove(27);
        map.remove(40);
        map.remove(79);

        map.resizeBackingTable(10);
        assertArrayEquals(new QuadraticProbingMapEntry[] {
            null,
            new QuadraticProbingMapEntry<>(1, "value"),
            null,
            new QuadraticProbingMapEntry<>(53, "value"),
            new QuadraticProbingMapEntry<>(14, "value"),
            null,
            new QuadraticProbingMapEntry<>(66, "value"),
            null,
            null,
            null
        }, map.getTable());
    }

}