package br.com.carlos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import br.com.carlos.dto.FunctionalityAccessProfileDTO;

@Service
public class AuthService {
	
	@Autowired
	private AccessProfileService accessProfileService;

	public List<GrantedAuthority> prepareAuthorities(Long idLogin) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<FunctionalityAccessProfileDTO> permissionsLogin = accessProfileService.retrievePermissionsForIdLogin(idLogin);

		for (FunctionalityAccessProfileDTO p : permissionsLogin) {
			StringBuilder sb = new StringBuilder();
			sb.append("ROLE_");
			sb.append(p.getFunctionalityName());
			if (p.getReadPermission().equals(Boolean.TRUE)) {
				sb.append("_LEITURA");
				authorities.add(new SimpleGrantedAuthority(sb.toString()));
			} 
			sb = new StringBuilder();
			sb.append("ROLE_");
			sb.append(p.getFunctionalityName());
			if (p.getWritePermission().equals(Boolean.TRUE)) {
				sb.append("_ESCRITA");
				authorities.add(new SimpleGrantedAuthority(sb.toString()));
			}
		}

		return authorities;
	}
}
