package com.mastrodaro;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mastrodaro.parser.OutputFormat;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class ApplicationTest {

    @Test
    public void splitSentences() throws IOException, XMLStreamException, SAXException, ParserConfigurationException {
        OutputFormat xml = OutputFormat.valueOf("XML");
        int a = 1;
        /*SentenceReader.readSentences(new FileInputStream(Paths.get("src/test/resources/small.in").toFile()),
                new FileOutputStream(Paths.get("src/test/resources/czy64.txt").toFile()));*/
        //assertThat(Input.fromString(xml), isIdenticalTo(Input.fromFile("src/test/resources/small.xml")));
    }


    @Test
    @Ignore
    public void weldTest() throws IOException, XMLStreamException {
        Injector injector = Guice.createInjector(new MyModule());
        Application main = injector.getInstance(Application.class);
        main.run(OutputFormat.XML);
    }
}