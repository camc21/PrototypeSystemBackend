package br.com.carlos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.carlos.dto.AccessProfileHasFunctionalityDTO;
import br.com.carlos.dto.FunctionalityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "functinality")
public class Functionality implements Serializable {

	private static final long serialVersionUID = -6129350156452087702L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nome")
	private String name;
	
	@OneToMany(mappedBy = "functionality")
    private List<AccessProfileHasFunctionality> accessProfileHasFunctionalities = new ArrayList<>();
	
	public Functionality(FunctionalityDTO dto) {
		Functionality f = new Functionality();
		f.setId(dto.getId());
		f.setName(dto.getDescription());
		for (AccessProfileHasFunctionalityDTO aphfDto : dto.getAccessProfileHasFunctionalitiesDto()) {
			accessProfileHasFunctionalities.add(new AccessProfileHasFunctionality(new AccessProfile(aphfDto.getAccessProfileDto()),new Functionality(aphfDto.getFunctionalityDto()), aphfDto.getReadPermission(), aphfDto.getWritePermission()));
		}
	}
	
	public Functionality(Long id) {
		this.id = id;
	}
	
	public Functionality(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
