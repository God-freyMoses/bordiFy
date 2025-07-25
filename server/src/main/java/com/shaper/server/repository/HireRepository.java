package com.shaper.server.repository;

import com.shaper.server.model.entity.Hire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HireRepository extends JpaRepository<Hire, UUID> {
    Optional<Hire> findByEmail(String email);
}