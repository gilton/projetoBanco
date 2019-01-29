package br.com.bank.bd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.bank.bd.Conexao;

public class CrudDao {

	protected PreparedStatement pstmt;
	protected ResultSet result;
	protected Connection connection = Conexao.conectaSingletonEnum();
	protected String query;
	protected String tabela = "";
	protected String codigo = "";

	public CrudDao() { super(); }

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
	
	public void commitRollBack() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			System.err.println("Error ao fazer RollBack: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String estarAbertoBD() {
		try 					{ 
			if( connection.isClosed() ) return "FECHADO.";
			else 						return "ABERTO.";
		} 
		catch (SQLException e)	{ e.printStackTrace(); }
		return "";
	}
	
	
	public final String getQuery() {
		return query;
	}

	public final void setQuery(String query) {
		this.query = query;
	}

	public void excluir(long id) {
		query = "DELETE FROM "+tabela+" WHERE "+codigo+" = "+id;
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			pstmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			commitRollBack();
		} finally {
			Conexao.closeBD(result, pstmt, connection);
		}
	}
	
}