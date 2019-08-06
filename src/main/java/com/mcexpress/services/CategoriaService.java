package com.mcexpress.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mcexpress.domain.Categoria;
import com.mcexpress.repositories.CategoriaRepository;
import com.mcexpress.services.exceptions.DataIntegrityException;
import com.mcexpress.services.exceptions.ObjectNotFountException;

//anotação de serviço do springboot
@Service
public class CategoriaService {

	// declarar uma dependência de um objeto do tipo CategoriaRepository
	// agora uma anotação para instanciar automaticamente o repositório, assim o
	// serviço acessa o objeto de acesso a dados que é o repository
	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		
		//Agora o meu método de serviço lança uma exceção caso o id não exista, porém o rest tem 
		//que capturar a exceção e enviar um JSON apropriado para a resposta HTTP do meu recurso
		return obj.orElseThrow(() -> new ObjectNotFountException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
																//tipo do objeto que trouxe essa exceção					
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null); // se o id do objeto for nulo o metodo save considerará uma inserção, se não for nulo será uma atualização
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId()); //chamo o find pois caso o objeto não exista ele já lança uma exceção
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id); //chamo o find pois caso o objeto não exista ele já lança uma exceção
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public List<Categoria> findAll(){
		
		return repo.findAll();
		
	}
}
