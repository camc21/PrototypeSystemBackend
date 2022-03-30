package br.com.carlos.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.carlos.dto.AccessProfileDTO;
import br.com.carlos.dto.AccessProfileHasFunctionalityDTO;
import br.com.carlos.dto.ComboBoxDTO;
import br.com.carlos.dto.FunctionalityAccessProfileDTO;
import br.com.carlos.model.AccessProfile;
import feign.Param;

@Repository
public interface AccessProfileRepository extends JpaRepository<AccessProfile, Long> {

	@Query("SELECT new AccessProfile(ap.id, ap.name, ap.description)" + " FROM AccessProfile ap")
	public List<AccessProfile> findAll();

	@Query("SELECT new br.com.carlos.dto.AccessProfileDTO(ap.id, ap.name, ap.description)" + " FROM AccessProfile ap")
	public Page<AccessProfileDTO> findAllPage(Pageable pageable);

	@Query("SELECT new AccessProfile(ap.id, ap.name, ap.description)" + " FROM AccessProfile ap" + " WHERE ap.id = :id")
	public AccessProfile findAccessProfileById(@Param("id") Long id);

	@Query("SELECT distinct new"
			+ " br.com.carlos.dto.FunctionalityAccessProfileDTO(ap.id, ap.name, ap.description, f.id, f.name, aphf.writePermission, aphf.readPermission)"
			+ " FROM AccessProfile ap" 
			+ " INNER JOIN ap.accessProfileHasFunctionalities aphf"
			+ " INNER JOIN aphf.functionality f"
			+ " WHERE ap.id = :idAccessProfile")
	List<FunctionalityAccessProfileDTO> loadPermissionsByIdAccessProfile(@Param("idAccessProfile") Long idAccessProfile);

	@Query("SELECT new"
			+ " br.com.carlos.dto.FunctionalityAccessProfileDTO(ap.id, ap.name, ap.description, f.id, f.name, aphf.writePermission, aphf.readPermission)"
			+ " FROM Login l" + " INNER JOIN l.accessProfiles ap"
			+ " INNER JOIN ap.accessProfileHasFunctionalities aphf" + " INNER JOIN aphf.functionality f"
			+ " WHERE l.id = :idLogin")
	List<FunctionalityAccessProfileDTO> retrievePermissionsForIdLogin(@Param("idLogin") Long idLogin);

	@Query("SELECT" + " new br.com.carlos.dto.ComboBoxDTO(ap.id, ap.name)" + " FROM AccessProfile ap")
	List<ComboBoxDTO> comboBox();

	@Query("SELECT new br.com.carlos.dto.ComboBoxDTO(ap.id, ap.name)" + " FROM Login l"
			+ " inner join l.accessProfiles ap" + " WHERE l.id = :idLogin")
	public List<ComboBoxDTO> findComboBoxByIdLogin(@Param("idLogin") Long idLogin);

//	@Query("SELECT f.id" + " FROM AccessProfileHasFunctionalities aphf" + " INNER JOIN aphf.accessProfile ap"
//			+ " JOIN aphf.functionality f" + " WHERE ap.id = :idProfile")
//	List<Long> findIdsFromFunctionalitiesForIdProfile(@Param("idProfile") Long idProfile);
//
//	@Query("SELECT new"
//			+ " br.com.carlos.dto.FunctionalityAccessProfileDTO(ap.id, ap.nome, f.id, f.name, aphf.writePermission, aphf.readPermission)"
//			+ " FROM AccessProfileHasFunctionalities aphf" + " INNER JOIN aphf.accessProfile ap"
//			+ " INNER JOIN aphf.functionality f" + " WHERE ap.id in (idsPerfis)")
//	List<FunctionalityAccessProfileDTO> retrievePermissionsForIdsProfiles(@Param("idsPerfis") List<Long> idsPerfis);
//
//	@Query("SELECT COUNT(aphf.readPermission) > 0" + " FROM br.com.carlos.model.AccessProfileHasFunctionalities aphf"
//			+ " INNER JOIN aphf.accessProfile ap" + " INNER JOIN aphf.functionality f"
//			+ " WHERE f.name like ?1 and ap.id = ?2 and aphf.writePermission = true")
//	boolean profileHasReadPermissionToFunctionalityForFunctionalityNameAndIdProfile(@Param("functionalityName") String functionalityName,
//			@Param("idPerfil") Long idPerfil);
//
//	@Query("SELECT COUNT(aphf.writePermission) > 0" + " FROM br.com.carlos.model.AccessProfileHasFunctionalities aphf"
//			+ " INNER JOIN aphf.accessProfile ap" + " INNER JOIN aphf.functionality f"
//			+ " WHERE f.name like ?1 and ap.id = ?2 and aphf.writePermission = true")
//	boolean profileHasWritePermissionToFunctionalityForFunctionalityNameAndIdProfile(@Param("functionalityName") String functionalityName,
//			@Param("idPerfil") Long idPerfil);
}