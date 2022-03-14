package br.com.carlos.dto;

import java.io.Serializable;

import br.com.carlos.model.AccessProfile;
import br.com.carlos.model.AccessProfileFunctionalityKey;
import br.com.carlos.model.AccessProfileHasFunctionality;
import br.com.carlos.model.Functionality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessProfileHasFunctionalityDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private AccessProfileFunctionalityKeyDTO id;

	private AccessProfileDTO accessProfileDto;

	private FunctionalityDTO functionalityDto;

	private Boolean writePermission;

	private Boolean readPermission;

	public AccessProfileHasFunctionalityDTO(AccessProfileDTO accessProfileDto, FunctionalityDTO functionality,
			Boolean readPermission, Boolean writePermission) {
		AccessProfileHasFunctionality accessProfileHasFunctionality = new AccessProfileHasFunctionality();
		accessProfileHasFunctionality
				.setId(new AccessProfileFunctionalityKey(accessProfileDto.getId(), functionality.getId()));
		accessProfileHasFunctionality.setAccessProfile(new AccessProfile(accessProfileDto));
		accessProfileHasFunctionality.setFunctionality(new Functionality(functionalityDto));
		accessProfileHasFunctionality.setReadPermission(readPermission);
		accessProfileHasFunctionality.setWritePermission(writePermission);
	}
	
	public AccessProfileHasFunctionalityDTO(Long idAccessProfile, Long idFunctionality) {
		AccessProfileDTO accessProfileDto = new AccessProfileDTO();
		accessProfileDto.setId(idAccessProfile);
		
		FunctionalityDTO functionalityDto = new FunctionalityDTO();
		functionalityDto.setId(idFunctionality);
		
		this.accessProfileDto = accessProfileDto;
		this.functionalityDto = functionalityDto;
	}
}
