package com.lucaslearning.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucaslearning.cursomc.domain.Categoria;
import com.lucaslearning.cursomc.repositories.CategoriaRepository;
import com.lucaslearning.cursomc.services.exceptions.ObjectNotFoundException;


@Service 
public class CategoriaService {
	
	@Autowired //injecao de dependencia, vai ser automaticamente instanciada pelo spring
	private CategoriaRepository repository;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
}
