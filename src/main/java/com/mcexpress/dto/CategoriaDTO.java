package com.mcexpress.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.mcexpress.domain.Categoria;

//Esse é um objeto que irá definir os dados que eu quero trafegar quando definir o objeto categoria
public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
//facilita gravar em arquivo ou trafegar em rede
	
	private Integer id;
	
	@NotEmpty(message="Preenchimento do nome obrigatório")
	@Length(min=5,max=80, message="Tamanho inválido, minimo 5, máximo 80 caracteres")
	private String nome;
	
	public CategoriaDTO() {
	}
	
	//construtor que recebe os parametros de categoria
	
	public CategoriaDTO(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	//agora no método ao inves de chamar a lista de categoria eu chamo CategoriaDTO, sem atrapalhar
	//a estrutura do objeto Categorias
}
