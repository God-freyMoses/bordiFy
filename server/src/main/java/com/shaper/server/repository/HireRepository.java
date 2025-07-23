package com.shaper.server.repository;

import com.shaper.server.model.entity.Hire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HireRepository extends JpaRepository<Hire, Long> {
}