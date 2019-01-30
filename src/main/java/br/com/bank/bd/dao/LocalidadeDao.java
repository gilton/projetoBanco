package br.com.bank.bd.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.bank.bd.Conexao;
import br.com.bank.bo.Localidade;

public class LocalidadeDao extends CrudDao implements ICrudDao<Localidade> {
	
	public LocalidadeDao() {
		tabela = "\"Localidade\"";
		codigo = "\"idLocalidade\"";
	}
	
	
	@Override
	public long inserir(Localidade localidade) {
		setQuery("INSERT INTO "+ tabela +" (endereco, num, bairro, cep) "
				+ "VALUES (?,?,?,?) RETURNING "+ codigo);
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(localidade);
			
			result = pstmt.executeQuery();
			connection.commit();
			
			if( result.next() )	return result.getInt(1);
			else 				return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error ao inserir Localidade: "+e.getMessage());
			
			commitRollBack();
		} finally {
			Conexao.closeBD(result, pstmt, connection);
		}
		return 0;
	}
	
	@Override
	public void alterar(Localidade localidade) {
		query = "UPDATE "+ tabela + " "
				+ "SET endereco = ?, num = ?, bairro = ?, cep = ? "
				+ "WHERE "+codigo+" = ?";
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(localidade);
			pstmt.setLong(5, localidade.getIdLocalidade());
			
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
	public Localidade getPojoByID(long id) {
		query = "SELECT * FROM "+ tabela +" WHERE "+codigo+" = "+id;
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			result = pstmt.executeQuery();
			while (result.next()) {
				long 	cod 	= result.getLong("idLocalidade");
				String 	endereco= result.getString("endereco");
				int 	num 	= result.getInt("num");
				String 	bairro 	= result.getString("bairro");
				String 	cep 	= result.getString("cep");
				
				return new Localidade(cod, endereco, num, bairro, cep);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexao.closeBD(result, pstmt, connection);
		}
		return null;
	}

	public Localidade getPojoBySTR(String str) {
		
			query = "SELECT * FROM "+ tabela +" WHERE endereco like '%"+str+"%' "
					+ "OR bairro like '%"+str+"%' "
					+" OR cep like '%"+str+"%'";
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			result = pstmt.executeQuery();
			while (result.next()) {
				long 	cod 	= result.getLong("idLocalidade");
				String 	endereco= result.getString("endereco");
				int 	num 	= result.getInt("num");
				String 	bairro 	= result.getString("bairro");
				String 	cep 	= result.getString("cep");
				
				return new Localidade(cod, endereco, num, bairro, cep);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexao.closeBD(result, pstmt, connection);
		}
		return null;
	}
	
	/**
	 * Retorna uma Localidade através de uma pesquisa abrangente (false) 
	 * ou restrintiva (true).
	 * @author 	Gilton Nascimento.
	 * @method 	getPojo
	 * @since 	25/01/2019 - 04:44:47
	 * @param 	local Localidade
	 * @param 	restringir boolean
	 * @return  Localidade
	 */
	public Localidade getPojo(Localidade local, boolean restringir) {
		if( !restringir ) {
			query = "SELECT * FROM "+ tabela
					+ " WHERE endereco like '%" + local.getEndereco() + "%'"
					+ " OR num = " + local.getNum()
					+ " OR bairro like '%" + local.getBairro() + "%'"
					+ " OR cep like '%" + local.getCep()+"%'";
		}
		else {
			query = "SELECT * FROM "+ tabela
					+ " WHERE endereco like '%" + local.getEndereco() + "%'"
					+ " AND num = " + local.getNum()
					+ " AND bairro like '%" + local.getBairro() + "%'"
					+ " AND cep like '%" + local.getCep()+"%'";
		}
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			result = pstmt.executeQuery();
			while (result.next()) {
				long 	cod 	= result.getLong("idLocalidade");
				String 	endereco= result.getString("endereco");
				int 	num 	= result.getInt("num");
				String 	bairro 	= result.getString("bairro");
				String 	cep 	= result.getString("cep");
				
				return new Localidade(cod, endereco, num, bairro, cep);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexao.closeBD(result, pstmt, connection);
		}
		return null;
	}
	

	@Override
	public List<Localidade> getALL() {
		query = "SELECT * FROM "+tabela+" ORDER BY "+codigo;
		List<Localidade> list = new ArrayList<Localidade>();
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			
			while (result.next()) {
				long 	cod 	= result.getLong("idLocalidade");
				String 	endereco= result.getString("endereco");
				int 	num 	= result.getInt("num");
				String 	bairro 	= result.getString("bairro");
				String 	cep 	= result.getString("cep");
				
				list.add( new Localidade(cod, endereco, num, bairro, cep) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			Conexao.closeBD(result, pstmt, connection); 
		}
		return list;
	}
	
	
//	AUXILIARs
	private void setParameters(Localidade localidade) throws SQLException {
		pstmt.setString(1, localidade.getEndereco());
		pstmt.setInt(2, localidade.getNum());
		pstmt.setString(3, localidade.getBairro());
		pstmt.setString(4, localidade.getCep());
	}
	
	
}