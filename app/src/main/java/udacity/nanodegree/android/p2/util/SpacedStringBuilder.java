package udacity.nanodegree.android.p2.util;

/**
 * Created by alexandre on 23/11/2016.
 */

public class SpacedStringBuilder {
    private StringBuilder sb = new StringBuilder();

    public SpacedStringBuilder(String s) {
        sb.append(" ").append(s).append(" ");
    }

    public SpacedStringBuilder() {

    }

    public static SpacedStringBuilder create() {
        return new SpacedStringBuilder();
    }

    public SpacedStringBuilder append(String s) {
        sb.append(" ").append(s).append(" ");
        return this;
    }

    public SpacedStringBuilder deleteAt (int index){
        sb.deleteCharAt(index);
        return this;
    }
    public SpacedStringBuilder replace(int start, int end, String str) {
        sb.replace(start, end, str);
        return this;
    }

    public int lastIndexOf(String str) {
        return sb.lastIndexOf(str);
    }



    @Override
    public String toString() {
        return sb.toString().replaceAll("\\s+", " ").trim();
    }
}
