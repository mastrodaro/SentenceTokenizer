package com.mastrodaro.lang;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public enum Dictionary {
    INSRANCE;

    private Map<Short, String> dictionary;
    private Map<String, Short> reverseDictionary;
    private short index;
    //pomyslec nad usunieciem reversedictionary i zastapienie go para

    Dictionary() {
        init();
    }
    @Inject
    public void init() {
        dictionary = new HashMap<>();
        reverseDictionary = new HashMap<>();
        index = 0;
    }

    public short addWord(String word) {
        String w = word.intern();
        dictionary.put(++index, w);
        reverseDictionary.put(w, index);
        return index;
    }

    public String getWord(short index) {
        return dictionary.get(index);
    }

    public short getWordIndex(String word) {
        short wordIndex;
        if(!dictionary.containsValue(word)) {
            wordIndex = addWord(word);
        } else {
            wordIndex = reverseDictionary.get(word);
        }
        return wordIndex;
    }
}
