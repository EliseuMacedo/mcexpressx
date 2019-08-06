package com.mcexpress.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mcexpress.domain.Categoria;
import com.mcexpress.dto.CategoriaDTO;
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
		//objeto monitorado pelo JPA
		Categoria newObj = find(obj.getId()); //chamo o find pois caso o objeto não exista ele já lança uma exceção
		updateData(newObj, obj); //atualizar o objeto newObj com base no obj, isso é para usar a atualização do ClienteDTO
		return repo.save(newObj);
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
	
	//Vou criar uma função de paginação, vou usar o sistema do spring data chamado page
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		//para retornar uma página de dados preciso criar um objeto pageRequest
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		
		return repo.findAll(pageRequest);
		//agora retornei o repositorio passando o pagerequest como argumento
		
	}
	
	//método auxiliar que instancia uma categoria a partir de um dto.
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	
	private void updateData(Categoria newObj, Categoria obj) { //método de dentro da classe
		newObj.setNome(obj.getNome());
	}
	
}
