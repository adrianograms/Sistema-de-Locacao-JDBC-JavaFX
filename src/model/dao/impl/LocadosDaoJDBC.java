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
import model.dao.LocadosDao;
import model.entities.Equipamento;
import model.entities.Locacao;
import util.UtilLocacao;

public class LocadosDaoJDBC implements LocadosDao {
	
	private Connection conn = DB.getConnection();

	@Override
	public void insert(Equipamento equipamento, Locacao locacao) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO locados " +
				"(id_equipamento, id_locacao) " +
				"VALUES " +
				"(?, ?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, equipamento.getId());
			st.setInt(2, locacao.getId());

			int rowsAffected = st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteByIdequipamento(Integer idequipamento) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM locados WHERE id_equipamento = ?");

			st.setInt(1, idequipamento);

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
	public List<Equipamento> findAll(Locacao locacao, List<Equipamento> equipamentoLocados) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM locados "
				+ "WHERE id_locacao = ?");
			
			st.setInt(1, locacao.getId());
			
			rs = st.executeQuery();

			List<Equipamento> list = new ArrayList<>();

			while (rs.next()) {
				Equipamento equip = UtilLocacao.findingEquipamento(equipamentoLocados, rs.getInt("id_equipamento"));
				list.add(equip);
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

	@Override
	public void deleteAll(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM locados WHERE id_locacao = ?");

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
	public void insertAll(Locacao locacao) {
		PreparedStatement st = null;
		try {
			deleteAll(locacao.getId());
			for(Equipamento equipamento : locacao.getEquipamentos()) {
				st = conn.prepareStatement(
					"INSERT INTO locados " +
					"(id_equipamento, id_locacao) " +
					"VALUES " +
					"(?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
	
				st.setInt(1, equipamento.getId());
				st.setInt(2, locacao.getId());
	
				int rowsAffected = st.executeUpdate();
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

}
