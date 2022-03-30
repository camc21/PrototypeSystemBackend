package br.com.carlos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.com.carlos.dto.FunctionalityAccessProfileDTO;
import br.com.carlos.interfaces.InterfaceCrud;
import br.com.carlos.model.AccessProfileHasFunctionality;
import br.com.carlos.repository.AccessProfileHasFunctionalityRepository;

@Service
public class AccessProfileHasFunctionalityService implements InterfaceCrud<FunctionalityAccessProfileDTO> {

	@Autowired
	private AccessProfileHasFunctionalityRepository accessProfileHasFunctionalityRepository;
	
	@PreAuthorize("hasAuthority('REGISTER_ACCESS_PROFILE_READING')")
	public List<FunctionalityAccessProfileDTO> findFunctionalityPermissionListDto(Long id) {
		return accessProfileHasFunctionalityRepository.findFunctionalityPermissionListDtoByIdAccessProfile(id);
	}
	
	@PreAuthorize("hasAuthority('REGISTER_ACCESS_PROFILE_WRITING')")
	public void saveAll(List<AccessProfileHasFunctionality> list) {
		accessProfileHasFunctionalityRepository.saveAll(list);
	}
	
	@PreAuthorize("hasAuthority('REGISTER_ACCESS_PROFILE_WRITING')")
	public void deleteAll(List<AccessProfileHasFunctionality> list) {
		accessProfileHasFunctionalityRepository.deleteAll(list);
	}

	@Override
	public void save(FunctionalityAccessProfileDTO dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(FunctionalityAccessProfileDTO dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
}
