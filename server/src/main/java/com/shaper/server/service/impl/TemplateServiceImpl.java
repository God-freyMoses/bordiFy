package com.shaper.server.service.impl;

import com.shaper.server.exception.DataNotFoundException;
import com.shaper.server.exception.ValidationException;
import com.shaper.server.mapper.TemplateMapper;
import com.shaper.server.model.dto.TemplateDTO;
import com.shaper.server.model.entity.CompanyDepartment;
import com.shaper.server.model.entity.HrUser;
import com.shaper.server.model.entity.Task;
import com.shaper.server.model.entity.Template;
import com.shaper.server.repository.CompanyDepartmentRepository;
import com.shaper.server.repository.HrUserRepository;
import com.shaper.server.repository.TaskRepository;
import com.shaper.server.repository.TemplateRepository;
import com.shaper.server.service.TemplateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TemplateServiceImpl implements TemplateService {
    private static final Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);

    private final TemplateRepository templateRepository;
    private final HrUserRepository hrUserRepository;
    private final CompanyDepartmentRepository departmentRepository;
    private final TaskRepository taskRepository;

    public TemplateServiceImpl(
            TemplateRepository templateRepository,
            HrUserRepository hrUserRepository,
            CompanyDepartmentRepository departmentRepository,
            TaskRepository taskRepository) {
        this.templateRepository = templateRepository;
        this.hrUserRepository = hrUserRepository;
        this.departmentRepository = departmentRepository;
        this.taskRepository = taskRepository;
    }

    /**
     * Validate template data before processing
     *
     * @param templateDTO The template data to validate
     * @throws ValidationException If validation fails
     */
    private void validateTemplateData(TemplateDTO templateDTO) {
        List<String> errors = new ArrayList<>();

        if (templateDTO == null) {
            throw new ValidationException("Template data cannot be null");
        }

        if (!StringUtils.hasText(templateDTO.getTitle())) {
            errors.add("Template title is required");
        }

        if (templateDTO.getHrId() == null) {
            errors.add("HR Manager ID is required");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Template validation failed: " + String.join(", ", errors));
        }
    }

    /**
     * Fetch tasks by IDs
     *
     * @param taskIds The task IDs to fetch
     * @return Set of Task entities
     */
    private Set<Task> fetchTasksByIds(List<Integer> taskIds) {
        if (taskIds == null || taskIds.isEmpty()) {
            return new HashSet<>();
        }

        return new HashSet<>(taskRepository.findAllById(taskIds));
    }

    /**
     * Fetch departments by IDs
     *
     * @param departmentIds The department IDs to fetch
     * @return Set of CompanyDepartment entities
     */
    private Set<CompanyDepartment> fetchDepartmentsByIds(List<Integer> departmentIds) {
        if (departmentIds == null || departmentIds.isEmpty()) {
            return new HashSet<>();
        }

        return new HashSet<>(departmentRepository.findAllById(departmentIds));
    }

    @Override
    @Transactional
    public TemplateDTO createTemplate(TemplateDTO templateDTO) {
        logger.info("Creating new template with title: {}", templateDTO.getTitle());
        
        try {
            // Validate input data
            validateTemplateData(templateDTO);
            
            // Fetch HR user
            UUID hrId = UUID.fromString(templateDTO.getHrId().toString());
            HrUser hrUser = hrUserRepository.findById(hrId)
                    .orElseThrow(() -> new DataNotFoundException("HR Manager not found with ID: " + templateDTO.getHrId()));
            
            // Fetch tasks and departments
            Set<Task> tasks = fetchTasksByIds(templateDTO.getTaskIds());
            Set<CompanyDepartment> departments = fetchDepartmentsByIds(templateDTO.getDepartmentIds());
            
            // Create and save template
            Template template = TemplateMapper.toEntity(templateDTO, hrUser, tasks, departments);
            Template savedTemplate = templateRepository.save(template);
            
            logger.info("Template created successfully with ID: {}", savedTemplate.getId());
            return TemplateMapper.toDTO(savedTemplate);
            
        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID format for HR ID: {}", templateDTO.getHrId(), e);
            throw new ValidationException("Invalid HR Manager ID format");
        } catch (Exception e) {
            logger.error("Error creating template: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "templates", key = "#id")
    public TemplateDTO getTemplateById(Integer id) {
        logger.debug("Fetching template with ID: {}", id);
        
        Template template = templateRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Template not found with ID: {}", id);
                    return new DataNotFoundException("Template not found with ID: " + id);
                });
        
        return TemplateMapper.toDTO(template);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemplateDTO> getAllTemplates() {
        logger.debug("Fetching all templates");
        
        return templateRepository.findAll().stream()
                .map(TemplateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TemplateDTO> getAllTemplatesPaged(Pageable pageable) {
        logger.debug("Fetching templates with pagination: {}", pageable);
        
        return templateRepository.findAll(pageable)
                .map(TemplateMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemplateDTO> getTemplatesByDepartmentId(Integer departmentId) {
        logger.debug("Fetching templates for department ID: {}", departmentId);
        
        // Use a more efficient query instead of loading the entire department
        List<Template> templates = templateRepository.findByDepartmentsId(departmentId);
        
        return templates.stream()
                .map(TemplateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemplateDTO> getTemplatesByHrManagerId(Integer hrManagerId) {
        logger.debug("Fetching templates for HR Manager ID: {}", hrManagerId);
        
        // Use a more efficient query instead of loading the entire HR user
        List<Template> templates = templateRepository.findByCreatedByHrId(UUID.fromString(hrManagerId.toString()));
        
        return templates.stream()
                .map(TemplateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = "templates", key = "#id")
    public TemplateDTO updateTemplate(Integer id, TemplateDTO templateDTO) {
        logger.info("Updating template with ID: {}", id);
        
        try {
            // Validate input data
            validateTemplateData(templateDTO);
            
            // Fetch existing template
            Template existingTemplate = templateRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("Template not found with ID: {}", id);
                        return new DataNotFoundException("Template not found with ID: " + id);
                    });
            
            // Fetch HR user
            UUID hrId = UUID.fromString(templateDTO.getHrId().toString());
            HrUser hrUser = hrUserRepository.findById(hrId)
                    .orElseThrow(() -> new DataNotFoundException("HR Manager not found with ID: " + templateDTO.getHrId()));
            
            // Fetch tasks and departments
            Set<Task> tasks = fetchTasksByIds(templateDTO.getTaskIds());
            Set<CompanyDepartment> departments = fetchDepartmentsByIds(templateDTO.getDepartmentIds());
            
            // Update template
            Template updatedTemplate = TemplateMapper.updateEntityFromDTO(
                    existingTemplate, templateDTO, hrUser, tasks, departments);
            
            Template savedTemplate = templateRepository.save(updatedTemplate);
            
            logger.info("Template updated successfully with ID: {}", savedTemplate.getId());
            return TemplateMapper.toDTO(savedTemplate);
            
        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID format for HR ID: {}", templateDTO.getHrId(), e);
            throw new ValidationException("Invalid HR Manager ID format");
        } catch (Exception e) {
            logger.error("Error updating template: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "templates", key = "#id")
    public void deleteTemplate(Integer id) {
        logger.info("Deleting template with ID: {}", id);
        
        if (!templateRepository.existsById(id)) {
            logger.warn("Template not found with ID: {}", id);
            throw new DataNotFoundException("Template not found with ID: " + id);
        }
        
        try {
            templateRepository.deleteById(id);
            logger.info("Template deleted successfully with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting template: {}", e.getMessage(), e);
            throw e;
        }
    }
}