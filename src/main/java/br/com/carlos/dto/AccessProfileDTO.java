package br.com.carlos.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.carlos.model.AccessProfile;
import br.com.carlos.model.AccessProfileHasFunctionality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessProfileDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String description;

	List<FunctionalityPermissionDTO> permissions = new ArrayList<>();
	
	public AccessProfileDTO(AccessProfile accessProfile) {
		this.id = accessProfile.getId();
		this.name = accessProfile.getName();
		this.description = accessProfile.getDescription();
		for (AccessProfileHasFunctionality fpDto : accessProfile.getAccessProfileHasFunctionalities()) {
			this.permissions.add(new FunctionalityPermissionDTO(fpDto.getFunctionality().getId(), fpDto.getFunctionality().getName(), fpDto.getReadPermission(), fpDto.getWritePermission()));
		}
	}
	
	public AccessProfileDTO(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
}