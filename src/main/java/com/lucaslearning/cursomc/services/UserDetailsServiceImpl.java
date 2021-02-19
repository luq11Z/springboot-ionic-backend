package com.lucaslearning.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lucaslearning.cursomc.domain.Cliente;
import com.lucaslearning.cursomc.repositories.ClienteRepository;
import com.lucaslearning.cursomc.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente obj = repository.findByEmail(email);
		if (obj == null) {
			throw new UsernameNotFoundException(email);
		} else {
			return new UserSS(obj.getId(), obj.getEmail(), obj.getSenha(), obj.getPerfis());
		}
	}

}
