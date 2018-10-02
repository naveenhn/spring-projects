package com.sarvah.mbg.commons;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MbgCommonsApplication {
	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(
				MbgCommonsApplication.class, args);

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		
	}

}
