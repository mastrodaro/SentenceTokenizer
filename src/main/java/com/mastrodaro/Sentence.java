package com.mastrodaro;

import java.util.Objects;

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
        return Objects.equals(words, sentence.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(words);
    }
}
