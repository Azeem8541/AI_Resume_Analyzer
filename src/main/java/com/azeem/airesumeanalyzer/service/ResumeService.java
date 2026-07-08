package com.azeem.airesumeanalyzer.service;

import com.azeem.airesumeanalyzer.dto.ResumeHistoryDTO;
import com.azeem.airesumeanalyzer.dto.ResumeResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {

    ResumeResponseDTO uploadResume(
            MultipartFile file,
            String authHeader
    );

    List<ResumeHistoryDTO> getMyResumes(
            String authHeader
    );

}