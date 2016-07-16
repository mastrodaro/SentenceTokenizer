package com.mastrodaro;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException, XMLStreamException {

        SentenceReader.readSentences(System.in, System.out);
    }
}
