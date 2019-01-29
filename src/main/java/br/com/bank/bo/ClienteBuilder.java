package br.com.bank.bo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

@SuppressWarnings("unused")
public class ClienteBuilder implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final Long idCliente;
	private final String descricaoCliente;
	private final Date dataCadastro;
	private final String tipoCliente;
	
	private final Login login;
	private final Localidade localidade;
	private final Pessoa pessoa;
	private final Empresa empresa;
	
	public static class Builder {

		private Long idCliente;
		private String descricaoCliente;
		private Date dataCadastro;
		private String tipoCliente;
		
		private Login login;
		private Localidade localidade;
		private Pessoa pessoa;
		private Empresa empresa;

        //builder methods for setting property
        public Builder idCliente(long cod){this.idCliente = cod; return this; }
        public Builder descricaoCliente(String dscCliente){this.descricaoCliente = dscCliente; return this; }
        public Builder dataCadastro(Date agora){this.dataCadastro = agora; return this; }
        public Builder tipoCliente(String tipCliente){this.tipoCliente = tipCliente; return this; }
        public Builder login(Login login){this.login = login; return this; }
        public Builder localidade(Localidade local){this.localidade = local; return this; }
        public Builder pessoa(Pessoa pessoa){this.pessoa = pessoa; return this; }
        public Builder empresa(Empresa empresa){this.empresa = empresa; return this; }
      
      
        //return fully build object
        public ClienteBuilder build() {
            return new ClienteBuilder(this);
        }
	}
/*
Read more: 
https://javarevisited.blogspot.com/2012/06/builder-design-pattern-in-java-example.html#ixzz5dYbFtCh3
*/
	
	private ClienteBuilder(Builder builder) {
		this.idCliente = builder.idCliente;
		this.descricaoCliente = builder.descricaoCliente;
		this.dataCadastro = builder.dataCadastro;
		this.tipoCliente = builder.tipoCliente;
		this.login = builder.login;
		this.localidade = builder.localidade;
		this.pessoa = builder.pessoa;
		this.empresa = builder.empresa;
	}
	
		
//	OVERRIDES
	
	@Override
	public String toString() {
		return 
				"Cliente ("+idCliente+") \n"
				+ "nome: "+pessoa.getNome()+" \n"
//				+ "empresa: "+empresa.getFantasia()+" \n"
				+ "descricao: "+descricaoCliente+" \n"
				+ "cadastro: "+new SimpleDateFormat("dd/MMM/yyyy").format(dataCadastro) +" \n"
				+ "tipo: "+tipoCliente+" \n"
				+ "endereco: "+localidade.getEndereco()+" - Nº "+ localidade.getNum() +" \n"
//				+ "user ("+login.getUsuario()+") "
				;
	}
	
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
		ClienteBuilder other = (ClienteBuilder) obj;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		return true;
	}

	
}