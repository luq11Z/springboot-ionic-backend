package com.lucaslearning.cursomc.domain.enums;

public enum EstadoPagamento {
	
	PENDENTE(1, "Pendente"),
	QUITDADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private Integer cod;
	private String descricao;

	private EstadoPagamento(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public Integer getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	//receber um codigo e retornar um objeto do tipoCliente ja instanciado conforme o codigo passado
		public static EstadoPagamento toEnum(Integer cod) {
			
			if(cod == null) {
				return null;
			}
			
			//percorrer todos valores possiveis do meu tipo enumerado
			for (EstadoPagamento x: EstadoPagamento.values()) {
				if (cod.equals(x.getCod())) {
					return x;
				}
			}
			
			throw new IllegalArgumentException("Id inválido: " + cod);
		}
}
