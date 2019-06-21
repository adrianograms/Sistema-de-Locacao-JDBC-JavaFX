package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;

public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private Character sexo;
	private String email;
	private String cpf;
	private String telefone;
	private List<Endereco> enderecos;
	
	public Cliente() {
	}
	
	
	
	public Cliente(Integer id, String nome, Character sexo, String email, String cpf, String telefone) {
		this.id = id;
		this.nome = nome;
		this.sexo = sexo;
		this.email = email;
		this.cpf = cpf;
		this.telefone = telefone;
	}


	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Character getSexo() {
		return sexo;
	}
	
	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Endereco endereco) {
		if(enderecos == null)
			enderecos = new ArrayList<Endereco>();
		enderecos.add(endereco);
	}
	
	public void setEnderecos(ObservableList<Endereco> endereco) {
		if(enderecos == null)
			enderecos = new ArrayList<Endereco>();
		enderecos.addAll(endereco);
	}
	
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", sexo=" + sexo + ", email=" + email + ", cpf=" + cpf
				+ ", telefone=" + telefone + "]";
	}
	

}
