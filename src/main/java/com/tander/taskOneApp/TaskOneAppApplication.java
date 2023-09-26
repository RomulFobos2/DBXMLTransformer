package com.tander.taskOneApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;

@SpringBootApplication
public class TaskOneAppApplication {
	private static final Logger logger = LoggerFactory.getLogger(TaskOneAppApplication.class);

	public static void main(String[] args) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		SpringApplication.run(TaskOneAppApplication.class, args);

		stopWatch.stop();
		logger.info("Lead time: " + stopWatch.getTotalTimeMillis() + " ms");
	}

}
