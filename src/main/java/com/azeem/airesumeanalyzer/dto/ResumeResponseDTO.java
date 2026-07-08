package com.azeem.airesumeanalyzer.dto;

public class ResumeResponseDTO {

    private Long resumeId;
    private String fileName;
    private String message;
    private AnalysisResponseDTO analysis;

    public ResumeResponseDTO() {
    }

    public ResumeResponseDTO(Long resumeId,
                             String fileName,
                             String message,
                             AnalysisResponseDTO analysis) {

        this.resumeId = resumeId;
        this.fileName = fileName;
        this.message = message;
        this.analysis = analysis;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AnalysisResponseDTO getAnalysis() {
        return analysis;
    }

    public void setAnalysis(AnalysisResponseDTO analysis) {
        this.analysis = analysis;
    }
}