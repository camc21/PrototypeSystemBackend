package br.com.carlos.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AccessProfileHasFunctionalitiesId implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long accessProfileId;
	
	private Long functionalityId;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccessProfileHasFunctionalitiesId other = (AccessProfileHasFunctionalitiesId) obj;
		return Objects.equals(accessProfileId, other.accessProfileId)
				&& Objects.equals(functionalityId, other.functionalityId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(accessProfileId, functionalityId);
	}

	
}
