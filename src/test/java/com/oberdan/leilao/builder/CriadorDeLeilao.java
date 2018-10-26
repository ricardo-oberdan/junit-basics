package com.oberdan.leilao.builder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.oberdan.leilao.modelo.Lance;
import com.oberdan.leilao.modelo.Leilao;
import com.oberdan.leilao.modelo.Usuario;

public class CriadorDeLeilao {
	
	private String produto;
	private Calendar data;
	private List<Lance> lances;
	private boolean encerrado;
	
	public CriadorDeLeilao() {
		this.data = Calendar.getInstance();
		lances = new ArrayList<Lance>();
	}

	public CriadorDeLeilao para(String produto) {
		this.produto = produto;
		return this;
	}

	public CriadorDeLeilao naData(Calendar data) {
		this.data = data;
		return this;
	}
	
	public CriadorDeLeilao lance(Usuario usuario, Double valorLance) {
		lances.add(new Lance(usuario, valorLance));
		return this;
	}

	public CriadorDeLeilao encerrado() {
		this.encerrado = true;
		return this;
	}	

	public Leilao constroi() {
		Leilao leilao = new Leilao(produto, data);

		for(Lance lanceDado : lances) 
			leilao.propoe(lanceDado);

		if(encerrado) leilao.encerra();

		return leilao;
	}

}
