package com.oberdan.leilao.servico;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oberdan.leilao.builder.CriadorDeLeilao;
import com.oberdan.leilao.modelo.Leilao;
import com.oberdan.leilao.modelo.Usuario;

public class LeilaoTest {

	private Leilao leilao;
	private Usuario usuario;
	private Usuario outroUsuario;

	@Before
	public void criaUsuarios() {
		this.usuario = new Usuario("Usuario");
		this.outroUsuario = new Usuario("Outro Usuario");
	}

	@After
	public void finaliza() {
		System.out.println("Fim");
	}

	@Test
	public void deveReceberUmLance() {
		leilao = new CriadorDeLeilao()
				.para("Produto")
				.constroi();

		assertEquals(0, leilao.getLances().size());

		leilao = new CriadorDeLeilao()
				.para("Produto")
				.lance(usuario, 2000.10)
				.constroi();

		assertEquals(1, leilao.getLances().size());
		assertEquals(new Double(2000.1), leilao.getLances().get(0).getValor());
	}

	@Test
	public void deveReceberVariosLances() {
		leilao = new CriadorDeLeilao()
				.para("Produto")
				.constroi();

		assertEquals(0, leilao.getLances().size());

		leilao = new CriadorDeLeilao()
				.para("Produto")
				.lance(usuario, new Double(2000))
				.lance(outroUsuario, new Double(3000))
				.constroi();

		assertEquals(2, leilao.getLances().size());
		assertEquals(new Double(2000), leilao.getLances().get(0).getValor());
		assertEquals(new Double(3000), leilao.getLances().get(1).getValor());
	}

	@Test
	public void naoDeveAceitarDoisLancesSeguidosMesmoUsuario() {

		leilao = new CriadorDeLeilao()
				.para("Produto")
				.lance(usuario, new Double(2000))
				.lance(usuario, new Double(3000))
				.constroi();

		assertEquals(1, leilao.getLances().size());
		assertEquals(new Double(2000), leilao.getLances().get(0).getValor());
	}

	@Test
	public void naoDeveAceitarMaisQue5LancesDoMesmoUsuario() {
		leilao = new CriadorDeLeilao()
				.para("Produto")
				.lance(usuario, new Double(1000))
				.lance(outroUsuario, new Double(2000))
				.lance(usuario, new Double(3000))
				.lance(outroUsuario, new Double(4000))
				.lance(usuario, new Double(5000))
				.lance(outroUsuario, new Double(6000))
				.lance(usuario, new Double(7000))
				.lance(outroUsuario, new Double(8000))
				.lance(usuario, new Double(9000))
				.lance(outroUsuario, new Double(10000))
				.lance(usuario, new Double(11000)) //a ser ignorado
				.constroi();

		assertEquals(10, leilao.getLances().size());
		assertEquals(new Double(10000), leilao.getLances().get(9).getValor());

	}

	@Test
	public void dobraLance() {

		leilao = new CriadorDeLeilao()
				.para("Produto")
				.lance(usuario, new Double(2000))
				.lance(outroUsuario, new Double(3000))
				.constroi();

		leilao.dobraLance(usuario);

		assertEquals(3, leilao.getLances().size());
		assertEquals(new Double(4000), leilao.getLances().get(2).getValor());
	}

	@Test
	public void naoDeveDobrarCasoNaoHajaLanceAnterior() {
		leilao = new CriadorDeLeilao()
				.para("Produto")
				.constroi();		

		leilao.dobraLance(usuario);

		assertEquals(0, leilao.getLances().size());
	}

	@Test
	public void naoDeveDobrarLancePoisJaFezMais5Lances() {

		leilao = new CriadorDeLeilao()
				.para("Produto")
				.lance(usuario, new Double(1000))
				.lance(outroUsuario, new Double(2000))
				.lance(usuario, new Double(3000))
				.lance(outroUsuario, new Double(4000))
				.lance(usuario, new Double(5000))
				.lance(outroUsuario, new Double(6000))
				.lance(usuario, new Double(7000))
				.lance(outroUsuario, new Double(8000))
				.lance(usuario, new Double(9000))
				.lance(outroUsuario, new Double(10000))
				.constroi();

		// A ser ignorado
		leilao.dobraLance(usuario);

		assertEquals(10, leilao.getLances().size());
		assertEquals(new Double(10000), leilao.getLances().get(9).getValor());

	}

}
