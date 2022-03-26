package br.com.carlos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.carlos.dto.LoginDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "login")
public class Login implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "email")
	private String email;

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
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "login")
    private UserEntity userEntity;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
	@JoinTable(name = "login_has_access_profile", joinColumns = {
	@JoinColumn(name = "login_id") }, inverseJoinColumns = { 
	@JoinColumn(name = "access_profile_id") })
	private List<AccessProfile> accessProfiles;

	public List<String> getRoles() {
		List<String> roles = new ArrayList<String>();
		for (AccessProfile ap : this.accessProfiles) {
			roles.add(ap.getName());
		}
		return roles;
	}

	public Login(Long id, String email, String password, String description) {
		this.id = id;
		this.email = email;
		this.password = password;
		List<AccessProfile> accessProfiles = new ArrayList<>();
		accessProfiles.add(new AccessProfile(description));
		this.accessProfiles = accessProfiles;
	}

	public Login(LoginDTO loginDto) {
		this.id = loginDto.getId();
		this.email = loginDto.getEmail();;
		this.password = loginDto.getPassword();
		List<AccessProfile> accessProfiles = new ArrayList<>();
		for (String descriptionProfile : loginDto.getPermissions()) {
			accessProfiles.add(new AccessProfile(descriptionProfile));
		}
		this.accessProfiles = accessProfiles;
	}

}
