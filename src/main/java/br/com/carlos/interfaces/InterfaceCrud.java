package br.com.carlos.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

public interface InterfaceCrud<T, T2, T3> {
	
	@PreAuthorize("hasAuthority('ADMIN') and hasAuthority('USER')")
	public List<T> findAll();
	
	@PreAuthorize("hasAuthority('ADMIN') and hasAuthority('USER')")
	public Optional<T> findById(Long id);
	
	@PreAuthorize("hasAuthority('ADMIN') and hasAuthority('USER')")
	public void save(T3 t);
	
	@PreAuthorize("hasAuthority('ADMIN') and hasAuthority('USER')")
	public void update(T2 t);
	
	@PreAuthorize("hasAuthority('ADMIN') and hasAuthority('USER')")
	public void delete(Long id);
	
}
