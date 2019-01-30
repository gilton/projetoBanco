package br.com.bank.view;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.bank.bd.dao.ClienteDao;
import br.com.bank.bd.dao.EmpresaDao;
import br.com.bank.bd.dao.LoginDao;
import br.com.bank.bd.dao.PessoaDao;
import br.com.bank.bo.Cliente;



@ViewScoped
@ManagedBean
public class ClienteBean implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Cliente selecionado = new Cliente();
	private List<Cliente> listarClientes;
	
//	CONSTRUCTOR
	public ClienteBean() {}
	
//	METHODs
	public void listar() { listarClientes = new ClienteDao().getALL(); }
	
	public void salvarCliente( ActionEvent event ) {
		salvarAction(event);
	}
	
	public void alterarCliente( ActionEvent event ) {
		alterarAction(event);
	}
	
//	ACTIONS
	public void salvarAction( ActionEvent event ) {
		/*
		
		Cliente c = new Cliente(
				"Cliente Comum", 
				new java.util.Date(),
				"cobre",
				new Pessoa("Sandro Torres","702.102.104-90",'M'),
				new Localidade("Rua dos Marfelins", 405, "Ouro de Tolo", "28010-020"),
				new Login("sandroty","salamander","sandrosalazzar@aol.com")
				);
		
		cdao.inserir(c);
		
		*/
		getSelecionado().setDataCadastro( new java.util.Date() );
		getSelecionado().set
		
		new ClienteDao().inserir( getSelecionado() );
	}
	
	public void alterarAction( ActionEvent event ) {
		
	}
	
	public void removerAction( ActionEvent event ) {
		new ClienteDao().excluir( getSelecionado().getIdCliente() );
		
		if( getSelecionado().getPessoa() != null ) {
			new PessoaDao().excluir( getSelecionado().getPessoa().getIdPessoa() );
		}
		
		if( getSelecionado().getEmpresa() != null ) {
			new EmpresaDao().excluir( getSelecionado().getEmpresa().getIdEmpresa	() );
		}
		
		if( getSelecionado().getLogin() != null ) {
			new LoginDao().excluir( getSelecionado().getLogin().getIdLogin() );
		}
	}
	
	
	
	public List<Cliente> getListarClientes() { return listarClientes; }

	public Cliente getSelecionado() { return selecionado; }
	public void setSelecionado(Cliente selecionado) { this.selecionado = selecionado; }
}