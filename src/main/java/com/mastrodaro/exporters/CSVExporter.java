package com.mastrodaro.exporters;

import com.mastrodaro.Dictionary;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import javax.inject.Inject;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CSVExporter implements Exporter {

    private static final String NEW_LINE_SEPARATOR = "\n";

    @Inject
    private Dictionary dictionary;

    @Override
    public void export(OutputStream out, List<short[]> sentences, int maxWordsInSentence) {
        try( Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
            CSVPrinter csvFilePrinter = new CSVPrinter(writer, csvFileFormat);

            List<String> header = new ArrayList<>();
            header.add(null);
            IntStream.range(1, maxWordsInSentence + 1).forEach(value -> {
                header.add("Word " + value);
            });

            csvFilePrinter.printRecord(header);
            for (int i = 0; i < sentences.size(); i++) {
                short[] wordIndexes = sentences.get(i);
                List<String> words = new ArrayList<>();
                words.add("Sentence " + (i + 1));
                for(int j = 0 ; j < wordIndexes.length; j++) {
                    words.add(Dictionary.INSRANCE.getWord(wordIndexes[j]));
                }
                try {
                    csvFilePrinter.printRecord(words);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
