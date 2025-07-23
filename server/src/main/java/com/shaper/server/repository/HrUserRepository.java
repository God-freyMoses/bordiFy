package com.shaper.server.repository;

import com.shaper.server.model.entity.HrUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HrUserRepository extends JpaRepository<HrUser, UUID> {
    Optional<HrUser> findByEmail(String email);
}