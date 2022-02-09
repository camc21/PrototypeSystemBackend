package br.com.carlos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.carlos.model.Anime;
import feign.Param;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
	
	@Query("SELECT new Anime(a.id, a.nome, a.temporada, a.possuiManga)"
			+ " FROM Anime a")
	public List<Anime> findAll();
	
	@Query("SELECT new Anime(a.id, a.nome)"
	+ " FROM Anime a"
	+ " WHERE a.id = :idAnime")
	public Anime buscaAnimePorId(@Param("idAnime") Long idAnime);
}
