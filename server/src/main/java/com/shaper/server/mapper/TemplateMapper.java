package com.shaper.server.mapper;

import com.shaper.server.model.dto.TemplateDTO;
import com.shaper.server.model.entity.CompanyDepartment;
import com.shaper.server.model.entity.HrUser;
import com.shaper.server.model.entity.Task;
import com.shaper.server.model.entity.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between Template entities and DTOs
 */
public class TemplateMapper {
    private static final Logger logger = LoggerFactory.getLogger(TemplateMapper.class);

    /**
     * Convert a Template entity to a TemplateDTO
     *
     * @param template The Template entity to convert
     * @return The corresponding TemplateDTO
     */
    public static TemplateDTO toDTO(Template template) {
        if (template == null) {
            logger.warn("Attempted to convert null Template to DTO");
            return null;
        }

        logger.debug("Converting Template entity to DTO: {}", template.getId());
        
        TemplateDTO dto = new TemplateDTO();
        dto.setId(template.getId());
        dto.setTitle(template.getTitle());
        dto.setDescription(template.getDescription());
        
        if (template.getStatus() != null) {
            dto.setStatus(template.getStatus().name());
        }
        
        // Handle HR user information
        if (template.getCreatedByHr() != null) {
            dto.setHrId(template.getCreatedByHr().getId().hashCode());
            
            // Include HR name for display purposes
            String hrName = template.getCreatedByHr().getFirstName() + " " +
                           template.getCreatedByHr().getLastName();
            dto.setHrName(hrName);
        }
        
        // Convert tasks to task IDs
        if (template.getTasks() != null && !template.getTasks().isEmpty()) {
            List<Integer> taskIds = template.getTasks().stream()
                .map(Task::getId)
                .collect(Collectors.toList());
            dto.setTaskIds(taskIds);
        } else {
            dto.setTaskIds(new ArrayList<>());
        }
        
        // Convert departments to department IDs
        if (template.getDepartments() != null && !template.getDepartments().isEmpty()) {
            List<Integer> departmentIds = template.getDepartments().stream()
                .map(CompanyDepartment::getId)
                .collect(Collectors.toList());
            dto.setDepartmentIds(departmentIds);
        } else {
            dto.setDepartmentIds(new ArrayList<>());
        }
        
        dto.setCreatedDate(template.getCreatedDate());
        dto.setUpdatedDate(template.getUpdatedDate());
        
        return dto;
    }

    /**
     * Convert a TemplateDTO to a Template entity
     *
     * @param dto The TemplateDTO to convert
     * @param hrUser The HR user creating/updating the template
     * @param tasks The tasks to include in the template
     * @param departments The departments to assign the template to
     * @return The corresponding Template entity
     */
    public static Template toEntity(TemplateDTO dto, HrUser hrUser, Set<Task> tasks, Set<CompanyDepartment> departments) {
        if (dto == null) {
            logger.warn("Attempted to convert null TemplateDTO to entity");
            return null;
        }

        logger.debug("Converting TemplateDTO to entity: {}", dto.getId());
        
        Template template = new Template();
        
        // Only set ID if it's an update operation
        if (dto.getId() != null) {
            template.setId(dto.getId());
        }
        
        template.setTitle(dto.getTitle());
        template.setDescription(dto.getDescription());
        
        // Set status if provided, otherwise it will be set by @PrePersist
        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            try {
                Template.TemplateStatus status = Template.TemplateStatus.valueOf(dto.getStatus());
                template.setStatus(status);
            } catch (IllegalArgumentException e) {
                logger.warn("Invalid status value in DTO: {}", dto.getStatus());
                // Default to PENDING if invalid status
                template.setStatus(Template.TemplateStatus.PENDING);
            }
        }
        
        template.setCreatedByHr(hrUser);
        
        // Set tasks and departments if provided
        if (tasks != null) {
            template.setTasks(tasks);
        } else {
            template.setTasks(new HashSet<>());
        }
        
        if (departments != null) {
            template.setDepartments(departments);
        } else {
            template.setDepartments(new HashSet<>());
        }
        
        return template;
    }
    
    /**
     * Update an existing Template entity with values from a DTO
     *
     * @param existingTemplate The existing Template entity to update
     * @param dto The DTO containing the new values
     * @param hrUser The HR user updating the template
     * @param tasks The tasks to include in the template
     * @param departments The departments to assign the template to
     * @return The updated Template entity
     */
    public static Template updateEntityFromDTO(
            Template existingTemplate,
            TemplateDTO dto,
            HrUser hrUser,
            Set<Task> tasks,
            Set<CompanyDepartment> departments) {
        
        if (existingTemplate == null || dto == null) {
            logger.warn("Attempted to update null Template or with null DTO");
            return existingTemplate;
        }
        
        logger.debug("Updating Template entity from DTO: {}", existingTemplate.getId());
        
        // Update basic fields
        if (dto.getTitle() != null) {
            existingTemplate.setTitle(dto.getTitle());
        }
        
        if (dto.getDescription() != null) {
            existingTemplate.setDescription(dto.getDescription());
        }
        
        // Update status if provided
        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            try {
                Template.TemplateStatus status = Template.TemplateStatus.valueOf(dto.getStatus());
                existingTemplate.setStatus(status);
            } catch (IllegalArgumentException e) {
                logger.warn("Invalid status value in DTO: {}", dto.getStatus());
                // Don't change status if invalid value
            }
        }
        
        // Update HR user if provided
        if (hrUser != null) {
            existingTemplate.setCreatedByHr(hrUser);
        }
        
        // Update tasks if provided
        if (tasks != null) {
            existingTemplate.setTasks(tasks);
        }
        
        // Update departments if provided
        if (departments != null) {
            existingTemplate.setDepartments(departments);
        }
        
        return existingTemplate;
    }
}