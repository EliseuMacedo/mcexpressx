package com.mcexpress.domain.enums;

public enum EstadoPagamento {
	
	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(2, "Cancelado");
	
	private int cod;
	private String descricao;
	
	//construtor de tipo enumerado é private
	
	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return this.cod;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	//Operacao que recebe um codigo e retorna um objeto do tipo cliente estanciado conforme o codigo que passarmos
	
	//static por que a operação será executada mesmo sem instanciar objeto 
	public static EstadoPagamento toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		} 
		
		for (EstadoPagamento x: EstadoPagamento.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		//Se esgotar o for e não for ninguém vou lancar uma exceção
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
	//as subclasses não precisa de hashcode e equals pois herdam da superclasse

}
