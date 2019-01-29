package br.com.bank;

import java.util.List;

import br.com.bank.bd.dao.ContaDao;
import br.com.bank.bd.dao.ICrudDao;
import br.com.bank.bo.Conta;

public class TestConta {

	public static void main(String[] args) {
		
		ICrudDao<Conta> icd = new ContaDao();
		/*
		int agency = 133;
		String num = "15203-07";
		String dsc_conta = "extra-comum";
		double saldo = 8551.45;
		Conta c = new Conta( 
				new Cliente("Cliente Benefico", new Date(), "alta-media",
				new Pessoa("Jimmy Mottaran","783.137.130-05",'M'),
				new Localidade(9),
				new Login(14)),
				agency, 
				num, 
				dsc_conta, 
				saldo
				);
		
		System.out.println("idConta: "+icd.inserir(c));
		*/
		
		List<Conta> list = icd.getALL();
		for (Conta conta : list) {
			System.out.println("\nclienteID: "+conta.getCliente().getIdCliente());
			System.out.println("agencia: "+conta.getAgencia());
			System.out.println("número: "+conta.getNumero());
			System.out.println("descricao: "+conta.getDescricaoConta());
			System.out.println("saldo: "+conta.getSaldo());
		}
	}

}
