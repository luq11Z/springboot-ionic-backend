package com.lucaslearning.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component //permitir que seja injetavel em outras classes como componente
public class JWTUtil {
	
	@Value("${jwt.secret}") //buscar valor dessa variavel no application.properties
	private String secret;
	
	@Value("${jwt.expiration}") 
	private Long expiration;
	
	/*
	 * Gerar token para um user
	 */
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	
}
