package com.mastrodaro;

public enum OutputFormat {
    XML("xml"), CSV("csv");

    private String format;

    OutputFormat(String format) {
        this.format = format;
    }
}
