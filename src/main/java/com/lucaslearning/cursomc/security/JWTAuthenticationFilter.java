package com.lucaslearning.cursomc.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucaslearning.cursomc.dto.CredenciaisDTO;

/*
 * Com essa extensao o spring automaticamente faz com que essa classe trate intercepta a requisicao de login
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
	
	private JWTUtil jwtUtil;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	/*
	 * Instanciar um obj do tipo credenciais a partir do que vem no corpo da requisicao,
	 * instanciar tbm um token do spring security passando as credenciais e uma lista,
	 * apos isso verifica se o user e senha sao validos atraves das varias classes implementadas 
	 * (spring automaticamente vai busca-las)
	 * 
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, 
			HttpServletResponse res) throws AuthenticationException {
		try {
		CredenciaisDTO creds = new ObjectMapper()
				.readValue(req.getInputStream(), CredenciaisDTO.class);
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), 
				creds.getSenha(), new ArrayList<>());
		Authentication auth = authenticationManager.authenticate(authToken);
		return auth;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * Se a tentativa obter sucesso entao retornar o token no header da resposta
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest req, 
			HttpServletResponse res,
			FilterChain chain,
			Authentication auth) throws IOException, ServletException{
		String username = ((UserSS) auth.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);
		res.addHeader("Authorization", token);
	}
}
