# Exam System - Architecture Overview

## System Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                         CLIENT LAYER                             │
│  (Web Browser, Mobile App, Desktop Client via REST API)          │
└────────────────────────────┬──────────────────────────────────────┘
                             │
                    HTTP/REST │
                             │
┌────────────────────────────▼──────────────────────────────────────┐
│                    API LAYER (Controllers)                         │
│  ┌─────────────────────────────────────────────────────────────┐  │
│  │ ExamController (/api/exams)                                │  │
│  │  - POST   / (Create)                                       │  │
│  │  - GET    /{id} (Retrieve)                                │  │
│  │  - GET    /module/{moduleId} (List by Module)            │  │
│  │  - PUT    /{id} (Update)                                 │  │
│  │  - DELETE /{id} (Delete)                                 │  │
│  │  - POST   /submit (Submit & Grade)                       │  │
│  └─────────────────────────────────────────────────────────────┘  │
└────────────────────────────┬──────────────────────────────────────┘
                             │
                   DTO DTO DTO│ DTO DTO
                   Request   │ Response
                             │
┌────────────────────────────▼──────────────────────────────────────┐
│                   SERVICE LAYER (Business Logic)                  │
│  ┌─────────────────────────────────────────────────────────────┐  │
│  │ ExamService                                               │  │
│  │  - createExam()                                           │  │
│  │  - getExam()                                              │  │
│  │  - getExamsByModule()                                     │  │
│  │  - updateExam()                                           │  │
│  │  - deleteExam()                                           │  │
│  │  - submitExam()  (Scoring Logic)                         │  │
│  │  - Conversion Methods (Entity ↔ DTO)                    │  │
│  └─────────────────────────────────────────────────────────────┘  │
└────────────────────────────┬──────────────────────────────────────┘
                             │
                   Spring    │ Data
                   Manages   │ Access
                             │
┌────────────────────────────▼──────────────────────────────────────┐
│                  REPOSITORY LAYER (Data Access)                   │
│  ┌─────────────────┐  ┌──────────────────┐  ┌────────────────┐  │
│  │ ExamRepository  │  │ ExamQuestion     │  │ ExamAnswer     │  │
│  │                 │  │ Repository       │  │ Repository     │  │
│  │ - findById      │  │ - findById       │  │ - findById     │  │
│  │ - findAll       │  │ - findByExamId   │  │ - findByQuest  │  │
│  │ - findByModule  │  │ - Custom Queries │  │ - Custom Qries │  │
│  │ - save/delete   │  │ - save/delete    │  │ - save/delete  │  │
│  └─────────────────┘  └──────────────────┘  └────────────────┘  │
└────────────────────────────┬──────────────────────────────────────┘
                             │
                    JPA/ORM  │
                   Mapping   │
                             │
┌────────────────────────────▼──────────────────────────────────────┐
│                      DATABASE LAYER (MySQL)                        │
│  ┌──────────────────────────────────────────────────────────────┐ │
│  │                                                              │ │
│  │  ┌──────────────┐  ┌─────────────────┐  ┌──────────────┐   │ │
│  │  │ EXAM         │  │ EXAM_QUESTIONS  │  │ EXAM_ANSWERS │   │ │
│  │  ├──────────────┤  ├─────────────────┤  ├──────────────┤   │ │
│  │  │ id (PK)      │  │ id (PK)         │  │ id (PK)      │   │ │
│  │  │ title        │  │ question_text   │  │ option_text  │   │ │
│  │  │ description  │  │ image_url       │  │ image_url    │   │ │
│  │  │ module_id(FK)├──│ order_index     │  │ is_correct   │   │ │
│  │  │ total_q      │  │ exam_id (FK)────┼──│ order_index  │   │ │
│  │  │ passing_score│  │                 │  │ question_id(FK)  │ │
│  │  │ time_limit   │  │                 │  │                │ │
│  │  │ created_at   │  │                 │  │                │ │
│  │  └──────────────┘  └─────────────────┘  └──────────────┘   │ │
│  │         ▲                    ▲                   ▲          │ │
│  │         └────────────────────┼───────────────────┘          │ │
│  │         One Exam has many   │  Questions have many        │ │
│  │         Questions           │  Answers                    │ │
│  │                                                              │ │
│  └──────────────────────────────────────────────────────────────┘ │
└──────────────────────────────────────────────────────────────────────┘
```

## Data Flow Diagrams

### Create Exam Flow
```
Client                    Controller              Service              Repository        Database
  │                          │                       │                     │                │
  ├─ POST /api/exams ────────>                       │                     │                │
  │  (ExamCreateRequest)     │                       │                     │                │
  │                          ├─ Validate Input ─────>│                     │                │
  │                          │                       ├─ Create Exam ──────>│                │
  │                          │                       │                     ├─ INSERT ──────>│
  │                          │                       │<─ Exam ID ─────────┤                │
  │                          │                       │                     │    ✓ Saved    │
  │                          │                       ├─ Create Questions ─>│                │
  │                          │                       │<─ Question IDs ────┤                │
  │                          │                       │                     ├─ INSERT ──────>│
  │                          │                       ├─ Create Answers ───>│                │
  │                          │                       │<─ Answer IDs ──────┤                │
  │                          │                       │                     ├─ INSERT ──────>│
  │                          │<─ ExamCreateResponse ─┤                     │                │
  │<─ 201 Created ───────────┤                       │                     │                │
  │  (ExamCreateResponse)    │                       │                     │                │
