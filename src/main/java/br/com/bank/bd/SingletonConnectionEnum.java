package br.com.bank.bd;

import java.sql.Connection;

public enum SingletonConnectionEnum {
	INSTANCE;
	
	private Connection connect;

	Connection getConnect() {
		return connect;
	}

	void setConnect(Connection connect) {
		this.connect = connect;
	}

}
