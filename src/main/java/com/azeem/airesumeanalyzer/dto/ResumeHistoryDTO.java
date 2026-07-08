package com.azeem.airesumeanalyzer.dto;

import java.time.LocalDateTime;

public class ResumeHistoryDTO {

    private Long resumeId;
    private String fileName;
    private int score;
    private LocalDateTime uploadTime;

    public ResumeHistoryDTO() {
    }

    public ResumeHistoryDTO(Long resumeId,
                            String fileName,
                            int score,
                            LocalDateTime uploadTime) {

        this.resumeId = resumeId;
        this.fileName = fileName;
        this.score = score;
        this.uploadTime = uploadTime;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }
}