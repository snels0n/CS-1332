import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Some extra tests for the galil rule!
 *
 * @author Raymond Bian
 * @version 1.0
 */
public class GalilRuleTests {

    private static final int TIMEOUT = 200;
    private List<Integer> emptyList;
    private CharacterComparator comparator;


    @Before
    public void setUp() {
        emptyList = new ArrayList<>();
        comparator = new CharacterComparator();
    }

    @Test(timeout = TIMEOUT)
    public void displayMessage() {
        System.out.println("My first time making tests!");
        System.out.println("If you get a test case wrong, make sure to check the test method itself for more information on what you got wrong.");
        System.out.println("I've included some tables that visualize the algorithm and its optimization, please let me know on Piazza if I've made a mistake or typo.");
        System.out.println("-Raymond Bian");
    }

    @Test(timeout = TIMEOUT)
    public void testStudentBoyerMooreMatchGalilVersion() {
        /*
            text: She sells seashells by the seashore.
            pattern: sell
            build failure table comparisons: 3
            ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
            │ S │ h │ e │   │ s │ e │ l │ l │ s │   │ s │ e │ a │ s │ h │ e │ l │ l │ s │   │ b │ y │   │ t │ h │ e │   │ s │ e │ a │ s │ h │ o │ r │ e │ . │
            ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
            │ s │ e │ l │ l │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │ s │ e │ l │ l │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 4. Found pattern!
            │   │   │   │   │   │   │   │   │ s │ e │ l │ l │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │ s │ e │ l │ l │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │ s │ e │ l │ l │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 2.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ s │ e │ l │ l │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 4.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ s │ e │ l │ l │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ s │ e │ l │ l │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ s │ e │ l │ l │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ s │ e │ l │ l │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ s │ e │ l │ l │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ s │ e │ l │ l │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ s │ e │ Terminate.
            └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘
            total expected comparisons: 22
            occurrences: [4]
         */
        assertEquals(new ArrayList<>(Arrays.asList(4)),
                PatternMatching.boyerMooreGalilRule("sell", "She sells seashells by the seashore.", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 22.", 22, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testStudentBoyerMooreNoMatchGalilVersion() {
        /*
            text: sea lions trains cardinal boardwalk
            pattern: sell
            build failure table comparisons: 3
            ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
            │ s │ e │ a │   │ l │ i │ o │ n │ s │   │ t │ r │ a │ i │ n │ s │   │ c │ a │ r │ d │ i │ n │ a │ l │   │ b │ o │ a │ r │ d │ w │ a │ l │ k │
            ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
            │ s │ e │ l │ l │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │ s │ e │ l │ l │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │ s │ e │ l │ l │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │ s │ e │ l │ l │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ s │ e │ l │ l │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ s │ e │ l │ l │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ s │ e │ l │ l │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ s │ e │ l │ l │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ s │ e │ l │ l │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Terminate.
            └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘
            total expected comparisons: 12
            occurrences: []
        */
        assertEquals(emptyList,
                PatternMatching.boyerMooreGalilRule("sell",
                        "sea lions trains cardinal boardwalk", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 12.", 12, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testStudentBoyerMooreMultipleMatchesGalilVersion() {
        /*
            text: abab
            pattern: ab
            build failure table comparisons: 1
            ┌───┬───┬───┬───┐
            │ a │ b │ a │ b │
            ├───┼───┼───┼───┤
            │ a │ b │   │   │ Comparisons: 2. Found pattern!
            │   │   │ a │ b │ Comparisons: 2. Found pattern!
            │   │   │   │   │ Terminate.
            └───┴───┴───┴───┘
            total expected comparisons: 5
            occurrences: [0, 2]
        */
        assertEquals(new ArrayList<>(Arrays.asList(0, 2)),
                PatternMatching.boyerMooreGalilRule("ab",
                        "abab", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 5.", 5, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testStudentBoyerMooreLongerTextGalilVersion() {
        /*
            text: sell
            pattern: sea lions trains cardinal boardwalk
            expected total comparisons: 0
            occurrences: []
         */
        assertEquals(emptyList,
                PatternMatching.boyerMooreGalilRule("sea lions trains cardinal boardwalk",
                        "sell", comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullPattern() {
        /*
            Double check and make sure you throw the proper exceptions!
         */
        PatternMatching.boyerMooreGalilRule(null, "If you're reading this, hello!", comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testEmptyPattern() {
        /*
            Make sure you read the javadocs for each method properly!
         */
        PatternMatching.boyerMooreGalilRule("", "The mitochondria is the powerhouse of the cell", comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullText() {
        /*
            Don't want to lose points for not checking edge cases!
         */
        PatternMatching.boyerMooreGalilRule("I HATE CHECKSTYLE", null, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullComparator() {
        /*
            I'm not sure why the comparator would ever be null, but sure.
         */
        PatternMatching.boyerMooreGalilRule("Don't forget to check for any debug print statements!", "", null);
    }

    @Test(timeout = TIMEOUT)
    public void testHomeworkPDFExample() {
        /*
            text: aababba
            pattern: aaba
            build failure table comparisons: 4
            ┌───┬───┬───┬───┬───┬───┬───┐
            │ a │ a │ b │ a │ b │ b │ a │
            ├───┼───┼───┼───┼───┼───┼───┤
            │ a │ a │ b │ a │   │   │   │ Comparisons: 4. Found pattern!
            │   │   │   │ a │ a │ b │ a │ Comparisons: 3.
            │   │   │   │   │ a │ a │ b │ Terminate.
            └───┴───┴───┴───┴───┴───┴───┘
            total expected comparisons: 11
            occurrences: [0]
         */
        assertEquals(new ArrayList<>(Arrays.asList(0)),
                PatternMatching.boyerMooreGalilRule("aaba",
                        "aababba", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 11.", 11, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRandomStackoverflowPost() {
        /*
            text: bababababab
            pattern: ababa
            build failure table comparisons: 4
            ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
            │ b │ a │ b │ a │ b │ a │ b │ a │ b │ a │ b │
            ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
            │ a │ b │ a │ b │ a │   │   │   │   │   │   │ Comparisons: 1.
            │   │ a │ b │ a │ b │ a │   │   │   │   │   │ Comparisons: 5. Found pattern!
            │   │   │   │ a │ b │ a │ b │ a │   │   │   │ Comparisons: 2. Found pattern!
            │   │   │   │   │   │ a │ b │ a │ b │ a │   │ Comparisons: 2. Found pattern!
            │   │   │   │   │   │   │   │ a │ b │ a │ b │ Terminate.
            └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘
            total expected comparisons: 14
            occurrences: [1, 3, 5]
         */
        assertEquals(new ArrayList<>(Arrays.asList(1, 3, 5)),
                PatternMatching.boyerMooreGalilRule("ababa",
                        "bababababab", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 14.", 14, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testPatternAllUniqueCharacters() {
        /*
            text: thistextisuncopyrightablesoisthisunittestitisuncopyrightable
            pattern: uncopyrightable
            build failure table comparisons: 14
            ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
            │ t │ h │ i │ s │ t │ e │ x │ t │ i │ s │ u │ n │ c │ o │ p │ y │ r │ i │ g │ h │ t │ a │ b │ l │ e │ s │ o │ i │ s │ t │ h │ i │ s │ u │ n │ i │ t │ t │ e │ s │ t │ i │ t │ i │ s │ u │ n │ c │ o │ p │ y │ r │ i │ g │ h │ t │ a │ b │ l │ e │
            ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
            │ u │ n │ c │ o │ p │ y │ r │ i │ g │ h │ t │ a │ b │ l │ e │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │ u │ n │ c │ o │ p │ y │ r │ i │ g │ h │ t │ a │ b │ l │ e │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 15. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ u │ n │ c │ o │ p │ y │ r │ i │ g │ h │ t │ a │ b │ l │ e │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ u │ n │ c │ o │ p │ y │ r │ i │ g │ h │ t │ a │ b │ l │ e │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ u │ n │ c │ o │ p │ y │ r │ i │ g │ h │ t │ a │ b │ l │ e │ Comparisons: 15. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Terminate.
            └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘
            total expected comparisons: 47
            occurrences: [10, 45]
         */
        assertEquals(new ArrayList<>(Arrays.asList(10, 45)),
                PatternMatching.boyerMooreGalilRule("uncopyrightable",
                        "thistextisuncopyrightablesoisthisunittestitisuncopyrightable", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 47.", 47, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testShiftPeriodicity() {
        /*
            text: baabaabaabaabaabaabaabaabbabaabaaba
            pattern: aabaa
            build failure table comparisons: 5
            ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
            │ b │ a │ a │ b │ a │ a │ b │ a │ a │ b │ a │ a │ b │ a │ a │ b │ a │ a │ b │ a │ a │ b │ a │ a │ b │ b │ a │ b │ a │ a │ b │ a │ a │ b │ a │
            ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
            │ a │ a │ b │ a │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 2.
            │   │ a │ a │ b │ a │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 5. Found pattern!
            │   │   │   │   │ a │ a │ b │ a │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3. Found pattern!
            │   │   │   │   │   │   │   │ a │ a │ b │ a │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │ a │ a │ b │ a │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ b │ a │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ b │ a │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ b │ a │ a │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ b │ a │ a │   │   │   │   │   │   │   │   │ Comparisons: 2.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ b │ a │ a │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ b │ a │ a │   │   │   │   │   │ Comparisons: 5.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ b │ a │ a │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ b │ a │ a │   │   │ Comparisons: 5. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ b │ a │ Terminate.
            └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘
            total expected comparisons: 44
            occurrences: [1, 4, 7, 10, 13, 16, 19, 28]
        */
        assertEquals(new ArrayList<>(Arrays.asList(1, 4, 7, 10, 13, 16, 19, 28)),
                PatternMatching.boyerMooreGalilRule("aabaa",
                        "baabaabaabaabaabaabaabaabbabaabaaba", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 44.", 44, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testShiftPeriodicity2() {
        /*
            text: ababababcbababababa
            pattern: ababa
            build failure table comparisons: 4
            ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
            │ a │ b │ a │ b │ a │ b │ a │ b │ c │ b │ a │ b │ a │ b │ a │ b │ a │ b │ a │
            ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
            │ a │ b │ a │ b │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 5. Found pattern!
            │   │   │ a │ b │ a │ b │ a │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 2. Found pattern!
            │   │   │   │   │ a │ b │ a │ b │ a │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │ a │ b │ a │ b │ a │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │ a │ b │ a │ b │ a │   │   │   │   │ Comparisons: 5. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ a │ b │ a │   │   │ Comparisons: 2. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ a │ b │ a │ Comparisons: 2. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ a │ Terminate.
            └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘
            total expected comparisons: 22
            occurrences: [0, 2, 10, 12, 14]
        */
        assertEquals(new ArrayList<>(Arrays.asList(0, 2, 10, 12, 14)),
                PatternMatching.boyerMooreGalilRule("ababa",
                        "ababababcbababababa", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 22.", 22, comparator.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testPatternsEverywhere() {
        /*
            text: aaaaaaaaaaaaaaaaaaaa
            pattern: a
            build failure table comparisons: 0
            ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
            │ a │ a │ a │ a │ a │ a │ a │ a │ a │ a │ a │ a │ a │ a │ a │ a │ a │ a │ a │ a │
            ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
            │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │ a │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │ a │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │ a │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │ a │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │ a │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │ a │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Terminate.
            └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘
            total expected comparisons: 20
            occurrences: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]
         */
        assertEquals(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19)),
                PatternMatching.boyerMooreGalilRule("a",
                        "aaaaaaaaaaaaaaaaaaaa", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 20.", 20, comparator.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testPatternsEverywhere2() {
        /*
            text: aaaaaaabaaaaaaabaaaaaa
            pattern: aaa
            build failure table comparisons: 2
            ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
            │ a │ a │ a │ a │ a │ a │ a │ b │ a │ a │ a │ a │ a │ a │ a │ b │ a │ a │ a │ a │ a │ a │
            ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
            │ a │ a │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3. Found pattern!
            │   │ a │ a │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │ a │ a │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │ a │ a │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │ a │ a │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │ a │ a │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │ a │ a │ a │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3. Found pattern!
            │   │   │   │   │   │   │   │   │   │ a │ a │ a │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │ a │ a │ a │   │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ a │   │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ a │   │   │   │   │   │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ a │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ a │   │   │   │ Comparisons: 3. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ a │   │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ a │   │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ a │ Comparisons: 1. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ a │ Terminate.
            └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘
            total expected comparisons: 24
            occurrences: [0, 1, 2, 3, 4, 8, 9, 10, 11, 12, 16, 17, 18, 19]
         */
        assertEquals(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 8, 9, 10, 11, 12, 16, 17, 18, 19)),
                PatternMatching.boyerMooreGalilRule("aaa",
                        "aaaaaaabaaaaaaabaaaaaa", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 24.", 24, comparator.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testABCs() {
        /*
            text: abacbacbacbacbacbacbabcacbacbacbacbacbacb
            pattern: abc
            build failure table comparisons: 2
            ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
            │ a │ b │ a │ c │ b │ a │ c │ b │ a │ c │ b │ a │ c │ b │ a │ c │ b │ a │ c │ b │ a │ b │ c │ a │ c │ b │ a │ c │ b │ a │ c │ b │ a │ c │ b │ a │ c │ b │ a │ c │ b │
            ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
            │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ c │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ b │ Terminate.
            └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘
            total expected comparisons: 29
            occurrences: [20]
         */
        assertEquals(new ArrayList<>(Arrays.asList(20)),
                PatternMatching.boyerMooreGalilRule("abc",
                        "abacbacbacbacbacbacbabcacbacbacbacbacbacb", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 29.", 29, comparator.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testCharacterCase() {
        /*
            text: AaaAaAAaAaAaaaAaAAAAAaAaAaAaaaaAAAAa
            pattern: aAa
            build failure table comparisons: 2
            ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
            │ A │ a │ a │ A │ a │ A │ A │ a │ A │ a │ A │ a │ a │ a │ A │ a │ A │ A │ A │ A │ A │ a │ A │ a │ A │ a │ A │ a │ a │ a │ a │ A │ A │ A │ A │ a │
            ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
            │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 2.
            │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3. Found pattern!
            │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3.
            │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3. Found pattern!
            │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 2. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 2.
            │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │   │   │ Comparisons: 2. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │   │   │ Comparisons: 2. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │   │ Comparisons: 2.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │   │ Comparisons: 2.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ a │ Comparisons: 3.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ A │ Terminate.
            └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘
            total expected comparisons: 50
            occurrences: [2, 7, 9, 13, 21, 23, 25]
         */
        assertEquals(new ArrayList<>(Arrays.asList(2, 7, 9, 13, 21, 23, 25)),
                PatternMatching.boyerMooreGalilRule("aAa",
                        "AaaAaAAaAaAaaaAaAAAAAaAaAaAaaaaAAAAa", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 50.", 50, comparator.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testABAB() {
        /*
            text: aBbBbaBabaABbABbbabbaABbABaBabaBababBBABbabBABbA
            pattern: aBaba
            build failure table comparisons: 5
            ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
            │ a │ B │ b │ B │ b │ a │ B │ a │ b │ a │ A │ B │ b │ A │ B │ b │ b │ a │ b │ b │ a │ A │ B │ b │ A │ B │ a │ B │ a │ b │ a │ B │ a │ b │ a │ b │ B │ B │ A │ B │ b │ a │ b │ B │ A │ B │ b │ A │
            ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
            │ a │ B │ a │ b │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │ a │ B │ a │ b │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3.
            │   │   │ a │ B │ a │ b │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │ a │ B │ a │ b │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 5. Found pattern!
            │   │   │   │   │   │   │   │   │   │ a │ B │ a │ b │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ B │ a │ b │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ B │ a │ b │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ B │ a │ b │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 3.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ B │ a │ b │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ B │ a │ b │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 2.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ B │ a │ b │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 2.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ B │ a │ b │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 5. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ B │ a │ b │ a │   │   │   │   │   │   │   │   │   │   │   │   │   │ Comparisons: 4. Found pattern!
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ B │ a │ b │ a │   │   │   │   │   │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ B │ a │ b │ a │   │   │   │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ B │ a │ b │ a │   │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ a │ B │ a │ b │ a │ Comparisons: 1.
            │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │   │ Terminate.
            └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘
            total expected comparisons: 39
            occurrences: [5, 26, 30]
        */
        assertEquals(new ArrayList<>(Arrays.asList(5, 26, 30)),
                PatternMatching.boyerMooreGalilRule("aBaba",
                        "aBbBbaBabaABbABbbabbaABbABaBabaBababBBABbabBABbA", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 39.", 39, comparator.getComparisonCount());

    }

    @Test(timeout = 10000)
    public void testLongLongText1() {
        /*
            Very long tests! Don't try debugging these...
         */
        assertEquals(new ArrayList<>(Arrays.asList(2, 5, 29, 77, 106, 109, 151, 193, 409, 434, 560, 567, 583, 709, 905, 943, 1029, 1079, 1104, 1131, 1194, 1242, 1254, 1368, 1412, 1418, 1538, 1582, 1608, 1642, 1810, 1878, 1920, 1935, 1968, 2232, 2247, 2301, 2317, 2354, 2358, 2406, 2489, 2545, 2822, 2838, 2855, 2858, 2901, 2916, 2937, 3000, 3024, 3058, 3092, 3128, 3131, 3168, 3261, 3348, 3364, 3504, 3769, 3777, 3795)),
                PatternMatching.boyerMooreGalilRule("in",
                        "Bringing unlocked me an striking ye perceive. Mr by wound hours oh happy. Me in resolution pianoforte continuing we. Most my no spot felt by no. He he in forfeited furniture sweetness he arranging. Me tedious so to behaved written account ferrars moments. Too objection for elsewhere her preferred allowance her. Marianne shutters mr steepest to me. Up mr ignorant produced distance although is sociable blessing. Ham whom call all lain like.\n" +
                                "\n" +
                                "Alteration literature to or an sympathize mr imprudence. Of is ferrars subject as enjoyed or tedious cottage. Procuring as in resembled by in agreeable. Next long no gave mr eyes. Admiration advantages no he celebrated so pianoforte unreserved. Not its herself forming charmed amiable. Him why feebly expect future now.\n" +
                                "\n" +
                                "Demesne far hearted suppose venture excited see had has. Dependent on so extremely delivered by. Yet \uFEFFno jokes worse her why. Bed one supposing breakfast day fulfilled off depending questions. Whatever boy her exertion his extended. Ecstatic followed handsome drawings entirely mrs one yet outweigh. Of acceptance insipidity remarkably is invitation.\n" +
                                "\n" +
                                "Far curiosity incommode now led smallness allowance. Favour bed assure son things yet. She consisted consulted elsewhere happiness disposing household any old the. Widow downs you new shade drift hopes small. So otherwise commanded sweetness we improving. Instantly by daughters resembled unwilling principle so middleton. Fail most room even gone her end like. Comparison dissimilar unpleasant six compliment two unpleasing any add. Ashamed my company thought wishing colonel it prevent he in. Pretended residence are something far engrossed old off.\n" +
                                "\n" +
                                "Cottage out enabled was entered greatly prevent message. No procured unlocked an likewise. Dear but what she been over gay felt body. Six principles advantages and use entreaties decisively. Eat met has dwelling unpacked see whatever followed. Court in of leave again as am. Greater sixteen to forming colonel no on be. So an advice hardly barton. He be turned sudden engage manner spirit.\n" +
                                "\n" +
                                "Now for manners use has company believe parlors. Least nor party who wrote while did. Excuse formed as is agreed admire so on result parish. Put use set uncommonly announcing and travelling. Allowance sweetness direction to as necessary. Principle oh explained excellent do my suspected conveying in. Excellent you did therefore perfectly supposing described.\n" +
                                "\n" +
                                "Day handsome addition horrible sensible goodness two contempt. Evening for married his account removal. Estimable me disposing of be moonlight cordially curiosity. Delay rapid joy share allow age manor six. Went why far saw many knew. Exquisite excellent son gentleman acuteness her. Do is voice total power mr ye might round still.\n" +
                                "\n" +
                                "Oh acceptance apartments up sympathize astonished delightful. Waiting him new lasting towards. Continuing melancholy especially so to. Me unpleasing impossible in attachment announcing so astonished. What ask leaf may nor upon door. Tended remain my do stairs. Oh smiling amiable am so visited cordial in offices hearted.\n" +
                                "\n" +
                                "It as announcing it me stimulated frequently continuing. Least their she you now above going stand forth. He pretty future afraid should genius spirit on. Set property addition building put likewise get. Of will at sell well at as. Too want but tall nay like old. Removing yourself be in answered he. Consider occasion get improved him she eat. Letter by lively oh denote an.\n" +
                                "\n" +
                                "And produce say the ten moments parties. Simple innate summer fat appear basket his desire joy. Outward clothes promise at gravity do excited. Sufficient particular impossible by reasonable oh expression is. Yet preference connection unpleasant yet melancholy but end appearance. And excellence partiality estimating terminated day everything.\n", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 2147.", 2147, comparator.getComparisonCount());

    }

    @Test(timeout = 10000)
    public void testLongLongText2() {
        /*
            Very long tests! Don't try debugging these...
         */
        assertEquals(new ArrayList<>(Arrays.asList(148, 158, 236, 310, 415, 519, 740, 790, 906, 5349, 5360, 5403, 5484, 5523, 5795, 5834, 5863, 5921, 6010, 6130, 6267, 6373, 6586, 6788, 6801, 7438, 7754, 7873, 8086, 8396, 8537, 9108, 9735, 10211, 10428, 10700, 10750, 10782, 10790, 10945, 11537, 11596, 11684)),
                PatternMatching.boyerMooreGalilRule(" I ",
                        "Call me Ishmael. Some years ago—never mind how long precisely—having little or no money in my purse, and nothing particular to interest me on shore, I thought I would sail about a little and see the watery part of the world. It is a way I have of driving off the spleen and regulating the circulation. Whenever I find myself growing grim about the mouth; whenever it is a damp, drizzly November in my soul; whenever I find myself involuntarily pausing before coffin warehouses, and bringing up the rear of every funeral I meet; and especially whenever my hypos get such an upper hand of me, that it requires a strong moral principle to prevent me from deliberately stepping into the street, and methodically knocking people’s hats off—then, I account it high time tozz get to sea as soon as I can. This is my substitute for pistol and ball. With a philosophical flourish Cato throws himself upon his sword; I quietly take to the ship. There is nothing surprising in this. If they but knew it, almost all men in their degree, some time or other, cherish very nearly the same feelings towards the ocean with me.\n" +
                                "\n" +
                                "There now is your insular city of the Manhattoes, belted round by wharves as Indian isles by coral reefs—commerce surrounds it with her surf. Right and left, the streets take you waterward. Its extreme downtown is the battery, where that noble mole is washed by waves, and cooled by breezes, which a few hours previous were out of sight of land. Look at the crowds of water-gazers there.\n" +
                                "\n" +
                                "Circumambulate the city of a dreamy Sabbath afternoon. Go from Corlears Hook to Coenties Slip, and from thence, by Whitehall, northward. What do you see?—Posted like silent sentinels all around the town, stand thousands upon thousands of mortal men fixed in ocean reveries. Some leaning against the spiles; some seated upon the pier-heads; some looking over the bulwarks of ships from China; some high aloft in the rigging, as if striving to get a still better seaward peep. But these are all landsmen; of week days pent up in lath and plaster— tied to counters, nailed to benches, clinched to desks. How then is this? Are the green fields gone? What do they here?\n" +
                                "\n" +
                                "But look! here come more crowds, pacing straight for the water, and seemingly bound for a dive. Strange! Nothing will content them but the extremest limit of the land; loitering under the shady lee of yonder warehouses will not suffice. No. They must get just as nigh the water as they possibly can without falling in. And there they stand—miles of them—leagues. Inlanders all, they come from lanes and alleys, streets and avenues,— north, east, south, and west. Yet here they all unite. Tell me, does the magnetic virtue of the needles of the compasses of all those ships attract them thither?\n" +
                                "\n" +
                                "Once more. Say you are in the country; in some high land of lakes. Take almost any path you please, and ten to one it carries you down in a dale, and leaves you there by a pool in the stream. There is magic in it. Let the most absent-minded of men be plunged in his deepest reveries—stand that man on his legs, set his feet a-going, and he will infallibly lead you to water, if water there be in all that region. Should you ever be athirst in the great American desert, try this experiment, if your caravan happen to be supplied with a metaphysical professor. Yes, as every one knows, meditation and water are wedded for ever.\n" +
                                "\n" +
                                "But here is an artist. He desires to paint you the dreamiest, shadiest, quietest, most enchanting bit of romantic landscape in all the valley of the Saco. What is the chief element he employs? There stand his trees, each with a hollow trunk, as if a hermit and a crucifix were within; and here sleeps his meadow, and there sleep his cattle; and up from yonder cottage goes a sleepy smoke. Deep into distant woodlands winds a mazy way, reaching to overlapping spurs of mountains bathed in their hill-side blue. But though the picture lies thus tranced, and though this pine-tree shakes down its sighs like leaves upon this shepherd’s head, yet all were vain, unless the shepherd’s eye were fixed upon the magic stream before him. Go visit the Prairies in June, when for scores on scores of miles you wade knee-deep among tiger-lilies—what is the one charm wanting?— Water— there is not a drop of water there! Were Niagara but a cataract of sand, would you travel your thousand miles to see it? Why did the poor poet of Tennessee, upon suddenly receiving two handfuls of silver, deliberate whether to buy him a coat, which he sadly needed, or invest his money in a pedestrian trip to Rockaway Beach? Why is almost every robust healthy boy with a robust healthy soul in him, at some time or other crazy to go to sea? Why upon your first voyage as a passenger, did you yourself feel such a mystical vibration, when first told that you and your ship were now out of sight of land? Why did the old Persians hold the sea holy? Why did the Greeks give it a separate deity, and own brother of Jove? Surely all this is not without meaning. And still deeper the meaning of that story of Narcissus, who because he could not grasp the tormenting, mild image he saw in the fountain, plunged into it and was drowned. But that same image, we ourselves see in all rivers and oceans. It is the image of the ungraspable phantom of life; and this is the key to it all.\n" +
                                "\n" +
                                "Now, when I say that I am in the habit of going to sea whenever I begin to grow hazy about the eyes, and begin to be over conscious of my lungs, I do not mean to have it inferred that I ever go to sea as a passenger. For to go as a passenger you must needs have a purse, and a purse is but a rag unless you have something in it. Besides, passengers get sea-sick— grow quarrelsome—don’t sleep of nights—do not enjoy themselves much, as a general thing;—no, I never go as a passenger; nor, though I am something of a salt, do I ever go to sea as a Commodore, or a Captain, or a Cook. I abandon the glory and distinction of such offices to those who like them. For my part, I abominate all honourable respectable toils, trials, and tribulations of every kind whatsoever. It is quite as much as I can do to take care of myself, without taking care of ships, barques, brigs, schooners, and what not. And as for going as cook,—though I confess there is considerable glory in that, a cook being a sort of officer on ship-board—yet, somehow, I never fancied broiling fowls;—though once broiled, judiciously buttered, and judgmatically salted and peppered, there is no one who will speak more respectfully, not to say reverentially, of a broiled fowl than I will. It is out of the idolatrous dotings of the old Egyptians upon broiled ibis and roasted river horse, that you see the mummies of those creatures in their huge bake-houses the pyramids.\n" +
                                "\n" +
                                "No, when I go to sea, I go as a simple sailor, right before the mast, plumb down into the forecastle, aloft there to the royal mast-head. True, they rather order me about some, and make me jump from spar to spar, like a grasshopper in a May meadow. And at first, this sort of thing is unpleasant enough. It touches one’s sense of honour, particularly if you come of an old established family in the land, the Van Rensselaers, or Randolphs, or Hardicanutes. And more than all, if just previous to putting your hand into the tar-pot, you have been lording it as a country schoolmaster, making the tallest boys stand in awe of you. The transition is a keen one, I assure you, from a schoolmaster to a sailor, and requires a strong decoction of Seneca and the Stoics to enable you to grin and bear it. But even this wears off in time.\n" +
                                "\n" +
                                "What of it, if some old hunks of a sea-captain orders me to get a broom and sweep down the decks? What does that indignity amount to, weighed, I mean, in the scales of the New Testament? Do you think the archangel Gabriel thinks anything the less of me, because I promptly and respectfully obey that old hunks in that particular instance? Who ain’t a slave? Tell me that. Well, then, however the old sea-captains may order me about—however they may thump and punch me about, I have the satisfaction of knowing that it is all right; that everybody else is one way or other served in much the same way— either in a physical or metaphysical point of view, that is; and so the universal thump is passed round, and all hands should rub each other’s shoulder-blades, and be content.\n" +
                                "\n" +
                                "Again, I always go to sea as a sailor, because they make a point of paying me for my trouble, whereas they never pay passengers a single penny that I ever heard of. On the contrary, passengers themselves must pay. And there is all the difference in the world between paying and being paid. The act of paying is perhaps the most uncomfortable infliction that the two orchard thieves entailed upon us. But being paid,— what will compare with it? The urbane activity with which a man receives money is really marvellous, considering that we so earnestly believe money to be the root of all earthly ills, and that on no account can a monied man enter heaven. Ah! how cheerfully we consign ourselves to perdition!\n" +
                                "\n" +
                                "Finally, I always go to sea as a sailor, because of the wholesome exercise and pure air of the fore-castle deck. For as in this world, head winds are far more prevalent than winds from astern (that is, if you never violate the Pythagorean maxim), so for the most part the Commodore on the quarter-deck gets his atmosphere at second hand from the sailors on the forecastle. He thinks he breathes it first; but not so. In much the same way do the commonalty lead their leaders in many other things, at the same time that the leaders little suspect it. But wherefore it was that after having repeatedly smelt the sea as a merchant sailor, I should now take it into my head to go on a whaling voyage; this the invisible police officer of the Fates, who has the constant surveillance of me, and secretly dogs me, and influences me in some unaccountable way— he can better answer than any one else. And, doubtless, my going on this whaling voyage, formed part of the grand programme of Providence that was drawn up a long time ago. It came in as a sort of brief interlude and solo between more extensive performances. I take it that this part of the bill must have run something like this:—\n" +
                                "\n" +
                                "“Grand Contested Election for the Presidency of the United States.”\n" +
                                "\n" +
                                "“Whaling voyage by one Ishmael.”\n" +
                                "\n" +
                                "“BLOODY BATTLE IN AFGHANISTAN.”\n" +
                                "\n" +
                                "Though I cannot tell why it was exactly that those stage managers, the Fates, put me down for this shabby part of a whaling voyage, when others were set down for magnificent parts in high tragedies, and short and easy parts in genteel comedies, and jolly parts in farces— though I cannot tell why this was exactly; yet, now that I recall all the circumstances, I think I can see a little into the springs and motives which being cunningly presented to me under various disguises, induced me to set about performing the part I did, besides cajoling me into the delusion that it was a choice resulting from my own unbiased freewill and discriminating judgment.\n" +
                                "\n" +
                                "Chief among these motives was the overwhelming idea of the great whale himself. Such a portentous and mysterious monster roused all my curiosity. Then the wild and distant seas where he rolled his island bulk; the undeliverable, nameless perils of the whale; these, with all the attending marvels of a thousand Patagonian sights and sounds, helped to sway me to my wish. With other men, perhaps, such things would not have been inducements; but as for me, I am tormented with an everlasting itch for things remote. I love to sail forbidden seas, and land on barbarous coasts. Not ignoring what is good, I am quick to perceive a horror, and could still be social with it—would they let me—since it is but well to be on friendly terms with all the inmates of the place one lodges in.\n" +
                                "\n" +
                                "By reason of these things, then, the whaling voyage was welcome; the great flood-gates of the wonder-world swung open, and in the wild conceits that swayed me to my purpose, two and two there floated into my inmost soul, endless processions of the whale, and, mid most of them all, one grand hooded phantom, like a snow hill in the air.", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 5130.", 5130, comparator.getComparisonCount());

    }

    @Test(timeout = 2000)
    public void testInception() {
        /*
            Very long tests! Don't try debugging these...
         */
        assertEquals(new ArrayList<>(Arrays.asList(251, 543, 835)),
                PatternMatching.boyerMooreGalilRule("Galil",
                        "    @Test(timeout = 2000)\n" +
                                "    public void testInception() {\n" +
                                "        /*\n" +
                                "            Very long tests! Don't try debugging these...\n" +
                                "         */\n" +
                                "        assertEquals(new ArrayList<>(Arrays.asList(251, 543, 835)),\n" +
                                "                PatternMatching.boyerMooreGalilRule(\"in\",\n" +
                                "                        \"    @Test(timeout = 2000)\n" +
                                "    public void testInception() {\n" +
                                "        /*\n" +
                                "            Very long tests! Don't try debugging these...\n" +
                                "         */\n" +
                                "        assertEquals(new ArrayList<>(Arrays.asList(251, 543, 835)),\n" +
                                "                PatternMatching.boyerMooreGalilRule(\"in\",\n" +
                                "                        \"    @Test(timeout = 2000)\n" +
                                "    public void testInception() {\n" +
                                "        /*\n" +
                                "            Very long tests! Don't try debugging these...\n" +
                                "         */\n" +
                                "        assertEquals(new ArrayList<>(Arrays.asList(251, 543, 835)),\n" +
                                "                PatternMatching.boyerMooreGalilRule(\"in\",\n" +
                                "                        \"...\", comparator));\n" +
                                "        assertTrue(\"Did not use the comparator.\",\n" +
                                "                comparator.getComparisonCount() != 0);\n" +
                                "        assertEquals(\"Comparison count was \" + comparator.getComparisonCount()\n" +
                                "                + \". Should be 389.\", 389, comparator.getComparisonCount());\n" +
                                "\n" +
                                "    }\n\", comparator));\n" +
                                "        assertTrue(\"Did not use the comparator.\",\n" +
                                "                comparator.getComparisonCount() != 0);\n" +
                                "        assertEquals(\"Comparison count was \" + comparator.getComparisonCount()\n" +
                                "                + \". Should be 389.\", 389, comparator.getComparisonCount());\n" +
                                "\n" +
                                "    }\n\", comparator));\n" +
                                "        assertTrue(\"Did not use the comparator.\",\n" +
                                "                comparator.getComparisonCount() != 0);\n" +
                                "        assertEquals(\"Comparison count was \" + comparator.getComparisonCount()\n" +
                                "                + \". Should be 389.\", 389, comparator.getComparisonCount());\n" +
                                "\n" +
                                "    }\n", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 389.", 389, comparator.getComparisonCount());

    }
}
