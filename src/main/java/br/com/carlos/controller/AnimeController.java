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

import br.com.carlos.dto.AnimeDTO;
import br.com.carlos.model.Anime;
import br.com.carlos.record.AnimeRecord;
import br.com.carlos.service.AnimeService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/anime")
public class AnimeController {

	@Autowired
	private AnimeService animeService;

	@GetMapping()
	public ResponseEntity<List<Anime>> findAll() {
		try {
			List<Anime> animeList = animeService.findAll();
			return ResponseEntity.ok().body(animeList);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

	@GetMapping("/page")
	public ResponseEntity<Page<AnimeDTO>> findAllPage(
			@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "nome") String sortBy) {
		try {
			Page<AnimeDTO> animeList = animeService.findAllPage(pageNo, pageSize, sortBy);
			return ResponseEntity.ok().body(animeList);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Anime> findById(@PathVariable Long id) {
		try {
			Optional<Anime> anime = animeService.findById(id);
			return ResponseEntity.ok().body(anime.get());
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

	@Transactional
	@PostMapping
	public void postAnime(@RequestBody AnimeRecord animeRecord) {
		try {
			animeService.save(animeRecord);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

	@Transactional
	@PutMapping
	public void putAnime(@RequestBody AnimeDTO animeDto) {
		try {
			animeService.update(animeDto);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

	@Transactional
	@DeleteMapping("/{id}")
	public void deleteAnime(@PathVariable Long id) {
		try {
			animeService.delete(id);
		} catch (AccessDeniedException ade) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN.value(),
					"Usuário não tem permissão para essa funcionalidade!", null);
		}
	}

}