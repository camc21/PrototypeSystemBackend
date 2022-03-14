package br.com.carlos.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.carlos.dto.UserEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_user")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = -213864562139748411L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Login login;
	
	public UserEntity(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public UserEntity(Long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	private UserEntity(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.email = builder.email;
	}
	
	
	public UserEntity(UserEntity userEntityDto) {
		this.id = userEntityDto.getId();
		this.name = userEntityDto.getName();
		this.email = userEntityDto.getEmail();
	}
	
	public UserEntity updateUserEntity(UserEntityDTO userEntityDto) {
		this.name = userEntityDto.getName();
		this.email = userEntityDto.getEmail();
		return this;
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
	    
	    public UserEntity build() {
	    	return new UserEntity(this);
	    }
	    
	}


}
