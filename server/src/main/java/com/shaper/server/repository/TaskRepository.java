package com.shaper.server.repository;

import com.shaper.server.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Task entity
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    
    /**
     * Find tasks by template ID
     * 
     * @param templateId the template ID
     * @return list of tasks
     */
    List<Task> findByTemplatesId(Integer templateId);
}