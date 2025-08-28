package object;

public class Edge {
    public Vertex from;
    public Vertex to;

    public Edge() {}
    public Edge(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[" + from + ", " + to + "]";
    }
}