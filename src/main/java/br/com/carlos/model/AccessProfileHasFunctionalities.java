package br.com.carlos.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "access_profile_has_functionalities")
@Embeddable
public class AccessProfileHasFunctionalities {

	@EmbeddedId
	private AccessProfileHasFunctionalitiesId id = new AccessProfileHasFunctionalitiesId();
	
	@ManyToOne
    @MapsId("accessProfileId")
    private AccessProfile accessProfile;
 
    @ManyToOne
    @MapsId("functionalityId")
    private Functionality functionality;
    
    @Column(name = "write_permission")
	private Boolean writePermission;
    
    @Column(name = "read_permission")
	private Boolean readPermission;

}
