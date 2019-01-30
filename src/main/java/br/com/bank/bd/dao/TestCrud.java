package br.com.bank.bd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.bank.bd.Conexao;
import br.com.bank.bo.Login;

public class TestCrud {
	
	protected PreparedStatement pstmt;
	protected ResultSet result;
	private Connection connection = Conexao.conectaSingletonEnum();
	private String query;
	
	public TestCrud() { }
	
	public void showTables() {
		query = "SELECT * FROM pg_catalog.pg_tables WHERE schemaname = 'public'";
		int cont = 0;
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			
			while(result.next()) {
				System.out.println("tabela: "+result.getString("tablename"));
				cont++;
			}
			System.out.println("\nTOTAL: "+cont+" tabelas ");
		} 
		catch (SQLException e) 	{ e.printStackTrace(); }
		finally 				{ Conexao.closeBD(result,pstmt, connection); }
	}
	
	public void insertLogin(Login login) {
		
		query = "INSERT INTO \"Login\"(usuario, senha, email) VALUES (?,?,?)";
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, login.getUsuario());
			pstmt.setString(2, login.getSenha());
			pstmt.setString(3, login.getEmail());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexao.closeBD(result, pstmt, connection);
		}
		
	}
	
	public void updateLogin(Login login) {
		query = "UPDATE \"Login\" "
				+ "SET usuario = ?, senha = ?, email = ? "
				+ "WHERE \"idLogin\" = ?";
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, login.getUsuario());
			pstmt.setString(2, login.getSenha());
			pstmt.setString(3, login.getEmail());
			pstmt.setLong(4, login.getIdLogin());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexao.closeBD(result, pstmt, connection);
		}
		
	}
	
	public void removerLogin(long id) {
		query = "DELETE FROM \"Login\" WHERE \"idLogin\" = "+id;
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexao.closeBD(result, pstmt, connection);
		}
			
	}
	
	/**
	 * @author 	Gilton Nascimento.
	 * @method 	findLoginBySTR 
	 * @since 	24/01/2019 - 10:18:49
	 * @param 	str
	 * @return  Login
	 */
	public Login findLoginBySTR(String str) {
		Login login = null;
		query = "SELECT * FROM \"Login\" WHERE usuario like '%"+str+"%' "
				+ "OR senha like '%"+str+"%' OR email like '%"+str+"%'";
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			while (result.next()) {
				long id = result.getLong("idLogin");
				String user = result.getString("usuario");
				String senha = result.getString("senha");
				String email = result.getString("email");
				login = new Login(id, user, senha, email);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexao.closeBD(result, pstmt, connection);
		}
		return login;
	}
	
	public List<? extends Object> getAll() {
		
		query = "SELECT * FROM \"Login\"";
		List<Login> list = new ArrayList<Login>();
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			
			while (result.next()) {
				long id = result.getLong("idLogin");
				String user = result.getString("usuario");
				String senha = result.getString("senha");
				String email = result.getString("email");
				list.add( new Login(id, user, senha, email) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			Conexao.closeBD(result, pstmt, connection); 
		}
		return list;
	}
	
	
	protected Connection grantAccess2Connection() {
		try {
			if ( connection.isClosed() )
				return connection = Conexao.conectaSingletonEnum();
			else 
				return connection;
		} catch (SQLException e) { 
			e.printStackTrace(); 
		}
		return connection; 
	}
	
	
	
}