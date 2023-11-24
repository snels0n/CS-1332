import org.junit.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static org.junit.Assert.*;

/**
 * Very fancy tests!!! (snapshot and property tests)
 *
 * @author Alexander Gualino
 * @version 1.0
 */
public class ExhaustiveTests {
    private static final int TIMEOUT = 200;
    private static final String ALPHABET = "abcdef";
    private static final int CHECK_TIMES = 1000;

    // you may want to bump up the TIMEOUT and CHECK_TIMES if minimizing.
    private static final boolean MINIMIZE = false;

    // todo: test cases re: checking conditions

    // I'm not sure of better snapshots to check.
    @Test(timeout = TIMEOUT)
    public void snapshotTestKMP() {
        BiFunction<CharSequence, CharSequence, Integer> check = (pattern, text) -> {
            var c = new CharacterComparator();
            PatternMatching.kmp(pattern, text, c);
            return c.getComparisonCount();
        };

        // manually typed out nonsense
        assertEquals(1, (int) check.apply("a", "b"));
        assertEquals(2, (int) check.apply("a", "bb"));
        assertEquals(1, (int) check.apply("b", "b"));
        assertEquals(2, (int) check.apply("b", "bb"));
        assertEquals(3, (int) check.apply("ab", "ab"));
        assertEquals(3, (int) check.apply("ab", "abb"));
        assertEquals(0, (int) check.apply("ab", ""));
    }

    @Test(timeout = TIMEOUT)
    public void snapshotTestBoyerMore() {
        BiFunction<CharSequence, CharSequence, Integer> check = (pattern, text) -> {
            var c = new CharacterComparator();
            PatternMatching.boyerMoore(pattern, text, c);
            return c.getComparisonCount();
        };

        // manually typed out nonsense
        assertEquals(1, (int) check.apply("a", "b"));
        assertEquals(2, (int) check.apply("a", "bb"));
        assertEquals(1, (int) check.apply("b", "b"));
        assertEquals(2, (int) check.apply("b", "bb"));
        assertEquals(2, (int) check.apply("ab", "ab"));
        assertEquals(4, (int) check.apply("ab", "abb"));
        assertEquals(0, (int) check.apply("ab", ""));
    }


    @Test(timeout = TIMEOUT)
    public void snapshotTestBoyerMooreGalil() {
        if (PatternMatching.boyerMooreGalilRule("a", "", new CharacterComparator()) == null) {
            return;
        }

        BiFunction<CharSequence, CharSequence, Integer> check = (pattern, text) -> {
            var c = new CharacterComparator();
            PatternMatching.boyerMooreGalilRule(pattern, text, c);
            return c.getComparisonCount();
        };

        // manually typed out nonsense
        assertEquals(1, (int) check.apply("a", "b"));
        assertEquals(2, (int) check.apply("a", "bb"));
        assertEquals(1, (int) check.apply("b", "b"));
        assertEquals(2, (int) check.apply("b", "bb"));
        assertEquals(3, (int) check.apply("ab", "ab"));
        assertEquals(3, (int) check.apply("ab", "abb"));
        assertEquals(0, (int) check.apply("ab", ""));
    }

    @Test(timeout = TIMEOUT)
    public void snapshotTestRabinKarp() {
        BiFunction<CharSequence, CharSequence, Integer> check = (pattern, text) -> {
            var c = new CharacterComparator();
            PatternMatching.rabinKarp(pattern, text, c);
            return c.getComparisonCount();
        };

        // manually typed out nonsense
        assertEquals(0, (int) check.apply("a", "b"));
        assertEquals(0, (int) check.apply("a", "bb"));
        assertEquals(1, (int) check.apply("b", "b"));
        assertEquals(2, (int) check.apply("b", "bb"));
        assertEquals(2, (int) check.apply("ab", "ab"));
        assertEquals(2, (int) check.apply("ab", "abb"));
        assertEquals(0, (int) check.apply("ab", ""));
    }


    // Property tests (they can be a pain to debug.)
    @Test(timeout = TIMEOUT)
    public void propertyTestKMP() {
        propertyTestSuffixMatch((pattern, text) -> PatternMatching.kmp(pattern, text, new CharacterComparator()));
        propertyTestAddTogether((pattern, text) -> PatternMatching.kmp(pattern, text, new CharacterComparator()));
    }

