package model.services;

import java.util.List;

import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.dao.EnderecoDao;
import model.dao.impl.EnderecoDaoJDBC;
import model.entities.Cliente;
import model.entities.Endereco;

public class EnderecoService {
	
private EnderecoDao dao = new EnderecoDaoJDBC();
	
	public void saveOrUpdate(Endereco end, Cliente cli) {
		if (end.getId() == null) {
			dao.insert(end,cli);
		}
		else {
			dao.update(end);
		}
	}
	
	public void remove(Cliente obj) {
		dao.deleteById(obj.getId());
	}


}
