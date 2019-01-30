package br.com.bank.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 	Gilton Nascimento.
 * @since 	23/01/2019 - 18:40:18 
 * @version 0.1	
 */
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idCliente;
	private String descricaoCliente;
	private Date dataCadastro;
	private String tipoCliente;
	
	private Login login;
	private Localidade localidade;
	private Pessoa pessoa;
	private Empresa empresa;
	
	
	
//	CONSTRUCTOR
	public Cliente() {}
	
	public Cliente(long id){ idCliente = id; }
	
	public Cliente(String dscCliente, Date datCadastro, String tipCliente) {
		descricaoCliente = dscCliente;
		dataCadastro = datCadastro;
		tipoCliente = tipCliente;
	}
	
	public Cliente(String dscCliente, Date datCadastro, String tipCliente,
			Login login) {
		this(dscCliente, datCadastro, tipCliente);
		this.login = login;
	}
	
	public Cliente(String dscCliente, Date datCadastro, String tipCliente,
			Localidade local) {
		this(dscCliente, datCadastro, tipCliente);
		this.localidade = local;
	}
	
	public Cliente(String dscCliente, Date datCadastro, String tipCliente,
			Localidade local, Pessoa pessoa) {
		this(dscCliente, datCadastro, tipCliente, local);
		this.pessoa = pessoa;
	}
	
	public Cliente(String dscCliente, Date datCadastro, String tipCliente,
			Localidade local, Empresa empresa) {
		this(dscCliente, datCadastro, tipCliente, local);
		this.empresa = empresa;
	}
	
	public Cliente(long cod, String desc, Date data, String tipo,
			Localidade local, Pessoa pessoa) {
		this(desc, data, tipo, local);
		this.pessoa = pessoa;
		this.idCliente = cod;
	}
	
	public Cliente(long cod, String desc, Date data, String tipo,
			Localidade local, Pessoa pessoa, Empresa empresa) {
		this(desc, data, tipo, local, empresa);
		this.pessoa = pessoa;
		this.idCliente = cod;
	}
	
	public Cliente(long cod, String desc, Date data, String tipo,
			Localidade local, Pessoa pessoa, Empresa empresa, Login login) {
		this(cod, desc, data, tipo, local, pessoa, empresa);
		this.login = login;
	}

	public Cliente(String desc, Date data, String tipo,
			Pessoa pessoa, Localidade local, Login login) {
		this(desc, data, tipo, local, pessoa);
		this.login = login;
	}


	//	GETs and SETs
	public final String getDescricaoCliente() {
		return descricaoCliente;
	}

	public final void setDescricaoCliente(String descricaoCliente) {
		this.descricaoCliente = descricaoCliente;
	}

	public final Date getDataCadastro() {
		return dataCadastro;
	}

	public final void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public final String getTipoCliente() {
		return tipoCliente;
	}

	public final void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Localidade getLocalidade() {
		return localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public final Long getIdCliente() {
		return idCliente;
	}

	
//	OVERRIDES
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idCliente == null) ? 0 : idCliente.hashCode());
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
		Cliente other = (Cliente) obj;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		return true;
	}
	
	
}