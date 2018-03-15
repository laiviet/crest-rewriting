package data;

public class Relation {


    public String head;
    public String rel;
    public String tail;

    public Relation(String head, String tail, String rel) {
        this.head = head;
        this.tail = tail;
        this.rel = rel;
    }

    @Override
    public String toString() {

        return String.format("<%s>:<%s>:<%s>", head, rel, tail);
    }
}
