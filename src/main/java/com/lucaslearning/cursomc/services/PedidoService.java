package com.lucaslearning.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucaslearning.cursomc.domain.Pedido;
import com.lucaslearning.cursomc.repositories.PedidoRepository;
import com.lucaslearning.cursomc.services.exceptions.ObjectNotFoundException;


@Service 
public class PedidoService {
	
	@Autowired //injecao de dependencia, vai ser automaticamente instanciada pelo spring
	private PedidoRepository repository;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
