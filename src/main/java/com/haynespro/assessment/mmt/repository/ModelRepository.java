package com.haynespro.assessment.mmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haynespro.assessment.mmt.model.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {

	List<Model> findAllByMakeId(int makeId);
}

