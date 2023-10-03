package com.tander.dbXMLTransformer;

import com.tander.dbXMLTransformer.worker.DatabaseWorker;
import com.tander.dbXMLTransformer.worker.XmlWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StopWatch;

@SpringBootApplication
public class DBXMLTransformerApp {
	private static final Logger logger = LoggerFactory.getLogger(DBXMLTransformerApp.class);

	public static void main(String[] args) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		ApplicationContext context = SpringApplication.run(DBXMLTransformerApp.class, args);
		DatabaseWorker databaseWorker = context.getBean(DatabaseWorker.class);
		XmlWorker xmlWorker = context.getBean(XmlWorker.class);
		databaseWorker.work();
		xmlWorker.work();

		stopWatch.stop();
		logger.info("Lead time: " + stopWatch.getTotalTimeMillis() + " ms");
	}

}
