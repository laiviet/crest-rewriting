package test;

import data.AMRGraph;
import utils.FileUtils;

import java.util.ArrayList;

public class TestAMRReader {

    public static void main(String[] args) {

        String path = "corpus/civilcode-1.0.txt";

        ArrayList<AMRGraph> arr =FileUtils.readAMRFile(path);
        System.out.println("Number of graphs: "+ arr.size());
        for(AMRGraph g: arr){
            g.build();
            System.out.println("------");
            g.print();
        }
    }
}
