package br.com.carlos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.carlos.dto.ComboBoxDTO;
import br.com.carlos.service.FunctionalityService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/functionality")
public class FunctionalityController {

	@Autowired
	private FunctionalityService functionalityService;

	@GetMapping("/comboBox")
	public ResponseEntity<List<ComboBoxDTO>> comboBox() {
		try {
			List<ComboBoxDTO> comboBox = functionalityService.comboBox();
			return ResponseEntity.ok().body(comboBox);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para a listagem de funcionalidades", null);
		}
	}
}