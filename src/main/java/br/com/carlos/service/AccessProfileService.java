package br.com.carlos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.carlos.dto.AccessProfileDTO;
import br.com.carlos.dto.ComboBoxDTO;
import br.com.carlos.dto.FunctionalityAccessProfileDTO;
import br.com.carlos.interfaces.InterfaceCrud;
import br.com.carlos.model.AccessProfile;
import br.com.carlos.model.AccessProfileFunctionalityKey;
import br.com.carlos.model.AccessProfileHasFunctionality;
import br.com.carlos.model.Functionality;
import br.com.carlos.repository.AccessProfileRepository;

@Service
public class AccessProfileService implements InterfaceCrud<AccessProfileDTO> {

	@Autowired
	private AccessProfileRepository accessProfileRepository;
	
	@Autowired
	private AccessProfileHasFunctionalityService accessProfileHasFunctionalityService;

	@PreAuthorize("hasAuthority('REGISTER_ACCESS_PROFILE_READING')")
	public List<AccessProfile> findAll() {
		List<AccessProfile> accessProfileList = accessProfileRepository.findAll();
		if (!accessProfileList.isEmpty()) {
			return accessProfileList;
		}
		return new ArrayList<>();
	}

	@PreAuthorize("hasAuthority('REGISTER_ACCESS_PROFILE_READING')")
	public Page<AccessProfileDTO> findAllPage(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<AccessProfileDTO> accessProfileList = accessProfileRepository.findAllPage(paging);
		for (AccessProfileDTO ap : accessProfileList) {
			ap.setPermissions(accessProfileRepository.loadPermissionsByIdAccessProfile(ap.getId()));
		}
		if (!accessProfileList.isEmpty()) {
			return accessProfileList;
		}
		return null;
	}

