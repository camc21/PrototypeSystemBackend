package br.com.carlos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.carlos.dto.ComboBoxDTO;
import br.com.carlos.model.Functionality;

@Repository
public interface FunctionalityRepository extends JpaRepository<Functionality, Long> {

	@Query("SELECT"
			+ " new br.com.carlos.dto.ComboBoxDTO(f.id, f.name)"
			+ " FROM Functionality f")
	List<ComboBoxDTO> comboBox();
	
	@Query("SELECT"
			+ " new br.com.carlos.model.Functionality(f.id, f.name)"
			+ " FROM Functionality f"
			+ " where f.id in (:ids)")
	List<Functionality> findFunctionalityByIds(List<Long> ids);
	
}