package com.lucaslearning.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lucaslearning.cursomc.domain.Estado;

@Repository //essa interface vai acessar a bd com base no tipo que for passado nesse caso e a categoria 
public interface EstadoRepository extends JpaRepository<Estado, Integer>{
	
	@Transactional(readOnly = true)
	public List<Estado> findAllByOrderByNome();
}
