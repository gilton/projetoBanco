package br.com.bank.bo;

import java.io.Serializable;

/**
 * @author 	Gilton Nascimento.
 * @since 	23/01/2019 - 18:43:13 
 * @version 0.1	
 */
public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idEmpresa;
	private String fantasia;
	private String razaoSocial;
	private String cnpj;
	
//	CONSTRUCT
	public Empresa() {}
	
	public Empresa(long id) { idEmpresa = id; }
	
	public Empresa(String fantasy, String rsocial, String cnpj) {
		fantasia = fantasy;
		razaoSocial = rsocial;
		this.cnpj = cnpj;
	}
	
	public Empresa(long cod, String fantasy, String rsocial, String cnpj) {
		this(fantasy, rsocial, cnpj);
		this.idEmpresa = cod;
	}

	public final String getFantasia() {
		return fantasia;
	}

	public final void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public final String getRazaoSocial() {
		return razaoSocial;
	}

	public final void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public final String getCnpj() {
		return cnpj;
	}

	public final void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public final Long getIdEmpresa() {
		return idEmpresa;
	}

//	OVERRIDEs
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((idEmpresa == null) ? 0 : idEmpresa.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		if (idEmpresa == null) {
			if (other.idEmpresa != null)
				return false;
		} else if (!idEmpresa.equals(other.idEmpresa))
			return false;
		return true;
	}
	
}