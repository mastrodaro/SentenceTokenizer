package com.mastrodaro;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Sentence {

    private List<Integer> words;

    public Sentence() {
        words = new ArrayList<>();
    }

    public void addWord(Integer word) {
        words.add(word);
    }

    public Stream<Integer> getWords() {
        return words.stream();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sentence sentence = (Sentence) o;
        return Objects.equals(words, sentence.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(words);
    }
}
