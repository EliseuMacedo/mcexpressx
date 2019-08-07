package com.mcexpress.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mcexpress.domain.Cidade;
import com.mcexpress.domain.Cliente;
import com.mcexpress.domain.Endereco;
import com.mcexpress.domain.enums.TipoCliente;
import com.mcexpress.dto.ClienteDTO;
import com.mcexpress.dto.ClienteNewDTO;
import com.mcexpress.repositories.ClienteRepository;
import com.mcexpress.repositories.EnderecoRepository;
import com.mcexpress.services.exceptions.DataIntegrityException;
import com.mcexpress.services.exceptions.ObjectNotFountException;

//anotação de serviço do springboot
@Service
public class ClienteService {

	// declarar uma dependência de um objeto do tipo CategoriaRepository
	// agora uma anotação para instanciar automaticamente o repositório, assim o
	// serviço acessa o objeto de acesso a dados que é o repository
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		
		//Agora o meu método de serviço lança uma exceção caso o id não exista, porém o rest tem 
		//que capturar a exceção e enviar um JSON apropriado para a resposta HTTP do meu recurso
		return obj.orElseThrow(() -> new ObjectNotFountException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
																//tipo do objeto que trouxe essa exceção					
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null); // se o id do objeto for nulo o metodo save considerará uma inserção, se não for nulo será uma atualização
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos()); //o Spring data salva lista também
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		//objeto monitorado pelo JPA
		Cliente newObj = find(obj.getId()); //chamo o find pois caso o objeto não exista ele já lança uma exceção
		updateData(newObj, obj); //atualizar o objeto newObj com base no obj, isso é para usar a atualização do ClienteDTO
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id); //chamo o find pois caso o objeto não exista ele já lança uma exceção
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir por que há entidades relacionadas");
		}
		//nesse caso o se o cliente estiver associado a ele apenas endereço o endereço 
		//pode ser excluído, mas se estiver associado pedidos ai o cliente não pode ser excluído
	}
	
	public List<Cliente> findAll(){
		
		return repo.findAll();
	}
	
	//Vou criar uma função de paginação, vou usar o sistema do spring data chamado page
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		//para retornar uma página de dados preciso criar um objeto pageRequest
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		
		return repo.findAll(pageRequest);
		//agora retornei o repositorio passando o pagerequest como argumento
		
	}
	
	//método auxiliar que instancia uma categoria a partir de um dto, esse não instancia do banco de dados
	//ele apenas instancia a partir dos dados que já temos
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	
	//sobrecarga do método fromDTO
	public Cliente fromDTO(ClienteNewDTO objDto) {
		
		//preciso primeiro instanciar um cliente
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipoCliente()));
		//Instanciar cidade
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		//Instanciar endereço
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end); // endereço obrigatório
		cli.getTelefones().add(objDto.getTelefone1()); //O telefone obrigatório
		
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
		//Preciso converter esse tipo cliente para inteiro, pois no construtor do cliente ele recebe um tipo cliente.
	}
	
	
	
	//Agora sim, esse objeto newObj que eu busquei ele no banco de dados com todos os dados
	//foi atualizado apenas os atributos fornecido no metodo auxiliar abaixo.
	private void updateData(Cliente newObj, Cliente obj) { //método de dentro da classe
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
