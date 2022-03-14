package br.com.carlos.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessProfileFunctionalityKeyDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long accessProfileId;
	
	private Long functionalityId;
}
