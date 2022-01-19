package br.com.carlos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.carlos.dto.AnimeDTO;
import br.com.carlos.model.Anime;
import br.com.carlos.record.AnimeRecord;
import br.com.carlos.repository.AnimeRepository;

@Service
public class AnimeService {
	
	@Autowired
	AnimeRepository animeRepository;
	
	public List<Anime> findAll(){
		List<Anime> animeList = animeRepository.findAll();
		Anime anime = new Anime.Builder().idAnime(1l).nome("Demon Slayer").temporada((short) 1).possuiManga(true).build();
		AnimeRecord anime2 = new AnimeRecord.Builder().idAnime(1L).nome("Demon Slayer2").temporada((short) 2).possuiManga(true).build();
		System.out.println(anime);
		System.out.println(anime2);
		if(!animeList.isEmpty()) {
			return animeList;
		}
		return new ArrayList<Anime>();
	}
	
	public Anime findById(Long id){
		Optional<Anime> anime = animeRepository.findById(id);
		return anime.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime não encontrado"));
	}
	
	public void postAnime(AnimeRecord animeRecord) {
		try {
			Anime anime = new Anime.Builder()
					.idAnime(animeRecord.idAnime())
					.nome(animeRecord.nome())
					.possuiManga(animeRecord.possuiManga())
					.temporada(animeRecord.temporada())
					.build();
			animeRepository.save(anime);
		} catch (Exception e) {
			System.out.println("Erro");
		}
	}
	
	public void putAnime(AnimeDTO animeDto) {
		try {
			Anime anime = findById(animeDto.getIdAnime());
			anime.updateAnime(animeDto);
			animeRepository.save(anime);
		} catch (Exception e) {
			System.out.println("Erro");
		}
	}
	
	public void deleteAnime(Long id) {
		try {
			Anime anime = findById(id);
			animeRepository.delete(anime);
		} catch (Exception e) {
			System.out.println("Erro");
		}
	}
	
}
