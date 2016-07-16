package com.mastrodaro;

import javax.xml.stream.XMLStreamException;

@FunctionalInterface
public interface RunnableXmlWrite {

    void run() throws XMLStreamException;
}
