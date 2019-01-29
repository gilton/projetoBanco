package br.com.bank;

import java.util.Date;

import br.com.bank.bd.dao.OperacaoDao;
import br.com.bank.bo.Conta;
import br.com.bank.bo.Operacao;
import br.com.bank.bo.TransferenciaBancos;

public class TestOperacao {
	
	public static void main(String[] args) {
		
		OperacaoDao odao = new OperacaoDao();
		
		String dscOperacao = "transferenciaBancos";
		
//		long idTipOperacao = 1;
		
		long cont = odao.inserir( new Operacao( new Conta(2), dscOperacao, new Date(), 
				new TransferenciaBancos( new Conta(4), new Date(), "cpf", 1245.98, "DOC" ),
				false )  
				
				);
		System.out.println("cont: "+cont);
	}
}
