package com.shaper.server.repository;

import com.shaper.server.model.entity.CompanySub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanySubRepository extends JpaRepository<CompanySub, Integer> {
    Optional<CompanySub> findByCompanyId(Integer companyId);
}