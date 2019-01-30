package br.com.bank.bd.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.bank.bd.Conexao;
import br.com.bank.bo.Conta;
import br.com.bank.bo.Transferencia;

public class TransferenciaDao extends CrudDao implements ICrudDao<Transferencia> {

	
	public TransferenciaDao() {
		tabela = "\"Transferencia\"";
		codigo = "\"idTransferencia\"";
	}
	

	@Override
	public long inserir(Transferencia transferencia) {
		setQuery("INSERT INTO "+ tabela
				+" (\"idConta\", \"idDestinatario\", valor, "
				+ "dat_transferencia, dep_judicial)"
				+ "VALUES (?,?,?,?,?) RETURNING "+ codigo);
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(transferencia);
			
			result = pstmt.executeQuery();
			connection.commit();
			
//			Atualizar no Banco de Dados o novo Valor do Saldo
			Conta beneficiador = new ContaDao().getPojoByID(transferencia.getConta().getIdConta());
			Conta receptor = new ContaDao().getPojoByID(transferencia.getDestinatario().getIdConta());
			
			beneficiador.debitar(transferencia.getValor());
			receptor.creditar( transferencia.getValor() );
			
			new ContaDao().alterarSaldo(beneficiador.getIdConta(), beneficiador.getSaldo());
			new ContaDao().alterarSaldo(receptor.getIdConta(), receptor.getSaldo());
			
			
			if( result.next() ) return result.getInt(1);
			else				return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error ao registrar Transferencia entre Contas: "+e.getMessage());
			
			commitRollBack();
		} 
		finally { Conexao.closeBD(result, pstmt, connection); }
		return 0;
	}

	@Override
	public void alterar(Transferencia transferencia) {
		new Throwable("Inapropriado alterar uma transferência já realida.");
	}

	@Override
	public Transferencia getPojoByID(long id) {
		query = "SELECT * FROM "+ tabela +" WHERE "+codigo+" = "+id;
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			while (result.next()) {
				long cod 			= result.getLong("idTransferencia");
				long contaID 		= result.getLong("idConta");
				long destinoID		= result.getLong("idDestinario");
				double vlr 			= result.getDouble("valor");
				Date data 			= new Date( result.getDate("dat_transferencia").getTime() );
				boolean depJudicial	= result.getBoolean("deb_judicial");
				
				return new Transferencia(cod, new Conta(contaID), 
						new Conta(destinoID), vlr, data, depJudicial);
			}
			
		} 
		catch(SQLException e) 	{ e.printStackTrace(); } 
		finally					{ Conexao.closeBD(result, pstmt, connection); }
		return null;
	}

	@Override
	public List<Transferencia> getALL() {
		query = "SELECT * FROM "+tabela+" ORDER BY "+codigo;
		List<Transferencia> list = new ArrayList<Transferencia>();
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			
			while (result.next()) {
				long cod 			= result.getLong("idTransferencia");
				long contaID 		= result.getLong("idConta");
				long destinoID		= result.getLong("idDestinario");
				double vlr 			= result.getDouble("valor");
				Date data 			= new Date( result.getDate("dat_transferencia").getTime() );
				boolean depJudicial	= result.getBoolean("deb_judicial");
				
				
				list.add( new Transferencia(cod, new Conta(contaID), 
						  new Conta(destinoID), vlr, data, depJudicial) );
			}
		} 
		catch (SQLException e) 	{ e.printStackTrace(); } 
		finally 				{ Conexao.closeBD(result, pstmt, connection); }
		
		return list;
	}
	
	
//	AUXILIARs
	private void setParameters(Transferencia transferencia) throws SQLException {
		pstmt.setLong(1, transferencia.getConta().getIdConta());
		pstmt.setLong(2, transferencia.getDestinatario().getIdConta());
		pstmt.setDouble(3, transferencia.getValor());
		pstmt.setDate(4, new java.sql.Date(transferencia.getDataTransferencia().getTime()));
		pstmt.setBoolean(5, transferencia.isDepositoJudicial());
	}

}