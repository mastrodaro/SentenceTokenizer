package com.mastrodaro.lang;

import java.util.Comparator;

public class WordSorter {

    private WordSorter() {}

    public static final Comparator<String> alphabeticalCapitalLsst = (s1, s2) -> {
        int result = s1.compareToIgnoreCase(s2);
        if(result == 0) {
            result = s2.compareTo(s1);
        }
        return result;
    };
}
