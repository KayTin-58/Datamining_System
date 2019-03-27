package com.zhang;

import com.zhang.config.FileUploadConfig;
import com.zhang.config.TaskSchedulerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class DataminingSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(DataminingSystemApplication.class, args);
        new AnnotationConfigApplicationContext(TaskSchedulerConfig.class);
		new AnnotationConfigApplicationContext(FileUploadConfig.class);
	}
}
