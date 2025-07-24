package com.shaper.server.service;

import com.shaper.server.model.entity.Hire;
import com.shaper.server.model.entity.Progress;
import com.shaper.server.repository.HireRepository;
import com.shaper.server.repository.ProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final HireRepository hireRepository;

    public ProgressService(ProgressRepository progressRepository, HireRepository hireRepository) {
        this.progressRepository = progressRepository;
        this.hireRepository = hireRepository;
    }

    public List<Progress> getProgressByHireId(UUID hireId) {
        return progressRepository.findByHireId(hireId);
    }

    public Progress getProgressById(Integer progressId) {
        return progressRepository.findById(progressId)
                .orElseThrow(() -> new NoSuchElementException("Progress not found with id: " + progressId));
    }

    public Progress updateProgress(Progress progress) {
        return progressRepository.save(progress);
    }

    public String getHireUsername(UUID hireId) {
        Hire hire = hireRepository.findById(hireId)
                .orElseThrow(() -> new NoSuchElementException("Hire not found with id: " + hireId));
        return hire.getEmail();
    }

    public String getHireUsernameByProgressId(Integer progressId) {
        Progress progress = getProgressById(progressId);
        return progress.getHire().getEmail();
    }
}