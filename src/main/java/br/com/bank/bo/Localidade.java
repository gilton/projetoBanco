package br.com.bank.bo;

import java.io.Serializable;

/**
 * @author 	Gilton Nascimento.
 * @since 	23/01/2019 - 18:43:30 
 * @version 0.1	
 */
public class Localidade implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idLocalidade;
	private String endereco;
	private int num;
	private String cep;
	private String bairro;
	
//	CONSTRUCTOR
	public Localidade() {}
	
	public Localidade(long id) { idLocalidade = id; }
	
	public Localidade( String endereco, int num, String bairro, String cep ) {
		this.endereco = endereco;
		this.num = num;
		this.bairro = bairro;
		this.cep = cep;
	}
	
	public Localidade( long id, String endereco, int num, String bairro, String cep ) {
		this(endereco, num, bairro, cep);
		this.idLocalidade = id;
	}
	
//	GETs and SETs
	public final String getEndereco() {
		return endereco;
	}

	public final void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public final int getNum() {
		return num;
	}

	public final void setNum(int num) {
		this.num = num;
	}

	public final String getCep() {
		return cep;
	}

	public final void setCep(String cep) {
		this.cep = cep;
	}

	public final String getBairro() {
		return bairro;
	}

	public final void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public final Long getIdLocalidade() {
		return idLocalidade;
	}

//	OVERRIDEs
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idLocalidade == null) ? 0 : idLocalidade.hashCode());
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
		Localidade other = (Localidade) obj;
		if (idLocalidade == null) {
			if (other.idLocalidade != null)
				return false;
		} else if (!idLocalidade.equals(other.idLocalidade))
			return false;
		return true;
	}
	
	
	
}
