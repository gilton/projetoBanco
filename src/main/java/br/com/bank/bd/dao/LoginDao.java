package br.com.bank.bd.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.bank.bd.Conexao;
import br.com.bank.bo.Login;

/**
 * @author 	Gilton Nascimento.
 * @since 	24/01/2019 - 10:50:29 
 * @version	0.1
 */

public class LoginDao extends CrudDao implements ICrudDao<Login> {
	
	public LoginDao() {
		tabela = "\"Login\"";
		codigo = "\"idLogin\"";
	}
	
	public long inserir(Login login)  {
		setQuery("INSERT INTO "+ tabela
				+" (usuario, senha, email) VALUES (?,?,?) RETURNING "+ codigo);
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(login);

			result = pstmt.executeQuery();
			connection.commit();
			
			if( result.next() ) return result.getInt(1);
			else				return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error ao inserir Login: "+e.getMessage());
			
			commitRollBack();
		} finally {
			Conexao.closeBD(result, pstmt, connection);
		}
		return 0;
	}

	public void alterar(Login login) {
		query = "UPDATE "+ tabela + " "
				+ "SET usuario = ?, senha = ?, email = ? "
				+ "WHERE "+codigo+" = ?";
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(login);
			pstmt.setLong(4, login.getIdLogin());
			
			pstmt.executeUpdate();
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			commitRollBack();
		} finally {
			Conexao.closeBD(result, pstmt, connection);
		}
		
	}

	public Login getPojoByID(long id) {
		query = "SELECT * FROM "+ tabela +" WHERE "+codigo+" = "+id;
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			while (result.next()) {
				long cod = result.getLong("idLogin");
				String user = result.getString("usuario");
				String senha = result.getString("senha");
				String email = result.getString("email");
				return new Login(cod, user, senha, email);
			}
			
		} catch(SQLException e) { e.printStackTrace(); } 
		finally					{ Conexao.closeBD(result, pstmt, connection); }
		
		return null;
	}
	
	public Login getPojoBySTR(String str) {
		query = "SELECT * FROM "+ tabela +" WHERE usuario like '%"+str+"%' "
				+ "OR senha like '%"+str+"%' OR email like '%"+str+"%'";
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			while (result.next()) {
				long cod = result.getLong("idLogin");
				String user = result.getString("usuario");
				String senha = result.getString("senha");
				String email = result.getString("email");
				return new Login(cod, user, senha, email);
			}
			
		} 
		catch (SQLException e)	{ e.printStackTrace(); } 
		finally 				{ Conexao.closeBD(result, pstmt, connection); }
		
		return null;
	}
	
	public List<Login> getALL() {
		query = "SELECT * FROM "+tabela+" ORDER BY "+codigo;
		List<Login> list = new ArrayList<Login>();
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			
			while (result.next()) {
				long cod = result.getLong("idLogin");
				String user = result.getString("usuario");
				String senha = result.getString("senha");
				String email = result.getString("email");
				list.add( new Login(cod, user, senha, email) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			Conexao.closeBD(result, pstmt, connection); 
		}
		return list;
	}
	
	
	//AUXLIARs
	private void setParameters(Login login) throws SQLException {
		pstmt.setString(1, login.getUsuario());
		pstmt.setString(2, login.getSenha());
		pstmt.setString(3, login.getEmail());
		
	}	
	
}