package br.com.bank.bd.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.bank.bd.Conexao;
import br.com.bank.bo.Conta;
import br.com.bank.bo.TransferenciaBancos;

public class TransferenciaBancosDao extends CrudDao implements ICrudDao<TransferenciaBancos> {
	
	public TransferenciaBancosDao() {
		tabela = "\"TransferenciaBancos\"";
		codigo = "\"idTransferenciaBancos\"";
	}
	
	
	@Override
	public long inserir(TransferenciaBancos transfBancos) {
		
		String tipo_de_pessoa = definirTipoDePessoa(transfBancos);
		
		setQuery("INSERT INTO "+ tabela
				+" (\"idConta\", dat_transferencia, "+tipo_de_pessoa+", "
				+ "valor, tip_transferencia)"
				+ "VALUES (?,?,?,?,?) RETURNING "+ codigo);
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(transfBancos);
			
			result = pstmt.executeQuery();
			connection.commit();
			
			// Altera o Valor do Saldo na Conta vigente.
			Conta conta = new ContaDao().getPojoByID( transfBancos.getConta().getIdConta() );
			conta.debitar(transfBancos.getValor());
			new ContaDao().alterarSaldo(conta.getIdConta(), conta.getSaldo());
			
			
			if( result.next() ) return result.getInt(1);
			else				return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error ao inserir Transferencia entre Bancos: "+e.getMessage());
			
			commitRollBack();
		} 
		finally { Conexao.closeBD(result, pstmt, connection); }
		return 0;
	}


	@Override
	public void alterar(TransferenciaBancos t) {
		new Throwable("Inapropriado alterar uma transferência já realida.");
	}

	@Override
	public TransferenciaBancos getPojoByID(long id) {
		query = "SELECT * FROM "+ tabela +" WHERE "+codigo+" = "+id;
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			while (result.next()) {
				long cod 			= result.getLong("idTransferenciaBancos");
				long contaID 		= result.getLong("idConta");
				Date data 			= new Date( result.getDate("dat_transferencia").getTime() );
				String cpf_cnpj 	= definirCpfCnpjDeBusca();
				double vlr 			= result.getDouble("valor");
				String tipTransf	= result.getString("tip_transferencia");
				
				return new TransferenciaBancos(cod, new Conta(contaID), data, 
						cpf_cnpj, vlr, tipTransf);
			}
			
		} 
		catch(SQLException e) 	{ e.printStackTrace(); } 
		finally					{ Conexao.closeBD(result, pstmt, connection); }

		return null;
	}



	@Override
	public List<TransferenciaBancos> getALL() {
		query = "SELECT * FROM "+tabela+" ORDER BY "+codigo;
		List<TransferenciaBancos> list = new ArrayList<TransferenciaBancos>();
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			
			while (result.next()) {
				long cod 			= result.getLong("idTransferenciaBancos");
				long contaID 		= result.getLong("idConta");
				Date data 			= new Date( result.getDate("dat_transferencia").getTime() );
				String cpf_cnpj 	= definirCpfCnpjDeBusca();
				double vlr 			= result.getDouble("valor");
				String tipTransf	= result.getString("tip_transferencia");
				
				list.add( new TransferenciaBancos(cod, new Conta(contaID), data, 
						cpf_cnpj, vlr, tipTransf) );
			}
		} 
		catch (SQLException e) 	{ e.printStackTrace(); } 
		finally 				{ Conexao.closeBD(result, pstmt, connection); }
		
		return list;
	}
	
//	AUXILIARs
	
	/**
	 * @author 	Gilton Nascimento.
	 * @method 	definirTipoDePessoa
	 * @since 	28/01/2019 - 00:48:39
	 * @param 	transfBancos
	 * @return  String
	 */
	private String definirTipoDePessoa(TransferenciaBancos transfBancos) {
		if( transfBancos.getCnpj() == null )
			return "cpf";
		else 
			return "cnpj";
	}
	
	/**
	 * @author 	Gilton Nascimento.
	 * @method 	definirCpfCnpjDeBusca
	 * @since 	28/01/2019 - 01:12:25
	 * @throws 	SQLException 
	 * @return  String
	 */
	private String definirCpfCnpjDeBusca() throws SQLException {
		String cpf_cnpj;
		if( result.getString("cpf") == null )
			cpf_cnpj  = result.getString("cpf");
		else
			cpf_cnpj  = result.getString("cnpj");
		return cpf_cnpj;
	}
	
	private void setParameters(TransferenciaBancos transferenciaBancos) throws SQLException {
		pstmt.setLong(1, transferenciaBancos.getConta().getIdConta());
		pstmt.setDate(2, new java.sql.Date(transferenciaBancos.getDataTransferenciaBancos().getTime()));
		
		if ( transferenciaBancos.getCnpj() != null ) 
			{ pstmt.setString(3, transferenciaBancos.getCnpj()); }
		else 
			{ pstmt.setString(3, transferenciaBancos.getCpf()); }
		
		pstmt.setDouble(4, transferenciaBancos.getValor());
		pstmt.setString(5, transferenciaBancos.getTipoDeTransferencia());
	}
	
}
