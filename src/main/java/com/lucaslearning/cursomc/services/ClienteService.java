package com.lucaslearning.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lucaslearning.cursomc.domain.Cliente;
import com.lucaslearning.cursomc.dto.ClienteDTO;
import com.lucaslearning.cursomc.repositories.ClienteRepository;
import com.lucaslearning.cursomc.services.exceptions.DataIntegrityException;
import com.lucaslearning.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente .class.getName()));
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return repository.save(obj);
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId()); //instanciar cliente a partir do banco de dados
		updateData(newObj, obj); //atualizar esse mesmo cliente com base nos dados recebidos 
		return repository.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
		repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar porque há entidades relacionadas.");
		}
	}
	
	public List<Cliente> findAll(){
		return repository.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Cliente fromDTo(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null); //instancia uma categoria a partir de um DTO
	}
	
	/*	Metodo para atualizar um cliente com base nos dados recebidos
	 * 	de um objeto do tipo cliente
	 */
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
