package br.com.bank.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 	Gilton Nascimento.
 * @since 	23/01/2019 - 18:50:07 
 * @version	0.1
 */
public class Transferencia implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idTransferencia;
	private Conta conta;
	private Conta destinatario;
	private Date dataTransferencia;
	private double valor;
	private boolean depositoJudicial;
	
//	CONSTRUCTOR
	public Transferencia() {}
	
	public Transferencia(long id) { this.idTransferencia = id; }

	public Transferencia(long cod, Conta conta, Conta destino, double vlr,
			Date data, boolean depJudicial) {
		this.idTransferencia = cod;
		this.conta = conta;
		this.destinatario = destino;
		this.valor = vlr;
		this.dataTransferencia = data;
		this.depositoJudicial = depJudicial;
	}
	
	public Transferencia(Conta conta, Conta destino, double vlr,
			Date data, boolean depJudicial) {
		this.conta = conta;
		this.destinatario = destino;
		this.valor = vlr;
		this.dataTransferencia = data;
		this.depositoJudicial = depJudicial;
	}
	
	public Transferencia(Conta conta, Conta destino, double vlr, Date data) {
		this.conta = conta;
		this.destinatario = destino;
		this.valor = vlr;
		this.dataTransferencia = data;
		this.depositoJudicial = false;
	}

	//	METHDOs
	/** Atualiza Saldos entre Contas
	 * @author 	Gilton Nascimento.
	 * @method 	atualizarSaldos
	 * @since 	28/01/2019 - 00:18:48
	 * @param 	valor
	 * @param 	benfeitor
	 * @param 	beneficiado 
	 * @return  void
	 */
	public void atualizarSaldos(double valor, Conta benfeitor, Conta beneficiado) {
		benfeitor.debitar(valor);
		beneficiado.creditar(valor);
	}
	
	
	//	GETs and SETs
	public final Date getDataTransferencia() {
		return dataTransferencia;
	}

	public final void setDataTransferencia(Date dataTransferencia) {
		this.dataTransferencia = dataTransferencia;
	}

	public final double getValor() {
		return valor;
	}

	public final void setValor(double valor) {
		this.valor = valor;
	}

	public final boolean isDepositoJudicial() {
		return depositoJudicial;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Conta getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Conta destinatario) {
		this.destinatario = destinatario;
	}

	public final void setDepositoJudicial(boolean depositoJudicial) {
		this.depositoJudicial = depositoJudicial;
	}

	public final Long getIdTransferencia() {
		return idTransferencia;
	}

//	OVERRIDEs
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idTransferencia == null) ? 0 : idTransferencia.hashCode());
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
		Transferencia other = (Transferencia) obj;
		if (idTransferencia == null) {
			if (other.idTransferencia != null)
				return false;
		} else if (!idTransferencia.equals(other.idTransferencia))
			return false;
		return true;
	}
	
	
	
}