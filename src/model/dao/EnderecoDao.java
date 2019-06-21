package model.dao;

import java.util.List;

import model.entities.Cliente;
import model.entities.Endereco;

public interface EnderecoDao {
	void insertAll(Cliente cliente);
	void updateAll(Cliente cliente);
	void deleteById(Integer id);
	void deleteAll(Integer id);
	List<Endereco> findByIdCliente(Integer id);

}
