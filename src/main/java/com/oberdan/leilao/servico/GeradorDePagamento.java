package com.oberdan.leilao.servico;

import java.util.Calendar;
import java.util.List;

import com.oberdan.leilao.dao.LeilaoDao;
import com.oberdan.leilao.dao.PagamentoDao;
import com.oberdan.leilao.modelo.Leilao;
import com.oberdan.leilao.modelo.Pagamento;
import com.oberdan.leilao.relogio.Relogio;
import com.oberdan.leilao.relogio.RelogioDoSistema;

public class GeradorDePagamento {

	private LeilaoDao leilaoDao;
	private Avaliador avaliador;
	private PagamentoDao pagamentoDao;
	private Relogio relogio;

	public GeradorDePagamento(LeilaoDao leilaoDao, PagamentoDao pagamentoDao, Avaliador avaliador, Relogio relogio) {
		this.leilaoDao = leilaoDao;
		this.pagamentoDao = pagamentoDao;
		this.avaliador = avaliador;
		this.relogio = relogio;
	}

	public GeradorDePagamento(LeilaoDao leilaoDao, PagamentoDao pagamentoDao, Avaliador avaliador) {
		this(leilaoDao, pagamentoDao, avaliador, new RelogioDoSistema());
	}

	public void gera() {
		List<Leilao> leiloesEncerrados = this.leilaoDao.encerrados();

		for (Leilao leilao : leiloesEncerrados) {
			this.avaliador.avalia(leilao);

			Pagamento novoPagamento = new Pagamento(avaliador.getMaiorLance(), proximoDiaUtil()); 
			this.pagamentoDao.salva(novoPagamento);
		}
	}

	private Calendar proximoDiaUtil() {
		Calendar data = relogio.hoje();
		int dia = data.get(Calendar.DAY_OF_WEEK);

		if (dia == Calendar.SATURDAY)
			data.add(Calendar.DAY_OF_MONTH, 2);

		if (dia == Calendar.SUNDAY)
			data.add(Calendar.DAY_OF_MONTH, 1);

		return data;
	}
}
