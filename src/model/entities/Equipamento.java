package model.entities;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Equipamento implements Serializable {
	private static final long serialVersionUID = 1L;
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	
	private Integer id;
	private String nome;
	private Double diaria;
	private Double semanal;
	private Double quinzenal;
	private Double mensal;
	private String descricao;
	
	public Equipamento() {
	}
	
	public Equipamento(Integer id, String nome, Double diaria, Double semanal, Double quinzenal, Double mensal,
			String descricao) {
		this.id = id;
		this.nome = nome;
		setDiaria(diaria);
		setSemanal(semanal);
		setQuinzenal(quinzenal);
		setMensal(mensal);
		this.descricao = descricao;
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
	
	
	public Double getDiaria() {
		return diaria;
	}
	
	
	public void setDiaria(Double diaria) {
		this.diaria = diaria;
	}
	
	
	public Double getSemanal() {
		return semanal;
	}
	
	
	public void setSemanal(Double semanal) {
		this.semanal = semanal;
	}
	
	
	public Double getQuinzenal() {
		return quinzenal;
	}
	
	
	public void setQuinzenal(Double quinzenal) {
		this.quinzenal = quinzenal;
	}
	
	
	public Double getMensal() {
		return mensal;
	}
	
	
	public void setMensal(Double mensal) {
		this.mensal = mensal;
	}
	
	
	public String getDescricao() {
		return descricao;
	}
	
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		Equipamento other = (Equipamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Equipamento [id=" + id + ", nome=" + nome + ", diaria=" + diaria + ", Semanal=" + semanal
				+ ", Quinzenal=" + quinzenal + ", mensal=" + mensal + ", descricao=" + descricao + "]";
	}
	
	
	

}
