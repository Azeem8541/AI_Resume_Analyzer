package com.azeem.airesumeanalyzer.service.impl;

import com.azeem.airesumeanalyzer.dto.AnalysisResponseDTO;
import com.azeem.airesumeanalyzer.dto.ResumeResponseDTO;
import com.azeem.airesumeanalyzer.entity.Resume;
import com.azeem.airesumeanalyzer.entity.ResumeAnalysis;
import com.azeem.airesumeanalyzer.entity.User;
import com.azeem.airesumeanalyzer.repository.ResumeAnalysisRepository;
import com.azeem.airesumeanalyzer.repository.ResumeRepository;
import com.azeem.airesumeanalyzer.repository.UserRepository;
import com.azeem.airesumeanalyzer.security.JwtUtil;
import com.azeem.airesumeanalyzer.service.PdfService;
import com.azeem.airesumeanalyzer.service.ResumeAnalysisService;
import com.azeem.airesumeanalyzer.service.ResumeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.azeem.airesumeanalyzer.dto.ResumeHistoryDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.io.File;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final ResumeAnalysisRepository resumeAnalysisRepository;
    private final UserRepository userRepository;
    private final PdfService pdfService;
    private final JwtUtil jwtUtil;
    private final ResumeAnalysisService resumeAnalysisService;

    public ResumeServiceImpl(
            ResumeRepository resumeRepository,
            ResumeAnalysisRepository resumeAnalysisRepository,
            UserRepository userRepository,
            PdfService pdfService,
            JwtUtil jwtUtil,
            ResumeAnalysisService resumeAnalysisService
    ) {
        this.resumeRepository = resumeRepository;
        this.resumeAnalysisRepository = resumeAnalysisRepository;
        this.userRepository = userRepository;
        this.pdfService = pdfService;
        this.jwtUtil = jwtUtil;
        this.resumeAnalysisService = resumeAnalysisService;
    }

    @Override
    public ResumeResponseDTO uploadResume(MultipartFile file, String authHeader) {

        String token = authHeader.substring(7);
        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found.");
        }

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("No file was uploaded.");
        }

        String originalName = file.getOriginalFilename();
        boolean looksLikePdf =
                (originalName != null && originalName.toLowerCase().endsWith(".pdf"))
                && "application/pdf".equalsIgnoreCase(file.getContentType());

        if (!looksLikePdf) {
            throw new RuntimeException("Only PDF files are supported.");
        }

        try {

            // Create uploads folder inside project directory
            String uploadDir = System.getProperty("user.dir")
                    + File.separator + "uploads";

            File directory = new File(uploadDir);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate a safe, unique filename instead of trusting the client-supplied
            // name (prevents path traversal like "../../evil.pdf" and file overwrites
            // when two users upload files with the same name).
            String safeFileName = UUID.randomUUID() + ".pdf";

            String filePath = uploadDir
                    + File.separator
                    + safeFileName;

            File destinationFile = new File(filePath);

            // Save PDF
            file.transferTo(destinationFile);

            // Extract text from PDF
            String extractedText = pdfService.extractText(filePath);

            // Save Resume
            Resume resume = new Resume();
            resume.setFileName(file.getOriginalFilename());
            resume.setFilePath(filePath);
            resume.setExtractedText(extractedText);
            resume.setUser(user);

            Resume savedResume = resumeRepository.save(resume);

            // Analyze Resume
            AnalysisResponseDTO analysis =
                    resumeAnalysisService.analyzeResume(extractedText);

            // Save Analysis
            ResumeAnalysis resumeAnalysis = new ResumeAnalysis();

            resumeAnalysis.setScore(analysis.getScore());
            resumeAnalysis.setDetectedSkills(analysis.getDetectedSkills());
            resumeAnalysis.setMissingSkills(analysis.getMissingSkills());
            resumeAnalysis.setSuggestions(analysis.getSuggestions());
            resumeAnalysis.setAiAnalysis(analysis.getAiAnalysis());   // <-- YE LINE ADD KARNI HAI
            resumeAnalysis.setResume(savedResume);

            resumeAnalysisRepository.save(resumeAnalysis);

            return new ResumeResponseDTO(
                    savedResume.getId(),
                    savedResume.getFileName(),
                    "Resume uploaded successfully.",
                    analysis
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Actual Error: " + e.getMessage(), e);
        }
    }
    @Override
    public List<ResumeHistoryDTO> getMyResumes(String authHeader) {

        String token = authHeader.substring(7);
        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found.");
        }

        List<Resume> resumes = resumeRepository.findByUser(user);

        List<ResumeHistoryDTO> history = new ArrayList<>();

        for (Resume resume : resumes) {

            ResumeAnalysis analysis = resumeAnalysisRepository.findByResume(resume);

            int score = 0;

            if (analysis != null) {
                score = analysis.getScore();
            }

            history.add(new ResumeHistoryDTO(
                    resume.getId(),
                    resume.getFileName(),
                    score,
                    resume.getUploadTime()
            ));
        }

        return history;
    }
}