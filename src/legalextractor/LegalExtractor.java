/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legalextractor;

import data.AMRGraph;
import data.LabeledEdge;
import data.Node;
import data.Relation;
import model.FindCandidate;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import utils.FileUtils;

import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author viett
 */
public class LegalExtractor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String path = "resources/civilcode-sample.txt";
        //String path = "resources/civilcode-1.0.txt";
        ArrayList<AMRGraph> graphs=FileUtils.readAMRFile(path);
        
        for(AMRGraph g: graphs){
            System.out.println("------------------------------------");
            g.build();
            //g.print();

            ArrayList<Node> candidates = FindCandidate.findCandidates(g);
            //g.print();
//            System.out.println("-> Candidate:");
//
            for(Node n: candidates){
                System.out.println(n.getConcept());
            }

            ArrayList<Relation> allRelations = g.extract(candidates);
            for(Relation r: allRelations){
                System.out.println(r);
            }


            ArrayList<GraphPath> arr  = g.extractVerbRelation(candidates);

            for(GraphPath p: arr){
                System.out.println(AMRGraph.convertPath2AMR(p));
            }
        }

    }

}
