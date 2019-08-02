package com.mcexpress.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mcexpress.domain.enums.TipoCliente;

@Entity
public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String email;
	private String cpfOuCnpj;
	
	//Enumeração
	private Integer tipoCliente;  //Internamente a classe vai entender tipo cliente como inteiro, porém externamente, no construtor
	//a classe vai exppor um tipoCliente;
	
	//Associações
	@JsonManagedReference
	@OneToMany(mappedBy="cliente") //já foi mapeado pelo campo cliente
	private List<Endereco> endereços = new ArrayList<>();
	
	//Telefone é uma entidade fraca sem ID próprio e dependente do cliente
	//portanto vamos implementar os telefones como uma coleção de strings associadas ao cliente
	//para isso não vou usar o tipo list, vou usar um tipo Set, pois set é conjunto, e conjunto não aceita repetições.
	
	@ElementCollection //mapear no JPA entidade fraca
	@CollectionTable(name="telefone") //Criar tabela telefone
	private Set<String> telefones = new HashSet<>();
	
	
	//Construtores
	public Cliente() {
	}

	//Construtor com campos - coleções
	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipoCliente) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipoCliente = tipoCliente.getCod(); //metodo get pois estou passando o tipo inteiro.
	}
	
	//get set todos inclusive coleções

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public TipoCliente getTipoCliente() {
		//aqui vou chamar o método estático da classe TipoCliente que pega um id e retorna um objeto
		//Chama o enum e passa o tipoCliente que é inteiro e está armazenado dentro dessa classe
		return TipoCliente.toEnum(tipoCliente);
	}

	public List<Endereco> getEndereços() {
		return endereços;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente.getCod();
	}

	public void setEndereços(List<Endereco> endereços) {
		this.endereços = endereços;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}
	
	
	
	

	
	
	
	
	

}
