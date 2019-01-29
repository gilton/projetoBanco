package br.com.bank.bo;

import java.io.Serializable;


/**
 * @author 	Gilton Nascimento.
 * @since 	23/01/2019 - 18:40:03 
 * @version	0.105
 */
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idConta;
	private int agencia;
	private String numero;
	private String descricaoConta;
	private double saldo;
	
	private Cliente cliente;
	private Operacao operacao;
//	private Pagamento pagamento;
//	private Sacar sacar;
//	private Deposito deposito;
//	private Transferencia transferencia;
//	private TransferenciaBancos transferenciaBancos;
	
	
//	Constructor
	public Conta() {}

	public Conta(long id) { this.idConta = id; }

	public Conta(long cod, Cliente cliente, int agency, String num,
			String dscConta, double saldo) {
		this.idConta = cod;
		this.cliente = cliente;
		this.agencia = agency;
		this.numero = num;
		this.descricaoConta = dscConta;
		this.saldo = saldo;
	}

	public Conta(Cliente cliente, int agency, String num, String dsc_conta,
			double saldo) {
		this.cliente = cliente;
		this.agencia = agency;
		this.numero = num;
		this.descricaoConta = dsc_conta;
		this.saldo = saldo;
	}

	//	METHDOs
	public void creditar( double valor ) { saldo =  saldo + valor; }
	
	public void debitar ( double valor ) { saldo = saldo - valor; }
	
		
	//	GETs and SETs
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public final int getAgencia() {
		return agencia;
	}

	public final void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public final String getNumero() {
		return numero;
	}

	public final void setNumero(String numero) {
		this.numero = numero;
	}

	public final Long getIdConta() {
		return idConta;
	}

	public final String getDescricaoConta() {
		return descricaoConta;
	}

	public final void setDescricaoConta(String descricaoConta) {
		this.descricaoConta = descricaoConta;
	}

	public final double getSaldo() {
		return saldo;
	}

	public final void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	public Operacao getOperacao() {
		return operacao;
	}
	
	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}
	
	
/*
	public final boolean isExtrato() {
		return extrato;
	}

	public final void setExtrato(boolean extrato) {
		this.extrato = extrato;
	}

	public final String getOperacaoRealizada() {
		return operacaoRealizada;
	}

	public final void setOperacaoRealizada(String operacaoRealizada) {
		this.operacaoRealizada = operacaoRealizada;
	}

	public final Date getDataOperacao() {
		return dataOperacao;
	}

	public final void setDataOperacao(Date dataOperacao) {
		this.dataOperacao = dataOperacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Sacar getSacar() {
		return sacar;
	}

	public void setSacar(Sacar sacar) {
		this.sacar = sacar;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	public Transferencia getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(Transferencia transferencia) {
		this.transferencia = transferencia;
	}

	public TransferenciaBancos getTransferenciaBancos() {
		return transferenciaBancos;
	}

	public void setTransferenciaBancos(TransferenciaBancos transferenciaBancos) {
		this.transferenciaBancos = transferenciaBancos;
	}

	public final Long getIdConta() {
		return idConta;
	}
*/
	
//	OVERRIDEs
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idConta == null) ? 0 : idConta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		if (idConta == null) {
			if (other.idConta != null)
				return false;
		} else if (!idConta.equals(other.idConta))
			return false;
		return true;
	}
	
}
