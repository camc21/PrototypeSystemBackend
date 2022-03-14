package br.com.carlos.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AccessProfileFunctionalityKey implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "access_profile_id")
	private Long accessProfileId;
	
	@Column(name = "functionality_id")
	private Long functionalityId;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccessProfileFunctionalityKey other = (AccessProfileFunctionalityKey) obj;
		return Objects.equals(accessProfileId, other.accessProfileId)
				&& Objects.equals(functionalityId, other.functionalityId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(accessProfileId, functionalityId);
	}

	
}
