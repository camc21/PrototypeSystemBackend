package br.com.carlos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carlos.dto.FunctionalityAccessProfileDTO;
import br.com.carlos.model.AccessProfile;
import br.com.carlos.repository.AccessProfileRepository;
import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class AccessProfileService {

	@Autowired
	private AccessProfileRepository accessProfileRepository;

	public AccessProfile findById(Long idProfile) throws ObjectNotFoundException {
		Optional<AccessProfile> obj = accessProfileRepository.findById(idProfile);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Perfil não encontrado!"));
	}

	public List<Long> findIdsFromFunctionalitiesForIdProfile(Long idPerfil) {
		return accessProfileRepository.findIdsFromFunctionalitiesForIdProfile(idPerfil);
	}

	public List<FunctionalityAccessProfileDTO> retrievePermissionsForIdsProfiles(List<Long> idsPerfis) {
		return accessProfileRepository.retrievePermissionsForIdsProfiles(idsPerfis);
	}
	
	public List<FunctionalityAccessProfileDTO> retrievePermissionsForIdLogin(Long idLogin) {
		return accessProfileRepository.retrievePermissionsForIdLogin(idLogin);
	}

	public boolean profileHasReadPermissionToFunctionalityForFunctionalityNameAndIdProfile(String functionalityName,
			Long idPerfil) {
		return accessProfileRepository
				.profileHasReadPermissionToFunctionalityForFunctionalityNameAndIdProfile(functionalityName, idPerfil);
	}

	public boolean profileHasWritePermissionToFunctionalityForFunctionalityNameAndIdProfile(String functionalityName,
			Long idPerfil) {
		return accessProfileRepository
				.profileHasWritePermissionToFunctionalityForFunctionalityNameAndIdProfile(functionalityName, idPerfil);
	}

}
