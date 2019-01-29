package br.com.bank.bd.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.bank.bd.Conexao;
import br.com.bank.bo.Conta;
import br.com.bank.bo.Deposito;
import br.com.bank.bo.Operacao;
import br.com.bank.bo.Pagamento;
import br.com.bank.bo.Sacar;
import br.com.bank.bo.Transferencia;
import br.com.bank.bo.TransferenciaBancos;

public class OperacaoDao extends CrudDao implements ICrudDao<Operacao> {
	
	public OperacaoDao() {
		tabela = "\"Operacao\"";
		codigo = "\"idOperacao\"";
	}
	
	@Override
	public long inserir(Operacao operacao) {
		
		String tipo_operacao = definirTipoDeOperacao(operacao.getDescricaoOperacao());
		
		setQuery("INSERT INTO "+ tabela
				+" (\"idConta\", dsc_operacao, dat_operacao, "
				+ tipo_operacao+", extrato) "
				+ "VALUES (?,?,?,?,?) RETURNING "+ codigo);
		
		
		if( operacao.getConta().getIdConta() != null ) {
			Conta contaEncontrada = new ContaDao().getPojoByID(operacao.getConta().getIdConta());
			operacao.setConta(contaEncontrada);
		}
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(operacao);
			
			result = pstmt.executeQuery();
			connection.commit();
			
			if( result.next() ) return result.getInt(1);
			else				return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error ao registrar Operacao: "+e.getMessage());
			
			commitRollBack();
		} 
		finally { Conexao.closeBD(result, pstmt, connection); }
		
		return 0;
	}

	
	@Override
	public void alterar(Operacao operacao) {
		new Throwable("Inapropriado alterar um registro de Operação já realida.");
	}

	@Override
	public Operacao getPojoByID(long id) {
		query = "SELECT * FROM "+ tabela +" WHERE "+codigo+" = "+id;
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			while (result.next()) {
				long cod 			= result.getLong("idOperacao");
				long contaID 		= result.getLong("idConta");
				String dscOperacao 	= result.getString("dsc_operacao");
				Date data 			= new Date( result.getDate("dat_operacao").getTime() );
				
				String tipOperacao 	= definirTipoDeOperacao(dscOperacao);
				long idTipOperacao	= result.getLong(tipOperacao);
				
				boolean extrato 	= result.getBoolean("extrato");
				
				
				return definirParametersConstructorOperacao(cod, contaID, dscOperacao,
						data, idTipOperacao, extrato);
			}
		} 
		catch(SQLException e) 	{ e.printStackTrace(); } 
		finally					{ Conexao.closeBD(result, pstmt, connection); }

		return null;
	}

	

	@Override
	public List<Operacao> getALL() {
		
		query = "SELECT * FROM "+tabela+" ORDER BY "+codigo;
		List<Operacao> list = new ArrayList<Operacao>();
		Operacao op = new Operacao();
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			
			while (result.next()) {
				long cod 			= result.getLong("idOperacao");
				long contaID 		= result.getLong("idConta");
				String dscOperacao 	= result.getString("dsc_operacao");
				Date data 			= new Date( result.getDate("dat_operacao").getTime() );
				
				String tipOperacao 	= definirTipoDeOperacao(dscOperacao);
				long idTipOperacao	= result.getLong(tipOperacao);
				
				boolean extrato 	= result.getBoolean("extrato");
				
				op = definirParametersConstructorOperacao(cod, contaID, dscOperacao,
						data, idTipOperacao, extrato);

				list.add( op );
			}
			
		} 
		catch (SQLException e) 	{ e.printStackTrace(); } 
		finally 				{ Conexao.closeBD(result, pstmt, connection); }
		
		return list;
	}
	
