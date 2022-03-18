package com.journalapp.ellis.journalapp.Service;



import antlr.StringUtils;

import java.util.Arrays;
import java.util.List;

public class Utils {
    public static long getId(String s) {
        s = s.replaceAll("\\D+","");

        return Long.parseLong(s);
    }

    public static List<String> delimitTags(String toDelim) {

        List<String> tags = Arrays.asList(toDelim.split(", "));
        String lastTag = tags.get(tags.size()-1);

        if(lastTag.charAt(lastTag.length()-1) == ',') {
            lastTag = lastTag.substring(0, lastTag.length()-1);
            tags.set(tags.size()-1, lastTag);
        }

        return tags;
    }

    public static String removeTrailingComma(String s) {
        return s.charAt(s.length()-2) == ',' ? s.substring(0, s.length()-2) : s;
    }
}
