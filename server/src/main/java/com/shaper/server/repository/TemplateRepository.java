package com.shaper.server.repository;

import com.shaper.server.model.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Integer> {
}