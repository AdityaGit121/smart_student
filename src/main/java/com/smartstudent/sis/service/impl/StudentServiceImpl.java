package com.smartstudent.sis.service.impl;

import com.smartstudent.sis.dto.StudentRequestDto;
import com.smartstudent.sis.dto.StudentResponseDto;
import com.smartstudent.sis.dto.StudentResultDto;
import com.smartstudent.sis.entity.Student;
import com.smartstudent.sis.entity.StudentResult;
import com.smartstudent.sis.exception.StudentAlreadyExistsException;
import com.smartstudent.sis.exception.StudentNotFoundException;
import com.smartstudent.sis.repository.StudentRepository;
import com.smartstudent.sis.repository.StudentResultRepository;
import com.smartstudent.sis.service.NotificationService;
import com.smartstudent.sis.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of StudentService.
 * 
 * Demonstrates:
 * 1. DEPENDENCY INJECTION:
 *    - Constructor Injection: Used for mandatory dependencies (StudentRepository, StudentResultRepository).
 *      Allows final fields, guarantees immutability, and prevents NullPointerExceptions at runtime.
 *    - Setter Injection: Used for optional/pluggable dependencies (NotificationService) via @Autowired setter.
 * 
 * 2. TRANSACTION MANAGEMENT:
 *    - @Transactional ensures all database operations within the method run in a single atomic transaction.
 *    - If any runtime exception is thrown, changes are rolled back automatically (ACID compliance).
 *    - readOnly = true optimizes read operations by disabling Hibernate dirty checking.
 */
@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    // =========================================================================
    // 1. MANDATORY DEPENDENCIES (Constructor Injection)
    // =========================================================================
    private final StudentRepository studentRepository;
    private final StudentResultRepository studentResultRepository;

    /**
     * CONSTRUCTOR INJECTION (Recommended Spring Best Practice)
     * Spring automatically injects the bean instances when instantiating StudentServiceImpl.
     * Note: In Spring 4.3+, @Autowired is optional on a single constructor.
     */
    public StudentServiceImpl(StudentRepository studentRepository,
                              StudentResultRepository studentResultRepository) {
        this.studentRepository = studentRepository;
        this.studentResultRepository = studentResultRepository;
        logger.info("[DEPENDENCY INJECTION] StudentServiceImpl instantiated with Constructor Injection for Repositories.");
    }

    // =========================================================================
    // 2. OPTIONAL DEPENDENCY (Setter Injection)
    // =========================================================================
    private NotificationService notificationService;

    /**
     * SETTER INJECTION
     * Explicitly demonstrates setter-based dependency injection for college viva/review.
     * Useful for optional dependencies, third-party pluggable services, or circular dependency resolution.
     */
    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
        logger.info("[DEPENDENCY INJECTION] NotificationService injected via Setter Injection.");
    }

    // =========================================================================
    // BUSINESS METHODS
    // =========================================================================

    /**
     * Create a new student with transaction demarcation.
     * Transaction guarantees that if sending notification fails or an error occurs,
     * the transaction can participate in rollback semantics.
     */
    @Override
    @Transactional
    public StudentResponseDto createStudent(StudentRequestDto requestDto) {
        // Check for duplicate email constraint
        if (studentRepository.existsByEmail(requestDto.getEmail())) {
            throw new StudentAlreadyExistsException("A student with email '" + requestDto.getEmail() + "' already exists.");
        }

        Student student = mapToEntity(requestDto);
        Student savedStudent = studentRepository.save(student);

        // Notify student via injected notification service (Setter Injection in action)
        if (notificationService != null) {
            notificationService.sendStudentNotification(
                    savedStudent.getEmail(),
                    "Enrollment Confirmation",
                    "Welcome " + savedStudent.getName() + "! You have been successfully registered with ID: " + savedStudent.getId()
            );
        }

        return mapToDto(savedStudent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return mapToDto(student);
    }

    @Override
    @Transactional
    public StudentResponseDto updateStudent(Long id, StudentRequestDto requestDto) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        // Ensure email uniqueness excluding the current student
        if (studentRepository.existsByEmailAndIdNot(requestDto.getEmail(), id)) {
            throw new StudentAlreadyExistsException("Email '" + requestDto.getEmail() + "' is already in use by another student.");
        }

        // Update properties
        existingStudent.setName(requestDto.getName());
        existingStudent.setEmail(requestDto.getEmail());
        existingStudent.setPhone(requestDto.getPhone());
        existingStudent.setDepartment(requestDto.getDepartment());
        existingStudent.setYear(requestDto.getYear());
        existingStudent.setDateOfBirth(requestDto.getDateOfBirth());

        Student updatedStudent = studentRepository.save(existingStudent);

        if (notificationService != null) {
            notificationService.sendStudentNotification(
                    updatedStudent.getEmail(),
                    "Profile Updated",
                    "Hello " + updatedStudent.getName() + ", your student profile details have been updated."
            );
        }

        return mapToDto(updatedStudent);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDto> getStudentsByDepartment(String department) {
        return studentRepository.findByDepartmentIgnoreCase(department)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResultDto> getStudentResults(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(studentId);
        }
        return studentResultRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapResultToDto)
                .collect(Collectors.toList());
    }

    // =========================================================================
    // DTO / ENTITY MAPPER HELPERS
    // =========================================================================

    private Student mapToEntity(StudentRequestDto dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setDepartment(dto.getDepartment());
        student.setYear(dto.getYear());
        student.setDateOfBirth(dto.getDateOfBirth());
        return student;
    }

    private StudentResponseDto mapToDto(Student student) {
        List<StudentResultDto> resultDtos = student.getResults() != null
                ? student.getResults().stream().map(this::mapResultToDto).collect(Collectors.toList())
                : List.of();

        return StudentResponseDto.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .department(student.getDepartment())
                .year(student.getYear())
                .dateOfBirth(student.getDateOfBirth())
                .results(resultDtos)
                .build();
    }

    private StudentResultDto mapResultToDto(StudentResult result) {
        return StudentResultDto.builder()
                .id(result.getId())
                .subject(result.getSubject())
                .marks(result.getMarks())
                .grade(result.getGrade())
                .semester(result.getSemester())
                .build();
    }
}
