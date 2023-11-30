import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Sam Nelson
 * @version 1.0
 * @userid snelson73
 * @GTID 903754732
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start vertex not in graph!");
        }
        LinkedList<Vertex<T>> queue = new LinkedList<>();
        List<Vertex<T>> toRet = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            Vertex<T> curr = queue.remove();
            toRet.add(curr);
            List<VertexDistance<T>> adjacentVertices = adjList.get(curr);
            for (VertexDistance<T> vertexDistance : adjacentVertices) {
                Vertex<T> currVert = vertexDistance.getVertex();
                if (!visited.contains(currVert)) {
                    visited.add(currVert);
                    queue.add(currVert);
                }
            }
        }

        return toRet;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start vertex not in graph!");
        }

        List<Vertex<T>> toRet = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();

        dfsH(start, graph.getAdjList(), toRet, visited);

        return toRet;
    }

    /**
     * Recursive helper method for DFS that includes visited set, adjacency lit, and return list in recursive call.
     *
     * @param curr the current vertex in the graph
     * @param adjList list of vertices and their adjacent vertices
     * @param toRet list of vertices in graph
     * @param visited set of vertices that have been visited in traversal
     * @param <T> type of data in vertices
     */
    private static <T> void dfsH(Vertex<T> curr, Map<Vertex<T>,
            List<VertexDistance<T>>> adjList, List<Vertex<T>> toRet, Set<Vertex<T>> visited) {
        toRet.add(curr);
        visited.add(curr);
        List<VertexDistance<T>> adjacentVertices = adjList.get(curr);
        for (VertexDistance<T> vertex : adjacentVertices) {
            if (!visited.contains(vertex.getVertex())) {
                dfsH(vertex.getVertex(), adjList, toRet, visited);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start vertex not in graph!");
        }

        Set<Vertex<T>> visited = new HashSet<>();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();
        PriorityQueue<VertexDistance<T>> heap = new PriorityQueue<>();

        for (Vertex<T> curr : graph.getVertices()) {
            distanceMap.put(curr, Integer.MAX_VALUE);
        }

        heap.add(new VertexDistance<>(start, 0));

        while (!heap.isEmpty() && visited.size() < distanceMap.size()) {
            VertexDistance<T> minPath = heap.remove();
            Vertex<T> curr = minPath.getVertex();
            int minWeight = minPath.getDistance();
            if (!visited.contains(curr)) {
                visited.add(curr);
                distanceMap.put(curr, minPath.getDistance());
                for (VertexDistance<T> incident: graph.getAdjList().get(curr)) {
                    if (!visited.contains(incident.getVertex())) {
                        heap.add(new VertexDistance<>(incident.getVertex(), incident.getDistance() + minWeight));
                    }
                }
            }
        }

        return distanceMap;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * An MST should NOT have self-loops or parallel edges.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Input cannot be null!");
        }

        Set<Edge<T>> mst = new HashSet<>();
        PriorityQueue<Edge<T>> edgeHeap = new PriorityQueue<>(graph.getEdges());
        DisjointSet<Vertex<T>> cluster = new DisjointSet<>();

        while (!edgeHeap.isEmpty() && mst.size() < (2 * (graph.getVertices().size() - 1))) {
            Edge<T> curr = edgeHeap.remove();
            Vertex<T> start = curr.getU();
            Vertex<T> end = curr.getV();
            if (!cluster.find(start).equals(cluster.find(end))) {
                mst.add(curr);
                mst.add(new Edge<>(end, start, curr.getWeight()));
                cluster.union(start, end);
            }
        }

        if (mst.size() == 2 * (graph.getVertices().size() - 1)) {
            return mst;
        } else {
            return null;
        }
    }
}
