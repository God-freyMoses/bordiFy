package com.shaper.server.repository;

import com.shaper.server.model.entity.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Template entity
 */
@Repository
public interface TemplateRepository extends JpaRepository<Template, Integer> {
    
    /**
     * Find templates by department ID
     *
     * @param departmentId the department ID
     * @return list of templates
     */
    @Query("SELECT t FROM Template t JOIN t.departments d WHERE d.id = :departmentId")
    List<Template> findByDepartmentsId(@Param("departmentId") Integer departmentId);
    
    /**
     * Find templates by HR user ID
     *
     * @param hrId the HR user ID
     * @return list of templates
     */
    List<Template> findByCreatedByHrId(UUID hrId);
    
    /**
     * Find templates by status
     *
     * @param status the template status
     * @return list of templates
     */
    List<Template> findByStatus(Template.TemplateStatus status);
    
    /**
     * Find templates by status with pagination
     *
     * @param status the template status
     * @param pageable pagination information
     * @return page of templates
     */
    Page<Template> findByStatus(Template.TemplateStatus status, Pageable pageable);
    
    /**
     * Find templates by title containing the given text
     *
     * @param title the title text to search for
     * @return list of templates
     */
    List<Template> findByTitleContainingIgnoreCase(String title);
}