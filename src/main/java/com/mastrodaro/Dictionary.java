package com.mastrodaro;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {

    private Map<Integer, String> dictionary;
    private Map<String, Integer> reverseDictionary;
    private Integer index;

    public Dictionary() {
        dictionary = new HashMap<>();
        reverseDictionary = new HashMap<>();
        index = 0;
    }

    public boolean containWord(String word) {
        return dictionary.containsValue(word);
    }

    public Integer addWord(String word) {
        dictionary.put(++index, word);
        reverseDictionary.put(word, index);
        return index;
    }

    public String getWord(Integer index) {
        return dictionary.get(index);
    }

    public Integer getWordIndex(String word) {
        return reverseDictionary.get(word);
    }
}
