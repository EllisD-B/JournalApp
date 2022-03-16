package com.journalapp.ellis.journalapp.Service;

public class Utils {
    public static long getId(String s) {
        s = s.replaceAll("\\D+","");

        return Long.parseLong(s);
    }
}
