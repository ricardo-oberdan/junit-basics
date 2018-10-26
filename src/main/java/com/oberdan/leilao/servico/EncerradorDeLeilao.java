package com.oberdan.leilao.servico;

import java.util.Calendar;
import java.util.List;

import com.oberdan.leilao.dao.LeilaoDao;
import com.oberdan.leilao.email.EnviadorDeEmail;
import com.oberdan.leilao.modelo.Leilao;

public class EncerradorDeLeilao {
	private int total = 0;
	private LeilaoDao dao;
	private EnviadorDeEmail carteiro;

	public EncerradorDeLeilao(LeilaoDao dao, EnviadorDeEmail carteiro) {
		this.dao = dao;
		this.carteiro = carteiro;
	}

	public void encerra() {
		List<Leilao> todosLeiloesCorrentes = dao.correntes();

		for (Leilao leilao : todosLeiloesCorrentes) {
			try {
				if (comecouSemanaPassada(leilao)) {
					leilao.encerra();
					total++;
					dao.atualiza(leilao);
					carteiro.envia(leilao);
				}
			} catch (Exception e) {
				// TODO: loga excecao e continua processamento dos demais leiloes
			}
		}
	}

	private boolean comecouSemanaPassada(Leilao leilao) {
		return diasEntre(leilao.getData(), Calendar.getInstance()) >= 7;
	}

	private int diasEntre(Calendar inicio, Calendar fim) {
		Calendar data = (Calendar) inicio.clone();
		int diasNoIntervalo = 0;
		while (data.before(fim)) {
			data.add(Calendar.DAY_OF_MONTH, 1);
			diasNoIntervalo++;
		}

		return diasNoIntervalo;
	}

	public int getTotalEncerrados() {
		return total;
	}
}
