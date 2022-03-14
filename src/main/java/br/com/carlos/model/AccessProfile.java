package br.com.carlos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.carlos.dto.AccessProfileDTO;
import br.com.carlos.dto.FunctionalityPermissionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "access_profile")
public class AccessProfile implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@OneToMany(mappedBy = "accessProfile", cascade = {CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private List<AccessProfileHasFunctionality> accessProfileHasFunctionalities = new ArrayList<>();
	
	public AccessProfile(AccessProfileDTO dto) {
		this.id = dto.getId();
		this.name = dto.getName();
		this.description = dto.getDescription();
		for (FunctionalityPermissionDTO fpDto : dto.getPermissions()) {
			AccessProfileHasFunctionality accessProfileHasFunctionality = new AccessProfileHasFunctionality();
			accessProfileHasFunctionality.setId(new AccessProfileFunctionalityKey(dto.getId(), fpDto.getId()));
			accessProfileHasFunctionality.setAccessProfile(new AccessProfile(dto.getId()));
			accessProfileHasFunctionality.setFunctionality(new Functionality(fpDto.getId()));
			accessProfileHasFunctionality.setReadPermission(fpDto.getReadingPermission());
			accessProfileHasFunctionality.setWritePermission(fpDto.getWritingPermission());
			this.accessProfileHasFunctionalities.add(accessProfileHasFunctionality);
		}
	}
	
	public AccessProfile(Long id) {
		this.id = id;
	}
	
	public AccessProfile(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public AccessProfile(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	private AccessProfile(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
	}
	
	public AccessProfile(AccessProfile accessProfileDto) {
		this.id = accessProfileDto.getId();
		this.name = accessProfileDto.getName();
		this.description = accessProfileDto.getDescription();
	}
	
	public AccessProfile updateAccessProfile(AccessProfileDTO accessProfileDto) {
		this.name = accessProfileDto.getName();
		this.description = accessProfileDto.getDescription();
		return this;
	}
	
	public AccessProfile(String description) {
		this.description = description;
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
	    
	    public AccessProfile build() {
	    	return new AccessProfile(this);
	    }
	}
}
