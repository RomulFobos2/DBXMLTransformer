package com.tander.dbXMLTransformer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

@Service
public class XmlTransformerService {
    private static final Logger logger = LoggerFactory.getLogger(XmlTransformerService.class);
    private final ResourceLoader resourceLoader;

    public XmlTransformerService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void transformXml(String templateNameXSL, String sourceXmlFile, String fileOutputName){
        try {
            Resource xsltResource = resourceLoader.getResource("classpath:template.xsl");
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(xsltResource.getInputStream());
            Transformer transformer = factory.newTransformer(xslt);
            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            Source xml = new StreamSource(sourceXmlFile);
            transformer.transform(xml, new StreamResult(fileOutputName));
            logger.info("Transformation complete.");
        }
        catch (TransformerException e){
            logger.error("Error transform.", e);
        } catch (IOException e) {
            logger.error("Error read template.", e);
        }
    }
}
