package com.oberdan.leilao.modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Leilao {
	
	private Integer id;
	private String descricao;
	private Calendar data;
	private boolean encerrado;
	private List<Lance> lances;

	public Leilao(String descricao) {
		this(descricao, Calendar.getInstance());
	}
	
	public Leilao(String descricao, Calendar data) {
		this.descricao = descricao;
		this.data = data;
		this.lances = new ArrayList<Lance>();
	}

	public void propoe(Lance lance) {

		// Usuario não pode dar dois lances seguidos
		// Tambem nao podera fazer mais de 5 lances no mesmo leilao
		if (lances.isEmpty() || podeDarLance(lance.getUsuario())) {
			lances.add(lance);
		}
	}

	private boolean podeDarLance(Usuario usuario) {
		return !ultimoLanceDado().getUsuario().equals(usuario) && qtdLancesDo(usuario) < 5;
	}

	private int qtdLancesDo(Usuario usuario) {
		int numeroLances = 0;

		for (Lance ln : lances) {
			if (usuario.equals(ln.getUsuario())) {
				numeroLances++;
			}
		}
		return numeroLances;
	}

	private Lance ultimoLanceDado() {
		return lances.get(lances.size() - 1);
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public Calendar getData() {
		return data;
	}

	public List<Lance> getLances() {
		return lances;
	}
	
	public void encerra() {
		this.encerrado = true;
	}
	
	public boolean isEncerrado() {
		return encerrado;
	}

	public void dobraLance(Usuario usuario) {
		Lance ultimoLance = ultimoLanceDo(usuario);
		
		if (ultimoLance != null)
			propoe(new Lance(usuario, ultimoLance.getValor()*2));
	}

	private Lance ultimoLanceDo(Usuario usuario) {
		Lance ultimo = null;

		for (Lance lance : lances) {
			if (lance.getUsuario().equals(usuario))
				ultimo = lance;
		}
		return ultimo;
	}

}
