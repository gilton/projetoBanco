package br.com.bank.bo;

import java.io.Serializable;

/**
 * @author 	Gilton Nascimento.
 * @since 	23/01/2019 - 18:43:39 
 * @version	0.1
 */
public class Login implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idLogin;
	private String usuario;
	private String senha;
	private String email;

	
//	CONSTRUCTOR
	public Login() {}
	
	public Login(long id) { idLogin = id; }

	public Login(String user, String senha, String email) {
		usuario = user;
		this.senha = senha;
		this.email = email;
	}

	public Login(long id, String user, String senha, String email) {
		this(user, senha, email);
		idLogin = id;
	}

	//	GETs and SETs
	public final String getUsuario() {
		return usuario;
	}

	public final void setUsuario(String user) {
		this.usuario = user;
	}

	public final String getSenha() {
		return senha;
	}

	public final void setSenha(String senha) {
		this.senha = senha;
	}

	public final String getEmail() {
		return email;
	}

	public final void setEmail(String email) {
		this.email = email;
	}

	public final Long getIdLogin() {
		return idLogin;
	}

//	OVERRIDEs
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLogin == null) ? 0 : idLogin.hashCode());
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
		Login other = (Login) obj;
		if (idLogin == null) {
			if (other.idLogin != null)
				return false;
		} else if (!idLogin.equals(other.idLogin))
			return false;
		return true;
	}
	
	
	
}
