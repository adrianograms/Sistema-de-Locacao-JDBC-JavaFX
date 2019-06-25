package model.dao;

import db.DB;
import model.dao.impl.ClienteDaoJDBC;
import model.dao.impl.EnderecoDaoJDBC;
import model.dao.impl.EquipamentoDaoJDBC;
import model.dao.impl.LocacaoDaoJDBC;

public class DaoFactory {
	public static EquipamentoDao createEquipamentoDao() {
		return new EquipamentoDaoJDBC(DB.getConnection());
	}
	public static ClienteDao createClienteDao() {
		return new ClienteDaoJDBC(DB.getConnection());
	}
	public static LocacaoDao createLocacaoDao() {
		return new LocacaoDaoJDBC(DB.getConnection());
	}
	public static EnderecoDao createEnderecoDao() {
		return new EnderecoDaoJDBC(DB.getConnection());
	}
}
