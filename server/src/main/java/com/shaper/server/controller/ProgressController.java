package com.shaper.server.controller;

import com.shaper.server.model.entity.Hire;
import com.shaper.server.model.entity.Progress;
import com.shaper.server.model.entity.Task;
import com.shaper.server.service.ProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/progress")
@Tag(name = "Progress", description = "Progress management APIs")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/hire/{hireId}")
    @PreAuthorize("hasAnyRole('HR_MANAGER', 'NEW_HIRE')")
    @Operation(summary = "Get progress items for a hire", description = "Get all progress items for a specific hire")
    public ResponseEntity<List<Progress>> getProgressByHireId(@PathVariable UUID hireId) {
        // Check if the authenticated user is the hire or an HR manager
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        
        // If the user is a NEW_HIRE, they can only access their own progress
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_NEW_HIRE"))) {
            // Get the hire's username from the service
            String hireUsername = progressService.getHireUsername(hireId);
            if (!currentUsername.equals(hireUsername)) {
                return ResponseEntity.status(403).build();
            }
        }
        
        return ResponseEntity.ok(progressService.getProgressByHireId(hireId));
    }

    @PutMapping("/{progressId}/start")
    @PreAuthorize("hasRole('NEW_HIRE')")
    @Operation(summary = "Start a task", description = "Mark a task as in progress")
    public ResponseEntity<Progress> startTask(@PathVariable Integer progressId) {
        // Check if the authenticated user is the hire assigned to this progress
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        
        // Get the hire's username from the service
        String hireUsername = progressService.getHireUsernameByProgressId(progressId);
        if (!currentUsername.equals(hireUsername)) {
            return ResponseEntity.status(403).build();
        }
        
        Progress progress = progressService.getProgressById(progressId);
        progress.setStatus(Task.TaskStatus.IN_PROGRESS);
        return ResponseEntity.ok(progressService.updateProgress(progress));
    }

    @PutMapping("/{progressId}/complete")
    @PreAuthorize("hasRole('NEW_HIRE')")
    @Operation(summary = "Complete a task", description = "Mark a task as completed")
    public ResponseEntity<Progress> completeTask(@PathVariable Integer progressId) {
        // Check if the authenticated user is the hire assigned to this progress
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        
        // Get the hire's username from the service
        String hireUsername = progressService.getHireUsernameByProgressId(progressId);
        if (!currentUsername.equals(hireUsername)) {
            return ResponseEntity.status(403).build();
        }
        
        Progress progress = progressService.getProgressById(progressId);
        progress.setStatus(Task.TaskStatus.COMPLETED);
        progress.setCompletedAt(LocalDateTime.now());
        return ResponseEntity.ok(progressService.updateProgress(progress));
    }

    @PutMapping("/{progressId}/sign")
    @PreAuthorize("hasRole('NEW_HIRE')")
    @Operation(summary = "Sign a task", description = "Add a signature to a completed task")
    public ResponseEntity<Progress> signTask(@PathVariable Integer progressId, @RequestBody Map<String, String> payload) {
        // Check if the authenticated user is the hire assigned to this progress
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        
        // Get the hire's username from the service
        String hireUsername = progressService.getHireUsernameByProgressId(progressId);
        if (!currentUsername.equals(hireUsername)) {
            return ResponseEntity.status(403).build();
        }
        
        String signatureData = payload.get("signatureData");
        if (signatureData == null || signatureData.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        Progress progress = progressService.getProgressById(progressId);
        if (progress.getStatus() != Task.TaskStatus.COMPLETED) {
            return ResponseEntity.badRequest().build();
        }
        
        progress.setSignatureData(signatureData);
        progress.setSignedAt(LocalDateTime.now());
        return ResponseEntity.ok(progressService.updateProgress(progress));
    }
}