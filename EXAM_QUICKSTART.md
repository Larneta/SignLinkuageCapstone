# Exam System - Quick Start Guide

## 🚀 Getting Started in 5 Minutes

### Step 1: Verify All Files Are Created
Check that these files exist in your project:

**Entities:**
- `src/main/java/org/example/signlinkuagecapstone/entity/Exam.java`
- `src/main/java/org/example/signlinkuagecapstone/entity/ExamQuestion.java`
- `src/main/java/org/example/signlinkuagecapstone/entity/ExamAnswer.java`

**DTOs:**
- `src/main/java/org/example/signlinkuagecapstone/dto/ExamCreateRequest.java`
- `src/main/java/org/example/signlinkuagecapstone/dto/ExamCreateResponse.java`
- `src/main/java/org/example/signlinkuagecapstone/dto/ExamOptionResponse.java`
- `src/main/java/org/example/signlinkuagecapstone/dto/ExamQuestionResponse.java`
- `src/main/java/org/example/signlinkuagecapstone/dto/ExamResponse.java`
- `src/main/java/org/example/signlinkuagecapstone/dto/ExamSubmitRequest.java`
- `src/main/java/org/example/signlinkuagecapstone/dto/ExamSubmitResponse.java`
- `src/main/java/org/example/signlinkuagecapstone/dto/ExamUpdateRequest.java`

**Repositories:**
- `src/main/java/org/example/signlinkuagecapstone/repository/ExamRepository.java`
- `src/main/java/org/example/signlinkuagecapstone/repository/ExamQuestionRepository.java`
- `src/main/java/org/example/signlinkuagecapstone/repository/ExamAnswerRepository.java`

**Service:**
- `src/main/java/org/example/signlinkuagecapstone/service/ExamService.java`

**Controller:**
- `src/main/java/org/example/signlinkuagecapstone/controller/ExamController.java`

**Data:**
- `src/main/resources/exam_data.sql`

### Step 2: Compile the Project
```bash
cd C:\Users\larneta.oglesby\IdeaProjects\SignLinkuageCapstone
mvn clean compile
```

### Step 3: Load Sample Data (Optional)
Execute the SQL file in your MySQL database:
```sql
-- Copy contents of src/main/resources/exam_data.sql
-- Make sure module with id=1 exists first
```

### Step 4: Start the Application
```bash
mvn spring-boot:run
```

### Step 5: Test the API

#### Option A: Using cURL

**Create an Exam:**
```bash
curl -X POST http://localhost:8081/api/exams \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d @exam_payload.json
```

**Get an Exam:**
```bash
curl -X GET http://localhost:8081/api/exams/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Submit Exam:**
```bash
curl -X POST http://localhost:8081/api/exams/submit \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d @submit_payload.json
```

#### Option B: Using Postman

1. **Create Collection**: "SignLinkuage Exams"
2. **Set Base URL**: `http://localhost:8081/api/exams`
3. **Add Authorization**:
   - Type: Bearer Token
   - Token: Your JWT token from /api/auth/login
4. **Create Requests**:
   - POST /
   - GET /{id}
   - POST /submit
   - etc.

#### Option C: Using IntelliJ Built-in HTTP Client

Create `requests.http`:
```http
### Create Exam
POST http://localhost:8081/api/exams
Authorization: Bearer YOUR_TOKEN
Content-Type: application/json

{
  "title": "Test Exam",
  "description": "Testing exam endpoint",
  "moduleId": 1,
  "totalQuestions": 2,
  "passingScore": 70,
  "timeLimit": 30,
  "questions": [...]
}

### Get Exam
GET http://localhost:8081/api/exams/1
Authorization: Bearer YOUR_TOKEN
```

## 📋 Example Payloads

### Create Exam Payload
**File: exam_payload.json**
```json
{
  "title": "ASL ABC Mastery Exam",
  "description": "Comprehensive exam covering ASL letters A-Z",
  "moduleId": 1,
  "totalQuestions": 4,
  "passingScore": 70,
  "timeLimit": 15,
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
          "optionText": "B",
          "imageUrl": "/images/asl/letter_b.jpg",
          "isCorrect": false,
          "orderIndex": 2
        },
        {
          "optionText": "C",
          "imageUrl": "/images/asl/letter_c.jpg",
          "isCorrect": true,
          "orderIndex": 3
        },
        {
          "optionText": "D",
          "imageUrl": "/images/asl/letter_d.jpg",
          "isCorrect": false,
          "orderIndex": 4
        }
      ]
    },
    {
      "questionText": "What does the ASL letter 'A' look like?",
      "imageUrl": "/images/asl/question_letter_a.jpg",
      "orderIndex": 2,
      "answers": [
        {
          "optionText": "A",
          "imageUrl": "/images/asl/letter_a.jpg",
          "isCorrect": true,
          "orderIndex": 1
        },
        {
          "optionText": "B",
          "imageUrl": "/images/asl/letter_b.jpg",
          "isCorrect": false,
          "orderIndex": 2
        },
        {
          "optionText": "C",
          "imageUrl": "/images/asl/letter_c.jpg",
          "isCorrect": false,
          "orderIndex": 3
        },
        {
          "optionText": "D",
          "imageUrl": "/images/asl/letter_d.jpg",
          "isCorrect": false,
          "orderIndex": 4
        }
      ]
    }
  ]
}
```

