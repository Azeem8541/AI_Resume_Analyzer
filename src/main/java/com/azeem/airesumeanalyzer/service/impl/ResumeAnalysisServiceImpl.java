package com.azeem.airesumeanalyzer.service.impl;

import com.azeem.airesumeanalyzer.dto.AnalysisResponseDTO;
import com.azeem.airesumeanalyzer.entity.Resume;
import com.azeem.airesumeanalyzer.entity.ResumeAnalysis;
import com.azeem.airesumeanalyzer.entity.User;
import com.azeem.airesumeanalyzer.repository.ResumeAnalysisRepository;
import com.azeem.airesumeanalyzer.repository.ResumeRepository;
import com.azeem.airesumeanalyzer.repository.UserRepository;
import com.azeem.airesumeanalyzer.security.JwtUtil;
import com.azeem.airesumeanalyzer.service.GeminiService;
import com.azeem.airesumeanalyzer.service.ResumeAnalysisService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResumeAnalysisServiceImpl implements ResumeAnalysisService {

    private final GeminiService geminiService;
    private final ResumeRepository resumeRepository;
    private final ResumeAnalysisRepository resumeAnalysisRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public ResumeAnalysisServiceImpl(
            GeminiService geminiService,
            ResumeRepository resumeRepository,
            ResumeAnalysisRepository resumeAnalysisRepository,
            UserRepository userRepository,
            JwtUtil jwtUtil
    ) {
        this.geminiService = geminiService;
        this.resumeRepository = resumeRepository;
        this.resumeAnalysisRepository = resumeAnalysisRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AnalysisResponseDTO analyzeResume(String resumeText) {

        String aiResponse = geminiService.analyzeResume(resumeText);

        int score = extractScore(aiResponse);

        String detectedSkills = extractSection(
                aiResponse,
                "Strengths",
                "Weaknesses"
        );

        String missingSkills = extractSection(
                aiResponse,
                "Missing Skills",
                "Suggested Improvements"
        );

        String suggestions = extractSection(
                aiResponse,
                "Suggested Improvements",
                "Suitable Job Roles"
        );

        return new AnalysisResponseDTO(
                score,
                detectedSkills,
                missingSkills,
                suggestions,
                aiResponse
        );
    }

    @Override
    public AnalysisResponseDTO getAnalysis(Long resumeId, String authHeader) {

        String token = authHeader.substring(7);
        String email = jwtUtil.extractUsername(token);

        User requester = userRepository.findByEmail(email);

        if (requester == null) {
            throw new RuntimeException("User not found.");
        }

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() ->
                        new RuntimeException("Resume not found."));

        // Ownership check: prevent one user from reading another user's analysis (IDOR)
        if (resume.getUser() == null
                || !resume.getUser().getId().equals(requester.getId())) {
            throw new AccessDeniedException("You do not have access to this resume.");
        }

        ResumeAnalysis analysis =
                resumeAnalysisRepository.findByResume(resume);

        if (analysis == null) {
            throw new RuntimeException("Analysis not found.");
        }

        return new AnalysisResponseDTO(
                analysis.getScore(),
                analysis.getDetectedSkills(),
                analysis.getMissingSkills(),
                analysis.getSuggestions(),
                analysis.getAiAnalysis()
        );
    }

    private int extractScore(String text) {

        Pattern pattern = Pattern.compile("(\\d{1,3})");

        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {

            int value = Integer.parseInt(matcher.group());

            if (value >= 0 && value <= 100) {
                return value;
            }
        }

        return 0;
    }

    private String extractSection(
            String text,
            String start,
            String end
    ) {

        int startIndex =
                text.toLowerCase().indexOf(start.toLowerCase());

        if (startIndex == -1) {
            return "";
        }

        int endIndex =
                text.toLowerCase().indexOf(
                        end.toLowerCase(),
                        startIndex
                );

        if (endIndex == -1) {
            endIndex = text.length();
        }

        return text.substring(startIndex, endIndex)
                .replace(start, "")
                .replace(":", "")
                .trim();
    }
}