    @Test(timeout = TIMEOUT)
    public void propertyTestBoyerMoore() {
        propertyTestSuffixMatch((pattern, text) -> PatternMatching.boyerMoore(pattern, text, new CharacterComparator()));
        propertyTestAddTogether((pattern, text) -> PatternMatching.boyerMoore(pattern, text, new CharacterComparator()));
    }

    @Test(timeout = TIMEOUT)
    public void propertyTestBoyerMooreGalil() {
        if (PatternMatching.boyerMooreGalilRule("a", "", new CharacterComparator()) == null) {
            return;
        }

        propertyTestSuffixMatch((pattern, text) -> PatternMatching.boyerMooreGalilRule(pattern, text, new CharacterComparator()));
        propertyTestAddTogether((pattern, text) -> PatternMatching.boyerMooreGalilRule(pattern, text, new CharacterComparator()));
    }

    @Test(timeout = TIMEOUT)
    public void propertyTestRabinKarp() {
        propertyTestSuffixMatch((pattern, text) -> {
            return PatternMatching.rabinKarp(
                    pattern.subSequence(0, 3),
                    text.subSequence(0, text.length() - 3),
                    new CharacterComparator()
            );
        });
        propertyTestAddTogether((pattern, text) -> PatternMatching.rabinKarp(pattern, text, new CharacterComparator()));
    }

    private void propertyTestSuffixMatch(BiFunction<CharSequence, CharSequence, List<Integer>> fn) {
        propertyTest(
                (a, b) -> {
                    var prefixSize = a.left;
                    var alphabetSize = a.right;
                    if (alphabetSize == 0) {
                        return;
                    }
                    var rand = new Random(b);

                    var prefixBuilder = new StringBuilder();
                    for (var i = 0; i < prefixSize; i++) {
                        prefixBuilder.append(ALPHABET.charAt(rand.nextInt(alphabetSize)));
                    }
                    var prefix = prefixBuilder.toString();

                    var patternBuilder = new StringBuilder();
                    for (var i = 0; i < 6; i++) {
                        patternBuilder.append(ALPHABET.charAt(rand.nextInt(alphabetSize)));
                    }
                    var pattern = patternBuilder.toString();

                    var result = fn.apply(pattern, prefix + pattern);
                    assertEquals(prefixSize, result.get(result.size() - 1));
                },
                10,
                6,
                (a, b) -> {
                    var prefixSize = a.left;
                    var alphabetSize = a.right;
                    if (alphabetSize == 0) {
                        return;
                    }
                    var rand = new Random(b);

                    var prefixBuilder = new StringBuilder();
                    for (var i = 0; i < prefixSize; i++) {
                        prefixBuilder.append(ALPHABET.charAt(rand.nextInt(alphabetSize)));
                    }
                    var prefix = prefixBuilder.toString();

                    var patternBuilder = new StringBuilder();
                    for (var i = 0; i < 6; i++) {
                        patternBuilder.append(ALPHABET.charAt(rand.nextInt(alphabetSize)));
                    }
                    var pattern = patternBuilder.toString();

                    System.out.println("failed on pattern: " + pattern + "; text content: " + prefix + pattern);
                }
        );
    }

