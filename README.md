# SMART STUDENT INFORMATION SYSTEM (SIS)

A complete, enterprise-grade Java Spring Boot 3.x reference application engineered for academic management. Demonstrates Dependency Injection (Constructor & Setter), REST APIs, Spring Data JPA, Hibernate ORM, MySQL, Spring AOP, Declarative Transaction Management (@Transactional), Jakarta Bean Validation, and Contract-First SOAP Web Services.

---

## 1. TECH STACK

- **Language:** Java 17+ (compatible with Java 21 / 25)
- **Framework:** Spring Boot 3.2.5
- **Modules:**
  - Spring Web (REST API, MVC, Embedded Apache Tomcat)
  - Spring Data JPA (Repository pattern, transaction integration)
  - Spring AOP (Aspect-Oriented Programming, AspectJ Weaver)
  - Spring Web Services (SOAP contract-first architecture)
  - Jakarta Validation (Hibernate Validator)
  - Spring Transaction Management (@Transactional)
- **Persistence & ORM:** Hibernate 6.x, MySQL 8.x
- **Build Tool:** Apache Maven 3.8+
- **Utilities:** Project Lombok, WSDL4J, Jakarta XML Binding (JAXB)
- **Testing:** JUnit 5, Mockito, Postman, SoapUI

---

## 2. PROJECT DIRECTORY ARCHITECTURE

```text
smart-student-information-system/
├── pom.xml                                   # Maven dependencies & build lifecycle
├── database_setup.sql                        # Complete MySQL DDL & DML sample data
├── README.md                                 # Full documentation & viva guide
├── postman/
│   └── Smart_Student_Information_System.postman_collection.json # Exported Postman suite
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/smartstudent/sis/
    │   │       ├── SmartStudentInformationSystemApplication.java # Spring Boot entrypoint
    │   │       ├── aspect/
    │   │       │   └── StudentLoggingAspect.java         # AOP: Profiling, activity & error audit
    │   │       ├── controller/
    │   │       │   └── StudentController.java            # REST CRUD & query endpoints
    │   │       ├── dto/
    │   │       │   ├── ApiResponse.java                  # Generic standard response wrapper
    │   │       │   ├── ErrorResponse.java                # Uniform error payload
    │   │       │   ├── StudentRequestDto.java            # Validated input DTO
    │   │       │   ├── StudentResponseDto.java           # Safe entity-to-client output DTO
    │   │       │   └── StudentResultDto.java             # Academic marksheet DTO
    │   │       ├── entity/
    │   │       │   ├── Student.java                      # JPA Entity: 'students' table
    │   │       │   └── StudentResult.java                # JPA Entity: 'student_results' table
    │   │       ├── exception/
    │   │       │   ├── GlobalExceptionHandler.java       # @RestControllerAdvice central handler
    │   │       │   ├── StudentAlreadyExistsException.java# 409 Conflict exception
    │   │       │   └── StudentNotFoundException.java     # 404 Not Found exception
    │   │       ├── repository/
    │   │       │   ├── StudentRepository.java            # JPA repository for students
    │   │       │   └── StudentResultRepository.java      # JPA repository for exam results
    │   │       ├── service/
    │   │       │   ├── NotificationService.java          # Notification abstraction
    │   │       │   ├── StudentService.java               # Business service interface
    │   │       │   └── impl/
    │   │       │       ├── EmailNotificationServiceImpl.java # Notification implementation
    │   │       │       └── StudentServiceImpl.java       # DI (Constructor+Setter) & @Transactional
    │   │       └── soap/
    │   │           ├── config/
    │   │           │   └── SoapWebServiceConfig.java     # MessageDispatcherServlet & WSDL bean
    │   │           ├── endpoint/
    │   │           │   └── StudentResultEndpoint.java    # SOAP XML request dispatcher
    │   │           └── model/
    │   │               ├── GetStudentResultRequest.java  # JAXB request model
    │   │               ├── GetStudentResultResponse.java # JAXB response model
    │   │               ├── ResultItemWsDto.java          # JAXB course score item
    │   │               └── package-info.java             # XML namespace binding
    │   └── resources/
    │       ├── application.properties                    # DB, server & logging configurations
    │       ├── data.sql                                  # Preloaded records
    │       └── xsd/
    │           └── student-result.xsd                    # Contract-first XML Schema Definition
    └── test/
        └── java/
            └── com/smartstudent/sis/
                └── SmartStudentInformationSystemApplicationTests.java # Mockito unit tests
```

