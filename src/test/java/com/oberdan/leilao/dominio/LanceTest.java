package com.oberdan.leilao.dominio;

import org.junit.Test;

import com.oberdan.leilao.modelo.Lance;
import com.oberdan.leilao.modelo.Usuario;

public class LanceTest {

	@Test(expected = IllegalArgumentException.class)
	public void testaLanceValorZero() {
		new Lance(new Usuario("Usuario"), new Double(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testaLanceValorNegativo() {
		new Lance(new Usuario("Usuario"), new Double(-100));
	}

}
