package com.oberdan.leilao.modelo;

public class Lance {

	private Usuario usuario;
	private Double valor;
	
	public Lance(Usuario usuario, double valor) {
		this.usuario = usuario;
		this.valor = valor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Double getValor() {
		return valor;
	}
	
	
	
}
