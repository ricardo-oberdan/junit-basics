package com.oberdan.leilao.servico;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.oberdan.leilao.modelo.Lance;
import com.oberdan.leilao.modelo.Leilao;
import com.oberdan.leilao.modelo.Usuario;

public class AvaliadorTest {

	@Test
	public void testaMaiorEMenorLance() {
		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("Jose");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Produto a leiloar");

		leilao.propoe(new Lance(joao, 250.0));
		leilao.propoe(new Lance(maria, 400.0));
		leilao.propoe(new Lance(jose, 300.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		Double maiorEsperado = 400.0;
		Double menorEsperado = 250.0;

		assertEquals(maiorEsperado, leiloeiro.getMaiorLance());
		assertEquals(menorEsperado, leiloeiro.getMenorLance());
	}
	
	@Test
	public void testaLeilaoComLanceMedioZero() {
		Leilao leilao = new Leilao("Produto");
		Avaliador avaliador = new Avaliador();
		avaliador.avalia(leilao);
		
		assertEquals(new Double(0), avaliador.getLanceMedio());
	}
	
	@Test
	public void testaLeilaoComUmLance() {
		Usuario joao = new Usuario("Joao");
		Leilao leilao = new Leilao("Produto");
		leilao.propoe(new Lance(joao, 250.0));

		Avaliador avaliador = new Avaliador();
		avaliador.avalia(leilao);
		
		assertEquals(new Double(250), avaliador.getMaiorLance());
		assertEquals(new Double(250), avaliador.getMenorLance());
		
	}
	
	@Test
	public void testaOsTresMaioresLances() {
		Usuario joao = new Usuario("Joao");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Produto");

		leilao.propoe(new Lance(joao, 1000.0));
		leilao.propoe(new Lance(maria, 2000.0));
		leilao.propoe(new Lance(joao, 3000.0));
		leilao.propoe(new Lance(maria, 4000.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaioresLances();
		
		assertEquals(3, maiores.size());
		assertEquals(new Double(4000), maiores.get(0).getValor());
		assertEquals(new Double(3000), maiores.get(1).getValor());
		assertEquals(new Double(2000), maiores.get(2).getValor());
	}

}
