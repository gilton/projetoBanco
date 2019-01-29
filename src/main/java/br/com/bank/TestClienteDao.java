package br.com.bank;

import br.com.bank.bd.dao.ClienteDao;
import br.com.bank.bd.dao.ICrudDao;
import br.com.bank.bo.Cliente;
import br.com.bank.bo.Localidade;
import br.com.bank.bo.Login;
import br.com.bank.bo.Pessoa;

public class TestClienteDao {

	/**
	 * @author 	Gilton Nascimento.
	 * @method 	main
	 * @since 	24/01/2019 - 17:38:58
	 * @param 	args String[]
	 * @return  void
	 */
	public static void main(String[] args) {

//		LoginDao ldao = new LoginDao();
//		long cont = ldao.inserir( new Login("greg", "greg", "gregorymonte@chromecast.com") );
//		System.out.println("cont: "+cont);
		
		
//		ldao.excluir(9);
		
//		Login login = ldao.getPojoByID(15);
//		System.out.println("user: "+login.getUsuario());
//		System.out.println("conexão aberta? "+ldao.estarAbertoBD());
		
		
		
		// TESTE CLIENTE
		ICrudDao<Cliente> cdao = new ClienteDao();
		
		Cliente c = new Cliente(
				"Cliente Comum", 
				new java.util.Date(),
				"cobre",
				new Pessoa("Sandro Torres","702.102.104-90",'M'),
				new Localidade("Rua dos Marfelins", 405, "Ouro de Tolo", "28010-020"),
				new Login("sandroty","salamander","sandrosalazzar@aol.com")
				);
		
		cdao.inserir(c);
		/*
		java.util.List<Cliente> list = cdao.getALL();
		
		for (Cliente cliente: list) {

			System.out.print("\nCliente");
			System.out.println(" ("+cliente.getIdCliente()+")");
			System.out.println("cadastro: "+cliente.getDataCadastro());
			System.out.println("desc: "+cliente.getDescricaoCliente());
			System.out.println("tipo: "+cliente.getTipoCliente());
			
			System.out.println("\nLocalidade");
			System.out.println("id: "+cliente.getLocalidade().getIdLocalidade());
			System.out.println("endereco: "+cliente.getLocalidade().getEndereco());
			System.out.println("numero: "+cliente.getLocalidade().getNum());
			System.out.println("bairro: "+cliente.getLocalidade().getBairro());
			System.out.println("cep: "+cliente.getLocalidade().getCep());
			
			if( cliente.getLogin() != null ) {
				System.out.println("\nLogin");
				System.out.println("id: "+cliente.getLogin().getIdLogin());
				System.out.println("user: "+cliente.getLogin().getUsuario());
				System.out.println("senha: "+cliente.getLogin().getSenha());
				System.out.println("email: "+cliente.getLogin().getEmail());
			}
			
			if( cliente.getPessoa() != null ) {
				System.out.println("\nPessoa");
				System.out.println("id: "+cliente.getPessoa().getIdPessoa());
				System.out.println("nome: "+cliente.getPessoa().getNome());
				System.out.println("cpf: "+cliente.getPessoa().getCpf());
				System.out.println("sexo: "+cliente.getPessoa().getSexo());
			}

			if ( cliente.getEmpresa() != null ) {
				System.out.println("\nEmpresa");
				System.out.println("id: "+cliente.getEmpresa().getIdEmpresa());
				System.out.println("fantasia: "+cliente.getEmpresa().getFantasia());
				System.out.println("razão social: "+cliente.getEmpresa().getRazaoSocial());
				System.out.println("cnpj: "+cliente.getEmpresa().getCnpj());
			}
		}
		 */
		
	}

}