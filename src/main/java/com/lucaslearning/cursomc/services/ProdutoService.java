package com.lucaslearning.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lucaslearning.cursomc.domain.Categoria;
import com.lucaslearning.cursomc.domain.Produto;
import com.lucaslearning.cursomc.repositories.CategoriaRepository;
import com.lucaslearning.cursomc.repositories.ProdutoRepository;
import com.lucaslearning.cursomc.services.exceptions.ObjectNotFoundException;


@Service 
public class ProdutoService {
	
	@Autowired //injecao de dependencia, vai ser automaticamente instanciada pelo spring
	private ProdutoRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) {
		Optional<Produto> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, 
			String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);	
	}
}
