package com.oberdan.leilao.servico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import com.oberdan.leilao.builder.CriadorDeLeilao;
import com.oberdan.leilao.dao.LeilaoDao;
import com.oberdan.leilao.email.EnviadorDeEmail;
import com.oberdan.leilao.modelo.Leilao;

public class EncerradorDeLeilaoTest {
	
	private EncerradorDeLeilao encerrador;
	private LeilaoDao daoMock;
	private EnviadorDeEmail carteiroMock;

	@Before
	public void setUp() {
		this.daoMock = mock(LeilaoDao.class);
		this.carteiroMock = mock(EnviadorDeEmail.class);
		this.encerrador = new EncerradorDeLeilao(daoMock, carteiroMock);
	}

	@Test
	public void deveEncerrarLeiloesQueComecaramUmaSemanaAntes() {
		Calendar antiga = Calendar.getInstance();
		antiga.set(1999,  1, 20);

		Leilao leilao1 = new CriadorDeLeilao()
				.para("Produto")
				.naData(antiga)
				.constroi();

		Leilao leilao2 = new CriadorDeLeilao()
				.para("Outro Produto")
				.naData(antiga)
				.constroi();
		
		List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);

		when(daoMock.correntes()).thenReturn(leiloesAntigos);
		
		encerrador.encerra();

		assertEquals(2, encerrador.getTotalEncerrados());
		assertTrue(leilao1.isEncerrado());
		assertTrue(leilao2.isEncerrado());
	}

	@Test
	public void deveEncerrarApenas1DosLeiloes() {
		Calendar antiga = Calendar.getInstance();
		antiga.set(1999,  1, 20);

		Leilao leilao1 = new CriadorDeLeilao()
				.para("Produto")
				.naData(antiga)
				.constroi();

		Leilao leilao2 = new CriadorDeLeilao()
				.para("Outro Produto")
				.naData(Calendar.getInstance())
				.constroi();
		
		List<Leilao> leiloes = Arrays.asList(leilao1, leilao2);
		
		when(daoMock.correntes()).thenReturn(leiloes);

		encerrador.encerra();

		assertEquals(1, encerrador.getTotalEncerrados());
		assertTrue(leilao1.isEncerrado());
		assertFalse(leilao2.isEncerrado());
	}

	
	@Test
	public void naoHaLeilaoParaEncerrar() {
		LeilaoDao daoMock = mock(LeilaoDao.class);
		
		when(daoMock.correntes()).thenReturn(new ArrayList<Leilao>());

		encerrador.encerra();

		assertEquals(0, encerrador.getTotalEncerrados());
	}
	
	@Test
	public void deveAtualizarLeiloesEncerrados() {
		Calendar antiga = Calendar.getInstance();
		antiga.set(1999,  1, 20);

		Leilao leilao = new CriadorDeLeilao()
				.para("Produto")
				.naData(antiga)
				.constroi();
		
		when(daoMock.correntes()).thenReturn(Arrays.asList(leilao));

		encerrador.encerra();
		
		//O metodo encerra chama o atualizado do dao
		//Agora tenho que verificar se esse metodo foi realmente invocado
		//E o metodo deve ter sido invocado apenas uma vez
		verify(daoMock, times(1)).atualiza(leilao);

		assertEquals(1, encerrador.getTotalEncerrados());
		assertTrue(leilao.isEncerrado());
	}

	@Test
	public void naoDeveEncerrarLeiloesQueComecaramHaMenosDeUmaSemana() {
		Leilao leilao1 = new CriadorDeLeilao()
				.para("Produto")
				.naData(Calendar.getInstance())
				.constroi();

		Leilao leilao2 = new CriadorDeLeilao()
				.para("Outro Produto")
				.naData(Calendar.getInstance())
				.constroi();
		
		List<Leilao> leiloes = Arrays.asList(leilao1, leilao2);
		
		when(daoMock.correntes()).thenReturn(leiloes);

		encerrador.encerra();

		verify(daoMock, never()).atualiza(leilao1);
		verify(daoMock, never()).atualiza(leilao2);

		assertEquals(0, encerrador.getTotalEncerrados());
		assertFalse(leilao1.isEncerrado());
		assertFalse(leilao2.isEncerrado());
	}
	
	@Test
	public void deveEncerrarLeilaoEEnviarEmailNestaOrdem() {
		Calendar antiga = Calendar.getInstance();
		antiga.set(1999,  1, 20);

		Leilao leilao = new CriadorDeLeilao()
				.para("Produto")
				.naData(antiga)
				.constroi();

		Leilao leilaoNovo = new CriadorDeLeilao()
				.para("Produto")
				.naData(Calendar.getInstance())
				.constroi();
		
		
		when(daoMock.correntes()).thenReturn(Arrays.asList(leilao, leilaoNovo));

		encerrador.encerra();

		//Vendo se os metodos foram executados na sequencia correta
		//E também verificando se foram executados apenas 1 vez
        InOrder inOrder = inOrder(daoMock, carteiroMock);
        inOrder.verify(daoMock, times(1)).atualiza(leilao);
        inOrder.verify(carteiroMock, times(1)).envia(leilao);
		
        assertEquals(1, encerrador.getTotalEncerrados());
		assertTrue(leilao.isEncerrado());
		assertFalse(leilaoNovo.isEncerrado());
	}
	
	@Test
	public void deveContinuarEncerramentoQuandoDaoFalha() {
		Calendar antiga = Calendar.getInstance();
		antiga.set(1999,  1, 20);

		Leilao leilao1 = new CriadorDeLeilao()
				.para("Produto")
				.naData(antiga)
				.constroi();

		Leilao leilao2 = new CriadorDeLeilao()
				.para("Outro Produto")
				.naData(antiga)
				.constroi();

		when(daoMock.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));

		//Simulando a excecao do dao quando executa o encerramento do leilao1
		doThrow(new RuntimeException()).when(daoMock).atualiza(leilao1);

		encerrador.encerra();

		//Tenho que garantir que 1 primeiro falhou, mas o segundo não
		verify(daoMock).atualiza(leilao2);
		verify(carteiroMock).envia(leilao2);

		//Tambem testar se o envio de email não aconteceu para quem falhou.
		verify(carteiroMock, never()).envia(leilao1);

		//Ou não importa qual falhou, mas um tem que falhar
		//verify(carteiroMock, never()).envia(any(Leilao.class));
	}

}
