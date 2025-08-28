package object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DirectedAcyclicGraph {
    Map<Vertex, List<Vertex>> adjacencyList;
    List<Vertex> vertices;

    public DirectedAcyclicGraph(List<Edge> edges, List<Vertex> vertices) {
        this.vertices = vertices;
        adjacencyList = createAdjacencyList(edges);
    }

    public Map<Vertex, List<Vertex>> createAdjacencyList(List<Edge> edges) {
        Map<Vertex, List<Vertex>> graph = new HashMap<>();
        edges.forEach(edge -> graph.computeIfAbsent(edge.from, k -> new ArrayList<>()).add(edge.to));

        return graph;
    }

    public Path findLongestPath(Vertex source) {
        if (!vertices.contains(source)) {
            return new Path();
        }
        Stack<Vertex> stack = topologicalSortVertices();
        Map<Vertex, Integer> distances = new HashMap<Vertex, Integer>();
        distances.put(source, 0);
        Map<Vertex, Path> longestPaths = new HashMap<Vertex, Path>();

        // Process vertices in topological order
        Integer longestDistance = Integer.MIN_VALUE;
        Path longestPath = longestPaths.getOrDefault(source, new Path(source));
        while (!stack.isEmpty()) {
            Vertex next = stack.pop();
            Path nextPath = longestPaths.getOrDefault(next, new Path(next));
            Integer nextDistance = distances.getOrDefault(next, Integer.MIN_VALUE);
            if (nextDistance == Integer.MIN_VALUE) {
                continue;
            }
            
            // Update distances of all adjacent vertices
            List<Vertex> children = adjacencyList.getOrDefault(next, new ArrayList<>());
            for (Vertex child : children) {
                Integer childDistance = distances.getOrDefault(child, Integer.MIN_VALUE);
                if (childDistance < nextDistance + 1) {
                    distances.put(child, nextDistance + 1);

                    // Update longestPaths for the child
                    Path childPath = nextPath.createCopy();
                    childPath.add(child);
                    longestPaths.put(child, childPath);
                    if (distances.get(child) > longestDistance) {
                        longestDistance = distances.get(child);
                        longestPath = childPath;
                    }
                }
            }
        }

        return longestPath;
    }

    /**
     * Creates a stack of vertices ordered such that:
     * For every directed edge u-v, vertex u is closer to the top of the stack than vertex v.
     */
    private Stack<Vertex> topologicalSortVertices() {
        Stack<Vertex> stack = new Stack<Vertex>();
        Map<Vertex, Boolean> visited = new HashMap<Vertex, Boolean>();

        // Build the stack by topologically-sorting vertices
        for (Vertex vertex : vertices) {
            if (!visited.getOrDefault(vertex, false)) {
                topologicalSortVerticesRecursive(vertex, visited, stack);
            }
        }
        
        return stack;
    }

    private void topologicalSortVerticesRecursive(Vertex current, Map<Vertex, Boolean> visited, Stack<Vertex> stack) {
        // Mark the current node as visited
        visited.put(current, true);

        // Recur for all the vertices adjacent to this vertex (that haven't been visited)
        List<Vertex> children = adjacencyList.getOrDefault(current, new ArrayList<>());
        for (Vertex child : children) {
            if (!visited.getOrDefault(child, false)) {
                topologicalSortVerticesRecursive(child, visited, stack);
            }
        }

        // Push current vertex onto stack
        stack.push(current);
    }
}
