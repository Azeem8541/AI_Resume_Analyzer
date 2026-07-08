package com.azeem.airesumeanalyzer.service.impl;

import com.azeem.airesumeanalyzer.service.PdfService;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public String extractText(String filePath) throws IOException {

        File file = new File(filePath);

        PDDocument document = Loader.loadPDF(file);

        PDFTextStripper pdfTextStripper = new PDFTextStripper();

        String text = pdfTextStripper.getText(document);

        document.close();

        return text;
    }
}