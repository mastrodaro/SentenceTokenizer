package com.mastrodaro;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mastrodaro.exporters.ExporterProvider;
import com.mastrodaro.parser.SentenceParser;

import javax.inject.Inject;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class Application {

    @Inject
    private ExporterProvider provider;

    @Inject
    private SentenceParser parser;

    public static void main(String[] args) throws IOException, XMLStreamException {
        if(args.length == 0) {
            System.err.println("Please provide the desired output format (xml|csv)");
            System.exit(0);
        }

        OutputFormat format = OutputFormat.XML;
        try {
            format = OutputFormat.valueOf(args[0].toUpperCase());
        } catch(IllegalArgumentException ex) {
            System.err.println("Please provide valid output format (xml|csv)");
            System.exit(0);
        }

        Injector injector = Guice.createInjector(new MyModule());
        Application main = injector.getInstance(Application.class);

        main.run(format);
    }

    public void run(OutputFormat format) throws FileNotFoundException {
        provider.bootstrap();
        parser.parse(System.in, System.out, format);

//        parser.parse( new FileInputStream(Paths.get("src/test/resources/small.in").toFile()),
//                new FileOutputStream(Paths.get("src/test/resources/czy64.csv").toFile()), format);

    }
}
