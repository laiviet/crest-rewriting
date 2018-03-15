package data;

import java.io.Serializable;

public class Node implements Serializable{
    String concept;
    String variable;

    public Node(String concept, String variable) {
        this.concept = concept;
        this.variable = variable;

    }

    public String getVariable() {
        return variable;
    }

    public String getConcept() {
        return concept;
    }

    @Override
    public String toString() {
        return "(" + variable + ":" + concept+ ")";
    }
}
