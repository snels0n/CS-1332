import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GraphAlgorithmsBasicTests {

    private static final int TIMEOUT = 200;
    private Vertex<Integer> testVertex;

    private Graph<Integer> directed;
    private Graph<Character> undirected;

    private Graph<Integer> largeDirected;
    private Graph<Character> largeUndirected;

    @Before
    public void setUp() {
        testVertex = new Vertex<>(Integer.MIN_VALUE);
        directed = createDirected();
        undirected = createUndirected();
        largeDirected = createLargeDirected();
        largeUndirected = createLargeUndirected();
    }

    /**
     * creates example directed graph
     * @return directed graph
     */
    private Graph<Integer> createDirected() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        for (int i = 1; i <= 9; i++) {
            vertices.add(new Vertex<>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(8), 0));
        edges.add(new Edge<>(new Vertex<>(8), new Vertex<>(9), 0));
        return new Graph<>(vertices, edges);
    }

    /**
     * creates example undirected graph
     * @return undirected graph
     */
    private Graph<Character> createUndirected() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        vertices.add(new Vertex<>('A'));
        vertices.add(new Vertex<>('B'));
        vertices.add(new Vertex<>('C'));
        vertices.add(new Vertex<>('D'));
        vertices.add(new Vertex<>('E'));
        vertices.add(new Vertex<>('F'));

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 3));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 6));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 6));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('A'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 6));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 6));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 5));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('F'), 3));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('E'), 2));

        return new Graph<>(vertices, edges);
    }

    /**
     * creates example large directed graph
     * @return large directed graph
     */
    private Graph<Integer> createLargeDirected() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        for (int i = 1; i <= 25; i++) {
            vertices.add(new Vertex<>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>(9), new Vertex<>(12), 0));
        edges.add(new Edge<>(new Vertex<>(11), new Vertex<>(22), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(21), 0));
        edges.add(new Edge<>(new Vertex<>(18), new Vertex<>(25), 0));
        edges.add(new Edge<>(new Vertex<>(11), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(13), new Vertex<>(25), 0));
        edges.add(new Edge<>(new Vertex<>(20), new Vertex<>(25), 0));
        edges.add(new Edge<>(new Vertex<>(10), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(9), 0));
        edges.add(new Edge<>(new Vertex<>(15), new Vertex<>(25), 0));
        edges.add(new Edge<>(new Vertex<>(23), new Vertex<>(14), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(1), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(21), 0));
        edges.add(new Edge<>(new Vertex<>(17), new Vertex<>(20), 0));
        edges.add(new Edge<>(new Vertex<>(9), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(18), new Vertex<>(21), 0));
        edges.add(new Edge<>(new Vertex<>(10), new Vertex<>(23), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(11), 0));
        edges.add(new Edge<>(new Vertex<>(24), new Vertex<>(1), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(12), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(12), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(20), new Vertex<>(17), 0));
        edges.add(new Edge<>(new Vertex<>(22), new Vertex<>(18), 0));
        edges.add(new Edge<>(new Vertex<>(23), new Vertex<>(21), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(19), new Vertex<>(22), 0));
        edges.add(new Edge<>(new Vertex<>(9), new Vertex<>(11), 0));
        edges.add(new Edge<>(new Vertex<>(20), new Vertex<>(22), 0));
        edges.add(new Edge<>(new Vertex<>(8), new Vertex<>(14), 0));
        edges.add(new Edge<>(new Vertex<>(14), new Vertex<>(20), 0));
        edges.add(new Edge<>(new Vertex<>(19), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(24), new Vertex<>(9), 0));
        edges.add(new Edge<>(new Vertex<>(15), new Vertex<>(18), 0));
        edges.add(new Edge<>(new Vertex<>(12), new Vertex<>(25), 0));
        edges.add(new Edge<>(new Vertex<>(8), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(13), 0));
        edges.add(new Edge<>(new Vertex<>(22), new Vertex<>(17), 0));
        edges.add(new Edge<>(new Vertex<>(24), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(20), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(10), 0));
        edges.add(new Edge<>(new Vertex<>(19), new Vertex<>(15), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(23), 0));
        edges.add(new Edge<>(new Vertex<>(9), new Vertex<>(16), 0));
        edges.add(new Edge<>(new Vertex<>(9), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(17), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(23), new Vertex<>(18), 0));
        edges.add(new Edge<>(new Vertex<>(9), new Vertex<>(10), 0));
        edges.add(new Edge<>(new Vertex<>(13), new Vertex<>(23), 0));
        edges.add(new Edge<>(new Vertex<>(18), new Vertex<>(19), 0));
        edges.add(new Edge<>(new Vertex<>(13), new Vertex<>(18), 0));
        edges.add(new Edge<>(new Vertex<>(22), new Vertex<>(20), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(11), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(15), 0));
        edges.add(new Edge<>(new Vertex<>(18), new Vertex<>(10), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(20), new Vertex<>(19), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(11), new Vertex<>(20), 0));
        edges.add(new Edge<>(new Vertex<>(11), new Vertex<>(1), 0));
        edges.add(new Edge<>(new Vertex<>(24), new Vertex<>(12), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(8), 0));
        edges.add(new Edge<>(new Vertex<>(9), new Vertex<>(15), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(24), 0));
        edges.add(new Edge<>(new Vertex<>(22), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(15), new Vertex<>(13), 0));
        edges.add(new Edge<>(new Vertex<>(21), new Vertex<>(19), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(9), 0));
        edges.add(new Edge<>(new Vertex<>(16), new Vertex<>(22), 0));
        edges.add(new Edge<>(new Vertex<>(11), new Vertex<>(21), 0));
        edges.add(new Edge<>(new Vertex<>(25), new Vertex<>(12), 0));
        edges.add(new Edge<>(new Vertex<>(15), new Vertex<>(9), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(17), 0));
        edges.add(new Edge<>(new Vertex<>(8), new Vertex<>(18), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(16), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(19), new Vertex<>(8), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(22), 0));
        edges.add(new Edge<>(new Vertex<>(23), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(24), new Vertex<>(23), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(24), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(12), new Vertex<>(13), 0));
        edges.add(new Edge<>(new Vertex<>(12), new Vertex<>(17), 0));
        edges.add(new Edge<>(new Vertex<>(22), new Vertex<>(1), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(24), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(15), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(11), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(16), 0));
        edges.add(new Edge<>(new Vertex<>(16), new Vertex<>(19), 0));
        edges.add(new Edge<>(new Vertex<>(9), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(8), new Vertex<>(23), 0));
        edges.add(new Edge<>(new Vertex<>(15), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(10), new Vertex<>(21), 0));
        edges.add(new Edge<>(new Vertex<>(14), new Vertex<>(9), 0));
        edges.add(new Edge<>(new Vertex<>(11), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(14), new Vertex<>(6), 0));

        return new Graph<>(vertices, edges);
    }

    /**
     * creates example large undirected graph
     * @return large undirected graph
     */
    private Graph<Character> createLargeUndirected() {
        Set<Vertex<Character>> vertices = new HashSet<>();

        for (int i = 65; i <= 90; i++) {
            vertices.add(new Vertex<>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('Q'), new Vertex<>('R'), 3));
        edges.add(new Edge<>(new Vertex<>('R'), new Vertex<>('Q'), 3));
        edges.add(new Edge<>(new Vertex<>('Z'), new Vertex<>('C'), 3));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('Z'), 3));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('Y'), 7));
        edges.add(new Edge<>(new Vertex<>('Y'), new Vertex<>('I'), 7));
        edges.add(new Edge<>(new Vertex<>('L'), new Vertex<>('B'), 8));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('L'), 8));
        edges.add(new Edge<>(new Vertex<>('X'), new Vertex<>('K'), 10));
        edges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('X'), 10));
        edges.add(new Edge<>(new Vertex<>('L'), new Vertex<>('I'), 9));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('L'), 9));
        edges.add(new Edge<>(new Vertex<>('M'), new Vertex<>('H'), 6));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('M'), 6));
        edges.add(new Edge<>(new Vertex<>('P'), new Vertex<>('T'), 3));
        edges.add(new Edge<>(new Vertex<>('T'), new Vertex<>('P'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('G'), 4));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('E'), 4));
        edges.add(new Edge<>(new Vertex<>('J'), new Vertex<>('O'), 2));
        edges.add(new Edge<>(new Vertex<>('O'), new Vertex<>('J'), 2));
        edges.add(new Edge<>(new Vertex<>('W'), new Vertex<>('O'), 3));
        edges.add(new Edge<>(new Vertex<>('O'), new Vertex<>('W'), 3));
        edges.add(new Edge<>(new Vertex<>('U'), new Vertex<>('J'), 4));
        edges.add(new Edge<>(new Vertex<>('J'), new Vertex<>('U'), 4));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('W'), 3));
        edges.add(new Edge<>(new Vertex<>('W'), new Vertex<>('H'), 3));
        edges.add(new Edge<>(new Vertex<>('L'), new Vertex<>('D'), 6));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('L'), 6));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('O'), 10));
        edges.add(new Edge<>(new Vertex<>('O'), new Vertex<>('A'), 10));
        edges.add(new Edge<>(new Vertex<>('V'), new Vertex<>('Z'), 1));
        edges.add(new Edge<>(new Vertex<>('Z'), new Vertex<>('V'), 1));
        edges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('X'), 9));
        edges.add(new Edge<>(new Vertex<>('X'), new Vertex<>('K'), 9));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('R'), 4));
        edges.add(new Edge<>(new Vertex<>('R'), new Vertex<>('C'), 4));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('Q'), 6));
        edges.add(new Edge<>(new Vertex<>('Q'), new Vertex<>('I'), 6));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 1));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('I'), 2));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('K'), 2));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('I'), 2));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('A'), 2));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('I'), 10));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('E'), 10));
        edges.add(new Edge<>(new Vertex<>('Q'), new Vertex<>('Y'), 10));
        edges.add(new Edge<>(new Vertex<>('Y'), new Vertex<>('Q'), 10));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('H'), 7));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('E'), 7));
        edges.add(new Edge<>(new Vertex<>('S'), new Vertex<>('Z'), 3));
        edges.add(new Edge<>(new Vertex<>('Z'), new Vertex<>('S'), 3));
        edges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('L'), 7));
        edges.add(new Edge<>(new Vertex<>('L'), new Vertex<>('K'), 7));
        edges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('I'), 6));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('K'), 6));
        edges.add(new Edge<>(new Vertex<>('W'), new Vertex<>('L'), 10));
        edges.add(new Edge<>(new Vertex<>('L'), new Vertex<>('W'), 10));
        edges.add(new Edge<>(new Vertex<>('U'), new Vertex<>('F'), 5));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('U'), 5));
        edges.add(new Edge<>(new Vertex<>('S'), new Vertex<>('C'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('S'), 5));
        edges.add(new Edge<>(new Vertex<>('Y'), new Vertex<>('Q'), 7));
        edges.add(new Edge<>(new Vertex<>('Q'), new Vertex<>('Y'), 7));
        edges.add(new Edge<>(new Vertex<>('O'), new Vertex<>('J'), 2));
        edges.add(new Edge<>(new Vertex<>('J'), new Vertex<>('O'), 2));
        edges.add(new Edge<>(new Vertex<>('V'), new Vertex<>('F'), 1));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('V'), 1));
        edges.add(new Edge<>(new Vertex<>('R'), new Vertex<>('X'), 6));
        edges.add(new Edge<>(new Vertex<>('X'), new Vertex<>('R'), 6));
        edges.add(new Edge<>(new Vertex<>('N'), new Vertex<>('F'), 9));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('N'), 9));
        edges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('U'), 8));
        edges.add(new Edge<>(new Vertex<>('U'), new Vertex<>('K'), 8));
        edges.add(new Edge<>(new Vertex<>('L'), new Vertex<>('P'), 8));
        edges.add(new Edge<>(new Vertex<>('P'), new Vertex<>('L'), 8));
        edges.add(new Edge<>(new Vertex<>('Z'), new Vertex<>('G'), 3));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('Z'), 3));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('X'), 8));
        edges.add(new Edge<>(new Vertex<>('X'), new Vertex<>('C'), 8));
        edges.add(new Edge<>(new Vertex<>('O'), new Vertex<>('F'), 9));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('O'), 9));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('K'), 5));
        edges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('B'), 5));
        edges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('T'), 3));
        edges.add(new Edge<>(new Vertex<>('T'), new Vertex<>('K'), 3));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('Y'), 3));
        edges.add(new Edge<>(new Vertex<>('Y'), new Vertex<>('I'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('Y'), 6));
        edges.add(new Edge<>(new Vertex<>('Y'), new Vertex<>('E'), 6));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('W'), 8));
        edges.add(new Edge<>(new Vertex<>('W'), new Vertex<>('D'), 8));
        edges.add(new Edge<>(new Vertex<>('S'), new Vertex<>('N'), 6));
        edges.add(new Edge<>(new Vertex<>('N'), new Vertex<>('S'), 6));
        edges.add(new Edge<>(new Vertex<>('Y'), new Vertex<>('U'), 7));
        edges.add(new Edge<>(new Vertex<>('U'), new Vertex<>('Y'), 7));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('H'), 8));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('D'), 8));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('H'), 2));

        continueLargeUndirected(edges);

        return new Graph<>(vertices, edges);
    }

    /**
     * continues the method from createLargeUndirected
     * @param edges set of edges to add to
     */
    private void continueLargeUndirected(Set<Edge<Character>> edges) {
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('A'), 2));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('Z'), 8));
        edges.add(new Edge<>(new Vertex<>('Z'), new Vertex<>('C'), 8));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('H'), 4));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('I'), 4));
        edges.add(new Edge<>(new Vertex<>('O'), new Vertex<>('L'), 8));
        edges.add(new Edge<>(new Vertex<>('L'), new Vertex<>('O'), 8));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('A'), 5));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('G'), 5));
        edges.add(new Edge<>(new Vertex<>('P'), new Vertex<>('E'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('P'), 3));
        edges.add(new Edge<>(new Vertex<>('P'), new Vertex<>('H'), 7));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('P'), 7));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('T'), 2));
        edges.add(new Edge<>(new Vertex<>('T'), new Vertex<>('E'), 2));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('L'), 9));
        edges.add(new Edge<>(new Vertex<>('L'), new Vertex<>('E'), 9));
        edges.add(new Edge<>(new Vertex<>('J'), new Vertex<>('S'), 5));
        edges.add(new Edge<>(new Vertex<>('S'), new Vertex<>('J'), 5));
        edges.add(new Edge<>(new Vertex<>('M'), new Vertex<>('H'), 1));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('M'), 1));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('M'), 2));
        edges.add(new Edge<>(new Vertex<>('M'), new Vertex<>('F'), 2));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('S'), 4));
        edges.add(new Edge<>(new Vertex<>('S'), new Vertex<>('I'), 4));
        edges.add(new Edge<>(new Vertex<>('L'), new Vertex<>('S'), 1));
        edges.add(new Edge<>(new Vertex<>('S'), new Vertex<>('L'), 1));
        edges.add(new Edge<>(new Vertex<>('O'), new Vertex<>('F'), 4));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('O'), 4));
        edges.add(new Edge<>(new Vertex<>('S'), new Vertex<>('V'), 1));
        edges.add(new Edge<>(new Vertex<>('V'), new Vertex<>('S'), 1));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('T'), 6));
        edges.add(new Edge<>(new Vertex<>('T'), new Vertex<>('G'), 6));
        edges.add(new Edge<>(new Vertex<>('N'), new Vertex<>('Y'), 5));
        edges.add(new Edge<>(new Vertex<>('Y'), new Vertex<>('N'), 5));
        edges.add(new Edge<>(new Vertex<>('U'), new Vertex<>('J'), 6));
        edges.add(new Edge<>(new Vertex<>('J'), new Vertex<>('U'), 6));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 7));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 7));
        edges.add(new Edge<>(new Vertex<>('Z'), new Vertex<>('O'), 2));
        edges.add(new Edge<>(new Vertex<>('O'), new Vertex<>('Z'), 2));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('F'), 6));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('B'), 6));
        edges.add(new Edge<>(new Vertex<>('S'), new Vertex<>('D'), 2));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('S'), 2));
        edges.add(new Edge<>(new Vertex<>('S'), new Vertex<>('W'), 5));
        edges.add(new Edge<>(new Vertex<>('W'), new Vertex<>('S'), 5));
        edges.add(new Edge<>(new Vertex<>('S'), new Vertex<>('Y'), 5));
        edges.add(new Edge<>(new Vertex<>('Y'), new Vertex<>('S'), 5));
        edges.add(new Edge<>(new Vertex<>('Q'), new Vertex<>('K'), 1));
        edges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('Q'), 1));
        edges.add(new Edge<>(new Vertex<>('J'), new Vertex<>('N'), 2));
        edges.add(new Edge<>(new Vertex<>('N'), new Vertex<>('J'), 2));
        edges.add(new Edge<>(new Vertex<>('T'), new Vertex<>('B'), 8));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('T'), 8));
        edges.add(new Edge<>(new Vertex<>('M'), new Vertex<>('G'), 2));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('M'), 2));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('M'), 10));
        edges.add(new Edge<>(new Vertex<>('M'), new Vertex<>('G'), 10));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('D'), 7));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('H'), 7));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 9));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 9));
        edges.add(new Edge<>(new Vertex<>('Y'), new Vertex<>('L'), 1));
        edges.add(new Edge<>(new Vertex<>('L'), new Vertex<>('Y'), 1));
        edges.add(new Edge<>(new Vertex<>('Z'), new Vertex<>('I'), 1));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('Z'), 1));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 5));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 5));
        edges.add(new Edge<>(new Vertex<>('W'), new Vertex<>('H'), 10));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('W'), 10));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 10));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 10));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('Z'), 8));
        edges.add(new Edge<>(new Vertex<>('Z'), new Vertex<>('F'), 8));
        edges.add(new Edge<>(new Vertex<>('W'), new Vertex<>('R'), 1));
        edges.add(new Edge<>(new Vertex<>('R'), new Vertex<>('W'), 1));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('X'), 7));
        edges.add(new Edge<>(new Vertex<>('X'), new Vertex<>('F'), 7));
        edges.add(new Edge<>(new Vertex<>('W'), new Vertex<>('C'), 10));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('W'), 10));
        edges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('R'), 5));
        edges.add(new Edge<>(new Vertex<>('R'), new Vertex<>('K'), 5));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('V'), 10));
        edges.add(new Edge<>(new Vertex<>('V'), new Vertex<>('I'), 10));
        edges.add(new Edge<>(new Vertex<>('J'), new Vertex<>('Z'), 3));
        edges.add(new Edge<>(new Vertex<>('Z'), new Vertex<>('J'), 3));
        edges.add(new Edge<>(new Vertex<>('P'), new Vertex<>('N'), 5));
        edges.add(new Edge<>(new Vertex<>('N'), new Vertex<>('P'), 5));
        edges.add(new Edge<>(new Vertex<>('Z'), new Vertex<>('P'), 5));
        edges.add(new Edge<>(new Vertex<>('P'), new Vertex<>('Z'), 5));
        edges.add(new Edge<>(new Vertex<>('J'), new Vertex<>('M'), 3));
        edges.add(new Edge<>(new Vertex<>('M'), new Vertex<>('J'), 3));
        edges.add(new Edge<>(new Vertex<>('L'), new Vertex<>('M'), 9));
        edges.add(new Edge<>(new Vertex<>('M'), new Vertex<>('L'), 9));
        edges.add(new Edge<>(new Vertex<>('P'), new Vertex<>('G'), 7));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('P'), 7));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('I'), 8));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('D'), 8));
        edges.add(new Edge<>(new Vertex<>('R'), new Vertex<>('K'), 6));
        edges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('R'), 6));
    }

    /**
     * helps build mstExpected
     * @param mstExpected set to add to
     * @param first first char
     * @param second second char
     * @param weight weight of edge
     */
    private void buildMSTExpected(Set<Edge<Character>> mstExpected, char first, char second, int weight) {
        mstExpected.add(new Edge<>(new Vertex<>(first), new Vertex<>(second), weight));
        mstExpected.add(new Edge<>(new Vertex<>(second), new Vertex<>(first), weight));
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testBFSNullGraph() {
        GraphAlgorithms.bfs(testVertex, null);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testBFSNullVertex() {
        GraphAlgorithms.bfs(null, directed);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testBFSStartNotInGraph() {
        GraphAlgorithms.bfs(testVertex, directed);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testDFSNullGraph() {
        GraphAlgorithms.dfs(testVertex, null);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testDFSNullVertex() {
        GraphAlgorithms.dfs(null, directed);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testDFSStartNotInGraph() {
        GraphAlgorithms.dfs(testVertex, directed);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testDijkstrasNullGraph() {
        GraphAlgorithms.dijkstras(testVertex, null);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testDijkstrasNullVertex() {
        GraphAlgorithms.dijkstras(null, directed);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testDijkstrasStartNotInGraph() {
        GraphAlgorithms.dijkstras(testVertex, directed);
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testKruskalsNullGraph() {
        GraphAlgorithms.kruskals(null);
    }

    @Test(timeout = TIMEOUT)
    public void testBFS() {
        List<Vertex<Integer>> bfsActual = GraphAlgorithms.bfs(
                new Vertex<>(1), directed);

        List<Vertex<Integer>> bfsExpected = new LinkedList<>();
        bfsExpected.add(new Vertex<>(1));
        bfsExpected.add(new Vertex<>(3));
        bfsExpected.add(new Vertex<>(4));
        bfsExpected.add(new Vertex<>(5));
        bfsExpected.add(new Vertex<>(6));
        bfsExpected.add(new Vertex<>(7));
        bfsExpected.add(new Vertex<>(2));
        bfsExpected.add(new Vertex<>(8));
        bfsExpected.add(new Vertex<>(9));
        assertEquals(bfsExpected, bfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testBFSLargeGraph() {
        List<Vertex<Integer>> bfsActual = GraphAlgorithms.bfs(
                new Vertex<>(1), largeDirected
        );

        int[] order = new int[]{1, 21, 11, 24, 19, 22, 4, 20,
            6, 9, 5, 12, 23, 2, 15, 8, 18, 17, 16, 25, 3, 10, 7, 13, 14};


        List<Vertex<Integer>> bfsExpected = new LinkedList<>();
        for (int i : order) {
            bfsExpected.add(new Vertex<>(i));
        }

        assertEquals(bfsExpected, bfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDFS() {
        List<Vertex<Integer>> dfsActual = GraphAlgorithms.dfs(
                new Vertex<>(1), directed);

        List<Vertex<Integer>> dfsExpected = new LinkedList<>();
        dfsExpected.add(new Vertex<>(1));
        dfsExpected.add(new Vertex<>(3));
        dfsExpected.add(new Vertex<>(2));
        dfsExpected.add(new Vertex<>(8));
        dfsExpected.add(new Vertex<>(9));
        dfsExpected.add(new Vertex<>(4));
        dfsExpected.add(new Vertex<>(5));
        dfsExpected.add(new Vertex<>(6));
        dfsExpected.add(new Vertex<>(7));
        assertEquals(dfsExpected, dfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDFSLargeGraph() {
        List<Vertex<Integer>> dfsActual = GraphAlgorithms.dfs(
                new Vertex<>(1), largeDirected);

        List<Vertex<Integer>> dfsExpected = new LinkedList<>();
        int[] order = new int[]{1, 21, 19, 22, 18, 25, 12, 13, 23,
            14, 20, 17, 6, 10, 4, 16, 24, 9, 3, 5, 8, 15, 11, 7, 2};
        for (int i : order) {
            dfsExpected.add(new Vertex<>(i));
        }
        assertEquals(dfsExpected, dfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasA() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
                new Vertex<>('A'), undirected);

        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 0);
        dijkExpected.put(new Vertex<>('B'), 2);
        dijkExpected.put(new Vertex<>('C'), 3);
        dijkExpected.put(new Vertex<>('D'), 6);
        dijkExpected.put(new Vertex<>('E'), 1);
        dijkExpected.put(new Vertex<>('F'), 5);

        assertEquals(dijkExpected, dijkActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasB() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
                new Vertex<>('B'), undirected);

        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 2);
        dijkExpected.put(new Vertex<>('B'), 0);
        dijkExpected.put(new Vertex<>('C'), 3);
        dijkExpected.put(new Vertex<>('D'), 8);
        dijkExpected.put(new Vertex<>('E'), 1);
        dijkExpected.put(new Vertex<>('F'), 3);

        assertEquals(dijkExpected, dijkActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasDisconnected() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        vertices.add(new Vertex<>('A'));
        vertices.add(new Vertex<>('B'));
        vertices.add(new Vertex<>('C'));
        vertices.add(new Vertex<>('D'));
        vertices.add(new Vertex<>('E'));
        vertices.add(new Vertex<>('F'));
        vertices.add(new Vertex<>('X'));

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 3));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 6));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 6));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('A'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 6));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 6));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 5));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('F'), 3));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('E'), 2));

        Graph<Character> graph = new Graph<>(vertices, edges);
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
                new Vertex<>('B'), graph);

        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 2);
        dijkExpected.put(new Vertex<>('B'), 0);
        dijkExpected.put(new Vertex<>('C'), 3);
        dijkExpected.put(new Vertex<>('D'), 8);
        dijkExpected.put(new Vertex<>('E'), 1);
        dijkExpected.put(new Vertex<>('F'), 3);
        dijkExpected.put(new Vertex<>('X'), Integer.MAX_VALUE);

        assertEquals(dijkExpected, dijkActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasLargeGraph() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
                new Vertex<>('A'), largeUndirected);

        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        int[] order = new int[]{0, 9, 6, 7, 6, 5, 5, 2, 2, 6, 4, 6, 3,
            8, 5, 8, 5, 6, 5, 7, 10, 4, 5, 12, 5, 3};

        for (int i = 65; i <= 90; i++) {
            dijkExpected.put(new Vertex<>((char) i), order[i - 65]);
        }

        assertEquals(dijkExpected, dijkActual);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskals() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.kruskals(
                undirected);

        Set<Edge<Character>> mstExpected = new HashSet<>();
        mstExpected.add(new Edge<>(new Vertex<>('A'), new Vertex<>('E'), 1));
        mstExpected.add(new Edge<>(new Vertex<>('E'), new Vertex<>('A'), 1));
        mstExpected.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 1));
        mstExpected.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 1));
        mstExpected.add(new Edge<>(new Vertex<>('E'), new Vertex<>('C'), 2));
        mstExpected.add(new Edge<>(new Vertex<>('C'), new Vertex<>('E'), 2));
        mstExpected.add(new Edge<>(new Vertex<>('B'), new Vertex<>('F'), 3));
        mstExpected.add(new Edge<>(new Vertex<>('F'), new Vertex<>('B'), 3));
        mstExpected.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 6));
        mstExpected.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 6));

        assertEquals(mstExpected, mstActual);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsNull() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        vertices.add(new Vertex<>('A'));
        vertices.add(new Vertex<>('B'));
        vertices.add(new Vertex<>('C'));
        vertices.add(new Vertex<>('D'));
        vertices.add(new Vertex<>('E'));
        vertices.add(new Vertex<>('F'));
        vertices.add(new Vertex<>('X'));

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 3));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 6));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 6));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('A'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 6));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 6));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 5));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('F'), 3));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('E'), 2));

        Graph<Character> graph = new Graph<>(vertices, edges);
        Set<Edge<Character>> mstActual = GraphAlgorithms.kruskals(
                graph);

        assertNull(mstActual);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsLargeGraph() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.kruskals(
                largeUndirected);

        Set<Edge<Character>> mstExpected = new HashSet<>();
        buildMSTExpected(mstExpected, 'K', 'I', 2);
        buildMSTExpected(mstExpected, 'Q', 'R', 3);
        buildMSTExpected(mstExpected, 'E', 'F', 1);
        buildMSTExpected(mstExpected, 'R', 'W', 1);
        buildMSTExpected(mstExpected, 'M', 'H', 1);
        buildMSTExpected(mstExpected, 'S', 'V', 1);
        buildMSTExpected(mstExpected, 'J', 'N', 2);
        buildMSTExpected(mstExpected, 'J', 'O', 2);
        buildMSTExpected(mstExpected, 'M', 'G', 2);
        buildMSTExpected(mstExpected, 'F', 'M', 2);
        buildMSTExpected(mstExpected, 'A', 'I', 2);
        buildMSTExpected(mstExpected, 'B', 'K', 5);
        buildMSTExpected(mstExpected, 'X', 'R', 6);
        buildMSTExpected(mstExpected, 'V', 'Z', 1);
        buildMSTExpected(mstExpected, 'V', 'F', 1);
        buildMSTExpected(mstExpected, 'Z', 'I', 1);
        buildMSTExpected(mstExpected, 'E', 'T', 2);
        buildMSTExpected(mstExpected, 'Y', 'L', 1);
        buildMSTExpected(mstExpected, 'S', 'D', 2);
        buildMSTExpected(mstExpected, 'P', 'E', 3);
        buildMSTExpected(mstExpected, 'Z', 'O', 2);
        buildMSTExpected(mstExpected, 'Z', 'C', 3);
        buildMSTExpected(mstExpected, 'K', 'Q', 1);
        buildMSTExpected(mstExpected, 'U', 'J', 4);
        buildMSTExpected(mstExpected, 'L', 'S', 1);

        assertEquals(mstExpected, mstActual);
    }
}