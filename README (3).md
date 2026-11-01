# 🧠 AI Resume Analyzer

An AI-powered backend application built with **Spring Boot** that analyzes resumes using the **Google Gemini API**. It extracts text from PDF resumes, produces ATS (Applicant Tracking System) scoring and feedback, and stores structured results in a **MySQL** database. APIs are secured with **JWT authentication**.

---

## ✨ Features

- 📄 Upload a resume (PDF) and extract its text using Apache PDFBox
- 🤖 AI-based resume analysis powered by Google Gemini
- 📊 ATS scoring logic with improvement suggestions
- 🗄️ Structured storage of users and analysis results in MySQL
- 🔐 Secure authentication & authorization with Spring Security + JWT
- ✅ Request validation using Bean Validation
- 📘 Interactive API docs with Swagger UI (springdoc-openapi)

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java 21 |
| Framework | Spring Boot 3.5.6 |
| Security | Spring Security, JWT (jjwt 0.12.6) |
| Database | MySQL, Spring Data JPA (Hibernate) |
| AI | Google Gemini API (via Spring WebFlux `WebClient`) |
| PDF Parsing | Apache PDFBox 3.0.3 |
| API Docs | springdoc-openapi (Swagger UI) |
| Build Tool | Maven (Maven Wrapper included) |
| Utilities | Lombok, Bean Validation |

---

## 🏗️ Architecture

```
Client  ──►  REST Controller  ──►  Service Layer  ──►  Gemini API
                 │                      │
          JWT Security Filter     PDFBox (text extraction)
                                        │
                                  JPA Repository  ──►  MySQL
```

1. User registers / logs in and receives a JWT token.
2. User uploads a PDF resume with the token.
3. The app extracts text from the PDF (PDFBox).
4. Extracted text is sent to Gemini for analysis and ATS scoring.
5. The structured result is saved in MySQL and returned to the user.

---

## 📁 Project Structure

```
AI_Resume_Analyzer/
├── src/
│   ├── main/
│   │   ├── java/com/azeem/...     # Controllers, services, entities, security
│   │   └── resources/             # application properties
│   └── test/
├── .env.example                   # Environment variable template
├── pom.xml
├── mvnw / mvnw.cmd                # Maven wrapper
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- Java 21
- MySQL 8+
- A Gemini API key from [Google AI Studio](https://aistudio.google.com/apikey)

### 1. Clone the repository

```bash
git clone https://github.com/Azeem8541/AI_Resume_Analyzer.git
cd AI_Resume_Analyzer
```

### 2. Create the database

```sql
CREATE DATABASE ai_resume_analyzer;
```

### 3. Configure environment variables

Copy `.env.example` to `.env` (or set these as real environment variables / in your IDE run configuration) and fill in your values:

| Variable | Description | Default |
|----------|-------------|---------|
| `DB_HOST` | MySQL host | `localhost` |
| `DB_PORT` | MySQL port | `3306` |
| `DB_NAME` | Database name | `ai_resume_analyzer` |
| `DB_USERNAME` | Database user | `root` |
| `DB_PASSWORD` | Database password | – |
| `JWT_SECRET` | Long random secret (32+ chars) | – |
| `JWT_EXPIRATION_MS` | Token validity in ms | `86400000` (24h) |
| `GEMINI_API_KEY` | Google Gemini API key | – |
| `SERVER_PORT` | Server port | `8080` |
| `DDL_AUTO` | Hibernate DDL mode | `update` |
| `SHOW_SQL` | Log SQL queries | `false` |
| `UPLOAD_DIR` | Folder for uploaded resumes | `uploads/` |

Generate a strong JWT secret:

```bash
openssl rand -base64 48
```

### 4. Run the application

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

The app starts at **http://localhost:8080**

### 5. Open the API docs

Swagger UI: **http://localhost:8080/swagger-ui/index.html**

---

## 🔌 API Overview

> Replace/extend this table with your actual endpoints (you can copy them from Swagger UI).

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/auth/register` | Register a new user | ❌ |
| POST | `/api/auth/login` | Login and get JWT token | ❌ |
| POST | `/api/resume/upload` | Upload & analyze a resume (PDF) | ✅ |
| GET | `/api/resume/{id}` | Get a saved analysis result | ✅ |

Send the token in the `Authorization` header:

```
Authorization: Bearer <your_jwt_token>
```

---

## 📸 Screenshots

> Add Swagger UI and sample analysis output screenshots here.

```
docs/swagger.png
docs/sample-analysis.png
```

---

## 🔒 Security & Privacy Notes

- Real secrets live only in `.env` / environment variables, which are git-ignored. Never commit them.
- Uploaded resumes are stored in `uploads/`, which is also git-ignored.
- **Privacy:** resume text may contain personal data (name, email, phone). This text is sent to the Google Gemini API for analysis. Do not upload sensitive documents you don't want processed by a third-party AI service.
- `DDL_AUTO=update` is convenient for development. Use `validate` or proper migrations (Flyway/Liquibase) in production.

---

## 🗺️ Future Improvements

- Dockerize the application (Dockerfile + docker-compose with MySQL)
- Add unit and integration tests
- Match resume against a specific job description
- Support DOCX resumes
- Add a frontend (React)
- Flyway database migrations
- CI pipeline with GitHub Actions

---

## 👤 Author

**Azeem Akhtar** — Data Engineer Aspirant

[LinkedIn](https://www.linkedin.com/in/azeemakhtar-) | [GitHub](https://github.com/Azeem8541)
