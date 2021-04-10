package com.lucaslearning.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucaslearning.cursomc.domain.Cidade;
import com.lucaslearning.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repository;
	
	public List<Cidade> findAll(Integer estadoId){
		return repository.findCidades(estadoId);
	}
}
