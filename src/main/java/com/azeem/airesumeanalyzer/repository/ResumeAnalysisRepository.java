package com.azeem.airesumeanalyzer.repository;

import com.azeem.airesumeanalyzer.entity.Resume;
import com.azeem.airesumeanalyzer.entity.ResumeAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeAnalysisRepository extends JpaRepository<ResumeAnalysis, Long> {

    ResumeAnalysis findByResume(Resume resume);

    ResumeAnalysis findByResumeId(Long resumeId);

}