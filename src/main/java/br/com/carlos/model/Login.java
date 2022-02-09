package br.com.carlos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.carlos.dto.LoginDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "login")
public class Login implements UserDetails, Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "user_name", unique = true)
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "account_non_expired")
	private Boolean accountNonExpired;

	@Column(name = "account_non_locked")
	private Boolean accountNonLocked;

	@Column(name = "credentials_non_expired")
	private Boolean credentialsNonExpired;

	@Column(name = "enabled")
	private Boolean enabled;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "login_has_access_profile", joinColumns = {
	@JoinColumn(name = "login_id") }, inverseJoinColumns = { 
	@JoinColumn(name = "access_profile_id") })
	private List<AccessProfile> accessProfiles;

	public List<String> getRoles() {
		List<String> roles = new ArrayList<String>();
		for (AccessProfile ap : this.accessProfiles) {
			roles.add(ap.getNome());
		}
		return roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.accessProfiles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public Login(Long id, String userName, String password, String description) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		List<AccessProfile> accessProfiles = new ArrayList<>();
		accessProfiles.add(new AccessProfile(description));
		this.accessProfiles = accessProfiles;
	}

	public Login(LoginDTO loginDto) {
		this.id = loginDto.getId();
		this.userName = loginDto.getUsername();
		this.password = loginDto.getPassword();
		List<AccessProfile> accessProfiles = new ArrayList<>();
		for (String descriptionProfile : loginDto.getPermissions()) {
			accessProfiles.add(new AccessProfile(descriptionProfile));
		}
		this.accessProfiles = accessProfiles;
	}

}
