package com.mastrodaro.exporters;

import java.io.OutputStream;
import java.util.List;

public interface Exporter {

    void export(OutputStream out, List<short[]> sentences, int maxWordsInSentence);
}
