package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import object.DirectedAcyclicGraph;
import object.Edge;
import object.Path;
import object.Vertex;

/**
 * Tests written for JUnit Jupiter
 */
public class DirectedAcyclicGraphTest {
    private List<Vertex> createVertices(int n) {
        List<Vertex> vertices = new ArrayList<Vertex>();
        for (int i = 0; i < n; i++) {
            vertices.add(new Vertex(i));
        }
        
        return vertices;
    }

    private List<Vertex> createVerticesWithRandomIds(int n) {
        List<Vertex> vertices = new ArrayList<Vertex>();
        HashMap<Long, Boolean> idMap = new HashMap<Long, Boolean>();
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            Long id = r.nextLong();
            int j = 1;
            while (idMap.containsKey(id) && j < 10) {
                id++;
            }
            if (j == 10) {
                throw new Error("Unable to produce unique random ids");
            }
            idMap.put(id, true);
            vertices.add(new Vertex(id));
        }
        
        return vertices;
    }

    private List<Edge> createEdges(Edge... edges) {
        return new ArrayList<>(Arrays.asList(edges));
    }

    /**
     * The requirements weren't clear on this point, so I decided to return an empty path.
     * This is something I would ask the customer (whoever the function is for) to get clarity on.
     */
    @Test
    public void findLongestPath_nullInput_returnsEmptyPath() {
        List<Vertex> vertices = new LinkedList<>();
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(createEdges(), vertices);
        Path actual = graph.findLongestPath(null);
        Path expected = new Path();
        assertEquals(expected, actual);
    }

    /**
     * The requirements weren't clear on this point, so I decided to return an empty path.
     * This is something I would ask the customer (whoever the function is for) to get clarity on.
     */
    @Test
    public void findLongestPath_emptyGraph_returnsEmptyPath() {
        List<Vertex> vertices = new LinkedList<>();
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(createEdges(), vertices);
        Path actual = graph.findLongestPath(new Vertex());
        Path expected = new Path();
        assertEquals(expected, actual);
    }

    /**
     * The requirements weren't clear on this point, so I decided that a path always contains 
     * the starting vertex.
     * This is something I would ask the customer (whoever the function is for) to get clarity on.
     */
    @Test
    public void findLongestPath_oneVertexNoEdges_containsVertex() {
        List<Vertex> vertices = createVertices(1);
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(createEdges(), vertices);
        Path actual = graph.findLongestPath(vertices.get(0));
        Path expected = new Path(vertices.get(0));
        assertEquals(expected, actual);
    }

    @Test
    public void findLongestPath_twoConnectedVertices_fromFirst_containsBoth() {
        List<Vertex> vertices = createVertices(2);
        List<Edge> edges = createEdges(new Edge(vertices.get(0), vertices.get(1)));
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(edges, vertices);
        Path actual = graph.findLongestPath(vertices.get(0));
        Path expected = new Path(vertices.get(0), vertices.get(1));
        assertEquals(expected, actual);
    }

    @Test
    public void findLongestPath_twoConnectedVertices_fromSecond_containsSecond() {
        List<Vertex> vertices = createVertices(2);
        List<Edge> edges = createEdges(new Edge(vertices.get(0), vertices.get(1)));
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(edges, vertices);
        Path actual = graph.findLongestPath(vertices.get(1));
        Path expected = new Path(vertices.get(1));
        assertEquals(expected, actual);
    }

    @Test
    public void findLongestPath_twoConnectedToOne_fromOne_containsOne() {
        List<Vertex> vertices = createVertices(2);
        List<Edge> edges = createEdges(new Edge(vertices.get(1), vertices.get(0)));
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(edges, vertices);
        Path actual = graph.findLongestPath(vertices.get(0));
        Path expected = new Path(vertices.get(0));
        assertEquals(expected, actual);
    }

    @Test
    public void findLongestPath_threeConnectedVertices_fromFirst_containsAll() {
        List<Vertex> vertices = createVertices(3);
        List<Edge> edges = createEdges(
            new Edge(vertices.get(0), vertices.get(1)),
            new Edge(vertices.get(1), vertices.get(2))
        );
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(edges, vertices);
        Path actual = graph.findLongestPath(vertices.get(0));
        Path expected = new Path(
            vertices.get(0), 
            vertices.get(1),
            vertices.get(2)
        );
        assertEquals(expected, actual);
    }

    @Test
    public void findLongestPath_startInMiddleOfPath_andLongerPathExistsElsewhere_returnsLongestPathFromSource() {
        List<Vertex> vertices = createVertices(6);
        List<Edge> edges = createEdges(
            new Edge(vertices.get(0), vertices.get(1)),
            new Edge(vertices.get(1), vertices.get(2)),
            new Edge(vertices.get(0), vertices.get(3)),
            new Edge(vertices.get(3), vertices.get(4)),
            new Edge(vertices.get(4), vertices.get(5))
        );
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(edges, vertices);
        Path actual = graph.findLongestPath(vertices.get(1));
        Path expected = new Path(
            vertices.get(1), 
            vertices.get(2)
        );
        assertEquals(expected, actual);
    }

    @Test
    public void findLongestPath_twoPathsFromSource_longestSecond_returnsLongestPath() {
        List<Vertex> vertices = createVertices(4);
        List<Edge> edges = createEdges(
            new Edge(vertices.get(0), vertices.get(1)),
            new Edge(vertices.get(0), vertices.get(2)),
            new Edge(vertices.get(2), vertices.get(3))
        );
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(edges, vertices);
        Path actual = graph.findLongestPath(vertices.get(0));
        Path expected = new Path(
            vertices.get(0),
            vertices.get(2),
            vertices.get(3)
        );
        assertEquals(expected, actual);
    }

    @Test
    public void findLongestPath_twoPathsFromSource_longestFirst_returnsLongestPath() {
        List<Vertex> vertices = createVertices(4);
        List<Edge> edges = createEdges(
            new Edge(vertices.get(0), vertices.get(1)),
            new Edge(vertices.get(1), vertices.get(2)),
            new Edge(vertices.get(0), vertices.get(3))
        );
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(edges, vertices);
        Path actual = graph.findLongestPath(vertices.get(0));
        Path expected = new Path(
            vertices.get(0),
            vertices.get(1),
            vertices.get(2)
        );
        assertEquals(expected, actual);
    }

    /**
     * The requirements weren't clear on this point, so I decided that when there's a tie, we 
     * return the first path.
     * This is something I would ask the customer (whoever the function is for) to get clarity on.
     */
    @Test
    public void findLongestPath_twoPathsSameLengthFromSource_returnsFirstPath() {
        List<Vertex> vertices = createVertices(4);
        List<Edge> edges = createEdges(
            new Edge(vertices.get(0), vertices.get(1)),
            new Edge(vertices.get(0), vertices.get(2))
        );
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(edges, vertices);
        Path actual = graph.findLongestPath(vertices.get(0));
        Path expected = new Path(
            vertices.get(0),
            vertices.get(1)
        );
        assertEquals(expected, actual);
    }

    @Test
    public void findLongestPath_disconnectedGraph_returnsLongestPathFromSource() {
        List<Vertex> vertices = createVertices(5);
        List<Edge> edges = createEdges(
            new Edge(vertices.get(0), vertices.get(1)),
            new Edge(vertices.get(4), vertices.get(3)),
            new Edge(vertices.get(3), vertices.get(2))
        );
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(edges, vertices);
        Path actual = graph.findLongestPath(vertices.get(4));
        Path expected = new Path(
            vertices.get(4),
            vertices.get(3),
            vertices.get(2)
        );
        assertEquals(expected, actual);
    }

    @Test
    public void findLongestPath_diamondGraph_returnsLongestPathFromSource() {
        List<Vertex> vertices = createVertices(5);
        List<Edge> edges = createEdges(
            new Edge(vertices.get(0), vertices.get(2)),
            new Edge(vertices.get(0), vertices.get(3)),
            new Edge(vertices.get(1), vertices.get(2)),
            new Edge(vertices.get(1), vertices.get(3)),
            new Edge(vertices.get(3), vertices.get(2)),
            new Edge(vertices.get(4), vertices.get(0)),
            new Edge(vertices.get(4), vertices.get(1)),
            new Edge(vertices.get(4), vertices.get(2))
        );
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(edges, vertices);
        Path actual = graph.findLongestPath(vertices.get(4));
        Path expected = new Path(
            vertices.get(4),
            vertices.get(1),
            vertices.get(3),
            vertices.get(2)
        );
        assertEquals(expected, actual);
    }

    @Test
    public void findLongestPath_diamondGraph_reverseOrderEdgesAdded_returnsLongestPathFromSource() {
        List<Vertex> vertices = createVertices(5);
        List<Edge> edges = createEdges(
            new Edge(vertices.get(4), vertices.get(2)),
            new Edge(vertices.get(4), vertices.get(1)),
            new Edge(vertices.get(4), vertices.get(0)),
            new Edge(vertices.get(3), vertices.get(2)),
            new Edge(vertices.get(1), vertices.get(3)),
            new Edge(vertices.get(1), vertices.get(2)),
            new Edge(vertices.get(0), vertices.get(3)),
            new Edge(vertices.get(0), vertices.get(2))
        );
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(edges, vertices);
        Path actual = graph.findLongestPath(vertices.get(4));
        Path expected = new Path(
            vertices.get(4),
            vertices.get(1),
            vertices.get(3),
            vertices.get(2)
        );
        assertEquals(expected, actual);
    }

    @Test
    public void findLongestPath_slightlyMoreComplicatedGraph_returnsLongestPathFromSource() {
        List<Vertex> vertices = createVertices(8);
        List<Edge> edges = createEdges(
            new Edge(vertices.get(0), vertices.get(1)),
            new Edge(vertices.get(0), vertices.get(2)),
            new Edge(vertices.get(1), vertices.get(2)),
            new Edge(vertices.get(1), vertices.get(3)),
            new Edge(vertices.get(1), vertices.get(4)),
            new Edge(vertices.get(2), vertices.get(3)),
            new Edge(vertices.get(2), vertices.get(6)),
            new Edge(vertices.get(3), vertices.get(4)),
            new Edge(vertices.get(3), vertices.get(5)),
            new Edge(vertices.get(3), vertices.get(6)),
            new Edge(vertices.get(4), vertices.get(7)),
            new Edge(vertices.get(5), vertices.get(7)),
            new Edge(vertices.get(6), vertices.get(5)),
            new Edge(vertices.get(6), vertices.get(7))
        );
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(edges, vertices);
        Path actual = graph.findLongestPath(vertices.get(0));
        Path expected = new Path(
            vertices.get(0),
            vertices.get(1),
            vertices.get(2),
            vertices.get(3),
            vertices.get(6),
            vertices.get(5),
            vertices.get(7)
        );
        assertEquals(expected, actual);
    }

    @Test
    public void findLongestPath_tenVertices_returnsLongestPathFromSource() {
        List<Vertex> vertices = createVertices(10);
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 10; j++) {
                edges.add(new Edge(vertices.get(i), vertices.get(j)));
            }
        }
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(edges, vertices);
        Path actual = graph.findLongestPath(vertices.get(0));
        Path expected = new Path(vertices);
        assertEquals(expected, actual);
    }

    @Test
    public void findLongestPath_tenVertices_randomIds_returnsLongestPathFromSource() {
        List<Vertex> vertices = createVerticesWithRandomIds(10);
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 10; j++) {
                edges.add(new Edge(vertices.get(i), vertices.get(j)));
            }
        }
        DirectedAcyclicGraph graph = new DirectedAcyclicGraph(edges, vertices);
        Path actual = graph.findLongestPath(vertices.get(0));
        Path expected = new Path(vertices);
        assertEquals(expected, actual);
    }

    // /**
    //  * This test takes non-negligible time to run, so only uncomment it when you 
    //  * need to check the algorithm's performance.
    //  */
    // @Test
    // public void findLongestPath_oneHundredVertices_returnsLongestPathFromSource() {
    //     List<Vertex> vertices = createVertices(100);
    //     List<Edge> edges = new ArrayList<>();
    //     for (int i = 0; i < 99; i++) {
    //         for (int j = i + 1; j < 100; j++) {
    //             edges.add(new Edge(vertices.get(i), vertices.get(j)));
    //         }
    //     }
    //     DirectedAcyclicGraph graph = new DirectedAcyclicGraph(edges, vertices);
    //     Path actual = graph.findLongestPath(vertices.get(0));
    //     Path expected = new Path(vertices);
    //     assertEquals(expected, actual);
    // }

    // /**
    //  * This test takes a long time to run (multiple seconds), so only uncomment it when you 
    //  * need to check the algorithm's performance.
    //  */
    // @Test
    // public void findLongestPath_oneThousandVertices_returnsLongestPathFromSource() {
    //     List<Vertex> vertices = createVertices(1000);
    //     List<Edge> edges = new ArrayList<>();
    //     for (int i = 0; i < 999; i++) {
    //         for (int j = i + 1; j < 1000; j++) {
    //             edges.add(new Edge(vertices.get(i), vertices.get(j)));
    //         }
    //     }
    //     DirectedAcyclicGraph graph = new DirectedAcyclicGraph(edges, vertices);
    //     Path actual = graph.findLongestPath(vertices.get(0));
    //     Path expected = new Path(vertices);
    //     assertEquals(expected, actual);
    // }
}
