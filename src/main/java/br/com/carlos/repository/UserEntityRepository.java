package br.com.carlos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.carlos.dto.UserEntityLoginDTO;
import br.com.carlos.model.UserEntity;
import feign.Param;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
	
	@Query("SELECT new UserEntity(ue.id, ue.name)"
			+ " FROM UserEntity ue")
	public List<UserEntity> findAll();
	
	@Query("SELECT new br.com.carlos.dto.UserEntityLoginDTO(ue.id, ue.name, l.id, l.email)"
			+ " FROM UserEntity ue"
			+ " inner join ue.login l")
	public Page<UserEntityLoginDTO> findAllPage(Pageable pageable);
	
//	@Query("SELECT new UserEntity(ue.id, ue.name)"
//	+ " FROM UserEntity ue"
//	+ " WHERE ue.id = :id")
//	public Optional<UserEntity> findById(@Param("id") Long id);
}
