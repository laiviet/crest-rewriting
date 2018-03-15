package rewriting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Utils {

    public static Article readArticle(String path) {
        Path p = Paths.get(path);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(p);
            Article a = new Article();
            for (String line : lines) {
                if (line.startsWith("#")) {
                    if (line.startsWith("# ::snt")) {
                        a.setSnt(line.substring(7));
                    }
                } else {
                    a.addAmrLine(line);
                    //System.out.println(line);
                }
            }

            return a;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }
}
