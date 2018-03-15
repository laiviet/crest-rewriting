package data;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.DirectedAcyclicGraph;
import utils.MyLogger;
import utils.StringUtils;

import java.util.*;
import java.util.logging.Logger;

public class AMRGraph extends DirectedAcyclicGraph<Node, LabeledEdge> {

    String id;
    String snt;
    Hashtable<String, Node> var2node = new Hashtable<String, Node>();

    Node root = null;

    Logger logger = MyLogger.getInstance();
    String amrText = "";
    ArrayList<String> comments = new ArrayList<String>();


    boolean isLog = false;

    public AMRGraph(String id, String snt) {
        super(LabeledEdge.class);
        this.id = id;
        this.snt = snt;
    }

    public AMRGraph() {
        super(LabeledEdge.class);
    }

    public String getSnt() {
        return snt;
    }

    private String generateVariable(String concept, Hashtable<String, String> con2var) {
        String sub = concept.substring(0, 1);
        String var = sub;
        int idx = 0;
        while (con2var.containsValue(var)) {
            var = sub + idx;
        }
        con2var.put(concept, var);
        return var;
    }

    public int build() {
        return buildFromText(amrText);
    }

    public int buildFromText(String text) {
        /*
         * Preprocess text:
         *    Remove tab, endline
         *    Separate bracket
         *    Remove redundant space
         * */

        //System.out.println(text);

        amrText = StringUtils.preprocessAMRText(text);
        String[] parts = amrText.split(" ");

        // Stack of node
        Stack<Node> nodeStack = new Stack<Node>();
        // Stack of variable and concept
        Stack<String> eStack = new Stack<String>();

        String edgeLabel = "";
        boolean isHead = true;
        boolean createNode = true;

        String newVar = "";
        String newConcept = "";
        String prev = "";

        Node head, tail;
        LabeledEdge edge;

//        for (String e : parts) {
//            System.out.print("\"" + e + "\" ");
//        }
        for (String e : parts) {
            if (e.length() == 0 || e.equals(" ")) {
                continue;
            }
            if ("/".equals(e)) {
                newVar = prev;
                prev = "";
                log("-> New variable: " + newVar);
            } else if ("(".equals(e)) {
                createNode = true;
                log("createNode=True");
            } else if (")".equals(e)) {
                nodeStack.pop();
                log("! pop node stack");

            } else if (e.startsWith(":")) {
                createNode = false;
                edgeLabel = e;
                isHead = false;
                log("createNode=False");
            } else {
                prev = e;
                if (eStack.isEmpty()) {
                    if (createNode) {
                        eStack.push(e);
                        //log("! New variable");
                    } else {
                        if (var2node.containsKey(e)) {
                            tail = var2node.get(e);
                        } else {
                            Node constantNode = new Node(e, e);
                            var2node.put(e, constantNode);
                            tail = constantNode;
                        }
                        head = nodeStack.pop();
                        if (!containsVertex(tail)) {
                            addVertex(tail);
                        }

                        edge = new LabeledEdge(head, tail, edgeLabel);

                        addEdge(head, tail, edge);
                        log("-> Search for node: " + tail.toString());
                        log(">>> New edge: " + head.toString() + edgeLabel + tail.toString());
                        nodeStack.push(head);
                    }
                } else {
                    newConcept = e;
                    newVar = eStack.pop();
                    if (isHead) {
                        head = new Node(newConcept, newVar);
                        addVertex(head);
                        log("-> create new head: " + head.toString());
                        nodeStack.push(head);
                        isHead = false;
                    } else {
                        head = nodeStack.pop();
                        tail = new Node(newConcept, newVar);
                        var2node.put(newVar, tail);
                        addVertex(tail);
                        log("-> create new tail: " + tail.toString());
                        edge = new LabeledEdge(head, tail, edgeLabel);
                        addEdge(head, tail, edge);
                        log(">>> New edge: " + head.toString() + edgeLabel + tail.toString());
                        nodeStack.push(head);
                        nodeStack.push(tail);
                    }
                }
            }
        }
        if (!eStack.isEmpty()) {
            System.out.println("Malformed structure eStack not empty");
            return -2;
        }
        if (!nodeStack.isEmpty()) {
            System.out.println("Malformed structure nodeStack not empty");
            return -3;
        }
        return 0;

    }

    public void addComment(String comment) {
        if (comment.startsWith("# ::id")) {
            this.id = comment.substring(7);
        } else if (comment.startsWith("# ::snt")) {
            this.snt = comment.substring(8);
        }
        comments.add(comment);
    }

    public void appendAMRText(String line) {
        //System.out.println(line);
        amrText += "\n" + line;
    }

    // Assume that the text are in well-formated.
    public String removeVariable(String text) {
        text = StringUtils.preprocessAMRText(text);

        return "";
    }

    public static String linearize(String text) {
        return "";
    }

    public String linearize() {
        return AMRGraph.linearize(amrText);
    }

