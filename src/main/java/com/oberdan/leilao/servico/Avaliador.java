package com.oberdan.leilao.servico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.oberdan.leilao.modelo.Lance;
import com.oberdan.leilao.modelo.Leilao;

public class Avaliador {

	private Double maiorLance = Double.NEGATIVE_INFINITY;
	private Double menorLance = Double.POSITIVE_INFINITY;
	private Double lanceMedio = new Double(0);
	private List<Lance> tresMaioresLances;

	public void avalia(Leilao leilao) {
		Double total = 0.0;

		for (Lance lance : leilao.getLances()) {

			total += lance.getValor();

			if (lance.getValor() > maiorLance) {
				maiorLance = lance.getValor();
			}

			if (lance.getValor() < menorLance) {
				menorLance = lance.getValor();
			}
		}

		tresMaioresLances = new ArrayList<Lance>(leilao.getLances());

		Collections.sort(tresMaioresLances, new Comparator<Lance>() {

			public int compare(Lance o1, Lance o2) {
				if (o1.getValor() < o2.getValor()) return 1;
				if (o1.getValor() > o2.getValor()) return -1;
				return 0;
			}

		});
		int tamanhoLista = tresMaioresLances.size() < 3 ? tresMaioresLances.size() : 3;
		
		tresMaioresLances = tresMaioresLances.subList(0, tamanhoLista);

		if (total == 0.0) {
			lanceMedio = 0.0;
			return;
		}

		lanceMedio = total / leilao.getLances().size();

	}

	public Double getMaiorLance() {
		return maiorLance;
	}

	public Double getMenorLance() {
		return menorLance;
	}
	
	public Double getLanceMedio() {
		return lanceMedio;
	}
	
	public List<Lance> getTresMaioresLances() {
		return tresMaioresLances;
	}

}
