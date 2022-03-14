package br.com.carlos.record;

public record AccessProfileRecord(Long id, String name, String description) {

	private AccessProfileRecord(Builder builder) {
		this(builder.id, builder.name, builder.description);
	}

	public static class Builder {
	    private Long id;
		
	    private String name;
		
	    private String description;
	    
	    public Builder id(Long id) {
	    	this.id = id;
	    	return this;
	    }
	    
	    public Builder name(String name) {
	    	this.name = name;
	    	return this;
	    }
	    
	    public Builder description(String description) {
	    	this.description = description;
	    	return this;
	    }
	    
	    public AccessProfileRecord build() {
	    	return new AccessProfileRecord(this);
	    }
	    
	}

}
