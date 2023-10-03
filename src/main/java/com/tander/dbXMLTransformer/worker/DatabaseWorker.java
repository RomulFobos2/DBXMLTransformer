package com.tander.dbXMLTransformer.worker;

import com.tander.dbXMLTransformer.models.Field;
import com.tander.dbXMLTransformer.service.TestTblService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DatabaseWorker {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseWorker.class);
    @Autowired
    private TestTblService testTblService;

    @Value("${N}")
    private String strN;

    @Value("${sqlClearTable}")
    private String sqlClearTable;


    @Value("${sqlInsertTable}")
    private String sqlInsertTable;

    public void work(){
        //createNewFieldsModels();
        createNewFields();
    }
    private void createNewFieldsModels() {
        testTblService.clearTblModels();
        logger.info("1. Start create field to table().");
        int intN = Integer.parseInt(this.strN);
        ArrayList<Field> fieldList = new ArrayList<>();
        for (int i = 1; i <= intN; i++) {
            fieldList.add(new Field(i));
            if (i > 1 && i % 100000 == 0) {
                testTblService.insertFieldsListModels(fieldList);
                fieldList.clear();
            }
        }
        testTblService.insertFieldsListModels(fieldList);
        logger.info("End create field to table.");
    }

    private void createNewFields() {
        testTblService.clearTbl(sqlClearTable);
        logger.info("1. Start create field to table().");
        int intN = Integer.parseInt(this.strN);
        testTblService.insertFields(intN, sqlInsertTable);
        logger.info("End create field to table.");
    }
}
