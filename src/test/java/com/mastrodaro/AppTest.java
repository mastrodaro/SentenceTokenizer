package com.mastrodaro;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.xmlunit.matchers.CompareMatcher.isIdenticalTo;

public class AppTest {

    //http://nlp.stanford.edu/software/lex-parser.shtml

    @Test
    public void inputTest() throws IOException {
//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//        in.lines().forEach(System.out::print);


        List<Integer> sentences = new ArrayList<>();
        Files.lines(Paths.get("src/test/resources/large.in")).forEach(s -> {
            if(s.endsWith(".")) {
                //System.out.println("line eneded");

            } else {
                //System.out.println("not");

            }
            sentences.add(s.hashCode());
        });

        assertTrue(true);
    }


    @Test
    public void splitSentences() throws IOException, XMLStreamException, SAXException, ParserConfigurationException {
        SentenceReader.readSentences(new FileInputStream(Paths.get("src/test/resources/small.in").toFile()),
                new FileOutputStream(Paths.get("src/test/resources/czy64.txt").toFile()));
        //assertThat(Input.fromString(xml), isIdenticalTo(Input.fromFile("src/test/resources/small.xml")));
    }

    @Test
    public void splitSentencesWithMem() throws IOException, XMLStreamException {
        SentenceReader.readSentencesWithMem(new FileInputStream(Paths.get("src/test/resources/large.in").toFile()),
                new FileOutputStream(Paths.get("src/test/resources/czy64.txt").toFile()));
    }
}