package com.shaper.server.repository;

import com.shaper.server.model.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Integer> {
    List<Progress> findByHireId(UUID hireId);
}