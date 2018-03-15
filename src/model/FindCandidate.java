/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import data.AMRGraph;
import data.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author viett
 */
public class FindCandidate {

    HashSet<String> ignoreList = new HashSet<>();

    public static ArrayList<Node> findCandidates(AMRGraph g) {
        ArrayList<Node> candidates = new ArrayList<Node>();
        Iterator<Node> it = g.getAllVertices().iterator();
        String[] parts;
        while (it.hasNext()) {
            Node n = it.next();
            parts = n.getConcept().split("-");
            try {
                if (parts.length >= 2) {
                    int senseIdx = Integer.parseInt(parts[parts.length - 1]);
                } else {
                    candidates.add(n);
                }
            }catch (NumberFormatException ex){

            }
        }
        return candidates;
    }

}