### Submit Exam Payload
**File: submit_payload.json**
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
      "answerId": 1
    }
  ]
}
```

## 🔍 Expected Responses

### Create Exam - 201 Created
```json
{
  "id": 1,
  "title": "ASL ABC Mastery Exam",
  "description": "Comprehensive exam covering ASL letters A-Z",
  "moduleId": 1,
  "totalQuestions": 4,
  "passingScore": 70,
  "timeLimit": 15
}
```

### Get Exam - 200 OK
```json
{
  "id": 1,
  "title": "ASL ABC Mastery Exam",
  "description": "Comprehensive exam covering ASL letters A-Z",
  "moduleId": 1,
  "totalQuestions": 4,
  "passingScore": 70,
  "timeLimit": 15,
  "questions": [
    {
      "id": 1,
      "questionText": "Which image shows the correct ASL sign for the letter 'C'?",
      "imageUrl": "/images/asl/question_letter_c.jpg",
      "orderIndex": 1,
      "answers": [
        {
          "id": 1,
          "optionText": "A",
          "imageUrl": "/images/asl/letter_a.jpg",
          "orderIndex": 1
        },
        {
          "id": 3,
          "optionText": "C",
          "imageUrl": "/images/asl/letter_c.jpg",
          "orderIndex": 3
        }
      ]
    }
  ]
}
```

### Submit Exam - 200 OK
```json
{
  "examId": 1,
  "totalQuestions": 2,
  "correctAnswers": 2,
  "score": 100,
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
    },
    {
      "questionId": 2,
      "questionText": "What does the ASL letter 'A' look like?",
      "imageUrl": "/images/asl/question_letter_a.jpg",
      "selectedAnswerId": 1,
      "selectedAnswerText": "A",
      "selectedAnswerImageUrl": "/images/asl/letter_a.jpg",
      "correctAnswerId": 1,
      "correctAnswerText": "A",
      "correctAnswerImageUrl": "/images/asl/letter_a.jpg",
      "isCorrect": true
    }
  ]
}
```

## ❌ Common Errors & Solutions

### Error: 401 Unauthorized
**Problem**: No JWT token or expired token
**Solution**: Login first and include valid token in Authorization header

### Error: 404 Not Found (Module/Exam)
**Problem**: Module or Exam ID doesn't exist
**Solution**: Verify module exists and use correct exam ID

### Error: 400 Bad Request
**Problem**: Missing required fields or validation failure
**Solution**: Check payload matches DTO requirements

### Error: 500 Internal Server Error
**Problem**: Server error
**Solution**: Check application logs for details

## 📚 Documentation Files

- **EXAM_SYSTEM_DOCUMENTATION.md** - Comprehensive system documentation
- **EXAM_IMPLEMENTATION_SUMMARY.md** - Overview of all created files
- **exam_data.sql** - Sample ASL ABC exam questions

## 🎯 Next Steps

1. ✅ **Verify Compilation**: `mvn clean compile`
2. ✅ **Start Application**: `mvn spring-boot:run`
3. ✅ **Login**: POST /api/auth/login
4. ✅ **Get JWT Token**: From login response
5. ✅ **Create Exam**: POST /api/exams with token
6. ✅ **Take Exam**: GET /api/exams/{id} with token
7. ✅ **Submit Exam**: POST /api/exams/submit with token
8. ✅ **View Results**: Returned in submit response

## 💡 Tips

- Store JWT token in variable for reuse in requests
- Use Postman or Insomnia for easier API testing
- Image URLs need to be valid and accessible
- Ensure all questions have exactly one correct answer
- Total questions in exam should match question list length
- Passing score is percentage (0-100)
- Time limit is in minutes

## 🆘 Need Help?

Refer to these files:
- **EXAM_SYSTEM_DOCUMENTATION.md** - Detailed API documentation
- **exam_data.sql** - Example questions structure
- **ExamController.java** - Source code for endpoints
- **ExamService.java** - Business logic implementation

---

**Quick Reference:**
- Base URL: `http://localhost:8081/api/exams`
- Auth Header: `Authorization: Bearer {token}`
- Content-Type: `application/json`
- Database: MySQL (configured in application.properties)

Happy testing! 🎉
