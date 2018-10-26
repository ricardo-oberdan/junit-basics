package com.oberdan.leilao.modelo;

import java.util.Calendar;

public class Pagamento {

	private Calendar data;
	private double valor;

	public Pagamento(double valor, Calendar data) {
		this.valor = valor;
		this.data = data;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
