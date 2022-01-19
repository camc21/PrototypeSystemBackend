package br.com.carlos.record;

public record AnimeRecord(Long idAnime, String nome, Short temporada, Boolean possuiManga) {

	private AnimeRecord(Builder builder) {
		this(builder.idAnime, builder.nome, builder.temporada, builder.possuiManga);
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
	    
	    public AnimeRecord build() {
	    	return new AnimeRecord(this);
	    }
	    
	}

}
