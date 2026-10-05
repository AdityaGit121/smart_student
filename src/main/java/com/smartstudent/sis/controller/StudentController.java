package com.smartstudent.sis.controller;

import com.smartstudent.sis.dto.ApiResponse;
import com.smartstudent.sis.dto.StudentRequestDto;
import com.smartstudent.sis.dto.StudentResponseDto;
import com.smartstudent.sis.dto.StudentResultDto;
import com.smartstudent.sis.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for Student operations.
 * 
 * Demonstrates:
 * - @RestController: Marks this class as a Spring RESTful Web Controller (combines @Controller and @ResponseBody).
 * - @RequestMapping("/api/students"): Base URI path mapping for all student endpoints.
 * - Constructor Injection: Injects StudentService cleanly and immutably.
 * - @Valid: Triggers Jakarta Bean Validation on the incoming StudentRequestDto.
 * - Standard HTTP Methods and Status Codes:
 *     POST   -> 201 CREATED
 *     GET    -> 200 OK
 *     PUT    -> 200 OK
 *     DELETE -> 200 OK / 204 NO_CONTENT
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    // Constructor Injection for Service Layer dependency
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * POST /api/students : Register a new student.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponseDto>> createStudent(@Valid @RequestBody StudentRequestDto requestDto) {
        StudentResponseDto createdStudent = studentService.createStudent(requestDto);
        return new ResponseEntity<>(
                ApiResponse.created("Student registered successfully", createdStudent),
                HttpStatus.CREATED
        );
    }

    /**
     * GET /api/students : Fetch all registered students.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponseDto>>> getAllStudents() {
        List<StudentResponseDto> students = studentService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.success("Fetched all students successfully", students));
    }

    /**
     * GET /api/students/{id} : Fetch a specific student by primary key ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> getStudentById(@PathVariable Long id) {
        StudentResponseDto student = studentService.getStudentById(id);
        return ResponseEntity.ok(ApiResponse.success("Student fetched successfully", student));
    }

    /**
     * PUT /api/students/{id} : Update an existing student's details.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDto requestDto) {
        StudentResponseDto updatedStudent = studentService.updateStudent(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success("Student updated successfully", updatedStudent));
    }

    /**
     * DELETE /api/students/{id} : Delete a student by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success("Student with ID " + id + " deleted successfully", null));
    }

    /**
     * GET /api/students/department/{department} : Search students by department.
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<ApiResponse<List<StudentResponseDto>>> getStudentsByDepartment(@PathVariable String department) {
        List<StudentResponseDto> students = studentService.getStudentsByDepartment(department);
        return ResponseEntity.ok(ApiResponse.success("Students retrieved for department: " + department, students));
    }

    /**
     * GET /api/students/{id}/results : Get all semester exam results for a student.
     */
    @GetMapping("/{id}/results")
    public ResponseEntity<ApiResponse<List<StudentResultDto>>> getStudentResults(@PathVariable Long id) {
        List<StudentResultDto> results = studentService.getStudentResults(id);
        return ResponseEntity.ok(ApiResponse.success("Results retrieved for student ID: " + id, results));
    }
}
