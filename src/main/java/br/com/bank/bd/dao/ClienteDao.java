package br.com.bank.bd.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.bank.bd.Conexao;
import br.com.bank.bo.Cliente;
import br.com.bank.bo.Empresa;
import br.com.bank.bo.Localidade;
import br.com.bank.bo.Login;
import br.com.bank.bo.Pessoa;

public class ClienteDao extends CrudDao implements ICrudDao<Cliente> {
	
	
	public ClienteDao() {
		tabela = "\"Cliente\"";
		codigo = "\"idCliente\"";
	}
	
	@Override
	public long inserir(Cliente cliente) {
		
		query = "INSERT INTO public.\"Cliente\"(dsc_cliente, dat_cadastro, tip_cliente, "
				+ "\"idLocalidade\"";
		
		/* Requer mais Testes...
		 
//		String cepCliente = cliente.getLocalidade().getCep();
//		Localidade localidadeRecuperada = new LocalidadeDao().getPojo(cliente.getLocalidade(),true);
 * 
		if( localidadeRecuperada != null ) {
			
	//		EM CASO CLIENTE MORE NA MESMA RESIDÊNCIA
			if( cepCliente.equals(localidadeRecuperada.getCep()) 
					&& localidadeRecuperada.getNum() == cliente.getLocalidade().getNum()
					&& localidadeRecuperada.getEndereco()
					.equals(cliente.getLocalidade().getEndereco()) ) {
				
				localID = localidadeRecuperada.getIdLocalidade();
				cliente.setLocalidade(new Localidade(localID));
			}
	//		EM CASO CLIENTE MORE NO MESMO CEP, NÚMERO, MAS O ENDEREÇO DIFERENTE (INSERIR)
			if( cepCliente.equals(localidadeRecuperada.getCep()) 
					&& localidadeRecuperada.getNum() == cliente.getLocalidade().getNum()
					&& !localidadeRecuperada.getEndereco()
					.equals(cliente.getLocalidade().getEndereco()) ) {
	
				localID = new LocalidadeDao().inserir(cliente.getLocalidade());
				cliente.setLocalidade(new Localidade(localID));
			}
			
	//		EM CASO CLIENTE MORE NO MESMO CEP, DIFERENTE NÚMERO, E O MESMO ENDEREÇO (INSERIR)
			if( cepCliente.equals(localidadeRecuperada.getCep()) 
					&& localidadeRecuperada.getNum() != cliente.getLocalidade().getNum()
					&& localidadeRecuperada.getEndereco()
					.equals(cliente.getLocalidade().getEndereco()) ) {
				
				localID = new LocalidadeDao().inserir(cliente.getLocalidade());
				cliente.setLocalidade(new Localidade(localID));
			}
			
	//		EM CASO CLIENTE MORE NO MESMO CEP, DIFERENTE NÚMERO, E DIFERENTE ENDEREÇO (INSERIR) 
			if( cepCliente.equals(localidadeRecuperada.getCep()) 
					&& localidadeRecuperada.getNum() != cliente.getLocalidade().getNum()
					&& localidadeRecuperada.getEndereco()
					.equals( cliente.getLocalidade().getEndereco() ) ) {
	
				localID = new LocalidadeDao().inserir(cliente.getLocalidade());
				cliente.setLocalidade(new Localidade(localID));
			}
		
		} else {
			localID  = new LocalidadeDao().inserir(cliente.getLocalidade());
			cliente.setLocalidade(new Localidade(localID));
		}
		 */
		
//		PESSOA
		if( cliente.getEmpresa() == null ) {
			query += ", \"idPessoa\"";
			Long id_pessoa = cliente.getPessoa().getIdPessoa();
			
			if( id_pessoa != null ) {
				Pessoa pessoaEncontrada = new PessoaDao().getPojoByID( id_pessoa );
				cliente.setPessoa( pessoaEncontrada );
			} else {
				long pessoaID  = new PessoaDao().inserir(cliente.getPessoa());
				cliente.setPessoa(new Pessoa(pessoaID));
			}
			
		} 
//		EMPRESA
		else {
			query += ", \"idEmpresa\"";
			Long id_empresa = cliente.getEmpresa().getIdEmpresa();

			if( id_empresa != null ) {
				Empresa empresaEncontrada = new EmpresaDao().getPojoByID( id_empresa );
				cliente.setEmpresa( empresaEncontrada );
			} else {
				long empresaID  = new EmpresaDao().inserir(cliente.getEmpresa());
				cliente.setEmpresa(new Empresa(empresaID));
			}
		}
		
//		LOCALIDADE
		Long id_local = cliente.getLocalidade().getIdLocalidade();
		if( id_local != null ) {
			Localidade localEncontrado = new LocalidadeDao().getPojoByID( id_local );
			cliente.setLocalidade( localEncontrado );
		} else {
			long localID  = new LocalidadeDao().inserir(cliente.getLocalidade());
			cliente.setLocalidade(new Localidade(localID));
		}
		
//		LOGIN
		if ( cliente.getLogin() == null ) { // Inserir Login nulo
			query += ") VALUES (?, ?, ?, ?, ?) RETURNING "+ codigo;
		}
		else { 
			query += ", \"idLogin\") VALUES (?, ?, ?, ?, ?, ?) RETURNING "+ codigo;
			Long id_login = cliente.getLogin().getIdLogin();
			
			if( id_login != null ) {
				Login loginEncontrada = new LoginDao().getPojoByID( id_login );
				cliente.setLogin( loginEncontrada );
			} else {
				long loginID  = new LoginDao().inserir(cliente.getLogin());
				cliente.setLogin(new Login(loginID));
			}
		}
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, cliente.getDescricaoCliente());
			pstmt.setDate(2, new java.sql.Date( cliente.getDataCadastro().getTime() ));
			pstmt.setString(3, cliente.getTipoCliente());
			
