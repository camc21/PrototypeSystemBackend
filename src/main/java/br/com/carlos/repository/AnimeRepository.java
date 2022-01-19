package br.com.carlos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.carlos.model.Anime;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
	
	@Query("select new Anime(a.idAnime, a.nome)"
	+ " from Anime a"
	+ " where a.idAnime = :idAnime")
	public Anime buscaAnimePorId(Long idAnime);
}
