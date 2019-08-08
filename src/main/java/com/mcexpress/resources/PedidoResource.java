package com.mcexpress.resources; // o nome Resources é utilizado para criar a classe de controlador rest

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mcexpress.domain.Pedido;
import com.mcexpress.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {
	
	@Autowired //Instanciar automaticamente
	private PedidoService service;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) { 
		
		//para o spring saber que o id da url vai vim na variável é preciso uma anotação @PathVariable
		//para sofistivcar o metodo vou retornar um responseEntity, tipo especial do Spring 
	   	//que encapsula, armazena informações de resposta HTTP para um serviço Rest, e o ? informa que pode ser qualquer tipo.
		// declaração obj recebendo o serviço buscar repassando o ID/ nesse caso nos estamos
		
		Pedido obj = service.find(id);
		
		//no controlador rest, acessando o serviço, e o serviço por sua vez irá acessar o repository (acesso a dados).
		// Vou retornar um objeto Response Entity http, complexo com várias informações do protocolo http
		// A resposta com metodo ok(operação com sucesso), e a resposta vai ter como corpo o obj que é a categoria
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST) //A anotação @Valid indica que o metodo irá usar o BeanValidation na Categoria DTO
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj){ //Para o objeto ser construido a partir dos dados JSON que eu enviar é preciso a anotação antes da variável
		
		obj = service.insert(obj);
		//O HTTP quando estou inserindo um novo recurso há um codigo de resposta particular, o codigo adequado é 201 Created
		//Vamos usar a chave da categoria para inserir a URL HTTP
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		// o fromCurrentRequest pega o URL base ex: "http://localhost:8081/categorias" e o buildAndExpand() o id do objeto Inserido.
		
		return ResponseEntity.created(uri).build(); 
	}
	
	
}
