package com.lucaslearning.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.lucaslearning.cursomc.domain.Cliente;
import com.lucaslearning.cursomc.dto.ClienteDTO;
import com.lucaslearning.cursomc.repositories.ClienteRepository;
import com.lucaslearning.cursomc.resources.exceptions.FieldMessage;

/* 
 * Implementa a interface do java ee, especificamos a anotacao a ser criada
 * nesse caso ClienteInsert e a classe que vai receber anotacao personalizada
 * ClienteNewDTO
 */
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repository;
	
	@Override	
	public void initialize(ClienteUpdate ann) {
		
	}

	/*
	 * Verifica se o nosso tipo que vem de argumento vai ser valido ou nao
	 * Vai ser percebido atraves da anotacao @Valid colocada na classe de 
	 * ClienteService.
	 * A lista instanciada e do tipo personalizada criada no pacote de 
	 * excecoes dos recursos.
	 */
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		/*
		 * Quando fazemos uma requisicao a mesma pode ter varios atributos
		 * e esses atributos sao guardados num map, por isso essa instanciacao,
		 * E parecido com Json, contem uma chave e um valor e.g("name" : "teste")
		 * Tambem e necessario faz um downcast para converter o resultado para
		 * um map(<String, String>)
		 */
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>(); 
		
		Cliente aux = repository.findByEmail(objDto.getEmail());
		
		/*
		 * Previnir que um cliente com id diferente do que lhe foi atribuido consiga alterar o email
		 * de outro cliente
		 */
		if (aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("email","Email já existente." ));
		}
		
		/*
		 * Esse for vai percorrer a minha lista personalizada e para cada objeto
		 * presente na lista, adiciona-se um erro correspondente na lista de erros
		 * da propria framework que sao os comandos dentro do for.
		 * Basicamente esses comandos permitem transportar a lista de erros criada
		 * por mim e personalizado para a lista de erros da propria framework.
		 */
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
