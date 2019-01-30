package br.com.bank.bd.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.bank.bd.Conexao;
import br.com.bank.bo.Conta;
import br.com.bank.bo.Pagamento;


public class PagamentoDao extends CrudDao implements ICrudDao<Pagamento> {
	
	
	public PagamentoDao() {
		tabela = "\"Pagamento\"";
		codigo = "\"idPagamento\"";
	}


	@Override
	public long inserir(Pagamento pagamento) {
		setQuery("INSERT INTO "+ tabela
				+" (\"idConta\", dsc_pagamento, vlr_pagamento, dat_pagamento, "
				+ "com_barras, cod_barras, debito_automatico) "
				+ "VALUES (?,?,?,?,?,?,?) RETURNING "+ codigo);
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(pagamento);
			
			result = pstmt.executeQuery();
			
			Conta conta = new ContaDao().getPojoByID( pagamento.getConta().getIdConta() );
			conta.debitar(pagamento.getVlrPagamento());
			new ContaDao().alterarSaldo(conta.getIdConta(), conta.getSaldo());
			
			connection.commit();
			
			if( result.next() ) return result.getInt(1);
			else				return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error ao inserir Pagamento: "+e.getMessage());
			
			commitRollBack();
		} 
		finally { Conexao.closeBD(result, pstmt, connection); }
		return 0;
	}


	@Override
	public void alterar(Pagamento pagamento) {
		query = "UPDATE "+ tabela + " "
				+ "SET \"idConta\" = ? ,dsc_pagamento = ?, vlr_pagamento = ?, "
				+ "dat_pagamento = ?, com_barras = ?, cod_barras = ?, "
				+ "debito_automatico = ? WHERE "+codigo+" = ?";
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(pagamento);
			pstmt.setLong(8, pagamento.getIdPagamento());
			
			pstmt.executeUpdate();
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			commitRollBack();
		} 
		finally { Conexao.closeBD(result, pstmt, connection); }
		
	}


	@Override
	public Pagamento getPojoByID(long id) {
		query = "SELECT * FROM "+ tabela +" WHERE "+codigo+" = "+id;
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			while (result.next()) {
				long cod 			= result.getLong("idDeposito");
				long contaID		= result.getLong("idConta");
				String dsc		 	= result.getString("dsc_pagamento");
				double vlr 			= result.getDouble("vlr_pagamento");
				Date data 			= new Date( result.getDate("dat_pagamento").getTime() );
				boolean combarras 	= result.getBoolean("com_barras");
				String codbarras 	= result.getString("cod_barras");
				boolean debitoAuto 	= result.getBoolean("debito_automatico");
				
				return new Pagamento(cod, new Conta(contaID), dsc, vlr, data, combarras, codbarras, debitoAuto);
			}
			
		} 
		catch(SQLException e) 	{ e.printStackTrace(); } 
		finally					{ Conexao.closeBD(result, pstmt, connection); }
		
		return null;
	}


	@Override
	public List<Pagamento> getALL() {
		query = "SELECT * FROM "+tabela+" ORDER BY "+codigo;
		List<Pagamento> list = new ArrayList<Pagamento>();
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			
			while (result.next()) {
				long cod 			= result.getLong("idDeposito");
				long contaID		= result.getLong("idConta");
				String dsc 			= result.getString("dsc_pagamento");
				double vlr 			= result.getDouble("vlr_pagamento");
				Date data 			= new Date( result.getDate("dat_pagamento").getTime() );
				boolean combarras 	= result.getBoolean("com_barras");
				String codbarras 	= result.getString("cod_barras");
				boolean debitoAuto 	= result.getBoolean("debito_automatico");
				
				list.add( new Pagamento(cod, new Conta(contaID), dsc, vlr, data, combarras, codbarras, debitoAuto) );
			}
		} 
		catch (SQLException e) 	{ e.printStackTrace(); } 
		finally 				{ Conexao.closeBD(result, pstmt, connection); }
		
		return list;
	}
	
	
//	AUXILIARs
	private void setParameters(Pagamento pagamento) throws SQLException {
		pstmt.setLong(1, pagamento.getConta().getIdConta());
		pstmt.setString(2, pagamento.getDescricaoPagamento());
		pstmt.setDouble(3, pagamento.getVlrPagamento());
		pstmt.setDate(4, new java.sql.Date(pagamento.getDataPagamento().getTime()));
		pstmt.setBoolean(5, pagamento.isComBarras());
		pstmt.setString(6, pagamento.getCodBarras());
		pstmt.setBoolean(7, pagamento.isDebitoAutomatico());
	}
	
}