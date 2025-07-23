package com.shaper.server.model.dto;

import lombok.Data;

@Data
public class TemplateDTO {
    
    private String templateName;
    private String templateDescription;
    private String templateContent;
    private Integer hrManagerId;
    private Integer departmentId;
}