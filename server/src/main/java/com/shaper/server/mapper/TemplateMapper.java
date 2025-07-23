package com.shaper.server.mapper;

import com.shaper.server.model.dto.TemplateDTO;
import com.shaper.server.model.entity.Template;
import com.shaper.server.model.entity.HrUser;
import com.shaper.server.model.entity.CompanyDepartment;

public class TemplateMapper {

    public static TemplateDTO toDTO(Template template) {
        TemplateDTO dto = new TemplateDTO();
        dto.setTemplateId(template.getTemplateId());
        dto.setTemplateName(template.getTemplateName());
        dto.setTemplateDescription(template.getTemplateDescription());
        dto.setTemplateContent(template.getTemplateContent());
        
        if (template.getAssignedByHr() != null) {
            dto.setHrManagerId(template.getAssignedByHr().getHrManagerId());
        }
        
        if (template.getCompanyDepartment() != null) {
            dto.setDepartmentId(template.getCompanyDepartment().getDepartmentId());
        }
        
        return dto;
    }

    public static Template toEntity(TemplateDTO dto, HrUser hrUser, CompanyDepartment department) {
        Template template = new Template();
        template.setTemplateId(dto.getTemplateId());
        template.setTemplateName(dto.getTemplateName());
        template.setTemplateDescription(dto.getTemplateDescription());
        template.setTemplateContent(dto.getTemplateContent());
        template.setAssignedByHr(hrUser);
        template.setCompanyDepartment(department);
        
        return template;
    }
}