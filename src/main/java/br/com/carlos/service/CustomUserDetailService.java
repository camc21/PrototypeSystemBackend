package br.com.carlos.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.carlos.model.AccessProfile;
import br.com.carlos.model.AccessProfileHasFunctionality;
import br.com.carlos.model.Login;
import br.com.carlos.repository.LoginRepository;

@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService {

	@Autowired(required = true)
	LoginRepository loginRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		final Login login = loginRepository.findByEmail(email);
		if (login == null) {
			throw new UsernameNotFoundException("UserName " + email + " not find");
		} else {
			UserDetails user = User.withUsername(login.getEmail()).password(login.getPassword())
					.disabled(!login.getEnabled()).authorities(getAuthorities(login)).build();
			return user;
		}
	}

	public Collection<? extends GrantedAuthority> getAuthorities(Login login) {
		List<AccessProfile> accessProfiles = login.getAccessProfiles();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (AccessProfile ap : accessProfiles) {
			for (AccessProfileHasFunctionality aphf : ap.getAccessProfileHasFunctionalities()) {
				if(aphf.getWritePermission() == Boolean.TRUE) {
					if(!authorities.contains(new SimpleGrantedAuthority(aphf.getFunctionality().getName()+"_WRITING"))) {
						authorities.add(new SimpleGrantedAuthority(aphf.getFunctionality().getName()+"_WRITING"));
					}
				}
				if(aphf.getReadPermission() == Boolean.TRUE) {
					if(!authorities.contains(new SimpleGrantedAuthority(aphf.getFunctionality().getName()+"_READING"))) {
						authorities.add(new SimpleGrantedAuthority(aphf.getFunctionality().getName()+"_READING"));
					}
					
				}
			}
		}
		System.out.println(authorities);
		return authorities;
	}

}
