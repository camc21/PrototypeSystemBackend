package br.com.carlos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.carlos.dto.FunctionalityAccessProfileDTO;
import br.com.carlos.model.AccessProfile;
import feign.Param;

@Repository
public interface AccessProfileRepository extends JpaRepository<AccessProfile, Long> {

	@Query("SELECT f.id" + " FROM AccessProfileHasFunctionalities aphf" + " INNER JOIN aphf.accessProfile ap"
			+ " JOIN aphf.functionality f" + " WHERE ap.id = :idProfile")
	List<Long> findIdsFromFunctionalitiesForIdProfile(@Param("idProfile") Long idProfile);

	@Query("SELECT new"
			+ " br.com.carlos.dto.FunctionalityAccessProfileDTO(ap.id, ap.nome, f.id, f.nome, aphf.writePermission, aphf.readPermission)"
			+ " FROM AccessProfileHasFunctionalities aphf" + " INNER JOIN aphf.accessProfile ap"
			+ " INNER JOIN aphf.functionality f" + " WHERE ap.id in (idsPerfis)")
	List<FunctionalityAccessProfileDTO> retrievePermissionsForIdsProfiles(@Param("idsPerfis") List<Long> idsPerfis);

	@Query("SELECT new"
			+ " br.com.carlos.dto.FunctionalityAccessProfileDTO(ap.id, ap.nome, f.id, f.nome, aphf.writePermission, aphf.readPermission)"
			+ " FROM Login l"
			+ " INNER JOIN l.accessProfiles ap"
			+ " INNER JOIN ap.accessProfileHasFunctionalities aphf"
			+ " INNER JOIN aphf.accessProfile ap"
			+ " INNER JOIN aphf.functionality f"
			+ " WHERE l.id = :idLogin")
	List<FunctionalityAccessProfileDTO> retrievePermissionsForIdLogin(@Param("idLogin") Long idLogin);

	@Query("SELECT COUNT(aphf.readPermission) > 0" + " FROM br.com.carlos.model.AccessProfileHasFunctionalities aphf"
			+ " INNER JOIN aphf.accessProfile ap" + " INNER JOIN aphf.functionality f"
			+ " WHERE f.nome like ?1 and ap.id = ?2 and aphf.writePermission = true")
	boolean profileHasReadPermissionToFunctionalityForFunctionalityNameAndIdProfile(@Param("functionalityName") String functionalityName,
			@Param("idPerfil") Long idPerfil);

	@Query("SELECT COUNT(aphf.writePermission) > 0" + " FROM br.com.carlos.model.AccessProfileHasFunctionalities aphf"
			+ " INNER JOIN aphf.accessProfile ap" + " INNER JOIN aphf.functionality f"
			+ " WHERE f.nome like ?1 and ap.id = ?2 and aphf.writePermission = true")
	boolean profileHasWritePermissionToFunctionalityForFunctionalityNameAndIdProfile(@Param("functionalityName") String functionalityName,
			@Param("idPerfil") Long idPerfil);
}