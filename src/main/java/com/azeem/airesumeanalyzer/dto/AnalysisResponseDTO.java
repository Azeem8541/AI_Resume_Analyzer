package com.azeem.airesumeanalyzer.dto;

public class AnalysisResponseDTO {

    private int score;

    private String detectedSkills;

    private String missingSkills;

    private String suggestions;

    private String aiAnalysis;

    public AnalysisResponseDTO() {
    }

    public AnalysisResponseDTO(int score,
                               String detectedSkills,
                               String missingSkills,
                               String suggestions,
                               String aiAnalysis) {

        this.score = score;
        this.detectedSkills = detectedSkills;
        this.missingSkills = missingSkills;
        this.suggestions = suggestions;
        this.aiAnalysis = aiAnalysis;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDetectedSkills() {
        return detectedSkills;
    }

    public void setDetectedSkills(String detectedSkills) {
        this.detectedSkills = detectedSkills;
    }

    public String getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(String missingSkills) {
        this.missingSkills = missingSkills;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public String getAiAnalysis() {
        return aiAnalysis;
    }

    public void setAiAnalysis(String aiAnalysis) {
        this.aiAnalysis = aiAnalysis;
    }
}