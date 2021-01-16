package com.lucaslearning.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.lucaslearning.cursomc.domain.enums.TipoCliente;
import com.lucaslearning.cursomc.dto.ClienteNewDTO;
import com.lucaslearning.cursomc.resources.exceptions.FieldMessage;
import com.lucaslearning.cursomc.services.validation.utils.BR;

/* 
 * Implementa a interface do java ee, especificamos a anotacao a ser criada
 * nesse caso ClienteInsert e a classe que vai receber anotacao personalizada
 * ClienteNewDTO
 */
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Override	
	public void initialize(ClienteInsert ann) {
	}

	/*
	 * Verifica se o nosso tipo que vem de argumento vai ser valido ou nao
	 * Vai ser percebido atraves da anotacao @Valid colocada na classe de 
	 * ClienteService.
	 * A lista instanciada e do tipo personalizada criada no pacote de 
	 * excecoes dos recursos.
	 */
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>(); 
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())){
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
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
