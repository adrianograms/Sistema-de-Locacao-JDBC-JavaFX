package model.entities;

import java.io.Serializable;

import gui.MainViewController;

public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String cep;
	private String bairro;
	private String numero;
	private String rua;
	private String cidade;
	private String estado;
	private String nome;
	
	public Endereco() {
	}
	
	public Endereco(Endereco adr) {
		this.nome = adr.getNome();		
		this.id = adr.getId();
		this.cep = adr.getCep();
		this.bairro = adr.getBairro();
		this.numero = adr.getNumero();
		this.rua = adr.getRua();
		this.cidade = adr.getCidade();
		this.estado = adr.getEstado();
	}

	public Endereco(Integer id, String cep, String bairro, String numero, String rua, String cidade, String estado, String nome) {
		
		this.nome = nome;		
		this.id = id;
		this.cep = cep;
		this.bairro = bairro;
		this.numero = numero;
		this.rua = rua;
		this.cidade = cidade;
		this.estado = estado;		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Endereco other = (Endereco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Endereco [id=" + id + ", cep=" + cep + ", bairro=" + bairro + ", numero=" + numero + ", rua=" + rua
				+ ", cidade=" + cidade + ", estado=" + estado + "]";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {	
		this.nome = nome;		
	}
	
	
	
	
}
