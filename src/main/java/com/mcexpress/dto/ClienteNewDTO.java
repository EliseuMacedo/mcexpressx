package com.mcexpress.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.mcexpress.services.validation.ClienteInsert;

@ClienteInsert
public class ClienteNewDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	//vou construir um Data Transfer Object (DTO) com dados da classe cliente/endereco e pelo menos 1 telefone
	//Pois no modelo conceitual um cliente precisa de pelo menos um endereço e um telefone.
	
	//Validações sintáticas
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=120, message="Preenchimento de no minimo 5, no máximo 120 caracteres")
	private String nome;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="Email inválido")
	private String email;
	
	//o hibernate tem validação apra @CPF @CNPJ, 
	//porém não é esse o caso pois os dois estão no 
	//mesmo campo. Nesse caso vamos customizar uma anotação
	@NotEmpty(message="Preenchimento obrigatório") 
	private String cpfOuCnpj;
	//Enumeração
	private Integer tipoCliente;  //Internamente a classe vai entender tipo cliente como inteiro, 
	//porém externamente, no construtor a classe vai exppor um tipoCliente;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String logradouro;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String numero;
	private String complemento;
	private String bairro;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String cep;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String telefone1;
	private String telefone2;
	private String telefone3;
	
	private Integer CidadeId;
	
	public ClienteNewDTO() {
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

	public Integer getTipoCliente() {
		return tipoCliente;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCep() {
		return cep;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public Integer getCidadeId() {
		return CidadeId;
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

	public void setTipoCliente(Integer tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public void setCidadeId(Integer cidadeId) {
		CidadeId = cidadeId;
	}
	
}