```

### Submit Exam Flow (Scoring)
```
Client                  Controller              Service              Repository        Database
  │                       │                       │                     │                │
  ├─ POST /submit ───────>│                       │                     │                │
  │ (ExamSubmitRequest)   │                       │                     │                │
  │                       ├─ Validate ──────────>│                     │                │
  │                       │                       ├─ Get Exam ─────────>│                │
  │                       │                       │<─ Exam Data────────┤                │
  │                       │                       ├─ Get Questions ────>│                │
  │                       │                       │<─ Questions────────┤                │
  │                       │                       ├─ Get Answers ──────>│                │
  │                       │                       │<─ Answers ─────────┤                │
  │                       │                       │                     │                │
  │                       │                       ├─ Compare Answers   │                │
  │                       │                       ├─ Calculate Score   │                │
  │                       │                       ├─ Check Pass/Fail   │                │
  │                       │                       ├─ Build Results     │                │
  │                       │<─ ExamSubmitResponse ─┤                     │                │
  │<─ 200 OK ─────────────┤                       │                     │                │
  │ (ExamSubmitResponse)  │                       │                     │                │
```

## Entity Relationships

```
┌─────────────┐          ┌─────────────────┐          ┌───────────────┐
│   Module    │          │     Exam        │          │   Lesson      │
├─────────────┤          ├─────────────────┤          ├───────────────┤
│ id          │          │ id              │          │ id            │
│ title       │◄─────────┤ module_id (FK)  │          │ module_id(FK) │
│ description │          │ title           │          │ title         │
│ orderIndex  │          │ description     │          │ description   │
│             │          │ totalQuestions  │          │ videoUrl      │
│             │          │ passingScore    │          │ lessonOrder   │
│             │          │ timeLimit       │          │ durationSecs  │
│             │          │ createdAt       │          │               │
└─────────────┘          └────────┬────────┘          └───────────────┘
                                  │
                                  │ 1:N
                                  │
                         ┌────────▼────────┐
                         │  ExamQuestion   │
                         ├─────────────────┤
                         │ id              │
                         │ questionText    │
                         │ imageUrl        │
                         │ orderIndex      │
                         │ exam_id (FK)    │
                         └────────┬────────┘
                                  │
                                  │ 1:N
                                  │
                         ┌────────▼────────┐
                         │   ExamAnswer    │
                         ├─────────────────┤
                         │ id              │
                         │ optionText      │
                         │ imageUrl        │
                         │ isCorrect       │
                         │ orderIndex      │
                         │ question_id(FK) │
                         └─────────────────┘
```

## DTO Conversion Flow

```
Input DTO                Service Conversion              Output DTO
(Request)                       │                      (Response)

ExamCreateRequest       ┌───────────────────┐         ExamCreateResponse
├─ title                │ Exam Entity       │         ├─ id
├─ description         │ ├─ Created        │         ├─ title
├─ moduleId            │ └─ Saved to DB    │         ├─ description
├─ totalQuestions      │                   │         ├─ moduleId
├─ passingScore        ├─ toExamCreateResp │         ├─ totalQuestions
├─ timeLimit           │                   │         ├─ passingScore
└─ questions[]         └───────────────────┘         └─ timeLimit
  ├─ questionText              │
  ├─ imageUrl                  │
  ├─ orderIndex                │
  └─ answers[]           ExamResponse
    ├─ optionText        ├─ id
    ├─ imageUrl          ├─ title
    ├─ isCorrect         ├─ description
    └─ orderIndex        ├─ moduleId
                         ├─ totalQuestions
                         ├─ passingScore
                         ├─ timeLimit
ExamSubmitRequest       └─ questions[]
├─ examId                 ├─ ExamQuestionResponse
└─ answers[]              │ ├─ id
  ├─ questionId           │ ├─ questionText
  └─ answerId             │ ├─ imageUrl
          │               │ ├─ orderIndex
          │               │ └─ answers[]
          │               │   └─ ExamOptionResponse
          │               │     ├─ id
          │               │     ├─ optionText
          │               │     ├─ imageUrl
          │               │     └─ orderIndex
          │               │
