package rewriting;

import com.oracle.javafx.jmx.json.JSONWriter;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringJoiner;

public class Article {
    String id;
    String snt;
    ArrayList<String> amr;
    ArrayList<String> rewrites;

    public Article() {
        amr = new ArrayList<>();
        rewrites = new ArrayList<>();
    }

    public ArrayList<String> getRewrites() {
        return rewrites;
    }


    public void setId(String id) {
        this.id = id;
    }


    public void setSnt(String snt) {
        this.snt = snt;
    }

    public String getSnt() {
        return snt;
    }

    public void addAmrLine(String line) {
        this.amr.add(line);
    }

    public void addRewriteLine(String line) {
        this.rewrites.add(line);
    }

    public void setRewrites(ArrayList<String> rewrites) {
        this.rewrites = rewrites;
    }

    public String toJson() {

        String amr = String.join("<line-break>", this.amr);
        String rewrite = String.join("<line-break>", rewrites);
        String output = String.format("{\"content\": \"%s\",\"amr\": \"%s\", \"result\": \"%s\"}", snt, amr, rewrite);
        return output;
    }


    public static String emptyJsonObject() {
        String output = "{\"content\": \"\", \"result\": \"\"}";
        return output;
    }
}
