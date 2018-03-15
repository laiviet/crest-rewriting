package test;

import data.AMRGraph;

public class TestAMRBuilder {


    public static void main(String[] args) {
        
//        System.out.println("List of entities: ");
//        for (String entity: AMRResources.entities){
//            System.out.println(entity);
//        }
//        
        

        String text = "(w / want-01 :polarity -\n" +
                "   :ARG0 (b / boy)\n" +
                "   :ARG1 (b2 / believe-01\n" +
                "             :ARG0 (g / girl)\n" +
                "             :ARG1 b))";
        System.out.println(text);

        AMRGraph g  = new AMRGraph();
        g.buildFromText(text);
        g.print();


    }
}
