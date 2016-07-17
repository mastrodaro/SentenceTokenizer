package com.mastrodaro.exporters;

import com.mastrodaro.parser.SentenceIterator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class CSVExporter implements Exporter {

    private static final String NEW_LINE_SEPARATOR = "\n";

    @Override
    public void export(OutputStream out, SentenceIterator sentences, int maxWordsInSentence) {
        try( Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
            CSVPrinter csvFilePrinter = new CSVPrinter(writer, csvFileFormat);

            List<String> header = new ArrayList<>();
            header.add(null);
            IntStream.range(1, maxWordsInSentence + 1).forEach(value -> header.add("Word " + value));
            csvFilePrinter.printRecord(header);

            int i = 0;
            while(sentences.hasNext()) {
                List<String> words = new ArrayList<>();
                words.add("Sentence " + (++i));
                words.addAll(sentences.next().collect(Collectors.toList()));
                csvFilePrinter.printRecord(words);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