---

## 3. HOW TO RUN THE PROJECT (STEP-BY-STEP)

### Step 1: Install Prerequisites
1. **Java JDK 17+**: Verify with `java -version`.
2. **MySQL Server 8.x**: Verify MySQL service is running on port 3306.
3. **Maven 3.8+**: Verify with `mvn -version` (or use IDE bundled Maven).

### Step 2: Initialize MySQL Database
Open MySQL Workbench, MySQL Shell, or command line:
```bash
mysql -u root -p < database_setup.sql
```
Or execute inside MySQL client:
```sql
CREATE DATABASE student_db;
USE student_db;
-- Paste contents from database_setup.sql
```

### Step 3: Configure Database Credentials
Open `src/main/resources/application.properties` and update your username & password:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### Step 4: Run Application
- **From IDE (IntelliJ IDEA / Eclipse):**
  Right click on `SmartStudentInformationSystemApplication.java` -> **Run As Spring Boot App**.
- **From Terminal (Maven):**
  ```bash
  mvn clean spring-boot:run
  ```

---

## 4. REST API SPECIFICATION

Base URL: `http://localhost:8080/api/students`

| Method | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/students` | Register a new student | `201 CREATED` |
| **GET** | `/api/students` | Fetch all active students | `200 OK` |
| **GET** | `/api/students/{id}` | Fetch student details by ID | `200 OK` / `404 NOT FOUND` |
| **PUT** | `/api/students/{id}` | Update existing student | `200 OK` / `400 BAD REQUEST` |
| **DELETE**| `/api/students/{id}` | Delete student record | `200 OK` / `404 NOT FOUND` |
| **GET** | `/api/students/department/{dept}` | Filter students by department | `200 OK` |
| **GET** | `/api/students/{id}/results` | Fetch academic results for student | `200 OK` |

---

## 5. SOAP WEB SERVICE SPECIFICATION

- **Endpoint URL:** `http://localhost:8080/ws`
- **Dynamic WSDL URL:** `http://localhost:8080/ws/studentResults.wsdl`
- **Target Namespace:** `http://www.smartstudent.com/sis/soap`
- **Operation:** `getStudentResult(studentId)`

### Sample SOAP Request (Postman / SoapUI)
- **HTTP Method:** `POST`
- **URL:** `http://localhost:8080/ws`
- **Headers:** `Content-Type: text/xml;charset=UTF-8`

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:soap="http://www.smartstudent.com/sis/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:getStudentResultRequest>
         <soap:studentId>1</soap:studentId>
      </soap:getStudentResultRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

### Sample SOAP Response
```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Header/>
   <SOAP-ENV:Body>
      <ns2:getStudentResultResponse xmlns:ns2="http://www.smartstudent.com/sis/soap">
         <ns2:studentId>1</ns2:studentId>
         <ns2:studentName>Aarav Sharma</ns2:studentName>
         <ns2:department>Computer Science</ns2:department>
         <ns2:semester>5</ns2:semester>
         <ns2:results>
            <ns2:subject>Data Structures &amp; Algorithms</ns2:subject>
            <ns2:marks>88.5</ns2:marks>
            <ns2:grade>A+</ns2:grade>
         </ns2:results>
         <ns2:results>
            <ns2:subject>Database Management Systems</ns2:subject>
            <ns2:marks>92.0</ns2:marks>
            <ns2:grade>O</ns2:grade>
         </ns2:results>
         <ns2:results>
            <ns2:subject>Computer Networks</ns2:subject>
            <ns2:marks>79.5</ns2:marks>
            <ns2:grade>A</ns2:grade>
         </ns2:results>
         <ns2:results>
            <ns2:subject>Software Engineering</ns2:subject>
            <ns2:marks>85.0</ns2:marks>
            <ns2:grade>A+</ns2:grade>
         </ns2:results>
         <ns2:totalMarks>345.0</ns2:totalMarks>
         <ns2:percentage>86.25</ns2:percentage>
         <ns2:status>PASSED</ns2:status>
      </ns2:getStudentResultResponse>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```
# smart_student
