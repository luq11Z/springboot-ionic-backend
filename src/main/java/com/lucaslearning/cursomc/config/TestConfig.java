package com.lucaslearning.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lucaslearning.cursomc.services.DBService;
import com.lucaslearning.cursomc.services.EmailService;
import com.lucaslearning.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService service;
	
	@Bean
	public boolean instantiateDataBase() throws ParseException {
		service.instantiateTestDataBase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
