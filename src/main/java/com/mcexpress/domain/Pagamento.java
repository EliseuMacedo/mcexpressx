package com.mcexpress.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mcexpress.domain.enums.EstadoPagamento;


@Entity
@Inheritance(strategy = InheritanceType.JOINED) //Associação da superclasse pode ser apenas uma 
//tabela se for poucos atributos, ou uma tabela independente se for muitos atributos. Na sub classe é só colocar o @Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type") //Essas anotações são do pacote Jackson que serializa e descerializa 
public abstract class Pagamento implements Serializable{										  //o JSON - Nesse caso essa anotação diz que haverá um campo adicional chado : @type  e na sub classe também será definido o campo adicional                                        
	//abstract para garantir que eu não consiga instanciar 
	//objeto do tipo de pagamento diretamente, para instanciar
	//é preciso um new com uma das sub classes.
	
	private static final long serialVersionUID = 1L;
	
	@Id //não vou gerar automaticamente, esse id pto == pedido
	private Integer id;
	private Integer estadoPagamento;
	
	//Associação
	@JsonIgnore
	@JoinColumn(name="pedido_id")
	@OneToOne
	@MapsId  //aqui eu não vou gerar o ID automático, vou gerar o ID do pto correspondente ao pedido, para isso o maps id
	private Pedido pedido;
	
	public Pagamento() {
	}

	public Pagamento(Integer id, EstadoPagamento estadoPagamento, Pedido pedido) {
		super();
		this.id = id;
		this.estadoPagamento = (estadoPagamento == null)? null: estadoPagamento.getCod(); //Condição ternaria para se o estado for null
		this.pedido = pedido;
	}

	public Integer getId() {
		return id;
	}

	public EstadoPagamento getEstadoPagamento() {
		return EstadoPagamento.toEnum(estadoPagamento);
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setEstadoPagamento(EstadoPagamento estadoPagamento) {
		this.estadoPagamento = estadoPagamento.getCod();
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
