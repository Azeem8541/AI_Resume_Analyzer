package com.azeem.airesumeanalyzer.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="analysis")
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer atsScore;

    @Lob
    private String missingSkills;

    @Lob
    private String strengths;

    @Lob
    private String weaknesses;

    @Lob
    private String suggestions;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name="resume_id")
    private Resume resume;

    @PrePersist
    public void prePersist(){
        createdAt=LocalDateTime.now();
    }

    public Analysis(){}

    // Getters and Setters
}