package object;

public class Vertex {
    long id;

    public Vertex() {}
    public Vertex(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vertex)) {
            return false;
        }
        return this.id == ((Vertex)obj).id;
    }

    @Override
    public String toString() {
        return id+"";
    }
}