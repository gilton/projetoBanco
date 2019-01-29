package br.com.bank.bd.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.bank.bd.Conexao;
import br.com.bank.bo.Conta;
import br.com.bank.bo.Deposito;

public class DepositoDao extends CrudDao implements ICrudDao<Deposito> {
	
	public DepositoDao() {
		tabela = "\"Deposito\"";
		codigo = "\"idDeposito\"";
	}

	@Override
	public long inserir(Deposito deposito) {
		setQuery("INSERT INTO "+ tabela
				+" (\"idConta\",valor, dat_deposito, favorecer_proprio) "
				+ "VALUES (?,?,?,?) RETURNING "+ codigo);
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(deposito);
			
			result = pstmt.executeQuery();
			connection.commit();
			
//			Atualizar SALDO em CONTA
			Conta conta = new ContaDao().getPojoByID(deposito.getConta().getIdConta());
			conta.creditar(deposito.getValor());
			new ContaDao().alterarSaldo(conta.getIdConta(), conta.getSaldo());
			
			if( result.next() ) return result.getInt(1);
			else				return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error ao inserir Deposito: "+e.getMessage());
			
			commitRollBack();
		} 
		finally { Conexao.closeBD(result, pstmt, connection); }
		
		return 0;
	}

	@Override
	public void alterar(Deposito deposito) {
		query = "UPDATE "+ tabela +" "
				+ "SET \"idConta\" = ?, valor = ?, dat_deposito = ?, favorecer_proprio = ?"
				+ "WHERE "+codigo+" = ?";
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(deposito);
			pstmt.setLong(5, deposito.getIdDeposito());
			
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
	public Deposito getPojoByID(long id) {
		query = "SELECT * FROM "+ tabela +" WHERE "+codigo+" = "+id;
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			while (result.next()) {
				long cod 			= result.getLong("idDeposito");
				long contaID		= result.getLong("idConta");
				double valor		= result.getDouble("valor");
				Date data 			= new Date( result.getDate("dat_deposito").getTime() );
				boolean favorecido	= result.getBoolean("favorecer_proprio");
				
				return new Deposito(cod, new Conta(contaID), valor,data,favorecido);
			}
			
		} catch(SQLException e) { e.printStackTrace(); } 
		finally					{ Conexao.closeBD(result, pstmt, connection); }
		
		return null;
	}

	@Override
	public List<Deposito> getALL() {
		query = "SELECT * FROM "+tabela+" ORDER BY "+codigo;
		List<Deposito> list = new ArrayList<Deposito>();
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			
			while (result.next()) {
				long cod 			= result.getLong("idDeposito");
				long contaID		= result.getLong("idConta");
				double valor		= result.getDouble("valor");
				Date data 			= new Date( result.getDate("dat_deposito").getTime() );
				boolean favorecido	= result.getBoolean("favorecer_proprio");
				
				list.add( new Deposito(cod,new Conta(contaID),valor,data,favorecido) );
			}
			
		} 
		catch (SQLException e) 	{ e.printStackTrace(); } 
		finally 				{ Conexao.closeBD(result, pstmt, connection); }
		
		return list;
	}
	
//	AUXILIARs
	private void setParameters(Deposito deposito) throws SQLException {
		pstmt.setLong(1, deposito.getConta().getIdConta());
		pstmt.setDouble(2, deposito.getValor());
		pstmt.setDate(3, new java.sql.Date(deposito.getDataDeposito().getTime()));
		pstmt.setBoolean(4, deposito.isFavorecido());
	}
}