    private void propertyTestAddTogether(BiFunction<CharSequence, CharSequence, List<Integer>> fn) {
        propertyTest(
                (a, b) -> {
                    var textSize = a.left;
                    var alphabetSize = a.right;
                    if (alphabetSize == 0) {
                        return;
                    }
                    var rand = new Random(b);

                    var split = rand.nextInt(textSize);
                    var text1Builder = new StringBuilder();
                    for (var i = 0; i < split; i++) {
                        text1Builder.append(ALPHABET.charAt(rand.nextInt(alphabetSize)));
                    }
                    var text1 = text1Builder.toString();
                    var text2Builder = new StringBuilder();
                    for (var i = split; i < textSize; i++) {
                        text2Builder.append(ALPHABET.charAt(rand.nextInt(alphabetSize)));
                    }
                    var text2 = text2Builder.toString();


                    var patternBuilder = new StringBuilder();
                    for (var i = 0; i < 2; i++) {
                        patternBuilder.append(ALPHABET.charAt(rand.nextInt(alphabetSize)));
                    }
                    var pattern = patternBuilder.toString();


                    var result1 = fn.apply(pattern, text1);
                    var result2 = fn.apply(pattern, text2);
                    var result = fn.apply(pattern, text1 + text2);
                    var offset = 0;
                    for (var i = 0; i < result1.size(); i++) {
                        assertEquals(result1.get(i), result.get(i));
                        offset = i;
                    }
                    while (
                            offset < result.size() &&
                                    !result2.isEmpty() &&
                                    !Objects.equals(result2.get(0) + split, result.get(offset))) {
                        offset++;
                    }
                    for (var i = 0; i < result2.size(); i++) {
                        assertEquals(result2.get(i) + split, (int) result.get(i + offset));
                    }
                },
                10,
                6,
                (a, b) -> {
                    var textSize = a.left;
                    var alphabetSize = a.right;
                    if (alphabetSize == 0) {
                        return;
                    }
                    var rand = new Random(b);

                    var split = rand.nextInt(textSize);
                    var text1Builder = new StringBuilder();
                    for (var i = 0; i < split; i++) {
                        text1Builder.append(ALPHABET.charAt(rand.nextInt(alphabetSize)));
                    }
                    var text1 = text1Builder.toString();
                    var text2Builder = new StringBuilder();
                    for (var i = split; i < textSize; i++) {
                        text2Builder.append(ALPHABET.charAt(rand.nextInt(alphabetSize)));
                    }
                    var text2 = text2Builder.toString();


                    var patternBuilder = new StringBuilder();
                    for (var i = 0; i < 2; i++) {
                        patternBuilder.append(ALPHABET.charAt(rand.nextInt(alphabetSize)));
                    }
                    var pattern = patternBuilder.toString();

                    System.out.println("failed on pattern: " + pattern + "; prefix: " + text1 + "; suffix: " + text2);
                }
        );
    }

    // Property testing infrastructure. Trying to make things easier to debug
    // :D
    private void propertyTest(BiConsumer<TwoTuple<Integer, Integer>, Long> fn, int arg1Max, int arg2Max, BiConsumer<TwoTuple<Integer, Integer>, Long> reportError) {
        var checked = new HashSet<TwoTuple<Integer, Integer>>();
        TwoTuple<Integer, Integer> best = null;
        Long bestSeed = null;
        int bestDistance = arg1Max + arg2Max + 1;
        AssertionError failure = null;

        var rand = new Random();

        var toCheck = new ArrayList<TwoTuple<Integer, Integer>>();
        toCheck.add(new TwoTuple<>(MINIMIZE ? arg1Max : rand.nextInt(arg1Max) + 1, MINIMIZE ? arg2Max : rand.nextInt(arg2Max) + 1));

        while (!toCheck.isEmpty()) {
            var e = toCheck.remove(toCheck.size() - 1);
            if (e.left + e.right >= bestDistance || checked.contains(e)) {
                continue;
            }
            checked.add(e);

            for (var i = 0; i < CHECK_TIMES; i++) {
                var seed = rand.nextLong();
                try {
                    fn.accept(e, seed);
                } catch (AssertionError err) {
                    bestDistance = e.left + e.right;
                    bestSeed = seed;
                    best = e;

                    if (MINIMIZE) {
                        if (e.left > 1) {
                            toCheck.add(new TwoTuple<>(e.left - 1, e.right));
                        }
                        if (e.right > 1) {
                            toCheck.add(new TwoTuple<>(e.left, e.right - 1));
                        }
                    }
                    failure = err;
                    break;
                }
            }
        }

        if (failure != null) {
            reportError.accept(best, bestSeed);
            System.out.println("(left: " + best.left + "; right: " + best.right + "; seed: " + bestSeed + ")");
            throw failure;
        }
    }

    private static class TwoTuple<T, U> {
        // Double but Double is already taken
        T left;
        U right;

        public TwoTuple(T left, U right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TwoTuple<?, ?> twoTuple = (TwoTuple<?, ?>) o;
            return Objects.equals(left, twoTuple.left) && Objects.equals(right, twoTuple.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }
    }
}
