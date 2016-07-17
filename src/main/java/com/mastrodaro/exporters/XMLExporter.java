package com.mastrodaro.exporters;

import com.mastrodaro.Dictionary;
import com.mastrodaro.RunnableXmlWrite;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class XMLExporter implements Exporter {

    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String TAG_TEXT = "text";
    private static final String TAG_SENTENCE = "sentence";
    private static final String TAG_WORD = "word";

    @Override
    public void export(OutputStream out, List<short[]> sentences, int maxWordsInSentence) {
        try( Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            factory.setProperty("escapeCharacters", false);

            XMLStreamWriter xmlWriter = factory.createXMLStreamWriter(writer);
            //xmlWriter.writeStartDocument(StandardCharsets.UTF_8.name(), "1.0");
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + NEW_LINE_SEPARATOR +
                    "<" + TAG_TEXT + ">" + NEW_LINE_SEPARATOR);
            //xmlWriter.writeStartElement(TAG_TEXT);

            sentences.forEach(words -> {
                uncheck(() -> xmlWriter.writeStartElement(TAG_SENTENCE));
                for(int i = 0 ; i < words.length; i++) {
                    final int ii = i;
                    uncheck(() -> {
                        xmlWriter.writeStartElement(TAG_WORD);
                        xmlWriter.writeCharacters(Dictionary.INSRANCE.getWord(words[ii]));
                        xmlWriter.writeEndElement();
                    });
                }
                uncheck(() -> xmlWriter.writeEndElement());
                try {
                    writer.write(NEW_LINE_SEPARATOR);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.write("</text>");
            //xmlWriter.writeEndElement();
            //xmlWriter.writeEndDocument();
            xmlWriter.flush();
            xmlWriter.close();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private static void uncheck(RunnableXmlWrite runner) {
        try {
            runner.run();
        } catch(XMLStreamException ex) {
            throw new RuntimeException(ex);
        }
    }
}
