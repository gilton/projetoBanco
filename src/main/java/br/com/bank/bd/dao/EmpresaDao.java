package br.com.bank.bd.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.bank.bd.Conexao;
import br.com.bank.bo.Empresa;

/**
 * @author 	Gilton Nascimento.
 * @since 	25/01/2019 - 01:38:36 
 * @version	0.1
 */
public class EmpresaDao extends CrudDao implements ICrudDao<Empresa> {

	public EmpresaDao() {
		tabela = "\"Empresa\"";
		codigo = "\"idEmpresa\"";
	}

	@Override
	public long inserir(Empresa empresa) {

		setQuery("INSERT INTO "+ tabela
				+" (fantasia, razao_social, cnpj) "
				+ "VALUES (?,?,?) RETURNING "+ codigo);
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(empresa);
			
			result = pstmt.executeQuery();
			connection.commit();
			
			if( result.next() ) return result.getInt(1);
			else				return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error ao inserir Empresa: "+e.getMessage());
			
			commitRollBack();
		} finally {
			Conexao.closeBD(result, pstmt, connection);
		}
		
		return 0;
	}

	@Override
	public void alterar(Empresa empresa) {
		query = "UPDATE "+ tabela +" "
				+ "SET fantasia = ?, razao_social = ?, cnpj = ? "
				+ "WHERE "+codigo+" = ?";
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(empresa);
			pstmt.setLong(4, empresa.getIdEmpresa());
			
			pstmt.executeUpdate();
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			commitRollBack();
		} finally {
			Conexao.closeBD(result, pstmt, connection);
		}
	}

	@Override
	public Empresa getPojoByID(long id) {
		
		query = "SELECT * FROM "+ tabela +" WHERE "+codigo+" = "+id;
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			while (result.next()) {
				long cod 		= result.getLong("idEmpresa");
				String fantasy 	= result.getString("fantasia");
				String rsocial 	= result.getString("razao_social");
				String cnpj 	= result.getString("cnpj");
				
				return new Empresa(cod, fantasy, rsocial, cnpj);
			}
			
		} catch(SQLException e) { e.printStackTrace(); } 
		finally					{ Conexao.closeBD(result, pstmt, connection); }
		
		return null;
	}

	public Empresa getPojoBySTR(String str) {
		
		query = "SELECT * FROM " + tabela + " WHERE fantasia like '%"+str+"%' "
				+ "OR razao_social like '%"+str+"%' OR cnpj like '%"+str+"%'";
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			while (result.next()) {
				long cod 		= result.getLong("idEmpresa");
				String fantasy 	= result.getString("fantasia");
				String rsocial 	= result.getString("razao_social");
				String cnpj 	= result.getString("cnpj");
				
				return new Empresa(cod, fantasy, rsocial, cnpj);
			}
			
		} 
		catch (SQLException e)	{ e.printStackTrace(); } 
		finally 				{ Conexao.closeBD(result, pstmt, connection); }
		
		return null;
	}

	@Override
	public List<Empresa> getALL() {
		
		query = "SELECT * FROM "+tabela+" ORDER BY "+codigo;
		List<Empresa> list = new ArrayList<Empresa>();
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			
			while (result.next()) {
				long cod 		= result.getLong("idEmpresa");
				String fantasy 	= result.getString("fantasia");
				String rsocial 	= result.getString("razao_social");
				String cnpj 	= result.getString("cnpj");
				
				list.add( new Empresa(cod, fantasy, rsocial, cnpj) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			Conexao.closeBD(result, pstmt, connection); 
		}
		return list;
	}

	//AUXILIARs
	private void setParameters(Empresa empresa) throws SQLException {
		pstmt.setString(1, empresa.getFantasia());
		pstmt.setString(2, empresa.getRazaoSocial());
		pstmt.setString(3, empresa.getCnpj());
	}
	
}