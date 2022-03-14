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

import br.com.carlos.dto.AccessProfileDTO;
import br.com.carlos.dto.ComboBoxDTO;
import br.com.carlos.model.AccessProfile;
import br.com.carlos.service.AccessProfileService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/accessProfile")
public class AccessProfileController {

	@Autowired
	private AccessProfileService accessProfileService;

	@GetMapping()
	public ResponseEntity<List<AccessProfile>> findAll() {
		try {
			List<AccessProfile> userEntityList = accessProfileService.findAll();
			return ResponseEntity.ok().body(userEntityList);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

	@GetMapping("/page")
	public ResponseEntity<Page<AccessProfileDTO>> findAllPage(
			@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "name") String sortBy) {
		try {
			Page<AccessProfileDTO> accessProfilePage = accessProfileService.findAllPage(pageNo, pageSize, sortBy);
			return ResponseEntity.ok().body(accessProfilePage);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccessProfileDTO> findById(@PathVariable Long id) {
		try {
			Optional<AccessProfileDTO> accessProfileDto = accessProfileService.findById(id);
			return ResponseEntity.ok().body(accessProfileDto.get());
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

	@Transactional
	@PostMapping
	public void postAccessProfile(@RequestBody AccessProfileDTO accessProfileDto) {
		try {
			accessProfileService.save(accessProfileDto);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

	@Transactional
	@PutMapping
	public void putAccessProfile(@RequestBody AccessProfileDTO accessProfileDto) {
		try {
			accessProfileService.update(accessProfileDto);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

//	@Transactional
	@DeleteMapping("/{id}")
	public void deleteAccessProfile(@PathVariable Long id) {
		try {
			accessProfileService.delete(id);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}
	
	@GetMapping("/comboboxAccessProfile")
	public ResponseEntity<List<ComboBoxDTO>> comboBox() {
		try {
			List<ComboBoxDTO> comboBox = accessProfileService.comboBox();
			return ResponseEntity.ok().body(comboBox);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para a listagem de funcionalidades", null);
		}
	}

}