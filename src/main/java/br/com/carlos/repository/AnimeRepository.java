package br.com.carlos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.carlos.dto.AnimeDTO;
import br.com.carlos.model.Anime;
import feign.Param;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
	
	@Query("SELECT new Anime(a.id, a.name, a.season, a.hasManga)"
			+ " FROM Anime a")
	public List<Anime> findAll();
	
	@Query("SELECT new br.com.carlos.dto.AnimeDTO(a.id, a.name, a.season, a.hasManga)"
			+ " FROM Anime a")
	public Page<AnimeDTO> findAllPage(Pageable pageable);
	
	@Query("SELECT new Anime(a.id, a.name)"
	+ " FROM Anime a"
	+ " WHERE a.id = :idAnime")
	public Optional<Anime> findById(@Param("idAnime") Long idAnime);
}
