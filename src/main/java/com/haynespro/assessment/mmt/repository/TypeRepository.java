package com.haynespro.assessment.mmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.haynespro.assessment.mmt.model.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
	@Query("select t from Type t join fetch t.model m join fetch m.make where m.id = :modelId")
	List<Type> findAllByModelIdFetchModelAndMake(Integer modelId);

	@Query("select t from Type t join fetch t.model m join fetch m.make where t.id = :id")
	Optional<Type> findByIdFetchModelAndMake(Integer id);
}
