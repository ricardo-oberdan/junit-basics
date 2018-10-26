package com.oberdan.leilao.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AnoBissextoTest {
	
	@Test
	public void naoEhBissexto() {
		assertFalse(AnoBissexto.ehBissexto(1995));
		assertFalse(AnoBissexto.ehBissexto(2018));
		assertFalse(AnoBissexto.ehBissexto(2019));
	}
	
	@Test
	public void ehBissexto() {
		assertTrue(AnoBissexto.ehBissexto(2000));
		assertTrue(AnoBissexto.ehBissexto(2020));
		assertTrue(AnoBissexto.ehBissexto(2024));
	}
	
}
