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

import br.com.carlos.dto.AnimeDTO;
import br.com.carlos.interfaces.InterfaceCrud;
import br.com.carlos.model.Anime;
import br.com.carlos.repository.AnimeRepository;

@Service
public class AnimeService implements InterfaceCrud<AnimeDTO> {

	@Autowired
	AnimeRepository animeRepository;
	
	public record Listagem(List<Anime> animes, long quantidade) {
	}

	@PreAuthorize("hasAuthority('REGISTER_ANIME_READING')")
	public List<Anime> findAll() {
		List<Anime> animeList = animeRepository.findAll();
//			Anime anime = new Anime.Builder().idAnime(1l).nome("Demon Slayer").temporada((short) 1).possuiManga(true).build();
//			AnimeRecord anime2 = new AnimeRecord.Builder().idAnime(1L).nome("Demon Slayer2").temporada((short) 2).possuiManga(true).build();
		if (!animeList.isEmpty()) {
			return animeList;
		}
		return new ArrayList<Anime>();
	}
	
	@PreAuthorize("hasAuthority('REGISTER_ANIME_READING')")
	public Page<AnimeDTO> findAllPage(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<AnimeDTO> animeList = animeRepository.findAllPage(paging);
//			Anime anime = new Anime.Builder().idAnime(1l).nome("Demon Slayer").temporada((short) 1).possuiManga(true).build();
//			AnimeRecord anime2 = new AnimeRecord.Builder().idAnime(1L).nome("Demon Slayer2").temporada((short) 2).possuiManga(true).build();
		if (!animeList.isEmpty()) {
			return animeList;
		}
		return animeList;
	}

	@PreAuthorize("hasAuthority('REGISTER_ANIME_READING')")
	public Optional<Anime> findById(Long id) {
		Optional<Anime> anime = animeRepository.findById(id);
		return Optional.ofNullable(
				anime.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime não encontrado")));
	}

	@Override
	@PreAuthorize("hasAuthority('REGISTER_ANIME_WRITING')")
	public void save(AnimeDTO animeDto) {
		Anime anime = new Anime.Builder().idAnime(animeDto.getId()).nome(animeDto.getName())
				.hasManga(animeDto.getHasManga()).season(animeDto.getSeason()).build();
		animeRepository.save(anime);
	}

	@Override
	@PreAuthorize("hasAuthority('REGISTER_ANIME_WRITING')")
	public void update(AnimeDTO animeDto) {
		Anime anime = findById(animeDto.getId()).get();
		anime.updateAnime(animeDto);
		animeRepository.save(anime);
	}

	@Override
	@PreAuthorize("hasAuthority('REGISTER_ANIME_WRITING')")
	public void delete(Long id) {
		animeRepository.deleteById(id);
	}
}
