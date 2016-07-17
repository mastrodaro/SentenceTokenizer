package com.mastrodaro;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ApplicationTest {

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
        OutputFormat xml = OutputFormat.valueOf("XML");
        int a = 1;
        /*SentenceReader.readSentences(new FileInputStream(Paths.get("src/test/resources/small.in").toFile()),
                new FileOutputStream(Paths.get("src/test/resources/czy64.txt").toFile()));*/
        //assertThat(Input.fromString(xml), isIdenticalTo(Input.fromFile("src/test/resources/small.xml")));
    }

    @Test
    public void splitSentencesWithMem() throws IOException, XMLStreamException {
        SentenceReader.readSentencesWithMem(new FileInputStream(Paths.get("src/test/resources/small.in").toFile()),
                new FileOutputStream(Paths.get("src/test/resources/czy64.csv").toFile()));
    }

    @Test
    @Ignore
    public void weldTest() throws IOException, XMLStreamException {
        Injector injector = Guice.createInjector(new MyModule());
        Application main = injector.getInstance(Application.class);
        main.run(OutputFormat.XML);
    }

    @Test
    @Ignore
    public void testShorts() {
        byte b = (byte)255;
        int i = b & 0xff;
        System.out.println(i);
    }

    @Test
    @Ignore
    public void testOverheat() {

       /* Sentence[] ss = new Sentence[550_000];
        Sentence[] ss2 = new Sentence[550_000];
        Sentence[] ss3 = new Sentence[450_000];
        int iGc = 0;
        int a = 0;
        for(int i = 0; i < 550_000; i++) {
            byte[] b = {1, 5, 65, -50, 120, 127};

            ss[a++] = new Sentence(b);
            if(++iGc % 50_000 == 0 || (iGc > 400_000 && iGc % 10_000 == 0) ||
                    (iGc > 500_000 && iGc % 1_000 == 0)) {
                System.err.println(iGc);
            }
        }

        a = 0;
        for(int i = 0; i < 550_000; i++) {
            byte[] b = {1, 5, 65, -50, 120, 117};

            ss2[a++] = new Sentence(b);
            ++iGc;
            if(iGc % 50_000 == 0 || (iGc > 400_000 && iGc % 10_000 == 0) ||
                    (iGc > 500_000 && iGc % 1_000 == 0)) {
                System.err.println(iGc);
            }
        }

        a = 0;
        for(int i = 0; i < 450_000; i++) {
            byte[] b = {1, 5, 65, -50, 120, 126};

            ss3[a++] = new Sentence(b);
            ++iGc;
            if(iGc % 50_000 == 0 || (iGc > 400_000 && iGc % 10_000 == 0) ||
                    (iGc > 500_000 && iGc % 1_000 == 0)) {
                System.err.println(iGc);
            }
        }*/
    }
}