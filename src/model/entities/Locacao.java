package model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.ObservableList;

public class Locacao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private LocalDate data_inicio;
	private LocalDate data_final;
	private Double valor;
	private Endereco endereco;
	private Cliente cliente;
	private List<Equipamento> equipamentos;
	private String nomeCliente;
	private String nomeEndereco;
	
	public Locacao() {
	}

	public Locacao(Integer id, LocalDate data_inicio, LocalDate data_final, Endereco endereco, Cliente cliente) {
		this.id = id;
		this.data_inicio = data_inicio;
		this.data_final = data_final;
		this.endereco = endereco;
		this.cliente = cliente;
		this.setNomeCliente(cliente.getNome());
		this.setNomeEndereco(endereco.getRua() + ", Número: " + endereco.getNumero() +  " - " + endereco.getCidade());	
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getData_inicio() {
		return data_inicio;
	}

	public void setData_inicio(LocalDate data_inicio) {
		this.data_inicio = data_inicio;
	}

	public LocalDate getData_final() {
		return data_final;
	}

	public void setData_final(LocalDate data_final) {
		this.data_final = data_final;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(Equipamento equipamento) {
		if(equipamentos == null)
			equipamentos = new ArrayList<Equipamento>();
		this.equipamentos.add(equipamento);
	}
	public void setEquipamentos(ObservableList equipamento) {
		if(equipamentos == null)
			equipamentos = new ArrayList<Equipamento>();
		this.equipamentos.addAll(equipamento);
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
		Locacao other = (Locacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Locacao [id=" + id + ", data_inicio=" + data_inicio + ", data_final=" + data_final + ", valor=" + valor
				+ ", endereco=" + endereco + ", cliente=" + cliente + ", equipamentos=" + equipamentos + "]";
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNomeEndereco() {
		return nomeEndereco;
	}

	public void setNomeEndereco(String nomeEndereco) {
		this.nomeEndereco = nomeEndereco;
	}
	
	
	

}
