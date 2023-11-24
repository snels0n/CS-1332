import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * CS 1332 HW 4
 * ArcWand tests for Binary Search Tree
 *
 * Collaborators: Raymond
 *
 * @author Robert Zhu
 * @version 1.3
 */
public class ArcWandMinHeapTests {
    private static final boolean UNLEASH_FULL = true;

    @Test(timeout = TIMEOUT)
    public void test_note() {
        assertTrue("Once you're feeling confident about your code, set UNLEASH_FULL to true!\nalso\nhttps://venmo.com/u/arcwand\njkjk\nunless ...?\n;D", UNLEASH_FULL);
    }

    private static final int TIMEOUT = 200;
    private static final int LONGTIMEOUT = TIMEOUT * 5;

    // how many layers of the heap to print when things go wrong
    private static final int PRINTLAYERS = 5;
    private static final int PRINTSIZE = 1 << PRINTLAYERS;

    private MinHeap<Integer> heap;

    /**
     * Get the backing array without errors
     *
     * @return a normal array without generics weirdness
     */
    private Comparable<Integer>[] heapArray() {
        return (Comparable<Integer>[]) (heap.getBackingArray());
    }

    /**
     * Nicer syntax for creating an ArrayList
     * @param arr contents of the to-be ArrayList
     */
    private ArrayList<Integer> with(Integer... arr) {
        return new ArrayList<Integer>(Arrays.asList(arr));
    }

    /**
     * Creates a range of numbers in an ArrayList
     *
     * @param start the first element
     * @param end the last element
     * @param step the step size
     * @return a sequence of numbers
     */
    private ArrayList<Integer> range(int start, int end, int step) {
        ArrayList<Integer> arr = new ArrayList<>();
        if (step > 0) {
            for (int i = start; i < end; i += step) {
                arr.add(i);
            }
        } else if(step < 0) {
            for (int i = start; i > end; i += step) {
                arr.add(i);
            }
        }
        return arr;
    }
    private ArrayList<Integer> range(int max) { return range(0, max, 1); }

    /**
     * Nicer syntax for constructing a MinHeap<Integer>.
     * Sets the private field appropriately.
     *
     * @param arr what to add to the heap
     */
    private void make(ArrayList<Integer> arr) {
        heap = new MinHeap<>(arr);
    }
    private void make(Integer... arr) {
        make(new ArrayList<Integer>(Arrays.asList(arr)));
    }
    private void make() {
        heap = new MinHeap<>();
    }

    /**
     * Nicer syntax to add many elements at once.
     *
     * @param arr the elements to add in order
     */
    private void multiAdd(List<Integer> arr) {
        for (Integer i : arr) {
            heap.add(i);
        }
    }
    private void multiAdd(Integer... arr) { multiAdd(Arrays.asList(arr)); }

    /**
     * Helper method to assert equality very strictly.
     *
     * @param arr what the backing array of the heap should contain
     */
    private void definitelyEquals(Integer... arr) {
        // Count the number of non-null elements
        int count = 0;
        for (Integer i : arr) {
            if (i == null) break;
            count++;
        }
        assertEquals(heap.size(), count);

        assertNull(heapArray()[0]);
        for (int i = 0; i < count; i++) {
            assertEquals("Differed at index " + i + ". Expected <" + arr[i] +"> but was <" + heapArray()[i+1] + ">.",
                    arr[i], heapArray()[i+1]);
        }

        for (int i = count+1; i < heapArray().length; i++) {
            assertNull(heapArray()[i]);
        }
    }
    private void definitelyEquals(ArrayList<Integer> arr) { definitelyEquals(arr.toArray(new Integer[0])); }

    /**
     * Makes a randomized ArrayList of a given size
     *
     * @param rand a Random instance
     * @param len the number of elements
     * @return the randomized ArrayList
     */
    private ArrayList<Integer> randList(Random rand, int len) {
        ArrayList<Integer> shuf = new ArrayList<>();
        for (int i = 1; i <= len; i++) { shuf.add(i); }
        Collections.shuffle(shuf);
        return shuf;
    }

