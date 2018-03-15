package utils;

import data.AMRGraph;

import java.io.*;
import java.util.ArrayList;

public class FileUtils {
    
    public static ArrayList<AMRGraph> readAMRFile(String path) {
        ArrayList<AMRGraph> amrGraphs = new ArrayList<AMRGraph>();
        try {
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream(path);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            AMRGraph g = null;

            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                //System.out.println(strLine);
                if(strLine.length()>3){
                    if (g==null){
                        g=new AMRGraph();
                    }
                    if(strLine.startsWith("#")) {
                        g.addComment(strLine);
                    }else{
                        g.appendAMRText(strLine);
                    }
                }else{
                    if(g!=null){
                        amrGraphs.add(g);
                        g = null;
                    }
                }

            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        return amrGraphs;
    }

    public static void saveArrayList(ArrayList<String> arr, String path) {
        try {
            FileWriter writer = new FileWriter(path);
            for (String line : arr) {
                writer.write(line + '\n');
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static ArrayList<String> readLines(String path) {
        ArrayList<String> arr = new ArrayList<String>();
        try {
            String tmp = "";
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            while ((tmp = reader.readLine()) != null) {
                arr.add(tmp);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return arr;
    }
}
