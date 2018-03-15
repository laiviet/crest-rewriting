package data;

import org.jgrapht.graph.DefaultEdge;

import java.io.Serializable;

public class LabeledEdge extends DefaultEdge implements Serializable {

    Node head;
    Node tail;

    String label;

    public LabeledEdge(Node head, Node tail, String label) {
        this.head = head;
        this.tail = tail;
        this.label = label;
    }

    public Node getHead() {
        return head;
    }


    public Node getTail() {
        return tail;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    public String getLabel() {
        return label;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return head.toString() +" "+ label +" "+ tail.toString();
    }
}
