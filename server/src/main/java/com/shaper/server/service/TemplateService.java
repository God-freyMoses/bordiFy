package com.shaper.server.service;

import com.shaper.server.model.dto.TemplateDTO;
import java.util.List;

public interface TemplateService {
    
    /**
     * Create a new template
     * @param templateDTO the template data
     * @return the created template
     */
    TemplateDTO createTemplate(TemplateDTO templateDTO);
    
    /**
     * Get a template by ID
     * @param id the template ID
     * @return the template
     */
    TemplateDTO getTemplateById(Integer id);
    
    /**
     * Get all templates
     * @return list of all templates
     */
    List<TemplateDTO> getAllTemplates();
    
    /**
     * Get templates by department ID
     * @param departmentId the department ID
     * @return list of templates in the department
     */
    List<TemplateDTO> getTemplatesByDepartmentId(Integer departmentId);
    
    /**
     * Get templates assigned by HR manager
     * @param hrManagerId the HR manager ID
     * @return list of templates assigned by the HR manager
     */
    List<TemplateDTO> getTemplatesByHrManagerId(Integer hrManagerId);
    
    /**
     * Update a template
     * @param id the template ID
     * @param templateDTO the updated template data
     * @return the updated template
     */
    TemplateDTO updateTemplate(Integer id, TemplateDTO templateDTO);
    
    /**
     * Delete a template
     * @param id the template ID
     */
    void deleteTemplate(Integer id);
}