import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * No property tests; generating random graphs is hard :(
 * 
 * (10th set of unit tests! Double digits!!)
 *
 * @author Alexander Gualino
 * @version 1.0
 */
public class ExhaustiveTests {
    private static final int TIMEOUT = 200;

    // simple checks
    @Test(timeout = TIMEOUT)
    public void testBfsSingleNode() {
        var node = new Vertex<>(5);
        var results = GraphAlgorithms.bfs(node, new Graph<>(Collections.singleton(node), Collections.emptySet()));

        assertEquals(1, results.size());
        assertEquals(node, results.get(0));
    }

    @Test(timeout = TIMEOUT)
    public void testDfsSingleNode() {
        var node = new Vertex<>(5);
        var results = GraphAlgorithms.dfs(node, new Graph<>(Collections.singleton(node), Collections.emptySet()));

        assertEquals(1, results.size());
        assertEquals(node, results.get(0));
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasSingleNode() {
        var node = new Vertex<>(5);
        var results = GraphAlgorithms.dijkstras(node, new Graph<>(Collections.singleton(node), Collections.emptySet()));

        assertEquals(1, results.size());
        assertEquals(0, (int) results.get(node));
    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsSingleNode() {
        var node = new Vertex<>(5);
        var results = GraphAlgorithms.kruskals(new Graph<>(Collections.singleton(node), Collections.emptySet()));

        assertNotNull(results);
        assertEquals(0, results.size());
    }

    // performance things
    @Test(timeout = TIMEOUT)
    public void testDijkstrasPreemptiveCheck() {
        var count = new int[]{0};
        var vertex = new Vertex<>(42) {
            @Override
            public boolean equals(Object o) {
                count[0]++;
                return super.equals(o);
            }
        };
        var evil = new Vertex<>(0) {
            @Override
            public int hashCode() {
                // >:]
                return vertex.hashCode();
            }
        };

        var vertices = new HashSet<Vertex<Integer>>();
        vertices.add(evil);
        // can't use `vertex` here because hashmap uses identity before .equals
        vertices.add(new Vertex<>(42));

        var result = GraphAlgorithms.dijkstras(evil, new Graph<>(vertices, Collections.singleton(new Edge<>(evil, vertex, 3))));

        // 4 from initialization, 1 from 1st .contains, 1 from .add, 1 from optimization (2nd .contains)
        assertEquals(7, count[0]);
        assertEquals(2, result.size());
        assertEquals(0, (int) result.get(evil));
        assertEquals(3, (int) result.get(vertex));
    }

    // error conditions
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBfsNullStart() {
        GraphAlgorithms.bfs(null, new Graph<>(Collections.emptySet(), Collections.emptySet()));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBfsNullGraph() {
        GraphAlgorithms.bfs(new Vertex<>(5), null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBfsEmptyGraph() {
        GraphAlgorithms.bfs(new Vertex<>(5), new Graph<>(Collections.emptySet(), Collections.emptySet()));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBfsMissingNode() {
        GraphAlgorithms.bfs(new Vertex<>(5), new Graph<>(Collections.singleton(new Vertex<>(3)), Collections.emptySet()));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDfsNullStart() {
        GraphAlgorithms.dfs(null, new Graph<>(Collections.emptySet(), Collections.emptySet()));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDfsNullGraph() {
        GraphAlgorithms.dfs(new Vertex<>(5), null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDfsEmptyGraph() {
        GraphAlgorithms.dfs(new Vertex<>(5), new Graph<>(Collections.emptySet(), Collections.emptySet()));
    }


    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDfsMissingNode() {
        GraphAlgorithms.dfs(new Vertex<>(5), new Graph<>(Collections.singleton(new Vertex<>(3)), Collections.emptySet()));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDijkstrasNullStart() {
        GraphAlgorithms.dijkstras(null, new Graph<>(Collections.emptySet(), Collections.emptySet()));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDijkstrasNullGraph() {
        GraphAlgorithms.dijkstras(new Vertex<>(5), null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDijkstrasEmptyGraph() {
        GraphAlgorithms.dijkstras(new Vertex<>(5), new Graph<>(Collections.emptySet(), Collections.emptySet()));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDijkstrasMissingNode() {
        GraphAlgorithms.dijkstras(new Vertex<>(5), new Graph<>(Collections.singleton(new Vertex<>(3)), Collections.emptySet()));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testKruskalsNullGraph() {
        GraphAlgorithms.kruskals(null);
    }

    // exhaustive stuff
    @Test(timeout = TIMEOUT)
    public void testDijkstrasSecondCondition() {
        var count = new int[]{0};
        var vertex = new Vertex<>(42) {
            @Override
            public boolean equals(Object o) {
                count[0]++;
                return super.equals(o);
            }
        };

        var a = new Vertex<>(1);
        var b = new Vertex<>(42);
        var vertices = new HashSet<Vertex<Integer>>();
        vertices.add(a);
        vertices.add(b);

        var edges = new HashSet<Edge<Integer>>();
        edges.add(new Edge<>(a, b, 3));
        edges.add(new Edge<>(a, vertex, 4));

        var result = GraphAlgorithms.dijkstras(a, new Graph<>(vertices, edges));

        assertEquals(0, count[0]);
        assertEquals(2, result.size());
        assertEquals(0, (int) result.get(a));
        assertEquals(3, (int) result.get(vertex));
        assertEquals(3, (int) result.get(b));
    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsSecondCondition() {
        var count = new int[]{0};
        var vertex = new Vertex<>(42) {
            @Override
            public boolean equals(Object o) {
                count[0]++;
                return super.equals(o);
            }
        };

        var a = new Vertex<>(1);
        var b = new Vertex<>(42);
        var vertices = new HashSet<Vertex<Integer>>();
        vertices.add(a);
        vertices.add(b);

        var edges = new HashSet<Edge<Integer>>();
        edges.add(new Edge<>(a, b, 3));
        edges.add(new Edge<>(b, a, 3));
        edges.add(new Edge<>(a, vertex, 4));
        edges.add(new Edge<>(vertex, a, 4));

        var result = GraphAlgorithms.kruskals(new Graph<>(vertices, edges));

        assertNotNull(result);
        // the 4 below is due to various initialization things.
        //  (use a debugger + a breakpoint at count[0]++ if you're curious)
        assertEquals(4, count[0]);
        assertEquals(2, result.size());
    }
}
