package br.com.carlos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.carlos.dto.AnimeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ANIME")
public class Anime implements Serializable {

	private static final long serialVersionUID = -731378901932364664L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "nome")
    private String nome;
	
	@Column(name = "temporada")
    private Short temporada;
	
	@Column(name = "possui_manga")
    private Boolean possuiManga;
	
	private Anime(Builder builder) {
		this.id = builder.idAnime;
		this.nome = builder.nome;
		this.temporada = builder.temporada;
		this.possuiManga = builder.possuiManga;
	}
	
	
	public Anime(AnimeDTO animeDto) {
		this.id = animeDto.getId();
		this.nome = animeDto.getNome();
		this.temporada = animeDto.getTemporada();
		this.possuiManga = animeDto.getPossuiManga();
	}
	
	public Anime updateAnime(AnimeDTO animeDto) {
		this.nome = animeDto.getNome();
		this.temporada = animeDto.getTemporada();
		this.possuiManga = animeDto.getPossuiManga();
		return this;
	}
	
	public Anime(Long idAnime, String nome) {
		this.id = idAnime;
		this.nome = nome;
	}
	
	public static class Builder {
	    private Long idAnime;
		
	    private String nome;
		
	    private Short temporada;
		
	    private Boolean possuiManga;
	    
	    public Builder idAnime(Long idAnime) {
	    	this.idAnime = idAnime;
	    	return this;
	    }
	    
	    public Builder nome(String nome) {
	    	this.nome = nome;
	    	return this;
	    }
	    
	    public Builder temporada(Short temporada) {
	    	this.temporada = temporada;
	    	return this;
	    }
	    
	    public Builder possuiManga(Boolean possuiManga) {
	    	this.possuiManga = possuiManga;
	    	return this;
	    }
	    
	    public Anime build() {
	    	return new Anime(this);
	    }
	    
	}
}