package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.LocacaoDao;
import model.entities.Cliente;
import model.entities.Equipamento;
import model.entities.Locacao;

public class LocacaoService {
	
private LocacaoDao dao = DaoFactory.createLocacaoDao();
	
	public List<Locacao> findAll(List<Cliente> clientes, List<Equipamento> equipamentos) {
		return dao.findAll(clientes, equipamentos);
	}
	
	public void saveOrUpdate(Locacao obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Locacao obj) {
		dao.deleteById(obj.getId());
	}

}
