package com.oberdan.leilao.servico;

import org.junit.Assert;
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

		Assert.assertEquals(maiorEsperado, leiloeiro.getMaiorLance());
		Assert.assertEquals(menorEsperado, leiloeiro.getMenorLance());
	}

}
