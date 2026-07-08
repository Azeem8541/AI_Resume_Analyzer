package com.azeem.airesumeanalyzer.service;

import java.io.IOException;

public interface PdfService {

    String extractText(String filePath) throws IOException;

}