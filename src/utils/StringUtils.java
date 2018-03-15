package utils;

public class StringUtils {
    public static String preprocessAMRText(String text){
        text = text.replace("\n", "");
        text = text.replace("\t", "");
        text = text.replace("(", "( ");
        text = text.replace(")", " )");
        text = text.replace(")", " )");
        text = text.replace("  ", " ");
        text = text.replace("  ", " ");
        text = text.replace("  ", " ");
        text = text.replace("  ", " ");
        text = text.replace("  ", " ");
        text = text.replace("  ", " ");
        return text;
    }





}
