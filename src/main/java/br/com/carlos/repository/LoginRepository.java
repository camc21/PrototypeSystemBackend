package br.com.carlos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.carlos.model.Login;
import br.com.carlos.model.AccessProfile;
import feign.Param;

public interface LoginRepository extends JpaRepository<Login, Long> {
	
	@Query("SELECT l"
			+ " FROM Login l"
			+ " WHERE l.email = :email")
	Login findByEmail(@Param("email") String email);
	
	@Query("SELECT"
			+ " new br.com.carlos.model.AccessProfile(ap.description)"
			+ " FROM Login l"
			+ " INNER JOIN l.accessProfiles ap"
			+ " WHERE l.id = :idLogin")
	List<AccessProfile> findProfilesByIdLogin(@Param("idLogin") Long idLogin);
	
	@Query("SELECT"
			+ " new br.com.carlos.model.Login(l.id, l.email, l.password, ap.description)"
			+ "	FROM Login l"
			+ " INNER JOIN l.accessProfiles ap"
			+ " WHERE l.id = :idLogin")
	Login findByUserNameDTO(@Param("idLogin") Long idLogin);

}
