package br.com.carlos.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
    public record Listagem(List<Anime> animes, long quantidade) {}
    @GetMapping()
    public ResponseEntity<Listagem> findAll() {
        List<Anime> animeList = animeService.findAll();
        return animeList != null ? ResponseEntity.ok().body(new Listagem(animeList, animeList.size())) : ResponseEntity.notFound().build(); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id) {
        Anime anime = animeService.findById(id);
        return ResponseEntity.ok().body(anime); 
    }
    
    @Transactional
    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public void postAnime(@RequestBody AnimeRecord animeRecord) {
    	animeService.postAnime(animeRecord);
    }
    
    @Transactional
    @PutMapping
    public void putAnime(@RequestBody AnimeDTO animeDto) {
    	animeService.putAnime(animeDto);
    }
    
    @Transactional
    @DeleteMapping("/{id}")
    public void deleteAnime(Long id) {
    	animeService.deleteAnime(id);
    }

}