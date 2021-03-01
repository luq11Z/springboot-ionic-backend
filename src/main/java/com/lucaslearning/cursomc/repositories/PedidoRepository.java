package com.lucaslearning.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lucaslearning.cursomc.domain.Cliente;
import com.lucaslearning.cursomc.domain.Pedido;

@Repository //essa interface vai acessar a bd com base no tipo que for passado nesse caso e a categoria 
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
	
	@Transactional(readOnly = true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
}
