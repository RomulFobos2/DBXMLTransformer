package com.tander.dbXMLTransformer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
@Service
public class XmlParserService {
    private static final Logger logger = LoggerFactory.getLogger(XmlParserService.class);
    public void calculateFieldValue(String srcFileName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(srcFileName));
            NodeList entryElements = document.getDocumentElement().getElementsByTagName("entry");
            long sum = 0;
            for (int i = 0; i < entryElements.getLength(); i++) {
                sum += Long.parseLong(entryElements.item(i).getAttributes().getNamedItem("field").getNodeValue());
            }
            logger.info("Summ of all elements FIELD = " + sum);
        } catch (Exception e){
            logger.error("Error calculate.", e);
        }
    }
}
