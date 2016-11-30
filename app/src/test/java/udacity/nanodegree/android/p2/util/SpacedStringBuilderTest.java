package udacity.nanodegree.android.p2.util;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import udacity.nanodegree.android.p2.util.SpacedStringBuilder;

/**
 * Created by alexandre on 24/11/2016.
 */
public class SpacedStringBuilderTest {

    @Test
    public void toStringTest() throws Exception {
        SpacedStringBuilder spacedStringBuilder = new SpacedStringBuilder(" test");
        spacedStringBuilder.append(" test").append("test").append("   test    ");

        Assert.assertEquals("test test test test", spacedStringBuilder.toString());

    }


    @Test
    public void appendTest (){
        List<String> paramList = new ArrayList<>();
        paramList.add( "param1" );
        paramList.add( "param2" );

        final String comma = ", ";

        StringBuilder result = new StringBuilder();
        for (String s : paramList) {

            result.append(s).append(comma);
        }
        if (!paramList.isEmpty()){
            result.setLength(result.length() - comma.length());
        }

        System.out.println(result);

    }

}