package br.com.carlos.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

public interface InterfaceCrud<T, T2, T3> {
	
	public List<T> findAll();
	
	public Page<T2> findAllPage(Integer pageNo, Integer pageSize, String sortBy);
	
	public Optional<T> findById(Long id);
	
	public void save(T3 t);
	
	public void update(T2 t);
	
	public void delete(Long id);
}
