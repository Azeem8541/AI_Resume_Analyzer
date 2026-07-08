package com.azeem.airesumeanalyzer.service;

import com.azeem.airesumeanalyzer.dto.AnalysisResponseDTO;

public interface ResumeAnalysisService {

    AnalysisResponseDTO analyzeResume(String resumeText);

    AnalysisResponseDTO getAnalysis(Long resumeId, String authHeader);

}