package com.shaper.server.controller;

import com.shaper.server.model.dto.TemplateDTO;
import com.shaper.server.service.TemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {

    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<TemplateDTO> createTemplate(@RequestBody TemplateDTO templateDTO) {
        return new ResponseEntity<>(templateService.createTemplate(templateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'HIRE')")
    public ResponseEntity<TemplateDTO> getTemplateById(@PathVariable Integer id) {
        return ResponseEntity.ok(templateService.getTemplateById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('HR', 'HIRE')")
    public ResponseEntity<List<TemplateDTO>> getAllTemplates() {
        return ResponseEntity.ok(templateService.getAllTemplates());
    }

    @GetMapping("/department/{departmentId}")
    @PreAuthorize("hasAnyRole('HR', 'HIRE')")
    public ResponseEntity<List<TemplateDTO>> getTemplatesByDepartmentId(@PathVariable Integer departmentId) {
        return ResponseEntity.ok(templateService.getTemplatesByDepartmentId(departmentId));
    }

    @GetMapping("/hr/{hrManagerId}")
    @PreAuthorize("hasAnyRole('HR', 'HIRE')")
    public ResponseEntity<List<TemplateDTO>> getTemplatesByHrManagerId(@PathVariable Integer hrManagerId) {
        return ResponseEntity.ok(templateService.getTemplatesByHrManagerId(hrManagerId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<TemplateDTO> updateTemplate(@PathVariable Integer id, @RequestBody TemplateDTO templateDTO) {
        return ResponseEntity.ok(templateService.updateTemplate(id, templateDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Integer id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }
}