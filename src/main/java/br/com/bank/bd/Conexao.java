package br.com.bank.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 	Gilton Nascimento.
 * @since 	23/01/2019 - 18:26:00 
 * @version 0.2
 */
public class Conexao {


	private static Connection conn;
	static SingletonConnectionEnum conect;
	
	private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
	private static final String JDBC_POSTGRES_URL = "jdbc:postgresql://localhost:5432/";
	private static final String BANCO = "mybank";
	private static final String SENHA = "test";
	private static final String USER = "teste";

	
	public Conexao() { }
	
	
	protected static Connection getConecta() {
		conectDriverDB();
		try {
			conn.setAutoCommit(false);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * @author 	Gilton Nascimento.
	 * @method 	conectaSingletonEnum Conexao
	 * @since 	23/01/2019 - 18:11:12
	 * @param 	-
	 * @return  Connection
	 */
	public static Connection conectaSingletonEnum() {
		conect = SingletonConnectionEnum.INSTANCE;
		conect.setConnect( Conexao.getConecta() );
		return conect.getConnect();
	}
	
	
	/**
	 * @author 	Gilton Nascimento.
	 * @method 	chamaDriverBD Conexao
	 * @since 	23/01/2019 - 11:24:47
	 * @param 	-
	 * @return  void
	 */
	private static void chamaDriverBD() {
		try {
			Class.forName(POSTGRESQL_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author 	Gilton Nascimento.
	 * @method 	conectDriverDB Conexao
	 * @since 	23/01/2019 - 11:25:29
	 * @param 	-
	 * @return  void
	 */
	private static void conectDriverDB(){
		chamaDriverBD();
		try {
			conn = DriverManager.getConnection(JDBC_POSTGRES_URL+BANCO, USER, SENHA);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author 	Gilton Nascimento.
	 * @method 	isConnected Conexao
	 * @since 	23/01/2019 - 11:26:20
	 * @param 	-
	 * @throws SQLException
	 * @return  String
	 */
	public static String isConnected() throws SQLException {
		return conn.isClosed() ? "Conexão Fechada!":"Conexão Aberta!";
	}
	
	public static void closeBD(){
		try {
			if(conn != null) 	{ conn.close(); }
		}
		catch (SQLException e) 	{ e.printStackTrace(); }
//		finally 				{ System.out.println("Banco fechado!"); }
	}
	
	/**
	 * @author 	Gilton Nascimento.
	 * @method 	closeBD Conexao
	 * @since 	23/01/2019 - 11:31:37
	 * @param	result
	 * @param 	pstmt
	 * @param 	connection
	 * @return  void
	 */
	public static void closeBD(ResultSet result, PreparedStatement pstmt, Connection connection) {
		try {
			if(result != null) 		result.close();
			if(pstmt != null) 		pstmt.close();
			if(connection != null) 	connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}