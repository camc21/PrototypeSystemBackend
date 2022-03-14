package br.com.carlos.record;

public record UserEntityRecord(Long id, String name, String email) {

	private UserEntityRecord(Builder builder) {
		this(builder.id, builder.name, builder.email);
	}

	public static class Builder {
	    private Long id;
		
	    private String name;
		
	    private String email;
	    
	    public Builder id(Long id) {
	    	this.id = id;
	    	return this;
	    }
	    
	    public Builder name(String name) {
	    	this.name = name;
	    	return this;
	    }
	    
	    public Builder email(String email) {
	    	this.email = email;
	    	return this;
	    }
	    
	    public UserEntityRecord build() {
	    	return new UserEntityRecord(this);
	    }
	    
	}

}
