package br.com.carlos.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FunctionalityDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String description;
	
	 private List<AccessProfileHasFunctionalityDTO> accessProfileHasFunctionalitiesDto = new ArrayList<>();
	
	public FunctionalityDTO(Long id) {
		this.id = id;
	}
	
	public FunctionalityDTO(String description) {
		this.description = description;
	}
}
