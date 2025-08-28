package object;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Path {
    List<Vertex> vertices;

    public Path(Vertex... vertices) {
        this.vertices = new LinkedList<>(Arrays.asList(vertices));
    }
    public Path(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Path)) {
            return false;
        }

        return vertices.equals(((Path)obj).vertices);
    }

    @Override
    public String toString() {
        return vertices.stream().map(Object::toString).collect(Collectors.joining(","));
    }

    public void add(Vertex v) {
        vertices.add(v);
    }

    public Path createCopy() {
        return new Path(new LinkedList<Vertex>(vertices));
    }

    public int length() {
        return vertices.size();
    }
}
