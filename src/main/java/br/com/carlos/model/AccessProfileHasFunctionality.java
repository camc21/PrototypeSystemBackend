package br.com.carlos.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "access_profile_has_functionality")

public class AccessProfileHasFunctionality {

	@EmbeddedId
	private AccessProfileFunctionalityKey id;
	
	@ManyToOne
    @MapsId("accessProfileId")
	@JoinColumn(name = "access_profile_id")
    private AccessProfile accessProfile;
 
    @ManyToOne
    @MapsId("functionalityId")
    @JoinColumn(name = "functionality_id")
    private Functionality functionality;
    
    @Column(name = "write_permission")
	private Boolean writePermission;
    
    @Column(name = "read_permission")
	private Boolean readPermission;
    
    public AccessProfileHasFunctionality(AccessProfile accessProfile, Functionality functionality, Boolean readPermission, Boolean writePermission) {
    	this.id = new AccessProfileFunctionalityKey(accessProfile.getId(), functionality.getId());
    	this.accessProfile = accessProfile;
    	this.functionality = functionality;
    	this.readPermission = readPermission;
    	this.writePermission = writePermission;
    }
    
    public AccessProfileHasFunctionality(Long idAccessProfile, Long idFunctionality) {
    	AccessProfile accessProfile = new AccessProfile();
    	accessProfile.setId(idAccessProfile);
    	
    	Functionality functionality = new Functionality();
    	functionality.setId(idFunctionality);
    	
    	this.id = new AccessProfileFunctionalityKey(accessProfile.getId(), functionality.getId());
    	this.accessProfile = accessProfile;
    	this.functionality = functionality;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccessProfileHasFunctionality other = (AccessProfileHasFunctionality) obj;
		return Objects.equals(accessProfile, other.accessProfile) && Objects.equals(functionality, other.functionality)
				&& Objects.equals(id, other.id) && Objects.equals(readPermission, other.readPermission)
				&& Objects.equals(writePermission, other.writePermission);
	}

	@Override
	public int hashCode() {
		return Objects.hash(accessProfile, functionality, id, readPermission, writePermission);
	}
    
    

}
