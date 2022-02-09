package br.com.carlos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.carlos.dto.SystemParameterDTO;
import br.com.carlos.model.SystemParameter;

public interface SystemParameterRepository extends JpaRepository<SystemParameter, Long> {
	
	SystemParameter findByParameterName(String name);

	@Query("SELECT"
			+ " new br.com.carlos.dto.SystemParameterDTO(sp.id, sp.parameterName, sp.parameterDescription, sp.parameterValue)"
			+ " FROM SystemParameter sp")
	Page<SystemParameterDTO> parameterSystemList(PageRequest pageable);
	
}
