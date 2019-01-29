package br.com.bank.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 	Gilton Nascimento.
 * @since 	23/01/2019 - 18:49:16 
 * @version 0.1	
 */
public class TransferenciaBancos implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idTransferenciaBancos;
	private Conta conta;
	private Date dataTransferenciaBancos;
	private String cpf;
	private String cnpj;
	private double valor;
	private String tipoDeTransferencia;
	
	
//	CONSTRUCTOR
	public TransferenciaBancos() {}

	public TransferenciaBancos(long id) { this.idTransferenciaBancos = id; }

	public TransferenciaBancos(long cod, Conta conta, Date data, String cnpj_cpf,
			double vlr, String tipTransf) {
		this.idTransferenciaBancos = cod;
		this.conta = conta;
		this.dataTransferenciaBancos = data;
		
		definirCNPJ_CPF(cnpj_cpf);
		
		this.valor = vlr;
		this.tipoDeTransferencia = tipTransf;
	}
	
	public TransferenciaBancos(Conta conta, Date data, String cnpj_cpf,
			double vlr, String tipTransf) {
		this.conta = conta;
		this.dataTransferenciaBancos = data;
		
		definirCNPJ_CPF(cnpj_cpf);
		
		this.valor = vlr;
		this.tipoDeTransferencia = tipTransf;
	}
	
//	METHODs
	
	/**
	 * @author 	Gilton Nascimento.
	 * @method 	atualizarSaldoDaConta
	 * @since 	28/01/2019 - 01:20:58
	 * @param 	vlr
	 * @return  double
	 */
	public double atualizarSaldoDaConta( double vlr ) {
		return conta.getSaldo() - vlr;
	}
	
	/**
	 * @author 	Gilton Nascimento.
	 * @method 	definirCNPJ_CPF
	 * @since 	28/01/2019 - 01:05:36
	 * @param 	cnpj_cpf
	 * @return  String
	 */
	private String definirCNPJ_CPF( String cnpj_cpf ) {
		if( "cnpj".equalsIgnoreCase(cnpj_cpf) )
			return this.cnpj = cnpj_cpf;
		else 
			return this.cpf = cnpj_cpf;
		
	}
	
	//	GETs and SETs
	public final String getCpf() {
		return cpf;
	}

	public final void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public final String getCnpj() {
		return cnpj;
	}

	public final void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public final Date getDataTransferenciaBancos() {
		return dataTransferenciaBancos;
	}

	public final void setDataTransferenciaBancos(Date dataTransferenciaBancos) {
		this.dataTransferenciaBancos = dataTransferenciaBancos;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public final String getTipoDeTransferencia() {
		return tipoDeTransferencia;
	}

	public final void setTipoDeTransferencia(String tipoDeTransferencia) {
		this.tipoDeTransferencia = tipoDeTransferencia;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public final Long getIdTransferenciaBancos() {
		return idTransferenciaBancos;
	}

//	OVERRIDEs
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idTransferenciaBancos == null) ? 0 : idTransferenciaBancos
						.hashCode());
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
		TransferenciaBancos other = (TransferenciaBancos) obj;
		if (idTransferenciaBancos == null) {
			if (other.idTransferenciaBancos != null)
				return false;
		} else if (!idTransferenciaBancos.equals(other.idTransferenciaBancos))
			return false;
		return true;
	}
	
	
}
