package br.com.carlos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionalityAccessProfileDTO {

	private Long accessProfileId;

	private String profileName;

	private Long functonalityId;

	private String functionalityName;

	private Boolean writePermission;

	private Boolean readPermission;

}
