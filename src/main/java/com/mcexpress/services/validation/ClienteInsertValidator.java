package com.mcexpress.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.mcexpress.domain.enums.TipoCliente;
import com.mcexpress.dto.ClienteNewDTO;
import com.mcexpress.resources.exception.FieldMessage;
import com.mcexpress.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> { //especificar o nome da anotação e o tipo da classe
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>(); //A lista FieldMessage é o objeto de exceção resources que retorna o campo e a mensagem de erro

		// inclua os testes aqui, inserindo erros na lista
		// Se tipo do cliente for PF, validação CPF se for PJ então validação de CNPJ
		if(objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		if(objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		for (FieldMessage e : list) { //percorre a lista de erros e adiciona na lista de erros do framework/
			//os dois campos personalizados permite adicionar a lista de erros personalizada no framework/
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
			//essa lista de erros vai set tratada e repassada como resposta lá no nosso exception handler
		}
		return list.isEmpty(); // se a lista for vazia o método retorna verdadeiro, se não for vazia o metodo retorna falso
	}
}
