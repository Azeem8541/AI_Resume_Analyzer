package com.azeem.airesumeanalyzer.controller;

import com.azeem.airesumeanalyzer.dto.AnalysisResponseDTO;
import com.azeem.airesumeanalyzer.service.ResumeAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
public class ResumeAnalysisController {

    private final ResumeAnalysisService resumeAnalysisService;

    public ResumeAnalysisController(ResumeAnalysisService resumeAnalysisService) {
        this.resumeAnalysisService = resumeAnalysisService;
    }

    @GetMapping("/{resumeId}")
    public ResponseEntity<AnalysisResponseDTO> getResumeAnalysis(
            @PathVariable Long resumeId,
            @RequestHeader("Authorization") String authHeader
    ) {

        AnalysisResponseDTO response =
                resumeAnalysisService.getAnalysis(resumeId, authHeader);

        return ResponseEntity.ok(response);
    }
}