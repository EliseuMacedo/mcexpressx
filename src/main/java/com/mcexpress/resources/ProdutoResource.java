package com.mcexpress.resources; // o nome Resources é utilizado para criar a classe de controlador rest

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mcexpress.domain.Produto;
import com.mcexpress.dto.ProdutoDTO;
import com.mcexpress.resources.utils.URL;
import com.mcexpress.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	
	@Autowired //Instanciar automaticamente
	private ProdutoService service;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) { 
		
		//para o spring saber que o id da url vai vim na variável é preciso uma anotação @PathVariable
		//para sofistivcar o metodo vou retornar um responseEntity, tipo especial do Spring 
	   	//que encapsula, armazena informações de resposta HTTP para um serviço Rest, e o ? informa que pode ser qualquer tipo.
		// declaração obj recebendo o serviço buscar repassando o ID/ nesse caso nos estamos
		
		Produto obj = service.find(id);
		
		//no controlador rest, acessando o serviço, e o serviço por sua vez irá acessar o repository (acesso a dados).
		// Vou retornar um objeto Response Entity http, complexo com várias informações do protocolo http
		// A resposta com metodo ok(operação com sucesso), e a resposta vai ter como corpo o obj que é a categoria
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET) //vou concategar um /page para diferenciar do categorias ex: http://localhost:8081/categorias/page
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue = "")String nome, 
			@RequestParam(value="categorias", defaultValue = "0")String categorias,
			@RequestParam(value="page", defaultValue = "0")Integer page,
			@RequestParam(value="linesPerPage", defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "nome")String orderBy, 
			@RequestParam(value="direction", defaultValue = "ASC")String direction) {
		
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias); //Lista de números inteiros geradas, vamos passar elas como argumentos
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy,direction);
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj)); 
		
		return ResponseEntity.ok().body(listDto);
		//exemplo do retorno: http://localhost:8081/produtos?&nome=t&categorias=1,4
		//Doc JPA Spring methods: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
	}
}
