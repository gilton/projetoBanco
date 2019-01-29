package br.com.bank.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 	Gilton Nascimento.
 * @since 	23/01/2019 - 18:50:19 
 * @version	0.1
 */
public class Sacar implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idSacar;
	private Conta conta;
	private double valor;
	private Date dataSacar;
	
//	CONSTRUCTOR
	public Sacar() {}
	
	public Sacar(long id) { this.idSacar = id; }
	
	public Sacar(long cod, Conta conta, double vlr, Date data) {
		this.idSacar = cod;
		this.conta = conta;
		this.valor = vlr;
		this.dataSacar = data;
	}

	public Sacar(Conta conta, double vlr, Date data) {
		this.conta = conta;
		this.valor = vlr;
		this.dataSacar = data;
	}

	//	GETs and SETs
	public final double getValor() {
		return valor;
	}

	public final void setValor(double valor) {
		this.valor = valor;
	}

	public final Date getDataSacar() {
		return dataSacar;
	}

	public final void setDataSacar(Date dataSacar) {
		this.dataSacar = dataSacar;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public final Long getIdSacar() {
		return idSacar;
	}

//	OVERRIDEs
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idSacar == null) ? 0 : idSacar.hashCode());
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
		Sacar other = (Sacar) obj;
		if (idSacar == null) {
			if (other.idSacar != null)
				return false;
		} else if (!idSacar.equals(other.idSacar))
			return false;
		return true;
	}
	
	
}