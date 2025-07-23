package com.shaper.server.repository;

import com.shaper.server.model.entity.HrUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HrUserRepository extends JpaRepository<HrUser, Long> {
}