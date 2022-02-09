package br.com.carlos.dto;

import java.io.Serializable;

import br.com.carlos.model.SystemParameter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SystemParameterDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String parameterName;
	
	private String parameterDescription;
	
	private String parameterValue;
	
	public SystemParameterDTO(Long id, String parameterName, String parameterDescription, String parameterValue) {
		setId(id);
		setParameterName(parameterName); 
		setParameterDescription(parameterDescription);
		setParameterValue(parameterValue);
	}
	
	public SystemParameterDTO modelToDTO(SystemParameter param) {
		SystemParameterDTO paramDTO = new SystemParameterDTO();
		paramDTO.setId(param.getId());
		paramDTO.setParameterName(param.getParameterName());
		paramDTO.setParameterDescription(param.getParameterDescription());
		paramDTO.setParameterValue(param.getParameterValue());
		return paramDTO;
	}

}
