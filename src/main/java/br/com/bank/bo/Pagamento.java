package br.com.bank.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 	Gilton Nascimento.
 * @since 	23/01/2019 - 18:43:54 
 * @version 0.1	
 */
public class Pagamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idPagamento;
	private String descricaoPagamento;
	private double vlrPagamento;
	private Date dataPagamento;
	private boolean comBarras;
	private String codBarras;
	private boolean debitoAutomatico;
	private Conta conta;
	
//	CONSTRUCTOR
	public Pagamento() {}

	public Pagamento(long id) { this.idPagamento = id;}
	
	public Pagamento(long cod, Conta conta, String dsc, double vlr, 
			Date data, boolean combarras, String codbarras, boolean debitoAuto) {
		this.idPagamento = cod;
		this.conta = conta;
		this.descricaoPagamento = dsc;
		this.vlrPagamento = vlr;
		this.dataPagamento = data;
		this.comBarras = combarras;
		this.codBarras = codbarras;
		this.debitoAutomatico = debitoAuto;
	}
	
	public Pagamento(Conta conta, String dsc, double vlr, Date data,
			boolean combarras, String codbarras, boolean debitoAuto) {
		this.conta = conta;
		this.descricaoPagamento = dsc;
		this.vlrPagamento = vlr;
		this.dataPagamento = data;
		this.comBarras = combarras;
		this.codBarras = codbarras;
		this.debitoAutomatico = debitoAuto;
	}


	public Pagamento(Conta conta, String dscPagto, double vlr, 
			Date data, boolean com_barras, String cod_barras) {
		this.conta = conta;
		this.descricaoPagamento = dscPagto;
		this.vlrPagamento = vlr;
		this.dataPagamento = data;
		this.comBarras = com_barras;
		this.codBarras = cod_barras;
		this.debitoAutomatico = false;
	}

	//	GETs and SETs
	public final String getDescricaoPagamento() {
		return descricaoPagamento;
	}

	public final void setDescricaoPagamento(String descricaoPagamento) {
		this.descricaoPagamento = descricaoPagamento;
	}

	public final double getVlrPagamento() {
		return vlrPagamento;
	}

	public final void setVlrPagamento(double vlrPagamento) {
		this.vlrPagamento = vlrPagamento;
	}

	public final Date getDataPagamento() {
		return dataPagamento;
	}

	public final void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public final boolean isComBarras() {
		return comBarras;
	}

	public final void setComBarras(boolean comBarras) {
		this.comBarras = comBarras;
	}

	public final String getCodBarras() {
		return codBarras;
	}

	public final void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}

	public final boolean isDebitoAutomatico() {
		return debitoAutomatico;
	}

	public final void setDebitoAutomatico(boolean debitoAutomatico) {
		this.debitoAutomatico = debitoAutomatico;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public final Long getIdPagamento() {
		return idPagamento;
	}


//	OVERRIDEs
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idPagamento == null) ? 0 : idPagamento.hashCode());
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
		Pagamento other = (Pagamento) obj;
		if (idPagamento == null) {
			if (other.idPagamento != null)
				return false;
		} else if (!idPagamento.equals(other.idPagamento))
			return false;
		return true;
	}
	
	
}