//	AUXILIARs
	
	/**
	 * @author 	Gilton Nascimento.
	 * @method 	definirParametersConstructorOperacao
	 * @since 	28/01/2019 - 16:44:24
	 * @param 	cod
	 * @param 	contaID
	 * @param 	dscOperacao
	 * @param 	data
	 * @param 	idTipOperacao
	 * @param 	extrato
	 * @return  Operacao
	 */
	private Operacao definirParametersConstructorOperacao(long cod, long contaID,
			String dscOperacao, Date data, long idTipOperacao, boolean extrato) {
		
		if( "deposito".equalsIgnoreCase( dscOperacao ) ) {
			return new Operacao(cod, new Conta(contaID), 
					dscOperacao, data, new Deposito(idTipOperacao),  extrato);
		}
		if( "sacar".equalsIgnoreCase( dscOperacao ) ) {
			return new Operacao(cod, new Conta(contaID), 
					dscOperacao, data, new Sacar(idTipOperacao),  extrato);
		}
		if( "pagamento".equalsIgnoreCase( dscOperacao ) ) {
			return new Operacao(cod, new Conta(contaID), 
					dscOperacao, data, new Pagamento(idTipOperacao),  extrato);
		}
		if( "transferencia".equalsIgnoreCase( dscOperacao ) ) {
			return new Operacao(cod, new Conta(contaID), 
					dscOperacao, data, new Transferencia(idTipOperacao),  extrato);
		}
		if( "transferenciaBancos".equalsIgnoreCase( dscOperacao ) ) {
			return new Operacao(cod, new Conta(contaID), 
					dscOperacao, data, new TransferenciaBancos(idTipOperacao),  extrato);
		}
		return null;
	}
	
	
	/**
	 * @author 	Gilton Nascimento.
	 * @method 	definirTipoDeOperacao
	 * @since 	28/01/2019 - 16:44:11
	 * @param 	tipo_de_operacao
	 * @return  String
	 */
	private String definirTipoDeOperacao(String tipo_de_operacao) {
		return "\"id"+tipo_de_operacao.substring(0,1).toUpperCase()
				.concat( tipo_de_operacao.substring(1) )+"\"";
	}
	
	private void setParameters(Operacao operacao) throws SQLException {
		
		pstmt.setLong	(1, operacao.getConta().getIdConta());
		pstmt.setString	(2, operacao.getDescricaoOperacao());
		pstmt.setDate	(3, new java.sql.Date(operacao.getDataOperacao().getTime()));
		
		if( operacao.getDeposito() != null  
			&& "deposito".equalsIgnoreCase(operacao.getDescricaoOperacao()) ) {
			
			long idDeposito = 0;
			if( operacao.getDeposito() != null  ) {
				idDeposito = new DepositoDao().inserir(operacao.getDeposito());
			}
			pstmt.setLong(4, idDeposito);
			
		}
		
		if( operacao.getSacar() != null 
			&& "sacar".equalsIgnoreCase(operacao.getDescricaoOperacao()) ) {
			
			long idSacar = 0;
			
			if( operacao.getSacar() != null  ) {
				idSacar= new SacarDao().inserir(operacao.getSacar());
			}
			pstmt.setLong(4, idSacar);
		}
			
		if( operacao.getPagamento() != null 
			&& "pagamento".equalsIgnoreCase(operacao.getDescricaoOperacao()) ) {
			
			long idPagar = 0;
			if( operacao.getPagamento() != null  ) {
				idPagar = new PagamentoDao().inserir(operacao.getPagamento());
			}
			pstmt.setLong(4, idPagar);
		}
		
		if( operacao.getTransferencia() != null 
			&& "transferencia".equalsIgnoreCase(operacao.getDescricaoOperacao()) ) {
			
			long idTransferir = 0;
			if( operacao.getTransferencia() != null  ) {
				idTransferir = new TransferenciaDao().inserir(operacao.getTransferencia());
			}
			pstmt.setLong(4, idTransferir);
		}
		
		if( operacao.getTransferenciaBancos() != null 
			&& "transferenciaBancos".equalsIgnoreCase(operacao.getDescricaoOperacao()) ) {
			
			long idTransferirEntreBancos = 0;
			if( operacao.getTransferenciaBancos() != null  ) {
				idTransferirEntreBancos = new TransferenciaBancosDao().inserir(operacao.getTransferenciaBancos());
			}
			pstmt.setLong(4, idTransferirEntreBancos);
		}
		
		pstmt.setBoolean(5, operacao.isExtrato());
	}
}