Scoring Logic ◄───────────┘
Calculate Score
Determine Pass/Fail
Build Results

          │
          ▼
      ExamSubmitResponse
      ├─ examId
      ├─ totalQuestions
      ├─ correctAnswers
      ├─ score
      ├─ passed
      └─ results[]
        └─ ExamQuestionResult
          ├─ questionId
          ├─ questionText
          ├─ selectedAnswerId
          ├─ selectedAnswerText
          ├─ correctAnswerId
          ├─ correctAnswerText
          └─ isCorrect
```

## Component Interaction Diagram

```
┌────────────────────────────────────────────────────────────────┐
│                    Spring Boot Application                     │
├────────────────────────────────────────────────────────────────┤
│                                                                │
│  ┌──────────────────┐      ┌──────────────────────────────┐  │
│  │ ExamController   │      │  Request/Response            │  │
│  │  (REST Endpoints)├─────>│  DTO Layer                   │  │
│  │                  │      │  - ExamCreateRequest         │  │
│  └──────────────────┘      │  - ExamResponse              │  │
│           │                │  - ExamSubmitRequest/Response│  │
│           │                └──────────────────────────────┘  │
│           │                         │                        │
│           ▼                         ▼                        │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ ExamService                                          │   │
│  │  - createExam()                                      │   │
│  │  - submitExam() ◄─────── Scoring Algorithm         │   │
│  │  - updateExam()              └─ Score Calculation   │   │
│  │  - deleteExam()              └─ Pass/Fail Check    │   │
│  │  - toExamResponse() ◄─── Conversion Methods        │   │
│  └──────────────────────────────────────────────────────┘   │
│           │                                                  │
│           ├──────┬─────────┬─────────┐                      │
│           ▼      ▼         ▼         ▼                      │
│  ┌──────────────┐ ┌───────────────┐ ┌─────────────────┐   │
│  │ ExamRepository│ │ExamQuestionRep│ │ ExamAnswerRepo  │   │
│  │              │ │               │ │                 │   │
│  │ - findById() │ │ - findByExamId│ │ - findByQuestion│   │
│  │ - findByModule│ │ - save()      │ │ - findByIdCorr  │   │
│  │ - save()     │ │ - delete()    │ │ - save()/delete │   │
│  │ - delete()   │ │               │ │                 │   │
│  └──────────────┘ └───────────────┘ └─────────────────┘   │
│           │              │                  │               │
│           └──────────────┼──────────────────┘               │
│                          │                                  │
│                          ▼                                  │
│                  ┌─────────────────┐                       │
│                  │  MySQL Database │                       │
│                  │  - exam         │                       │
│                  │  - exam_questions│                      │
│                  │  - exam_answers │                       │
│                  └─────────────────┘                       │
│                                                            │
└────────────────────────────────────────────────────────────┘

Security Layer (Spring Security)
└─ JWT Token Validation on all endpoints
└─ Role-based Access Control
```

## API Request/Response Cycle

```
1. CLIENT REQUEST
   ├─ HTTP Method: POST, GET, PUT, DELETE
   ├─ Endpoint: /api/exams/{operation}
   ├─ Headers:
   │  ├─ Authorization: Bearer {JWT_TOKEN}
   │  └─ Content-Type: application/json
   └─ Body: Request DTO (JSON)

2. CONTROLLER PROCESSING
   ├─ Validate JWT Token (Security)
   ├─ Deserialize JSON to DTO
   ├─ Validate DTO (@Valid annotations)
   └─ Call Service Method

3. SERVICE PROCESSING
   ├─ Business Logic Validation
   ├─ Data Transformation
   ├─ Call Repository Methods
   ├─ Handle Exceptions
   └─ Convert Entity to Response DTO

4. REPOSITORY PROCESSING
   ├─ Build SQL Query (via JPA)
   ├─ Execute Query
   ├─ Map Results to Entity
   └─ Return to Service

5. DATABASE OPERATION
   ├─ Parse SQL
   ├─ Acquire Connection
   ├─ Execute Query
   ├─ Commit/Rollback Transaction
   └─ Return Results

6. RESPONSE CONSTRUCTION
   ├─ Serialize Response DTO to JSON
   ├─ Add HTTP Status Code
   ├─ Add Response Headers
   └─ Return to Client

7. CLIENT RESPONSE
   ├─ HTTP Status Code
   ├─ Response Headers
   ├─ Response Body (JSON)
   └─ Process Data
```

---

**System Design Principles Applied:**
- ✅ Separation of Concerns (Controller, Service, Repository)
- ✅ Single Responsibility Principle
- ✅ Dependency Injection
- ✅ Layered Architecture
- ✅ DTO Pattern for API Security
- ✅ Transaction Management
- ✅ Error Handling
- ✅ RESTful API Design
