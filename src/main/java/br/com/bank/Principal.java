package br.com.bank;

import java.sql.SQLException;

public class Principal {

	public static void main(String[] args) throws SQLException {
		
//		LoginDao ldao = new LoginDao();
		
//		ldao.inserir(new Login("ada","ada","adayounghong@umbrella.com"));
//		ldao.alterar(new Login(5,"ralf","ralf","ralf@uol.com.br"));
		
//		TestCrud tc = new TestCrud();
//		tc.insertLogin(new Login("tommy","tommy","tommny@uol.com.br"));
//		tc.updateLogin(new Login(5,"tony","tony","tonny@terrance.net"));
//		tc.removerLogin(6);
		
//		java.util.List<Login> list = (List<Login>) ldao.getALL();
		
//		for (Login login : list) {
//			System.out.println("id: "+login.getIdLogin());
//			System.out.println("user: "+login.getUsuario());
//			System.out.println("senha: "+login.getSenha());
//			System.out.println("email: "+login.getEmail());
//			System.out.println();
//			
//		}
		
		
		String test = "deposito";
		String aux =  test.substring(0,1).toUpperCase().concat( test.substring(1) );
		System.out.println( "aux: "+ aux);
		
	}
}
