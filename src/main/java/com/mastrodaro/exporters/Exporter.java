package com.mastrodaro.exporters;

import com.mastrodaro.parser.SentenceIterator;

import java.io.OutputStream;

public interface Exporter {

    void export(OutputStream out, SentenceIterator sentences, int maxWordsInSentence);
}
