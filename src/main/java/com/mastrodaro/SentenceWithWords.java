package com.mastrodaro;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class SentenceWithWords {

    private String[] words;

    public SentenceWithWords(String[] words) {
        this.words = words;
    }

    public String[] getWords() {
        return words;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SentenceWithWords sentence = (SentenceWithWords) o;
        return Objects.equals(words, sentence.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(words);
    }
}
