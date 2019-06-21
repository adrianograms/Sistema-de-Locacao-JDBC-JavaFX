package model.dao;

import java.util.List;

import model.entities.Cliente;
import model.entities.Equipamento;
import model.entities.Locacao;

public interface LocacaoDao {
	
	void insert(Locacao obj);
	void update(Locacao obj);
	void deleteById(Integer id);
	Locacao findById(Integer id, List<Cliente> clientes, List<Equipamento> equipamentos);
	List<Locacao> findAll(List<Cliente> clientes, List<Equipamento> equipamentos);

}
