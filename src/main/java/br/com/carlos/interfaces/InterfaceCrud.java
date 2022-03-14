package br.com.carlos.interfaces;

public interface InterfaceCrud<T> {

	public void save(T dto);
	
	public void update(T dto);
	
	public void delete(Long id);
}
