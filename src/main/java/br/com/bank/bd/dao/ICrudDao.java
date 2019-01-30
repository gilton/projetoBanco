package br.com.bank.bd.dao;


public interface ICrudDao <T extends Object> {
	
	long inserir(T t);
	void alterar(T t);
	void excluir(long id);
	
	T getPojoByID(long id);
//	T getPojoBySTR(String str);
	
	java.util.List<T> getALL();
	
//	AUXILIARs
	
}