package com.shaper.server.service;

import com.shaper.server.model.dto.TemplateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing templates
 */
public interface TemplateService {
    
    /**
     * Create a new template
     * @param templateDTO the template data
     * @return the created template
     * @throws com.shaper.server.exception.ValidationException if validation fails
     * @throws com.shaper.server.exception.DataNotFoundException if HR manager or department not found
     */
    TemplateDTO createTemplate(TemplateDTO templateDTO);
    
    /**
     * Get a template by ID
     * @param id the template ID
     * @return the template
     * @throws com.shaper.server.exception.DataNotFoundException if template not found
     */
    TemplateDTO getTemplateById(Integer id);
    
    /**
     * Get all templates
     * @return list of all templates
     */
    List<TemplateDTO> getAllTemplates();
    
    /**
     * Get all templates with pagination
     * @param pageable pagination information
     * @return page of templates
     */
    Page<TemplateDTO> getAllTemplatesPaged(Pageable pageable);
    
    /**
     * Get templates by department ID
     * @param departmentId the department ID
     * @return list of templates in the department
     * @throws com.shaper.server.exception.DataNotFoundException if department not found
     */
    List<TemplateDTO> getTemplatesByDepartmentId(Integer departmentId);
    
    /**
     * Get templates assigned by HR manager
     * @param hrManagerId the HR manager ID
     * @return list of templates assigned by the HR manager
     * @throws com.shaper.server.exception.DataNotFoundException if HR manager not found
     */
    List<TemplateDTO> getTemplatesByHrManagerId(Integer hrManagerId);
    
    /**
     * Update a template
     * @param id the template ID
     * @param templateDTO the updated template data
     * @return the updated template
     * @throws com.shaper.server.exception.ValidationException if validation fails
     * @throws com.shaper.server.exception.DataNotFoundException if template, HR manager, or department not found
     */
    TemplateDTO updateTemplate(Integer id, TemplateDTO templateDTO);
    
    /**
     * Delete a template
     * @param id the template ID
     * @throws com.shaper.server.exception.DataNotFoundException if template not found
     */
    void deleteTemplate(Integer id);
}