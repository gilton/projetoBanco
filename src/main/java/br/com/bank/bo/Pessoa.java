package br.com.bank.bo;

import java.io.Serializable;


/**
 * @author 	Gilton Nascimento.
 * @since 	23/01/2019 - 18:29:57 
 * @version	0.1
 */
public class Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long idPessoa;
	private String nome;
	private String cpf;
	private char sexo;

	
//	CONSTRUCTOR
	public Pessoa() {}
	
	public Pessoa(long id) { idPessoa = id; }
	
	public Pessoa(String nome, String cpf, char sex) {
		this.nome = nome;
		this.cpf = cpf;
		this.sexo = sex;
	}
	
	public Pessoa(long id, String nome, String cpf, char sex) {
		this(nome, cpf, sex);
		this.idPessoa = id;
	}
	
	public final String getNome() {
		return nome;
	}

	public final void setNome(String nome) {
		this.nome = nome;
	}

	public final String getCpf() {
		return cpf;
	}

	public final void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public final char getSexo() {
		return sexo;
	}

	public final void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public final Long getIdPessoa() {
		return idPessoa;
	}

//	OVERRIDEs
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((idPessoa == null) ? 0 : idPessoa.hashCode());
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
		Pessoa other = (Pessoa) obj;
		if (idPessoa == null) {
			if (other.idPessoa != null)
				return false;
		} else if (!idPessoa.equals(other.idPessoa))
			return false;
		return true;
	}
	
	
	
}
