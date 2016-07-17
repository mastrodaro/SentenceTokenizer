package com.mastrodaro.parser;

import com.mastrodaro.lang.Dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SentenceIterator {

    private List<short[]> sentences;
    private Dictionary dictionary;
    private int i;

    SentenceIterator(List<short[]> sentences, Dictionary dictionary) {
        this.sentences = sentences;
        this.dictionary = dictionary;
        i = 0;
    }

    public boolean hasNext() {
        return i != sentences.size();
    }

    public Stream<String> next() {
        short[] wordIndexes = sentences.get(i++);

        List<String> words = new ArrayList<>(wordIndexes.length);
        for (short index : wordIndexes) {
            words.add(dictionary.getWord(index));
        }

        return words.stream();
    }

}
