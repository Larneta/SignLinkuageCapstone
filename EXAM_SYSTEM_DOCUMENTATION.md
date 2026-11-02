# ASL Learning Platform - Exam System Documentation

## Overview
The Exam system is a comprehensive component of the SignLinkuage Capstone project that allows learners to test their knowledge of American Sign Language (ASL) letters through interactive image-based questions.

## Architecture

### Entities

#### 1. **Exam Entity**
- **Purpose**: Represents a complete exam assessment
- **Key Fields**:
  - `id`: Unique identifier
  - `title`: Exam title (e.g., "ASL ABC Mastery Exam")
  - `description`: Exam description
  - `module`: Reference to the Module (one-to-many relationship)
  - `totalQuestions`: Number of questions in the exam
  - `passingScore`: Minimum score percentage required to pass
  - `timeLimit`: Time allowed in minutes
  - `questions`: List of ExamQuestion objects
  - `createdAt`: Timestamp of exam creation

#### 2. **ExamQuestion Entity**
- **Purpose**: Represents a single question within an exam
- **Key Fields**:
  - `id`: Unique identifier
  - `questionText`: The question text (e.g., "Which image shows the correct ASL sign for the letter 'C'?")
  - `imageUrl`: URL to the image showing the ASL sign being asked about
  - `orderIndex`: Position of the question in the exam
  - `exam`: Reference to the parent Exam
  - `answers`: List of ExamAnswer objects (multiple choice options)

#### 3. **ExamAnswer Entity**
- **Purpose**: Represents a single answer option for an exam question
- **Key Fields**:
  - `id`: Unique identifier
  - `optionText`: The letter option (A, B, C, D, etc.)
  - `imageUrl`: URL to the image of the ASL sign this option represents
  - `isCorrect`: Boolean flag indicating if this is the correct answer
  - `orderIndex`: Position of the option in the list
  - `question`: Reference to the parent ExamQuestion

### Data Transfer Objects (DTOs)

#### Request DTOs

1. **ExamCreateRequest**
   - Used for creating new exams
   - Includes nested `ExamQuestionRequest` and `ExamAnswerRequest` classes
   - Validates all required fields

2. **ExamUpdateRequest**
   - Used for updating existing exams
   - Similar structure to create request but may have different validation rules

3. **ExamSubmitRequest**
   - Used when a learner submits their exam answers
   - Contains exam ID and list of answer submissions
   - Each submission includes questionId and answerId

#### Response DTOs

1. **ExamCreateResponse**
   - Returns created exam summary (id, title, module info, etc.)

2. **ExamResponse**
   - Full exam details including all questions and answers
   - Used for retrieving exam to take/review

3. **ExamQuestionResponse**
   - Individual question with all its answer options

4. **ExamOptionResponse**
   - Single answer option with image URL

5. **ExamSubmitResponse**
   - Detailed results after exam submission
   - Includes:
     - Total questions and correct answers
     - Score percentage
     - Pass/fail status
     - Detailed results for each question showing:
       - Selected answer
       - Correct answer
       - Whether it was correct

## API Endpoints

### Base URL: `/api/exams`

#### 1. Create Exam
- **POST** `/api/exams`
- **Request**: ExamCreateRequest
- **Response**: ExamCreateResponse (201 Created)
- **Description**: Create a new exam with questions and answers

#### 2. Get Exam
- **GET** `/api/exams/{examId}`
- **Response**: ExamResponse (200 OK)
- **Description**: Retrieve complete exam details for taking the exam

#### 3. Get Exams by Module
- **GET** `/api/exams/module/{moduleId}`
- **Response**: List<ExamResponse> (200 OK)
- **Description**: Get all exams for a specific module

#### 4. Update Exam
- **PUT** `/api/exams/{examId}`
- **Request**: ExamUpdateRequest
- **Response**: ExamResponse (200 OK)
- **Description**: Update exam details and questions

#### 5. Delete Exam
- **DELETE** `/api/exams/{examId}`
- **Response**: 204 No Content
- **Description**: Remove an exam

#### 6. Submit Exam
- **POST** `/api/exams/submit`
- **Request**: ExamSubmitRequest
- **Response**: ExamSubmitResponse (200 OK)
- **Description**: Submit exam answers and get scoring results

## Service Layer

### ExamService
Handles all business logic for exam operations:
- **createExam()**: Creates exam with questions and answers
- **getExam()**: Retrieves exam by ID
- **getExamsByModule()**: Gets all exams for a module
- **updateExam()**: Updates exam and recreates questions
- **deleteExam()**: Removes exam and all associated data
- **submitExam()**: Scores exam and returns results

**Key Features**:
- Transactional operations ensure data consistency
- Automatic scoring calculation
- Conversion between entities and DTOs
- Comprehensive error handling

## Database Schema

