package com.lucaslearning.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucaslearning.cursomc.domain.Endereco;

@Repository //essa interface vai acessar a bd com base no tipo que for passado nesse caso e a categoria 
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{

}
