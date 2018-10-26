package com.oberdan.leilao.modelo;

public class Lance {
	
	private Integer id;	
	private Usuario usuario;
	private Double valor;
	
	public Lance(Usuario usuario, Double valor) {
		if (valor <= 0) {
			throw new IllegalArgumentException("Lances devem ser maiores que zero");
		}
		
		this.usuario = usuario;
		this.valor = valor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Double getValor() {
		return valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lance other = (Lance) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lance [id=" + id + ", usuario=" + usuario + ", valor=" + valor + "]";
	}
	
}
