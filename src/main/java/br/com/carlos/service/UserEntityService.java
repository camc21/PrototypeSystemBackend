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

import br.com.carlos.dto.UserEntityDTO;
import br.com.carlos.interfaces.InterfaceCrud;
import br.com.carlos.model.UserEntity;
import br.com.carlos.repository.UserEntityRepository;

@Service
public class UserEntityService implements InterfaceCrud<UserEntityDTO> {

	@Autowired
	UserEntityRepository userEntityRepository;
	
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
	public Page<UserEntityDTO> findAllPage(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<UserEntityDTO> userEntityList = userEntityRepository.findAllPage(paging);
		if (!userEntityList.isEmpty()) {
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

	@Override
	@PreAuthorize("hasAuthority('REGISTER_USER_ENTITY_WRITING')")
	public void save(UserEntityDTO userEntityDto) {
		UserEntity userEntity = new UserEntity.Builder().id(userEntityDto.getId()).name(userEntityDto.getName())
				.email(userEntityDto.getEmail()).build();
		userEntityRepository.save(userEntity);
	}

	@Override
	@PreAuthorize("hasAuthority('REGISTER_USER_ENTITY_WRITING')")
	public void update(UserEntityDTO userEntityDto) {
		UserEntity userEntity = findById(userEntityDto.getId()).get();
		userEntity.updateUserEntity(userEntityDto);
		userEntityRepository.save(userEntity);
	}

	@Override
	@PreAuthorize("hasAuthority('REGISTER_USER_ENTITY_WRITING')")
	public void delete(Long id) {
		userEntityRepository.deleteById(id);
	}
}
