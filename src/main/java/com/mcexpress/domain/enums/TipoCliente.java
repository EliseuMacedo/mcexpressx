package com.mcexpress.domain.enums;

public enum TipoCliente {
	
	//Essa é uma implementação enumerada sofisticada.
	
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	
	//construtor de tipo enumerado é private
	
	private TipoCliente(int cod, String descricao) {
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
	public static TipoCliente toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		} 
		
		for (TipoCliente x: TipoCliente.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		//Se esgotar o for e não for ninguém vou lancar uma exceção
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
	
}