```
exam
├── id (PK)
├── title
├── description
├── module_id (FK)
├── total_questions
├── passing_score
├── time_limit
└── created_at

exam_questions
├── id (PK)
├── question_text
├── image_url
├── order_index
├── exam_id (FK)

exam_answers
├── id (PK)
├── option_text
├── image_url
├── is_correct
├── order_index
├── question_id (FK)
```

## Sample Data

The `exam_data.sql` file contains 12 exam questions covering ASL letters A-Z with:
- Question images showing the ASL sign being tested
- 4 answer options each with images
- Clear indication of the correct answer
- Questions ordered from easiest to harder

### Example Question Structure:
```
Question: "Which image shows the correct ASL sign for the letter 'C'?"
Image: /images/asl/question_letter_c.jpg
Answers:
  - A: /images/asl/letter_a.jpg (incorrect)
  - B: /images/asl/letter_b.jpg (incorrect)
  - C: /images/asl/letter_c.jpg (CORRECT) ✓
  - D: /images/asl/letter_d.jpg (incorrect)
```

## Repositories

### ExamRepository
- `findByModuleId(Long moduleId)`: Get exams for a module
- `findByIdAndModuleId()`: Validate exam belongs to module

### ExamQuestionRepository
- `findByExamIdOrderByOrderIndex()`: Get sorted questions
- `findByExamId()`: Get all questions for exam

### ExamAnswerRepository
- `findByQuestionIdOrderByOrderIndex()`: Get sorted answers
- `findByIdAndIsCorrect()`: Find correct answer

## Scoring Logic

```
Score = (Correct Answers × 100) / Total Questions
Passed = Score >= Passing Score (default 70%)
```

## Example Request/Response

### Create Exam Request:
```json
{
  "title": "ASL ABC Mastery Exam",
  "description": "Comprehensive exam covering all ASL letters",
  "moduleId": 1,
  "totalQuestions": 12,
  "passingScore": 70,
  "timeLimit": 30,
  "questions": [
    {
      "questionText": "Which image shows the correct ASL sign for the letter 'C'?",
      "imageUrl": "/images/asl/question_letter_c.jpg",
      "orderIndex": 1,
      "answers": [
        {
          "optionText": "A",
          "imageUrl": "/images/asl/letter_a.jpg",
          "isCorrect": false,
          "orderIndex": 1
        },
        {
          "optionText": "C",
          "imageUrl": "/images/asl/letter_c.jpg",
          "isCorrect": true,
          "orderIndex": 3
        }
      ]
    }
  ]
}
```

### Submit Exam Request:
```json
{
  "examId": 1,
  "answers": [
    {
      "questionId": 1,
      "answerId": 3
    },
    {
      "questionId": 2,
      "answerId": 5
    }
  ]
}
```

### Submit Exam Response:
```json
{
  "examId": 1,
  "totalQuestions": 12,
  "correctAnswers": 10,
  "score": 83,
  "passed": true,
  "results": [
    {
      "questionId": 1,
      "questionText": "Which image shows the correct ASL sign for the letter 'C'?",
      "imageUrl": "/images/asl/question_letter_c.jpg",
      "selectedAnswerId": 3,
      "selectedAnswerText": "C",
      "selectedAnswerImageUrl": "/images/asl/letter_c.jpg",
      "correctAnswerId": 3,
      "correctAnswerText": "C",
      "correctAnswerImageUrl": "/images/asl/letter_c.jpg",
      "isCorrect": true
    }
  ]
}
```

## Integration Notes

1. **Module Integration**: Exams are linked to Modules
2. **User Progress**: Can be extended to track user exam results via UserExamProgress entity
3. **Security**: All endpoints require USER role or higher
4. **Transaction Management**: Critical operations are transactional
5. **DTO Pattern**: All API responses use DTOs (no raw entities)

## Future Enhancements

1. **UserExamProgress Entity**: Track individual user exam attempts and scores
2. **Timed Exams**: Implement countdown timer with auto-submit
3. **Adaptive Testing**: Adjust question difficulty based on performance
4. **Exam Review**: Allow users to review their answers after submission
5. **Analytics Dashboard**: Show exam statistics and learner performance metrics
6. **Multiple Attempts**: Track best score, average score, etc.
7. **Certificate Generation**: Award certificates upon passing

## Testing

Example test scenarios:
1. Create exam with valid data → 201 Created
2. Create exam with missing required fields → 400 Bad Request
3. Get non-existent exam → 404 Not Found
4. Submit exam with correct/incorrect answers → 200 OK with results
5. Update exam and verify changes → 200 OK
6. Delete exam and verify it's gone → 204 No Content

## Notes

- All image URLs should point to actual ASL sign language images
- Ensure images are properly formatted and optimized for web
- Consider implementing image caching for performance
- All timestamps are stored in UTC
- The exam service automatically calculates scores and pass/fail status
- Each exam is linked to a specific module for curriculum organization
