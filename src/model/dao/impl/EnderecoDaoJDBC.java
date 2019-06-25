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
import model.dao.EnderecoDao;
import model.entities.Cliente;
import model.entities.Endereco;

public class EnderecoDaoJDBC implements EnderecoDao {
	
	private Connection conn;
	
	public EnderecoDaoJDBC() {
	}
	
	public EnderecoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insertAll(Cliente cliente) {
		PreparedStatement st = null;
		try {
			for(int i =0; i< cliente.getEnderecos().size(); i++) {
				Endereco obj = cliente.getEnderecos().get(i);
				st = conn.prepareStatement(
					"INSERT INTO endereco " +
					"(nome, numero, rua, cep, bairro, cidade, uf, id_cliente) " +
					"VALUES " +
					"(?,?,?,?,?,?,?,?)", 
					Statement.RETURN_GENERATED_KEYS);
	
				st.setString(1, obj.getNome());
				st.setString(2, obj.getNumero());
				st.setString(3, obj.getRua());
				st.setString(4, obj.getCep());
				st.setString(5, obj.getBairro());
				st.setString(6, obj.getCidade());
				st.setString(7, obj.getEstado());
				st.setInt(8, cliente.getId());
	
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
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}

}

	@Override
	public void updateAll(Cliente cliente) {
		PreparedStatement st = null;
		try {
			for(int i =0; i< cliente.getEnderecos().size(); i++) {
				Endereco obj = cliente.getEnderecos().get(i);
				st = conn.prepareStatement(
					"UPDATE endereco " +
					"SET nome = ?, numero = ?, rua = ?, cep = ?, bairro = ? cidade = ?, uf = ? " +
					"WHERE idendereco = ?");
	
				st.setString(1, obj.getNome());
				st.setString(2, obj.getNumero());
				st.setString(3, obj.getRua());
				st.setString(4, obj.getCep());
				st.setString(5, obj.getBairro());
				st.setString(6, obj.getCidade());
				st.setString(7, obj.getEstado());
	
				st.executeUpdate();
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
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM endereco WHERE idendereco = ?");

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
	public List<Endereco> findByIdCliente(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM endereco "
				+ "WHERE id_cliente = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();

			List<Endereco> list = new ArrayList<>();

			while (rs.next()) {
				Endereco obj = new Endereco();
				obj.setId(rs.getInt("idendereco"));
				obj.setNome(rs.getString("nome"));
				obj.setNumero(rs.getString("numero"));
				obj.setRua(rs.getString("rua"));
				obj.setCep(rs.getString("cep"));
				obj.setBairro(rs.getString("bairro"));
				obj.setCidade(rs.getString("cidade"));
				obj.setEstado(rs.getString("uf"));
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

	@Override
	public void deleteAll(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM endereco WHERE id_cliente = ?");

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
	public void insert(Endereco obj, Cliente cli) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO endereco " +
							"(nome, numero, rua, cep, bairro, cidade, uf, id_cliente) " +
							"VALUES " +
							"(?,?,?,?,?,?,?,?)", 
							Statement.RETURN_GENERATED_KEYS); 

			st.setString(1, obj.getNome());
			st.setString(2, obj.getNumero());
			st.setString(3, obj.getRua());
			st.setString(4, obj.getCep());
			st.setString(5, obj.getBairro());
			st.setString(6, obj.getCidade());
			st.setString(7, obj.getEstado());
			st.setInt(8, cli.getId());

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
	public void update(Endereco obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE endereco " +
					"SET nome = ?, numero = ?, rua = ?, cep = ?, bairro = ?, cidade = ?, uf = ? " +
					"WHERE idendereco = ?");
	
				st.setString(1, obj.getNome());
				st.setString(2, obj.getNumero());
				st.setString(3, obj.getRua());
				st.setString(4, obj.getCep());
				st.setString(5, obj.getBairro());
				st.setString(6, obj.getCidade());
				st.setString(7, obj.getEstado());
				
				st.setInt(8, obj.getId());
	
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
	public void delete(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM endereco WHERE idendereco = ?");

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

}