	@PreAuthorize("hasAuthority('REGISTER_ACCESS_PROFILE_READING')")
	public Optional<AccessProfileDTO> findById(Long id) {
		Optional<AccessProfile> accessProfile = accessProfileRepository.findById(id);
		Optional<AccessProfileDTO> accessProfileDto = Optional.ofNullable(new AccessProfileDTO(accessProfile.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil de Acesso não encontrado"))));
		List<FunctionalityAccessProfileDTO> findFunctionalityPermissionListDto =  accessProfileHasFunctionalityService.findFunctionalityPermissionListDto(id);
		accessProfileDto.get().setPermissions(findFunctionalityPermissionListDto);
		return accessProfileDto;
	}
	
	@PreAuthorize("hasAuthority('REGISTER_ACCESS_PROFILE_READING')")
	public List<FunctionalityAccessProfileDTO> retrievePermissionsForIdLogin(Long idLogin) {
		return accessProfileRepository.retrievePermissionsForIdLogin(idLogin);
	}

	@Override
	@PreAuthorize("hasAuthority('REGISTER_ACCESS_PROFILE_WRITING')")
	public void save(AccessProfileDTO accessProfileDto) {
		
		AccessProfile accessProfile = new AccessProfile();
		accessProfile.setName(accessProfileDto.getName());
		accessProfile.setDescription(accessProfileDto.getDescription());
		List<AccessProfileHasFunctionality> accessProfileHasFunctionalityList = new ArrayList<>();
		for (int i = 0; i < accessProfileDto.getPermissions().size(); i++) {
			Functionality functionality = new Functionality(accessProfileDto.getPermissions().get(i).getFunctonalityId());
			AccessProfileHasFunctionality accessProfileHasFunctionality = new AccessProfileHasFunctionality(accessProfile, functionality, accessProfileDto.getPermissions().get(i).getReadPermission(), accessProfileDto.getPermissions().get(i).getWritePermission());
			accessProfileHasFunctionalityList.add(accessProfileHasFunctionality);
		}
		accessProfile.setAccessProfileHasFunctionalities(accessProfileHasFunctionalityList);
		accessProfile = accessProfileRepository.save(accessProfile);
		accessProfileHasFunctionalityService.saveAll(accessProfileHasFunctionalityList);
	}

	@Override
	@PreAuthorize("hasAuthority('REGISTER_ACCESS_PROFILE_WRITING')")
	public void update(AccessProfileDTO accessProfileDto) {
		AccessProfile accessProfile = accessProfileRepository.findById(accessProfileDto.getId()).get();
		accessProfile.setName(accessProfileDto.getName());
		accessProfile.setDescription(accessProfileDto.getDescription());
		accessProfile = accessProfileRepository.save(accessProfile);
		List<FunctionalityAccessProfileDTO> accessProfileHasFunctionalityListCurrent = accessProfileRepository.loadPermissionsByIdAccessProfile(accessProfileDto.getId());
		List<AccessProfileHasFunctionality> auxList = new ArrayList<>();
		List<AccessProfileHasFunctionality> accessProfileHasFunctionalityListRemove = new ArrayList<>();
		for (FunctionalityAccessProfileDTO fa : accessProfileHasFunctionalityListCurrent) {
			AccessProfileHasFunctionality accessProfileHasFunctionality = new AccessProfileHasFunctionality(new AccessProfile(fa.getAccessProfileId()), new Functionality(fa.getFunctonalityId()), fa.getReadPermission(), fa.getWritePermission());
			accessProfileHasFunctionality.setId(new AccessProfileFunctionalityKey(accessProfileHasFunctionality.getAccessProfile().getId(), accessProfileHasFunctionality.getFunctionality().getId()));
			auxList.add(accessProfileHasFunctionality);
		}
		
		List<AccessProfileHasFunctionality> accessProfileHasFunctionalityListAdd = new ArrayList<>();
		for (int i = 0; i < accessProfileDto.getPermissions().size(); i++) {
			Functionality functionality = new Functionality(accessProfileDto.getPermissions().get(i).getFunctonalityId());
			AccessProfileHasFunctionality accessProfileHasFunctionality = new AccessProfileHasFunctionality(accessProfile, functionality, accessProfileDto.getPermissions().get(i).getReadPermission(), accessProfileDto.getPermissions().get(i).getWritePermission());
			accessProfileHasFunctionalityListAdd.add(accessProfileHasFunctionality);
		}
		
		for (AccessProfileHasFunctionality aphf : auxList) {
			if(!accessProfileHasFunctionalityListAdd.contains(aphf)) {
				accessProfileHasFunctionalityListRemove.add(aphf);
			}
		}
		
		accessProfileHasFunctionalityService.deleteAll(accessProfileHasFunctionalityListRemove);
		accessProfileHasFunctionalityService.saveAll(accessProfileHasFunctionalityListAdd);
	}

	@Override
	@PreAuthorize("hasAuthority('REGISTER_ACCESS_PROFILE_WRITING')")
	public void delete(Long id) {
		accessProfileRepository.deleteById(id);
	}
	
	@PreAuthorize("hasAuthority('REGISTER_ACCESS_PROFILE_READING')")
	public List<ComboBoxDTO> comboBox() {
		List<ComboBoxDTO> comboBox = accessProfileRepository.comboBox();
		if (!comboBox.isEmpty()) {
			return comboBox;
		}
		return new ArrayList<>();
	}
	
	@PreAuthorize("hasAuthority('REGISTER_ACCESS_PROFILE_READING')")
	public List<ComboBoxDTO> findComboBoxByIdLogin(Long idLogin) {
		return accessProfileRepository.findComboBoxByIdLogin(idLogin);
	}

//	@PreAuthorize("hasAuthority('REGISTER_ACCESS_PROFILE_READING')")
//	public List<Long> findIdsFromFunctionalitiesForIdProfile(Long idPerfil) {
//		return accessProfileRepository.findIdsFromFunctionalitiesForIdProfile(idPerfil);
//	}
//
//	@PreAuthorize("hasAuthority('REGISTER_ACCESS_PROFILE_READING')")
//	public List<FunctionalityAccessProfileDTO> retrievePermissionsForIdsProfiles(List<Long> idsPerfis) {
//		return accessProfileRepository.retrievePermissionsForIdsProfiles(idsPerfis);
//	}
//
//	@PreAuthorize("hasAuthority('REGISTER_ACCESS_PROFILE_READING')")
//	public boolean profileHasReadPermissionToFunctionalityForFunctionalityNameAndIdProfile(String functionalityName,
//			Long idPerfil) {
//		return accessProfileRepository
//				.profileHasReadPermissionToFunctionalityForFunctionalityNameAndIdProfile(functionalityName, idPerfil);
//	}
//
//	@PreAuthorize("hasAuthority('REGISTER_ACCESS_PROFILE_READING')")
//	public boolean profileHasWritePermissionToFunctionalityForFunctionalityNameAndIdProfile(String functionalityName,
//			Long idPerfil) {
//		return accessProfileRepository
//				.profileHasWritePermissionToFunctionalityForFunctionalityNameAndIdProfile(functionalityName, idPerfil);
//	}

}
