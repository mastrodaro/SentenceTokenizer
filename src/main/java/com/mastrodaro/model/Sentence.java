package com.mastrodaro.model;

import java.util.Arrays;

public class Sentence {

    private byte[] words;

    public Sentence(byte[] words) {
        this.words = words;
    }

    public byte[] getWords() {
        return words;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sentence sentence = (Sentence) o;
        return Arrays.equals(words, sentence.words);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(words);
    }
}
