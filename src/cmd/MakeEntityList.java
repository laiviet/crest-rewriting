package cmd;

import data.AMRGraph;
import data.AMRResources;
import edu.stanford.nlp.ling.TaggedWord;
import utils.FileUtils;
import utils.StanfordCoreNLP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MakeEntityList {

    public static void main(String[] args) {
        StanfordCoreNLP parser = StanfordCoreNLP.getInstance();
        ArrayList<AMRGraph> graphArr = FileUtils.readAMRFile(AMRResources.CIVILCODE_FILE);
        ArrayList<String> responseArr = new ArrayList<>();
        String result;
        
        HashSet<String> nounDict=new HashSet<>();
        HashSet<String> tagDict=new HashSet<>();
        
        for (AMRGraph g : graphArr) {
            List<TaggedWord> words = StanfordCoreNLP.parse(g.getSnt());
            for(TaggedWord w: words){
                tagDict.add(w.tag());
                if(w.tag().compareTo("NN")==0){
                    nounDict.add(w.word());
                }
            }
        }
        
        for(String tag: tagDict){
            System.out.print(tag+" ");
            
        }
        
//        System.out.println("");
//        
//        for(String w: nounDict){
//            System.out.print(w+" ");
//            
//        }
//        
//        
    }

}
