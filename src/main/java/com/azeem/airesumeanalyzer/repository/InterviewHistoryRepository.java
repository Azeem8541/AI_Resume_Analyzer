package com.azeem.airesumeanalyzer.repository;

import com.azeem.airesumeanalyzer.entity.InterviewHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewHistoryRepository
        extends JpaRepository<InterviewHistory,Long>{

}