package br.com.bank.bd.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.bank.bd.Conexao;
import br.com.bank.bo.Pessoa;

public class PessoaDao extends CrudDao implements ICrudDao<Pessoa> {
	
	public PessoaDao() {
		tabela = "\"Pessoa\"";
		codigo = "\"idPessoa\"";
	}

	@Override
	public long inserir(Pessoa pessoa) {
		
		setQuery("INSERT INTO "+ tabela
				+" (nome, cpf, sexo) VALUES (?,?,?) RETURNING "+ codigo);
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(pessoa);
			
			result = pstmt.executeQuery();
			connection.commit();
			
			if( result.next() ) return result.getInt(1);
			else				return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error ao inserir Pessoa: "+e.getMessage());
			
			commitRollBack();
		} finally {
			Conexao.closeBD(result, pstmt, connection);
		}
		return 0;
	}

	@Override
	public void alterar(Pessoa pessoa) {
		query = "UPDATE "+ tabela + " "
				+ "SET nome = ?, cpf = ?, sexo = ? "
				+ "WHERE "+codigo+" = ?";
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(pessoa);
			pstmt.setLong(4, pessoa.getIdPessoa());
			
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
	public Pessoa getPojoByID(long id) {
		query = "SELECT * FROM "+ tabela +" WHERE "+codigo+" = "+id;
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			while (result.next()) {
				long cod = result.getLong("idPessoa");
				String nome = result.getString("nome");
				String cpf = result.getString("cpf");
				String sexo = result.getString("sexo");
				return new Pessoa(cod,nome,cpf,sexo.charAt(0));
			}
			
		} catch(SQLException e) { e.printStackTrace(); } 
		finally					{ Conexao.closeBD(result, pstmt, connection); }
		
		return null;
	}

	public Pessoa getPojoBySTR(String str) {
		query = "SELECT * FROM " + tabela + " WHERE nome like '%"+str+"%' "
				+ "OR cpf like '%"+str+"%' OR sexo like '%"+str+"%'";
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			while (result.next()) {
				long cod = result.getLong("idPessoa");
				String nome = result.getString("nome");
				String cpf = result.getString("cpf");
				String sexo = result.getString("sexo");
				return new Pessoa(cod,nome,cpf,sexo.charAt(0));
			}
			
		} 
		catch (SQLException e)	{ e.printStackTrace(); } 
		finally 				{ Conexao.closeBD(result, pstmt, connection); }
		
		return null;
	}

	@Override
	public List<Pessoa> getALL() {
		query = "SELECT * FROM "+tabela+" ORDER BY "+codigo;
		List<Pessoa> list = new ArrayList<Pessoa>();
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			
			while (result.next()) {
				long cod = result.getLong("idPessoa");
				String nome = result.getString("nome");
				String cpf = result.getString("cpf");
				String sexo = result.getString("sexo");
				
				list.add( new Pessoa(cod,nome,cpf,sexo.charAt(1)) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			Conexao.closeBD(result, pstmt, connection); 
		}
		return list;
	}
	
//	AUXILIARs
	private String setSexoSTR(char sexo) {
		if( sexo == 'M' )	return "M";
		else 				return "F";
	}
	
	private void setParameters(Pessoa pessoa) throws SQLException {
		pstmt.setString(1, pessoa.getNome());
		pstmt.setString(2, pessoa.getCpf());
		pstmt.setString(3, setSexoSTR(pessoa.getSexo()));
	}

}