package br.com.carlos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.carlos.dto.ComboBoxDTO;
import br.com.carlos.dto.UserEntityDTO;
import br.com.carlos.dto.UserEntityLoginDTO;
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
	
	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinColumn(name = "id_login", referencedColumnName = "id")
    private Login login;
	
	public UserEntity create(UserEntityLoginDTO userEntityLoginDto) {
		this.id = userEntityLoginDto.getIdUserEntity();
		this.name = userEntityLoginDto.getName();
		Login login = new Login();
		login.setId(userEntityLoginDto.getIdLogin());
		login.setEmail(userEntityLoginDto.getEmail());
		login.setPassword(userEntityLoginDto.getPassword());
		login.setEnabled(true);
		login.setCredentialsNonExpired(true);
		login.setAccountNonLocked(true);
		login.setAccountNonExpired(true);
		List<AccessProfile> accessProfileList = new ArrayList<>();
		for (ComboBoxDTO ap : userEntityLoginDto.getAccessProfileList()) {
			AccessProfile apAux = new AccessProfile(ap.getValue());
			accessProfileList.add(apAux);
		}
		login.setAccessProfiles(accessProfileList);
		this.login = login;
		return this;
	}
	
	public UserEntity update(UserEntityLoginDTO userEntityLoginDto, UserEntity userEntity) {
		userEntity.setName(userEntityLoginDto.getName());
		userEntity.getLogin().setEmail(userEntityLoginDto.getEmail());
		if(userEntityLoginDto.getPassword() != null) {
			userEntity.getLogin().setPassword(userEntityLoginDto.getPassword());
		}
		
		userEntity.getLogin().setEnabled(true);
		userEntity.getLogin().setCredentialsNonExpired(true);
		userEntity.getLogin().setAccountNonLocked(true);
		userEntity.getLogin().setAccountNonExpired(true);
		List<AccessProfile> accessProfileList = new ArrayList<>();
		for (ComboBoxDTO ap : userEntityLoginDto.getAccessProfileList()) {
			AccessProfile apAux = new AccessProfile(ap.getValue());
			accessProfileList.add(apAux);
		}
		userEntity.getLogin().setAccessProfiles(accessProfileList);
		return userEntity;
	}
	
	public UserEntity(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public UserEntity(Long id, String name, String email) {
		this.id = id;
		this.name = name;
	}
	
	private UserEntity(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
	}
	
	
	public UserEntity(UserEntity userEntityDto) {
		this.id = userEntityDto.getId();
		this.name = userEntityDto.getName();
	}
	
	public UserEntity updateUserEntity(UserEntityDTO userEntityDto) {
		this.name = userEntityDto.getName();
		return this;
	}
	
	public static class Builder {
	    private Long id;
		
	    private String name;
		
	    public Builder id(Long id) {
	    	this.id = id;
	    	return this;
	    }
	    
	    public Builder name(String name) {
	    	this.name = name;
	    	return this;
	    }
	    
	    public UserEntity build() {
	    	return new UserEntity(this);
	    }
	    
	}


}
