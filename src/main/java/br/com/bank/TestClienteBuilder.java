package br.com.bank;

import br.com.bank.bo.ClienteBuilder;
import br.com.bank.bo.Localidade;
import br.com.bank.bo.Login;
import br.com.bank.bo.Pessoa;

public class TestClienteBuilder {
	
	public static void main(String[] args) {
		ClienteBuilder cb = new ClienteBuilder.Builder()
				.idCliente(20)
				.pessoa(new Pessoa("Josheph Pedrosa","","M".charAt(0)))
//				.empresa( new Empresa("Escola de Mergulho do Tio José","",""))
				.descricaoCliente("Cliente Ultra")
				.dataCadastro(new java.util.Date())
				.tipoCliente("Platinium")
				.localidade(new Localidade("Rua Americo Vespulcio",2135,"",""))
				.login(new Login("josepedrosa","",""))
				.build();
		/*
				"Cliente ( "+idCliente+") "
				+ "nome: "+pessoa.getNome()+" "
				+ "empresa: "+empresa.getFantasia()+" "
				+ "descricao: "+descricaoCliente+" "
				+ "cadastro: "+new SimpleDateFormat("dd/MMM/yyyy").format(dataCadastro)
				+ "tipo: "+tipoCliente+" "
				+ "endereco: "+localidade.getEndereco()+" - Nº "+ localidade.getNum()
				+ "user ("+login.getUsuario()+") "
		*/
		System.out.println(cb);
		System.out.println();
	}
	
}
