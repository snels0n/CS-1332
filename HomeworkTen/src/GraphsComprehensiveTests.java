import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ashwin Mudaliar
 * @version 1.1
 * 
 * In a really weird way I'm going to miss making these
 * 
 * NOTE: all tests unless otherwise specified use the csvistool graphs
 * 
 * NOTE 2 for BFS and DFS: If your actual list differs, first check if the differences occur within rank/distance levels, then it's fine - use your judgement for these
 * I'm pretty sure the differences from the csvistool and the expected arrays here are due to the order I add the edges in
 * 
 * EDIT: I know for sure now that it is because of the order they are added in. I would be cautious if your lists differ, but it probably isn't worth stressing about
 * 
 * Planning to add self loop and parallel edges test soon
 * 
 */
public class GraphsComprehensiveTests {

    private final static int TIMEOUT = 500;

    Graph<String> graph;
    
    // clears graph
    @Before()
    public void setup() {

        graph = new Graph<>(new HashSet<>(), new HashSet<>());

    }

    private void makeUndirectedGraph(String[] verticesIn, String[][] edgesIn, int[] weights) {

        Set<Vertex<String>> vertices = new HashSet<>();
        Set<Edge<String>> edges = new HashSet<>();

        for (String v : verticesIn) {

            vertices.add(new Vertex<String>(v));

        }

        int k = 0;
        for (String[] pair : edgesIn) {

            edges.add(new Edge<String>(new Vertex<String>(pair[0]), new Vertex<String>(pair[1]), weights[k]));
            edges.add(new Edge<String>(new Vertex<String>(pair[1]), new Vertex<String>(pair[0]), weights[k]));
            k++;

        }

        graph = new Graph<>(vertices, edges);

    }

    private void makeUndirectedGraph(String[] verticesIn, String[][] edgesIn) {

        int[] defaultWeights = new int[edgesIn.length];

        for (int k = 0; k < defaultWeights.length; k++) {
            defaultWeights[k] = 1;
        }

        makeUndirectedGraph(verticesIn, edgesIn, defaultWeights);

    }

    private List<Vertex<String>> makeExpected(String[] expectedVals) {

        List<Vertex<String>> ret = new ArrayList<Vertex<String>>();

        for (String s : expectedVals) {
            ret.add(ret.size(), new Vertex<String>(s));
        }

        return ret;

    }

    private void makeDirectedGraph(String[] verticesIn, String[][] edgesIn, int[] weights) {

        Set<Vertex<String>> vertices = new HashSet<>();
        Set<Edge<String>> edges = new HashSet<>();

        for (String v : verticesIn) {

            vertices.add(new Vertex<String>(v));

        }

        int k = 0;
        for (String[] pair : edgesIn) {

            edges.add(new Edge<String>(new Vertex<String>(pair[0]), new Vertex<String>(pair[1]), weights[k]));
            k++;

        }

        graph = new Graph<>(vertices, edges);

    }

    private void makeDirectedGraph(String[] verticesIn, String[][] edgesIn) {

        int[] defaultWeights = new int[edgesIn.length];

        for (int k = 0; k < defaultWeights.length; k++) {
            defaultWeights[k] = 1;
        }

        makeDirectedGraph(verticesIn, edgesIn, defaultWeights);

    }

    // Test Error Catching
    @Test(timeout = TIMEOUT, expected = java.lang.IllegalArgumentException.class)
    public void testBFSStartIsNull() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[][] edges = {{"A", "B"}, {"A", "C"}, {"A", "D"}, 
        {"A", "E"}, {"B", "G"}, {"D", "F"}, {"E", "G"}, {"F", "G"}, {"G", "H"}};

        makeUndirectedGraph(vertices, edges);

