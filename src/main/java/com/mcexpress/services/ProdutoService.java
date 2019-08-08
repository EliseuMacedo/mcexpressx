package com.mcexpress.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mcexpress.domain.Categoria;
import com.mcexpress.domain.Produto;
import com.mcexpress.repositories.CategoriaRepository;
import com.mcexpress.repositories.ProdutoRepository;
import com.mcexpress.services.exceptions.ObjectNotFountException;

//anotação de serviço do springboot
@Service
public class ProdutoService {

	// declarar uma dependência de um objeto do tipo ProdutoRepository
	// agora uma anotação para instanciar automaticamente o repositório, assim o
	// serviço acessa o objeto de acesso a dados que é o repository
	@Autowired
	private ProdutoRepository repo;
	@Autowired
	private CategoriaRepository catRepo;

	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		
		//Agora o meu método de serviço lança uma exceção caso o id não exista, porém o rest tem 
		//que capturar a exceção e enviar um JSON apropriado para a resposta HTTP do meu recurso
		return obj.orElseThrow(() -> new ObjectNotFountException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
																//tipo do objeto que trouxe essa exceção					
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){ 
		//Esse método alem do nome e da lista de is's contem os parámetros da paginação  

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		List<Categoria> categorias =  catRepo.findAllById(ids);
		
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
