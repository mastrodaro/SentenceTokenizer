package com.mastrodaro.lang;

public class SentenceRecognizer {

    public boolean isSentence(char letter, String sentence) {
        boolean result = false;
        if(letter == '!' || letter == '?') {
            result = true;
        } else if(letter == '.' && languageExceptions(sentence)) {
            result = true;
        }
        return result;
    }

    private boolean languageExceptions(String sentence) {
        return !sentence.endsWith("Mr")
                && !sentence.endsWith("Mrs")
                && !sentence.endsWith("Ms");
    }
}
