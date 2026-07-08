package com.azeem.airesumeanalyzer.repository;

import com.azeem.airesumeanalyzer.entity.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository
        extends JpaRepository<Analysis,Long>{

}