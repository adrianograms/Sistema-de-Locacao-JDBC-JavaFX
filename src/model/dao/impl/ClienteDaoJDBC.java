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
import model.dao.ClienteDao;
import model.dao.EnderecoDao;
import model.entities.Cliente;
import model.entities.Endereco;

public class ClienteDaoJDBC implements ClienteDao {
	
	private Connection conn;
	
	private EnderecoDao endDao;
	
	public ClienteDaoJDBC(Connection conn) {
		this.conn = conn;
		endDao = new EnderecoDaoJDBC(conn);
	}

	@Override
	public void insert(Cliente obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO cliente " +
				"(nome,sexo,email,cpf,telefone) " +
				"VALUES " +
				"(?,?,?,?,?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setString(2, obj.getSexo().toString());
			st.setString(3, obj.getEmail());
			st.setString(4, obj.getCpf());
			st.setString(5, obj.getTelefone());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				endDao.insertAll(obj);
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
	public void update(Cliente obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE cliente "
					+ "SET nome = ?, sexo = ?, email = ?, cpf = ?, telefone = ? "
					+ "WHERE idcliente = ?");

			st.setString(1, obj.getNome());
			st.setString(2, obj.getSexo().toString());
			st.setString(3, obj.getEmail());
			st.setString(4, obj.getCpf());
			st.setString(5, obj.getTelefone());
			
			st.setInt(6, obj.getId());

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
			st = conn.prepareStatement("DELETE FROM cliente WHERE idcliente = ?");

			st.setInt(1, id);
			
			endDao.deleteAll(id);

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
	public Cliente findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM cliente WHERE idcliente = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Cliente obj = new Cliente();
				obj.setId(rs.getInt("idcliente"));
				obj.setNome(rs.getString("nome"));
				obj.setEmail(rs.getString("email"));
				obj.setCpf(rs.getString("cpf"));
				obj.setTelefone(rs.getString("telefone"));
				obj.setSexo(rs.getString("sexo").charAt(0));
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
	public List<Cliente> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM cliente ORDER BY nome");
			rs = st.executeQuery();

			List<Cliente> list = new ArrayList<>();

			while (rs.next()) {
				Cliente obj = new Cliente();
				obj.setId(rs.getInt("idcliente"));
				obj.setNome(rs.getString("nome"));
				obj.setEmail(rs.getString("email"));
				obj.setCpf(rs.getString("cpf"));
				obj.setTelefone(rs.getString("telefone"));
				obj.setSexo(rs.getString("sexo").charAt(0));
				obj.setEnderecos(endDao.findByIdCliente(obj.getId()));
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
