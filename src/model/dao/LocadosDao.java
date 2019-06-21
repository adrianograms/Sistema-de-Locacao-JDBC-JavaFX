package model.dao;

import java.util.List;

import model.entities.Equipamento;
import model.entities.Locacao;

public interface LocadosDao {
	void insert(Equipamento equipamento, Locacao locacao);
	void insertAll(Locacao locacao);
	void deleteByIdequipamento(Integer idequipamento);
	void deleteAll(Integer id);
	List<Equipamento> findAll(Locacao locacao, List<Equipamento> equipamentoLocados);
}
