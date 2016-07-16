package com.mastrodaro;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

public class SentenceReader {

    private static Dictionary dictionary = new Dictionary();

    public static void readSentencesWithMem(InputStream in, OutputStream out) throws IOException, XMLStreamException {
        Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
        int c;
        char letter;
        StringBuilder sb = new StringBuilder();

        List<Sentence> sentences = new ArrayList<>();

        while((c = reader.read()) != -1) {
            letter = (char) c;
            if (letter == '!' || letter == '?') {
                writeSentenceToMem(sentences, sb.toString());
                sb.setLength(0);
                continue;
            }

            if (letter == '.' && !sb.toString().endsWith("Mr")) {
                writeSentenceToMem(sentences, sb.toString());
                sb.setLength(0);
                continue;
            }


            sb.append(letter);
        }

        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        factory.setProperty("escapeCharacters", false);

        XMLStreamWriter xmlWriter = factory.createXMLStreamWriter(writer);
        xmlWriter.writeStartDocument(StandardCharsets.UTF_8.name(), "1.0");
        xmlWriter.writeStartElement("text");

        sentences.forEach(sentence -> {
            uncheck(() -> xmlWriter.writeStartElement("sentence"));
            sentence.getWords().forEach(index -> {
                uncheck(() -> {
                    xmlWriter.writeStartElement("word");
                    xmlWriter.writeCharacters(dictionary.getWord(index));
                    xmlWriter.writeEndElement();
                });
            });
            uncheck(() -> xmlWriter.writeEndElement());
        });

        xmlWriter.writeEndElement();
        xmlWriter.writeEndDocument();
        xmlWriter.flush();
        xmlWriter.close();
    }

    private static void writeSentenceToMem(List<Sentence> sentences, String s) {
        Sentence sentence = new Sentence();

        List<String> words = new ArrayList<>();

        Stream.of(s.replaceAll(",([^ ])", ", $1").split("\\s+")).forEach(s1 -> {
            if(!s1.trim().isEmpty()) {
                String word = s1.trim().replaceAll("[:!,\\?\\(\\)]", "");
                if(word.length() > 1 || word.matches("[a-zA-Z]"))
                    words.add(word);
            }
        });

        Collections.sort(words, (o1, o2) -> {
            int a = 1;
            int result = o1.compareToIgnoreCase(o2);
            if(result == 0) {
                result = o2.compareTo(o1);
            }
            return result;
        });

        words.forEach(s1 -> {
            Integer wordIndex;
            if(!dictionary.containWord(s1)) {
                wordIndex = dictionary.addWord(s1);
            } else {
                wordIndex = dictionary.getWordIndex(s1);
            }
            sentence.addWord(wordIndex);
        });

        sentences.add(sentence);


    }


    public static void readSentences(InputStream in, OutputStream out) throws IOException, XMLStreamException {

        Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
        int c;
        char letter;

        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        factory.setProperty("escapeCharacters", false);

        XMLStreamWriter xmlWriter = factory.createXMLStreamWriter(writer);
        xmlWriter.writeStartDocument(StandardCharsets.UTF_8.name(), "1.0");
        xmlWriter.writeStartElement("text");

        StringBuilder sb = new StringBuilder();
        while((c = reader.read()) != -1) {
            letter = (char) c;
            if (letter == '!' || letter == '?') {
                writeSentenceToXmlStream(xmlWriter, sb.toString());
                sb.setLength(0);
                continue;
            }

            if (letter == '.' && !sb.toString().endsWith("Mr")) {
                writeSentenceToXmlStream(xmlWriter, sb.toString());
                sb.setLength(0);
                continue;
            }


            sb.append(letter);
        }

        xmlWriter.writeEndElement();
        xmlWriter.writeEndDocument();
        xmlWriter.flush();
        xmlWriter.close();
    }

    private static void writeSentenceToXmlStream(XMLStreamWriter xmlWriter, String s) {
        uncheck(() -> xmlWriter.writeStartElement("sentence"));
        List<String> words = new ArrayList<>();
        Stream.of(s.replaceAll(",([^ ])", ", $1").split("\\s+")).forEach(s1 -> {
            if(!s1.trim().isEmpty()) {
                String word = s1.trim().replaceAll("[:!,\\?\\(\\)]", "").replaceAll("[’']", "&apos;");
                if(word.length() > 1 || word.matches("[a-zA-Z]"))
                    words.add(word);
            }
        });

        Collections.sort(words, (o1, o2) -> {
            int result = o1.compareToIgnoreCase(o2);
            if(result == 0) {
                result = o2.compareTo(o1); //zeby Market bylo przed market
            }
            return result;
        });
        words.forEach(s1 -> {
            uncheck(() -> {
                xmlWriter.writeStartElement("word");
                xmlWriter.writeCharacters(s1);
                xmlWriter.writeEndElement();
            });
        });

        uncheck(() -> xmlWriter.writeEndElement());
    }

    private static void uncheck(RunnableXmlWrite runner) {
            try {
                runner.run();
            } catch(XMLStreamException ignored) {
                int a = 1;
            }
    }
}
