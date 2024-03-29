package com.mcexpress.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


//Entidade do JPA
@Entity
public class Produto implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Gerar id automaticamente
	private Integer id;
	private String nome;
	private Double preco;
	
	//Quando tem duas tabelas com muitos para muitos precisamos de uma terceira tabela que faz Join com as duas atuais (Categoria e Produto)
	@JsonIgnore // omite a lista de categorias para cada produto pois do outro ládo já aparece os produtos da categoria.
	@ManyToMany
	@JoinTable(name = "PRODUTO_CATEGORIA",
	joinColumns = @JoinColumn(name = "produto_id"), //definimos a chave estrangieira de produto (produto_id)
	inverseJoinColumns = @JoinColumn(name = "categoria_id") //definimos a chave estrangieira da categoria (categoria_id)
	)
	private List<Categoria> categorias = new ArrayList<>();
	
	//A classe pedido vai ter que conhecer os itens de pedido associados a ela, o Set ajuda a garantir que não vai ter item repetido no mesmo pedido.
	@JsonIgnore
	@OneToMany(mappedBy = "id.produto")
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Produto() {
	}

	//coleções não entrarm no método construtor pois já foi iniciada
	public Produto(Integer id, String nome, Double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
	
	//Um produto conhece seus pedidos, assim posso montar um get pedido, varrendo os itens do pedido e montar uma lista de pedidos associados a esses itens,
	@JsonIgnore
	public List<Pedido> getPedidos(){
		List<Pedido> listaPedidos = new ArrayList<>();
		for(ItemPedido x: itens) {
			listaPedidos.add(x.getPedido());
		}
		return listaPedidos;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((preco == null) ? 0 : preco.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (preco == null) {
			if (other.preco != null)
				return false;
		} else if (!preco.equals(other.preco))
			return false;
		return true;
	}




}
