import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * CS 1332 HW 4
 * ArcWand tests for Binary Search Tree
 *
 * Collaborators: Raymond
 *
 * @author Robert Zhu
 * @version 1.0
 */
public class ArcWandBSTTests {
    private static final boolean UNLEASH_FULL = true;

    @BeforeClass
    @AfterClass
    public static void note() {
        if (!UNLEASH_FULL) {
            System.out.println();
            System.out.println("Once you're feeling confident about your code, set UNLEASH_FULL to true!");
            System.out.println();
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_note() {
        assertTrue("Once you're feeling confident about your code, set UNLEASH_FULL to true!", UNLEASH_FULL);
    }


    private static final int TIMEOUT = 200;
    private BST<TestClass> tree;
    private BST<TestClass> degenLeft;
    private BST<TestClass> degenRight;
    private BST<TestClass> pbBST;
    private BST<TestClass> fullBST;

    /**
     * Class to test the features of our binary search tree
     */
    private class TestClass implements Comparable<TestClass> {
        public final int key;
        private int value; 

        public TestClass(int key, int value) {
            this.key = key;
            this.value = value;
        }
        public TestClass(int key) { this(key, 0); }

        @Override
        public int compareTo(TestClass that) {
            return this.key - that.key;
        }

        @Override
        public boolean equals(Object that) {
            if (that == null || !(that instanceof TestClass)) return false;
            return this.key == ((TestClass)that).key;
        }

        @Override
        public String toString() {
            return key + "";
        }
    }

    /**
     * Helper method to print out a consistent representation of a binary search tree
     *
     * @param tree binary search tree to print
     */
    private String toString(BST<TestClass> tree) {
        StringBuilder sb = new StringBuilder();

        Queue<BSTNode<TestClass>> currLevel = new LinkedList<>();
        Queue<BSTNode<TestClass>> nextLevel = new LinkedList<>();

        currLevel.add(tree.getRoot());
        boolean nonEmpty = true;
        while (nonEmpty) {
            nonEmpty = false;
            while (!currLevel.isEmpty()) {
                // Get the next node
                BSTNode<TestClass> curr = currLevel.remove();

                // Enqueue the children and append to the string
                if (curr == null) {
                    nextLevel.add(null);
                    nextLevel.add(null);
                    sb.append("_");
                } else {
                    nextLevel.add(curr.getLeft());
                    nextLevel.add(curr.getRight());
                    sb.append(curr.getData());
                    nonEmpty = true;
                }
                sb.append(" ");
            }

            // Swap the queues and move to the next row
            Queue<BSTNode<TestClass>> tmp = currLevel;
            currLevel = nextLevel;
            nextLevel = tmp;

            sb.append('\n');
        }

        return sb.toString();
    }

    /**
     * Helper method to assert the equality of a tree
     * The array passed in should be in level order or pre-order
     *
     * @param expected an array that represents a unique tree
     * @param actual the actual tree
     */
    private void definitelyEquals(TestClass[] expected, BST<TestClass> actual) {
        assertEquals(toString(new BST<TestClass>(Arrays.asList(expected))),
                toString(actual));
        assertEquals(expected.length, actual.size());
    }

    /**
     * Helper method to lazy load fullBST tree.
     */
    public void loadFullBST() {
        fullBST = new BST<>(Arrays.asList(FULL_BST_ARRAY));
    }

    @Before
    public void setup() {
        tree = new BST<>();
        degenLeft = new BST<>(Arrays.asList(DEGEN_LEFT_ARRAY));
        degenRight = new BST<>(Arrays.asList(DEGEN_RIGHT_ARRAY));
        pbBST = new BST<>(Arrays.asList(PBBST_ARRAY));
        // fullBST = new BST<>(Arrays.asList(FULL_BST_ARRAY));
    }

	@Test(timeout = TIMEOUT)
	public void test_Empty_Constructor() {
        assertEquals(0, tree.size());
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void test_add_IllegalArgumentException() {
        pbBST.add(null);
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void test_remove_IllegalArgumentException() {
        pbBST.remove(null);
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void test_get_IllegalArgumentException() {
        pbBST.get(null);
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void test_contains_IllegalArgumentException() {
        pbBST.contains(null);
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void test_findPathBetween_IllegalArgumentException_First() {
        pbBST.findPathBetween(null, new TestClass(2));
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void test_findPathBetween_IllegalArgumentException_Second() {
        pbBST.findPathBetween(new TestClass(1), null);
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void test_findPathBetween_IllegalArgumentException_Both() {
        pbBST.findPathBetween(null, null);
	}

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_remove_NoSuchElementException_PBBST() {
        pbBST.remove(new TestClass(10000));
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_remove_NoSuchElementException_Full_BST() {
        if (!UNLEASH_FULL) { throw new java.util.NoSuchElementException("Ignoring test"); }
        loadFullBST();
        for (int i = 1; i < NOT_CONTAINED_IN_FULL_BST.length; i++) {
            try {
                fullBST.remove(NOT_CONTAINED_IN_FULL_BST[i]);
                assertTrue("Element was found, when it should not have been in the tree.", false);
            } catch (java.util.NoSuchElementException e) {
            }
        }
        fullBST.remove(NOT_CONTAINED_IN_FULL_BST[0]);
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_get_NoSuchElementException_PBBST() {
        pbBST.get(new TestClass(10000));
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_get_NoSuchElementException_Full_BST() {
        if (!UNLEASH_FULL) { throw new java.util.NoSuchElementException("Ignoring test"); }
        loadFullBST();
        for (int i = 1; i < NOT_CONTAINED_IN_FULL_BST.length; i++) {
            try {
                fullBST.get(NOT_CONTAINED_IN_FULL_BST[i]);
                assertTrue("Element was found, when it should not have been in the tree.", false);
            } catch (java.util.NoSuchElementException e) {
            }
        }
        fullBST.get(NOT_CONTAINED_IN_FULL_BST[0]);
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_findPathBetween_NoSuchElementException_Full_BST_First() {
        if (!UNLEASH_FULL) { throw new java.util.NoSuchElementException("Ignoring test"); }
        loadFullBST();
        // TestClass(1332) should not be in fullBST
        // TestClass(6969) should be in fullBST
        fullBST.findPathBetween(new TestClass(1332), new TestClass(6969));
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_findPathBetween_NoSuchElementException_Full_BST_Second() {
        if (!UNLEASH_FULL) { throw new java.util.NoSuchElementException("Ignoring test"); }
        loadFullBST();
        // TestClass(1024) should be in fullBST
        // TestClass(420) should not be in fullBST
        fullBST.findPathBetween(new TestClass(1024), new TestClass(420));
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_findPathBetween_NoSuchElementException_Full_BST_Both() {
        if (!UNLEASH_FULL) { throw new java.util.NoSuchElementException("Ignoring test"); }
        loadFullBST();
        // TestClass(1984) and TestClass(8888) should both not be in fullBST
        fullBST.findPathBetween(new TestClass(1984), new TestClass(8888));
    }

	@Test(timeout = TIMEOUT)
	public void test_Constructor_Degen_Left() {
        definitelyEquals(DEGEN_LEFT_ARRAY, degenLeft);
	}

	@Test(timeout = TIMEOUT)
	public void test_Constructor_Degen_Right() {
        definitelyEquals(DEGEN_RIGHT_ARRAY, degenRight);
	}

	@Test(timeout = TIMEOUT)
	public void test_Constructor_PBBST() {
        definitelyEquals(PBBST_ARRAY, pbBST);
	}

	@Test(timeout = TIMEOUT)
	public void test_Constructor_Full_BST() {
        if (!UNLEASH_FULL) { return; }
        loadFullBST();
        definitelyEquals(FULL_BST_ARRAY, fullBST);
	}

	@Test(timeout = TIMEOUT)
	public void test_add_to_Empty_Tree() {
        tree.add(new TestClass(1));

        assertEquals(new TestClass(1), tree.getRoot().getData());
        assertNull(tree.getRoot().getLeft());
        assertNull(tree.getRoot().getRight());
        assertEquals(1, tree.size());
	}

	@Test(timeout = TIMEOUT)
	public void test_add_updates_Size() {
        for (int i = 1; i < 500; i++) {
            tree.add(new TestClass(i));
            assertEquals(i, tree.size());
        }
	}

	@Test(timeout = TIMEOUT)
	public void test_add_Duplicate_does_Not_update_Size_One_Node() {
        tree.add(new TestClass(22));

        for (int i = 0; i < 500; i++) {
            tree.add(new TestClass(22));
            assertEquals(1, tree.size());
        }
	}

    @Test(timeout = TIMEOUT)
    public void test_add_Duplicate_Not_Added_One_Node() {
        tree.add(new TestClass(21));

        for (int i = 0; i < 500; i++) {
            tree.add(new TestClass(21));
            definitelyEquals(new TestClass[] { new TestClass(21) }, tree);
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_add_Duplicate_Not_Added_to_PBBST() {
        for (int i = 0; i < 500; i++) {
            pbBST.add(new TestClass(i%15 + 1));
            definitelyEquals(PBBST_ARRAY, pbBST);
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_add_Duplicate_Not_Added_to_Full_BST() {
        if (!UNLEASH_FULL) { return; }
        loadFullBST();
        for (int i = 0; i < CONTAINED_IN_FULL_BST.length*5; i++) {
            fullBST.add(CONTAINED_IN_FULL_BST[i % CONTAINED_IN_FULL_BST.length]);
        }
        definitelyEquals(FULL_BST_ARRAY, fullBST);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_to_Degen_Left() {
        for (int i = 2; i <= 20; i += 2) {
            degenLeft.add(new TestClass(i));
        }

        // Compare with the correct level order traversal
        definitelyEquals(new TestClass[] {
            new TestClass(19), new TestClass(17), new TestClass(20), new TestClass(15),
            new TestClass(18), new TestClass(13), new TestClass(16), new TestClass(11),
            new TestClass(14), new TestClass(9),  new TestClass(12), new TestClass(7),
            new TestClass(10), new TestClass(5),  new TestClass(8),  new TestClass(3),
            new TestClass(6),  new TestClass(1),  new TestClass(4),  new TestClass(2)
        }, degenLeft);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_to_Degen_Right() {
        for (int i = 1; i < 20; i += 2) {
            degenRight.add(new TestClass(i));
        }

        // Compare with the correct level order traversal
        definitelyEquals(new TestClass[] {
            new TestClass(2),  new TestClass(1),  new TestClass(4),  new TestClass(3),
            new TestClass(6),  new TestClass(5),  new TestClass(8),  new TestClass(7),
            new TestClass(10), new TestClass(9),  new TestClass(12), new TestClass(11),
            new TestClass(14), new TestClass(13), new TestClass(16), new TestClass(15),
            new TestClass(18), new TestClass(17), new TestClass(20), new TestClass(19),
        }, degenRight);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_to_PBBST() {
        pbBST.add(new TestClass(16));
        pbBST.add(new TestClass(0));

        // Compare with the correct level order traversal
        definitelyEquals(new TestClass[] {
            new TestClass(8),  new TestClass(4),  new TestClass(12), new TestClass(2),
            new TestClass(6),  new TestClass(10), new TestClass(14), new TestClass(1),
            new TestClass(3),  new TestClass(5),  new TestClass(7),  new TestClass(9),
            new TestClass(11), new TestClass(13), new TestClass(15), new TestClass(0),
            new TestClass(16) }, pbBST);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_to_Full_BST() {
        if (!UNLEASH_FULL) { return; }
        loadFullBST();
        for (TestClass n : NOT_CONTAINED_IN_FULL_BST) {
            fullBST.add(n);
        }

        definitelyEquals(new TestClass[] { new TestClass(2403), new TestClass(1283), new TestClass(633), new TestClass(362), new TestClass(190), new TestClass(104), new TestClass(45), new TestClass(17), new TestClass(8), new TestClass(16), new TestClass(20), new TestClass(59), new TestClass(58), new TestClass(85), new TestClass(169), new TestClass(116), new TestClass(106), new TestClass(166), new TestClass(176), new TestClass(175), new TestClass(183), new TestClass(248), new TestClass(228), new TestClass(202), new TestClass(195), new TestClass(206), new TestClass(212), new TestClass(235), new TestClass(231), new TestClass(240), new TestClass(310), new TestClass(298), new TestClass(271), new TestClass(252), new TestClass(302), new TestClass(337), new TestClass(336), new TestClass(348), new TestClass(339), new TestClass(501), new TestClass(435), new TestClass(400), new TestClass(376), new TestClass(365), new TestClass(383), new TestClass(427), new TestClass(415), new TestClass(430), new TestClass(461), new TestClass(452), new TestClass(444), new TestClass(460), new TestClass(487), new TestClass(477), new TestClass(493), new TestClass(573), new TestClass(538), new TestClass(523), new TestClass(504), new TestClass(512), new TestClass(534), new TestClass(554), new TestClass(547), new TestClass(556), new TestClass(609), new TestClass(599), new TestClass(592), new TestClass(600), new TestClass(603), new TestClass(627), new TestClass(615), new TestClass(623), new TestClass(629), new TestClass(996), new TestClass(793), new TestClass(725), new TestClass(702), new TestClass(682), new TestClass(652), new TestClass(678), new TestClass(688), new TestClass(713), new TestClass(711), new TestClass(720), new TestClass(722), new TestClass(754), new TestClass(733), new TestClass(726), new TestClass(740), new TestClass(746), new TestClass(779), new TestClass(755), new TestClass(783), new TestClass(925), new TestClass(867), new TestClass(835), new TestClass(797), new TestClass(843), new TestClass(918), new TestClass(880), new TestClass(907), new TestClass(884), new TestClass(915), new TestClass(919), new TestClass(950), new TestClass(930), new TestClass(929), new TestClass(931), new TestClass(985), new TestClass(975), new TestClass(986), new TestClass(1112), new TestClass(1044), new TestClass(1024), new TestClass(1013), new TestClass(1012), new TestClass(1022), new TestClass(1033), new TestClass(1028), new TestClass(1040), new TestClass(1091), new TestClass(1078), new TestClass(1065), new TestClass(1085), new TestClass(1106), new TestClass(1104), new TestClass(1108), new TestClass(1183), new TestClass(1137), new TestClass(1123), new TestClass(1122), new TestClass(1118), new TestClass(1133), new TestClass(1124), new TestClass(1172), new TestClass(1144), new TestClass(1179), new TestClass(1239), new TestClass(1189), new TestClass(1188), new TestClass(1209), new TestClass(1213), new TestClass(1258), new TestClass(1251), new TestClass(1249), new TestClass(1270), new TestClass(1959), new TestClass(1648), new TestClass(1498), new TestClass(1389), new TestClass(1337), new TestClass(1317), new TestClass(1309), new TestClass(1308), new TestClass(1326), new TestClass(1329), new TestClass(1362), new TestClass(1338), new TestClass(1386), new TestClass(1387), new TestClass(1433), new TestClass(1416), new TestClass(1412), new TestClass(1421), new TestClass(1482), new TestClass(1475), new TestClass(1497), new TestClass(1601), new TestClass(1567), new TestClass(1509), new TestClass(1508), new TestClass(1559), new TestClass(1577), new TestClass(1575), new TestClass(1597), new TestClass(1630), new TestClass(1621), new TestClass(1602), new TestClass(1624), new TestClass(1638), new TestClass(1633), new TestClass(1641), new TestClass(1770), new TestClass(1703), new TestClass(1691), new TestClass(1682), new TestClass(1678), new TestClass(1687), new TestClass(1701), new TestClass(1697), new TestClass(1702), new TestClass(1726), new TestClass(1716), new TestClass(1705), new TestClass(1722), new TestClass(1747), new TestClass(1746), new TestClass(1757), new TestClass(1858), new TestClass(1816), new TestClass(1784), new TestClass(1777), new TestClass(1810), new TestClass(1828), new TestClass(1824), new TestClass(1857), new TestClass(1897), new TestClass(1878), new TestClass(1871), new TestClass(1881), new TestClass(1930), new TestClass(1903), new TestClass(1956), new TestClass(1936), new TestClass(2163), new TestClass(2052), new TestClass(1993), new TestClass(1975), new TestClass(1969), new TestClass(1962), new TestClass(1960), new TestClass(1971), new TestClass(1981), new TestClass(1979), new TestClass(1983), new TestClass(2024), new TestClass(1997), new TestClass(1995), new TestClass(2011), new TestClass(2038), new TestClass(2032), new TestClass(2047), new TestClass(2043), new TestClass(2098), new TestClass(2060), new TestClass(2056), new TestClass(2054), new TestClass(2057), new TestClass(2091), new TestClass(2087), new TestClass(2093), new TestClass(2128), new TestClass(2107), new TestClass(2104), new TestClass(2115), new TestClass(2150), new TestClass(2131), new TestClass(2152), new TestClass(2151), new TestClass(2273), new TestClass(2211), new TestClass(2175), new TestClass(2170), new TestClass(2168), new TestClass(2171), new TestClass(2203), new TestClass(2200), new TestClass(2178), new TestClass(2206), new TestClass(2250), new TestClass(2223), new TestClass(2214), new TestClass(2219), new TestClass(2224), new TestClass(2248), new TestClass(2266), new TestClass(2259), new TestClass(2267), new TestClass(2352), new TestClass(2307), new TestClass(2298), new TestClass(2283), new TestClass(2305), new TestClass(2340), new TestClass(2324), new TestClass(2344), new TestClass(2370), new TestClass(2361), new TestClass(2354), new TestClass(2357), new TestClass(2366), new TestClass(2389), new TestClass(2384), new TestClass(2378), new TestClass(2400), new TestClass(2391), new TestClass(4979), new TestClass(3651), new TestClass(2960), new TestClass(2699), new TestClass(2566), new TestClass(2502), new TestClass(2460), new TestClass(2452), new TestClass(2406), new TestClass(2441), new TestClass(2454), new TestClass(2478), new TestClass(2468), new TestClass(2491), new TestClass(2486), new TestClass(2548), new TestClass(2526), new TestClass(2523), new TestClass(2528), new TestClass(2561), new TestClass(2559), new TestClass(2563), new TestClass(2645), new TestClass(2607), new TestClass(2599), new TestClass(2585), new TestClass(2568), new TestClass(2602), new TestClass(2641), new TestClass(2612), new TestClass(2644), new TestClass(2690), new TestClass(2673), new TestClass(2671), new TestClass(2675), new TestClass(2682), new TestClass(2694), new TestClass(2692), new TestClass(2698), new TestClass(2801), new TestClass(2744), new TestClass(2721), new TestClass(2713), new TestClass(2709), new TestClass(2717), new TestClass(2737), new TestClass(2732), new TestClass(2739), new TestClass(2771), new TestClass(2763), new TestClass(2757), new TestClass(2765), new TestClass(2778), new TestClass(2777), new TestClass(2794), new TestClass(2850), new TestClass(2841), new TestClass(2837), new TestClass(2831), new TestClass(2840), new TestClass(2848), new TestClass(2847), new TestClass(2849), new TestClass(2884), new TestClass(2862), new TestClass(2853), new TestClass(2877), new TestClass(2874), new TestClass(2939), new TestClass(2890), new TestClass(2888), new TestClass(2953), new TestClass(3367), new TestClass(3142), new TestClass(3044), new TestClass(2979), new TestClass(2970), new TestClass(2969), new TestClass(2974), new TestClass(3002), new TestClass(3001), new TestClass(3040), new TestClass(3094), new TestClass(3061), new TestClass(3050), new TestClass(3067), new TestClass(3116), new TestClass(3115), new TestClass(3138), new TestClass(3269), new TestClass(3208), new TestClass(3163), new TestClass(3151), new TestClass(3178), new TestClass(3233), new TestClass(3220), new TestClass(3259), new TestClass(3242), new TestClass(3341), new TestClass(3303), new TestClass(3292), new TestClass(3304), new TestClass(3364), new TestClass(3362), new TestClass(3365), new TestClass(3531), new TestClass(3429), new TestClass(3391), new TestClass(3387), new TestClass(3372), new TestClass(3389), new TestClass(3410), new TestClass(3400), new TestClass(3414), new TestClass(3486), new TestClass(3455), new TestClass(3444), new TestClass(3466), new TestClass(3467), new TestClass(3468), new TestClass(3489), new TestClass(3487), new TestClass(3508), new TestClass(3583), new TestClass(3576), new TestClass(3556), new TestClass(3554), new TestClass(3565), new TestClass(3579), new TestClass(3578), new TestClass(3582), new TestClass(3630), new TestClass(3601), new TestClass(3597), new TestClass(3591), new TestClass(3615), new TestClass(3639), new TestClass(3636), new TestClass(3643), new TestClass(4415), new TestClass(3991), new TestClass(3815), new TestClass(3735), new TestClass(3676), new TestClass(3668), new TestClass(3666), new TestClass(3671), new TestClass(3702), new TestClass(3681), new TestClass(3732), new TestClass(3706), new TestClass(3728), new TestClass(3801), new TestClass(3766), new TestClass(3765), new TestClass(3777), new TestClass(3774), new TestClass(3810), new TestClass(3804), new TestClass(3812), new TestClass(3912), new TestClass(3843), new TestClass(3837), new TestClass(3833), new TestClass(3839), new TestClass(3892), new TestClass(3861), new TestClass(3893), new TestClass(3936), new TestClass(3923), new TestClass(3918), new TestClass(3916), new TestClass(3927), new TestClass(3988), new TestClass(3968), new TestClass(3990), new TestClass(4248), new TestClass(4089), new TestClass(4062), new TestClass(4041), new TestClass(4006), new TestClass(4060), new TestClass(4074), new TestClass(4069), new TestClass(4083), new TestClass(4192), new TestClass(4115), new TestClass(4114), new TestClass(4104), new TestClass(4095), new TestClass(4142), new TestClass(4179), new TestClass(4228), new TestClass(4227), new TestClass(4215), new TestClass(4237), new TestClass(4317), new TestClass(4281), new TestClass(4265), new TestClass(4259), new TestClass(4268), new TestClass(4295), new TestClass(4286), new TestClass(4309), new TestClass(4391), new TestClass(4387), new TestClass(4333), new TestClass(4370), new TestClass(4388), new TestClass(4404), new TestClass(4403), new TestClass(4409), new TestClass(4719), new TestClass(4564), new TestClass(4475), new TestClass(4438), new TestClass(4420), new TestClass(4418), new TestClass(4417), new TestClass(4426), new TestClass(4456), new TestClass(4447), new TestClass(4457), new TestClass(4518), new TestClass(4493), new TestClass(4478), new TestClass(4515), new TestClass(4494), new TestClass(4511), new TestClass(4539), new TestClass(4521), new TestClass(4551), new TestClass(4627), new TestClass(4598), new TestClass(4593), new TestClass(4566), new TestClass(4576), new TestClass(4595), new TestClass(4607), new TestClass(4600), new TestClass(4624), new TestClass(4674), new TestClass(4665), new TestClass(4654), new TestClass(4666), new TestClass(4687), new TestClass(4676), new TestClass(4698), new TestClass(4804), new TestClass(4763), new TestClass(4736), new TestClass(4730), new TestClass(4726), new TestClass(4732), new TestClass(4743), new TestClass(4737), new TestClass(4754), new TestClass(4787), new TestClass(4784), new TestClass(4774), new TestClass(4785), new TestClass(4792), new TestClass(4789), new TestClass(4801), new TestClass(4865), new TestClass(4838), new TestClass(4829), new TestClass(4815), new TestClass(4818), new TestClass(4833), new TestClass(4844), new TestClass(4839), new TestClass(4860), new TestClass(4950), new TestClass(4912), new TestClass(4904), new TestClass(4868), new TestClass(4938), new TestClass(4923), new TestClass(4917), new TestClass(4962), new TestClass(4955), new TestClass(4951), new TestClass(4964), new TestClass(6210), new TestClass(5657), new TestClass(5325), new TestClass(5206), new TestClass(5081), new TestClass(5018), new TestClass(5004), new TestClass(4994), new TestClass(4984), new TestClass(5016), new TestClass(5038), new TestClass(5036), new TestClass(5059), new TestClass(5188), new TestClass(5116), new TestClass(5086), new TestClass(5160), new TestClass(5198), new TestClass(5194), new TestClass(5200), new TestClass(5201), new TestClass(5275), new TestClass(5223), new TestClass(5215), new TestClass(5211), new TestClass(5220), new TestClass(5218), new TestClass(5257), new TestClass(5242), new TestClass(5249), new TestClass(5263), new TestClass(5305), new TestClass(5289), new TestClass(5277), new TestClass(5284), new TestClass(5294), new TestClass(5317), new TestClass(5315), new TestClass(5318), new TestClass(5465), new TestClass(5396), new TestClass(5379), new TestClass(5354), new TestClass(5342), new TestClass(5340), new TestClass(5367), new TestClass(5392), new TestClass(5383), new TestClass(5393), new TestClass(5433), new TestClass(5418), new TestClass(5413), new TestClass(5419), new TestClass(5447), new TestClass(5446), new TestClass(5462), new TestClass(5569), new TestClass(5514), new TestClass(5490), new TestClass(5481), new TestClass(5493), new TestClass(5531), new TestClass(5527), new TestClass(5541), new TestClass(5587), new TestClass(5577), new TestClass(5570), new TestClass(5583), new TestClass(5627), new TestClass(5589), new TestClass(5638), new TestClass(5941), new TestClass(5820), new TestClass(5723), new TestClass(5676), new TestClass(5670), new TestClass(5666), new TestClass(5671), new TestClass(5703), new TestClass(5682), new TestClass(5707), new TestClass(5767), new TestClass(5742), new TestClass(5727), new TestClass(5743), new TestClass(5783), new TestClass(5781), new TestClass(5801), new TestClass(5897), new TestClass(5848), new TestClass(5841), new TestClass(5827), new TestClass(5846), new TestClass(5892), new TestClass(5887), new TestClass(5853), new TestClass(5896), new TestClass(5923), new TestClass(5903), new TestClass(5902), new TestClass(5899), new TestClass(5916), new TestClass(5935), new TestClass(5928), new TestClass(5939), new TestClass(6074), new TestClass(6004), new TestClass(5984), new TestClass(5954), new TestClass(5953), new TestClass(5964), new TestClass(5995), new TestClass(5989), new TestClass(5997), new TestClass(6047), new TestClass(6010), new TestClass(6008), new TestClass(6041), new TestClass(6063), new TestClass(6060), new TestClass(6065), new TestClass(6068), new TestClass(6140), new TestClass(6096), new TestClass(6088), new TestClass(6083), new TestClass(6090), new TestClass(6131), new TestClass(6110), new TestClass(6132), new TestClass(6179), new TestClass(6164), new TestClass(6144), new TestClass(6151), new TestClass(6168), new TestClass(6190), new TestClass(6184), new TestClass(6205), new TestClass(7558), new TestClass(6887), new TestClass(6544), new TestClass(6383), new TestClass(6271), new TestClass(6241), new TestClass(6224), new TestClass(6212), new TestClass(6215), new TestClass(6237), new TestClass(6250), new TestClass(6246), new TestClass(6258), new TestClass(6296), new TestClass(6279), new TestClass(6277), new TestClass(6287), new TestClass(6317), new TestClass(6310), new TestClass(6364), new TestClass(6479), new TestClass(6418), new TestClass(6405), new TestClass(6398), new TestClass(6414), new TestClass(6443), new TestClass(6439), new TestClass(6450), new TestClass(6454), new TestClass(6532), new TestClass(6508), new TestClass(6504), new TestClass(6512), new TestClass(6538), new TestClass(6537), new TestClass(6543), new TestClass(6701), new TestClass(6618), new TestClass(6576), new TestClass(6561), new TestClass(6545), new TestClass(6564), new TestClass(6595), new TestClass(6578), new TestClass(6612), new TestClass(6607), new TestClass(6675), new TestClass(6658), new TestClass(6640), new TestClass(6667), new TestClass(6692), new TestClass(6676), new TestClass(6696), new TestClass(6783), new TestClass(6759), new TestClass(6738), new TestClass(6716), new TestClass(6756), new TestClass(6774), new TestClass(6773), new TestClass(6781), new TestClass(6833), new TestClass(6814), new TestClass(6793), new TestClass(6785), new TestClass(6831), new TestClass(6826), new TestClass(6847), new TestClass(6843), new TestClass(6834), new TestClass(6861), new TestClass(7207), new TestClass(7059), new TestClass(6969), new TestClass(6940), new TestClass(6902), new TestClass(6896), new TestClass(6931), new TestClass(6904), new TestClass(6917), new TestClass(6911), new TestClass(6951), new TestClass(6945), new TestClass(6961), new TestClass(7031), new TestClass(6991), new TestClass(6982), new TestClass(7007), new TestClass(7045), new TestClass(7036), new TestClass(7046), new TestClass(7157), new TestClass(7136), new TestClass(7110), new TestClass(7078), new TestClass(7132), new TestClass(7142), new TestClass(7140), new TestClass(7150), new TestClass(7187), new TestClass(7170), new TestClass(7160), new TestClass(7163), new TestClass(7180), new TestClass(7192), new TestClass(7190), new TestClass(7197), new TestClass(7349), new TestClass(7265), new TestClass(7243), new TestClass(7232), new TestClass(7212), new TestClass(7236), new TestClass(7254), new TestClass(7246), new TestClass(7258), new TestClass(7320), new TestClass(7292), new TestClass(7283), new TestClass(7301), new TestClass(7338), new TestClass(7327), new TestClass(7346), new TestClass(7464), new TestClass(7429), new TestClass(7371), new TestClass(7357), new TestClass(7366), new TestClass(7373), new TestClass(7440), new TestClass(7438), new TestClass(7453), new TestClass(7486), new TestClass(7478), new TestClass(7474), new TestClass(7481), new TestClass(7504), new TestClass(7496), new TestClass(7554), new TestClass(7543), new TestClass(8328), new TestClass(7954), new TestClass(7795), new TestClass(7671), new TestClass(7612), new TestClass(7579), new TestClass(7575), new TestClass(7609), new TestClass(7642), new TestClass(7633), new TestClass(7652), new TestClass(7711), new TestClass(7686), new TestClass(7672), new TestClass(7699), new TestClass(7752), new TestClass(7747), new TestClass(7780), new TestClass(7884), new TestClass(7824), new TestClass(7803), new TestClass(7801), new TestClass(7808), new TestClass(7849), new TestClass(7844), new TestClass(7877), new TestClass(7910), new TestClass(7897), new TestClass(7891), new TestClass(7886), new TestClass(7898), new TestClass(7925), new TestClass(7924), new TestClass(7953), new TestClass(7940), new TestClass(8176), new TestClass(8023), new TestClass(7986), new TestClass(7966), new TestClass(7958), new TestClass(7980), new TestClass(8013), new TestClass(8005), new TestClass(8017), new TestClass(8126), new TestClass(8068), new TestClass(8045), new TestClass(8116), new TestClass(8070), new TestClass(8166), new TestClass(8153), new TestClass(8171), new TestClass(8257), new TestClass(8211), new TestClass(8204), new TestClass(8191), new TestClass(8205), new TestClass(8226), new TestClass(8217), new TestClass(8244), new TestClass(8297), new TestClass(8277), new TestClass(8276), new TestClass(8281), new TestClass(8315), new TestClass(8314), new TestClass(8310), new TestClass(8325), new TestClass(9020), new TestClass(8735), new TestClass(8527), new TestClass(8412), new TestClass(8354), new TestClass(8336), new TestClass(8335), new TestClass(8333), new TestClass(8343), new TestClass(8366), new TestClass(8363), new TestClass(8371), new TestClass(8472), new TestClass(8439), new TestClass(8436), new TestClass(8450), new TestClass(8469), new TestClass(8496), new TestClass(8482), new TestClass(8491), new TestClass(8520), new TestClass(8625), new TestClass(8591), new TestClass(8548), new TestClass(8535), new TestClass(8580), new TestClass(8606), new TestClass(8594), new TestClass(8623), new TestClass(8666), new TestClass(8639), new TestClass(8631), new TestClass(8644), new TestClass(8645), new TestClass(8691), new TestClass(8667), new TestClass(8725), new TestClass(8881), new TestClass(8805), new TestClass(8766), new TestClass(8755), new TestClass(8738), new TestClass(8761), new TestClass(8775), new TestClass(8774), new TestClass(8768), new TestClass(8783), new TestClass(8859), new TestClass(8839), new TestClass(8835), new TestClass(8857), new TestClass(8874), new TestClass(8871), new TestClass(8863), new TestClass(8878), new TestClass(8965), new TestClass(8918), new TestClass(8893), new TestClass(8884), new TestClass(8900), new TestClass(8931), new TestClass(8919), new TestClass(8957), new TestClass(8994), new TestClass(8976), new TestClass(8968), new TestClass(8977), new TestClass(8999), new TestClass(8997), new TestClass(9003), new TestClass(9420), new TestClass(9221), new TestClass(9128), new TestClass(9036), new TestClass(9027), new TestClass(9021), new TestClass(9030), new TestClass(9076), new TestClass(9043), new TestClass(9114), new TestClass(9092), new TestClass(9171), new TestClass(9130), new TestClass(9129), new TestClass(9165), new TestClass(9214), new TestClass(9186), new TestClass(9220), new TestClass(9281), new TestClass(9252), new TestClass(9236), new TestClass(9229), new TestClass(9232), new TestClass(9247), new TestClass(9249), new TestClass(9277), new TestClass(9269), new TestClass(9279), new TestClass(9278), new TestClass(9352), new TestClass(9335), new TestClass(9305), new TestClass(9330), new TestClass(9343), new TestClass(9386), new TestClass(9355), new TestClass(9403), new TestClass(9396), new TestClass(9416), new TestClass(9548), new TestClass(9476), new TestClass(9455), new TestClass(9431), new TestClass(9427), new TestClass(9449), new TestClass(9463), new TestClass(9459), new TestClass(9468), new TestClass(9502), new TestClass(9489), new TestClass(9488), new TestClass(9495), new TestClass(9533), new TestClass(9510), new TestClass(9538), new TestClass(9535), new TestClass(9664), new TestClass(9602), new TestClass(9566), new TestClass(9550), new TestClass(9549), new TestClass(9553), new TestClass(9574), new TestClass(9570), new TestClass(9578), new TestClass(9575), new TestClass(9652), new TestClass(9645), new TestClass(9626), new TestClass(9651), new TestClass(9655), new TestClass(9653), new TestClass(9661), new TestClass(9770), new TestClass(9702), new TestClass(9686), new TestClass(9682), new TestClass(9681), new TestClass(9684), new TestClass(9695), new TestClass(9688), new TestClass(9700), new TestClass(9723), new TestClass(9711), new TestClass(9706), new TestClass(9714), new TestClass(9740), new TestClass(9739), new TestClass(9767), new TestClass(9830), new TestClass(9792), new TestClass(9780), new TestClass(9777), new TestClass(9789), new TestClass(9817), new TestClass(9794), new TestClass(9825), new TestClass(9872), new TestClass(9840), new TestClass(9833), new TestClass(9863), new TestClass(9913), new TestClass(9904), new TestClass(9889), new TestClass(9906), new TestClass(9951), new TestClass(9928), new TestClass(9918), new TestClass(9950), new TestClass(9980), new TestClass(9969), new TestClass(9953), new TestClass(9982), new TestClass(9997)
        }, fullBST);
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_One_Node_to_Empty() {
        // Assume this works
        tree.add(new TestClass(1));

        tree.remove(new TestClass(1));
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_remove_One_Node_Twice() {
        // Assume this works
        tree.add(new TestClass(1));

        tree.remove(new TestClass(1));
        tree.remove(new TestClass(1));
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_PBBST_Returns_Inserted() {
        assertEquals(0, pbBST.remove(new TestClass(1, 10)).value);
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_Degen_Left() {
        degenLeft.remove(new TestClass(5));
        degenLeft.remove(new TestClass(7));
        degenLeft.remove(new TestClass(19));

        // Level/pre-order traversal
        definitelyEquals(new TestClass[] {
            new TestClass(17), new TestClass(15), new TestClass(13), new TestClass(11),
            new TestClass(9), new TestClass(3), new TestClass(1)
        }, degenLeft);
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_Degen_Left_Leaf() {
        degenLeft.remove(new TestClass(1));
        degenLeft.remove(new TestClass(3));
        degenLeft.remove(new TestClass(5));

        // Level/pre-order traversal
        definitelyEquals(new TestClass[] {
            new TestClass(19), new TestClass(17), new TestClass(15), new TestClass(13),
            new TestClass(11), new TestClass(9), new TestClass(7)
        }, degenLeft);
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_Degen_Right() {
        degenRight.remove(new TestClass(2));
        degenRight.remove(new TestClass(4));
        degenRight.remove(new TestClass(14));

        // Level/pre-order traversal
        definitelyEquals(new TestClass[] {
            new TestClass(6), new TestClass(8), new TestClass(10), new TestClass(12),
            new TestClass(16), new TestClass(18), new TestClass(20)
        }, degenRight);
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_Degen_Right_Leaf() {
        degenRight.remove(new TestClass(20));
        degenRight.remove(new TestClass(18));
        degenRight.remove(new TestClass(16));

        // Level/pre-order traversal
        definitelyEquals(new TestClass[] {
            new TestClass(2), new TestClass(4), new TestClass(6), new TestClass(8),
            new TestClass(10), new TestClass(12), new TestClass(14)
        }, degenRight);
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_from_PBBST_1_root_root_root_root() {
        pbBST.remove(new TestClass(8));
        pbBST.remove(new TestClass(9));
        pbBST.remove(new TestClass(10));
        pbBST.remove(new TestClass(11));

        definitelyEquals(new TestClass[] {       new TestClass(12), new TestClass(4),
            new TestClass(14), new TestClass(2), new TestClass(6),  new TestClass(13),
            new TestClass(15), new TestClass(1), new TestClass(3),  new TestClass(5),
            new TestClass(7) }, pbBST);
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_from_PBBST_2_leaf_leaf_leaf_leaf() {
        // The method name's a lie; this actually removes 8 leaves
        for (int i = 1; i < 16; i += 2) {
            pbBST.remove(new TestClass(i));
        }

        definitelyEquals(new TestClass[] {
            new TestClass(8), new TestClass(4),  new TestClass(12), new TestClass(2),
            new TestClass(6), new TestClass(10), new TestClass(14)
        }, pbBST);
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_from_PBBST_3_leaf_leaf_left_right() {
        pbBST.remove(new TestClass(7));
        pbBST.remove(new TestClass(9));
        pbBST.remove(new TestClass(6));
        pbBST.remove(new TestClass(10));

        definitelyEquals(new TestClass[] {       new TestClass(8), new TestClass(4),
            new TestClass(12), new TestClass(2), new TestClass(5), new TestClass(11),
            new TestClass(14), new TestClass(1), new TestClass(3), new TestClass(13),
            new TestClass(15) }, pbBST); }

    @Test(timeout = TIMEOUT)
    public void test_remove_from_PBBST_4_two_two_right_left() {
        pbBST.remove(new TestClass(2));
        pbBST.remove(new TestClass(4));
        pbBST.remove(new TestClass(6));
        pbBST.remove(new TestClass(3));

        definitelyEquals(new TestClass[] {       new TestClass(8),  new TestClass(5),
            new TestClass(12), new TestClass(1), new TestClass(7),  new TestClass(10),
            new TestClass(14), new TestClass(9), new TestClass(11), new TestClass(13),
            new TestClass(15) }, pbBST);
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_from_PBBST_5_root_right_newleaf_root() {
        pbBST.remove(new TestClass(8));
        pbBST.remove(new TestClass(10));
        pbBST.remove(new TestClass(11));
        pbBST.remove(new TestClass(9));

        definitelyEquals(new TestClass[] {       new TestClass(12), new TestClass(4),
            new TestClass(14), new TestClass(2), new TestClass(6),  new TestClass(13),
            new TestClass(15), new TestClass(1), new TestClass(3),  new TestClass(5),
            new TestClass(7) }, pbBST);
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_from_Full_BST() {
        if (!UNLEASH_FULL) { return; }
        loadFullBST();
        for (TestClass n : CONTAINED_IN_FULL_BST) {
            fullBST.remove(n);
        }

        definitelyEquals(new TestClass[] { new TestClass(2403), new TestClass(1283), new TestClass(633), new TestClass(362), new TestClass(190), new TestClass(104), new TestClass(45), new TestClass(17), new TestClass(20), new TestClass(59), new TestClass(85), new TestClass(175), new TestClass(116), new TestClass(106), new TestClass(166), new TestClass(176), new TestClass(183), new TestClass(248), new TestClass(228), new TestClass(202), new TestClass(195), new TestClass(206), new TestClass(235), new TestClass(231), new TestClass(240), new TestClass(310), new TestClass(298), new TestClass(271), new TestClass(302), new TestClass(337), new TestClass(348), new TestClass(504), new TestClass(435), new TestClass(400), new TestClass(376), new TestClass(365), new TestClass(383), new TestClass(427), new TestClass(415), new TestClass(461), new TestClass(452), new TestClass(444), new TestClass(460), new TestClass(493), new TestClass(477), new TestClass(573), new TestClass(538), new TestClass(523), new TestClass(534), new TestClass(554), new TestClass(547), new TestClass(556), new TestClass(609), new TestClass(599), new TestClass(592), new TestClass(600), new TestClass(627), new TestClass(615), new TestClass(996), new TestClass(793), new TestClass(725), new TestClass(702), new TestClass(688), new TestClass(652), new TestClass(713), new TestClass(720), new TestClass(755), new TestClass(733), new TestClass(726), new TestClass(740), new TestClass(779), new TestClass(783), new TestClass(930), new TestClass(867), new TestClass(835), new TestClass(797), new TestClass(843), new TestClass(918), new TestClass(880), new TestClass(919), new TestClass(950), new TestClass(931), new TestClass(985), new TestClass(975), new TestClass(986), new TestClass(1112), new TestClass(1044), new TestClass(1024), new TestClass(1013), new TestClass(1012), new TestClass(1022), new TestClass(1033), new TestClass(1028), new TestClass(1040), new TestClass(1091), new TestClass(1078), new TestClass(1065), new TestClass(1085), new TestClass(1106), new TestClass(1104), new TestClass(1108), new TestClass(1183), new TestClass(1137), new TestClass(1123), new TestClass(1122), new TestClass(1133), new TestClass(1172), new TestClass(1144), new TestClass(1179), new TestClass(1239), new TestClass(1189), new TestClass(1188), new TestClass(1209), new TestClass(1258), new TestClass(1251), new TestClass(1270), new TestClass(1959), new TestClass(1648), new TestClass(1508), new TestClass(1389), new TestClass(1337), new TestClass(1317), new TestClass(1309), new TestClass(1326), new TestClass(1362), new TestClass(1386), new TestClass(1433), new TestClass(1416), new TestClass(1412), new TestClass(1421), new TestClass(1482), new TestClass(1475), new TestClass(1497), new TestClass(1601), new TestClass(1567), new TestClass(1509), new TestClass(1559), new TestClass(1577), new TestClass(1575), new TestClass(1597), new TestClass(1630), new TestClass(1621), new TestClass(1602), new TestClass(1624), new TestClass(1638), new TestClass(1633), new TestClass(1641), new TestClass(1770), new TestClass(1703), new TestClass(1691), new TestClass(1682), new TestClass(1678), new TestClass(1687), new TestClass(1701), new TestClass(1697), new TestClass(1702), new TestClass(1726), new TestClass(1716), new TestClass(1722), new TestClass(1746), new TestClass(1858), new TestClass(1816), new TestClass(1784), new TestClass(1777), new TestClass(1810), new TestClass(1828), new TestClass(1824), new TestClass(1857), new TestClass(1897), new TestClass(1878), new TestClass(1871), new TestClass(1881), new TestClass(1930), new TestClass(1903), new TestClass(1956), new TestClass(2163), new TestClass(2052), new TestClass(1993), new TestClass(1975), new TestClass(1971), new TestClass(1962), new TestClass(1981), new TestClass(1979), new TestClass(2024), new TestClass(1997), new TestClass(1995), new TestClass(2011), new TestClass(2047), new TestClass(2098), new TestClass(2060), new TestClass(2056), new TestClass(2054), new TestClass(2057), new TestClass(2091), new TestClass(2087), new TestClass(2093), new TestClass(2128), new TestClass(2107), new TestClass(2104), new TestClass(2115), new TestClass(2152), new TestClass(2131), new TestClass(2273), new TestClass(2211), new TestClass(2175), new TestClass(2170), new TestClass(2168), new TestClass(2171), new TestClass(2203), new TestClass(2200), new TestClass(2206), new TestClass(2250), new TestClass(2224), new TestClass(2214), new TestClass(2267), new TestClass(2259), new TestClass(2352), new TestClass(2324), new TestClass(2298), new TestClass(2305), new TestClass(2340), new TestClass(2344), new TestClass(2370), new TestClass(2361), new TestClass(2366), new TestClass(2389), new TestClass(2384), new TestClass(2400), new TestClass(4979), new TestClass(3651), new TestClass(2960), new TestClass(2699), new TestClass(2566), new TestClass(2502), new TestClass(2460), new TestClass(2452), new TestClass(2406), new TestClass(2454), new TestClass(2478), new TestClass(2468), new TestClass(2491), new TestClass(2548), new TestClass(2526), new TestClass(2523), new TestClass(2528), new TestClass(2561), new TestClass(2559), new TestClass(2563), new TestClass(2645), new TestClass(2607), new TestClass(2599), new TestClass(2585), new TestClass(2602), new TestClass(2641), new TestClass(2612), new TestClass(2644), new TestClass(2690), new TestClass(2675), new TestClass(2671), new TestClass(2694), new TestClass(2692), new TestClass(2698), new TestClass(2801), new TestClass(2744), new TestClass(2721), new TestClass(2713), new TestClass(2709), new TestClass(2717), new TestClass(2739), new TestClass(2732), new TestClass(2771), new TestClass(2763), new TestClass(2757), new TestClass(2765), new TestClass(2778), new TestClass(2777), new TestClass(2794), new TestClass(2850), new TestClass(2841), new TestClass(2837), new TestClass(2831), new TestClass(2840), new TestClass(2848), new TestClass(2847), new TestClass(2849), new TestClass(2884), new TestClass(2862), new TestClass(2853), new TestClass(2877), new TestClass(2939), new TestClass(2890), new TestClass(2953), new TestClass(3367), new TestClass(3142), new TestClass(3044), new TestClass(2979), new TestClass(2970), new TestClass(2969), new TestClass(3002), new TestClass(3001), new TestClass(3040), new TestClass(3094), new TestClass(3061), new TestClass(3050), new TestClass(3067), new TestClass(3116), new TestClass(3115), new TestClass(3138), new TestClass(3269), new TestClass(3208), new TestClass(3178), new TestClass(3151), new TestClass(3233), new TestClass(3220), new TestClass(3259), new TestClass(3341), new TestClass(3303), new TestClass(3292), new TestClass(3304), new TestClass(3365), new TestClass(3362), new TestClass(3531), new TestClass(3429), new TestClass(3391), new TestClass(3387), new TestClass(3372), new TestClass(3389), new TestClass(3410), new TestClass(3400), new TestClass(3414), new TestClass(3486), new TestClass(3455), new TestClass(3444), new TestClass(3466), new TestClass(3489), new TestClass(3487), new TestClass(3508), new TestClass(3597), new TestClass(3576), new TestClass(3556), new TestClass(3554), new TestClass(3565), new TestClass(3579), new TestClass(3578), new TestClass(3582), new TestClass(3630), new TestClass(3601), new TestClass(3615), new TestClass(3639), new TestClass(3636), new TestClass(3643), new TestClass(4415), new TestClass(3991), new TestClass(3815), new TestClass(3735), new TestClass(3676), new TestClass(3668), new TestClass(3666), new TestClass(3671), new TestClass(3702), new TestClass(3681), new TestClass(3732), new TestClass(3801), new TestClass(3766), new TestClass(3765), new TestClass(3777), new TestClass(3812), new TestClass(3804), new TestClass(3912), new TestClass(3843), new TestClass(3837), new TestClass(3833), new TestClass(3839), new TestClass(3892), new TestClass(3861), new TestClass(3893), new TestClass(3936), new TestClass(3927), new TestClass(3918), new TestClass(3988), new TestClass(3968), new TestClass(3990), new TestClass(4248), new TestClass(4089), new TestClass(4062), new TestClass(4041), new TestClass(4006), new TestClass(4060), new TestClass(4074), new TestClass(4083), new TestClass(4192), new TestClass(4115), new TestClass(4114), new TestClass(4228), new TestClass(4227), new TestClass(4317), new TestClass(4281), new TestClass(4265), new TestClass(4268), new TestClass(4295), new TestClass(4286), new TestClass(4309), new TestClass(4391), new TestClass(4387), new TestClass(4333), new TestClass(4388), new TestClass(4404), new TestClass(4409), new TestClass(4719), new TestClass(4564), new TestClass(4475), new TestClass(4447), new TestClass(4420), new TestClass(4418), new TestClass(4456), new TestClass(4457), new TestClass(4518), new TestClass(4515), new TestClass(4478), new TestClass(4539), new TestClass(4521), new TestClass(4551), new TestClass(4654), new TestClass(4598), new TestClass(4593), new TestClass(4566), new TestClass(4595), new TestClass(4607), new TestClass(4600), new TestClass(4624), new TestClass(4674), new TestClass(4665), new TestClass(4666), new TestClass(4687), new TestClass(4676), new TestClass(4698), new TestClass(4804), new TestClass(4763), new TestClass(4736), new TestClass(4730), new TestClass(4726), new TestClass(4732), new TestClass(4743), new TestClass(4737), new TestClass(4754), new TestClass(4787), new TestClass(4784), new TestClass(4774), new TestClass(4785), new TestClass(4792), new TestClass(4789), new TestClass(4801), new TestClass(4865), new TestClass(4839), new TestClass(4829), new TestClass(4815), new TestClass(4833), new TestClass(4860), new TestClass(4950), new TestClass(4912), new TestClass(4904), new TestClass(4938), new TestClass(4962), new TestClass(4955), new TestClass(4964), new TestClass(6210), new TestClass(5657), new TestClass(5325), new TestClass(5206), new TestClass(5081), new TestClass(5018), new TestClass(5004), new TestClass(4994), new TestClass(5038), new TestClass(5059), new TestClass(5194), new TestClass(5116), new TestClass(5086), new TestClass(5198), new TestClass(5200), new TestClass(5275), new TestClass(5223), new TestClass(5215), new TestClass(5211), new TestClass(5220), new TestClass(5257), new TestClass(5242), new TestClass(5263), new TestClass(5305), new TestClass(5289), new TestClass(5277), new TestClass(5294), new TestClass(5317), new TestClass(5315), new TestClass(5318), new TestClass(5465), new TestClass(5396), new TestClass(5379), new TestClass(5354), new TestClass(5342), new TestClass(5367), new TestClass(5392), new TestClass(5383), new TestClass(5393), new TestClass(5433), new TestClass(5418), new TestClass(5413), new TestClass(5419), new TestClass(5447), new TestClass(5446), new TestClass(5462), new TestClass(5569), new TestClass(5514), new TestClass(5490), new TestClass(5481), new TestClass(5493), new TestClass(5531), new TestClass(5527), new TestClass(5541), new TestClass(5587), new TestClass(5577), new TestClass(5570), new TestClass(5583), new TestClass(5627), new TestClass(5589), new TestClass(5638), new TestClass(5953), new TestClass(5827), new TestClass(5723), new TestClass(5682), new TestClass(5670), new TestClass(5666), new TestClass(5671), new TestClass(5703), new TestClass(5707), new TestClass(5767), new TestClass(5742), new TestClass(5727), new TestClass(5743), new TestClass(5783), new TestClass(5801), new TestClass(5897), new TestClass(5848), new TestClass(5846), new TestClass(5892), new TestClass(5887), new TestClass(5896), new TestClass(5923), new TestClass(5903), new TestClass(5902), new TestClass(5916), new TestClass(5939), new TestClass(6074), new TestClass(6004), new TestClass(5984), new TestClass(5954), new TestClass(5997), new TestClass(5989), new TestClass(6047), new TestClass(6041), new TestClass(6008), new TestClass(6063), new TestClass(6060), new TestClass(6065), new TestClass(6140), new TestClass(6096), new TestClass(6088), new TestClass(6083), new TestClass(6090), new TestClass(6131), new TestClass(6110), new TestClass(6132), new TestClass(6179), new TestClass(6168), new TestClass(6144), new TestClass(6190), new TestClass(6184), new TestClass(6205), new TestClass(7558), new TestClass(6887), new TestClass(6545), new TestClass(6383), new TestClass(6271), new TestClass(6241), new TestClass(6237), new TestClass(6212), new TestClass(6250), new TestClass(6246), new TestClass(6258), new TestClass(6296), new TestClass(6279), new TestClass(6277), new TestClass(6287), new TestClass(6317), new TestClass(6310), new TestClass(6364), new TestClass(6504), new TestClass(6418), new TestClass(6405), new TestClass(6398), new TestClass(6414), new TestClass(6443), new TestClass(6439), new TestClass(6450), new TestClass(6532), new TestClass(6508), new TestClass(6512), new TestClass(6538), new TestClass(6543), new TestClass(6716), new TestClass(6618), new TestClass(6576), new TestClass(6561), new TestClass(6564), new TestClass(6595), new TestClass(6578), new TestClass(6612), new TestClass(6675), new TestClass(6658), new TestClass(6640), new TestClass(6667), new TestClass(6692), new TestClass(6696), new TestClass(6793), new TestClass(6773), new TestClass(6756), new TestClass(6774), new TestClass(6781), new TestClass(6833), new TestClass(6814), new TestClass(6831), new TestClass(6847), new TestClass(6843), new TestClass(6861), new TestClass(7207), new TestClass(7059), new TestClass(6982), new TestClass(6940), new TestClass(6902), new TestClass(6896), new TestClass(6931), new TestClass(6951), new TestClass(6945), new TestClass(6961), new TestClass(7031), new TestClass(6991), new TestClass(7007), new TestClass(7045), new TestClass(7036), new TestClass(7046), new TestClass(7170), new TestClass(7136), new TestClass(7110), new TestClass(7078), new TestClass(7132), new TestClass(7142), new TestClass(7140), new TestClass(7150), new TestClass(7187), new TestClass(7180), new TestClass(7192), new TestClass(7190), new TestClass(7349), new TestClass(7265), new TestClass(7243), new TestClass(7232), new TestClass(7212), new TestClass(7236), new TestClass(7254), new TestClass(7246), new TestClass(7258), new TestClass(7320), new TestClass(7292), new TestClass(7283), new TestClass(7338), new TestClass(7346), new TestClass(7464), new TestClass(7429), new TestClass(7371), new TestClass(7357), new TestClass(7373), new TestClass(7440), new TestClass(7438), new TestClass(7453), new TestClass(7496), new TestClass(7478), new TestClass(7474), new TestClass(7481), new TestClass(7504), new TestClass(7554), new TestClass(8328), new TestClass(7954), new TestClass(7795), new TestClass(7686), new TestClass(7612), new TestClass(7579), new TestClass(7575), new TestClass(7609), new TestClass(7642), new TestClass(7633), new TestClass(7711), new TestClass(7699), new TestClass(7752), new TestClass(7747), new TestClass(7780), new TestClass(7884), new TestClass(7824), new TestClass(7803), new TestClass(7801), new TestClass(7808), new TestClass(7849), new TestClass(7844), new TestClass(7877), new TestClass(7910), new TestClass(7897), new TestClass(7891), new TestClass(7898), new TestClass(7925), new TestClass(7924), new TestClass(7953), new TestClass(8191), new TestClass(8023), new TestClass(7986), new TestClass(7966), new TestClass(7958), new TestClass(7980), new TestClass(8013), new TestClass(8005), new TestClass(8017), new TestClass(8126), new TestClass(8068), new TestClass(8045), new TestClass(8116), new TestClass(8171), new TestClass(8153), new TestClass(8257), new TestClass(8211), new TestClass(8204), new TestClass(8205), new TestClass(8226), new TestClass(8217), new TestClass(8244), new TestClass(8297), new TestClass(8277), new TestClass(8276), new TestClass(8281), new TestClass(8315), new TestClass(8314), new TestClass(8325), new TestClass(9020), new TestClass(8735), new TestClass(8527), new TestClass(8412), new TestClass(8354), new TestClass(8336), new TestClass(8335), new TestClass(8343), new TestClass(8366), new TestClass(8363), new TestClass(8371), new TestClass(8482), new TestClass(8439), new TestClass(8436), new TestClass(8450), new TestClass(8496), new TestClass(8520), new TestClass(8625), new TestClass(8591), new TestClass(8548), new TestClass(8535), new TestClass(8580), new TestClass(8606), new TestClass(8594), new TestClass(8623), new TestClass(8666), new TestClass(8639), new TestClass(8631), new TestClass(8644), new TestClass(8691), new TestClass(8667), new TestClass(8725), new TestClass(8881), new TestClass(8839), new TestClass(8766), new TestClass(8755), new TestClass(8738), new TestClass(8761), new TestClass(8775), new TestClass(8774), new TestClass(8783), new TestClass(8859), new TestClass(8857), new TestClass(8878), new TestClass(8871), new TestClass(8965), new TestClass(8918), new TestClass(8900), new TestClass(8884), new TestClass(8931), new TestClass(8957), new TestClass(8994), new TestClass(8976), new TestClass(8977), new TestClass(8999), new TestClass(8997), new TestClass(9003), new TestClass(9420), new TestClass(9221), new TestClass(9128), new TestClass(9036), new TestClass(9027), new TestClass(9030), new TestClass(9076), new TestClass(9043), new TestClass(9114), new TestClass(9171), new TestClass(9130), new TestClass(9129), new TestClass(9165), new TestClass(9214), new TestClass(9186), new TestClass(9220), new TestClass(9281), new TestClass(9252), new TestClass(9236), new TestClass(9247), new TestClass(9277), new TestClass(9269), new TestClass(9279), new TestClass(9352), new TestClass(9343), new TestClass(9305), new TestClass(9386), new TestClass(9355), new TestClass(9403), new TestClass(9549), new TestClass(9476), new TestClass(9455), new TestClass(9431), new TestClass(9449), new TestClass(9463), new TestClass(9459), new TestClass(9468), new TestClass(9502), new TestClass(9489), new TestClass(9488), new TestClass(9495), new TestClass(9538), new TestClass(9510), new TestClass(9664), new TestClass(9602), new TestClass(9566), new TestClass(9550), new TestClass(9553), new TestClass(9574), new TestClass(9570), new TestClass(9578), new TestClass(9652), new TestClass(9645), new TestClass(9626), new TestClass(9651), new TestClass(9655), new TestClass(9653), new TestClass(9661), new TestClass(9770), new TestClass(9702), new TestClass(9686), new TestClass(9682), new TestClass(9681), new TestClass(9695), new TestClass(9688), new TestClass(9723), new TestClass(9711), new TestClass(9706), new TestClass(9714), new TestClass(9740), new TestClass(9739), new TestClass(9767), new TestClass(9830), new TestClass(9792), new TestClass(9780), new TestClass(9777), new TestClass(9789), new TestClass(9817), new TestClass(9794), new TestClass(9825), new TestClass(9872), new TestClass(9840), new TestClass(9833), new TestClass(9863), new TestClass(9913), new TestClass(9906), new TestClass(9889), new TestClass(9951), new TestClass(9928), new TestClass(9950), new TestClass(9980), new TestClass(9969), new TestClass(9982), new TestClass(9997)
        }, fullBST);
    }

    @Test(timeout = TIMEOUT)
    public void test_get_PBBST_Returns_Inserted() {
        assertEquals(0, pbBST.get(new TestClass(1, 10)).value);
    }

    @Test(timeout = TIMEOUT)
    public void test_get_Degen_Left() {
        for (int i = 1; i < 20; i += 2) {
            TestClass t = degenLeft.get(new TestClass(i, 19));
            assertEquals(i, t.key);
            assertEquals(0, t.value);
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_get_Degen_Right() {
        for (int i = 2; i <= 20; i += 2) {
            TestClass t = degenRight.get(new TestClass(i, 19));
            assertEquals(i, t.key);
            assertEquals(0, t.value);
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_get_PBBST() {
        for (int i = 1; i < 16; i++) {
            TestClass t = pbBST.get(new TestClass(i, 19));
            assertEquals(i, t.key);
            assertEquals(0, t.value);
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_get_Full_BST() {
        if (!UNLEASH_FULL) { return; }
        loadFullBST();
        for (int i = 1; i < CONTAINED_IN_FULL_BST.length; i++) {
            TestClass t = fullBST.get(CONTAINED_IN_FULL_BST[i]);
            assertEquals(CONTAINED_IN_FULL_BST[i].key, t.key);
            assertEquals(0, t.value);
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_contains_in_Degen_Left() {
        for (int i = 1; i < 20; i += 2) {
            assertTrue(degenLeft.contains(new TestClass(i)));
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_contains_in_Degen_Right() {
        for (int i = 2; i <= 20; i += 2) {
            assertTrue(degenRight.contains(new TestClass(i)));
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_contains_in_PBBST() {
        for (int i = 1; i < 16; i++) {
            assertTrue(pbBST.contains(new TestClass(i)));
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_contains_in_Full_BST() {
        if (!UNLEASH_FULL) { return; }
        loadFullBST();
        for (int i = 1; i < CONTAINED_IN_FULL_BST.length; i++) {
            assertTrue(fullBST.contains(CONTAINED_IN_FULL_BST[i]));
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_contains_Not_in_Degen_Left() {
        for (int i = 2; i <= 20; i += 2) {
            assertFalse(degenLeft.contains(new TestClass(i)));
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_contains_Not_in_Degen_Right() {
        for (int i = 1; i < 20; i += 2) {
            assertFalse(degenRight.contains(new TestClass(i)));
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_contains_Not_in_PBBST() {
        for (int i = 16; i < 100; i++) {
            assertFalse(pbBST.contains(new TestClass(i)));
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_contains_Not_in_Full_BST() {
        if (!UNLEASH_FULL) { return; }
        loadFullBST();
        for (int i = 1; i < NOT_CONTAINED_IN_FULL_BST.length; i++) {
            assertFalse(fullBST.contains(NOT_CONTAINED_IN_FULL_BST[i]));
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_preorder_Degen_Left() {
        assertEquals(Arrays.asList(DEGEN_LEFT_ARRAY), degenLeft.preorder());
    }

    @Test(timeout = TIMEOUT)
    public void test_preorder_Degen_Right() {
        assertEquals(Arrays.asList(DEGEN_RIGHT_ARRAY), degenRight.preorder());
    }

    @Test(timeout = TIMEOUT)
    public void test_preorder_PBBST() {
        assertEquals(Arrays.asList(new TestClass[] {
            new TestClass(8),  new TestClass(4),  new TestClass(2), new TestClass(1),
            new TestClass(3),  new TestClass(6),  new TestClass(5), new TestClass(7),
            new TestClass(12), new TestClass(10), new TestClass(9), new TestClass(11),
            new TestClass(14), new TestClass(13), new TestClass(15)
        }), pbBST.preorder());
    }

    @Test(timeout = TIMEOUT)
    public void test_preorder_Full_BST() {
        if (!UNLEASH_FULL) { return; }
        loadFullBST();
        assertEquals(Arrays.asList(FULL_BST_ARRAY), fullBST.preorder());
    }

    @Test(timeout = TIMEOUT)
    public void test_inorder_Degen_Left() {
        TestClass[] sorted = Arrays.copyOf(DEGEN_LEFT_ARRAY, DEGEN_LEFT_ARRAY.length);
        Arrays.sort(sorted);
        assertEquals(Arrays.asList(sorted), degenLeft.inorder());
    }

    @Test(timeout = TIMEOUT)
    public void test_inorder_Degen_Right() {
        // TestClass[] sorted = Arrays.copyOf(DEGEN_RIGHT_ARRAY, DEGEN_RIGHT_ARRAY.length);
        // Arrays.sort(sorted);
        assertEquals(Arrays.asList(DEGEN_RIGHT_ARRAY), degenRight.inorder());
    }

    @Test(timeout = TIMEOUT)
    public void test_inorder_PBBST() {
        TestClass[] sorted = Arrays.copyOf(PBBST_ARRAY, PBBST_ARRAY.length);
        Arrays.sort(sorted);
        assertEquals(Arrays.asList(sorted), pbBST.inorder());
    }

    @Test(timeout = TIMEOUT)
    public void test_inorder_Full_BST() {
        if (!UNLEASH_FULL) { return; }
        loadFullBST();
        TestClass[] sorted = Arrays.copyOf(FULL_BST_ARRAY, FULL_BST_ARRAY.length);
        Arrays.sort(sorted);
        assertEquals(Arrays.asList(sorted), fullBST.inorder());
    }

    @Test(timeout = TIMEOUT)
    public void test_postorder_Degen_Left() {
        TestClass[] reversed = Arrays.copyOf(DEGEN_LEFT_ARRAY, DEGEN_LEFT_ARRAY.length);
        Collections.reverse(Arrays.asList(reversed));
        assertEquals(Arrays.asList(reversed), degenLeft.postorder());
    }

    @Test(timeout = TIMEOUT)
    public void test_postorder_Degen_Right() {
        TestClass[] reversed = Arrays.copyOf(DEGEN_RIGHT_ARRAY, DEGEN_RIGHT_ARRAY.length);
        Collections.reverse(Arrays.asList(reversed));
        assertEquals(Arrays.asList(reversed), degenRight.postorder());
    }

    @Test(timeout = TIMEOUT)
    public void test_postorder_PBBST() {
        assertEquals(Arrays.asList(new TestClass[] {
            new TestClass(1),  new TestClass(3),  new TestClass(2),  new TestClass(5),
            new TestClass(7),  new TestClass(6),  new TestClass(4),  new TestClass(9),
            new TestClass(11), new TestClass(10), new TestClass(13), new TestClass(15),
            new TestClass(14), new TestClass(12), new TestClass(8)
        }), pbBST.postorder());
    }

    @Test(timeout = TIMEOUT)
    public void test_postorder_Full_BST() {
        if (!UNLEASH_FULL) { return; }
        loadFullBST();
        assertEquals(Arrays.asList(new TestClass[] { new TestClass(8), new TestClass(20), new TestClass(17), new TestClass(58), new TestClass(85), new TestClass(59), new TestClass(45), new TestClass(106), new TestClass(166), new TestClass(116), new TestClass(175), new TestClass(183), new TestClass(176), new TestClass(169), new TestClass(104), new TestClass(195), new TestClass(206), new TestClass(202), new TestClass(231), new TestClass(240), new TestClass(235), new TestClass(228), new TestClass(271), new TestClass(302), new TestClass(298), new TestClass(336), new TestClass(348), new TestClass(337), new TestClass(310), new TestClass(248), new TestClass(190), new TestClass(365), new TestClass(383), new TestClass(376), new TestClass(415), new TestClass(430), new TestClass(427), new TestClass(400), new TestClass(444), new TestClass(460), new TestClass(452), new TestClass(477), new TestClass(493), new TestClass(487), new TestClass(461), new TestClass(435), new TestClass(504), new TestClass(534), new TestClass(523), new TestClass(547), new TestClass(556), new TestClass(554), new TestClass(538), new TestClass(592), new TestClass(600), new TestClass(599), new TestClass(615), new TestClass(629), new TestClass(627), new TestClass(609), new TestClass(573), new TestClass(501), new TestClass(362), new TestClass(652), new TestClass(688), new TestClass(682), new TestClass(711), new TestClass(720), new TestClass(713), new TestClass(702), new TestClass(726), new TestClass(740), new TestClass(733), new TestClass(755), new TestClass(783), new TestClass(779), new TestClass(754), new TestClass(725), new TestClass(797), new TestClass(843), new TestClass(835), new TestClass(880), new TestClass(919), new TestClass(918), new TestClass(867), new TestClass(929), new TestClass(931), new TestClass(930), new TestClass(975), new TestClass(986), new TestClass(985), new TestClass(950), new TestClass(925), new TestClass(793), new TestClass(1012), new TestClass(1022), new TestClass(1013), new TestClass(1028), new TestClass(1040), new TestClass(1033), new TestClass(1024), new TestClass(1065), new TestClass(1085), new TestClass(1078), new TestClass(1104), new TestClass(1108), new TestClass(1106), new TestClass(1091), new TestClass(1044), new TestClass(1122), new TestClass(1133), new TestClass(1123), new TestClass(1144), new TestClass(1179), new TestClass(1172), new TestClass(1137), new TestClass(1188), new TestClass(1209), new TestClass(1189), new TestClass(1251), new TestClass(1270), new TestClass(1258), new TestClass(1239), new TestClass(1183), new TestClass(1112), new TestClass(996), new TestClass(633), new TestClass(1309), new TestClass(1326), new TestClass(1317), new TestClass(1338), new TestClass(1386), new TestClass(1362), new TestClass(1337), new TestClass(1412), new TestClass(1421), new TestClass(1416), new TestClass(1475), new TestClass(1497), new TestClass(1482), new TestClass(1433), new TestClass(1389), new TestClass(1508), new TestClass(1559), new TestClass(1509), new TestClass(1575), new TestClass(1597), new TestClass(1577), new TestClass(1567), new TestClass(1602), new TestClass(1624), new TestClass(1621), new TestClass(1633), new TestClass(1641), new TestClass(1638), new TestClass(1630), new TestClass(1601), new TestClass(1498), new TestClass(1678), new TestClass(1687), new TestClass(1682), new TestClass(1697), new TestClass(1702), new TestClass(1701), new TestClass(1691), new TestClass(1705), new TestClass(1722), new TestClass(1716), new TestClass(1746), new TestClass(1757), new TestClass(1747), new TestClass(1726), new TestClass(1703), new TestClass(1777), new TestClass(1810), new TestClass(1784), new TestClass(1824), new TestClass(1857), new TestClass(1828), new TestClass(1816), new TestClass(1871), new TestClass(1881), new TestClass(1878), new TestClass(1903), new TestClass(1956), new TestClass(1930), new TestClass(1897), new TestClass(1858), new TestClass(1770), new TestClass(1648), new TestClass(1962), new TestClass(1971), new TestClass(1969), new TestClass(1979), new TestClass(1983), new TestClass(1981), new TestClass(1975), new TestClass(1995), new TestClass(2011), new TestClass(1997), new TestClass(2032), new TestClass(2047), new TestClass(2038), new TestClass(2024), new TestClass(1993), new TestClass(2054), new TestClass(2057), new TestClass(2056), new TestClass(2087), new TestClass(2093), new TestClass(2091), new TestClass(2060), new TestClass(2104), new TestClass(2115), new TestClass(2107), new TestClass(2131), new TestClass(2152), new TestClass(2150), new TestClass(2128), new TestClass(2098), new TestClass(2052), new TestClass(2168), new TestClass(2171), new TestClass(2170), new TestClass(2200), new TestClass(2206), new TestClass(2203), new TestClass(2175), new TestClass(2214), new TestClass(2224), new TestClass(2223), new TestClass(2259), new TestClass(2267), new TestClass(2266), new TestClass(2250), new TestClass(2211), new TestClass(2283), new TestClass(2305), new TestClass(2298), new TestClass(2324), new TestClass(2344), new TestClass(2340), new TestClass(2307), new TestClass(2354), new TestClass(2366), new TestClass(2361), new TestClass(2384), new TestClass(2400), new TestClass(2389), new TestClass(2370), new TestClass(2352), new TestClass(2273), new TestClass(2163), new TestClass(1959), new TestClass(1283), new TestClass(2406), new TestClass(2454), new TestClass(2452), new TestClass(2468), new TestClass(2491), new TestClass(2478), new TestClass(2460), new TestClass(2523), new TestClass(2528), new TestClass(2526), new TestClass(2559), new TestClass(2563), new TestClass(2561), new TestClass(2548), new TestClass(2502), new TestClass(2585), new TestClass(2602), new TestClass(2599), new TestClass(2612), new TestClass(2644), new TestClass(2641), new TestClass(2607), new TestClass(2671), new TestClass(2675), new TestClass(2673), new TestClass(2692), new TestClass(2698), new TestClass(2694), new TestClass(2690), new TestClass(2645), new TestClass(2566), new TestClass(2709), new TestClass(2717), new TestClass(2713), new TestClass(2732), new TestClass(2739), new TestClass(2737), new TestClass(2721), new TestClass(2757), new TestClass(2765), new TestClass(2763), new TestClass(2777), new TestClass(2794), new TestClass(2778), new TestClass(2771), new TestClass(2744), new TestClass(2831), new TestClass(2840), new TestClass(2837), new TestClass(2847), new TestClass(2849), new TestClass(2848), new TestClass(2841), new TestClass(2853), new TestClass(2877), new TestClass(2862), new TestClass(2890), new TestClass(2953), new TestClass(2939), new TestClass(2884), new TestClass(2850), new TestClass(2801), new TestClass(2699), new TestClass(2969), new TestClass(2974), new TestClass(2970), new TestClass(3001), new TestClass(3040), new TestClass(3002), new TestClass(2979), new TestClass(3050), new TestClass(3067), new TestClass(3061), new TestClass(3115), new TestClass(3138), new TestClass(3116), new TestClass(3094), new TestClass(3044), new TestClass(3151), new TestClass(3178), new TestClass(3163), new TestClass(3220), new TestClass(3259), new TestClass(3233), new TestClass(3208), new TestClass(3292), new TestClass(3304), new TestClass(3303), new TestClass(3362), new TestClass(3365), new TestClass(3364), new TestClass(3341), new TestClass(3269), new TestClass(3142), new TestClass(3372), new TestClass(3389), new TestClass(3387), new TestClass(3400), new TestClass(3414), new TestClass(3410), new TestClass(3391), new TestClass(3444), new TestClass(3466), new TestClass(3455), new TestClass(3487), new TestClass(3508), new TestClass(3489), new TestClass(3486), new TestClass(3429), new TestClass(3554), new TestClass(3565), new TestClass(3556), new TestClass(3578), new TestClass(3582), new TestClass(3579), new TestClass(3576), new TestClass(3597), new TestClass(3615), new TestClass(3601), new TestClass(3636), new TestClass(3643), new TestClass(3639), new TestClass(3630), new TestClass(3583), new TestClass(3531), new TestClass(3367), new TestClass(2960), new TestClass(3666), new TestClass(3671), new TestClass(3668), new TestClass(3681), new TestClass(3732), new TestClass(3702), new TestClass(3676), new TestClass(3765), new TestClass(3777), new TestClass(3766), new TestClass(3804), new TestClass(3812), new TestClass(3810), new TestClass(3801), new TestClass(3735), new TestClass(3833), new TestClass(3839), new TestClass(3837), new TestClass(3861), new TestClass(3893), new TestClass(3892), new TestClass(3843), new TestClass(3918), new TestClass(3927), new TestClass(3923), new TestClass(3968), new TestClass(3990), new TestClass(3988), new TestClass(3936), new TestClass(3912), new TestClass(3815), new TestClass(4006), new TestClass(4060), new TestClass(4041), new TestClass(4069), new TestClass(4083), new TestClass(4074), new TestClass(4062), new TestClass(4114), new TestClass(4142), new TestClass(4115), new TestClass(4227), new TestClass(4237), new TestClass(4228), new TestClass(4192), new TestClass(4089), new TestClass(4259), new TestClass(4268), new TestClass(4265), new TestClass(4286), new TestClass(4309), new TestClass(4295), new TestClass(4281), new TestClass(4333), new TestClass(4388), new TestClass(4387), new TestClass(4403), new TestClass(4409), new TestClass(4404), new TestClass(4391), new TestClass(4317), new TestClass(4248), new TestClass(3991), new TestClass(4418), new TestClass(4426), new TestClass(4420), new TestClass(4447), new TestClass(4457), new TestClass(4456), new TestClass(4438), new TestClass(4478), new TestClass(4515), new TestClass(4493), new TestClass(4521), new TestClass(4551), new TestClass(4539), new TestClass(4518), new TestClass(4475), new TestClass(4566), new TestClass(4595), new TestClass(4593), new TestClass(4600), new TestClass(4624), new TestClass(4607), new TestClass(4598), new TestClass(4654), new TestClass(4666), new TestClass(4665), new TestClass(4676), new TestClass(4698), new TestClass(4687), new TestClass(4674), new TestClass(4627), new TestClass(4564), new TestClass(4726), new TestClass(4732), new TestClass(4730), new TestClass(4737), new TestClass(4754), new TestClass(4743), new TestClass(4736), new TestClass(4774), new TestClass(4785), new TestClass(4784), new TestClass(4789), new TestClass(4801), new TestClass(4792), new TestClass(4787), new TestClass(4763), new TestClass(4815), new TestClass(4833), new TestClass(4829), new TestClass(4839), new TestClass(4860), new TestClass(4844), new TestClass(4838), new TestClass(4904), new TestClass(4938), new TestClass(4912), new TestClass(4955), new TestClass(4964), new TestClass(4962), new TestClass(4950), new TestClass(4865), new TestClass(4804), new TestClass(4719), new TestClass(4415), new TestClass(3651), new TestClass(4994), new TestClass(5016), new TestClass(5004), new TestClass(5036), new TestClass(5059), new TestClass(5038), new TestClass(5018), new TestClass(5086), new TestClass(5160), new TestClass(5116), new TestClass(5194), new TestClass(5200), new TestClass(5198), new TestClass(5188), new TestClass(5081), new TestClass(5211), new TestClass(5220), new TestClass(5215), new TestClass(5242), new TestClass(5263), new TestClass(5257), new TestClass(5223), new TestClass(5277), new TestClass(5294), new TestClass(5289), new TestClass(5315), new TestClass(5318), new TestClass(5317), new TestClass(5305), new TestClass(5275), new TestClass(5206), new TestClass(5342), new TestClass(5367), new TestClass(5354), new TestClass(5383), new TestClass(5393), new TestClass(5392), new TestClass(5379), new TestClass(5413), new TestClass(5419), new TestClass(5418), new TestClass(5446), new TestClass(5462), new TestClass(5447), new TestClass(5433), new TestClass(5396), new TestClass(5481), new TestClass(5493), new TestClass(5490), new TestClass(5527), new TestClass(5541), new TestClass(5531), new TestClass(5514), new TestClass(5570), new TestClass(5583), new TestClass(5577), new TestClass(5589), new TestClass(5638), new TestClass(5627), new TestClass(5587), new TestClass(5569), new TestClass(5465), new TestClass(5325), new TestClass(5666), new TestClass(5671), new TestClass(5670), new TestClass(5682), new TestClass(5707), new TestClass(5703), new TestClass(5676), new TestClass(5727), new TestClass(5743), new TestClass(5742), new TestClass(5781), new TestClass(5801), new TestClass(5783), new TestClass(5767), new TestClass(5723), new TestClass(5827), new TestClass(5846), new TestClass(5841), new TestClass(5887), new TestClass(5896), new TestClass(5892), new TestClass(5848), new TestClass(5902), new TestClass(5916), new TestClass(5903), new TestClass(5928), new TestClass(5939), new TestClass(5935), new TestClass(5923), new TestClass(5897), new TestClass(5820), new TestClass(5953), new TestClass(5964), new TestClass(5954), new TestClass(5989), new TestClass(5997), new TestClass(5995), new TestClass(5984), new TestClass(6008), new TestClass(6041), new TestClass(6010), new TestClass(6060), new TestClass(6065), new TestClass(6063), new TestClass(6047), new TestClass(6004), new TestClass(6083), new TestClass(6090), new TestClass(6088), new TestClass(6110), new TestClass(6132), new TestClass(6131), new TestClass(6096), new TestClass(6144), new TestClass(6168), new TestClass(6164), new TestClass(6184), new TestClass(6205), new TestClass(6190), new TestClass(6179), new TestClass(6140), new TestClass(6074), new TestClass(5941), new TestClass(5657), new TestClass(6212), new TestClass(6237), new TestClass(6224), new TestClass(6246), new TestClass(6258), new TestClass(6250), new TestClass(6241), new TestClass(6277), new TestClass(6287), new TestClass(6279), new TestClass(6310), new TestClass(6364), new TestClass(6317), new TestClass(6296), new TestClass(6271), new TestClass(6398), new TestClass(6414), new TestClass(6405), new TestClass(6439), new TestClass(6450), new TestClass(6443), new TestClass(6418), new TestClass(6504), new TestClass(6512), new TestClass(6508), new TestClass(6537), new TestClass(6543), new TestClass(6538), new TestClass(6532), new TestClass(6479), new TestClass(6383), new TestClass(6545), new TestClass(6564), new TestClass(6561), new TestClass(6578), new TestClass(6612), new TestClass(6595), new TestClass(6576), new TestClass(6640), new TestClass(6667), new TestClass(6658), new TestClass(6676), new TestClass(6696), new TestClass(6692), new TestClass(6675), new TestClass(6618), new TestClass(6716), new TestClass(6756), new TestClass(6738), new TestClass(6773), new TestClass(6781), new TestClass(6774), new TestClass(6759), new TestClass(6793), new TestClass(6831), new TestClass(6814), new TestClass(6843), new TestClass(6861), new TestClass(6847), new TestClass(6833), new TestClass(6783), new TestClass(6701), new TestClass(6544), new TestClass(6896), new TestClass(6931), new TestClass(6902), new TestClass(6945), new TestClass(6961), new TestClass(6951), new TestClass(6940), new TestClass(6982), new TestClass(7007), new TestClass(6991), new TestClass(7036), new TestClass(7046), new TestClass(7045), new TestClass(7031), new TestClass(6969), new TestClass(7078), new TestClass(7132), new TestClass(7110), new TestClass(7140), new TestClass(7150), new TestClass(7142), new TestClass(7136), new TestClass(7160), new TestClass(7180), new TestClass(7170), new TestClass(7190), new TestClass(7197), new TestClass(7192), new TestClass(7187), new TestClass(7157), new TestClass(7059), new TestClass(7212), new TestClass(7236), new TestClass(7232), new TestClass(7246), new TestClass(7258), new TestClass(7254), new TestClass(7243), new TestClass(7283), new TestClass(7301), new TestClass(7292), new TestClass(7327), new TestClass(7346), new TestClass(7338), new TestClass(7320), new TestClass(7265), new TestClass(7357), new TestClass(7373), new TestClass(7371), new TestClass(7438), new TestClass(7453), new TestClass(7440), new TestClass(7429), new TestClass(7474), new TestClass(7481), new TestClass(7478), new TestClass(7496), new TestClass(7554), new TestClass(7504), new TestClass(7486), new TestClass(7464), new TestClass(7349), new TestClass(7207), new TestClass(6887), new TestClass(7575), new TestClass(7609), new TestClass(7579), new TestClass(7633), new TestClass(7652), new TestClass(7642), new TestClass(7612), new TestClass(7672), new TestClass(7699), new TestClass(7686), new TestClass(7747), new TestClass(7780), new TestClass(7752), new TestClass(7711), new TestClass(7671), new TestClass(7801), new TestClass(7808), new TestClass(7803), new TestClass(7844), new TestClass(7877), new TestClass(7849), new TestClass(7824), new TestClass(7891), new TestClass(7898), new TestClass(7897), new TestClass(7924), new TestClass(7953), new TestClass(7925), new TestClass(7910), new TestClass(7884), new TestClass(7795), new TestClass(7958), new TestClass(7980), new TestClass(7966), new TestClass(8005), new TestClass(8017), new TestClass(8013), new TestClass(7986), new TestClass(8045), new TestClass(8116), new TestClass(8068), new TestClass(8153), new TestClass(8171), new TestClass(8166), new TestClass(8126), new TestClass(8023), new TestClass(8191), new TestClass(8205), new TestClass(8204), new TestClass(8217), new TestClass(8244), new TestClass(8226), new TestClass(8211), new TestClass(8276), new TestClass(8281), new TestClass(8277), new TestClass(8314), new TestClass(8325), new TestClass(8315), new TestClass(8297), new TestClass(8257), new TestClass(8176), new TestClass(7954), new TestClass(8335), new TestClass(8343), new TestClass(8336), new TestClass(8363), new TestClass(8371), new TestClass(8366), new TestClass(8354), new TestClass(8436), new TestClass(8450), new TestClass(8439), new TestClass(8482), new TestClass(8520), new TestClass(8496), new TestClass(8472), new TestClass(8412), new TestClass(8535), new TestClass(8580), new TestClass(8548), new TestClass(8594), new TestClass(8623), new TestClass(8606), new TestClass(8591), new TestClass(8631), new TestClass(8644), new TestClass(8639), new TestClass(8667), new TestClass(8725), new TestClass(8691), new TestClass(8666), new TestClass(8625), new TestClass(8527), new TestClass(8738), new TestClass(8761), new TestClass(8755), new TestClass(8774), new TestClass(8783), new TestClass(8775), new TestClass(8766), new TestClass(8835), new TestClass(8857), new TestClass(8839), new TestClass(8871), new TestClass(8878), new TestClass(8874), new TestClass(8859), new TestClass(8805), new TestClass(8884), new TestClass(8900), new TestClass(8893), new TestClass(8919), new TestClass(8957), new TestClass(8931), new TestClass(8918), new TestClass(8968), new TestClass(8977), new TestClass(8976), new TestClass(8997), new TestClass(9003), new TestClass(8999), new TestClass(8994), new TestClass(8965), new TestClass(8881), new TestClass(8735), new TestClass(9021), new TestClass(9030), new TestClass(9027), new TestClass(9043), new TestClass(9114), new TestClass(9076), new TestClass(9036), new TestClass(9129), new TestClass(9165), new TestClass(9130), new TestClass(9186), new TestClass(9220), new TestClass(9214), new TestClass(9171), new TestClass(9128), new TestClass(9229), new TestClass(9247), new TestClass(9236), new TestClass(9269), new TestClass(9279), new TestClass(9277), new TestClass(9252), new TestClass(9305), new TestClass(9343), new TestClass(9335), new TestClass(9355), new TestClass(9403), new TestClass(9386), new TestClass(9352), new TestClass(9281), new TestClass(9221), new TestClass(9427), new TestClass(9449), new TestClass(9431), new TestClass(9459), new TestClass(9468), new TestClass(9463), new TestClass(9455), new TestClass(9488), new TestClass(9495), new TestClass(9489), new TestClass(9510), new TestClass(9538), new TestClass(9533), new TestClass(9502), new TestClass(9476), new TestClass(9549), new TestClass(9553), new TestClass(9550), new TestClass(9570), new TestClass(9578), new TestClass(9574), new TestClass(9566), new TestClass(9626), new TestClass(9651), new TestClass(9645), new TestClass(9653), new TestClass(9661), new TestClass(9655), new TestClass(9652), new TestClass(9602), new TestClass(9681), new TestClass(9684), new TestClass(9682), new TestClass(9688), new TestClass(9700), new TestClass(9695), new TestClass(9686), new TestClass(9706), new TestClass(9714), new TestClass(9711), new TestClass(9739), new TestClass(9767), new TestClass(9740), new TestClass(9723), new TestClass(9702), new TestClass(9777), new TestClass(9789), new TestClass(9780), new TestClass(9794), new TestClass(9825), new TestClass(9817), new TestClass(9792), new TestClass(9833), new TestClass(9863), new TestClass(9840), new TestClass(9889), new TestClass(9906), new TestClass(9904), new TestClass(9918), new TestClass(9950), new TestClass(9928), new TestClass(9969), new TestClass(9997), new TestClass(9982), new TestClass(9980), new TestClass(9951), new TestClass(9913), new TestClass(9872), new TestClass(9830), new TestClass(9770), new TestClass(9664), new TestClass(9548), new TestClass(9420), new TestClass(9020), new TestClass(8328), new TestClass(7558), new TestClass(6210), new TestClass(4979), new TestClass(2403) }), fullBST.postorder());
    }

	// @Test(timeout = TIMEOUT)
	// public void test_levelorder_Degen_Left() {
	// }

	// @Test(timeout = TIMEOUT)
	// public void test_levelorder_Degen_Right() {
	// }

	// @Test(timeout = TIMEOUT)
	// public void test_levelorder_PBBST() {
	// }

	// @Test(timeout = TIMEOUT)
	// public void test_levelorder_Full_BST() {
	// }

	@Test(timeout = TIMEOUT)
	public void test_height_Degen_Left() {
        assertEquals(9, degenLeft.height());
	}

	@Test(timeout = TIMEOUT)
	public void test_height_Degen_Right() {
        assertEquals(9, degenRight.height());
	}

	@Test(timeout = TIMEOUT)
	public void test_height_PBBST() {
        assertEquals(3, pbBST.height());
	}

	@Test(timeout = TIMEOUT)
	public void test_height_Full_BST() {
        if (!UNLEASH_FULL) { return; }
        loadFullBST();
        assertEquals(16, fullBST.height());
	}

	@Test(timeout = TIMEOUT)
	public void test_clear_Degen_Left() {
        degenLeft.clear();
        assertEquals(0, degenLeft.size());
        assertNull(degenLeft.getRoot());
	}

	@Test(timeout = TIMEOUT)
	public void test_clear_Degen_Right() {
        degenRight.clear();
        assertEquals(0, degenRight.size());
        assertNull(degenRight.getRoot());
	}

	@Test(timeout = TIMEOUT)
	public void test_clear_PBBST() {
        pbBST.clear();
        assertEquals(0, pbBST.size());
        assertNull(pbBST.getRoot());
	}

	@Test(timeout = TIMEOUT)
	public void test_clear_Full_BST() {
        if (!UNLEASH_FULL) { return; }
        loadFullBST();
        fullBST.clear();
        assertEquals(0, fullBST.size());
        assertNull(fullBST.getRoot());
	}

    @Test(timeout = TIMEOUT)
    public void test_findPathBetween_javadoc_example() {
        // This is the example given in the provided javadocs
        BST<Integer> t = new BST<>(Arrays.asList(new Integer[] { 50, 25, 75, 12, 37, 11, 15, 40, 10}));

        assertEquals(Arrays.asList(new Integer[] { 10, 11, 12, 25, 37, 40 }), t.findPathBetween(10, 40));
        assertEquals(Arrays.asList(new Integer[] { 50, 25, 37 }), t.findPathBetween(50, 37));
        assertEquals(Arrays.asList(new Integer[] { 75 }), t.findPathBetween(75, 75));
    }

    @Test(timeout = TIMEOUT)
    public void test_findPathBetween_Degen_Left() {
        assertEquals(Arrays.asList(DEGEN_LEFT_ARRAY),
                degenLeft.findPathBetween(new TestClass(19), new TestClass(1)));

        assertEquals(Arrays.asList(new TestClass[] {
            new TestClass(17), new TestClass(15), new TestClass(13), new TestClass(11),
            new TestClass(9), new TestClass(7), new TestClass(5), new TestClass(3)
        }), degenLeft.findPathBetween(new TestClass(17), new TestClass(3)));

        assertEquals(Arrays.asList(new TestClass[] {
            new TestClass(3), new TestClass(5), new TestClass(7), new TestClass(9),
            new TestClass(11), new TestClass(13), new TestClass(15), new TestClass(17),
        }), degenLeft.findPathBetween(new TestClass(3), new TestClass(17)));
    }

    @Test(timeout = TIMEOUT)
    public void test_findPathBetween_Degen_Right() {
        assertEquals(Arrays.asList(DEGEN_RIGHT_ARRAY),
                degenRight.findPathBetween(new TestClass(2), new TestClass(20)));

        assertEquals(Arrays.asList(new TestClass[] {
            new TestClass(4), new TestClass(6), new TestClass(8), new TestClass(10),
            new TestClass(12), new TestClass(14), new TestClass(16), new TestClass(18),
        }), degenRight.findPathBetween(new TestClass(4), new TestClass(18)));

        assertEquals(Arrays.asList(new TestClass[] {
            new TestClass(18), new TestClass(16), new TestClass(14), new TestClass(12),
            new TestClass(10), new TestClass(8), new TestClass(6), new TestClass(4)
        }), degenRight.findPathBetween(new TestClass(18), new TestClass(4)));
    }

    @Test(timeout = TIMEOUT)
    public void test_findPathBetween_PBBST() {
        // This is actually the one that covers all the edge cases

        // 5, 7
        assertEquals(Arrays.asList(new TestClass[] {
            new TestClass(5), new TestClass(6), new TestClass(7)
        }), pbBST.findPathBetween(new TestClass(5), new TestClass(7)));

        // 7, 5
        assertEquals(Arrays.asList(new TestClass[] {
            new TestClass(7), new TestClass(6), new TestClass(5)
        }), pbBST.findPathBetween(new TestClass(7), new TestClass(5)));

        // 6, 10
        assertEquals(Arrays.asList(new TestClass[] {
            new TestClass(6), new TestClass(4), new TestClass(8), new TestClass(12), new TestClass(10)
        }), pbBST.findPathBetween(new TestClass(6), new TestClass(10)));

        // 5, 14
        assertEquals(Arrays.asList(new TestClass[] {
            new TestClass(5), new TestClass(6),  new TestClass(4),
            new TestClass(8), new TestClass(12), new TestClass(14)
        }), pbBST.findPathBetween(new TestClass(5), new TestClass(14)));

        // 12, 3
        assertEquals(Arrays.asList(new TestClass[] {
            new TestClass(12), new TestClass(8),  new TestClass(4),
            new TestClass(2), new TestClass(3)
        }), pbBST.findPathBetween(new TestClass(12), new TestClass(3)));

        // 8, 2
        assertEquals(Arrays.asList(new TestClass[] {
            new TestClass(8), new TestClass(4), new TestClass(2)
        }), pbBST.findPathBetween(new TestClass(8), new TestClass(2)));

    }

    @Test(timeout = TIMEOUT)
    public void test_findPathBetween_Full_BST() {
        if (!UNLEASH_FULL) { return; }
        loadFullBST();

        // 8482, 7301
        assertEquals(Arrays.asList(new TestClass[] { new TestClass(8482), new TestClass(8496), new TestClass(8472), new TestClass(8412), new TestClass(8527), new TestClass(8735), new TestClass(9020), new TestClass(8328), new TestClass(7558), new TestClass(6887), new TestClass(7207), new TestClass(7349), new TestClass(7265), new TestClass(7320), new TestClass(7292), new TestClass(7301) }), fullBST.findPathBetween(new TestClass(8482), new TestClass(7301)));

        // 8997, 4515
        assertEquals(Arrays.asList(new TestClass[] { new TestClass(8997), new TestClass(8999), new TestClass(8994), new TestClass(8965), new TestClass(8881), new TestClass(8735), new TestClass(9020), new TestClass(8328), new TestClass(7558), new TestClass(6210), new TestClass(4979), new TestClass(3651), new TestClass(4415), new TestClass(4719), new TestClass(4564), new TestClass(4475), new TestClass(4518), new TestClass(4493), new TestClass(4515) }), fullBST.findPathBetween(new TestClass(8997), new TestClass(4515)));

        // 6004, 2406
        assertEquals(Arrays.asList(new TestClass[] { new TestClass(6004), new TestClass(6074), new TestClass(5941), new TestClass(5657), new TestClass(6210), new TestClass(4979), new TestClass(3651), new TestClass(2960), new TestClass(2699), new TestClass(2566), new TestClass(2502), new TestClass(2460), new TestClass(2452), new TestClass(2406) }), fullBST.findPathBetween(new TestClass(6004), new TestClass(2406)));

        // 9343, 931
        assertEquals(Arrays.asList(new TestClass[] { new TestClass(9343), new TestClass(9335), new TestClass(9352), new TestClass(9281), new TestClass(9221), new TestClass(9420), new TestClass(9020), new TestClass(8328), new TestClass(7558), new TestClass(6210), new TestClass(4979), new TestClass(2403), new TestClass(1283), new TestClass(633), new TestClass(996), new TestClass(793), new TestClass(925), new TestClass(950), new TestClass(930), new TestClass(931) }), fullBST.findPathBetween(new TestClass(9343), new TestClass(931)));

    }

    // Randomly sorted array of 100 elements in fullBST
    private final TestClass[] CONTAINED_IN_FULL_BST = { new TestClass(6537), new TestClass(2223), new TestClass(169), new TestClass(501), new TestClass(2737), new TestClass(629), new TestClass(7160), new TestClass(7157), new TestClass(1747), new TestClass(8), new TestClass(336), new TestClass(2354), new TestClass(6479), new TestClass(5928), new TestClass(5820), new TestClass(5841), new TestClass(5036), new TestClass(5964), new TestClass(5016), new TestClass(8919), new TestClass(929), new TestClass(3810), new TestClass(9684), new TestClass(3163), new TestClass(1983), new TestClass(7301), new TestClass(8472), new TestClass(2032), new TestClass(682), new TestClass(4426), new TestClass(8805), new TestClass(5676), new TestClass(8166), new TestClass(7486), new TestClass(925), new TestClass(4844), new TestClass(6010), new TestClass(754), new TestClass(9548), new TestClass(9427), new TestClass(4069), new TestClass(4403), new TestClass(1969), new TestClass(3583), new TestClass(5995), new TestClass(4838), new TestClass(5941), new TestClass(8835), new TestClass(1498), new TestClass(2038), new TestClass(9335), new TestClass(2307), new TestClass(6224), new TestClass(9918), new TestClass(2974), new TestClass(4237), new TestClass(4493), new TestClass(2673), new TestClass(487), new TestClass(430), new TestClass(6676), new TestClass(6783), new TestClass(3923), new TestClass(6701), new TestClass(9700), new TestClass(58), new TestClass(9229), new TestClass(1757), new TestClass(5935), new TestClass(9021), new TestClass(5781), new TestClass(6544), new TestClass(6759), new TestClass(2283), new TestClass(4142), new TestClass(711), new TestClass(9904), new TestClass(8874), new TestClass(4438), new TestClass(5160), new TestClass(1338), new TestClass(2150), new TestClass(7327), new TestClass(2266), new TestClass(8176), new TestClass(7671), new TestClass(6164), new TestClass(7197), new TestClass(5188), new TestClass(9533), new TestClass(8968), new TestClass(6969), new TestClass(3364), new TestClass(7672), new TestClass(7652), new TestClass(6738), new TestClass(1705), new TestClass(8893), new TestClass(4259), new TestClass(4627) };

    // Randomly sorted array of 100 elements not in fullBST
    private final TestClass[] NOT_CONTAINED_IN_FULL_BST = { new TestClass(746), new TestClass(1329), new TestClass(1936), new TestClass(2151), new TestClass(9416), new TestClass(4923), new TestClass(4818), new TestClass(2357), new TestClass(2568), new TestClass(6151), new TestClass(623), new TestClass(4370), new TestClass(212), new TestClass(1249), new TestClass(3591), new TestClass(2178), new TestClass(6904), new TestClass(4868), new TestClass(1124), new TestClass(4951), new TestClass(8491), new TestClass(9232), new TestClass(8645), new TestClass(8863), new TestClass(1387), new TestClass(16), new TestClass(5201), new TestClass(3242), new TestClass(2888), new TestClass(4576), new TestClass(5853), new TestClass(7163), new TestClass(5340), new TestClass(8768), new TestClass(907), new TestClass(2378), new TestClass(8070), new TestClass(7366), new TestClass(678), new TestClass(9330), new TestClass(2682), new TestClass(3706), new TestClass(603), new TestClass(2043), new TestClass(6215), new TestClass(6826), new TestClass(3467), new TestClass(252), new TestClass(884), new TestClass(7543), new TestClass(6454), new TestClass(6917), new TestClass(9535), new TestClass(4417), new TestClass(4494), new TestClass(2441), new TestClass(1960), new TestClass(6911), new TestClass(2391), new TestClass(9092), new TestClass(2248), new TestClass(6785), new TestClass(6607), new TestClass(1213), new TestClass(512), new TestClass(1308), new TestClass(5249), new TestClass(9953), new TestClass(2486), new TestClass(3468), new TestClass(5899), new TestClass(339), new TestClass(2219), new TestClass(8333), new TestClass(6068), new TestClass(9575), new TestClass(6834), new TestClass(4215), new TestClass(5218), new TestClass(5284), new TestClass(1118), new TestClass(7940), new TestClass(4104), new TestClass(4984), new TestClass(4511), new TestClass(9278), new TestClass(4095), new TestClass(9249), new TestClass(915), new TestClass(7886), new TestClass(4179), new TestClass(9396), new TestClass(3728), new TestClass(8469), new TestClass(2874), new TestClass(3774), new TestClass(8310), new TestClass(3916), new TestClass(722), new TestClass(4917) };

    // Degenerate tree, left
    private final TestClass[] DEGEN_LEFT_ARRAY = { new TestClass(19), new TestClass(17), new TestClass(15), new TestClass(13), new TestClass(11), new TestClass(9), new TestClass(7), new TestClass(5), new TestClass(3), new TestClass(1) };

    // Degenerate tree, right
    private final TestClass[] DEGEN_RIGHT_ARRAY = { new TestClass(2), new TestClass(4), new TestClass(6), new TestClass(8), new TestClass(10), new TestClass(12), new TestClass(14), new TestClass(16), new TestClass(18), new TestClass(20) };

    // Perfectly balanced binary search tree
    // This array is a level order traversal
    private final TestClass[] PBBST_ARRAY = new TestClass[] { new TestClass(8), new TestClass(4), new TestClass(12), new TestClass(2), new TestClass(6), new TestClass(10), new TestClass(14), new TestClass(1), new TestClass(3), new TestClass(5), new TestClass(7), new TestClass(9), new TestClass(11), new TestClass(13), new TestClass(15) };

    // Large test case with mostly balanced tree
    // (balanced as red-black tree)
    // This array is a preorder traversal
    private final TestClass[] FULL_BST_ARRAY = new TestClass[] {new TestClass(2403), new TestClass(1283), new TestClass(633), new TestClass(362), new TestClass(190), new TestClass(104), new TestClass(45), new TestClass(17), new TestClass(8), new TestClass(20), new TestClass(59), new TestClass(58), new TestClass(85), new TestClass(169), new TestClass(116), new TestClass(106), new TestClass(166), new TestClass(176), new TestClass(175), new TestClass(183), new TestClass(248), new TestClass(228), new TestClass(202), new TestClass(195), new TestClass(206), new TestClass(235), new TestClass(231), new TestClass(240), new TestClass(310), new TestClass(298), new TestClass(271), new TestClass(302), new TestClass(337), new TestClass(336), new TestClass(348), new TestClass(501), new TestClass(435), new TestClass(400), new TestClass(376), new TestClass(365), new TestClass(383), new TestClass(427), new TestClass(415), new TestClass(430), new TestClass(461), new TestClass(452), new TestClass(444), new TestClass(460), new TestClass(487), new TestClass(477), new TestClass(493), new TestClass(573), new TestClass(538), new TestClass(523), new TestClass(504), new TestClass(534), new TestClass(554), new TestClass(547), new TestClass(556), new TestClass(609), new TestClass(599), new TestClass(592), new TestClass(600), new TestClass(627), new TestClass(615), new TestClass(629), new TestClass(996), new TestClass(793), new TestClass(725), new TestClass(702), new TestClass(682), new TestClass(652), new TestClass(688), new TestClass(713), new TestClass(711), new TestClass(720), new TestClass(754), new TestClass(733), new TestClass(726), new TestClass(740), new TestClass(779), new TestClass(755), new TestClass(783), new TestClass(925), new TestClass(867), new TestClass(835), new TestClass(797), new TestClass(843), new TestClass(918), new TestClass(880), new TestClass(919), new TestClass(950), new TestClass(930), new TestClass(929), new TestClass(931), new TestClass(985), new TestClass(975), new TestClass(986), new TestClass(1112), new TestClass(1044), new TestClass(1024), new TestClass(1013), new TestClass(1012), new TestClass(1022), new TestClass(1033), new TestClass(1028), new TestClass(1040), new TestClass(1091), new TestClass(1078), new TestClass(1065), new TestClass(1085), new TestClass(1106), new TestClass(1104), new TestClass(1108), new TestClass(1183), new TestClass(1137), new TestClass(1123), new TestClass(1122), new TestClass(1133), new TestClass(1172), new TestClass(1144), new TestClass(1179), new TestClass(1239), new TestClass(1189), new TestClass(1188), new TestClass(1209), new TestClass(1258), new TestClass(1251), new TestClass(1270), new TestClass(1959), new TestClass(1648), new TestClass(1498), new TestClass(1389), new TestClass(1337), new TestClass(1317), new TestClass(1309), new TestClass(1326), new TestClass(1362), new TestClass(1338), new TestClass(1386), new TestClass(1433), new TestClass(1416), new TestClass(1412), new TestClass(1421), new TestClass(1482), new TestClass(1475), new TestClass(1497), new TestClass(1601), new TestClass(1567), new TestClass(1509), new TestClass(1508), new TestClass(1559), new TestClass(1577), new TestClass(1575), new TestClass(1597), new TestClass(1630), new TestClass(1621), new TestClass(1602), new TestClass(1624), new TestClass(1638), new TestClass(1633), new TestClass(1641), new TestClass(1770), new TestClass(1703), new TestClass(1691), new TestClass(1682), new TestClass(1678), new TestClass(1687), new TestClass(1701), new TestClass(1697), new TestClass(1702), new TestClass(1726), new TestClass(1716), new TestClass(1705), new TestClass(1722), new TestClass(1747), new TestClass(1746), new TestClass(1757), new TestClass(1858), new TestClass(1816), new TestClass(1784), new TestClass(1777), new TestClass(1810), new TestClass(1828), new TestClass(1824), new TestClass(1857), new TestClass(1897), new TestClass(1878), new TestClass(1871), new TestClass(1881), new TestClass(1930), new TestClass(1903), new TestClass(1956), new TestClass(2163), new TestClass(2052), new TestClass(1993), new TestClass(1975), new TestClass(1969), new TestClass(1962), new TestClass(1971), new TestClass(1981), new TestClass(1979), new TestClass(1983), new TestClass(2024), new TestClass(1997), new TestClass(1995), new TestClass(2011), new TestClass(2038), new TestClass(2032), new TestClass(2047), new TestClass(2098), new TestClass(2060), new TestClass(2056), new TestClass(2054), new TestClass(2057), new TestClass(2091), new TestClass(2087), new TestClass(2093), new TestClass(2128), new TestClass(2107), new TestClass(2104), new TestClass(2115), new TestClass(2150), new TestClass(2131), new TestClass(2152), new TestClass(2273), new TestClass(2211), new TestClass(2175), new TestClass(2170), new TestClass(2168), new TestClass(2171), new TestClass(2203), new TestClass(2200), new TestClass(2206), new TestClass(2250), new TestClass(2223), new TestClass(2214), new TestClass(2224), new TestClass(2266), new TestClass(2259), new TestClass(2267), new TestClass(2352), new TestClass(2307), new TestClass(2298), new TestClass(2283), new TestClass(2305), new TestClass(2340), new TestClass(2324), new TestClass(2344), new TestClass(2370), new TestClass(2361), new TestClass(2354), new TestClass(2366), new TestClass(2389), new TestClass(2384), new TestClass(2400), new TestClass(4979), new TestClass(3651), new TestClass(2960), new TestClass(2699), new TestClass(2566), new TestClass(2502), new TestClass(2460), new TestClass(2452), new TestClass(2406), new TestClass(2454), new TestClass(2478), new TestClass(2468), new TestClass(2491), new TestClass(2548), new TestClass(2526), new TestClass(2523), new TestClass(2528), new TestClass(2561), new TestClass(2559), new TestClass(2563), new TestClass(2645), new TestClass(2607), new TestClass(2599), new TestClass(2585), new TestClass(2602), new TestClass(2641), new TestClass(2612), new TestClass(2644), new TestClass(2690), new TestClass(2673), new TestClass(2671), new TestClass(2675), new TestClass(2694), new TestClass(2692), new TestClass(2698), new TestClass(2801), new TestClass(2744), new TestClass(2721), new TestClass(2713), new TestClass(2709), new TestClass(2717), new TestClass(2737), new TestClass(2732), new TestClass(2739), new TestClass(2771), new TestClass(2763), new TestClass(2757), new TestClass(2765), new TestClass(2778), new TestClass(2777), new TestClass(2794), new TestClass(2850), new TestClass(2841), new TestClass(2837), new TestClass(2831), new TestClass(2840), new TestClass(2848), new TestClass(2847), new TestClass(2849), new TestClass(2884), new TestClass(2862), new TestClass(2853), new TestClass(2877), new TestClass(2939), new TestClass(2890), new TestClass(2953), new TestClass(3367), new TestClass(3142), new TestClass(3044), new TestClass(2979), new TestClass(2970), new TestClass(2969), new TestClass(2974), new TestClass(3002), new TestClass(3001), new TestClass(3040), new TestClass(3094), new TestClass(3061), new TestClass(3050), new TestClass(3067), new TestClass(3116), new TestClass(3115), new TestClass(3138), new TestClass(3269), new TestClass(3208), new TestClass(3163), new TestClass(3151), new TestClass(3178), new TestClass(3233), new TestClass(3220), new TestClass(3259), new TestClass(3341), new TestClass(3303), new TestClass(3292), new TestClass(3304), new TestClass(3364), new TestClass(3362), new TestClass(3365), new TestClass(3531), new TestClass(3429), new TestClass(3391), new TestClass(3387), new TestClass(3372), new TestClass(3389), new TestClass(3410), new TestClass(3400), new TestClass(3414), new TestClass(3486), new TestClass(3455), new TestClass(3444), new TestClass(3466), new TestClass(3489), new TestClass(3487), new TestClass(3508), new TestClass(3583), new TestClass(3576), new TestClass(3556), new TestClass(3554), new TestClass(3565), new TestClass(3579), new TestClass(3578), new TestClass(3582), new TestClass(3630), new TestClass(3601), new TestClass(3597), new TestClass(3615), new TestClass(3639), new TestClass(3636), new TestClass(3643), new TestClass(4415), new TestClass(3991), new TestClass(3815), new TestClass(3735), new TestClass(3676), new TestClass(3668), new TestClass(3666), new TestClass(3671), new TestClass(3702), new TestClass(3681), new TestClass(3732), new TestClass(3801), new TestClass(3766), new TestClass(3765), new TestClass(3777), new TestClass(3810), new TestClass(3804), new TestClass(3812), new TestClass(3912), new TestClass(3843), new TestClass(3837), new TestClass(3833), new TestClass(3839), new TestClass(3892), new TestClass(3861), new TestClass(3893), new TestClass(3936), new TestClass(3923), new TestClass(3918), new TestClass(3927), new TestClass(3988), new TestClass(3968), new TestClass(3990), new TestClass(4248), new TestClass(4089), new TestClass(4062), new TestClass(4041), new TestClass(4006), new TestClass(4060), new TestClass(4074), new TestClass(4069), new TestClass(4083), new TestClass(4192), new TestClass(4115), new TestClass(4114), new TestClass(4142), new TestClass(4228), new TestClass(4227), new TestClass(4237), new TestClass(4317), new TestClass(4281), new TestClass(4265), new TestClass(4259), new TestClass(4268), new TestClass(4295), new TestClass(4286), new TestClass(4309), new TestClass(4391), new TestClass(4387), new TestClass(4333), new TestClass(4388), new TestClass(4404), new TestClass(4403), new TestClass(4409), new TestClass(4719), new TestClass(4564), new TestClass(4475), new TestClass(4438), new TestClass(4420), new TestClass(4418), new TestClass(4426), new TestClass(4456), new TestClass(4447), new TestClass(4457), new TestClass(4518), new TestClass(4493), new TestClass(4478), new TestClass(4515), new TestClass(4539), new TestClass(4521), new TestClass(4551), new TestClass(4627), new TestClass(4598), new TestClass(4593), new TestClass(4566), new TestClass(4595), new TestClass(4607), new TestClass(4600), new TestClass(4624), new TestClass(4674), new TestClass(4665), new TestClass(4654), new TestClass(4666), new TestClass(4687), new TestClass(4676), new TestClass(4698), new TestClass(4804), new TestClass(4763), new TestClass(4736), new TestClass(4730), new TestClass(4726), new TestClass(4732), new TestClass(4743), new TestClass(4737), new TestClass(4754), new TestClass(4787), new TestClass(4784), new TestClass(4774), new TestClass(4785), new TestClass(4792), new TestClass(4789), new TestClass(4801), new TestClass(4865), new TestClass(4838), new TestClass(4829), new TestClass(4815), new TestClass(4833), new TestClass(4844), new TestClass(4839), new TestClass(4860), new TestClass(4950), new TestClass(4912), new TestClass(4904), new TestClass(4938), new TestClass(4962), new TestClass(4955), new TestClass(4964), new TestClass(6210), new TestClass(5657), new TestClass(5325), new TestClass(5206), new TestClass(5081), new TestClass(5018), new TestClass(5004), new TestClass(4994), new TestClass(5016), new TestClass(5038), new TestClass(5036), new TestClass(5059), new TestClass(5188), new TestClass(5116), new TestClass(5086), new TestClass(5160), new TestClass(5198), new TestClass(5194), new TestClass(5200), new TestClass(5275), new TestClass(5223), new TestClass(5215), new TestClass(5211), new TestClass(5220), new TestClass(5257), new TestClass(5242), new TestClass(5263), new TestClass(5305), new TestClass(5289), new TestClass(5277), new TestClass(5294), new TestClass(5317), new TestClass(5315), new TestClass(5318), new TestClass(5465), new TestClass(5396), new TestClass(5379), new TestClass(5354), new TestClass(5342), new TestClass(5367), new TestClass(5392), new TestClass(5383), new TestClass(5393), new TestClass(5433), new TestClass(5418), new TestClass(5413), new TestClass(5419), new TestClass(5447), new TestClass(5446), new TestClass(5462), new TestClass(5569), new TestClass(5514), new TestClass(5490), new TestClass(5481), new TestClass(5493), new TestClass(5531), new TestClass(5527), new TestClass(5541), new TestClass(5587), new TestClass(5577), new TestClass(5570), new TestClass(5583), new TestClass(5627), new TestClass(5589), new TestClass(5638), new TestClass(5941), new TestClass(5820), new TestClass(5723), new TestClass(5676), new TestClass(5670), new TestClass(5666), new TestClass(5671), new TestClass(5703), new TestClass(5682), new TestClass(5707), new TestClass(5767), new TestClass(5742), new TestClass(5727), new TestClass(5743), new TestClass(5783), new TestClass(5781), new TestClass(5801), new TestClass(5897), new TestClass(5848), new TestClass(5841), new TestClass(5827), new TestClass(5846), new TestClass(5892), new TestClass(5887), new TestClass(5896), new TestClass(5923), new TestClass(5903), new TestClass(5902), new TestClass(5916), new TestClass(5935), new TestClass(5928), new TestClass(5939), new TestClass(6074), new TestClass(6004), new TestClass(5984), new TestClass(5954), new TestClass(5953), new TestClass(5964), new TestClass(5995), new TestClass(5989), new TestClass(5997), new TestClass(6047), new TestClass(6010), new TestClass(6008), new TestClass(6041), new TestClass(6063), new TestClass(6060), new TestClass(6065), new TestClass(6140), new TestClass(6096), new TestClass(6088), new TestClass(6083), new TestClass(6090), new TestClass(6131), new TestClass(6110), new TestClass(6132), new TestClass(6179), new TestClass(6164), new TestClass(6144), new TestClass(6168), new TestClass(6190), new TestClass(6184), new TestClass(6205), new TestClass(7558), new TestClass(6887), new TestClass(6544), new TestClass(6383), new TestClass(6271), new TestClass(6241), new TestClass(6224), new TestClass(6212), new TestClass(6237), new TestClass(6250), new TestClass(6246), new TestClass(6258), new TestClass(6296), new TestClass(6279), new TestClass(6277), new TestClass(6287), new TestClass(6317), new TestClass(6310), new TestClass(6364), new TestClass(6479), new TestClass(6418), new TestClass(6405), new TestClass(6398), new TestClass(6414), new TestClass(6443), new TestClass(6439), new TestClass(6450), new TestClass(6532), new TestClass(6508), new TestClass(6504), new TestClass(6512), new TestClass(6538), new TestClass(6537), new TestClass(6543), new TestClass(6701), new TestClass(6618), new TestClass(6576), new TestClass(6561), new TestClass(6545), new TestClass(6564), new TestClass(6595), new TestClass(6578), new TestClass(6612), new TestClass(6675), new TestClass(6658), new TestClass(6640), new TestClass(6667), new TestClass(6692), new TestClass(6676), new TestClass(6696), new TestClass(6783), new TestClass(6759), new TestClass(6738), new TestClass(6716), new TestClass(6756), new TestClass(6774), new TestClass(6773), new TestClass(6781), new TestClass(6833), new TestClass(6814), new TestClass(6793), new TestClass(6831), new TestClass(6847), new TestClass(6843), new TestClass(6861), new TestClass(7207), new TestClass(7059), new TestClass(6969), new TestClass(6940), new TestClass(6902), new TestClass(6896), new TestClass(6931), new TestClass(6951), new TestClass(6945), new TestClass(6961), new TestClass(7031), new TestClass(6991), new TestClass(6982), new TestClass(7007), new TestClass(7045), new TestClass(7036), new TestClass(7046), new TestClass(7157), new TestClass(7136), new TestClass(7110), new TestClass(7078), new TestClass(7132), new TestClass(7142), new TestClass(7140), new TestClass(7150), new TestClass(7187), new TestClass(7170), new TestClass(7160), new TestClass(7180), new TestClass(7192), new TestClass(7190), new TestClass(7197), new TestClass(7349), new TestClass(7265), new TestClass(7243), new TestClass(7232), new TestClass(7212), new TestClass(7236), new TestClass(7254), new TestClass(7246), new TestClass(7258), new TestClass(7320), new TestClass(7292), new TestClass(7283), new TestClass(7301), new TestClass(7338), new TestClass(7327), new TestClass(7346), new TestClass(7464), new TestClass(7429), new TestClass(7371), new TestClass(7357), new TestClass(7373), new TestClass(7440), new TestClass(7438), new TestClass(7453), new TestClass(7486), new TestClass(7478), new TestClass(7474), new TestClass(7481), new TestClass(7504), new TestClass(7496), new TestClass(7554), new TestClass(8328), new TestClass(7954), new TestClass(7795), new TestClass(7671), new TestClass(7612), new TestClass(7579), new TestClass(7575), new TestClass(7609), new TestClass(7642), new TestClass(7633), new TestClass(7652), new TestClass(7711), new TestClass(7686), new TestClass(7672), new TestClass(7699), new TestClass(7752), new TestClass(7747), new TestClass(7780), new TestClass(7884), new TestClass(7824), new TestClass(7803), new TestClass(7801), new TestClass(7808), new TestClass(7849), new TestClass(7844), new TestClass(7877), new TestClass(7910), new TestClass(7897), new TestClass(7891), new TestClass(7898), new TestClass(7925), new TestClass(7924), new TestClass(7953), new TestClass(8176), new TestClass(8023), new TestClass(7986), new TestClass(7966), new TestClass(7958), new TestClass(7980), new TestClass(8013), new TestClass(8005), new TestClass(8017), new TestClass(8126), new TestClass(8068), new TestClass(8045), new TestClass(8116), new TestClass(8166), new TestClass(8153), new TestClass(8171), new TestClass(8257), new TestClass(8211), new TestClass(8204), new TestClass(8191), new TestClass(8205), new TestClass(8226), new TestClass(8217), new TestClass(8244), new TestClass(8297), new TestClass(8277), new TestClass(8276), new TestClass(8281), new TestClass(8315), new TestClass(8314), new TestClass(8325), new TestClass(9020), new TestClass(8735), new TestClass(8527), new TestClass(8412), new TestClass(8354), new TestClass(8336), new TestClass(8335), new TestClass(8343), new TestClass(8366), new TestClass(8363), new TestClass(8371), new TestClass(8472), new TestClass(8439), new TestClass(8436), new TestClass(8450), new TestClass(8496), new TestClass(8482), new TestClass(8520), new TestClass(8625), new TestClass(8591), new TestClass(8548), new TestClass(8535), new TestClass(8580), new TestClass(8606), new TestClass(8594), new TestClass(8623), new TestClass(8666), new TestClass(8639), new TestClass(8631), new TestClass(8644), new TestClass(8691), new TestClass(8667), new TestClass(8725), new TestClass(8881), new TestClass(8805), new TestClass(8766), new TestClass(8755), new TestClass(8738), new TestClass(8761), new TestClass(8775), new TestClass(8774), new TestClass(8783), new TestClass(8859), new TestClass(8839), new TestClass(8835), new TestClass(8857), new TestClass(8874), new TestClass(8871), new TestClass(8878), new TestClass(8965), new TestClass(8918), new TestClass(8893), new TestClass(8884), new TestClass(8900), new TestClass(8931), new TestClass(8919), new TestClass(8957), new TestClass(8994), new TestClass(8976), new TestClass(8968), new TestClass(8977), new TestClass(8999), new TestClass(8997), new TestClass(9003), new TestClass(9420), new TestClass(9221), new TestClass(9128), new TestClass(9036), new TestClass(9027), new TestClass(9021), new TestClass(9030), new TestClass(9076), new TestClass(9043), new TestClass(9114), new TestClass(9171), new TestClass(9130), new TestClass(9129), new TestClass(9165), new TestClass(9214), new TestClass(9186), new TestClass(9220), new TestClass(9281), new TestClass(9252), new TestClass(9236), new TestClass(9229), new TestClass(9247), new TestClass(9277), new TestClass(9269), new TestClass(9279), new TestClass(9352), new TestClass(9335), new TestClass(9305), new TestClass(9343), new TestClass(9386), new TestClass(9355), new TestClass(9403), new TestClass(9548), new TestClass(9476), new TestClass(9455), new TestClass(9431), new TestClass(9427), new TestClass(9449), new TestClass(9463), new TestClass(9459), new TestClass(9468), new TestClass(9502), new TestClass(9489), new TestClass(9488), new TestClass(9495), new TestClass(9533), new TestClass(9510), new TestClass(9538), new TestClass(9664), new TestClass(9602), new TestClass(9566), new TestClass(9550), new TestClass(9549), new TestClass(9553), new TestClass(9574), new TestClass(9570), new TestClass(9578), new TestClass(9652), new TestClass(9645), new TestClass(9626), new TestClass(9651), new TestClass(9655), new TestClass(9653), new TestClass(9661), new TestClass(9770), new TestClass(9702), new TestClass(9686), new TestClass(9682), new TestClass(9681), new TestClass(9684), new TestClass(9695), new TestClass(9688), new TestClass(9700), new TestClass(9723), new TestClass(9711), new TestClass(9706), new TestClass(9714), new TestClass(9740), new TestClass(9739), new TestClass(9767), new TestClass(9830), new TestClass(9792), new TestClass(9780), new TestClass(9777), new TestClass(9789), new TestClass(9817), new TestClass(9794), new TestClass(9825), new TestClass(9872), new TestClass(9840), new TestClass(9833), new TestClass(9863), new TestClass(9913), new TestClass(9904), new TestClass(9889), new TestClass(9906), new TestClass(9951), new TestClass(9928), new TestClass(9918), new TestClass(9950), new TestClass(9980), new TestClass(9969), new TestClass(9982), new TestClass(9997)};

}
