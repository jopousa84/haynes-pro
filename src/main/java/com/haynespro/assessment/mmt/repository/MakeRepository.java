package com.haynespro.assessment.mmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haynespro.assessment.mmt.model.Make;

@Repository
public interface MakeRepository extends JpaRepository<Make, Integer> {
}

