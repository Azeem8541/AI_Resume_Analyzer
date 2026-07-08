package com.azeem.airesumeanalyzer.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="interview_history")
public class InterviewHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String question;

    @Lob
    private String answer;

    @Lob
    private String feedback;

    private Integer score;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @PrePersist
    public void prePersist(){
        createdAt=LocalDateTime.now();
    }

    public InterviewHistory(){}

    // Getters and Setters
}