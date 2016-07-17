package com.mastrodaro.exporters;

import com.mastrodaro.parser.OutputFormat;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ExporterProvider {

    private Map<OutputFormat, Exporter> exporters;

    public void bootstrap() {
        exporters = new HashMap<>(2);
        exporters.put(OutputFormat.XML, new XMLExporter());
        exporters.put(OutputFormat.CSV, new CSVExporter());
    }

    public Exporter getExporter(OutputFormat format) {
        return exporters.get(format);
    }

    public long registeredExportersSize() {
        return exporters.size();
    }

    public void registerExporter(OutputFormat format, Exporter writer) {
        if(exporters.containsKey(format) || exporters.containsValue(writer)) {
            throw new IllegalArgumentException("Exporter already registered!");
        }
        exporters.put(format, writer);
    }

    public void unregisterExporter(OutputFormat format) {
        exporters.remove(format);
    }

}
