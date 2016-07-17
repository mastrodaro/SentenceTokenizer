package com.mastrodaro;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SentenceReader {

  // private static Dictionary dictionary = new Dictionary();
    private static List<short[]> sentencesList = new ArrayList<>(25);
    private static int longestWords = 0;
    private static int iGc = 0;

    public static void readSentencesWithMem(InputStream in, OutputStream out) throws IOException, XMLStreamException {
        Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);

        int c;
        char letter;
        StringBuilder sb = new StringBuilder();

       /* while((c = reader.read()) != -1) {
            letter = (char) c;
            if (letter == '!' || letter == '?') {
                writeSentenceToMem(sb.toString());
                sb = new StringBuilder();
                continue;
            }

            if (letter == '.' && !sb.toString().endsWith("Mr")) {
                writeSentenceToMem(sb.toString());
                sb = new StringBuilder();
                continue;
            }


            sb.append(letter);
        }*/

        Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
       /* XMLOutputFactory factory = XMLOutputFactory.newInstance();
        factory.setProperty("escapeCharacters", false);

        XMLStreamWriter xmlWriter = factory.createXMLStreamWriter(writer);
        //xmlWriter.writeStartDocument(StandardCharsets.UTF_8.name(), "1.0");
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<text>\n");
        //xmlWriter.writeStartElement("text");


        sentencesList.forEach(words -> {
            uncheck(() -> xmlWriter.writeStartElement("sentence"));
            for(int j = 0 ; j < words.length; j++) {
                final int ii = j;
                uncheck(() -> {
                    xmlWriter.writeStartElement("word");
                    xmlWriter.writeCharacters(dictionary.getWord(words[ii]));
                    xmlWriter.writeEndElement();
                });
            };
            uncheck(() -> xmlWriter.writeEndElement());
            try {
                writer.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        writer.write("</text>");
        //xmlWriter.writeEndElement();
        //xmlWriter.writeEndDocument();
        xmlWriter.flush();
        xmlWriter.close();*/


       /* CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
        CSVPrinter csvFilePrinter = new CSVPrinter(writer, csvFileFormat);

        List<String> header = new ArrayList<>();
        header.add(null);
        IntStream.range(1, longestWords + 1).forEach(value -> {
            header.add("Word " + value);
        });

        csvFilePrinter.printRecord(header);
        for (int i = 0; i < sentencesList.size(); i++) {
            short[] words = sentencesList.get(i);
            List<String> wrds = new ArrayList<>();
            wrds.add("Sentence " + (i + 1));
            for(int j = 0 ; j < words.length; j++) {
                wrds.add(dictionary.getWord(words[j]));
            }
            try {
                csvFilePrinter.printRecord(wrds);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer.flush();*/

    }

  /*  private static void writeSentenceToMem(String s) {
        List<String> words = new ArrayList<>();
        List<Integer> wordsIndexes = new ArrayList<>();

        String[] wordsArr = s.replaceAll(",([^ ])", ", $1").split("\\s+");

        for(int i = 0 ; i < wordsArr.length; i++) {
            if(!wordsArr[i].trim().isEmpty()) {
                String word = wordsArr[i].trim().replaceAll("[:!,\\?\\(\\)]", "").replaceAll("['`’]", "&apos;");
                if(word.length() > 1 || word.matches("[a-zA-Z]"))
                    words.add(word);
            }
        }

        if(words.size() > longestWords) {
            longestWords = words.size();
        }

        Collections.sort(words, (o1, o2) -> {
            int result = o1.compareToIgnoreCase(o2);
            if(result == 0) {
                result = o2.compareTo(o1);
            }
            return result;
        });

        short[] wrds = new short[words.size()];
        int a = 0;

        Iterator<String> iterator = words.iterator();
        while(iterator.hasNext()) {
            String s1 = iterator.next();
            int wordIndex;
            if(!dictionary.containWord(s1)) {
                wordIndex = dictionary.addWord(s1);
            } else {
                wordIndex = dictionary.getWordIndex(s1);
            }
            wordsIndexes.add(wordIndex);
            wrds[a++] = (short)wordIndex;
        }

        sentencesList.add(wrds);
        iGc++;
        if(iGc % 50_000 == 0 || (iGc > 400_000 && iGc % 10_000 == 0) ||
                (iGc > 500_000 && iGc % 1_000 == 0)) {
            System.err.println(iGc);
        }
    }*/

    private static void uncheck(RunnableXmlWrite runner) {
            try {
                runner.run();
            } catch(XMLStreamException ignored) {
                int a = 1;
            }
    }
}
