package com.azeem.airesumeanalyzer.service.impl;

import com.azeem.airesumeanalyzer.dto.GeminiRequestDTO;
import com.azeem.airesumeanalyzer.dto.GeminiResponseDTO;
import com.azeem.airesumeanalyzer.service.GeminiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class GeminiServiceImpl implements GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    public GeminiServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public String analyzeResume(String resumeText) {

        String prompt = """
                You are an ATS Resume Analyzer.

                Analyze this resume and provide:

                1. ATS Score (0-100)
                2. Strengths
                3. Weaknesses
                4. Missing Skills
                5. Suggested Improvements
                6. Suitable Job Roles

                Resume:
                """ + resumeText;

        GeminiRequestDTO.Part part =
                new GeminiRequestDTO.Part(prompt);

        GeminiRequestDTO.Content content =
                new GeminiRequestDTO.Content(List.of(part));

        GeminiRequestDTO request =
                new GeminiRequestDTO(List.of(content));

        GeminiResponseDTO response = webClient.post()
                .uri("/models/gemini-2.0-flash:generateContent?key=" + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GeminiResponseDTO.class)
                .block();

        if (response != null
                && response.getCandidates() != null
                && !response.getCandidates().isEmpty()
                && response.getCandidates().get(0).getContent() != null
                && response.getCandidates().get(0).getContent().getParts() != null
                && !response.getCandidates().get(0).getContent().getParts().isEmpty()) {

            return response.getCandidates()
                    .get(0)
                    .getContent()
                    .getParts()
                    .get(0)
                    .getText();
        }

        return "Unable to analyze resume.";
    }
}