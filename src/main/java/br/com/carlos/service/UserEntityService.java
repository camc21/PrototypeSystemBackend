package br.com.carlos.service;

import java.util.ArrayList;
import java.util.Iterator;
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

import br.com.carlos.dto.ComboBoxDTO;
import br.com.carlos.dto.UserEntityLoginDTO;
import br.com.carlos.exception.ResourceNotFoundException;
import br.com.carlos.model.UserEntity;
import br.com.carlos.repository.UserEntityRepository;

@Service
public class UserEntityService {

	@Autowired
	private UserEntityRepository userEntityRepository;
	
	@Autowired
	private AccessProfileService accessProfileService;
	
	
	public record Listagem(List<UserEntity> animes, long quantidade) {
	}

	@PreAuthorize("hasAuthority('REGISTER_USER_ENTITY_READING')")
	public List<UserEntity> findAll() {
		List<UserEntity> userEntityList = userEntityRepository.findAll();
		if (!userEntityList.isEmpty()) {
			return userEntityList;
		}
		return new ArrayList<>();
	}
	
	@PreAuthorize("hasAuthority('REGISTER_USER_ENTITY_READING')")
	public Page<UserEntityLoginDTO> findAllPage(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<UserEntityLoginDTO> userEntityList = userEntityRepository.findAllPage(paging);
		if (!userEntityList.isEmpty()) {
			for (UserEntityLoginDTO uel : userEntityList) {
				List<ComboBoxDTO> accessProfiles = accessProfileService.findComboBoxByIdLogin(uel.getIdLogin());
				String accessProfileAux = getAccessProfilesText(accessProfiles);
				uel.setAccessProfileList(accessProfiles);
				uel.setAccessProfilesText(accessProfileAux);
			}
			return userEntityList;
		}
		return null;
	}

	@PreAuthorize("hasAuthority('REGISTER_USER_ENTITY_READING')")
	public Optional<UserEntity> findById(Long id) {
		Optional<UserEntity> userEntity = userEntityRepository.findById(id);
		return Optional.ofNullable(
				userEntity.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")));
	}

	@PreAuthorize("hasAuthority('REGISTER_USER_ENTITY_WRITING')")
	public void save(UserEntityLoginDTO userEntityLoginDto) {
		UserEntity user = new  UserEntity();
		user.create(userEntityLoginDto);
		userEntityRepository.save(user);
	}

	@PreAuthorize("hasAuthority('REGISTER_USER_ENTITY_WRITING')")
	public void update(UserEntityLoginDTO userEntityLoginDto) {
		Optional<UserEntity> userEntity = findById(userEntityLoginDto.getIdUserEntity());
		if(userEntity.isPresent()) {
			userEntity.get().update(userEntityLoginDto, userEntity.get());
		} else {
			throw new ResourceNotFoundException("Usuário não encontrado");
		}
		userEntityRepository.save(userEntity.get());
	}

	@PreAuthorize("hasAuthority('REGISTER_USER_ENTITY_WRITING')")
	public void delete(Long id) {
		userEntityRepository.deleteById(id);
	}
	
	private String getAccessProfilesText(List<ComboBoxDTO> accessProfilesDto) {
		Iterator<ComboBoxDTO> iterator = accessProfilesDto.iterator();
		String accessProfileText = "";
		while(iterator.hasNext()) {
			ComboBoxDTO element = iterator.next();
			accessProfileText += element.getLabel();
			if(iterator.hasNext()) {
				accessProfileText += " / ";
			}
		}
		return accessProfileText;
	}
}
