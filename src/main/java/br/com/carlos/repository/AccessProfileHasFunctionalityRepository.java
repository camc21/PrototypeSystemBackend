package br.com.carlos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.carlos.dto.FunctionalityAccessProfileDTO;
import br.com.carlos.model.AccessProfileHasFunctionality;
import feign.Param;

@Repository
public interface AccessProfileHasFunctionalityRepository extends JpaRepository<AccessProfileHasFunctionality, Long> {
	
	@Query("SELECT new"
			+ " br.com.carlos.dto.FunctionalityAccessProfileDTO(ap.id, ap.name, ap.description, f.id, f.name, aphf.writePermission, aphf.readPermission)"
			+ " FROM AccessProfileHasFunctionality aphf"
			+ " INNER JOIN aphf.accessProfile ap"
			+ " INNER JOIN aphf.functionality f"
			+ " WHERE ap.id = :id")
	public List<FunctionalityAccessProfileDTO> findFunctionalityPermissionListDtoByIdAccessProfile(@Param("id") Long id);
}