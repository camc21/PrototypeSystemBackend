package br.com.carlos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.carlos.dto.UserEntityDTO;
import br.com.carlos.model.UserEntity;
import feign.Param;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
	
	@Query("SELECT new UserEntity(ue.id, ue.name, ue.email)"
			+ " FROM UserEntity ue")
	public List<UserEntity> findAll();
	
	@Query("SELECT new br.com.carlos.dto.UserEntityDTO(ue.id, ue.name, ue.email)"
			+ " FROM UserEntity ue")
	public Page<UserEntityDTO> findAllPage(Pageable pageable);
	
	@Query("SELECT new UserEntity(ue.id, ue.name, ue.email)"
	+ " FROM UserEntity ue"
	+ " WHERE ue.id = :id")
	public Optional<UserEntity> findById(@Param("id") Long id);
}
