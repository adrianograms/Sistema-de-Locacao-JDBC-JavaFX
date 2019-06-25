package model.services;

import java.util.List;

import db.DB;
import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.dao.EnderecoDao;
import model.dao.impl.EnderecoDaoJDBC;
import model.entities.Cliente;
import model.entities.Endereco;

public class EnderecoService {
	
private EnderecoDao dao = DaoFactory.createEnderecoDao();
	
	public void saveOrUpdate(Endereco end, Cliente cli) {
		if (end.getId() == null) {
			dao.insert(end,cli);
		}
		else {
			dao.update(end);
		}
	}
	
	public void remove(Endereco obj) {
		dao.deleteById(obj.getId());
	}


}
