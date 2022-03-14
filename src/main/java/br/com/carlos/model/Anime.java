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
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "season")
    private Short season;
	
	@Column(name = "has_manga")
    private Boolean hasManga;
	
	private Anime(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.season = builder.season;
		this.hasManga = builder.hasManga;
	}
	
	
	public Anime(AnimeDTO animeDto) {
		this.id = animeDto.getId();
		this.name = animeDto.getName();
		this.season = animeDto.getSeason();
		this.hasManga = animeDto.getHasManga();
	}
	
	public Anime updateAnime(AnimeDTO animeDto) {
		this.name = animeDto.getName();
		this.season = animeDto.getSeason();
		this.hasManga = animeDto.getHasManga();
		return this;
	}
	
	public Anime(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public static class Builder {
	    private Long id;
		
	    private String name;
		
	    private Short season;
		
	    private Boolean hasManga;
	    
	    public Builder idAnime(Long id) {
	    	this.id = id;
	    	return this;
	    }
	    
	    public Builder nome(String name) {
	    	this.name = name;
	    	return this;
	    }
	    
	    public Builder season(Short season) {
	    	this.season = season;
	    	return this;
	    }
	    
	    public Builder hasManga(Boolean hasManga) {
	    	this.hasManga = hasManga;
	    	return this;
	    }
	    
	    public Anime build() {
	    	return new Anime(this);
	    }
	    
	}
}