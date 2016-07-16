package com.nav.spring.velocity;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VelocitySampleApplication implements CommandLineRunner{
	
	@Autowired
	private VelocityEngine velocityEngine;

	public static void main(String[] args) {
		SpringApplication.run(VelocitySampleApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
