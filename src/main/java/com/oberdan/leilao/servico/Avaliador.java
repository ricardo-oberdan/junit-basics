package com.oberdan.leilao.servico;

import com.oberdan.leilao.modelo.Lance;
import com.oberdan.leilao.modelo.Leilao;

public class Avaliador {

	private Double maiorLance = Double.NEGATIVE_INFINITY;
	private Double menorLance = Double.POSITIVE_INFINITY;

	public void avalia(Leilao leilao) {

		for (Lance lance : leilao.getLances()) {

			if (lance.getValor() > maiorLance) {
				maiorLance = lance.getValor();
			}
			
			if (lance.getValor() < menorLance) {
				menorLance = lance.getValor();
			}
		}

	}

	public Double getMaiorLance() {
		return maiorLance;
	}

	public Double getMenorLance() {
		return menorLance;
	}

}
