package com.mcexpress.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable //para dizer que essa classe é um subtipo do ItemPedido
public class ItemPedidoPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//A classe de associação não tem id próprio, o que identifica a classe são os dois objetos associados a ela
	//A forma mais interessante é criar uma chave composta contendo o produto e o pedido  para tanto vamos criar uma clase auxiliar
	//Vamos criar uma referencia para o pedido e uma para o produto
	
	@ManyToOne
	@JoinColumn(name="pedido_id")
	private Pedido pedido;
	@ManyToOne
	@JoinColumn(name="produto_id")
	private Produto produto;
	
	
	//Essa classe não precisa de construtor
	
	public Pedido getPedido() {
		return pedido;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pedido == null) ? 0 : pedido.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
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
		ItemPedidoPK other = (ItemPedidoPK) obj;
		if (pedido == null) {
			if (other.pedido != null)
				return false;
		} else if (!pedido.equals(other.pedido))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}
	
}
