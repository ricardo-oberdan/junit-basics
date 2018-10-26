package com.oberdan.leilao.email;

import com.oberdan.leilao.modelo.Leilao;

public interface EnviadorDeEmail {

	void envia(Leilao leilao);

}