			pstmt.setObject(4, cliente.getLocalidade().getIdLocalidade());
			
			if( cliente.getEmpresa() == null ) 
				{ pstmt.setObject(5, cliente.getPessoa().getIdPessoa()); }
			else{ pstmt.setObject(5, cliente.getEmpresa().getIdEmpresa()); }
			
			if( cliente.getLogin() != null )
				pstmt.setObject(6, cliente.getLogin().getIdLogin());
			
			result = pstmt.executeQuery();
			connection.commit();
			
			if( result.next() ) 
				return result.getInt(1);
			else 
				return 0;
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error ao inserir Cliente: "+e.getMessage());
			
			commitRollBack();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error ao inserir Cliente: "+e.getMessage());
			
			commitRollBack();
		}
		finally {
			Conexao.closeBD(result, pstmt, connection);
		}
		return 0;
	}

	@Override
	public void alterar(Cliente cliente) {
		query = "UPDATE "+ tabela +" "
				+ "SET dsc_cliente = ?, dat_cadastro = ?, tip_cliente = ? "
				+ "WHERE "+codigo+" = ?";
		
		setWhich2Change(cliente);
		
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			
			setParameters(cliente);
			pstmt.setLong(4, cliente.getIdCliente());
			
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
	public Cliente getPojoByID(long id) {
		query = "SELECT * FROM "+ tabela +" WHERE "+codigo+" = "+id;

		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			while (result.next()) {
				long cod = result.getLong("idCliente");
				String desc= result.getString("dsc_cliente");
				java.util.Date data = new java.util.Date(result.getDate("dat_cadastro").getTime());
				String tipo = result.getString("tip_cliente");
				
				long localID = result.getLong("idLocalidade");
				Localidade local = new LocalidadeDao().getPojoByID(localID);
				
				Pessoa pessoa = null;
				long pessoaID = result.getLong("idPessoa");
				if( pessoaID != 0 ) { pessoa = new PessoaDao().getPojoByID(pessoaID); }
				
				Empresa empresa = null;
				long empresaID = result.getLong("idEmpresa");
				if( empresaID != 0 ) { empresa = new EmpresaDao().getPojoByID(empresaID); }
				
				Login login = null;
				long loginID = result.getLong("idLogin");
				if( loginID != 0 ) { login = new LoginDao().getPojoByID(loginID); }
					
				
				new Cliente(cod,desc,data,tipo,local,pessoa,empresa,login);
			}
			
		} catch(SQLException e) { e.printStackTrace(); } 
		finally					{ Conexao.closeBD(result, pstmt, connection); }
		
		return null;
	}


	@Override
	public List<Cliente> getALL() {
		query = "SELECT * FROM "+tabela+" ORDER BY "+codigo;
		List<Cliente> list = new ArrayList<Cliente>();
		try {
			grantAccess2Connection();
			pstmt = connection.prepareStatement(query);
			result = pstmt.executeQuery();
			
			while (result.next()) {
				long cod = result.getLong("idCliente");
				String desc= result.getString("dsc_cliente");
				java.util.Date data = new java.util.Date(result.getDate("dat_cadastro").getTime());
				String tipo = result.getString("tip_cliente");
				
				long localID = result.getLong("idLocalidade");
				Localidade local = new LocalidadeDao().getPojoByID(localID);
				
				Pessoa pessoa = null;
				long pessoaID = result.getLong("idPessoa");
				if( pessoaID != 0 ) { pessoa = new PessoaDao().getPojoByID(pessoaID); }
				
				Empresa empresa = null;
				long empresaID = result.getLong("idEmpresa");
				if( empresaID != 0 ) { empresa = new EmpresaDao().getPojoByID(empresaID); }
				
				Login login = null;
				long loginID = result.getLong("idLogin");
				if( loginID != 0 ) { login = new LoginDao().getPojoByID(loginID); }
					
				
				list.add( new Cliente(cod,desc,data,tipo,local,pessoa,empresa,login) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			Conexao.closeBD(result, pstmt, connection); 
		}
		return list;
	}
	
	
//	AUXILIARs
	private void setParameters(Cliente cliente) throws SQLException {
		pstmt.setString(1, cliente.getDescricaoCliente());
		pstmt.setDate(2, new java.sql.Date( cliente.getDataCadastro().getTime()) );
		pstmt.setString(3, cliente.getTipoCliente());
	}
	
	private void setWhich2Change(Cliente cliente) {
		
		if( cliente.getLocalidade() != null ) {
			new LocalidadeDao().alterar(cliente.getLocalidade());
		}
		if( cliente.getEmpresa() != null ) {
			new EmpresaDao().alterar(cliente.getEmpresa());
		}
		if( cliente.getPessoa() != null ) {
			new PessoaDao().alterar(cliente.getPessoa());
		}
		if( cliente.getLogin() != null ) {
			new LoginDao().alterar(cliente.getLogin());
		}
	}
}