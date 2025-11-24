package com.haynespro.assessment.mmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.haynespro.assessment.mmt.model.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {
	@Query("select m from Model m join fetch m.make where m.make.id = :makeId")
	List<Model> findAllByMakeIdFetchMake(Integer makeId);

	@Query("select m from Model m join fetch m.make where m.id = :id")
	Optional<Model> findByIdFetchMake(Integer id);
}
