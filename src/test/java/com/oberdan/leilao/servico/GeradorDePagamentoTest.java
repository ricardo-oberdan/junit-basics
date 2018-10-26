package com.oberdan.leilao.servico;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.oberdan.leilao.builder.CriadorDeLeilao;
import com.oberdan.leilao.dao.LeilaoDao;
import com.oberdan.leilao.dao.PagamentoDao;
import com.oberdan.leilao.modelo.Leilao;
import com.oberdan.leilao.modelo.Pagamento;
import com.oberdan.leilao.modelo.Usuario;
import com.oberdan.leilao.relogio.Relogio;

public class GeradorDePagamentoTest {
	
	private LeilaoDao leilaoDao;
	private PagamentoDao pgtoDao;
	private Avaliador avaliador;
	private GeradorDePagamento gerador;
	private Relogio relogio;

	@Before
	public void setUp() {
		leilaoDao = mock(LeilaoDao.class);
		pgtoDao = mock(PagamentoDao.class);

		//Preciso de uma instancia de avaliador para poder retornar o maior lance
		avaliador = new Avaliador();

		relogio = mock(Relogio.class);
		gerador = new GeradorDePagamento(leilaoDao, pgtoDao, avaliador, relogio);
	}
	
	@Test
	public void deveGerarPagamentoParaUmLeilaoEncerrado() {
		when(relogio.hoje()).thenReturn(Calendar.getInstance());
		
		Leilao leilao = new CriadorDeLeilao()
				.para("produto")
				.naData(relogio.hoje())
				.lance(new Usuario("Usuario"), 100.0)
				.lance(new Usuario("Outro Usuario"), 300.0)
				.constroi();

		when(leilaoDao.encerrados()).thenReturn(Arrays.asList(leilao));

		//Testando classe e metodo alvo
		gerador.gera();

		//Eu não retorno o pagamento gerado, entao tenho que capturá-lo atraves do mock do dao
		//Me permite acessar objetos criados pela classe testada, mas que não são devolvidos 
		ArgumentCaptor<Pagamento> capturado = ArgumentCaptor.forClass(Pagamento.class);

		//Pego no mock o objeto que ele recebeu
		verify(pgtoDao).salva(capturado.capture());
		Pagamento pgto = capturado.getValue();

		assertEquals(300.0, pgto.getValor(), 0.001);
	}
	
	@Test
	public void deveEmpurrarPagamentoNoSabadoParaProximoDiaUtil() {
		Calendar sabado = Calendar.getInstance();
		sabado.set(2018, Calendar.OCTOBER, 27);

		//Para poder testar classe que usa calendar.getinstance, temos que mockar também essa data
		//Agora ensino o mock a dizer que hoje é sabado
		when(relogio.hoje()).thenReturn(sabado);

		Leilao leilao = new CriadorDeLeilao()
				.para("produto")
				.lance(new Usuario("Usuario"), 100.0)
				.lance(new Usuario("Outro Usuario"), 300.0)
				.constroi();

		when(leilaoDao.encerrados()).thenReturn(Arrays.asList(leilao));

		gerador.gera();

		ArgumentCaptor<Pagamento> capturado = ArgumentCaptor.forClass(Pagamento.class);
		verify(pgtoDao).salva(capturado.capture());
		Pagamento pgto = capturado.getValue();

		assertEquals(Calendar.MONDAY, pgto.getData().get(Calendar.DAY_OF_WEEK));
	}

	@Test
	public void deveEmpurrarPagamentoNoDomingoParaProximoDiaUtil() {
		Calendar sabado = Calendar.getInstance();
		sabado.set(2018, Calendar.OCTOBER, 28);

		//Para poder testar classe que usa calendar.getinstance, temos que mockar também essa data
		//Agora ensino o mock a dizer que hoje é sabado
		when(relogio.hoje()).thenReturn(sabado);

		Leilao leilao = new CriadorDeLeilao()
				.para("produto")
				.lance(new Usuario("Usuario"), 100.0)
				.lance(new Usuario("Outro Usuario"), 300.0)
				.constroi();

		when(leilaoDao.encerrados()).thenReturn(Arrays.asList(leilao));

		gerador.gera();

		ArgumentCaptor<Pagamento> capturado = ArgumentCaptor.forClass(Pagamento.class);
		verify(pgtoDao).salva(capturado.capture());
		Pagamento pgto = capturado.getValue();

		assertEquals(Calendar.MONDAY, pgto.getData().get(Calendar.DAY_OF_WEEK));
	}

}
