package com.azeem.airesumeanalyzer.controller;

import com.azeem.airesumeanalyzer.dto.ResumeHistoryDTO;
import com.azeem.airesumeanalyzer.dto.ResumeResponseDTO;
import com.azeem.airesumeanalyzer.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResumeResponseDTO> uploadResume(

            @RequestParam("file") MultipartFile file,

            @RequestHeader("Authorization") String authHeader
    ) {

        ResumeResponseDTO response =
                resumeService.uploadResume(file, authHeader);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-resumes")
    public ResponseEntity<List<ResumeHistoryDTO>> getMyResumes(

            @RequestHeader("Authorization") String authHeader
    ) {

        return ResponseEntity.ok(
                resumeService.getMyResumes(authHeader)
        );
    }
}