        GraphAlgorithms.bfs(null, graph);

    }

    @Test(timeout = TIMEOUT, expected = java.lang.IllegalArgumentException.class)
    public void testBFSGraphIsNull() {

        GraphAlgorithms.bfs(new Vertex<String>("Turkey Lover"), null);

    }

    @Test(timeout = TIMEOUT, expected = java.lang.IllegalArgumentException.class)
    public void testBFSVertexNotInGraph() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[][] edges = {{"A", "B"}, {"A", "C"}, {"A", "D"}, 
        {"A", "E"}, {"B", "G"}, {"D", "F"}, {"E", "G"}, {"F", "G"}, {"G", "H"}};

        makeUndirectedGraph(vertices, edges);

        GraphAlgorithms.bfs(new Vertex<String>("GOBBLE GOBBLE"), graph);

    }

    @Test(timeout = TIMEOUT, expected = java.lang.IllegalArgumentException.class)
    public void testDFSStartIsNull() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[][] edges = {{"A", "B"}, {"A", "C"}, {"A", "D"}, 
        {"A", "E"}, {"B", "G"}, {"D", "F"}, {"E", "G"}, {"F", "G"}, {"G", "H"}};

        makeUndirectedGraph(vertices, edges);

        GraphAlgorithms.dfs(null, graph);

    }

    @Test(timeout = TIMEOUT, expected = java.lang.IllegalArgumentException.class)
    public void testDFSGraphIsNull() {

        GraphAlgorithms.dfs(new Vertex<String>("Insert whatever turkeys say"), null);

    }

    @Test(timeout = TIMEOUT, expected = java.lang.IllegalArgumentException.class)
    public void testDFSVertexNotInGraph() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[][] edges = {{"A", "B"}, {"A", "C"}, {"A", "D"}, 
        {"A", "E"}, {"B", "G"}, {"D", "F"}, {"E", "G"}, {"F", "G"}, {"G", "H"}};

        makeUndirectedGraph(vertices, edges);

        GraphAlgorithms.dfs(new Vertex<String>("I AM THANKFUL FOR JUNITS"), graph);

    }

    @Test(timeout = TIMEOUT, expected = java.lang.IllegalArgumentException.class)
    public void testDijkstrasStartIsNull() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[][] edges = {{"A", "B"}, {"A", "C"}, {"A", "D"}, 
        {"A", "E"}, {"B", "G"}, {"D", "F"}, {"E", "G"}, {"F", "G"}, {"G", "H"}};

        makeUndirectedGraph(vertices, edges);

        GraphAlgorithms.dfs(null, graph);

    }

    @Test(timeout = TIMEOUT, expected = java.lang.IllegalArgumentException.class)
    public void testDijkstrasGraphIsNull() {

        GraphAlgorithms.dfs(new Vertex<String>("Insert whatever turkeys say"), null);

    }

    @Test(timeout = TIMEOUT, expected = java.lang.IllegalArgumentException.class)
    public void testDijkstrasVertexNotInGraph() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[][] edges = {{"A", "B"}, {"A", "C"}, {"A", "D"}, 
        {"A", "E"}, {"B", "G"}, {"D", "F"}, {"E", "G"}, {"F", "G"}, {"G", "H"}};

        makeUndirectedGraph(vertices, edges);

        GraphAlgorithms.dfs(new Vertex<String>("I AM THANKFUL FOR JUNITS"), graph);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testKruskalsInputIsNull() {

        GraphAlgorithms.kruskals(null);

    }

    @Test(timeout = TIMEOUT)
    public void testBFSUndirectedSmallGraphFromC() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[][] edges = {{"A", "B"}, {"A", "C"}, {"A", "D"}, 
        {"A", "E"}, {"B", "G"}, {"D", "F"}, {"E", "G"}, {"F", "G"}, {"G", "H"}};

        makeUndirectedGraph(vertices, edges);

        String[] expectedVals = {"C", "A", "B", "D", "E", "G", "F", "H"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.bfs(new Vertex<String>("C"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testBFSUndirectedSmallGraphFromA() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[][] edges = {{"A", "B"}, {"A", "C"}, {"A", "D"}, 
        {"A", "E"}, {"B", "G"}, {"D", "F"}, {"E", "G"}, {"F", "G"}, {"G", "H"}};

        makeUndirectedGraph(vertices, edges);

        String[] expectedVals = {"A", "B", "C", "D", "E", "G", "F", "H"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.bfs(new Vertex<String>("A"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testBFSUndirectedSmallGraphFromB() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[][] edges = {{"A", "B"}, {"A", "C"}, {"A", "D"}, 
        {"A", "E"}, {"B", "G"}, {"D", "F"}, {"E", "G"}, {"F", "G"}, {"G", "H"}};

        makeUndirectedGraph(vertices, edges);

        String[] expectedVals = {"B", "A", "G", "C", "D", "E", "F", "H"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.bfs(new Vertex<String>("B"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testBFSUndirectedSmallGraphFromH() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[][] edges = {{"A", "B"}, {"A", "C"}, {"A", "D"}, 
        {"A", "E"}, {"B", "G"}, {"D", "F"}, {"E", "G"}, {"F", "G"}, {"G", "H"}};

        makeUndirectedGraph(vertices, edges);

        String[] expectedVals = {"H", "G",    "F", "E", "B",   "D", "A", "C"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.bfs(new Vertex<String>("H"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testBFSUndirectedLargeGraphFromI() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "O"}, {"A", "B"},
            {"B", "A"}, {"B", "F"},
            {"D", "G"}, {"D", "K"}, {"D", "R"},
            {"E", "H"}, {"E", "I"},
            {"F", "B"}, {"F", "G"},
            {"G", "D"}, {"G", "K"}, {"G", "F"},
            {"H", "E"}, {"H", "I"}, {"H", "L"},
            {"I", "E"}, {"I", "H"}, {"I", "L"}, {"I", "M"}, {"I", "J"},
            {"J", "I"}, {"J", "M"}, {"J", "N"},
            {"K", "N"}, {"K", "G"}, {"K", "D"},
            {"L", "H"}, {"L", "I"}, {"L", "M"}, {"L", "P"}, {"L", "O"},
            {"M", "J"}, {"M", "I"}, {"M", "L"}, {"M", "P"}, {"M", "Q"},
            {"N", "K"}, {"N", "J"}, {"N", "Q"},
            {"O", "A"}, {"O", "L"}, {"O", "P"},
            {"P", "O"}, {"P", "L"}, {"P", "M"}, {"P", "Q"},
            {"Q", "P"}, {"Q", "M"}, {"Q", "N"},
            {"R", "D"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"I", "H", "J", "L", "M", "E", "N", "O", "P", "Q", "K", "A", "G", "D", "B", "F", "R"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.bfs(new Vertex<String>("I"), graph));

    }
        
    @Test(timeout = TIMEOUT)
    public void testBFSUndirectedLargeGraphFromA() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "O"}, {"A", "B"},
            {"B", "A"}, {"B", "F"},
            {"D", "G"}, {"D", "K"}, {"D", "R"},
            {"E", "H"}, {"E", "I"},
            {"F", "B"}, {"F", "G"},
            {"G", "D"}, {"G", "K"}, {"G", "F"},
            {"H", "E"}, {"H", "I"}, {"H", "L"},
            {"I", "E"}, {"I", "H"}, {"I", "L"}, {"I", "M"}, {"I", "J"},
            {"J", "I"}, {"J", "M"}, {"J", "N"},
            {"K", "N"}, {"K", "G"}, {"K", "D"},
            {"L", "H"}, {"L", "I"}, {"L", "M"}, {"L", "P"}, {"L", "O"},
            {"M", "J"}, {"M", "I"}, {"M", "L"}, {"M", "P"}, {"M", "Q"},
            {"N", "K"}, {"N", "J"}, {"N", "Q"},
            {"O", "A"}, {"O", "L"}, {"O", "P"},
            {"P", "O"}, {"P", "L"}, {"P", "M"}, {"P", "Q"},
            {"Q", "P"}, {"Q", "M"}, {"Q", "N"},
            {"R", "D"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"A", "B", "O", "F", "L", "P", "G", "M", "I", "H", "Q", "D", "K", "J", "E", "N", "R"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.bfs(new Vertex<String>("A"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testBFSUndirectedLargeGraphFromR() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "O"}, {"A", "B"},
            {"B", "A"}, {"B", "F"},
            {"D", "G"}, {"D", "K"}, {"D", "R"},
            {"E", "H"}, {"E", "I"},
            {"F", "B"}, {"F", "G"},
            {"G", "D"}, {"G", "K"}, {"G", "F"},
            {"H", "E"}, {"H", "I"}, {"H", "L"},
            {"I", "E"}, {"I", "H"}, {"I", "L"}, {"I", "M"}, {"I", "J"},
            {"J", "I"}, {"J", "M"}, {"J", "N"},
            {"K", "N"}, {"K", "G"}, {"K", "D"},
            {"L", "H"}, {"L", "I"}, {"L", "M"}, {"L", "P"}, {"L", "O"},
            {"M", "J"}, {"M", "I"}, {"M", "L"}, {"M", "P"}, {"M", "Q"},
            {"N", "K"}, {"N", "J"}, {"N", "Q"},
            {"O", "A"}, {"O", "L"}, {"O", "P"},
            {"P", "O"}, {"P", "L"}, {"P", "M"}, {"P", "Q"},
            {"Q", "P"}, {"Q", "M"}, {"Q", "N"},
            {"R", "D"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"R", "D", "G", "K", "F", "N", "B", "J", "Q", "A", "I", "M", "P", "O", "H", "L", "E"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.bfs(new Vertex<String>("R"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testBFSUndirectedLargeGraphFromC() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "O"}, {"A", "B"},
            {"B", "A"}, {"B", "F"},
            {"D", "G"}, {"D", "K"}, {"D", "R"},
            {"E", "H"}, {"E", "I"},
            {"F", "B"}, {"F", "G"},
            {"G", "D"}, {"G", "K"}, {"G", "F"},
            {"H", "E"}, {"H", "I"}, {"H", "L"},
            {"I", "E"}, {"I", "H"}, {"I", "L"}, {"I", "M"}, {"I", "J"},
            {"J", "I"}, {"J", "M"}, {"J", "N"},
            {"K", "N"}, {"K", "G"}, {"K", "D"},
            {"L", "H"}, {"L", "I"}, {"L", "M"}, {"L", "P"}, {"L", "O"},
            {"M", "J"}, {"M", "I"}, {"M", "L"}, {"M", "P"}, {"M", "Q"},
            {"N", "K"}, {"N", "J"}, {"N", "Q"},
            {"O", "A"}, {"O", "L"}, {"O", "P"},
            {"P", "O"}, {"P", "L"}, {"P", "M"}, {"P", "Q"},
            {"Q", "P"}, {"Q", "M"}, {"Q", "N"},
            {"R", "D"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"C"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.bfs(new Vertex<String>("C"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testBFSDirectedSmallGraphFromC() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "B"}, {"A", "E"},
            {"B", "A"}, {"B", "D"}, {"B", "G"},
            {"C", "B"},
            {"D", "H"},
            {"E", "G"},
            {"F", "D"}, {"F", "H"},
            {"G", "F"},
            {"H", "G"}, {"H", "F"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"C", "B", "A", "G", "D", "E", "F", "H"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.bfs(new Vertex<String>("C"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testBFSDirectedSmallGraphFromA() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "B"}, {"A", "E"},
            {"B", "A"}, {"B", "D"}, {"B", "G"},
            {"C", "B"},
            {"D", "H"},
            {"E", "G"},
            {"F", "D"}, {"F", "H"},
            {"G", "F"},
            {"H", "G"}, {"H", "F"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"A", "B", "E", "G", "D", "F", "H"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.bfs(new Vertex<String>("A"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testBFSDirectedSmallGraphFromE() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "B"}, {"A", "E"},
            {"B", "A"}, {"B", "D"}, {"B", "G"},
            {"C", "B"},
            {"D", "H"},
            {"E", "G"},
            {"F", "D"}, {"F", "H"},
            {"G", "F"},
            {"H", "G"}, {"H", "F"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"E", "G", "F", "D", "H"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.bfs(new Vertex<String>("E"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testBFSDirectedSmallGraphFromF() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "B"}, {"A", "E"},
            {"B", "A"}, {"B", "D"}, {"B", "G"},
            {"C", "B"},
            {"D", "H"},
            {"E", "G"},
            {"F", "D"}, {"F", "H"},
            {"G", "F"},
            {"H", "G"}, {"H", "F"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"F", "D", "H", "G"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.bfs(new Vertex<String>("F"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testBFSDirectedLargeGraphFromQ() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "H"}, {"A", "B"},
            {"B", "C"}, {"B", "F"},
            {"C", "D"},
            {"D", "C"},
            {"E", "A"}, {"E", "H"},
            {"F", "B"}, {"F", "E"}, {"F", "I"}, {"F", "G"},
            {"G", "F"}, {"G", "C"}, {"G", "D"},
            {"H", "A"}, {"H", "E"},
            {"I", "E"}, {"I", "J"},
            {"J", "G"}, {"J", "K"},
            {"L", "H"}, {"L", "O"},
            {"M", "I"}, {"M", "L"}, {"M", "P"}, {"M", "N"}, {"M", "Q"},
            {"N", "Q"},
            {"O", "P"},
            {"P", "Q"},
            {"Q", "P"}, {"Q", "R"}, {"Q", "N"},
            {"R", "K"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"Q", "P", "R", "N", "K"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.bfs(new Vertex<String>("Q"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testBFSDirectedLargeGraphFromI() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "H"}, {"A", "B"},
            {"B", "C"}, {"B", "F"},
            {"C", "D"},
            {"D", "C"},
            {"E", "A"}, {"E", "H"},
            {"F", "B"}, {"F", "E"}, {"F", "I"}, {"F", "G"},
            {"G", "F"}, {"G", "C"}, {"G", "D"},
            {"H", "A"}, {"H", "E"},
            {"I", "E"}, {"I", "J"},
            {"J", "G"}, {"J", "K"},
            {"L", "H"}, {"L", "O"},
            {"M", "I"}, {"M", "L"}, {"M", "P"}, {"M", "N"}, {"M", "Q"},
            {"N", "Q"},
            {"O", "P"},
            {"P", "Q"},
            {"Q", "P"}, {"Q", "R"}, {"Q", "N"},
            {"R", "K"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"I", "J", "E", "K", "G", "A", "H", "F", "D", "C", "B"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.bfs(new Vertex<String>("I"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testBFSDirectedLargeGraphFromF() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "H"}, {"A", "B"},
            {"B", "C"}, {"B", "F"},
            {"C", "D"},
            {"D", "C"},
            {"E", "A"}, {"E", "H"},
            {"F", "B"}, {"F", "E"}, {"F", "I"}, {"F", "G"},
            {"G", "F"}, {"G", "C"}, {"G", "D"},
            {"H", "A"}, {"H", "E"},
            {"I", "E"}, {"I", "J"},
            {"J", "G"}, {"J", "K"},
            {"L", "H"}, {"L", "O"},
            {"M", "I"}, {"M", "L"}, {"M", "P"}, {"M", "N"}, {"M", "Q"},
            {"N", "Q"},
            {"O", "P"},
            {"P", "Q"},
            {"Q", "P"}, {"Q", "R"}, {"Q", "N"},
            {"R", "K"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"F", "G", "E", "B", "I", "D", "C", "A", "H", "J", "K"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.bfs(new Vertex<String>("F"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testBFSDirectedLargeGraphFromM() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "H"}, {"A", "B"},
            {"B", "C"}, {"B", "F"},
            {"C", "D"},
            {"D", "C"},
            {"E", "A"}, {"E", "H"},
            {"F", "B"}, {"F", "E"}, {"F", "I"}, {"F", "G"},
            {"G", "F"}, {"G", "C"}, {"G", "D"},
            {"H", "A"}, {"H", "E"},
            {"I", "E"}, {"I", "J"},
            {"J", "G"}, {"J", "K"},
            {"L", "H"}, {"L", "O"},
            {"M", "I"}, {"M", "L"}, {"M", "P"}, {"M", "N"}, {"M", "Q"},
            {"N", "Q"},
            {"O", "P"},
            {"P", "Q"},
            {"Q", "P"}, {"Q", "R"}, {"Q", "N"},
            {"R", "K"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"M", "L", "N", "I", "P", "Q", "O", "H", "J", "E", "R", "A", "K", "G", "B", "F", "D", "C"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.bfs(new Vertex<String>("M"), graph));

    }
    
    @Test(timeout = TIMEOUT)
    public void testDFSUndirectedSmallGraphFromC() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[][] edges = {{"A", "B"}, {"A", "C"}, {"A", "D"}, 
        {"A", "E"}, {"B", "G"}, {"D", "F"}, {"E", "G"}, {"F", "G"}, {"G", "H"}};

        makeUndirectedGraph(vertices, edges);

        String[] expectedVals = {"C", "A", "B", "G", "F", "D", "E", "H"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.dfs(new Vertex<String>("C"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDFSUndirectedSmallGraphFromA() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[][] edges = {{"A", "B"}, {"A", "C"}, {"A", "D"}, 
        {"A", "E"}, {"B", "G"}, {"D", "F"}, {"E", "G"}, {"F", "G"}, {"G", "H"}};

        makeUndirectedGraph(vertices, edges);

        String[] expectedVals = {"A", "B", "G", "F", "D", "E", "H", "C"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.dfs(new Vertex<String>("A"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDFSUndirectedSmallGraphFromB() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[][] edges = {{"A", "B"}, {"A", "C"}, {"A", "D"}, 
        {"A", "E"}, {"B", "G"}, {"D", "F"}, {"E", "G"}, {"F", "G"}, {"G", "H"}};

        makeUndirectedGraph(vertices, edges);

        String[] expectedVals = {"B", "A", "C", "D", "F", "G", "E", "H"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.dfs(new Vertex<String>("B"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDFSUndirectedSmallGraphFromH() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[][] edges = {{"A", "B"}, {"A", "C"}, {"A", "D"}, 
        {"A", "E"}, {"B", "G"}, {"D", "F"}, {"E", "G"}, {"F", "G"}, {"G", "H"}};

        makeUndirectedGraph(vertices, edges);

        String[] expectedVals = {"H", "G", "F", "D", "A", "B", "C", "E"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.dfs(new Vertex<String>("H"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDFSUndirectedLargeGraphFromI() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "O"}, {"A", "B"},
            {"B", "A"}, {"B", "F"},
            {"D", "G"}, {"D", "K"}, {"D", "R"},
            {"E", "H"}, {"E", "I"},
            {"F", "B"}, {"F", "G"},
            {"G", "D"}, {"G", "K"}, {"G", "F"},
            {"H", "E"}, {"H", "I"}, {"H", "L"},
            {"I", "E"}, {"I", "H"}, {"I", "L"}, {"I", "M"}, {"I", "J"},
            {"J", "I"}, {"J", "M"}, {"J", "N"},
            {"K", "N"}, {"K", "G"}, {"K", "D"},
            {"L", "H"}, {"L", "I"}, {"L", "M"}, {"L", "P"}, {"L", "O"},
            {"M", "J"}, {"M", "I"}, {"M", "L"}, {"M", "P"}, {"M", "Q"},
            {"N", "K"}, {"N", "J"}, {"N", "Q"},
            {"O", "A"}, {"O", "L"}, {"O", "P"},
            {"P", "O"}, {"P", "L"}, {"P", "M"}, {"P", "Q"},
            {"Q", "P"}, {"Q", "M"}, {"Q", "N"},
            {"R", "D"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"I", "H", "L", "M", "J", "N", "K", "G", "F", "B", "A", "O", "P", "Q", "D", "R", "E"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.dfs(new Vertex<String>("I"), graph));

    }
        
    @Test(timeout = TIMEOUT)
    public void testDFSUndirectedLargeGraphFromA() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "O"}, {"A", "B"},
            {"B", "A"}, {"B", "F"},
            {"D", "G"}, {"D", "K"}, {"D", "R"},
            {"E", "H"}, {"E", "I"},
            {"F", "B"}, {"F", "G"},
            {"G", "D"}, {"G", "K"}, {"G", "F"},
            {"H", "E"}, {"H", "I"}, {"H", "L"},
            {"I", "E"}, {"I", "H"}, {"I", "L"}, {"I", "M"}, {"I", "J"},
            {"J", "I"}, {"J", "M"}, {"J", "N"},
            {"K", "N"}, {"K", "G"}, {"K", "D"},
            {"L", "H"}, {"L", "I"}, {"L", "M"}, {"L", "P"}, {"L", "O"},
            {"M", "J"}, {"M", "I"}, {"M", "L"}, {"M", "P"}, {"M", "Q"},
            {"N", "K"}, {"N", "J"}, {"N", "Q"},
            {"O", "A"}, {"O", "L"}, {"O", "P"},
            {"P", "O"}, {"P", "L"}, {"P", "M"}, {"P", "Q"},
            {"Q", "P"}, {"Q", "M"}, {"Q", "N"},
            {"R", "D"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"A", "B", "F", "G", "D", "K", "N", "J", "I", "H", "L", "M", "P", "Q", "O", "E", "R"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.dfs(new Vertex<String>("A"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDFSUndirectedLargeGraphFromR() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "O"}, {"A", "B"},
            {"B", "A"}, {"B", "F"},
            {"D", "G"}, {"D", "K"}, {"D", "R"},
            {"E", "H"}, {"E", "I"},
            {"F", "B"}, {"F", "G"},
            {"G", "D"}, {"G", "K"}, {"G", "F"},
            {"H", "E"}, {"H", "I"}, {"H", "L"},
            {"I", "E"}, {"I", "H"}, {"I", "L"}, {"I", "M"}, {"I", "J"},
            {"J", "I"}, {"J", "M"}, {"J", "N"},
            {"K", "N"}, {"K", "G"}, {"K", "D"},
            {"L", "H"}, {"L", "I"}, {"L", "M"}, {"L", "P"}, {"L", "O"},
            {"M", "J"}, {"M", "I"}, {"M", "L"}, {"M", "P"}, {"M", "Q"},
            {"N", "K"}, {"N", "J"}, {"N", "Q"},
            {"O", "A"}, {"O", "L"}, {"O", "P"},
            {"P", "O"}, {"P", "L"}, {"P", "M"}, {"P", "Q"},
            {"Q", "P"}, {"Q", "M"}, {"Q", "N"},
            {"R", "D"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"R", "D", "G", "F", "B", "A", "O", "L", "M", "I", "H", "E", "J", "N", "K", "Q", "P"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.dfs(new Vertex<String>("R"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDFSUndirectedLargeGraphFromC() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "O"}, {"A", "B"},
            {"B", "A"}, {"B", "F"},
            {"D", "G"}, {"D", "K"}, {"D", "R"},
            {"E", "H"}, {"E", "I"},
            {"F", "B"}, {"F", "G"},
            {"G", "D"}, {"G", "K"}, {"G", "F"},
            {"H", "E"}, {"H", "I"}, {"H", "L"},
            {"I", "E"}, {"I", "H"}, {"I", "L"}, {"I", "M"}, {"I", "J"},
            {"J", "I"}, {"J", "M"}, {"J", "N"},
            {"K", "N"}, {"K", "G"}, {"K", "D"},
            {"L", "H"}, {"L", "I"}, {"L", "M"}, {"L", "P"}, {"L", "O"},
            {"M", "J"}, {"M", "I"}, {"M", "L"}, {"M", "P"}, {"M", "Q"},
            {"N", "K"}, {"N", "J"}, {"N", "Q"},
            {"O", "A"}, {"O", "L"}, {"O", "P"},
            {"P", "O"}, {"P", "L"}, {"P", "M"}, {"P", "Q"},
            {"Q", "P"}, {"Q", "M"}, {"Q", "N"},
            {"R", "D"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"C"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.dfs(new Vertex<String>("C"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDFSDirectedSmallGraphFromC() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "B"}, {"A", "E"},
            {"B", "A"}, {"B", "D"}, {"B", "G"},
            {"C", "B"},
            {"D", "H"},
            {"E", "G"},
            {"F", "D"}, {"F", "H"},
            {"G", "F"},
            {"H", "G"}, {"H", "F"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"C", "B", "A", "E", "G", "F", "D", "H"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.dfs(new Vertex<String>("C"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDFSDirectedSmallGraphFromA() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "B"}, {"A", "E"},
            {"B", "A"}, {"B", "D"}, {"B", "G"},
            {"C", "B"},
            {"D", "H"},
            {"E", "G"},
            {"F", "D"}, {"F", "H"},
            {"G", "F"},
            {"H", "G"}, {"H", "F"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"A", "B", "G", "F", "D", "H", "E"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.dfs(new Vertex<String>("A"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDFSDirectedSmallGraphFromE() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "B"}, {"A", "E"},
            {"B", "A"}, {"B", "D"}, {"B", "G"},
            {"C", "B"},
            {"D", "H"},
            {"E", "G"},
            {"F", "D"}, {"F", "H"},
            {"G", "F"},
            {"H", "G"}, {"H", "F"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"E", "G", "F", "D", "H"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.dfs(new Vertex<String>("E"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDFSDirectedSmallGraphFromF() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "B"}, {"A", "E"},
            {"B", "A"}, {"B", "D"}, {"B", "G"},
            {"C", "B"},
            {"D", "H"},
            {"E", "G"},
            {"F", "D"}, {"F", "H"},
            {"G", "F"},
            {"H", "G"}, {"H", "F"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"F", "D", "H", "G"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.dfs(new Vertex<String>("F"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDFSDirectedLargeGraphFromQ() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "H"}, {"A", "B"},
            {"B", "C"}, {"B", "F"},
            {"C", "D"},
            {"D", "C"},
            {"E", "A"}, {"E", "H"},
            {"F", "B"}, {"F", "E"}, {"F", "I"}, {"F", "G"},
            {"G", "F"}, {"G", "C"}, {"G", "D"},
            {"H", "A"}, {"H", "E"},
            {"I", "E"}, {"I", "J"},
            {"J", "G"}, {"J", "K"},
            {"L", "H"}, {"L", "O"},
            {"M", "I"}, {"M", "L"}, {"M", "P"}, {"M", "N"}, {"M", "Q"},
            {"N", "Q"},
            {"O", "P"},
            {"P", "Q"},
            {"Q", "P"}, {"Q", "R"}, {"Q", "N"},
            {"R", "K"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"Q", "P", "R", "K", "N"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.dfs(new Vertex<String>("Q"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDFSDirectedLargeGraphFromI() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "H"}, {"A", "B"},
            {"B", "C"}, {"B", "F"},
            {"C", "D"},
            {"D", "C"},
            {"E", "A"}, {"E", "H"},
            {"F", "B"}, {"F", "E"}, {"F", "I"}, {"F", "G"},
            {"G", "F"}, {"G", "C"}, {"G", "D"},
            {"H", "A"}, {"H", "E"},
            {"I", "E"}, {"I", "J"},
            {"J", "G"}, {"J", "K"},
            {"L", "H"}, {"L", "O"},
            {"M", "I"}, {"M", "L"}, {"M", "P"}, {"M", "N"}, {"M", "Q"},
            {"N", "Q"},
            {"O", "P"},
            {"P", "Q"},
            {"Q", "P"}, {"Q", "R"}, {"Q", "N"},
            {"R", "K"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"I", "J", "K", "G", "F", "E", "A", "B", "C", "D", "H"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.dfs(new Vertex<String>("I"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDFSDirectedLargeGraphFromF() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "H"}, {"A", "B"},
            {"B", "C"}, {"B", "F"},
            {"C", "D"},
            {"D", "C"},
            {"E", "A"}, {"E", "H"},
            {"F", "B"}, {"F", "E"}, {"F", "I"}, {"F", "G"},
            {"G", "F"}, {"G", "C"}, {"G", "D"},
            {"H", "A"}, {"H", "E"},
            {"I", "E"}, {"I", "J"},
            {"J", "G"}, {"J", "K"},
            {"L", "H"}, {"L", "O"},
            {"M", "I"}, {"M", "L"}, {"M", "P"}, {"M", "N"}, {"M", "Q"},
            {"N", "Q"},
            {"O", "P"},
            {"P", "Q"},
            {"Q", "P"}, {"Q", "R"}, {"Q", "N"},
            {"R", "K"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"F", "G", "D", "C", "E", "A", "B", "H", "I", "J", "K"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.dfs(new Vertex<String>("F"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDFSDirectedLargeGraphFromM() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "H"}, {"A", "B"},
            {"B", "C"}, {"B", "F"},
            {"C", "D"},
            {"D", "C"},
            {"E", "A"}, {"E", "H"},
            {"F", "B"}, {"F", "E"}, {"F", "I"}, {"F", "G"},
            {"G", "F"}, {"G", "C"}, {"G", "D"},
            {"H", "A"}, {"H", "E"},
            {"I", "E"}, {"I", "J"},
            {"J", "G"}, {"J", "K"},
            {"L", "H"}, {"L", "O"},
            {"M", "I"}, {"M", "L"}, {"M", "P"}, {"M", "N"}, {"M", "Q"},
            {"N", "Q"},
            {"O", "P"},
            {"P", "Q"},
            {"Q", "P"}, {"Q", "R"}, {"Q", "N"},
            {"R", "K"}
        };

        makeDirectedGraph(vertices, edges);

        String[] expectedVals = {"M", "L", "O", "P", "Q", "R", "K", "N", "H", "A", "B", "C", "D", "F", "G", "E", "I", "J"};

        assertEquals(makeExpected(expectedVals), GraphAlgorithms.dfs(new Vertex<String>("M"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasUndirectedSmallGraphFromA() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "D"}, {"A", "B"}, {"A", "E"},
            {"B", "F"},
            {"C", "F"}, {"C", "G"},
            {"D", "H"},
            {"E", "G"},
            {"F", "H"}
        };

        int[] weights = {2, 4, 5, 9, 4, 1, 6, 8, 2};

        makeUndirectedGraph(vertices, edges, weights);

        Map<Vertex<String>, Integer> expected = new HashMap<>();

        expected.put(new Vertex<String>("A"), 0);
        expected.put(new Vertex<String>("B"), 4);
        expected.put(new Vertex<String>("C"), 14);
        expected.put(new Vertex<String>("D"), 2);
        expected.put(new Vertex<String>("E"), 5);
        expected.put(new Vertex<String>("F"), 10);
        expected.put(new Vertex<String>("G"), 13);
        expected.put(new Vertex<String>("H"), 8);

        assertEquals(expected, GraphAlgorithms.dijkstras(new Vertex<String>("A"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasUndirectedSmallGraphFromG() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "D"}, {"A", "B"}, {"A", "E"},
            {"B", "F"},
            {"C", "F"}, {"C", "G"},
            {"D", "H"},
            {"E", "G"},
            {"F", "H"}
        };

        int[] weights = {2, 4, 5, 9, 4, 1, 6, 8, 2};

        makeUndirectedGraph(vertices, edges, weights);

        Map<Vertex<String>, Integer> expected = new HashMap<>();

        expected.put(new Vertex<String>("A"), 13);
        expected.put(new Vertex<String>("B"), 14);
        expected.put(new Vertex<String>("C"), 1);
        expected.put(new Vertex<String>("D"), 13);
        expected.put(new Vertex<String>("E"), 8);
        expected.put(new Vertex<String>("F"), 5);
        expected.put(new Vertex<String>("G"), 0);
        expected.put(new Vertex<String>("H"), 7);

        assertEquals(expected, GraphAlgorithms.dijkstras(new Vertex<String>("G"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasUndirectedSmallGraphFromD() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "D"}, {"A", "B"}, {"A", "E"},
            {"B", "F"},
            {"C", "F"}, {"C", "G"},
            {"D", "H"},
            {"E", "G"},
            {"F", "H"}
        };

        int[] weights = {2, 4, 5, 9, 4, 1, 6, 8, 2};

        makeUndirectedGraph(vertices, edges, weights);

        Map<Vertex<String>, Integer> expected = new HashMap<>();

        expected.put(new Vertex<String>("A"), 2);
        expected.put(new Vertex<String>("B"), 6);
        expected.put(new Vertex<String>("C"), 12);
        expected.put(new Vertex<String>("D"), 0);
        expected.put(new Vertex<String>("E"), 7);
        expected.put(new Vertex<String>("F"), 8);
        expected.put(new Vertex<String>("G"), 13);
        expected.put(new Vertex<String>("H"), 6);

        assertEquals(expected, GraphAlgorithms.dijkstras(new Vertex<String>("D"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasUndirectedSmallGraphFromC() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "D"}, {"A", "B"}, {"A", "E"},
            {"B", "F"},
            {"C", "F"}, {"C", "G"},
            {"D", "H"},
            {"E", "G"},
            {"F", "H"}
        };

        int[] weights = {2, 4, 5, 9, 4, 1, 6, 8, 2};

        makeUndirectedGraph(vertices, edges, weights);

        Map<Vertex<String>, Integer> expected = new HashMap<>();

        expected.put(new Vertex<String>("A"), 14);
        expected.put(new Vertex<String>("B"), 13);
        expected.put(new Vertex<String>("C"), 0);
        expected.put(new Vertex<String>("D"), 12);
        expected.put(new Vertex<String>("E"), 9);
        expected.put(new Vertex<String>("F"), 4);
        expected.put(new Vertex<String>("G"), 1);
        expected.put(new Vertex<String>("H"), 6);

        assertEquals(expected, GraphAlgorithms.dijkstras(new Vertex<String>("C"), graph));

    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasUndirectedLargeGraphFromC() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "B"}, {"A", "C"}, {"A", "H"}, {"A", "O"},
            {"B", "A"}, {"B", "C"}, {"B", "E"}, {"B", "F"},
            {"C", "A"}, {"C", "B"}, {"C", "D"}, {"C", "G"},
            {"D", "C"}, {"D", "G"}, {"D", "K"}, {"D", "R"},
            {"E", "B"}, {"E", "F"}, {"E", "H"}, {"E", "I"},
            {"F", "B"}, {"F", "E"}, {"F", "G"}, {"F", "J"},
            {"G", "C"}, {"G", "D"}, {"G", "F"}, {"G", "J"},
            {"H", "A"}, {"H", "E"}, {"H", "I"}, {"H", "O"},
            {"I", "E"}, {"I", "H"}, {"I", "L"}, {"I", "M"},
            {"J", "F"}, {"J", "G"}, {"J", "M"}, {"J", "N"},
            {"K", "D"}, {"K", "N"},
            {"L", "I"}, {"L", "M"}, {"L", "O"}, {"L", "P"},
            {"M", "I"}, {"M", "J"}, {"M", "L"}, {"M", "P"},
            {"N", "J"}, {"N", "K"}, {"N", "Q"},
            {"O", "A"}, {"O", "H"}, {"O", "L"}, {"O", "P"},
            {"P", "L"}, {"P", "M"}, {"P", "O"}, {"P", "Q"}, {"P", "R"},
            {"Q", "N"}, {"Q", "P"}, {"Q", "R"},
            {"R", "D"}, {"R", "P"}, {"R", "Q"},
        };

        int weights[] = {
            2, 6, 4, 4,
            2, 9, 6, 1,
            6, 9, 8, 1,
            8, 7, 3, 8,
            6, 1, 8, 5,
            1, 1, 9, 5,
            1, 7, 9, 7,
            4, 8, 5, 3,
            5, 5, 9, 8,
            5, 7, 9, 1,
            3, 1, 
            9, 5, 8, 7,
            8, 9, 5, 5,
            1, 1, 8,
            4, 3, 8, 8,
            7, 5, 8, 7, 6,
            8, 7, 9,
            8, 6, 9
        };
        
        makeUndirectedGraph(vertices, edges, weights);

        Map<Vertex<String>, Integer> expected = new HashMap<>();

        expected.put(new Vertex<String>("A"), 6);
        expected.put(new Vertex<String>("B"), 8);
        expected.put(new Vertex<String>("C"), 0);
        expected.put(new Vertex<String>("D"), 8);
        expected.put(new Vertex<String>("E"), 10);
        expected.put(new Vertex<String>("F"), 9);
        expected.put(new Vertex<String>("G"), 1);
        expected.put(new Vertex<String>("H"), 10);
        expected.put(new Vertex<String>("I"), 15);
        expected.put(new Vertex<String>("J"), 8);
        expected.put(new Vertex<String>("K"), 10);
        expected.put(new Vertex<String>("L"), 18);
        expected.put(new Vertex<String>("M"), 17);
        expected.put(new Vertex<String>("N"), 9);
        expected.put(new Vertex<String>("O"), 10);
        expected.put(new Vertex<String>("P"), 18);
        expected.put(new Vertex<String>("Q"), 17);
        expected.put(new Vertex<String>("R"), 16);

        assertEquals(expected, GraphAlgorithms.dijkstras(new Vertex<String>("C"), graph)); 

    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasUndirectedLargeGraphFromR() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "B"}, {"A", "C"}, {"A", "H"}, {"A", "O"},
            {"B", "A"}, {"B", "C"}, {"B", "E"}, {"B", "F"},
            {"C", "A"}, {"C", "B"}, {"C", "D"}, {"C", "G"},
            {"D", "C"}, {"D", "G"}, {"D", "K"}, {"D", "R"},
            {"E", "B"}, {"E", "F"}, {"E", "H"}, {"E", "I"},
            {"F", "B"}, {"F", "E"}, {"F", "G"}, {"F", "J"},
            {"G", "C"}, {"G", "D"}, {"G", "F"}, {"G", "J"},
            {"H", "A"}, {"H", "E"}, {"H", "I"}, {"H", "O"},
            {"I", "E"}, {"I", "H"}, {"I", "L"}, {"I", "M"},
            {"J", "F"}, {"J", "G"}, {"J", "M"}, {"J", "N"},
            {"K", "D"}, {"K", "N"},
            {"L", "I"}, {"L", "M"}, {"L", "O"}, {"L", "P"},
            {"M", "I"}, {"M", "J"}, {"M", "L"}, {"M", "P"},
            {"N", "J"}, {"N", "K"}, {"N", "Q"},
            {"O", "A"}, {"O", "H"}, {"O", "L"}, {"O", "P"},
            {"P", "L"}, {"P", "M"}, {"P", "O"}, {"P", "Q"}, {"P", "R"},
            {"Q", "N"}, {"Q", "P"}, {"Q", "R"},
            {"R", "D"}, {"R", "P"}, {"R", "Q"},
        };

        int weights[] = {
            2, 6, 4, 4,
            2, 9, 6, 1,
            6, 9, 8, 1,
            8, 7, 3, 8,
            6, 1, 8, 5,
            1, 1, 9, 5,
            1, 7, 9, 7,
            4, 8, 5, 3,
            5, 5, 9, 8,
            5, 7, 9, 1,
            3, 1, 
            9, 5, 8, 7,
            8, 9, 5, 5,
            1, 1, 8,
            4, 3, 8, 8,
            7, 5, 8, 7, 6,
            8, 7, 9,
            8, 6, 9
        };
        
        makeUndirectedGraph(vertices, edges, weights);

        Map<Vertex<String>, Integer> expected = new HashMap<>();

        expected.put(new Vertex<String>("A"), 18);
        expected.put(new Vertex<String>("B"), 19);
        expected.put(new Vertex<String>("C"), 16);
        expected.put(new Vertex<String>("D"), 8);
        expected.put(new Vertex<String>("E"), 19);
        expected.put(new Vertex<String>("F"), 18);
        expected.put(new Vertex<String>("G"), 15);
        expected.put(new Vertex<String>("H"), 17);
        expected.put(new Vertex<String>("I"), 19);
        expected.put(new Vertex<String>("J"), 13);
        expected.put(new Vertex<String>("K"), 11);
        expected.put(new Vertex<String>("L"), 13);
        expected.put(new Vertex<String>("M"), 11);
        expected.put(new Vertex<String>("N"), 12);
        expected.put(new Vertex<String>("O"), 14);
        expected.put(new Vertex<String>("P"), 6);
        expected.put(new Vertex<String>("Q"), 9);
        expected.put(new Vertex<String>("R"), 0);

        assertEquals(expected, GraphAlgorithms.dijkstras(new Vertex<String>("R"), graph)); 

    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasUndirectedLargeGraphFromJ() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "B"}, {"A", "C"}, {"A", "H"}, {"A", "O"},
            {"B", "A"}, {"B", "C"}, {"B", "E"}, {"B", "F"},
            {"C", "A"}, {"C", "B"}, {"C", "D"}, {"C", "G"},
            {"D", "C"}, {"D", "G"}, {"D", "K"}, {"D", "R"},
            {"E", "B"}, {"E", "F"}, {"E", "H"}, {"E", "I"},
            {"F", "B"}, {"F", "E"}, {"F", "G"}, {"F", "J"},
            {"G", "C"}, {"G", "D"}, {"G", "F"}, {"G", "J"},
            {"H", "A"}, {"H", "E"}, {"H", "I"}, {"H", "O"},
            {"I", "E"}, {"I", "H"}, {"I", "L"}, {"I", "M"},
            {"J", "F"}, {"J", "G"}, {"J", "M"}, {"J", "N"},
            {"K", "D"}, {"K", "N"},
            {"L", "I"}, {"L", "M"}, {"L", "O"}, {"L", "P"},
            {"M", "I"}, {"M", "J"}, {"M", "L"}, {"M", "P"},
            {"N", "J"}, {"N", "K"}, {"N", "Q"},
            {"O", "A"}, {"O", "H"}, {"O", "L"}, {"O", "P"},
            {"P", "L"}, {"P", "M"}, {"P", "O"}, {"P", "Q"}, {"P", "R"},
            {"Q", "N"}, {"Q", "P"}, {"Q", "R"},
            {"R", "D"}, {"R", "P"}, {"R", "Q"},
        };

        int weights[] = {
            2, 6, 4, 4,
            2, 9, 6, 1,
            6, 9, 8, 1,
            8, 7, 3, 8,
            6, 1, 8, 5,
            1, 1, 9, 5,
            1, 7, 9, 7,
            4, 8, 5, 3,
            5, 5, 9, 8,
            5, 7, 9, 1,
            3, 1, 
            9, 5, 8, 7,
            8, 9, 5, 5,
            1, 1, 8,
            4, 3, 8, 8,
            7, 5, 8, 7, 6,
            8, 7, 9,
            8, 6, 9
        };
        
        makeUndirectedGraph(vertices, edges, weights);

        Map<Vertex<String>, Integer> expected = new HashMap<>();

        expected.put(new Vertex<String>("A"), 8);
        expected.put(new Vertex<String>("B"), 6);
        expected.put(new Vertex<String>("C"), 8);
        expected.put(new Vertex<String>("D"), 5);
        expected.put(new Vertex<String>("E"), 6);
        expected.put(new Vertex<String>("F"), 5);
        expected.put(new Vertex<String>("G"), 7);
        expected.put(new Vertex<String>("H"), 12);
        expected.put(new Vertex<String>("I"), 11);
        expected.put(new Vertex<String>("J"), 0);
        expected.put(new Vertex<String>("K"), 2);
        expected.put(new Vertex<String>("L"), 14);
        expected.put(new Vertex<String>("M"), 9);
        expected.put(new Vertex<String>("N"), 1);
        expected.put(new Vertex<String>("O"), 12);
        expected.put(new Vertex<String>("P"), 14);
        expected.put(new Vertex<String>("Q"), 9);
        expected.put(new Vertex<String>("R"), 13);

        assertEquals(expected, GraphAlgorithms.dijkstras(new Vertex<String>("J"), graph)); 

    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasUndirectedLargeGraphFromK() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "B"}, {"A", "C"}, {"A", "H"}, {"A", "O"},
            {"B", "A"}, {"B", "C"}, {"B", "E"}, {"B", "F"},
            {"C", "A"}, {"C", "B"}, {"C", "D"}, {"C", "G"},
            {"D", "C"}, {"D", "G"}, {"D", "K"}, {"D", "R"},
            {"E", "B"}, {"E", "F"}, {"E", "H"}, {"E", "I"},
            {"F", "B"}, {"F", "E"}, {"F", "G"}, {"F", "J"},
            {"G", "C"}, {"G", "D"}, {"G", "F"}, {"G", "J"},
            {"H", "A"}, {"H", "E"}, {"H", "I"}, {"H", "O"},
            {"I", "E"}, {"I", "H"}, {"I", "L"}, {"I", "M"},
            {"J", "F"}, {"J", "G"}, {"J", "M"}, {"J", "N"},
            {"K", "D"}, {"K", "N"},
            {"L", "I"}, {"L", "M"}, {"L", "O"}, {"L", "P"},
            {"M", "I"}, {"M", "J"}, {"M", "L"}, {"M", "P"},
            {"N", "J"}, {"N", "K"}, {"N", "Q"},
            {"O", "A"}, {"O", "H"}, {"O", "L"}, {"O", "P"},
            {"P", "L"}, {"P", "M"}, {"P", "O"}, {"P", "Q"}, {"P", "R"},
            {"Q", "N"}, {"Q", "P"}, {"Q", "R"},
            {"R", "D"}, {"R", "P"}, {"R", "Q"},
        };

        int weights[] = {
            2, 6, 4, 4,
            2, 9, 6, 1,
            6, 9, 8, 1,
            8, 7, 3, 8,
            6, 1, 8, 5,
            1, 1, 9, 5,
            1, 7, 9, 7,
            4, 8, 5, 3,
            5, 5, 9, 8,
            5, 7, 9, 1,
            3, 1, 
            9, 5, 8, 7,
            8, 9, 5, 5,
            1, 1, 8,
            4, 3, 8, 8,
            7, 5, 8, 7, 6,
            8, 7, 9,
            8, 6, 9
        };
        
        makeUndirectedGraph(vertices, edges, weights);

        Map<Vertex<String>, Integer> expected = new HashMap<>();

        expected.put(new Vertex<String>("A"), 10);
        expected.put(new Vertex<String>("B"), 8);
        expected.put(new Vertex<String>("C"), 10);
        expected.put(new Vertex<String>("D"), 3);
        expected.put(new Vertex<String>("E"), 8);
        expected.put(new Vertex<String>("F"), 7);
        expected.put(new Vertex<String>("G"), 9);
        expected.put(new Vertex<String>("H"), 14);
        expected.put(new Vertex<String>("I"), 13);
        expected.put(new Vertex<String>("J"), 2);
        expected.put(new Vertex<String>("K"), 0);
        expected.put(new Vertex<String>("L"), 16);
        expected.put(new Vertex<String>("M"), 11);
        expected.put(new Vertex<String>("N"), 1);
        expected.put(new Vertex<String>("O"), 14);
        expected.put(new Vertex<String>("P"), 16);
        expected.put(new Vertex<String>("Q"), 9);
        expected.put(new Vertex<String>("R"), 11);

        assertEquals(expected, GraphAlgorithms.dijkstras(new Vertex<String>("K"), graph)); 

    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasDirectedSmallGraphFromC() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "C"},
            {"B", "G"}, {"B", "C"},
            {"C", "G"},
            {"E", "G"}, {"E", "H"},
            {"F", "H"}, {"F", "D"}, {"F", "B"},
            {"G", "C"},
            {"H", "G"}
        };

        int weights[] = {7, 1, 6, 1, 8, 9, 5, 1, 2, 2, 2};

        makeDirectedGraph(vertices, edges, weights);

        Map<Vertex<String>, Integer> expected = new HashMap<>();

        expected.put(new Vertex<String>("A"), Integer.MAX_VALUE);
        expected.put(new Vertex<String>("B"), Integer.MAX_VALUE);
        expected.put(new Vertex<String>("C"), 0);
        expected.put(new Vertex<String>("D"), Integer.MAX_VALUE);
        expected.put(new Vertex<String>("E"), Integer.MAX_VALUE);
        expected.put(new Vertex<String>("F"), Integer.MAX_VALUE);
        expected.put(new Vertex<String>("G"), 1);
        expected.put(new Vertex<String>("H"), Integer.MAX_VALUE);

        assertEquals(expected, GraphAlgorithms.dijkstras(new Vertex<String>("C"), graph)); 

    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasDirectedSmallGraphFromE() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "C"},
            {"B", "G"}, {"B", "C"},
            {"C", "G"},
            {"E", "G"}, {"E", "H"},
            {"F", "H"}, {"F", "D"}, {"F", "B"},
            {"G", "C"},
            {"H", "G"}
        };

        int weights[] = {7, 1, 6, 1, 8, 9, 5, 1, 2, 2, 2};

        makeDirectedGraph(vertices, edges, weights);

        Map<Vertex<String>, Integer> expected = new HashMap<>();

        expected.put(new Vertex<String>("A"), Integer.MAX_VALUE);
        expected.put(new Vertex<String>("B"), Integer.MAX_VALUE);
        expected.put(new Vertex<String>("C"), 10);
        expected.put(new Vertex<String>("D"), Integer.MAX_VALUE);
        expected.put(new Vertex<String>("E"), 0);
        expected.put(new Vertex<String>("F"), Integer.MAX_VALUE);
        expected.put(new Vertex<String>("G"), 8);
        expected.put(new Vertex<String>("H"), 9);

        assertEquals(expected, GraphAlgorithms.dijkstras(new Vertex<String>("E"), graph)); 

    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasDirectedSmallGraphFromF() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "C"},
            {"B", "G"}, {"B", "C"},
            {"C", "G"},
            {"E", "G"}, {"E", "H"},
            {"F", "H"}, {"F", "D"}, {"F", "B"},
            {"G", "C"},
            {"H", "G"}
        };

        int weights[] = {7, 1, 6, 1, 8, 9, 5, 1, 2, 2, 2};

        makeDirectedGraph(vertices, edges, weights);

        Map<Vertex<String>, Integer> expected = new HashMap<>();

        expected.put(new Vertex<String>("A"), Integer.MAX_VALUE);
        expected.put(new Vertex<String>("B"), 2);
        expected.put(new Vertex<String>("C"), 5);
        expected.put(new Vertex<String>("D"), 1);
        expected.put(new Vertex<String>("E"), Integer.MAX_VALUE);
        expected.put(new Vertex<String>("F"), 0);
        expected.put(new Vertex<String>("G"), 3);
        expected.put(new Vertex<String>("H"), 5);

        assertEquals(expected, GraphAlgorithms.dijkstras(new Vertex<String>("F"), graph)); 

    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasDirectedSmallGraphFromH() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "C"},
            {"B", "G"}, {"B", "C"},
            {"C", "G"},
            {"E", "G"}, {"E", "H"},
            {"F", "H"}, {"F", "D"}, {"F", "B"},
            {"G", "C"},
            {"H", "G"}
        };

        int weights[] = {7, 1, 6, 1, 8, 9, 5, 1, 2, 2, 2};

        makeDirectedGraph(vertices, edges, weights);

        Map<Vertex<String>, Integer> expected = new HashMap<>();

        expected.put(new Vertex<String>("A"), Integer.MAX_VALUE);
        expected.put(new Vertex<String>("B"), Integer.MAX_VALUE);
        expected.put(new Vertex<String>("C"), 4);
        expected.put(new Vertex<String>("D"), Integer.MAX_VALUE);
        expected.put(new Vertex<String>("E"), Integer.MAX_VALUE);
        expected.put(new Vertex<String>("F"), Integer.MAX_VALUE);
        expected.put(new Vertex<String>("G"), 2);
        expected.put(new Vertex<String>("H"), 0);

        assertEquals(expected, GraphAlgorithms.dijkstras(new Vertex<String>("H"), graph)); 

    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsUndirectedSmallGraph() {

        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};

        String[][] edges = {
            {"A", "E"},
            {"B", "C"}, {"B", "D"}, {"B", "G"},
            {"C", "F"},
            {"D", "H"},
            {"E", "G"},
        };

        int[] weights = {8, 4, 4, 7, 2, 1, 1};

        makeUndirectedGraph(vertices, edges, weights);

        Set<Edge<String>> expected = new HashSet<>();

        String[][] expectedVals = {
            {"D", "H"}, {"E", "G"}, {"C", "F"}, {"B", "C"}, {"B", "D"}, {"B", "G"}, {"A", "E"},
            {"H", "D"}, {"G", "E"}, {"F", "C"}, {"C", "B"}, {"D", "B"}, {"G", "B"}, {"E", "A"},
        };

        int expectedWeights[] = {1, 1, 2, 4, 4, 7, 8,
                                 1, 1, 2, 4, 4, 7, 8};

        int k = 0;
        for (String[] edge : expectedVals) {
            expected.add(new Edge<String>(new Vertex<String>(edge[0]), new Vertex<String>(edge[1]), expectedWeights[k]));
            k++;
        }

        assertEquals(expected, GraphAlgorithms.kruskals(graph));

    }

    /**
     * The graph below has multiple MSTs, so this method checks if the one you output is valid.
     */
    @Test
    public void testKruskalsUndirectedLargeGraph() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R"};

        String[][] edges = {
            {"A", "B"}, {"A", "C"}, {"A", "H"}, {"A", "O"},
            {"B", "A"}, {"B", "C"}, {"B", "E"}, {"B", "F"},
            {"C", "A"}, {"C", "B"}, {"C", "D"}, {"C", "G"},
            {"D", "C"}, {"D", "G"}, {"D", "K"}, {"D", "R"},
            {"E", "B"}, {"E", "F"}, {"E", "H"}, {"E", "I"},
            {"F", "B"}, {"F", "E"}, {"F", "G"}, {"F", "J"},
            {"G", "C"}, {"G", "D"}, {"G", "F"}, {"G", "J"},
            {"H", "A"}, {"H", "E"}, {"H", "I"}, {"H", "O"},
            {"I", "E"}, {"I", "H"}, {"I", "L"}, {"I", "M"},
            {"J", "F"}, {"J", "G"}, {"J", "M"}, {"J", "N"},
            {"K", "D"}, {"K", "N"},
            {"L", "I"}, {"L", "M"}, {"L", "O"}, {"L", "P"},
            {"M", "I"}, {"M", "J"}, {"M", "L"}, {"M", "P"},
            {"N", "J"}, {"N", "K"}, {"N", "Q"},
            {"O", "A"}, {"O", "H"}, {"O", "L"}, {"O", "P"},
            {"P", "L"}, {"P", "M"}, {"P", "O"}, {"P", "Q"}, {"P", "R"},
            {"Q", "N"}, {"Q", "P"}, {"Q", "R"},
            {"R", "D"}, {"R", "P"}, {"R", "Q"},
        };

        int weights[] = {
            2, 6, 4, 4,
            2, 9, 6, 1,
            6, 9, 8, 1,
            8, 7, 3, 8,
            6, 1, 8, 5,
            1, 1, 9, 5,
            1, 7, 9, 7,
            4, 8, 5, 3,
            5, 5, 9, 8,
            5, 7, 9, 1,
            3, 1, 
            9, 5, 8, 7,
            8, 9, 5, 5,
            1, 1, 8,
            4, 3, 8, 8,
            7, 5, 8, 7, 6,
            8, 7, 9,
            8, 6, 9
        };
        
        makeUndirectedGraph(vertices, edges, weights);

        Set<Edge<String>> studentTree = GraphAlgorithms.kruskals(graph);

        System.out.println(studentTree);

        for (Edge<String> edge : studentTree) {

            assertTrue(isEdgePartOfAMST(edge));

        }

    }

    /**
     * Helper method to see if an edge is part of an MST
     * @param inGraph
     * @return if edge is in a MST
     */
    private boolean isEdgePartOfAMST(Edge<String> edge) {


        Set<Edge<String>> newEdges = graph.getEdges();
        newEdges.remove(new Edge<>(edge.getU(), edge.getV(), edge.getWeight()));
        newEdges.remove(new Edge<>(edge.getV(), edge.getU(), edge.getWeight()));

        Graph<String> xGraph = new Graph<>(graph.getVertices(), newEdges);

        return getTreeWeight(GraphAlgorithms.kruskals(graph)) == getTreeWeight(GraphAlgorithms.kruskals(xGraph));
        
    }

    private int getTreeWeight(Set<Edge<String>> tree) {

        if (tree == null) {
            return -1;
        }

        int sum = 0;

        for (Edge<String> edge : tree) {

            sum += edge.getWeight();

        }

        return sum;

    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsDisconnected1() {

        String[] vertices = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "BA", "AB"};

        String[][] edges = {
            {"A", "B"}, {"A", "C"}, {"A", "H"}, {"A", "O"},
            {"B", "A"}, {"B", "C"}, {"B", "E"}, {"B", "F"},
            {"C", "A"}, {"C", "B"}, {"C", "D"}, {"C", "G"},
            {"D", "C"}, {"D", "G"}, {"D", "K"}, {"D", "R"},
            {"E", "B"}, {"E", "F"}, {"E", "H"}, {"E", "I"},
            {"F", "B"}, {"F", "E"}, {"F", "G"}, {"F", "J"},
            {"G", "C"}, {"G", "D"}, {"G", "F"}, {"G", "J"},
            {"H", "A"}, {"H", "E"}, {"H", "I"}, {"H", "O"},
            {"I", "E"}, {"I", "H"}, {"I", "L"}, {"I", "M"},
            {"J", "F"}, {"J", "G"}, {"J", "M"}, {"J", "N"},
            {"K", "D"}, {"K", "N"},
            {"L", "I"}, {"L", "M"}, {"L", "O"}, {"L", "P"},
            {"M", "I"}, {"M", "J"}, {"M", "L"}, {"M", "P"},
            {"N", "J"}, {"N", "K"}, {"N", "Q"},
            {"O", "A"}, {"O", "H"}, {"O", "L"}, {"O", "P"},
            {"P", "L"}, {"P", "M"}, {"P", "O"}, {"P", "Q"}, {"P", "R"},
            {"Q", "N"}, {"Q", "P"}, {"Q", "R"},
            {"R", "D"}, {"R", "P"}, {"R", "Q"},
            {"AB", "BA"}, {"BA", "AB"},
        };

        int weights[] = {
            2, 6, 4, 4,
            2, 9, 6, 1,
            6, 9, 8, 1,
            8, 7, 3, 8,
            6, 1, 8, 5,
            1, 1, 9, 5,
            1, 7, 9, 7,
            4, 8, 5, 3,
            5, 5, 9, 8,
            5, 7, 9, 1,
            3, 1, 
            9, 5, 8, 7,
            8, 9, 5, 5,
            1, 1, 8,
            4, 3, 8, 8,
            7, 5, 8, 7, 6,
            8, 7, 9,
            8, 6, 9,
            3, 4,
        };
        
        makeUndirectedGraph(vertices, edges, weights);

        assertEquals(null, GraphAlgorithms.kruskals(graph));

    }

}