package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.LocacaoDao;
import model.dao.LocadosDao;
import model.entities.Cliente;
import model.entities.Endereco;
import model.entities.Equipamento;
import model.entities.Locacao;
import util.UtilLocacao;

public class LocacaoDaoJDBC implements LocacaoDao {
	
	private Connection conn;
	
	private LocadosDao loc = new LocadosDaoJDBC();
	
	public LocacaoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Locacao obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO locacao " +
				"(data_inicio, data_final, valor, id_endereco, id_cliente) " +
				"VALUES " +
				"(?,?,?,?,?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setDate(1, Date.valueOf(obj.getData_inicio()));
			st.setDate(2, Date.valueOf(obj.getData_final()));
			st.setDouble(3, obj.getValor());
			st.setInt(4, obj.getEndereco().getId());
			st.setInt(5, obj.getCliente().getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
					loc.insertAll(obj);
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
	public void update(Locacao obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE locacao " +
				"SET data_inicio = ?, data_final = ?, valor= ?, id_endereco = ?, id_cliente = ? " +
				"WHERE idlocacao = ?");

			st.setDate(1, Date.valueOf(obj.getData_inicio()));
			st.setDate(2, Date.valueOf(obj.getData_final()));
			st.setDouble(3, obj.getValor());
			st.setInt(4, obj.getEndereco().getId());
			st.setInt(5, obj.getCliente().getId());
			st.setInt(6, obj.getId());
			
			loc.insertAll(obj);

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
				"DELETE FROM locacao WHERE idlocacao = ?");

			st.setInt(1, id);
			
			loc.deleteAll(id);

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
	public Locacao findById(Integer id, List<Cliente> clientes, List<Equipamento> equipamentos) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM locacao WHERE idlocacao = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Locacao obj = new Locacao();
				obj.setId(rs.getInt("idlocacao"));
				obj.setData_inicio(rs.getDate("data_inicio").toLocalDate());
				obj.setData_final(rs.getDate("data_final").toLocalDate());
				obj.setValor(rs.getDouble("valor"));
				
				Cliente cliente = UtilLocacao.findingCliente(clientes, rs.getInt("id_cliente"));
				obj.setCliente(cliente);
				
				Endereco endereco = UtilLocacao.findingEndereco(cliente, rs.getInt("id_endereco"));
				obj.setEndereco(endereco);
				
				obj.setEquipamentos(loc.findAll(obj, equipamentos));
				
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
	public List<Locacao> findAll(List<Cliente> clientes, List<Equipamento> equipamentos) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM locacao");
			rs = st.executeQuery();
			List<Locacao> list = new ArrayList<>();
			while (rs.next()) {
				Locacao obj = new Locacao();
				obj.setId(rs.getInt("idlocacao"));
				obj.setData_inicio(rs.getDate("data_inicio").toLocalDate());
				obj.setData_final(rs.getDate("data_final").toLocalDate());
				obj.setValor(rs.getDouble("valor"));
				
				Cliente cliente = UtilLocacao.findingCliente(clientes, rs.getInt("id_cliente"));
				obj.setCliente(cliente);
				
				Endereco endereco = UtilLocacao.findingEndereco(cliente, rs.getInt("id_endereco"));
				obj.setEndereco(endereco);
				
				obj.setEquipamentos(loc.findAll(obj, equipamentos));
				
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
