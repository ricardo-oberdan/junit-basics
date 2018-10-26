package com.oberdan.leilao.relogio;

import java.util.Calendar;

public class RelogioDoSistema implements Relogio{

	public Calendar hoje() {
		return Calendar.getInstance();
	}

}