    public static AMRGraph delinearize(String linearized_text) {
        return null;
    }

    public HashSet<Node> getAllVertices() {
        HashSet<Node> allVertices = new HashSet<Node>();
        for (String var : this.var2node.keySet()) {
            allVertices.add(this.var2node.get(var));
        }
        return allVertices;
    }

    public void print() {
        System.out.println(this.id);
        System.out.println(this.snt);
        for (String var : this.var2node.keySet()) {
            Node n = this.var2node.get(var);
            System.out.println(n.toString());
        }

        //System.out.println("-");

    }

    public ArrayList<Relation> extract(ArrayList<Node> candidates) {
        ArrayList<Relation> result = extractMod(candidates);
        result.addAll(extractLocation(candidates));
        return result;

    }


    public ArrayList<Relation> extractMod(ArrayList<Node> candidates) {
        // Find edges with :mod
        ArrayList<LabeledEdge> modEdges = new ArrayList<>();
        for (Node n : candidates) {
            Iterator<LabeledEdge> it = edgesOf(n).iterator();
            while (it.hasNext()) {
                LabeledEdge edge = it.next();
                if (":mod".compareTo(edge.label) == 0) {
                    modEdges.add(edge);
                }
            }
        }

        // Convert into relation structure
        ArrayList<Relation> rels = new ArrayList<Relation>();
        for (LabeledEdge e : modEdges) {
            rels.add(new Relation(e.head.concept, e.tail.concept, "be"));
        }
        return rels;
    }


    public ArrayList<Relation> extractLocation(ArrayList<Node> candidates) {
        // Find edges with :mod
        ArrayList<LabeledEdge> modEdges = new ArrayList<>();
        for (Node n : candidates) {
            Iterator<LabeledEdge> it = edgesOf(n).iterator();
            while (it.hasNext()) {
                LabeledEdge edge = it.next();
                if (":location".compareTo(edge.label) == 0) {
                    modEdges.add(edge);
                }
            }
        }
        // Convert into relation structure
        ArrayList<Relation> rels = new ArrayList<Relation>();
        for (LabeledEdge e : modEdges) {
            rels.add(new Relation(e.head.concept, e.tail.concept, "locate"));
        }
        return rels;
    }


    class NodePair {
        Node src;
        Node tgt;

        public NodePair(Node src, Node tgt) {
            this.src = src;
            this.tgt = tgt;
        }
    }


    public ArrayList<GraphPath> extractVerbRelation(ArrayList<Node> candidates) {
        Node[] candidateArray = new Node[candidates.size()];
        candidateArray = candidates.toArray(candidateArray);
        ArrayList<NodePair> pairs = new ArrayList<>();
        for (int i = 0; i < candidateArray.length; i++) {
            for (int j = i + 1; j < candidateArray.length; j++) {
                pairs.add(new NodePair(candidateArray[i], candidateArray[j]));
            }
        }
        ArrayList<GraphPath> allPaths = new ArrayList<>();
        ArrayList<HashSet<String>> allNodesSet = new ArrayList<>();
//        AllDirectedPaths pathFinder = new AllDirectedPaths(this);
//        for(NodePair p:pairs){
//            ArrayList<GraphPath> paths = (ArrayList<GraphPath>)
//                    pathFinder.getAllPaths(p.src, p.tgt,false, 3);
//            allPaths.addAll(paths);
//
//        }

        AsUndirectedGraph uGraph = new AsUndirectedGraph(this);
        DijkstraShortestPath algor = new DijkstraShortestPath(uGraph);

        for (NodePair p : pairs) {
            GraphPath path = algor.getPath(p.src, p.tgt);
            if (path.getLength() < 4) {
                allPaths.add(path);
                allNodesSet.add(convertPath2Set(path));
            }
        }

        // Remove redundant path

        return allPaths;
    }


    public static HashSet<String> convertPath2Set(GraphPath g){
        HashSet<String> nodes = new HashSet<>();
        for (Object o : g.getEdgeList()){
            LabeledEdge e = (LabeledEdge) o;
            nodes.add(e.head.variable);
            nodes.add(e.tail.variable);
        }
        return nodes;
    }

    public static String convertPath2AMR(GraphPath g){
        Node start = (Node) g.getStartVertex();
        Object end = (Node) g.getEndVertex();

        //System.out.println(start + " -> " + end);

        String result = start.concept+ " ";

        List<LabeledEdge> edgeList = g.getEdgeList();

        Node current = start;
        while(!edgeList.isEmpty()){
            for (LabeledEdge e : edgeList){
                if(e.head == current){
                    result += e.label + " "+ e.tail.concept + " ";
                    current = e.tail;
                    edgeList.remove(e);
                    break;
                }else if (e.tail == current){
                    result += e.label + " "+ e.head.concept+ " ";
                    current = e.head;
                    edgeList.remove(e);
                    break;
                }
            }
        }

        return result;

    }



    public void log(String msg) {
        if (isLog) {
            System.out.println(msg);
        }
    }

}
