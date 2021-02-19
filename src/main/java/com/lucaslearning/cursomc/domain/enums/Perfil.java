package com.lucaslearning.cursomc.domain.enums;

public enum Perfil {
	
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private Integer cod;
	private String descricao;

	private Perfil(Integer cod, String descricao) {
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
		public static Perfil toEnum(Integer cod) {
			
			if(cod == null) {
				return null;
			}
			
			//percorrer todos valores possiveis do meu tipo enumerado
			for (Perfil x: Perfil.values()) {
				if (cod.equals(x.getCod())) {
					return x;
				}
			}
			
			throw new IllegalArgumentException("Id inválido: " + cod);
		}
}
