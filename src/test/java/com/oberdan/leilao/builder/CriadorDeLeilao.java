package com.oberdan.leilao.builder;

import com.oberdan.leilao.modelo.Lance;
import com.oberdan.leilao.modelo.Leilao;
import com.oberdan.leilao.modelo.Usuario;

public class CriadorDeLeilao {
	
	private Leilao leilao;
	
	public CriadorDeLeilao() {	
	}

	public CriadorDeLeilao para(String produto) {
		this.leilao = new Leilao(produto);
		return this;
	}

	public CriadorDeLeilao lance(Usuario usuario, Double valorLance) {
		leilao.propoe(new Lance(usuario, valorLance));
		return this;
	}

	public Leilao constroi() {
		return this.leilao;
	}

}
