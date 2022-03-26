package br.com.carlos.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityLoginDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long idUserEntity;
	
    private String name;
    
    private Long idLogin;
    
    private String email;
    
    private String password;
    
    private String accessProfilesText;
    
    private List<ComboBoxDTO> accessProfileList = new ArrayList<>();
    
    public UserEntityLoginDTO(Long idUserEntity, String name, Long idLogin, String email) {
    	this.idUserEntity = idUserEntity;
    	this.name = name;
    	this.idLogin = idLogin;
    	this.email = email;
    }
}