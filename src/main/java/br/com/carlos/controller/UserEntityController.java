package br.com.carlos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.carlos.dto.UserEntityDTO;
import br.com.carlos.model.UserEntity;
import br.com.carlos.service.UserEntityService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/userEntity")
public class UserEntityController {

	@Autowired
	private UserEntityService userEntityService;

	@GetMapping()
	public ResponseEntity<List<UserEntity>> findAll() {
		try {
			List<UserEntity> userEntityList = userEntityService.findAll();
			return ResponseEntity.ok().body(userEntityList);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

	@GetMapping("/page")
	public ResponseEntity<Page<UserEntityDTO>> findAllPage(
			@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "name") String sortBy) {
		try {
			Page<UserEntityDTO> userEntityList = userEntityService.findAllPage(pageNo, pageSize, sortBy);
			return ResponseEntity.ok().body(userEntityList);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserEntity> findById(@PathVariable Long id) {
		try {
			Optional<UserEntity> userEntity = userEntityService.findById(id);
			return ResponseEntity.ok().body(userEntity.get());
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

	@Transactional
	@PostMapping
	public void postUserEntity(@RequestBody UserEntityDTO userEntityDto) {
		try {
			userEntityService.save(userEntityDto);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

	@Transactional
	@PutMapping
	public void putUserEntity(@RequestBody UserEntityDTO userEntityDto) {
		try {
			userEntityService.update(userEntityDto);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

	@Transactional
	@DeleteMapping("/{id}")
	public void deleteUserEntity(@PathVariable Long id) {
		try {
			userEntityService.delete(id);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

}