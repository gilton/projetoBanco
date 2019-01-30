package br.com.bank.bd.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.bank.bd.Conexao;
import br.com.bank.bo.Conta;
import br.com.bank.bo.Sacar;

public class SacarDao extends CrudDao implements ICrudDao<Sacar> {
	
	public SacarDao() {
		tabela = "\"Sacar\"";
		codigo = "\"idSacar\"";
	}
	
	
	@Override
	public long inserir(Sacar sacar) {
		setQuery("INSERT INTO "+ tabela
				+" ( \"idConta\", valor, dat_sacar) "
				+ "VALUES (?,?,?) RETURNING "+ codigo);
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(sacar);
			
			result = pstmt.executeQuery();
			connection.commit();
			
			Conta conta = new ContaDao().getPojoByID(sacar.getConta().getIdConta());
			conta.debitar(sacar.getValor());
			new ContaDao().alterarSaldo(conta.getIdConta(), conta.getSaldo());
			
			if( result.next() ) return result.getInt(1);
			else				return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error ao inserir Sacar: "+e.getMessage());
			
			commitRollBack();
		} 
		finally { Conexao.closeBD(result, pstmt, connection); }
		
		return 0;
	}

	@Override
	public void alterar(Sacar sacar) {
		query = "UPDATE "+ tabela + " "
				+ "SET \"idConta\", valor = ?, dat_sacar = ? "
				+ "WHERE "+codigo+" = ?";
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(sacar);
			pstmt.setLong(4, sacar.getIdSacar());
			
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
	public Sacar getPojoByID(long id) {
		query = "SELECT * FROM "+ tabela +" WHERE "+codigo+" = "+id;
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			while (result.next()) {
				long cod 		= result.getLong("idSacar");
				long contaID	= result.getLong("idConta");
				double vlr		= result.getDouble("valor");
				Date data 		= new Date( result.getDate("dat_sacar").getTime() );
				
				return new Sacar(cod,new Conta(contaID),vlr,data);
			}
			
		} catch(SQLException e) { e.printStackTrace(); } 
		finally					{ Conexao.closeBD(result, pstmt, connection); }
		
		return null;
	}


	@Override
	public List<Sacar> getALL() {
		query = "SELECT * FROM "+tabela+" ORDER BY "+codigo;
		List<Sacar> list = new ArrayList<Sacar>();
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			
			while (result.next()) {
				long cod 		= result.getLong("idSacar");
				long contaID	= result.getLong("idConta");
				double vlr		= result.getDouble("valor");
				Date data 		= new Date( result.getDate("dat_sacar").getTime() );
				
				list.add( new Sacar(cod,new Conta(contaID),vlr,data) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			Conexao.closeBD(result, pstmt, connection); 
		}
		return list;
	}
	
//	AUXILIARs
	private void setParameters(Sacar sacar) throws SQLException {
		pstmt.setLong(1, sacar.getConta().getIdConta());
		pstmt.setDouble(2, sacar.getValor());
		pstmt.setDate(3, new java.sql.Date(sacar.getDataSacar().getTime()));
	}
}