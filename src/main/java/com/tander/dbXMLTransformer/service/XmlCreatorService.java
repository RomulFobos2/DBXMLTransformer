package com.tander.dbXMLTransformer.service;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import com.tander.dbXMLTransformer.models.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class XmlCreatorService {
    private static final Logger logger = LoggerFactory.getLogger(XmlCreatorService.class);

    public void xmlCreateJAXP(String outXmlFile, String rootElement, String childElement, String attributeName, List<Field> fieldList) {
        try {
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new FileWriter(outXmlFile));
            xmlStreamWriter = new IndentingXMLStreamWriter(xmlStreamWriter); //вывод с форматированием xml
            xmlStreamWriter.writeStartDocument();
            xmlStreamWriter.writeStartElement(rootElement);

            for (Field field : fieldList) {
                xmlStreamWriter.writeStartElement(childElement);
                xmlStreamWriter.writeStartElement(attributeName);
                xmlStreamWriter.writeCharacters(String.valueOf(field.getField()));
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeEndElement();
            }

            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();
            xmlStreamWriter.close();
            logger.info("XML document created successfully.");
        } catch (XMLStreamException e) {
            logger.error("Error write to XML.", e);
        } catch (IOException e) {
            logger.error("Error create file.", e);
        }
    }
}