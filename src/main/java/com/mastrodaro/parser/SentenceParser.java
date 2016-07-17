package com.mastrodaro.parser;

import com.mastrodaro.Dictionary;
import com.mastrodaro.OutputFormat;
import com.mastrodaro.exporters.ExporterProvider;
import com.mastrodaro.lang.SentenceRecognizer;
import com.mastrodaro.lang.WordSorter;

import javax.inject.Inject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SentenceParser {

    /*@Inject
    private Dictionary dictionary;*/

    @Inject
    private ExporterProvider exporterProvider;

    @Inject
    private SentenceRecognizer sentenceRecognizer;

    @Inject
    private WordsExtractor wordsExtractor;

    private List<short[]> sentences = new ArrayList<>(25);
    private int maxWordsInSentence = 0;
    private int parsedSentences = 0;    //to mozna potem wywalic


    public void parse(InputStream in, OutputStream out, OutputFormat format) {
        read(in, format);
        export(out, format);
    }

    private void read(InputStream in, OutputFormat format) {
        try(Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            int c;
            char letter;
            StringBuilder sb = new StringBuilder();

            while((c = reader.read()) != -1) {
                letter = (char) c;
                if(sentenceRecognizer.isSentence(letter, sb.toString())) {
                    processSentence(sb.toString(), format);
                    sb = new StringBuilder();
                    continue;
                }
                sb.append(letter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processSentence(String sentence, OutputFormat format) {
        List<String> words = wordsExtractor.setMode(format).extract(sentence);
        saveLongestWordsCount(words);
        words.sort(WordSorter.alphabeticalCapitalLsst);
        sentences.add(toIndexes(words));
        parsedSentences++;

        if(parsedSentences % 50_000 == 0 || (parsedSentences > 400_000 && parsedSentences % 10_000 == 0) ||
                (parsedSentences > 500_000 && parsedSentences % 1_000 == 0)) {
            System.err.println(parsedSentences);
        }
    }

    private short[] toIndexes(List<String> words) {
        short[] wordsIndexed = new short[words.size()];
        int i = 0;
        Iterator<String> iterator = words.iterator();
        while(iterator.hasNext()) {
            String word = iterator.next();
            wordsIndexed[i++] = Dictionary.INSRANCE.getWordIndex(word);
        }
        return wordsIndexed;
    }

    private void saveLongestWordsCount(List<String> words) {
        if(words.size() > maxWordsInSentence) {
            maxWordsInSentence = words.size();
        }
    }

    private void export(OutputStream out, OutputFormat format) {
        exporterProvider.getExporter(format).export(out, sentences, maxWordsInSentence);
    }

}
