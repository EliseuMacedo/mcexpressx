package com.mcexpress.resources; // o nome Resources é utilizado para criar a classe de controlador rest

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.mcexpress.domain.Pedido;
import com.mcexpress.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {
	
	@Autowired //Instanciar automaticamente
	private PedidoService service;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) { 
		
		//para o spring saber que o id da url vai vim na variável é preciso uma anotação @PathVariable
		//para sofistivcar o metodo vou retornar um responseEntity, tipo especial do Spring 
	   	//que encapsula, armazena informações de resposta HTTP para um serviço Rest, e o ? informa que pode ser qualquer tipo.
		
		
		// declaração obj recebendo o serviço buscar repassando o ID/ nesse caso nos estamos
		
		Pedido obj = service.buscar(id);
		
		//no controlador rest, acessando o serviço, e o serviço por sua vez irá acessar o repository (acesso a dados).

		
		// Vou retornar um objeto Response Entity http, complexo com várias informações do protocolo http
		// A resposta com metodo ok(operação com sucesso), e a resposta vai ter como corpo o obj que é a categoria
		return ResponseEntity.ok().body(obj);
	}
}
