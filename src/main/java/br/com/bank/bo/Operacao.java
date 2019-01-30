package br.com.bank.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 	Gilton Nascimento.
 * @since 	28/01/2019 - 03:36:18 
 * @version	0.1
 */
public class Operacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idOperacao;
	private String descricaoOperacao;
	private Date dataOperacao;

	private Conta conta;
	private Sacar sacar;
	private Pagamento pagamento;
	private Deposito deposito;
	private Transferencia transferencia;
	private TransferenciaBancos transferenciaBancos;
	private boolean extrato;
	
//	CONSTRUCTOR
	public Operacao() { }

	public Operacao(long cod, Conta conta, String dscOperacao, Date data,
			boolean extrato) {
		this.idOperacao = cod;
		this.conta = conta;
		this.descricaoOperacao = dscOperacao;
		this.dataOperacao = data;
		this.extrato = extrato;
	}
	
	public Operacao(long cod, Conta conta, String dscOperacao, Date data,
			Deposito deposito, boolean extrato) {

		this(cod, conta, dscOperacao, data, extrato);
		this.deposito = deposito;
	}
	public Operacao(long cod, Conta conta, String dscOperacao, Date data,
			Sacar sacar, boolean extrato) {

		this(cod, conta, dscOperacao, data, extrato);
		this.sacar = sacar;
	}
	public Operacao(long cod, Conta conta, String dscOperacao, Date data,
			Pagamento pagto, boolean extrato) {

		this(cod, conta, dscOperacao, data, extrato);
		this.pagamento = pagto;
	}
	public Operacao(long cod, Conta conta, String dscOperacao, Date data,
			Transferencia transferencia, boolean extrato) {

		this(cod, conta, dscOperacao, data, extrato);
		this.transferencia = transferencia;
	}
	public Operacao(long cod, Conta conta, String dscOperacao, Date data,
			TransferenciaBancos transfBancos, boolean extrato) {

		this(cod, conta, dscOperacao, data, extrato);
		this.transferenciaBancos = transfBancos;
	}

	
	public Operacao(String dscOperacao, Date data, long idTipOperacao, boolean extrato) {
		this.descricaoOperacao = dscOperacao;
		this.dataOperacao = data;
		
		setTipOperacao(dscOperacao, idTipOperacao);
		
		this.extrato = extrato;
	}

	public Operacao(Conta conta, String dscOperacao, Date data, Deposito deposito,
			boolean extrato) {
		this.conta = conta;
		this.descricaoOperacao = dscOperacao;
		this.dataOperacao = data;
		this.extrato = extrato;
		
		this.deposito = deposito;
	}
	
	public Operacao(Conta conta, String dscOperacao, Date data, Sacar sacar,
			boolean extrato) {
		this.conta = conta;
		this.descricaoOperacao = dscOperacao;
		this.dataOperacao = data;
		this.extrato = extrato;
		
		this.sacar = sacar;
	}
	
	public Operacao(Conta conta, String dscOperacao, Date data, Pagamento pagto,
			boolean extrato) {
		this.conta = conta;
		this.descricaoOperacao = dscOperacao;
		this.dataOperacao = data;
		this.extrato = extrato;
		
		this.pagamento = pagto;
	}
	
	public Operacao(Conta conta, String dscOperacao, Date data, Transferencia transferir,
			boolean extrato) {
		this.conta = conta;
		this.descricaoOperacao = dscOperacao;
		this.dataOperacao = data;
		this.extrato = extrato;
		
		this.transferencia = transferir;
	}

	public Operacao(Conta conta, String dscOperacao, Date data, 
			TransferenciaBancos transferirEntreBancos, boolean extrato) {
		this.conta = conta;
		this.descricaoOperacao = dscOperacao;
		this.dataOperacao = data;
		this.extrato = extrato;
		
		this.transferenciaBancos = transferirEntreBancos;
	}

//	METHODs
	
	/**
	 * @author 	Gilton Nascimento.
	 * @method 	setTipOperacao
	 * @since 	28/01/2019 - 19:56:35
	 * @param 	dsc_operacao
	 * @param 	id 
	 * @return  void
	 */
	private void setTipOperacao(String dsc_operacao, long id) {
		if( "depositar".equalsIgnoreCase(dsc_operacao) ) 
			this.deposito = new Deposito(id);
		if( "sacar".equalsIgnoreCase(dsc_operacao) ) 
			this.sacar = new Sacar(id);
		if( "pagar".equalsIgnoreCase(dsc_operacao) ) 
			this.pagamento = new Pagamento(id);
		if( "transferir".equalsIgnoreCase(dsc_operacao) ) 
			this.transferencia = new Transferencia(id);
		if( "transferirEntreBancos".equalsIgnoreCase(dsc_operacao) ) 
			this.transferenciaBancos = new TransferenciaBancos(id);
	}
	
	//	GETs and SETs
	public final String getDescricaoOperacao() {
		return descricaoOperacao;
	}

	public final void setDescricaoOperacao(String descricaoOperacao) {
		this.descricaoOperacao = descricaoOperacao;
	}


	public final Date getDataOperacao() {
		return dataOperacao;
	}


	public final void setDataOperacao(Date dataOperacao) {
		this.dataOperacao = dataOperacao;
	}


	public final Conta getConta() {
		return conta;
	}


	public final void setConta(Conta conta) {
		this.conta = conta;
	}


	public final Sacar getSacar() {
		return sacar;
	}


	public final void setSacar(Sacar sacar) {
		this.sacar = sacar;
	}
	
	public Pagamento getPagamento() {
		return pagamento;
	}
	
	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public final Deposito getDeposito() {
		return deposito;
	}


	public final void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}


	public final Transferencia getTransferencia() {
		return transferencia;
	}


	public final void setTransferencia(Transferencia transferencia) {
		this.transferencia = transferencia;
	}


	public final TransferenciaBancos getTransferenciaBancos() {
		return transferenciaBancos;
	}


	public final void setTransferenciaBancos(TransferenciaBancos transferenciaBancos) {
		this.transferenciaBancos = transferenciaBancos;
	}


	public final boolean isExtrato() {
		return extrato;
	}


	public final void setExtrato(boolean extrato) {
		this.extrato = extrato;
	}


	public final Long getIdOperacao() {
		return idOperacao;
	}
	
	
//	OVERRIDEs
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idOperacao == null) ? 0 : idOperacao.hashCode());
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
		Operacao other = (Operacao) obj;
		if (idOperacao == null) {
			if (other.idOperacao != null)
				return false;
		} else if (!idOperacao.equals(other.idOperacao))
			return false;
		return true;
	}

	
}
