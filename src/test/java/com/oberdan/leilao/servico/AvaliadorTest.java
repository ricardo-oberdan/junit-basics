package com.oberdan.leilao.servico;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.oberdan.leilao.builder.CriadorDeLeilao;
import com.oberdan.leilao.modelo.Lance;
import com.oberdan.leilao.modelo.Leilao;
import com.oberdan.leilao.modelo.Usuario;

public class AvaliadorTest {

	private Avaliador leiloeiro;
	private Usuario maria;
	private Usuario jose;
	private Usuario joao;

	@BeforeClass
	public static void testandoBeforeClass() {
	  System.out.println("before class");
	}

	@AfterClass
	public static void testandoAfterClass() {
	  System.out.println("after class");
	}

	@Before
	public void criaAvaliador() {
		leiloeiro = new Avaliador();
	}

	@Before
	public void criaUsuarios() {
		this.joao = new Usuario("Joao");
		this.jose = new Usuario("Jose");
		this.maria = new Usuario("Maria");
	}

	@Test
	public void testaMaiorEMenorLance() {

		Leilao leilao = new CriadorDeLeilao()
				.para("Produto a leiloar")
				.lance(joao, new Double(250))
				.lance(maria, new Double(400))
				.lance(jose, new Double(300))
				.lance(maria, new Double(200))
				.lance(joao, new Double(250))
				.constroi();

		leiloeiro.avalia(leilao);

		Double maiorEsperado = 400.0;
		Double menorEsperado = 200.0;

		assertEquals(maiorEsperado, leiloeiro.getMaiorLance());
		assertEquals(menorEsperado, leiloeiro.getMenorLance());
	}

	@Test
	public void testaLeilaoComLanceMedioZero() {
		Leilao leilao = new Leilao("Produto");
		leiloeiro.avalia(leilao);

		assertEquals(new Double(0), leiloeiro.getLanceMedio());
		assertEquals(0, leiloeiro.getMaioresLances().size());
	}

	@Test
	public void testaLeilaoComUmLance() {
		Leilao leilao = new CriadorDeLeilao()
				.para("Produto a leiloar")
				.lance(joao, new Double(250))
				.constroi();

		leiloeiro.avalia(leilao);

		assertEquals(new Double(250), leiloeiro.getMaiorLance());
		assertEquals(new Double(250), leiloeiro.getMenorLance());
		assertEquals(1, leiloeiro.getMaioresLances().size());
	}

	@Test
	public void testaOsTresMaioresLances() {

		Leilao leilao = new CriadorDeLeilao()
				.para("Produto a leiloar")
				.lance(joao, new Double(1000))
				.lance(maria, new Double(2000))
				.lance(joao, new Double(3000))
				.lance(maria, new Double(4000))
				.constroi();

		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getMaioresLances();

		assertEquals(3, maiores.size());
		assertEquals(new Double(4000), maiores.get(0).getValor());
		assertEquals(new Double(3000), maiores.get(1).getValor());
		assertEquals(new Double(2000), maiores.get(2).getValor());
	}

}
