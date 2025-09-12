package com.mycompany.p1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringbootProject1250905Application extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SpringbootProject1250905Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringbootProject1250905Application.class);
	}
	
	

}
