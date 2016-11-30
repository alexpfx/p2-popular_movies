package udacity.nanodegree.android.p2.data;

/**
 * Created by alexandre on 23/11/2016.
 */

public class SpacedStringBuilder {
    private StringBuilder sb = new StringBuilder();

    public SpacedStringBuilder(String s) {
        sb.append(" ").append(s).append(" ");
    }

    public SpacedStringBuilder append (String s){
        sb.append(" ").append(s).append(" ");
        return this;
    }

    @Override
    public String toString() {
        return sb.toString().replaceAll("\\s+", " ").trim();
    }
}
