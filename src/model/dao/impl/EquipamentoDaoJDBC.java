package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.EquipamentoDao;
import model.entities.Equipamento;

public class EquipamentoDaoJDBC implements EquipamentoDao {
	
	private Connection conn;
	
	public EquipamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Equipamento obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO equipamento " +
				"(nome,diaria,semanal,quinzenal,mensal,descricao) " +
				"VALUES " +
				"(?,?,?,?,?,?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setDouble(2, obj.getDiaria());
			st.setDouble(3, obj.getSemanal());
			st.setDouble(4, obj.getQuinzenal());
			st.setDouble(5, obj.getMensal());
			st.setString(6, obj.getDescricao());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Equipamento obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE equipamento " +
				"SET nome = ?, " +
				"diaria = ?, "
				+ "semanal = ?, "
				+ "quinzenal = ?, "
				+ "mensal = ?, "
				+ "descricao = ? " +
				"WHERE idequipamento = ?");

			st.setString(1, obj.getNome());
			st.setDouble(2, obj.getDiaria());
			st.setDouble(3, obj.getSemanal());
			st.setDouble(4, obj.getQuinzenal());
			st.setDouble(5, obj.getMensal());
			st.setString(6, obj.getDescricao());
			st.setInt(7, obj.getId());

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM department WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
}

		
	@Override
	public Equipamento findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM equipamento WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Equipamento obj = new Equipamento();
				obj.setId(rs.getInt("Id"));
				obj.setNome((rs.getString("nome")));
				obj.setDiaria((rs.getDouble(("diaria"))));
				obj.setSemanal((rs.getDouble(("semanal"))));
				obj.setQuinzenal((rs.getDouble(("quinzenal"))));
				obj.setMensal((rs.getDouble(("mensal"))));
				obj.setDescricao((rs.getString("descricao")));
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}


	@Override
	public List<Equipamento> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM equipamento ORDER BY Name");
			rs = st.executeQuery();

			List<Equipamento> list = new ArrayList<>();

			while (rs.next()) {
				Equipamento obj = new Equipamento();
				obj.setId(rs.getInt("Id"));
				obj.setNome((rs.getString("nome")));
				obj.setDiaria((rs.getDouble(("diaria"))));
				obj.setSemanal((rs.getDouble(("semanal"))));
				obj.setQuinzenal((rs.getDouble(("quinzenal"))));
				obj.setMensal((rs.getDouble(("mensal"))));
				obj.setDescricao((rs.getString("descricao")));
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

}
