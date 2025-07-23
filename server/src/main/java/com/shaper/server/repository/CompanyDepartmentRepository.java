package com.shaper.server.repository;

import com.shaper.server.model.entity.CompanyDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDepartmentRepository extends JpaRepository<CompanyDepartment, Integer> {
}