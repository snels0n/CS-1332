import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ashwin Mudaliar
 * @version 1.2.2
 * 
 * Shoutout Raymond Bian for providing the spurious hit case
 * 
 */

public class PatternMatchingComprehensive {

    private CharacterComparator cc;
    private static final int TIMEOUT = 200;

    @Before
    public void setup() {
        cc = new CharacterComparator();
    }

    private List<Integer> makeList(int[] arr) {

        List<Integer> ret = new ArrayList<>();

        for (int x : arr) {
            ret.add(x);
        }

        return ret;

    }

    private Map<Character, Integer> makeMap(char[] chars, int[] occurrences) {

        if (chars.length != occurrences.length) {
            throw new java.lang.IllegalArgumentException("array lengths differ");
        }

        Map<Character, Integer> ret = new HashMap<>();

        for (int k = 0; k < chars.length; k++) {

            ret.put(chars[k], occurrences[k]);

        }

        return ret;

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testKMPPatternNull() {
        PatternMatching.kmp(null, "I want to be a TA", cc);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testKMPPatternIsEmpty() {
        PatternMatching.kmp("", "I want to be a TA", cc);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testKMPTextIsNull() {
        PatternMatching.kmp("I want to be a TA", null, cc);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testKMPComparatorIsNull() {
        PatternMatching.kmp("I want to be a TA", "Someone please make me a TA", null);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testBuildFailureTablePatternIsNull() {
        PatternMatching.buildFailureTable(null, cc);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testBuildFailureTableComparatorIsNull() {
        PatternMatching.buildFailureTable("something", null);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testBoyerMoorePatternNull() {
        PatternMatching.boyerMoore(null, "I want to be a TA", cc);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testBoyerMoorePatternIsEmpty() {
        PatternMatching.boyerMoore("", "I want to be a TA", cc);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testBoyerMooreTextIsNull() {
        PatternMatching.boyerMoore("I want to be a TA", null, cc);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testBoyerMooreComparatorIsNull() {
        PatternMatching.boyerMoore("I want to be a TA", "Someone please make me a TA", null);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testBuildLastTablePatternIsNull() {
        PatternMatching.buildLastTable(null);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testRabinKarpPatternNull() {
        PatternMatching.rabinKarp(null, "I want to be a TA", cc);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testRabinKarpPatternIsEmpty() {
        PatternMatching.rabinKarp("", "I want to be a TA", cc);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testRabinKarpTextIsNull() {
        PatternMatching.rabinKarp("I want to be a TA", null, cc);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testRabinKarpComparatorIsNull() {
        PatternMatching.rabinKarp("I want to be a TA", "Someone please make me a TA", null);
    }


    @Test(timeout = TIMEOUT)
    public void testBuildFailureTable1() {

        String message = "Failed Build Failure Table";
        CharSequence test = "abaaaaa";

        int[] expected = {0, 0, 1, 1, 1, 1, 1};

        assertArrayEquals(message, expected, PatternMatching.buildFailureTable(test, cc));
        assertEquals((Integer) 10, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testBuildFailureTable2() {

        String message = "Failed build failure table test";
        CharSequence test = "ababab";
        int comparisons = 5;

        int[] expected = {0, 0, 1, 2, 3, 4};

        assertArrayEquals(message, expected, PatternMatching.buildFailureTable(test, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testBuildFailureTable3() {

        String message = "failed build failue table test";
        CharSequence test = "abcabcdc";
        int comparisons = 8;

        int[] expected = {0, 0, 0, 1, 2, 3, 0, 0};

        assertArrayEquals(message, expected, PatternMatching.buildFailureTable(test, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testKMPMultipleMatches1() {

        String message = "";
        CharSequence text = "aaaaa";
        CharSequence pattern = "a";
        int comparisons = 5;

        int[] expectedVals = {0, 1, 2, 3, 4};
        List<Integer> expected = makeList(expectedVals);

        assertEquals(expected, PatternMatching.kmp(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testKMPMultipleMatches2() {

        String message = "";
        CharSequence text = "ababababababababa";
        CharSequence pattern = "aba";
        int comparisons = 19;

        int[] expectedVals = {0, 2, 4, 6, 8, 10, 12, 14};
        List<Integer> expected = makeList(expectedVals);

        assertEquals(expected, PatternMatching.kmp(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testKMPMultipleMatches3() {

        String message = "";
        CharSequence text = "racecaracecar";
        CharSequence pattern = "racecar";
        int comparisons = 19;

        int[] expectedVals = {0, 6};
        List<Integer> expected = makeList(expectedVals);

        assertEquals(expected, PatternMatching.kmp(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testKMPLongishText() {

        String message = "";
        CharSequence text = "I really really want to be a Teaching Assistant for this class";
        CharSequence pattern = "Assistant";
        int comparisons = 62;

        int[] expectedVals = {38};
        List<Integer> expected = makeList(expectedVals);

        assertEquals(expected, PatternMatching.kmp(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testKMPSuperLongText() {

        String message = "";
        CharSequence text = "Lorem Ipsum is simply dummy text of" 
        + "the printing and typesetting industry. Lorem Ipsum has been the industry's " 
        + "standard dummy text ever since the 1500s, when an unknown printer took a galley of" 
        + " type and scrambled it to make a type specimen book. It has survived not only five centuries," 
        + " but also the leap into electronic typesetting, remaining essentially unchanged. It" 
        + "  was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum" 
        + " passages, and more recently with desktop publishing software like Aldus PageMaker " 
        + "including versions of Lorem Ipsum";
        CharSequence pattern = "PageMaker";
        int comparisons = 573;

        int[] expectedVals = {530};
        List<Integer> expected = makeList(expectedVals);

        System.out.println(cc.getComparisonCount());

        assertEquals(expected, PatternMatching.kmp(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testKMPCaseSensitive() {

        // pretty useless test
        String message = "";
        CharSequence text = "aBcaBC";
        CharSequence pattern = "abc";
        int comparisons = 8;

        int[] expectedVals = {};
        List<Integer> expected = makeList(expectedVals);

        assertEquals(expected, PatternMatching.kmp(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testBuildLastTable1() {

        String message = "";

        CharSequence pattern = "abcabc";
        char[] chars = {'a', 'b', 'c'};
        int[] occurrences = {3, 4, 5};
        int comparisons = 0;

        Map<Character, Integer> expected = makeMap(chars, occurrences);

        assertEquals(expected, PatternMatching.buildLastTable(pattern));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testBuildLastTable2() {

        String message = "";

        CharSequence pattern = "racecar";
        char[] chars = {'r', 'a', 'c', 'e'};
        int[] occurrences = {6, 5, 4, 3};
        int comparisons = 0;

        Map<Character, Integer> expected = makeMap(chars, occurrences);

        assertEquals(expected, PatternMatching.buildLastTable(pattern));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testBuildLastTable3() {

        String message = "";

        CharSequence pattern = "alsjhvljarhgfajlrhbajwehbgla";
        char[] chars = {'a', 'l', 's', 'j', 'h', 'v'
        , 'r', 'g', 'f', 'b', 'w', 'e'};
        int[] occurrences = {27, 26, 2, 20, 23, 5, 16, 25, 12, 24, 21, 22};
        int comparisons = 0;

        Map<Character, Integer> expected = makeMap(chars, occurrences);

        assertEquals(expected, PatternMatching.buildLastTable(pattern));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreMultipleMatches1() {

        String message = "";
        CharSequence text = "aaaaa";
        CharSequence pattern = "a";
        int comparisons = 5;

        int[] expectedVals = {0, 1, 2, 3, 4};
        List<Integer> expected = makeList(expectedVals);

        assertEquals(expected, PatternMatching.boyerMoore(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreMultipleMatches2() {

        String message = "";
        CharSequence text = "ababababababababa";
        CharSequence pattern = "aba";
        int comparisons = 31;

        int[] expectedVals = {0, 2, 4, 6, 8, 10, 12, 14};
        List<Integer> expected = makeList(expectedVals);

        assertEquals(expected, PatternMatching.boyerMoore(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreMultipleMatches3() {

        String message = "";
        CharSequence text = "racecaracecar";
        CharSequence pattern = "racecar";
        int comparisons = 17;

        int[] expectedVals = {0, 6};
        List<Integer> expected = makeList(expectedVals);

        assertEquals(expected, PatternMatching.boyerMoore(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreLongishText() {

        String message = "";
        CharSequence text = "I really really want to be a Teaching Assistant for this class";
        CharSequence pattern = "Assistant";
        int comparisons = 22;

        int[] expectedVals = {38};
        List<Integer> expected = makeList(expectedVals);

        assertEquals(expected, PatternMatching.boyerMoore(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreSuperLongText() {

        String message = "";
        CharSequence text = "Lorem Ipsum is simply dummy text of" 
        + "the printing and typesetting industry. Lorem Ipsum has been the industry's " 
        + "standard dummy text ever since the 1500s, when an unknown printer took a galley of" 
        + " type and scrambled it to make a type specimen book. It has survived not only five centuries," 
        + " but also the leap into electronic typesetting, remaining essentially unchanged. It" 
        + "  was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum" 
        + " passages, and more recently with desktop publishing software like Aldus PageMaker " 
        + "including versions of Lorem Ipsum";
        CharSequence pattern = "PageMaker";
        int comparisons = 92;

        int[] expectedVals = {530};
        List<Integer> expected = makeList(expectedVals);

        assertEquals(expected, PatternMatching.boyerMoore(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreCaseSensitive() {

        // pretty useless test
        String message = "";
        CharSequence text = "aBcaBC";
        CharSequence pattern = "abc";
        int comparisons = 3;

        int[] expectedVals = {};
        List<Integer> expected = makeList(expectedVals);

        assertEquals(expected, PatternMatching.boyerMoore(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    // Normal Cases

    @Test(timeout = TIMEOUT)
    public void testRabinKarpMultipleMatches1() {

        String message = "";
        CharSequence text = "aaaaa";
        CharSequence pattern = "a";

        int comparisons = 5;

        int[] expectedVals = {0, 1, 2, 3, 4};
        List<Integer> expected = makeList(expectedVals);

        assertEquals(expected, PatternMatching.rabinKarp(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpMultipleMatches2() {

        String message = "";
        CharSequence text = "ababababababababa";
        CharSequence pattern = "aba";
        int comparisons = 24;

        int[] expectedVals = {0, 2, 4, 6, 8, 10, 12, 14};
        List<Integer> expected = makeList(expectedVals);

        assertEquals(expected, PatternMatching.rabinKarp(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpMultipleMatches3() {

        String message = "";
        CharSequence text = "racecaracecar";
        CharSequence pattern = "race";

        int comparisons = 8;

        int[] expectedVals = {0, 6};
        List<Integer> expected = makeList(expectedVals);

        assertEquals(expected, PatternMatching.rabinKarp(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpLongishText() {

        String message = "";
        CharSequence text = "weekofwelcomeissoweird";
        CharSequence pattern = "come";
        int comparisons = 4;

        int[] expectedVals = {9};
        List<Integer> expected = makeList(expectedVals);

        assertEquals(expected, PatternMatching.rabinKarp(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

    @Test(timeout = 3000)
    public void testRabinKarpEquivalentHashes() {

        CharSequence text = "ShoutOutRaymond"
            + "ABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBA"
            + "middle"
            + "BAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAAB"
            + "end";
        CharSequence pattern = "ABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBAABBABAABBAABABBABAABABBAABBABAABABBABAABBAABABBABAABABBAABBABAABBAABABBAABBABAABABBABAABBAABABBA";
        int comparisons = pattern.length() + 1;

        int[] expectedVals = {15};
        List<Integer> expected = makeList(expectedVals);

        assertEquals(expected, PatternMatching.rabinKarp(pattern, text, cc));
        assertEquals((Integer) comparisons, (Integer) cc.getComparisonCount());

    }

//    @Test(timeout = TIMEOUT)
//    public void testHashFunction() {
//
//        // CHANGE METHOD NAME WITH YOUR HELPER (make it public for now)
//
//        assertEquals((Integer) 142910419, (Integer) PatternMatching.INSERT_HASH_FUNCTION_NAME("bunn"));
//
//
//    }

    // TODO: GALIL RULE TESTS


}
