package model.dao;

import db.DB;
import model.dao.impl.EquipamentoDaoJDBC;

public class DaoFactory {
	public static EquipamentoDao createEquipamentoDao() {
		return new EquipamentoDaoJDBC(DB.getConnection());
	}
}
