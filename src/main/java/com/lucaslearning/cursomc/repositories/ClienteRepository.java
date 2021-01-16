package com.lucaslearning.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lucaslearning.cursomc.domain.Cliente;

@Repository //essa interface vai acessar a bd com base no tipo que for passado nesse caso e a categoria 
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	@Transactional(readOnly = true)
	Cliente findByEmail(String email);
}
