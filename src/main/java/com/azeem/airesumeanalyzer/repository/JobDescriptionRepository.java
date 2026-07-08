package com.azeem.airesumeanalyzer.repository;

import com.azeem.airesumeanalyzer.entity.JobDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobDescriptionRepository
        extends JpaRepository<JobDescription,Long>{

}