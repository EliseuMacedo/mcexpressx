package com.mcexpress.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.mcexpress.domain.Cliente;
import com.mcexpress.dto.ClienteDTO;
import com.mcexpress.repositories.ClienteRepository;
import com.mcexpress.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> { //especificar o nome da anotação e o tipo da classe
	
	// preciso desse objeto request para pegar o número do ID enviado na url para comparar com o objeto id no banco de dados e verificar se o mesmo já existe para atualização.
	@Autowired
	private HttpServletRequest request;
	
	@Autowired 
	private ClienteRepository clienteRepository;

	
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		//estrutura de dados, coleção chave/valor do pacote java.ultil
		
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE); // nome que identifica os atributos
		Integer	uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>(); //A lista FieldMessage é o objeto de exceção resources que retorna o campo e a mensagem de erro

		// inclua os testes aqui, inserindo erros na lista
		
		Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
		if(aux != null && !aux.getId().equals(uriId)) { //vou testar se esse cliente possui um email já existente no banco
			list.add(new FieldMessage("Email", "Email já existente"));
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
