package com.mastrodaro;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class SentenceReader {

    private static Dictionary dictionary = new Dictionary();
//    private static Sentence[] sentences = new Sentence[250_000];
//    private static Sentence[] sentences2 = new Sentence[250_000];
//    private static Sentence[] sentences3 = new Sentence[250_000];
    private static List<short[]> sentencesList = new ArrayList<>(25);
    private static short[][] currentSentences;
    private static int CAP = 32768;
//    private static List<Sentence> sentencesList = new LinkedList<>();
    private static int iGc = 0;

    public static void readSentencesWithMem(InputStream in, OutputStream out) throws IOException, XMLStreamException {
        Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);

        int c;
        char letter;
        StringBuilder sb = new StringBuilder();

        while((c = reader.read()) != -1) {
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
        }

        Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        factory.setProperty("escapeCharacters", false);

        XMLStreamWriter xmlWriter = factory.createXMLStreamWriter(writer);
        xmlWriter.writeStartDocument(StandardCharsets.UTF_8.name(), "1.0");
        xmlWriter.writeStartElement("text");


       /* for(int i = 0; i < sentences.length; i++) {
            uncheck(() -> xmlWriter.writeStartElement("sentence"));
            byte [] wws = sentences[i].getWords();
            for(int j = 0 ; j < wws.length; j++) {
                final int ii = j;
                uncheck(() -> {
                    xmlWriter.writeStartElement("word");
                    xmlWriter.writeCharacters(dictionary.getWord(wws[ii]));
                    xmlWriter.writeEndElement();
                });
            };
            uncheck(() -> xmlWriter.writeEndElement());
        };*/

        xmlWriter.writeEndElement();
        xmlWriter.writeEndDocument();
        xmlWriter.flush();
        xmlWriter.close();
    }

    private static void writeSentenceToMem(String s) {
        List<String> words = new ArrayList<>();
        List<Integer> wordsIndexes = new ArrayList<>();

        String[] wordsArr = s/*.replaceAll(",([^ ])", ", $1")*/.split("\\s+");

        for(int i = 0 ; i < wordsArr.length; i++) {
            if(!wordsArr[i].trim().isEmpty()) {
                String word = wordsArr[i].trim().replaceAll("[:!,\\?\\(\\)]", "");
                if(word.length() > 1 || word.matches("[a-zA-Z]"))
                    words.add(word);
            }
        }

//        Stream.of(s.replaceAll(",([^ ])", ", $1").split("\\s+")).forEach(s1 -> {
//            if(!s1.trim().isEmpty()) {
//                String word = s1.trim().replaceAll("[:!,\\?\\(\\)]", "");
//                if(word.length() > 1 || word.matches("[a-zA-Z]"))
//                    words.add(word);
//            }
//        });

        /*Collections.sort(words, (o1, o2) -> {
            int a = 1;
            int result = o1.compareToIgnoreCase(o2);
            if(result == 0) {
                result = o2.compareTo(o1);
            }
            return result;
        });*/

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
//        for(String s1 : words) {
//            int wordIndex;
//            if(!dictionary.containWord(s1)) {
//                wordIndex = dictionary.addWord(s1);
//            } else {
//                wordIndex = dictionary.getWordIndex(s1);
//            }
//            wordsIndexes.add(wordIndex);
//            wrds[a++] = (byte)wordIndex;
//        }
//        byte[] wrds = {1, 5, 65, -50, 120, 127};
//        words.forEach(s1 -> {
//
//            wrds[a++] =
//
//        });

        /*if(iGc < 250_000) {
            sentences[iGc++] = new Sentence(wrds);
        } else if(iGc < 500_000) {
            sentences2[iGc++ - 250_000] = new Sentence(wrds);
        } else {
            sentences3[iGc++ - 500_000] = new Sentence(wrds);
        }*/

        /*boolean add = false;
        if(iGc % CAP == 0) {
            currentSentences = new short[CAP][];
            add = true;
        }

        currentSentences[iGc % CAP] = wrds;
        if(add) {
            sentencesList.add(currentSentences);
        }*/

        sentencesList.add(wrds);
        iGc++;
       // sentencesList.add(new Sentence(wrds));
        if(iGc % 50_000 == 0 || (iGc > 400_000 && iGc % 10_000 == 0) ||
                (iGc > 500_000 && iGc % 1_000 == 0)) {
            System.err.println(iGc);
        }
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