    /**
     * Check that any given array represents a valid heap.
     *
     * @param arr the array to check
     * @return whether or not it is a valid heap
     */
    private boolean isHeap(Comparable<Integer>[] arr, ArrayList<Integer> out) {
        int count = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == null) { break; }
            count++;
        }

        for (int i = 2; i < count; i++) {
            if (arr[i].compareTo((Integer) arr[i/2]) < 0) {
                out.add(i);
            }
        }
        return out.size() == 0;
    }

    /**
     * Assert that the order of elements being removed is strictly increasing.
     */
    private void assertRemoveOrder() {
        Integer prev, curr = null;
        int len = heap.size();
        if (len > 0) {
            curr = heap.remove();
        }
        for (int i = 1; i < len; i++) {
            prev = curr;
            curr = heap.remove();
            assertFalse(prev + " is greater than " + curr + " but was removed first.", prev > curr);
        }
        definitelyEquals();
    }

    /**
     * Helper function to find the minimum element in an array.
     *
     * @param arr the array to search
     * @return the minimum element in it
     */
    private int findMin(Comparable<Integer>[] arr) {
        Integer min = Integer.MAX_VALUE;
        for (int i = 1; i <= heap.size(); i++) {
            if (heapArray()[i].compareTo(min) < 0) {
                min = (Integer) heapArray()[i];
            }
        }
        return min;
    }

    /**
     * Asserts that getMin() functions correctly for some particular range.
     *
     * @param range the range to check through
     */
    private void assertMinOverRange(ArrayList<Integer> range) {
        // Add the elements, making sure getMin() is always correct
        for (Integer i : range) {
            heap.add(i);
            int min = findMin(heapArray());
            if (heap.getMin() != min) {
                if (heap.size() <= PRINTSIZE) {
                    printHeap();
                }
                assertTrue(min + " is the smallest element, but getMin returned " + heap.getMin(), false);
            }
        }

        // Remove the elements, making sure getMin() is always correct
        assertRemoveOrder();
    }

    /**
     * Print out a representation of the heap for debugging purposes.
     */
    private void printHeap() {
        System.out.println();
        System.out.println("Heap: ");
        for (int i = 1; i <= heap.size(); i++) {
            System.out.print(heapArray()[i] + " ");
            int l = i+1;
            while(l > 1) {
                if (l % 2 == 1) break;
                l >>= 1;
                if (l == 1) l = -1;
            }
            if (l == -1) { System.out.println(); }
        }
        System.out.println();
    }

    /**
     * Debugging print for when a test fails
     *
     * @param method name of the test that failed
     * @param diff the difference list
     */
    private void printDiff(String method, ArrayList<Integer> diff) {
        System.out.printf("%s failed!\n", method);

        if (heap.size() <= PRINTSIZE) {
            printHeap();
        }

        for (Integer i : diff) {
            System.out.printf("Index %d: %d, parent at %d: %d%n", i, heapArray()[i], i/2, heapArray()[i/2]);
        }
    }

    /**
     * Print the array without formatting for testing purposes.
     */
    private void printHeapAsArray() {
        System.out.println();
        System.out.println("Heap: ");
        System.out.print("(");
        for (int i = 1; i <= heap.size(); i++) {
            System.out.print(heapArray()[i]);
            if (i != heap.size()) {
                System.out.print(", ");
            }
        }
        System.out.print(")");
        System.out.println();
    }

    /**
     * Find all the permutations of some length.
     *
     * @param len the length of each permutation
     * @return a List of ArrayLists containing the permutations
     */
    private List<ArrayList<Integer>> permutate(int len) {
        List<ArrayList<Integer>> result = new ArrayList<>();
        List<Integer> currentPermutation = new ArrayList<>();
        boolean[] used = new boolean[len];
        backtrack(result, currentPermutation, used);
        return result;
    }

    private void backtrack(List<ArrayList<Integer>> result, List<Integer> currentPermutation, boolean[] used) {
        if (currentPermutation.size() == used.length) {
            result.add(new ArrayList<>(currentPermutation));
            return;
        }

        for (int i = 0; i < used.length; i++) {
            if (!used[i]) {
                currentPermutation.add(i + 1); // Assuming you want permutations of integers 1 to len
                used[i] = true;
                backtrack(result, currentPermutation, used);
                used[i] = false;
                currentPermutation.remove(currentPermutation.size() - 1);
            }
        }
    }

    /**
     * =========
     * | Tests |
     * =========
     */

    @Before
    public void setup() {
        make();
    }

    @Test(timeout = TIMEOUT)
    public void test_Empty_Constructor() {
        assertEquals(0, heap.size());
        assertEquals(13, heapArray().length);
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor_allows_Empty_ArrayList() {
        make(with());
        assertEquals(0, heap.size());
        assertEquals(2*0 + 1, heapArray().length);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_Constructor_IllegalArgumentException_with_Null_ArrayList() {
        heap = new MinHeap<>(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_Constructor_IllegalArgument_when_ArrayList_has_null() {
        make((Integer) null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_Constructor_IllegalArgument_when_ArrayList_has_null_at_start() {
        make((Integer) null, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_Constructor_IllegalArgument_when_ArrayList_has_null_in_middle() {
        make(0, 1, 2, 3, 4, (Integer) null, 6, 7, 8, 9);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_Constructor_IllegalArgument_when_ArrayList_has_null_in_end() {
        make(0, 1, 2, 3, 4, 5, 6, 7, 8, (Integer) null);
    }

    @Test(timeout = TIMEOUT, expected = ClassCastException.class)
    public void test_Constructor_ClassCastException_with_LinkedList() {
        List<Integer> list = new LinkedList<Integer>(with(1, 2, 3, 4, 5));
        heap = new MinHeap<Integer>((ArrayList<Integer>) (list));
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor_Initial_Capacity_matches_ArrayList() {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arr.add(i);
            make(arr);
            assertEquals(arr.size(), heap.size());
            assertEquals(2 * (i+1) + 1, heapArray().length);
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor_adds_One_Element() {
        make(72);
        definitelyEquals(72);
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor_adds_7_elements_from_Sorted() {
        make(range(7));
        definitelyEquals(range(7));
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor_adds_7_elements_from_Reverse_Sorted() {
        make(range(7, 0, -1));
        definitelyEquals(1, 3, 2, 4, 6, 7, 5);
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor_adds_7_elements_1() {
        make(1, 2, 3, 4, 7, 6, 5);
        definitelyEquals(1, 2, 3, 4, 7, 6, 5);
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor_adds_7_elements_2() {
        make(5, 4, 7, 6, 3, 1, 2);
        definitelyEquals(1, 3, 2, 6, 4, 7, 5);
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor_adds_14_elements_from_Sorted() {
        make(range(14));
        definitelyEquals(range(14));
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor_adds_14_elements_from_Reverse_Sorted() {
        make(range(14, 0, -1));
        definitelyEquals(1, 4, 2, 6, 5, 3, 8, 7, 11, 13, 10, 14, 9, 12);
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor_adds_14_elements_1() {
        make(4, 3, 2, 12, 5, 13, 9, 14, 6, 11, 7, 8, 10, 1);
        definitelyEquals(1, 3, 2, 6, 5, 8, 4, 14, 12, 11, 7, 13, 10, 9);
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor_adds_14_elements_2() {
        make(12, 4, 11, 2, 3, 5, 8, 10, 14, 13, 9, 1, 7, 6);
        definitelyEquals(1, 2, 5, 4, 3, 7, 6, 10, 14, 13, 9, 11, 12, 8);
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor_Random_Small() {
        Random rand = new Random(1);

        // Test five times
        for (int i = 0; i < 5; i++) {
            // Make a random heap of size 20
            make(randList(rand, 30));

            // Check that it is a heap
            ArrayList<Integer> diff = new ArrayList<>();
            if (!isHeap(heapArray(), diff)) {
                printDiff("test_Constructor_Random_Small trial " + i, diff);
                assertTrue("Not a heap!", false);
            }
        }
    }

    @Test(timeout = LONGTIMEOUT)
    public void test_Constructor_adds_9_elements_Full() {
		if (!UNLEASH_FULL) { return; }
        for (ArrayList<Integer> p : permutate(9)) {
            make(p);

            // Check that it is a heap
            ArrayList<Integer> diff = new ArrayList<>();
            if (!isHeap(heapArray(), diff)) {
                printDiff("test_Constructor_adds_9_elements_Full", diff);
                assertTrue("Not a heap!", false);
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor_adds_1023_elements_from_Sorted_Full() {
		if (!UNLEASH_FULL) { return; }
        ArrayList<Integer> r = range(1023);
        make(r);
        definitelyEquals(r);
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor_adds_1023_elements_from_Reverse_Sorted_Full() {
		if (!UNLEASH_FULL) { return; }
        ArrayList<Integer> r = range(1023, 0, -1);
        make(r);
        definitelyEquals(1, 257, 2, 385, 258, 129, 3, 449, 386, 321, 259, 193, 130, 65, 4, 481, 450, 417, 387, 353, 322, 289, 260, 225, 194, 161, 131, 97, 66, 33, 5, 497, 482, 465, 451, 433, 418, 401, 388, 369, 354, 337, 323, 305, 290, 273, 261, 241, 226, 209, 195, 177, 162, 145, 132, 113, 98, 81, 67, 49, 34, 17, 6, 505, 498, 489, 483, 473, 466, 457, 452, 441, 434, 425, 419, 409, 402, 393, 389, 377, 370, 361, 355, 345, 338, 329, 324, 313, 306, 297, 291, 281, 274, 265, 262, 249, 242, 233, 227, 217, 210, 201, 196, 185, 178, 169, 163, 153, 146, 137, 133, 121, 114, 105, 99, 89, 82, 73, 68, 57, 50, 41, 35, 25, 18, 9, 7, 509, 506, 501, 499, 493, 490, 485, 484, 477, 474, 469, 467, 461, 458, 453, 737, 445, 442, 437, 435, 429, 426, 421, 420, 413, 410, 405, 403, 397, 394, 390, 705, 381, 378, 373, 371, 365, 362, 357, 356, 349, 346, 341, 339, 333, 330, 325, 673, 317, 314, 309, 307, 301, 298, 293, 292, 285, 282, 277, 275, 269, 266, 263, 641, 253, 250, 245, 243, 237, 234, 229, 228, 221, 218, 213, 211, 205, 202, 197, 609, 189, 186, 181, 179, 173, 170, 165, 164, 157, 154, 149, 147, 141, 138, 134, 577, 125, 122, 117, 115, 109, 106, 101, 100, 93, 90, 85, 83, 77, 74, 69, 545, 61, 58, 53, 51, 45, 42, 37, 36, 29, 26, 21, 19, 13, 10, 8, 513, 511, 510, 507, 765, 503, 502, 500, 761, 495, 494, 491, 757, 487, 486, 754, 753, 479, 478, 475, 749, 471, 470, 468, 745, 463, 462, 459, 741, 455, 454, 738, 881, 447, 446, 443, 733, 439, 438, 436, 729, 431, 430, 427, 725, 423, 422, 722, 721, 415, 414, 411, 717, 407, 406, 404, 713, 399, 398, 395, 709, 391, 707, 706, 865, 383, 382, 379, 701, 375, 374, 372, 697, 367, 366, 363, 693, 359, 358, 690, 689, 351, 350, 347, 685, 343, 342, 340, 681, 335, 334, 331, 677, 327, 326, 674, 849, 319, 318, 315, 669, 311, 310, 308, 665, 303, 302, 299, 661, 295, 294, 658, 657, 287, 286, 283, 653, 279, 278, 276, 649, 271, 270, 267, 645, 264, 643, 642, 833, 255, 254, 251, 637, 247, 246, 244, 633, 239, 238, 235, 629, 231, 230, 626, 625, 223, 222, 219, 621, 215, 214, 212, 617, 207, 206, 203, 613, 199, 198, 610, 817, 191, 190, 187, 605, 183, 182, 180, 601, 175, 174, 171, 597, 167, 166, 594, 593, 159, 158, 155, 589, 151, 150, 148, 585, 143, 142, 139, 581, 135, 579, 578, 801, 127, 126, 123, 573, 119, 118, 116, 569, 111, 110, 107, 565, 103, 102, 562, 561, 95, 94, 91, 557, 87, 86, 84, 553, 79, 78, 75, 549, 71, 70, 546, 785, 63, 62, 59, 541, 55, 54, 52, 537, 47, 46, 43, 533, 39, 38, 530, 529, 31, 30, 27, 525, 23, 22, 20, 521, 15, 14, 11, 517, 516, 515, 514, 769, 512, 768, 896, 767, 508, 766, 895, 960, 504, 764, 894, 763, 992, 762, 893, 959, 496, 760, 892, 759, 492, 758, 891, 958, 488, 756, 890, 755, 991, 1008, 889, 957, 480, 752, 888, 751, 476, 750, 887, 956, 472, 748, 886, 747, 990, 746, 885, 955, 464, 744, 884, 743, 460, 742, 883, 954, 456, 740, 882, 739, 989, 1007, 1016, 953, 448, 736, 880, 735, 444, 734, 879, 952, 440, 732, 878, 731, 988, 730, 877, 951, 432, 728, 876, 727, 428, 726, 875, 950, 424, 724, 874, 723, 987, 1006, 873, 949, 416, 720, 872, 719, 412, 718, 871, 948, 408, 716, 870, 715, 986, 714, 869, 947, 400, 712, 868, 711, 396, 710, 867, 946, 392, 708, 866, 1020, 985, 1005, 1015, 945, 384, 704, 864, 703, 380, 702, 863, 944, 376, 700, 862, 699, 984, 698, 861, 943, 368, 696, 860, 695, 364, 694, 859, 942, 360, 692, 858, 691, 983, 1004, 857, 941, 352, 688, 856, 687, 348, 686, 855, 940, 344, 684, 854, 683, 982, 682, 853, 939, 336, 680, 852, 679, 332, 678, 851, 938, 328, 676, 850, 675, 981, 1003, 1014, 937, 320, 672, 848, 671, 316, 670, 847, 936, 312, 668, 846, 667, 980, 666, 845, 935, 304, 664, 844, 663, 300, 662, 843, 934, 296, 660, 842, 659, 979, 1002, 841, 933, 288, 656, 840, 655, 284, 654, 839, 932, 280, 652, 838, 651, 978, 650, 837, 931, 272, 648, 836, 647, 268, 646, 835, 930, 1022, 644, 834, 1019, 977, 1001, 1013, 929, 256, 640, 832, 639, 252, 638, 831, 928, 248, 636, 830, 635, 976, 634, 829, 927, 240, 632, 828, 631, 236, 630, 827, 926, 232, 628, 826, 627, 975, 1000, 825, 925, 224, 624, 824, 623, 220, 622, 823, 924, 216, 620, 822, 619, 974, 618, 821, 923, 208, 616, 820, 615, 204, 614, 819, 922, 200, 612, 818, 611, 973, 999, 1012, 921, 192, 608, 816, 607, 188, 606, 815, 920, 184, 604, 814, 603, 972, 602, 813, 919, 176, 600, 812, 599, 172, 598, 811, 918, 168, 596, 810, 595, 971, 998, 809, 917, 160, 592, 808, 591, 156, 590, 807, 916, 152, 588, 806, 587, 970, 586, 805, 915, 144, 584, 804, 583, 140, 582, 803, 914, 136, 580, 802, 1018, 969, 997, 1011, 913, 128, 576, 800, 575, 124, 574, 799, 912, 120, 572, 798, 571, 968, 570, 797, 911, 112, 568, 796, 567, 108, 566, 795, 910, 104, 564, 794, 563, 967, 996, 793, 909, 96, 560, 792, 559, 92, 558, 791, 908, 88, 556, 790, 555, 966, 554, 789, 907, 80, 552, 788, 551, 76, 550, 787, 906, 72, 548, 786, 547, 965, 995, 1010, 905, 64, 544, 784, 543, 60, 542, 783, 904, 56, 540, 782, 539, 964, 538, 781, 903, 48, 536, 780, 535, 44, 534, 779, 902, 40, 532, 778, 531, 963, 994, 777, 901, 32, 528, 776, 527, 28, 526, 775, 900, 24, 524, 774, 523, 962, 522, 773, 899, 16, 520, 772, 519, 12, 518, 771, 898, 1021, 1023, 770, 1017, 961, 993, 1009, 897);
    }

    @Test(timeout = TIMEOUT)
    public void test_Constructor_adds_1023_elements_Full() {
        if (!UNLEASH_FULL) { return; }
        make(536, 545, 399, 645, 1002, 924, 322, 938, 176, 391, 40, 601, 298, 213, 172, 818, 414, 183, 338, 977, 296, 798, 150, 250, 548, 254, 608, 205, 1020, 345, 81, 572, 11, 647, 395, 36, 590, 732, 15, 598, 787, 632, 684, 635, 100, 336, 614, 867, 441, 849, 520, 1006, 770, 1014, 311, 170, 783, 246, 379, 854, 856, 333, 313, 149, 812, 692, 900, 998, 507, 821, 669, 370, 801, 898, 436, 895, 730, 652, 362, 971, 12, 819, 103, 89, 718, 219, 923, 348, 603, 261, 569, 308, 1022, 462, 13, 502, 823, 128, 579, 467, 929, 661, 286, 571, 55, 809, 257, 248, 551, 581, 559, 366, 75, 243, 416, 956, 586, 642, 109, 245, 201, 2, 796, 564, 990, 650, 739, 904, 357, 693, 877, 354, 232, 226, 168, 813, 259, 1, 690, 190, 558, 1003, 543, 331, 64, 397, 596, 758, 678, 115, 469, 356, 133, 340, 711, 432, 497, 297, 574, 99, 979, 578, 121, 553, 782, 792, 527, 815, 10, 22, 107, 729, 838, 738, 703, 435, 667, 23, 957, 267, 530, 886, 470, 753, 976, 448, 628, 794, 903, 984, 726, 542, 805, 682, 229, 117, 668, 889, 396, 622, 712, 239, 515, 314, 180, 192, 870, 1018, 948, 175, 200, 925, 944, 318, 1008, 757, 18, 858, 358, 426, 148, 406, 392, 59, 490, 47, 31, 38, 439, 593, 328, 50, 291, 437, 92, 656, 86, 699, 882, 966, 74, 629, 872, 981, 694, 963, 912, 850, 698, 186, 252, 445, 276, 273, 910, 398, 216, 84, 708, 560, 869, 1001, 402, 763, 98, 224, 193, 455, 480, 541, 761, 461, 207, 184, 202, 935, 874, 566, 595, 49, 237, 223, 922, 670, 275, 503, 651, 39, 112, 521, 415, 363, 177, 894, 287, 878, 57, 329, 227, 484, 691, 680, 620, 639, 494, 777, 240, 654, 517, 989, 444, 748, 263, 671, 21, 302, 863, 409, 7, 266, 501, 772, 555, 158, 211, 105, 638, 710, 606, 321, 1015, 63, 44, 61, 222, 908, 641, 510, 625, 920, 988, 447, 87, 389, 951, 210, 217, 427, 825, 945, 26, 380, 154, 926, 496, 791, 166, 113, 131, 961, 268, 723, 185, 655, 759, 573, 999, 258, 653, 633, 883, 683, 969, 495, 504, 704, 848, 808, 385, 498, 884, 292, 937, 745, 557, 378, 231, 94, 323, 72, 283, 304, 784, 913, 911, 161, 97, 35, 438, 789, 740, 407, 592, 440, 361, 146, 88, 890, 281, 707, 305, 70, 605, 118, 233, 597, 768, 917, 422, 108, 648, 803, 474, 511, 864, 640, 203, 992, 751, 78, 983, 673, 420, 43, 466, 156, 611, 317, 535, 749, 289, 312, 681, 285, 369, 93, 876, 940, 788, 269, 907, 488, 804, 950, 860, 165, 142, 744, 179, 800, 69, 906, 221, 410, 120, 706, 742, 845, 919, 649, 978, 411, 871, 618, 486, 529, 954, 1000, 500, 862, 82, 431, 182, 162, 734, 284, 73, 540, 214, 280, 943, 351, 960, 985, 685, 847, 722, 725, 580, 741, 242, 45, 294, 814, 456, 755, 695, 666, 512, 918, 288, 538, 901, 810, 851, 672, 835, 1005, 428, 19, 272, 264, 429, 868, 405, 797, 465, 550, 830, 879, 83, 987, 747, 735, 106, 588, 916, 675, 472, 522, 374, 842, 994, 171, 228, 793, 968, 119, 384, 271, 534, 339, 91, 767, 705, 526, 20, 80, 786, 516, 208, 609, 151, 844, 220, 487, 278, 122, 153, 66, 660, 14, 424, 382, 631, 506, 993, 852, 28, 724, 702, 212, 450, 939, 773, 686, 147, 346, 307, 931, 477, 483, 887, 982, 492, 643, 528, 77, 65, 134, 419, 85, 1011, 303, 719, 780, 301, 368, 67, 421, 191, 737, 547, 132, 137, 244, 442, 347, 752, 689, 909, 839, 360, 973, 995, 372, 626, 664, 746, 743, 892, 295, 841, 765, 824, 1012, 9, 1016, 249, 417, 563, 418, 204, 473, 247, 451, 37, 157, 658, 750, 932, 549, 946, 265, 293, 832, 955, 802, 413, 524, 733, 727, 736, 481, 582, 327, 826, 51, 282, 33, 902, 102, 820, 928, 1010, 335, 330, 970, 624, 980, 230, 111, 145, 585, 110, 343, 459, 986, 781, 349, 485, 260, 251, 225, 933, 843, 775, 423, 491, 90, 721, 509, 215, 79, 325, 34, 934, 446, 332, 816, 855, 364, 833, 914, 533, 124, 604, 130, 859, 594, 262, 401, 720, 1021, 576, 861, 936, 452, 513, 482, 169, 921, 644, 817, 238, 617, 634, 700, 255, 433, 178, 778, 972, 6, 350, 525, 96, 274, 505, 138, 443, 965, 453, 320, 949, 1007, 60, 519, 562, 164, 393, 167, 822, 457, 959, 29, 32, 479, 344, 355, 891, 381, 776, 623, 367, 173, 514, 974, 62, 676, 5, 837, 471, 828, 958, 71, 589, 846, 54, 556, 316, 774, 4, 449, 235, 888, 570, 713, 475, 476, 25, 840, 831, 277, 139, 769, 829, 300, 341, 853, 141, 174, 48, 646, 953, 425, 659, 630, 615, 256, 1017, 489, 602, 665, 76, 715, 408, 762, 241, 52, 388, 24, 125, 27, 508, 997, 785, 583, 353, 468, 371, 756, 373, 927, 621, 716, 198, 941, 731, 701, 881, 463, 523, 827, 337, 674, 129, 194, 591, 962, 253, 270, 140, 306, 404, 493, 101, 460, 376, 56, 991, 163, 412, 857, 880, 636, 1019, 893, 663, 342, 616, 58, 610, 771, 386, 811, 754, 310, 1009, 319, 199, 234, 728, 807, 947, 95, 160, 795, 383, 873, 607, 866, 499, 309, 324, 46, 195, 188, 709, 315, 334, 565, 68, 679, 612, 760, 123, 104, 1013, 897, 377, 136, 41, 537, 359, 577, 209, 196, 717, 967, 952, 865, 3, 1004, 834, 806, 218, 126, 400, 964, 539, 599, 127, 836, 17, 387, 290, 155, 627, 152, 1023, 116, 464, 552, 554, 619, 561, 662, 531, 403, 899, 546, 779, 799, 365, 236, 696, 600, 996, 790, 375, 885, 568, 430, 584, 532, 915, 875, 390, 454, 575, 181, 930, 299, 53, 613, 518, 135, 766, 764, 16, 326, 189, 687, 352, 458, 587, 567, 478, 677, 143, 434, 114, 697, 187, 8, 544, 206, 896, 42, 144, 688, 279, 637, 159, 197, 975, 905, 942, 657, 30, 394, 714);
        definitelyEquals(1, 6, 2, 7, 10, 4, 3, 11, 9, 12, 13, 5, 18, 17, 8, 19, 14, 28, 15, 33, 22, 23, 29, 35, 25, 24, 43, 31, 50, 16, 30, 84, 83, 20, 49, 36, 57, 67, 21, 37, 44, 87, 26, 34, 100, 96, 32, 62, 54, 48, 52, 27, 78, 56, 58, 46, 38, 92, 86, 73, 53, 45, 42, 149, 264, 98, 168, 119, 80, 151, 66, 39, 147, 65, 85, 132, 244, 263, 176, 99, 105, 321, 51, 89, 107, 210, 90, 79, 113, 185, 169, 178, 138, 60, 40, 231, 72, 71, 97, 139, 88, 180, 70, 125, 55, 203, 129, 101, 156, 148, 93, 59, 47, 68, 41, 120, 126, 152, 109, 74, 162, 181, 135, 189, 81, 144, 197, 216, 357, 272, 402, 354, 106, 226, 374, 171, 184, 91, 414, 190, 220, 122, 382, 112, 64, 177, 287, 483, 77, 115, 301, 191, 133, 340, 360, 372, 497, 295, 249, 204, 247, 157, 121, 413, 524, 63, 61, 102, 330, 111, 110, 349, 217, 423, 215, 154, 332, 166, 124, 262, 261, 452, 238, 255, 308, 336, 150, 320, 385, 164, 292, 344, 367, 94, 229, 117, 128, 161, 396, 467, 277, 141, 146, 281, 256, 76, 118, 254, 353, 108, 198, 511, 337, 194, 140, 298, 248, 163, 317, 289, 311, 199, 234, 95, 269, 170, 75, 142, 104, 69, 209, 196, 291, 218, 127, 290, 155, 116, 500, 82, 182, 375, 201, 214, 280, 299, 172, 326, 458, 114, 187, 206, 159, 273, 288, 398, 810, 645, 428, 560, 429, 405, 465, 550, 692, 224, 193, 455, 472, 522, 761, 228, 207, 259, 202, 339, 705, 526, 595, 208, 237, 223, 278, 153, 275, 424, 631, 370, 183, 521, 212, 363, 397, 307, 477, 878, 492, 329, 227, 134, 436, 303, 469, 356, 421, 547, 137, 442, 347, 689, 444, 748, 432, 664, 652, 297, 765, 362, 338, 266, 418, 473, 451, 158, 211, 549, 265, 710, 553, 733, 727, 481, 327, 103, 222, 815, 641, 335, 624, 230, 145, 391, 296, 389, 485, 251, 219, 427, 491, 721, 509, 325, 435, 446, 496, 364, 533, 604, 130, 267, 268, 576, 530, 513, 482, 573, 470, 258, 653, 433, 778, 350, 525, 274, 443, 453, 794, 808, 462, 393, 167, 457, 614, 479, 355, 378, 502, 173, 323, 601, 283, 304, 589, 668, 316, 250, 235, 441, 438, 476, 740, 407, 592, 300, 239, 174, 646, 425, 314, 661, 305, 192, 408, 241, 233, 597, 508, 785, 422, 175, 373, 621, 200, 731, 701, 463, 809, 318, 591, 253, 257, 306, 420, 376, 466, 412, 611, 358, 535, 342, 426, 312, 681, 285, 319, 392, 807, 160, 383, 490, 499, 309, 195, 188, 315, 165, 612, 123, 179, 377, 136, 359, 221, 410, 246, 706, 742, 399, 400, 539, 586, 411, 387, 213, 379, 464, 552, 561, 531, 403, 546, 365, 236, 600, 734, 284, 430, 532, 390, 454, 694, 345, 518, 766, 685, 564, 352, 587, 478, 143, 186, 333, 252, 322, 445, 279, 276, 313, 512, 657, 394, 538, 901, 904, 851, 672, 835, 1005, 708, 572, 693, 869, 812, 868, 1001, 797, 877, 763, 830, 879, 818, 987, 747, 735, 232, 588, 916, 675, 480, 900, 541, 842, 994, 461, 813, 793, 968, 998, 384, 271, 534, 507, 935, 767, 874, 690, 566, 647, 786, 516, 938, 609, 821, 844, 558, 487, 922, 670, 395, 1003, 660, 503, 543, 651, 669, 506, 993, 852, 331, 724, 702, 415, 450, 939, 773, 686, 801, 346, 894, 931, 596, 898, 887, 982, 758, 643, 528, 678, 590, 484, 419, 691, 1011, 680, 719, 780, 620, 368, 639, 895, 494, 737, 777, 240, 732, 654, 730, 517, 752, 989, 909, 839, 711, 973, 995, 545, 626, 671, 746, 743, 892, 302, 841, 863, 824, 1012, 409, 1016, 574, 417, 563, 977, 501, 971, 772, 979, 555, 578, 658, 750, 932, 598, 946, 638, 293, 832, 955, 802, 606, 782, 819, 1015, 736, 787, 582, 792, 826, 536, 282, 527, 902, 908, 820, 928, 1010, 632, 510, 970, 625, 980, 920, 988, 718, 585, 447, 343, 459, 986, 781, 951, 729, 260, 838, 225, 933, 843, 775, 825, 738, 945, 923, 684, 703, 380, 348, 798, 934, 667, 926, 816, 855, 791, 833, 914, 603, 957, 635, 131, 859, 594, 961, 401, 720, 1021, 723, 861, 936, 655, 569, 886, 759, 921, 644, 817, 999, 617, 634, 700, 753, 976, 633, 883, 972, 683, 448, 1022, 969, 495, 505, 504, 628, 965, 704, 848, 949, 1007, 903, 519, 562, 498, 984, 884, 822, 1002, 959, 937, 726, 542, 745, 557, 891, 381, 776, 623, 805, 867, 514, 974, 682, 676, 823, 837, 471, 828, 958, 784, 924, 846, 913, 556, 911, 774, 889, 449, 579, 888, 570, 713, 475, 622, 789, 840, 831, 712, 849, 769, 829, 440, 341, 853, 361, 548, 515, 929, 953, 890, 659, 630, 615, 707, 1017, 489, 602, 665, 520, 715, 605, 762, 286, 870, 388, 1006, 1018, 768, 571, 997, 917, 583, 948, 468, 371, 756, 648, 927, 803, 716, 474, 941, 925, 864, 881, 640, 523, 827, 944, 674, 992, 751, 770, 962, 1008, 270, 983, 673, 404, 493, 757, 460, 608, 1014, 991, 858, 551, 857, 880, 636, 1019, 893, 663, 749, 616, 581, 610, 771, 386, 811, 754, 310, 1009, 406, 369, 559, 728, 876, 947, 940, 366, 795, 788, 873, 607, 866, 907, 488, 324, 804, 205, 950, 709, 860, 334, 565, 243, 679, 783, 760, 744, 439, 1013, 897, 800, 416, 593, 537, 906, 577, 328, 1020, 717, 967, 952, 865, 956, 1004, 834, 806, 845, 919, 437, 964, 649, 599, 978, 836, 656, 642, 871, 618, 627, 486, 1023, 529, 699, 954, 554, 619, 1000, 662, 882, 862, 899, 966, 779, 799, 431, 245, 696, 629, 996, 790, 854, 885, 568, 872, 584, 540, 915, 875, 981, 856, 575, 943, 930, 963, 351, 613, 960, 985, 796, 764, 912, 850, 847, 687, 722, 725, 698, 567, 580, 677, 741, 434, 242, 697, 990, 294, 544, 814, 896, 456, 650, 688, 755, 637, 695, 666, 975, 905, 942, 910, 918, 739, 714);
    }

    @Test(timeout = LONGTIMEOUT)
    public void test_Constructor_Random_Full() {
        if (!UNLEASH_FULL) { return; }
        Random rand = new Random(2);

        // Test a bunch of times
        for (int i = 0; i < 500; i++) {
            // Make a big random heap
            make(randList(rand, 10000));

            // Check that it is a heap
            ArrayList<Integer> diff = new ArrayList<>();
            if (!isHeap(heapArray(), diff)) {
                printDiff("test_Constructor_Random_Full", diff);
                assertTrue("Not a heap!", false);
            }
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test_add_IllegalArgumentException() {
        heap.add(null);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_to_Empty() {
        heap.add(121);
        definitelyEquals(121);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_7_elements_from_Sorted() {
        multiAdd(range(7));
        definitelyEquals(range(7));
    }

    @Test(timeout = TIMEOUT)
    public void test_add_7_elements_from_Reverse_Sorted() {
        multiAdd(range(7, 0, -1));
        definitelyEquals(1, 4, 2, 7, 5, 6, 3);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_7_elements_1() {
        multiAdd(5, 1, 4, 7, 2, 6, 3);
        definitelyEquals(1, 2, 3, 7, 5, 6, 4);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_7_elements_2() {
        multiAdd(2, 3, 5, 6, 1, 4, 7);
        definitelyEquals(1, 2, 4, 6, 3, 5, 7);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_7_elements_3() {
        multiAdd(4, 5, 6, 1, 2, 7, 3);
        definitelyEquals(1, 2, 3, 5, 4, 7, 6);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_14_elements_from_Sorted() {
        multiAdd(range(14));
        definitelyEquals(range(14));
    }

    @Test(timeout = TIMEOUT)
    public void test_add_14_elements_from_Reverse_Sorted() {
        multiAdd(range(14, 0, -1));
        definitelyEquals(1, 5, 2, 8, 6, 4, 3, 14, 11, 12, 7, 13, 9, 10);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_14_elements_1() {
        multiAdd(14, 7, 10, 4, 1, 9, 5, 2, 3, 11, 6, 8, 13, 12);
        definitelyEquals(1, 2, 5, 3, 6, 8, 9, 14, 4, 11, 7, 10, 13, 12);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_14_elements_2() {
        multiAdd(4, 8, 11, 12, 1, 5, 7, 3, 9, 6, 14, 13, 10, 2);
        definitelyEquals(1, 3, 2, 4, 6, 10, 5, 12, 9, 8, 14, 13, 11, 7);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_14_elements_3() {
        multiAdd(12, 8, 10, 6, 9, 13, 1, 7, 11, 5, 2, 4, 14, 3);
        definitelyEquals(1, 2, 3, 8, 5, 6, 4, 12, 11, 9, 7, 13, 14, 10);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_Random_Small() {
        Random rand = new Random(3);

        // Test five times
        for (int i = 0; i < 5; i++) {
            // Make a random heap of size 20
            multiAdd(randList(rand, 30));

            // Check that it is a heap
            ArrayList<Integer> diff = new ArrayList<>();
            if (!isHeap(heapArray(), diff)) {
                printDiff("test_add_Random_Small trial " + i, diff);
                assertTrue("Not a heap!", false);
            }
        }
    }

    @Test(timeout = LONGTIMEOUT)
    public void test_add_6_elements_Full() {
        if (!UNLEASH_FULL) { return; }
        for (ArrayList<Integer> p : permutate(6)) {
            multiAdd(p);

            // Check that it is a heap
            ArrayList<Integer> diff = new ArrayList<>();
            if (!isHeap(heapArray(), diff)) {
                printDiff("test_add_6_elements_Full", diff);
                assertTrue("Not a heap!", false);
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_add_1023_elements_from_Sorted_Full() {
        if (!UNLEASH_FULL) { return; }
        ArrayList<Integer> r = range(1023);
        multiAdd(r);
        definitelyEquals(r);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_1023_elements_from_Reverse_Sorted_Full() {
        if (!UNLEASH_FULL) { return; }
        ArrayList<Integer> r = range(1023, 0, -1);
        multiAdd(r);
        definitelyEquals(1, 258, 2, 387, 259, 131, 3, 452, 388, 324, 260, 196, 132, 68, 4, 485, 453, 421, 389, 357, 325, 293, 261, 229, 197, 165, 133, 101, 69, 37, 5, 502, 486, 470, 454, 438, 422, 406, 390, 374, 358, 342, 326, 310, 294, 278, 262, 246, 230, 214, 198, 182, 166, 150, 134, 118, 102, 86, 70, 54, 38, 22, 6, 511, 503, 495, 487, 479, 471, 463, 455, 447, 439, 431, 423, 415, 407, 399, 391, 383, 375, 367, 359, 351, 343, 335, 327, 319, 311, 303, 295, 287, 279, 271, 263, 255, 247, 239, 231, 223, 215, 207, 199, 191, 183, 175, 167, 159, 151, 143, 135, 127, 119, 111, 103, 95, 87, 79, 71, 63, 55, 47, 39, 31, 23, 15, 7, 740, 512, 508, 504, 500, 496, 492, 488, 484, 480, 476, 472, 468, 464, 460, 456, 708, 448, 444, 440, 436, 432, 428, 424, 420, 416, 412, 408, 404, 400, 396, 392, 676, 384, 380, 376, 372, 368, 364, 360, 356, 352, 348, 344, 340, 336, 332, 328, 644, 320, 316, 312, 308, 304, 300, 296, 292, 288, 284, 280, 276, 272, 268, 264, 612, 256, 252, 248, 244, 240, 236, 232, 228, 224, 220, 216, 212, 208, 204, 200, 580, 192, 188, 184, 180, 176, 172, 168, 164, 160, 156, 152, 148, 144, 140, 136, 548, 128, 124, 120, 116, 112, 108, 104, 100, 96, 92, 88, 84, 80, 76, 72, 516, 64, 60, 56, 52, 48, 44, 40, 36, 32, 28, 24, 20, 16, 12, 8, 867, 757, 707, 513, 767, 509, 507, 505, 763, 501, 499, 497, 759, 493, 491, 489, 755, 741, 483, 481, 751, 477, 475, 473, 747, 469, 467, 465, 743, 461, 459, 457, 739, 725, 451, 449, 735, 445, 443, 441, 731, 437, 435, 433, 727, 429, 427, 425, 723, 709, 419, 417, 719, 413, 411, 409, 715, 405, 403, 401, 711, 397, 395, 393, 835, 693, 643, 385, 703, 381, 379, 377, 699, 373, 371, 369, 695, 365, 363, 361, 691, 677, 355, 353, 687, 349, 347, 345, 683, 341, 339, 337, 679, 333, 331, 329, 675, 661, 323, 321, 671, 317, 315, 313, 667, 309, 307, 305, 663, 301, 299, 297, 659, 645, 291, 289, 655, 285, 283, 281, 651, 277, 275, 273, 647, 269, 267, 265, 803, 629, 579, 257, 639, 253, 251, 249, 635, 245, 243, 241, 631, 237, 235, 233, 627, 613, 227, 225, 623, 221, 219, 217, 619, 213, 211, 209, 615, 205, 203, 201, 611, 597, 195, 193, 607, 189, 187, 185, 603, 181, 179, 177, 599, 173, 171, 169, 595, 581, 163, 161, 591, 157, 155, 153, 587, 149, 147, 145, 583, 141, 139, 137, 771, 565, 515, 129, 575, 125, 123, 121, 571, 117, 115, 113, 567, 109, 107, 105, 563, 549, 99, 97, 559, 93, 91, 89, 555, 85, 83, 81, 551, 77, 75, 73, 547, 533, 67, 65, 543, 61, 59, 57, 539, 53, 51, 49, 535, 45, 43, 41, 531, 517, 35, 33, 527, 29, 27, 25, 523, 21, 19, 17, 519, 13, 11, 9, 1023, 930, 961, 766, 992, 884, 893, 642, 1007, 834, 947, 510, 956, 768, 769, 506, 1014, 894, 897, 758, 978, 764, 765, 498, 987, 762, 895, 494, 896, 760, 761, 490, 1017, 890, 957, 750, 960, 756, 885, 482, 993, 754, 891, 478, 892, 752, 753, 474, 1002, 886, 889, 742, 958, 748, 749, 466, 959, 746, 887, 462, 888, 744, 745, 458, 1020, 882, 953, 734, 988, 868, 877, 450, 991, 738, 883, 446, 948, 736, 737, 442, 1008, 878, 881, 726, 954, 732, 733, 434, 955, 730, 879, 430, 880, 728, 729, 426, 1009, 874, 949, 718, 952, 724, 869, 418, 989, 722, 875, 414, 876, 720, 721, 410, 990, 870, 873, 710, 950, 716, 717, 402, 951, 714, 871, 398, 872, 712, 713, 394, 1021, 866, 945, 702, 984, 852, 861, 386, 1003, 706, 931, 382, 940, 704, 705, 378, 1006, 862, 865, 694, 946, 700, 701, 370, 979, 698, 863, 366, 864, 696, 697, 362, 1015, 858, 941, 686, 944, 692, 853, 354, 985, 690, 859, 350, 860, 688, 689, 346, 986, 854, 857, 678, 942, 684, 685, 338, 943, 682, 855, 334, 856, 680, 681, 330, 1016, 850, 937, 670, 980, 836, 845, 322, 983, 674, 851, 318, 932, 672, 673, 314, 1004, 846, 849, 662, 938, 668, 669, 306, 939, 666, 847, 302, 848, 664, 665, 298, 1005, 842, 933, 654, 936, 660, 837, 290, 981, 658, 843, 286, 844, 656, 657, 282, 982, 838, 841, 646, 934, 652, 653, 274, 935, 650, 839, 270, 840, 648, 649, 266, 1022, 898, 929, 638, 976, 820, 829, 514, 999, 770, 915, 254, 924, 640, 641, 250, 1010, 830, 833, 630, 962, 636, 637, 242, 971, 634, 831, 238, 832, 632, 633, 234, 1013, 826, 925, 622, 928, 628, 821, 226, 977, 626, 827, 222, 828, 624, 625, 218, 994, 822, 825, 614, 926, 620, 621, 210, 927, 618, 823, 206, 824, 616, 617, 202, 1018, 818, 921, 606, 972, 804, 813, 194, 975, 610, 819, 190, 916, 608, 609, 186, 1000, 814, 817, 598, 922, 604, 605, 178, 923, 602, 815, 174, 816, 600, 601, 170, 1001, 810, 917, 590, 920, 596, 805, 162, 973, 594, 811, 158, 812, 592, 593, 154, 974, 806, 809, 582, 918, 588, 589, 146, 919, 586, 807, 142, 808, 584, 585, 138, 1019, 802, 913, 574, 968, 788, 797, 130, 995, 578, 899, 126, 908, 576, 577, 122, 998, 798, 801, 566, 914, 572, 573, 114, 963, 570, 799, 110, 800, 568, 569, 106, 1011, 794, 909, 558, 912, 564, 789, 98, 969, 562, 795, 94, 796, 560, 561, 90, 970, 790, 793, 550, 910, 556, 557, 82, 911, 554, 791, 78, 792, 552, 553, 74, 1012, 786, 905, 542, 964, 772, 781, 66, 967, 546, 787, 62, 900, 544, 545, 58, 996, 782, 785, 534, 906, 540, 541, 50, 907, 538, 783, 46, 784, 536, 537, 42, 997, 778, 901, 526, 904, 532, 773, 34, 965, 530, 779, 30, 780, 528, 529, 26, 966, 774, 777, 518, 902, 524, 525, 18, 903, 522, 775, 14, 776, 520, 521, 10);
    }

    @Test(timeout = TIMEOUT)
    public void test_add_1023_elements_Full() {
        if (!UNLEASH_FULL) { return; }
        multiAdd(536, 545, 399, 645, 1002, 924, 322, 938, 176, 391, 40, 601, 298, 213, 172, 818, 414, 183, 338, 977, 296, 798, 150, 250, 548, 254, 608, 205, 1020, 345, 81, 572, 11, 647, 395, 36, 590, 732, 15, 598, 787, 632, 684, 635, 100, 336, 614, 867, 441, 849, 520, 1006, 770, 1014, 311, 170, 783, 246, 379, 854, 856, 333, 313, 149, 812, 692, 900, 998, 507, 821, 669, 370, 801, 898, 436, 895, 730, 652, 362, 971, 12, 819, 103, 89, 718, 219, 923, 348, 603, 261, 569, 308, 1022, 462, 13, 502, 823, 128, 579, 467, 929, 661, 286, 571, 55, 809, 257, 248, 551, 581, 559, 366, 75, 243, 416, 956, 586, 642, 109, 245, 201, 2, 796, 564, 990, 650, 739, 904, 357, 693, 877, 354, 232, 226, 168, 813, 259, 1, 690, 190, 558, 1003, 543, 331, 64, 397, 596, 758, 678, 115, 469, 356, 133, 340, 711, 432, 497, 297, 574, 99, 979, 578, 121, 553, 782, 792, 527, 815, 10, 22, 107, 729, 838, 738, 703, 435, 667, 23, 957, 267, 530, 886, 470, 753, 976, 448, 628, 794, 903, 984, 726, 542, 805, 682, 229, 117, 668, 889, 396, 622, 712, 239, 515, 314, 180, 192, 870, 1018, 948, 175, 200, 925, 944, 318, 1008, 757, 18, 858, 358, 426, 148, 406, 392, 59, 490, 47, 31, 38, 439, 593, 328, 50, 291, 437, 92, 656, 86, 699, 882, 966, 74, 629, 872, 981, 694, 963, 912, 850, 698, 186, 252, 445, 276, 273, 910, 398, 216, 84, 708, 560, 869, 1001, 402, 763, 98, 224, 193, 455, 480, 541, 761, 461, 207, 184, 202, 935, 874, 566, 595, 49, 237, 223, 922, 670, 275, 503, 651, 39, 112, 521, 415, 363, 177, 894, 287, 878, 57, 329, 227, 484, 691, 680, 620, 639, 494, 777, 240, 654, 517, 989, 444, 748, 263, 671, 21, 302, 863, 409, 7, 266, 501, 772, 555, 158, 211, 105, 638, 710, 606, 321, 1015, 63, 44, 61, 222, 908, 641, 510, 625, 920, 988, 447, 87, 389, 951, 210, 217, 427, 825, 945, 26, 380, 154, 926, 496, 791, 166, 113, 131, 961, 268, 723, 185, 655, 759, 573, 999, 258, 653, 633, 883, 683, 969, 495, 504, 704, 848, 808, 385, 498, 884, 292, 937, 745, 557, 378, 231, 94, 323, 72, 283, 304, 784, 913, 911, 161, 97, 35, 438, 789, 740, 407, 592, 440, 361, 146, 88, 890, 281, 707, 305, 70, 605, 118, 233, 597, 768, 917, 422, 108, 648, 803, 474, 511, 864, 640, 203, 992, 751, 78, 983, 673, 420, 43, 466, 156, 611, 317, 535, 749, 289, 312, 681, 285, 369, 93, 876, 940, 788, 269, 907, 488, 804, 950, 860, 165, 142, 744, 179, 800, 69, 906, 221, 410, 120, 706, 742, 845, 919, 649, 978, 411, 871, 618, 486, 529, 954, 1000, 500, 862, 82, 431, 182, 162, 734, 284, 73, 540, 214, 280, 943, 351, 960, 985, 685, 847, 722, 725, 580, 741, 242, 45, 294, 814, 456, 755, 695, 666, 512, 918, 288, 538, 901, 810, 851, 672, 835, 1005, 428, 19, 272, 264, 429, 868, 405, 797, 465, 550, 830, 879, 83, 987, 747, 735, 106, 588, 916, 675, 472, 522, 374, 842, 994, 171, 228, 793, 968, 119, 384, 271, 534, 339, 91, 767, 705, 526, 20, 80, 786, 516, 208, 609, 151, 844, 220, 487, 278, 122, 153, 66, 660, 14, 424, 382, 631, 506, 993, 852, 28, 724, 702, 212, 450, 939, 773, 686, 147, 346, 307, 931, 477, 483, 887, 982, 492, 643, 528, 77, 65, 134, 419, 85, 1011, 303, 719, 780, 301, 368, 67, 421, 191, 737, 547, 132, 137, 244, 442, 347, 752, 689, 909, 839, 360, 973, 995, 372, 626, 664, 746, 743, 892, 295, 841, 765, 824, 1012, 9, 1016, 249, 417, 563, 418, 204, 473, 247, 451, 37, 157, 658, 750, 932, 549, 946, 265, 293, 832, 955, 802, 413, 524, 733, 727, 736, 481, 582, 327, 826, 51, 282, 33, 902, 102, 820, 928, 1010, 335, 330, 970, 624, 980, 230, 111, 145, 585, 110, 343, 459, 986, 781, 349, 485, 260, 251, 225, 933, 843, 775, 423, 491, 90, 721, 509, 215, 79, 325, 34, 934, 446, 332, 816, 855, 364, 833, 914, 533, 124, 604, 130, 859, 594, 262, 401, 720, 1021, 576, 861, 936, 452, 513, 482, 169, 921, 644, 817, 238, 617, 634, 700, 255, 433, 178, 778, 972, 6, 350, 525, 96, 274, 505, 138, 443, 965, 453, 320, 949, 1007, 60, 519, 562, 164, 393, 167, 822, 457, 959, 29, 32, 479, 344, 355, 891, 381, 776, 623, 367, 173, 514, 974, 62, 676, 5, 837, 471, 828, 958, 71, 589, 846, 54, 556, 316, 774, 4, 449, 235, 888, 570, 713, 475, 476, 25, 840, 831, 277, 139, 769, 829, 300, 341, 853, 141, 174, 48, 646, 953, 425, 659, 630, 615, 256, 1017, 489, 602, 665, 76, 715, 408, 762, 241, 52, 388, 24, 125, 27, 508, 997, 785, 583, 353, 468, 371, 756, 373, 927, 621, 716, 198, 941, 731, 701, 881, 463, 523, 827, 337, 674, 129, 194, 591, 962, 253, 270, 140, 306, 404, 493, 101, 460, 376, 56, 991, 163, 412, 857, 880, 636, 1019, 893, 663, 342, 616, 58, 610, 771, 386, 811, 754, 310, 1009, 319, 199, 234, 728, 807, 947, 95, 160, 795, 383, 873, 607, 866, 499, 309, 324, 46, 195, 188, 709, 315, 334, 565, 68, 679, 612, 760, 123, 104, 1013, 897, 377, 136, 41, 537, 359, 577, 209, 196, 717, 967, 952, 865, 3, 1004, 834, 806, 218, 126, 400, 964, 539, 599, 127, 836, 17, 387, 290, 155, 627, 152, 1023, 116, 464, 552, 554, 619, 561, 662, 531, 403, 899, 546, 779, 799, 365, 236, 696, 600, 996, 790, 375, 885, 568, 430, 584, 532, 915, 875, 390, 454, 575, 181, 930, 299, 53, 613, 518, 135, 766, 764, 16, 326, 189, 687, 352, 458, 587, 567, 478, 677, 143, 434, 114, 697, 187, 8, 544, 206, 896, 42, 144, 688, 279, 637, 159, 197, 975, 905, 942, 657, 30, 394, 714);
        definitelyEquals(1, 2, 3, 7, 6, 5, 4, 14, 9, 13, 10, 11, 24, 17, 8, 36, 19, 28, 12, 33, 22, 23, 15, 18, 25, 27, 56, 41, 31, 45, 16, 84, 83, 49, 20, 40, 57, 67, 21, 44, 37, 87, 26, 34, 169, 96, 29, 62, 35, 48, 52, 43, 108, 78, 58, 47, 46, 50, 38, 75, 53, 73, 30, 216, 168, 98, 226, 171, 80, 151, 66, 64, 147, 65, 85, 132, 244, 263, 39, 103, 105, 322, 51, 102, 89, 217, 90, 79, 124, 262, 185, 255, 138, 292, 32, 344, 72, 71, 54, 139, 70, 256, 76, 81, 198, 257, 129, 101, 156, 93, 148, 95, 59, 68, 55, 92, 126, 86, 116, 182, 82, 205, 74, 189, 114, 144, 42, 398, 357, 264, 405, 183, 106, 455, 232, 202, 184, 339, 91, 190, 220, 149, 122, 112, 212, 177, 287, 483, 77, 134, 301, 191, 137, 340, 360, 372, 297, 295, 133, 266, 204, 157, 121, 413, 524, 321, 61, 510, 330, 111, 100, 349, 219, 423, 210, 113, 332, 348, 130, 267, 268, 452, 238, 336, 258, 178, 176, 385, 320, 167, 60, 355, 367, 173, 117, 304, 94, 97, 161, 407, 146, 180, 141, 314, 281, 118, 88, 125, 353, 254, 200, 701, 337, 194, 140, 248, 128, 163, 317, 289, 312, 199, 234, 160, 269, 109, 172, 142, 104, 69, 209, 196, 120, 170, 127, 246, 152, 379, 500, 245, 201, 345, 162, 390, 214, 299, 135, 326, 458, 186, 143, 206, 159, 276, 197, 538, 545, 672, 428, 402, 272, 560, 465, 550, 354, 572, 193, 588, 472, 374, 761, 228, 507, 207, 259, 395, 705, 526, 119, 237, 208, 223, 278, 275, 153, 424, 382, 506, 331, 521, 370, 773, 363, 307, 477, 678, 492, 528, 115, 436, 419, 469, 303, 368, 240, 547, 150, 442, 347, 689, 444, 748, 432, 664, 497, 362, 765, 302, 249, 501, 417, 473, 247, 158, 211, 549, 265, 819, 606, 553, 727, 481, 327, 99, 63, 641, 908, 335, 624, 718, 145, 110, 107, 729, 485, 251, 225, 775, 491, 427, 215, 325, 261, 446, 667, 364, 533, 154, 131, 296, 401, 576, 530, 513, 470, 573, 569, 617, 391, 433, 778, 448, 350, 495, 274, 704, 453, 614, 519, 393, 308, 462, 164, 479, 378, 399, 381, 323, 229, 231, 283, 828, 441, 668, 316, 250, 235, 570, 438, 476, 789, 277, 622, 341, 300, 174, 361, 425, 615, 305, 467, 239, 408, 241, 192, 422, 175, 785, 583, 371, 373, 621, 474, 809, 731, 511, 463, 674, 203, 318, 253, 404, 306, 376, 311, 358, 611, 551, 535, 426, 342, 386, 681, 369, 310, 285, 807, 213, 383, 490, 499, 324, 195, 188, 315, 165, 243, 179, 123, 377, 136, 359, 221, 291, 410, 586, 742, 806, 218, 539, 437, 411, 290, 486, 155, 464, 552, 561, 531, 403, 546, 365, 236, 600, 375, 568, 284, 540, 532, 454, 280, 351, 518, 685, 181, 564, 352, 580, 478, 298, 242, 252, 187, 456, 273, 445, 279, 512, 666, 288, 394, 938, 901, 810, 851, 904, 835, 1005, 708, 812, 693, 869, 429, 1001, 868, 877, 797, 763, 830, 879, 692, 987, 747, 735, 224, 900, 916, 675, 480, 541, 522, 842, 994, 998, 461, 793, 968, 813, 384, 271, 534, 935, 818, 874, 767, 690, 566, 595, 786, 821, 516, 609, 558, 844, 647, 922, 487, 1003, 543, 670, 660, 669, 503, 651, 631, 645, 993, 852, 338, 724, 702, 415, 450, 939, 801, 686, 397, 894, 346, 931, 596, 898, 887, 982, 878, 758, 643, 329, 227, 590, 484, 691, 1011, 680, 719, 780, 620, 895, 494, 639, 421, 777, 737, 730, 356, 732, 517, 654, 752, 989, 909, 839, 711, 973, 995, 652, 626, 671, 746, 743, 892, 414, 841, 863, 824, 1012, 574, 1016, 409, 1002, 563, 598, 418, 979, 555, 772, 451, 971, 658, 750, 932, 578, 946, 638, 293, 832, 955, 802, 710, 782, 733, 1015, 736, 792, 582, 527, 826, 787, 282, 222, 902, 977, 820, 928, 1010, 815, 625, 970, 632, 980, 920, 988, 230, 585, 447, 343, 459, 986, 781, 951, 684, 838, 260, 389, 933, 923, 843, 825, 703, 945, 721, 738, 509, 798, 380, 435, 934, 926, 496, 816, 855, 791, 833, 914, 603, 957, 604, 166, 859, 961, 594, 635, 720, 1021, 723, 861, 936, 886, 655, 759, 482, 921, 644, 999, 817, 753, 634, 700, 653, 976, 633, 883, 972, 1022, 683, 969, 525, 628, 505, 504, 443, 965, 794, 848, 949, 1007, 903, 808, 562, 984, 498, 884, 822, 726, 959, 937, 457, 924, 557, 745, 891, 805, 776, 623, 542, 823, 514, 974, 502, 682, 676, 837, 471, 867, 958, 784, 589, 913, 846, 911, 556, 889, 774, 449, 396, 888, 579, 713, 475, 849, 740, 840, 831, 712, 592, 769, 829, 929, 440, 853, 515, 520, 286, 646, 953, 890, 659, 661, 630, 707, 1017, 489, 602, 665, 548, 715, 605, 870, 762, 233, 388, 1018, 768, 948, 508, 1006, 997, 917, 597, 601, 468, 756, 648, 927, 803, 716, 571, 941, 925, 864, 881, 944, 523, 827, 640, 992, 751, 770, 591, 1008, 962, 983, 270, 1014, 420, 673, 493, 757, 460, 466, 991, 858, 412, 857, 880, 636, 1019, 893, 663, 749, 616, 608, 610, 771, 559, 811, 754, 581, 1009, 392, 319, 406, 728, 876, 947, 940, 536, 795, 788, 873, 607, 907, 866, 488, 366, 804, 309, 950, 709, 860, 334, 783, 565, 679, 612, 760, 744, 439, 1013, 897, 800, 593, 328, 906, 537, 577, 416, 1020, 717, 967, 952, 865, 706, 1004, 834, 956, 845, 919, 400, 964, 649, 978, 599, 836, 656, 871, 387, 642, 627, 618, 1023, 699, 529, 954, 554, 1000, 619, 882, 662, 966, 899, 862, 779, 854, 799, 431, 696, 629, 996, 790, 734, 885, 872, 430, 584, 981, 915, 875, 694, 856, 575, 943, 930, 963, 796, 960, 613, 985, 766, 912, 764, 850, 722, 847, 687, 725, 587, 698, 567, 990, 677, 741, 434, 697, 333, 294, 544, 814, 896, 650, 313, 755, 688, 695, 637, 739, 975, 905, 942, 918, 657, 910, 714);
    }

    @Test(timeout = LONGTIMEOUT)
    public void test_add_Random_Full() {
        if (!UNLEASH_FULL) { return; }
        Random rand = new Random(4);

        // Test a bunch of times
        for (int i = 0; i < 200; i++) {
            // Make a big random heap
            multiAdd(randList(rand, 6000));

            // Check that it is a heap
            ArrayList<Integer> diff = new ArrayList<>();
            if (!isHeap(heapArray(), diff)) {
                printDiff("test_add_Random_Full", diff);
                assertTrue("Not a heap!", false);
            }
        }
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_remove_NoSuchElementException() {
        heap.remove();
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_to_Empty() {
        make(404);
        assertEquals((Integer) 404, heap.remove());
        definitelyEquals();
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_12_elements_using_Constructor_from_Sorted() {
        make(range(12));
        assertRemoveOrder();
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_12_elements_using_Constructor_from_Reverse_Sorted() {
        make(range(12, 0, -1));
        assertRemoveOrder();
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_12_elements_using_Constructor_1() {
        make(4, 10, 3, 11, 8, 1, 6, 12, 5, 2, 9, 7);
        assertRemoveOrder();
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_12_elements_using_Constructor_2() {
        make(3, 5, 4, 10, 12, 9, 11, 6, 8, 1, 2, 7);
        assertRemoveOrder();
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_12_elements_using_Constructor_3() {
        make(3, 8, 6, 5, 11, 1, 9, 2, 7, 12, 10, 4);
        assertRemoveOrder();
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_12_elements_using_Add_from_Sorted() {
        multiAdd(range(12));
        assertRemoveOrder();
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_12_elements_using_Add_from_Reverse_Sorted() {
        multiAdd(range(12, 0, -1));
        assertRemoveOrder();
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_12_elements_using_Add_1() {
        multiAdd(4, 7, 2, 5, 1, 10, 6, 8, 12, 3, 11, 9);
        assertRemoveOrder();
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_12_elements_using_Add_2() {
        multiAdd(11, 4, 5, 9, 1, 8, 2, 12, 10, 6, 7, 3);
        assertRemoveOrder();
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_12_elements_using_Add_3() {
        multiAdd(5, 10, 11, 6, 4, 9, 3, 7, 1, 2, 12, 8);
        assertRemoveOrder();
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_Random_Small_with_Constructor() {
        Random rand = new Random(5);

        // Test five times
        for (int i = 0; i < 5; i++) {
            make(randList(rand, 30));
            assertRemoveOrder();
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_Random_Small_with_Add() {
        Random rand = new Random(6);

        // Test five times
        for (int i = 0; i < 5; i++) {
            multiAdd(randList(rand, 30));
            assertRemoveOrder();
        }
    }

    @Test(timeout = LONGTIMEOUT)
    public void test_remove_9_elements_using_Constructor_Full() {
        if (!UNLEASH_FULL) { return; }

        for (ArrayList<Integer> p : permutate(9)) {
            make(p);
            assertRemoveOrder();
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_1023_elements_using_Constructor_from_Sorted_Full() {
        if (!UNLEASH_FULL) { return; }
        ArrayList<Integer> r = range(1023);
        make(r);
        assertRemoveOrder();
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_1023_elements_using_Constructor_from_Reverse_Sorted_Full() {
        if (!UNLEASH_FULL) { return; }
        ArrayList<Integer> r = range(1023, 0, -1);
        make(r);
        assertRemoveOrder();
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_1023_elements_using_Constructor_Full() {
        if (!UNLEASH_FULL) { return; }
        make(36, 496, 53, 989, 721, 24, 941, 195, 949, 481, 698, 18, 130, 367, 585, 83, 129, 526, 3, 439, 372, 730, 326, 501, 774, 758, 619, 155, 915, 940, 544, 35, 816, 880, 74, 932, 979, 568, 804, 398, 63, 90, 602, 574, 341, 178, 160, 927, 370, 593, 964, 322, 993, 986, 705, 716, 276, 297, 261, 344, 753, 447, 12, 764, 866, 1019, 731, 380, 315, 104, 307, 291, 855, 361, 652, 382, 156, 218, 775, 455, 839, 605, 971, 902, 379, 350, 570, 254, 664, 17, 397, 751, 906, 584, 167, 28, 548, 743, 475, 675, 1021, 883, 1006, 169, 636, 248, 976, 712, 759, 358, 760, 513, 497, 762, 685, 269, 184, 490, 887, 222, 368, 579, 553, 616, 507, 890, 120, 613, 597, 68, 116, 20, 992, 791, 164, 147, 656, 561, 223, 984, 795, 820, 113, 779, 245, 517, 146, 391, 172, 821, 997, 470, 302, 701, 357, 610, 884, 898, 179, 734, 918, 640, 1005, 538, 643, 467, 648, 525, 970, 948, 111, 575, 889, 489, 273, 550, 288, 923, 566, 755, 930, 589, 423, 560, 768, 952, 73, 809, 180, 438, 540, 161, 394, 665, 506, 857, 749, 581, 793, 451, 742, 893, 57, 44, 536, 556, 847, 10, 931, 708, 442, 309, 944, 813, 114, 331, 834, 137, 84, 710, 707, 723, 329, 157, 75, 829, 700, 532, 405, 806, 983, 181, 205, 456, 19, 962, 815, 711, 695, 246, 311, 800, 960, 1007, 426, 1010, 549, 724, 23, 360, 332, 227, 168, 790, 850, 437, 162, 206, 95, 583, 483, 796, 123, 837, 292, 305, 1011, 69, 266, 879, 725, 480, 824, 151, 671, 253, 943, 639, 428, 407, 608, 524, 564, 217, 807, 263, 42, 722, 736, 406, 502, 607, 792, 745, 515, 176, 785, 145, 29, 541, 271, 1009, 363, 682, 980, 131, 340, 452, 846, 872, 54, 477, 486, 732, 877, 466, 786, 300, 735, 371, 965, 563, 112, 474, 353, 644, 727, 324, 1022, 7, 409, 626, 272, 362, 634, 748, 911, 530, 96, 436, 412, 693, 85, 528, 696, 818, 318, 354, 627, 845, 277, 892, 202, 203, 599, 444, 875, 328, 511, 293, 645, 473, 926, 396, 625, 190, 133, 26, 868, 400, 71, 465, 922, 77, 825, 336, 14, 741, 798, 435, 50, 933, 904, 216, 441, 138, 399, 609, 801, 364, 600, 752, 479, 594, 330, 46, 459, 62, 434, 306, 674, 518, 988, 686, 559, 462, 310, 163, 194, 867, 916, 236, 996, 16, 213, 234, 920, 100, 375, 79, 377, 534, 37, 158, 43, 453, 387, 951, 135, 611, 239, 921, 389, 333, 747, 848, 844, 687, 417, 242, 666, 990, 265, 458, 900, 784, 651, 637, 592, 814, 91, 355, 621, 773, 469, 384, 241, 134, 228, 303, 64, 917, 460, 419, 728, 969, 445, 750, 678, 313, 420, 346, 519, 803, 1020, 531, 765, 298, 937, 947, 661, 432, 1, 92, 630, 122, 529, 8, 72, 209, 450, 410, 991, 842, 11, 998, 740, 1013, 484, 849, 334, 737, 6, 744, 587, 995, 325, 853, 260, 659, 403, 185, 204, 624, 175, 219, 782, 527, 278, 294, 4, 304, 256, 557, 52, 999, 897, 959, 229, 799, 109, 558, 810, 183, 778, 631, 107, 237, 533, 663, 929, 65, 919, 896, 226, 981, 699, 649, 521, 578, 676, 783, 812, 197, 629, 660, 244, 2, 771, 251, 878, 499, 141, 946, 1016, 478, 713, 739, 464, 794, 854, 94, 945, 299, 508, 285, 440, 159, 106, 827, 967, 1003, 873, 653, 274, 662, 257, 1023, 776, 925, 211, 383, 49, 249, 132, 388, 811, 539, 852, 319, 935, 275, 232, 443, 55, 781, 41, 769, 148, 252, 189, 851, 635, 717, 424, 680, 703, 808, 488, 15, 422, 142, 235, 188, 233, 928, 296, 604, 503, 936, 140, 957, 512, 702, 449, 425, 373, 552, 338, 542, 692, 491, 650, 61, 863, 766, 416, 633, 715, 30, 208, 891, 729, 869, 212, 669, 770, 654, 958, 780, 841, 316, 567, 620, 787, 221, 628, 888, 191, 283, 720, 697, 905, 871, 250, 934, 34, 153, 966, 48, 471, 393, 617, 214, 861, 143, 97, 546, 746, 127, 1002, 973, 385, 994, 843, 642, 874, 596, 468, 572, 618, 240, 882, 33, 500, 763, 586, 224, 498, 255, 858, 47, 955, 535, 591, 545, 139, 901, 924, 58, 835, 56, 301, 351, 797, 907, 386, 395, 270, 381, 108, 802, 691, 1014, 38, 150, 975, 862, 352, 657, 427, 647, 279, 166, 823, 641, 492, 314, 772, 856, 670, 598, 463, 210, 876, 638, 555, 177, 312, 230, 972, 317, 231, 968, 859, 186, 280, 623, 601, 220, 694, 448, 258, 165, 914, 756, 59, 9, 149, 39, 13, 864, 913, 144, 831, 225, 832, 87, 461, 580, 961, 173, 977, 688, 348, 1012, 805, 78, 614, 571, 719, 903, 573, 761, 836, 282, 290, 833, 323, 76, 738, 714, 590, 684, 1017, 337, 124, 67, 953, 978, 516, 733, 187, 93, 881, 404, 777, 767, 505, 287, 482, 909, 838, 950, 690, 51, 170, 667, 238, 22, 615, 99, 956, 378, 27, 199, 754, 356, 88, 672, 286, 198, 98, 431, 413, 82, 89, 192, 414, 476, 335, 401, 840, 295, 510, 117, 908, 429, 66, 1001, 788, 390, 709, 125, 200, 376, 504, 418, 828, 819, 860, 126, 60, 81, 421, 681, 171, 886, 494, 547, 885, 174, 347, 21, 577, 86, 588, 789, 118, 119, 284, 128, 102, 457, 31, 543, 677, 201, 80, 537, 987, 308, 963, 268, 942, 365, 281, 939, 430, 493, 632, 514, 446, 115, 25, 569, 1008, 870, 402, 121, 289, 679, 267, 912, 103, 655, 264, 523, 105, 349, 196, 817, 154, 343, 938, 668, 262, 1000, 193, 551, 472, 520, 152, 982, 411, 974, 894, 658, 562, 366, 646, 673, 895, 40, 415, 408, 595, 582, 359, 757, 70, 110, 822, 606, 910, 433, 622, 865, 136, 985, 689, 215, 342, 612, 522, 495, 487, 207, 704, 243, 706, 826, 576, 899, 718, 509, 247, 45, 485, 603, 554, 320, 5, 1018, 101, 327, 182, 726, 374, 259, 454, 345, 954, 369, 683, 565, 321, 1015, 392, 339, 830, 32, 1004);
        assertRemoveOrder();
    }

    @Test(timeout = LONGTIMEOUT * 2)
    public void test_remove_9_elements_using_Add_Full() {
        if (!UNLEASH_FULL) { return; }

        for (ArrayList<Integer> p : permutate(9)) {
            multiAdd(p);
            assertRemoveOrder();
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_1023_elements_using_Add_from_Sorted_Full() {
        if (!UNLEASH_FULL) { return; }
        ArrayList<Integer> r = range(1023);
        multiAdd(r);
        assertRemoveOrder();
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_1023_elements_using_Add_from_Reverse_Sorted_Full() {
        if (!UNLEASH_FULL) { return; }
        ArrayList<Integer> r = range(1023, 0, -1);
        multiAdd(r);
        assertRemoveOrder();
    }

    @Test(timeout = TIMEOUT)
    public void test_remove_1023_elements_using_Add_Full() {
        if (!UNLEASH_FULL) { return; }
        multiAdd(35, 828, 955, 599, 846, 805, 562, 85, 309, 824, 466, 757, 535, 693, 222, 496, 235, 382, 180, 461, 682, 567, 26, 75, 756, 598, 411, 303, 976, 456, 925, 900, 840, 24, 61, 338, 427, 571, 1009, 1007, 13, 845, 596, 81, 195, 921, 454, 407, 722, 891, 295, 485, 324, 653, 79, 122, 549, 251, 912, 237, 153, 486, 1014, 77, 506, 799, 960, 429, 614, 579, 572, 919, 747, 21, 794, 164, 140, 942, 660, 514, 60, 989, 450, 441, 391, 578, 570, 963, 822, 438, 137, 190, 88, 328, 898, 937, 415, 268, 555, 290, 959, 547, 1, 1020, 909, 106, 31, 277, 994, 888, 744, 404, 564, 259, 337, 872, 775, 1010, 71, 451, 165, 274, 651, 297, 524, 916, 375, 68, 452, 420, 685, 787, 559, 871, 592, 269, 473, 393, 199, 809, 905, 390, 911, 758, 886, 839, 918, 545, 813, 124, 738, 556, 934, 752, 557, 435, 714, 687, 95, 292, 78, 720, 289, 967, 311, 523, 306, 157, 874, 261, 624, 472, 703, 154, 632, 807, 345, 745, 376, 437, 690, 924, 978, 243, 266, 966, 474, 508, 244, 812, 141, 90, 69, 645, 479, 892, 105, 23, 760, 236, 770, 409, 203, 471, 455, 788, 1018, 974, 613, 417, 739, 234, 501, 622, 938, 885, 22, 39, 174, 663, 878, 879, 6, 125, 620, 117, 56, 226, 210, 957, 115, 260, 151, 86, 880, 672, 853, 146, 968, 229, 991, 551, 332, 25, 283, 586, 618, 1019, 422, 691, 498, 783, 652, 733, 333, 766, 371, 477, 408, 444, 1002, 647, 457, 44, 359, 353, 304, 504, 946, 965, 634, 988, 421, 643, 17, 602, 459, 396, 468, 464, 883, 82, 603, 424, 462, 1015, 273, 114, 20, 626, 355, 143, 540, 844, 320, 87, 232, 947, 319, 718, 212, 196, 300, 291, 949, 694, 679, 58, 5, 238, 922, 410, 322, 533, 231, 605, 4, 67, 536, 326, 816, 950, 743, 522, 795, 3, 349, 385, 315, 964, 868, 27, 800, 568, 342, 356, 489, 639, 893, 670, 36, 370, 220, 544, 847, 361, 512, 782, 53, 480, 166, 587, 593, 573, 488, 341, 187, 172, 66, 386, 851, 727, 829, 247, 145, 852, 499, 467, 990, 849, 298, 215, 134, 123, 865, 615, 163, 183, 869, 484, 763, 979, 1005, 518, 574, 866, 29, 932, 445, 59, 901, 416, 286, 857, 580, 104, 931, 97, 276, 414, 1000, 28, 944, 139, 920, 362, 89, 697, 182, 606, 239, 548, 539, 98, 70, 19, 186, 401, 895, 116, 943, 825, 372, 119, 695, 344, 675, 704, 177, 378, 952, 430, 998, 873, 635, 981, 354, 192, 958, 737, 48, 776, 1022, 160, 773, 64, 561, 705, 369, 181, 969, 629, 252, 189, 552, 492, 188, 149, 294, 764, 191, 566, 734, 717, 325, 55, 832, 418, 432, 531, 463, 323, 329, 534, 224, 940, 161, 630, 301, 423, 702, 560, 1001, 500, 1013, 2, 211, 171, 497, 609, 308, 521, 725, 528, 72, 443, 358, 201, 673, 948, 502, 811, 330, 346, 777, 225, 553, 168, 51, 771, 360, 111, 350, 708, 601, 383, 642, 94, 835, 779, 135, 132, 658, 155, 786, 584, 250, 677, 493, 246, 197, 368, 439, 859, 926, 93, 65, 529, 753, 894, 1021, 707, 655, 726, 595, 858, 929, 754, 74, 941, 40, 331, 200, 984, 281, 223, 589, 41, 217, 588, 712, 109, 746, 363, 678, 288, 930, 610, 12, 99, 503, 245, 696, 402, 84, 904, 732, 176, 399, 470, 11, 167, 591, 433, 842, 939, 1016, 413, 887, 638, 790, 400, 446, 336, 487, 563, 659, 265, 108, 977, 855, 49, 287, 249, 92, 394, 927, 133, 923, 928, 431, 313, 96, 364, 554, 684, 257, 34, 793, 138, 8, 230, 50, 208, 318, 481, 343, 731, 513, 728, 436, 889, 148, 899, 1008, 804, 527, 194, 448, 992, 339, 715, 285, 267, 796, 537, 836, 637, 144, 519, 517, 465, 997, 755, 856, 476, 983, 520, 374, 815, 32, 193, 709, 711, 864, 263, 440, 792, 406, 16, 823, 854, 785, 716, 616, 612, 120, 884, 831, 781, 993, 327, 801, 791, 490, 227, 509, 83, 253, 91, 46, 761, 818, 723, 686, 129, 668, 648, 162, 881, 817, 381, 483, 838, 216, 890, 218, 510, 762, 334, 299, 980, 774, 158, 700, 876, 701, 136, 392, 1012, 850, 625, 961, 185, 264, 214, 806, 367, 819, 280, 317, 128, 604, 971, 241, 405, 661, 619, 594, 657, 577, 9, 617, 877, 434, 995, 538, 542, 875, 307, 550, 962, 719, 516, 482, 600, 206, 565, 662, 759, 100, 384, 906, 278, 121, 460, 442, 507, 366, 242, 826, 688, 150, 302, 860, 198, 546, 676, 491, 767, 494, 636, 142, 671, 1011, 357, 282, 76, 608, 296, 202, 312, 698, 917, 896, 63, 915, 175, 262, 515, 713, 841, 913, 914, 101, 633, 951, 147, 38, 656, 131, 699, 255, 582, 778, 903, 204, 863, 765, 395, 741, 808, 861, 650, 768, 751, 996, 127, 178, 607, 820, 365, 426, 447, 275, 649, 669, 469, 80, 985, 674, 110, 982, 43, 305, 52, 532, 388, 73, 569, 10, 412, 1004, 644, 749, 910, 541, 102, 389, 213, 936, 184, 590, 769, 7, 419, 689, 664, 173, 221, 33, 833, 205, 748, 347, 169, 316, 348, 870, 373, 681, 272, 827, 803, 511, 449, 351, 680, 1023, 908, 380, 798, 30, 54, 179, 986, 814, 126, 314, 706, 428, 830, 387, 397, 999, 750, 667, 340, 37, 576, 352, 631, 478, 57, 897, 495, 907, 970, 284, 233, 933, 710, 797, 583, 692, 623, 640, 112, 837, 258, 335, 159, 575, 729, 152, 543, 802, 862, 526, 170, 973, 530, 882, 585, 293, 156, 628, 425, 45, 736, 271, 1017, 248, 843, 14, 403, 945, 228, 209, 821, 581, 377, 310, 665, 611, 398, 279, 902, 683, 834, 525, 18, 597, 789, 1006, 321, 810, 740, 935, 953, 62, 724, 975, 867, 956, 646, 954, 784, 1003, 558, 742, 256, 972, 735, 118, 730, 240, 772, 219, 475, 453, 254, 207, 666, 641, 42, 780, 130, 113, 848, 987, 270, 15, 654, 721, 107, 379, 627, 458, 505, 621, 47, 103);
        assertRemoveOrder();
    }

    @Test(timeout = LONGTIMEOUT)
    public void test_remove_Random_with_Constructor_Full() {
        if (!UNLEASH_FULL) { return; }
        Random rand = new Random(7);

        // Test a bunch of times
        for (int i = 0; i < 300; i++) {
            make(randList(rand, 8000));
            assertRemoveOrder();
        }
    }

    @Test(timeout = LONGTIMEOUT)
    public void test_remove_Random_with_Add_Full() {
        if (!UNLEASH_FULL) { return; }
        Random rand = new Random(7);

        // Test a bunch of times
        for (int i = 0; i < 250; i++) {
            multiAdd(randList(rand, 7000));
            assertRemoveOrder();
        }
    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void test_getMin_NoSuchElementException() {
        heap.getMin();
    }

    @Test(timeout = TIMEOUT)
    public void test_getMin_One_Element() {
        make(404);
        assertEquals((Integer) 404, heap.remove());
    }

    @Test(timeout = TIMEOUT)
    public void test_getMin_12_elements_from_Sorted() {
        assertMinOverRange(range(12));
    }

    @Test(timeout = TIMEOUT)
    public void test_getMin_12_elements_from_Reverse_Sorted() {
        assertMinOverRange(range(12, 0, -1));
    }

    @Test(timeout = TIMEOUT)
    public void test_getMin_Random_Small() {
        Random rand = new Random(9);

        // Test five times
        for (int i = 0; i < 5; i++) {
            assertMinOverRange(randList(rand, 30));
        }
    }

    @Test(timeout = LONGTIMEOUT * 2)
    public void test_getMin_9_elements_Full() {
        if (!UNLEASH_FULL) { return; }

        for (ArrayList<Integer> p : permutate(9)) {
            assertMinOverRange(p);
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_getMin_1023_elements_from_Sorted_Full() {
        if (!UNLEASH_FULL) { return; }
        assertMinOverRange(range(1023));
    }

    @Test(timeout = TIMEOUT)
    public void test_getMin_1023_elements_from_Reverse_Sorted_Full() {
        if (!UNLEASH_FULL) { return; }
        assertMinOverRange(range(1023, 0, -1));
    }

    @Test(timeout = TIMEOUT)
    public void test_getMin_1023_elements() {
        if (!UNLEASH_FULL) { return; }
        assertMinOverRange(with(35, 828, 955, 599, 846, 805, 562, 85, 309, 824, 466, 757, 535, 693, 222, 496, 235, 382, 180, 461, 682, 567, 26, 75, 756, 598, 411, 303, 976, 456, 925, 900, 840, 24, 61, 338, 427, 571, 1009, 1007, 13, 845, 596, 81, 195, 921, 454, 407, 722, 891, 295, 485, 324, 653, 79, 122, 549, 251, 912, 237, 153, 486, 1014, 77, 506, 799, 960, 429, 614, 579, 572, 919, 747, 21, 794, 164, 140, 942, 660, 514, 60, 989, 450, 441, 391, 578, 570, 963, 822, 438, 137, 190, 88, 328, 898, 937, 415, 268, 555, 290, 959, 547, 1, 1020, 909, 106, 31, 277, 994, 888, 744, 404, 564, 259, 337, 872, 775, 1010, 71, 451, 165, 274, 651, 297, 524, 916, 375, 68, 452, 420, 685, 787, 559, 871, 592, 269, 473, 393, 199, 809, 905, 390, 911, 758, 886, 839, 918, 545, 813, 124, 738, 556, 934, 752, 557, 435, 714, 687, 95, 292, 78, 720, 289, 967, 311, 523, 306, 157, 874, 261, 624, 472, 703, 154, 632, 807, 345, 745, 376, 437, 690, 924, 978, 243, 266, 966, 474, 508, 244, 812, 141, 90, 69, 645, 479, 892, 105, 23, 760, 236, 770, 409, 203, 471, 455, 788, 1018, 974, 613, 417, 739, 234, 501, 622, 938, 885, 22, 39, 174, 663, 878, 879, 6, 125, 620, 117, 56, 226, 210, 957, 115, 260, 151, 86, 880, 672, 853, 146, 968, 229, 991, 551, 332, 25, 283, 586, 618, 1019, 422, 691, 498, 783, 652, 733, 333, 766, 371, 477, 408, 444, 1002, 647, 457, 44, 359, 353, 304, 504, 946, 965, 634, 988, 421, 643, 17, 602, 459, 396, 468, 464, 883, 82, 603, 424, 462, 1015, 273, 114, 20, 626, 355, 143, 540, 844, 320, 87, 232, 947, 319, 718, 212, 196, 300, 291, 949, 694, 679, 58, 5, 238, 922, 410, 322, 533, 231, 605, 4, 67, 536, 326, 816, 950, 743, 522, 795, 3, 349, 385, 315, 964, 868, 27, 800, 568, 342, 356, 489, 639, 893, 670, 36, 370, 220, 544, 847, 361, 512, 782, 53, 480, 166, 587, 593, 573, 488, 341, 187, 172, 66, 386, 851, 727, 829, 247, 145, 852, 499, 467, 990, 849, 298, 215, 134, 123, 865, 615, 163, 183, 869, 484, 763, 979, 1005, 518, 574, 866, 29, 932, 445, 59, 901, 416, 286, 857, 580, 104, 931, 97, 276, 414, 1000, 28, 944, 139, 920, 362, 89, 697, 182, 606, 239, 548, 539, 98, 70, 19, 186, 401, 895, 116, 943, 825, 372, 119, 695, 344, 675, 704, 177, 378, 952, 430, 998, 873, 635, 981, 354, 192, 958, 737, 48, 776, 1022, 160, 773, 64, 561, 705, 369, 181, 969, 629, 252, 189, 552, 492, 188, 149, 294, 764, 191, 566, 734, 717, 325, 55, 832, 418, 432, 531, 463, 323, 329, 534, 224, 940, 161, 630, 301, 423, 702, 560, 1001, 500, 1013, 2, 211, 171, 497, 609, 308, 521, 725, 528, 72, 443, 358, 201, 673, 948, 502, 811, 330, 346, 777, 225, 553, 168, 51, 771, 360, 111, 350, 708, 601, 383, 642, 94, 835, 779, 135, 132, 658, 155, 786, 584, 250, 677, 493, 246, 197, 368, 439, 859, 926, 93, 65, 529, 753, 894, 1021, 707, 655, 726, 595, 858, 929, 754, 74, 941, 40, 331, 200, 984, 281, 223, 589, 41, 217, 588, 712, 109, 746, 363, 678, 288, 930, 610, 12, 99, 503, 245, 696, 402, 84, 904, 732, 176, 399, 470, 11, 167, 591, 433, 842, 939, 1016, 413, 887, 638, 790, 400, 446, 336, 487, 563, 659, 265, 108, 977, 855, 49, 287, 249, 92, 394, 927, 133, 923, 928, 431, 313, 96, 364, 554, 684, 257, 34, 793, 138, 8, 230, 50, 208, 318, 481, 343, 731, 513, 728, 436, 889, 148, 899, 1008, 804, 527, 194, 448, 992, 339, 715, 285, 267, 796, 537, 836, 637, 144, 519, 517, 465, 997, 755, 856, 476, 983, 520, 374, 815, 32, 193, 709, 711, 864, 263, 440, 792, 406, 16, 823, 854, 785, 716, 616, 612, 120, 884, 831, 781, 993, 327, 801, 791, 490, 227, 509, 83, 253, 91, 46, 761, 818, 723, 686, 129, 668, 648, 162, 881, 817, 381, 483, 838, 216, 890, 218, 510, 762, 334, 299, 980, 774, 158, 700, 876, 701, 136, 392, 1012, 850, 625, 961, 185, 264, 214, 806, 367, 819, 280, 317, 128, 604, 971, 241, 405, 661, 619, 594, 657, 577, 9, 617, 877, 434, 995, 538, 542, 875, 307, 550, 962, 719, 516, 482, 600, 206, 565, 662, 759, 100, 384, 906, 278, 121, 460, 442, 507, 366, 242, 826, 688, 150, 302, 860, 198, 546, 676, 491, 767, 494, 636, 142, 671, 1011, 357, 282, 76, 608, 296, 202, 312, 698, 917, 896, 63, 915, 175, 262, 515, 713, 841, 913, 914, 101, 633, 951, 147, 38, 656, 131, 699, 255, 582, 778, 903, 204, 863, 765, 395, 741, 808, 861, 650, 768, 751, 996, 127, 178, 607, 820, 365, 426, 447, 275, 649, 669, 469, 80, 985, 674, 110, 982, 43, 305, 52, 532, 388, 73, 569, 10, 412, 1004, 644, 749, 910, 541, 102, 389, 213, 936, 184, 590, 769, 7, 419, 689, 664, 173, 221, 33, 833, 205, 748, 347, 169, 316, 348, 870, 373, 681, 272, 827, 803, 511, 449, 351, 680, 1023, 908, 380, 798, 30, 54, 179, 986, 814, 126, 314, 706, 428, 830, 387, 397, 999, 750, 667, 340, 37, 576, 352, 631, 478, 57, 897, 495, 907, 970, 284, 233, 933, 710, 797, 583, 692, 623, 640, 112, 837, 258, 335, 159, 575, 729, 152, 543, 802, 862, 526, 170, 973, 530, 882, 585, 293, 156, 628, 425, 45, 736, 271, 1017, 248, 843, 14, 403, 945, 228, 209, 821, 581, 377, 310, 665, 611, 398, 279, 902, 683, 834, 525, 18, 597, 789, 1006, 321, 810, 740, 935, 953, 62, 724, 975, 867, 956, 646, 954, 784, 1003, 558, 742, 256, 972, 735, 118, 730, 240, 772, 219, 475, 453, 254, 207, 666, 641, 42, 780, 130, 113, 848, 987, 270, 15, 654, 721, 107, 379, 627, 458, 505, 621, 47, 103));
    }

    @Test(timeout = LONGTIMEOUT * 2)
    public void test_getMin_Random_Full() {
        if (!UNLEASH_FULL) { return; }
        Random rand = new Random(10);

        // Test a bunch of times
        for (int i = 0; i < 200; i++) {
            assertMinOverRange(randList(rand, 2000));
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_isEmpty_when_Empty() {
        assertTrue(heap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void test_isEmpty_Not_Empty() {
        make(1);
        assertFalse(heap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void test_clear() {
        assertEquals(0, heap.size());
        assertEquals(13, heapArray().length);
    }

    @Test(timeout = TIMEOUT)
    public void test_Full_Send_Random_add_remove_cycle() {
        Random rand = new Random(11);

        make(randList(rand, 30));
        for (int i = 0; i < 5; i++) {
            assertRemoveOrder();

            multiAdd(randList(rand, 30));

            ArrayList<Integer> diff = new ArrayList<>();
            if (!isHeap(heapArray(), diff)) {
                printDiff("test_add_Random_Full", diff);
                assertTrue("Not a heap!", false);
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_Full_Send_Random_add_clear_cycle() {
        Random rand = new Random(12);

        make(randList(rand, 30));
        for (int i = 0; i < 5; i++) {
            heap.clear();

            multiAdd(randList(rand, 30));

            ArrayList<Integer> diff = new ArrayList<>();
            if (!isHeap(heapArray(), diff)) {
                printDiff("test_add_Random_Full", diff);
                assertTrue("Not a heap!", false);
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_Full_Send_Random_add_remove_add_clear_cycle() {
        Random rand = new Random(13);

        make(randList(rand, 30));
        for (int i = 0; i < 5; i++) {
            assertRemoveOrder();

            multiAdd(randList(rand, 30));

            ArrayList<Integer> diff = new ArrayList<>();
            if (!isHeap(heapArray(), diff)) {
                printDiff("test_add_Random_Full", diff);
                assertTrue("Not a heap!", false);
            }

            heap.clear();

            multiAdd(randList(rand, 30));

            diff = new ArrayList<>();
            if (!isHeap(heapArray(), diff)) {
                printDiff("test_add_Random_Full", diff);
                assertTrue("Not a heap!", false);
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void test_Full_Send_Random_add_clear_add_remove_cycle() {
        Random rand = new Random(14);

        make(randList(rand, 30));
        for (int i = 0; i < 5; i++) {
            heap.clear();

            multiAdd(randList(rand, 30));

            ArrayList<Integer> diff = new ArrayList<>();
            if (!isHeap(heapArray(), diff)) {
                printDiff("test_add_Random_Full", diff);
                assertTrue("Not a heap!", false);
            }

            assertRemoveOrder();

            multiAdd(randList(rand, 30));

            diff = new ArrayList<>();
            if (!isHeap(heapArray(), diff)) {
                printDiff("test_add_Random_Full", diff);
                assertTrue("Not a heap!", false);
            }
        }
    }

    @Test(timeout = LONGTIMEOUT)
    public void test_Full_Send_Random_add_remove_Full() {
        if (!UNLEASH_FULL) { return; }
        Random rand = new Random(15);

        make(randList(rand, 4000));
        for (int i = 0; i < 500; i++) {
            assertRemoveOrder();

            multiAdd(randList(rand, 4000));

            ArrayList<Integer> diff = new ArrayList<>();
            if (!isHeap(heapArray(), diff)) {
                printDiff("test_add_Random_Full", diff);
                assertTrue("Not a heap!", false);
            }
        }
    }

    @Test(timeout = LONGTIMEOUT)
    public void test_Full_Send_Random_add_clear_Full() {
        if (!UNLEASH_FULL) { return; }
        Random rand = new Random(16);

        make(randList(rand, 4000));
        for (int i = 0; i < 500; i++) {
            heap.clear();

            multiAdd(randList(rand, 4000));

            ArrayList<Integer> diff = new ArrayList<>();
            if (!isHeap(heapArray(), diff)) {
                printDiff("test_add_Random_Full", diff);
                assertTrue("Not a heap!", false);
            }
        }
    }

    /**
     * A small social experiment. Hopefully the TAs don't hate too much.
     */
    @Test(timeout = TIMEOUT)
    public void test_venmo() {
        Random rand = new Random(System.nanoTime());
        if (rand.nextInt(7) == 0 || (UNLEASH_FULL && rand.nextInt(3) == 0)) {
            System.out.println();
            System.out.println("my venmo is @arcwand");
            System.out.println("https://venmo.com/u/arcwand");
            System.out.println("jkjk");
            System.out.println("unless... ?");
            System.out.println(";D");
        }
    }

    @BeforeClass
    @AfterClass
    public static void note() {
        if (!UNLEASH_FULL) {
            System.out.println();
            System.out.println("Once you're feeling confident about your code, set UNLEASH_FULL to true!");
            System.out.println();
        }
    }

}