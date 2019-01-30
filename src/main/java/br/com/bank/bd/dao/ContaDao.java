package br.com.bank.bd.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.bank.bd.Conexao;
import br.com.bank.bo.Cliente;
import br.com.bank.bo.Conta;

public class ContaDao extends CrudDao implements ICrudDao<Conta> {
	
	public ContaDao() {
		tabela = "\"Conta\"";
		codigo = "\"idConta\"";
	}

	@Override
	public long inserir(Conta conta) {
		
		if( conta.getCliente().getIdCliente() == null ) {
			long clienteID = new ClienteDao().inserir(conta.getCliente());
			conta.setCliente( new Cliente(clienteID) );
		} 
		
		setQuery("INSERT INTO "+ tabela
				+" (\"idCliente\", agencia, numero, dsc_conta, saldo) "
				+ "VALUES (?,?,?,?,?) RETURNING "+ codigo);
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(conta);
			
			result = pstmt.executeQuery();
			connection.commit();
			
			if( result.next() ) return result.getInt(1);
			else				return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error ao inserir Conta: "+e.getMessage());
			
			commitRollBack();
		} 
		finally { Conexao.closeBD(result, pstmt, connection); }
		
		return 0;
	}

	@Override
	public void alterar(Conta conta) {
		query = "UPDATE "+ tabela +" "
				+"SET \"idCliente\" = ?, agencia = ?, numero = ?, "
				+ "dsc_conta = ?, saldo = ?"
				+ "WHERE "+codigo+" = ?";
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(conta);
			pstmt.setLong(6, conta.getIdConta());
			
			pstmt.executeUpdate();
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			commitRollBack();
		} finally {
			Conexao.closeBD(result, pstmt, connection);
		}
		
	}
	
	public void alterarSaldo(long id, double vlr) {
		query = "UPDATE "+ tabela +" "
				+"SET saldo = "+ vlr
				+ "WHERE "+codigo+" = "+id;
		
		System.out.println("id-conta: "+id);
		System.out.println("vlr-conta: "+vlr);
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
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
	public Conta getPojoByID(long id) {
		query = "SELECT * FROM "+ tabela +" WHERE "+codigo+" = "+id;
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			while (result.next()) {
				long cod 		= result.getLong("idConta");
				long clienteID	= result.getLong("idCliente");
				int agency		= result.getInt("agencia");
				String num		= result.getString("numero");
				String dscConta	= result.getString("dsc_conta");
				double saldo	= result.getDouble("saldo");
				
				return new Conta(cod, new Cliente(clienteID), agency, num, dscConta, saldo);
			}
			
		} catch(SQLException e) { e.printStackTrace(); } 
		finally					{ Conexao.closeBD(result, pstmt, connection); }
		
		return null;
	}

	@Override
	public List<Conta> getALL() {
		
		query = "SELECT * FROM "+tabela+" ORDER BY "+codigo;
		List<Conta> list = new ArrayList<Conta>();
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			
			while (result.next()) {
				long cod 		= result.getLong("idConta");
				long clienteID	= result.getLong("idCliente");
				int agency		= result.getInt("agencia");
				String num		= result.getString("numero");
				String dscConta	= result.getString("dsc_conta");
				double saldo	= result.getDouble("saldo");
				
				list.add( new Conta(cod, new Cliente(clienteID), agency, num, dscConta, saldo) );
			}
			
		} 
		catch (SQLException e) 	{ e.printStackTrace(); } 
		finally 				{ Conexao.closeBD(result, pstmt, connection); }
		
		return list;
	}
	
	
//	AUXILIARs
	private void setParameters(Conta conta) throws SQLException {
		pstmt.setLong(1, conta.getCliente().getIdCliente());
		pstmt.setInt(2, conta.getAgencia());
		pstmt.setString(3, conta.getNumero());
		pstmt.setString(4, conta.getDescricaoConta());
		pstmt.setDouble(5, conta.getSaldo());
	}
	
}
