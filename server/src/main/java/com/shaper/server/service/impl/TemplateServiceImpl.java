package com.shaper.server.service.impl;

import com.shaper.server.exception.DataNotFoundException;
import com.shaper.server.mapper.TemplateMapper;
import com.shaper.server.model.dto.TemplateDTO;
import com.shaper.server.model.entity.CompanyDepartment;
import com.shaper.server.model.entity.HrUser;
import com.shaper.server.model.entity.Template;
import com.shaper.server.repository.CompanyDepartmentRepository;
import com.shaper.server.repository.HrUserRepository;
import com.shaper.server.repository.TemplateRepository;
import com.shaper.server.service.TemplateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;
    private final HrUserRepository hrUserRepository;
    private final CompanyDepartmentRepository departmentRepository;

    public TemplateServiceImpl(TemplateRepository templateRepository, 
                              HrUserRepository hrUserRepository, 
                              CompanyDepartmentRepository departmentRepository) {
        this.templateRepository = templateRepository;
        this.hrUserRepository = hrUserRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public TemplateDTO createTemplate(TemplateDTO templateDTO) {
        HrUser hrUser = hrUserRepository.findById(templateDTO.getHrManagerId())
                .orElseThrow(() -> new DataNotFoundException("HR Manager not found"));
        
        CompanyDepartment department = departmentRepository.findById(templateDTO.getDepartmentId())
                .orElseThrow(() -> new DataNotFoundException("Department not found"));
        
        Template template = TemplateMapper.toEntity(templateDTO, hrUser, department);
        Template savedTemplate = templateRepository.save(template);
        
        return TemplateMapper.toDTO(savedTemplate);
    }

    @Override
    public TemplateDTO getTemplateById(Integer id) {
        Template template = templateRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Template not found"));
        
        return TemplateMapper.toDTO(template);
    }

    @Override
    public List<TemplateDTO> getAllTemplates() {
        return templateRepository.findAll().stream()
                .map(TemplateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TemplateDTO> getTemplatesByDepartmentId(Integer departmentId) {
        CompanyDepartment department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DataNotFoundException("Department not found"));
        
        return department.getTemplates().stream()
                .map(TemplateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TemplateDTO> getTemplatesByHrManagerId(Integer hrManagerId) {
        HrUser hrUser = hrUserRepository.findById(hrManagerId)
                .orElseThrow(() -> new DataNotFoundException("HR Manager not found"));
        
        return hrUser.getAssignedTemplates().stream()
                .map(TemplateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TemplateDTO updateTemplate(Integer id, TemplateDTO templateDTO) {
        Template existingTemplate = templateRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Template not found"));
        
        HrUser hrUser = hrUserRepository.findById(templateDTO.getHrManagerId())
                .orElseThrow(() -> new DataNotFoundException("HR Manager not found"));
        
        CompanyDepartment department = departmentRepository.findById(templateDTO.getDepartmentId())
                .orElseThrow(() -> new DataNotFoundException("Department not found"));
        
        existingTemplate.setTemplateName(templateDTO.getTemplateName());
        existingTemplate.setTemplateDescription(templateDTO.getTemplateDescription());
        existingTemplate.setTemplateContent(templateDTO.getTemplateContent());
        existingTemplate.setAssignedByHr(hrUser);
        existingTemplate.setCompanyDepartment(department);
        
        Template updatedTemplate = templateRepository.save(existingTemplate);
        
        return TemplateMapper.toDTO(updatedTemplate);
    }

    @Override
    public void deleteTemplate(Integer id) {
        if (!templateRepository.existsById(id)) {
            throw new DataNotFoundException("Template not found");
        }
        
        templateRepository.deleteById(id);
    }
}