package com.mastrodaro.parser;

import com.mastrodaro.OutputFormat;

import java.util.ArrayList;
import java.util.List;

import static com.mastrodaro.OutputFormat.*;

public class WordsExtractor {

    private OutputFormat mode;

    public WordsExtractor setMode(OutputFormat mode) {
        this.mode = mode;
        return this;
    }

    public List<String> extract(String sentence) {
        List<String> words = new ArrayList<>();
        if(mode == XML) {
            words = extractForXML(sentence);
        } else if(mode == CSV) {
            words = extractForCSV(sentence);
        }
        return words;
    }

    private String[] split(String sentence) {
        return sentence.replaceAll(",([^ ])", ", $1").split("\\s+");
    }

    private List<String> extractForXML(String sentence) {
        List<String> words = new ArrayList<>();
        String[] wordsArr = split(sentence);

        for(int i = 0 ; i < wordsArr.length; i++) {
            if(!wordsArr[i].trim().isEmpty()) {
                String word = wordsArr[i].trim().replaceAll("[:!,\\?\\(\\)]", "").replaceAll("['`’]", "&apos;");
                if(word.length() > 1 || word.matches("[a-zA-Z]"))
                    words.add(word);
            }
        }
        return words;
    }

    private List<String> extractForCSV(String sentence) {
        List<String> words = new ArrayList<>();
        String[] wordsArr = split(sentence);

        for(int i = 0 ; i < wordsArr.length; i++) {
            if(!wordsArr[i].trim().isEmpty()) {
                String word = wordsArr[i].trim().replaceAll("[:!,\\?\\(\\)]", "").replaceAll("['`’]", "'");
                if(word.length() > 1 || word.matches("[a-zA-Z]"))
                    words.add(word);
            }
        }
        return words;
    }
}
