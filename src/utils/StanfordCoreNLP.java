/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author viett
 */
public class StanfordCoreNLP {

    static StanfordCoreNLP instance = new StanfordCoreNLP();
    static MaxentTagger tagger;

    private StanfordCoreNLP() {
        String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";
        tagger = new MaxentTagger(taggerPath);

    }

    public static StanfordCoreNLP getInstance() {
        return instance;
    }

    public static List<TaggedWord> parse(String str) {
        DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(str));
        for (List<HasWord> sentence : tokenizer) {
            List<TaggedWord> tagged = tagger.tagSentence(sentence);
            return tagged;
        }
        return new ArrayList<TaggedWord>();
    }
}
