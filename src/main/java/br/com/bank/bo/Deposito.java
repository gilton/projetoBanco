package br.com.bank.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 	Gilton Nascimento.
 * @since 	23/01/2019 - 18:42:59 
 * @version	0.1
 */
public class Deposito implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Long idDeposito;
	private Date dataDeposito;
	private double valor;
	private boolean favorecido;
	private Conta conta;
	
//	CONSTRUCT
	public Deposito(){}

	public Deposito(long id) { this.idDeposito = id; }
	
	public Deposito(long cod, Conta conta, double vlr, Date data, boolean favorecido) {
		this.idDeposito = cod;
		this.conta = conta;
		this.valor = vlr;
		this.dataDeposito = data;
		this.favorecido = favorecido;
	}
	
	public Deposito(Conta conta, double vlr, Date data, boolean favorecido) {
		this.conta = conta;
		this.valor = vlr;
		this.dataDeposito = data;
		this.favorecido = favorecido;
	}

	public Deposito(Conta conta, double vlr, Date data) {
		this.conta = conta;
		this.valor = vlr;
		this.dataDeposito = data;
		this.favorecido = false;
	}

	//	GETs and SETs
	public final Date getDataDeposito() {
		return dataDeposito;
	}

	public final void setDataDeposito(Date dataDeposito) {
		this.dataDeposito = dataDeposito;
	}

	public final double getValor() {
		return valor;
	}

	public final void setValor(double valor) {
		this.valor = valor;
	}

	public boolean isFavorecido() {
		return favorecido;
	}

	public void setFavorecido(boolean favorecido) {
		this.favorecido = favorecido;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public final Long getIdDeposito() {
		return idDeposito;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idDeposito == null) ? 0 : idDeposito.hashCode());
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
		Deposito other = (Deposito) obj;
		if (idDeposito == null) {
			if (other.idDeposito != null)
				return false;
		} else if (!idDeposito.equals(other.idDeposito))
			return false;
		return true;
	}
	
}