package br.com.carlos.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	private String nome;
	
	@OneToMany(mappedBy = "functionality")
    private List<AccessProfileHasFunctionalities> accessProfileHasFunctionalities;
	
	public Functionality(Long id) {
		this.id = id;
	}
}
