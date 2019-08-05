package com.mcexpress.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ItemPedido implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//O id dessa classe é ItemPedidoPK e a associação de pedido e produto já foi feita no ItemPedidoPK
	//A classe de associação não tem id próprio, o que identifica a classe são os dois objetos associados a ela
	//A forma mais interessante é criar uma chave composta contendo o produto e o pedido  para tanto vamos criar uma clase auxiliar
	
	@EmbeddedId //é um id embutido em um tipo auxiliar
	private ItemPedidoPK id = new ItemPedidoPK();
	
	private Double desconto;
	private Integer quantidade;
	private Double preco;
	
	public ItemPedido() {
	}

	//o Item pedido PK é uma peculiaridade do JPA, para os programadores que forem 
	//utilizar a classe o objeto ItemPedidoPk não faz sentido, então vou trocar o 
	//ItemPedidoPK por pedido e produto e atribuir pedido e produto dentro do objeto id o produto e pedido que veio como argumento 
	public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
		super();
		this.id.setPedido(pedido);
		this.id.setProduto(produto);
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}
	
	//vou criar os gets de pedido e produto, isso é para eu ter acesso direto ao Pedido e 
	//Produto fora da minha classe itemPedido, faz mas sentido do que eu ter que acessar 
	//primeiro o ID e depois acessar o produto e pedido
	
	public Pedido getPedido() {
		return this.id.getPedido();
	}
	
	public Produto getProduto() {
		return this.id.getProduto();
	}

	public ItemPedidoPK getId() {
		return id;
	}

	public Double getDesconto() {
		return desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
