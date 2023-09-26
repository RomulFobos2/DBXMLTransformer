package com.tander.taskOneApp.service;

import com.tander.taskOneApp.models.Field;
import com.tander.taskOneApp.repo.FieldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Service
public class TestTblService {
    private static final Logger logger = LoggerFactory.getLogger(TestTblService.class);
    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertFieldsListModels(List<Field> fieldList) {
        fieldRepository.saveAll(fieldList);
        logger.debug("Save field to table, count - " + fieldList.size() + ".");
    }

    public void clearTblModels() {
        logger.info("Clear test_tbl table, count - " + fieldRepository.count() + ".");
        fieldRepository.deleteAll();
    }

//    public void insertFields(int N, String sqlCommand) {
//        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
//            Statement statement = connection.createStatement();
//            StringBuilder strForExecute = new StringBuilder();
//            strForExecute.append(sqlCommand);
//            for (int i = 1; i <= N; i++) {
//                strForExecute.append("(" + i + "), ");
//                if (i > 1 && i % 10000 == 0) {
//                    strForExecute.deleteCharAt(strForExecute.length() - 2);
//                    statement.executeUpdate(strForExecute.toString());
//                    strForExecute.setLength(0);
//                    strForExecute.append(sqlCommand);
//                    logger.debug("Save field to table, count - " + N + ".");
//                }
//            }
//            if (!strForExecute.toString().equals(sqlCommand)) {
//                strForExecute.deleteCharAt(strForExecute.length() - 2);
//                statement.executeUpdate(strForExecute.toString());
//                logger.debug("Save field to table, count - " + N + ".");
//            }
//        } catch (SQLException e) {
//            logger.error("Error when executing sql query.", e);
//        }
//    }

    public void insertFieldsList(int N, String sqlCommand) {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            StringBuilder strForExecute = new StringBuilder(sqlCommand);
            for (int i = 1; i <= N; i++) {
                strForExecute.append("(" + i + "), ");
                if (i > 1 && i % 10000 == 0) {
                    strForExecute.delete(strForExecute.length() - 2, strForExecute.length());
                    statement.addBatch(strForExecute.toString());
                    statement.executeBatch();
                    strForExecute.setLength(sqlCommand.length());
                    logger.debug("Save field to table, count - " + N + ".");
                }
            }
            if (!strForExecute.toString().equals(sqlCommand)) {
                strForExecute.delete(strForExecute.length() - 2, strForExecute.length());
                statement.addBatch(strForExecute.toString());
                statement.executeBatch();
                logger.debug("Save field to table, count - " + N + ".");
            }
        } catch (SQLException e) {
            logger.error("Error when executing sql query.", e);
        }
    }

    public void clearTbl(String sqlCommand) {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sqlCommand);
        } catch (SQLException e) {
            logger.error("Error when executing sql query.", e);
        }
        logger.info("Clear test_tbl table.");
    }
}