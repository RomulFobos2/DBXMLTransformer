package com.tander.taskOneApp.worker;

import com.tander.taskOneApp.models.Field;
import com.tander.taskOneApp.repo.FieldRepository;
import com.tander.taskOneApp.service.XmlCreatorService;
import com.tander.taskOneApp.service.XmlParserService;
import com.tander.taskOneApp.service.XmlTransformerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class XmlWorker {
    private static final Logger logger = LoggerFactory.getLogger(XmlWorker.class);
    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private XmlCreatorService xmlCreatorService;

    @Autowired
    private XmlTransformerService xmlTransformerService;

    @Autowired
    private XmlParserService xmlParserService;

    @Value("${outXmlFile}")
    private String outXmlFile;

    @Value("${rootElement}")
    private String rootElement;

    @Value("${childElement}")
    private String childElement;

    @Value("${attributeName}")
    private String attributeName;

    @Value("${transformNameXml}")
    private String transformNameXml;

    @Value("${spring.xml.xslt.template}")
    private String templateName;

    public void work(){
        createXml();
        transformXml();
        calculateValue();
    }

    private void createXml() {
        logger.info("2. Start createXml().");
        List<Field> fieldList = fieldRepository.findAll();
        xmlCreatorService.xmlCreateJAXP(outXmlFile, rootElement, childElement, attributeName, fieldList);
    }

    private void transformXml() {
        logger.info("3. Start transformXml().");
        xmlTransformerService.transformXml(templateName, outXmlFile, transformNameXml);
    }

    private void calculateValue(){
        logger.info("4. Start calculateValue().");
        xmlParserService.calculateFieldValue(transformNameXml);
    }
}