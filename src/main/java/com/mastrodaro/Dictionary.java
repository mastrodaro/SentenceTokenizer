package com.mastrodaro;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {

    private Map<Short, String> dictionary;
    private Map<String, Short> reverseDictionary;
    private Short index;

    public Dictionary() {
        dictionary = new HashMap<>();
        reverseDictionary = new HashMap<>();
        index = 0;
    }

    public boolean containWord(String word) {
        return dictionary.containsValue(word);
    }

    public Short addWord(String word) {
        String w = word.intern();
        dictionary.put(++index, w);
        reverseDictionary.put(w, index);
        return index;
    }

    public String getWord(short index) {
        return dictionary.get(index);
    }

    public int getWordIndex(String word) {
        //return dictionary.entrySet().stream().filter(e -> e.getValue().equals(word)).map(e -> e.getKey()).findFirst().orElse((short)0);
        return reverseDictionary.get(word);
    }
}
