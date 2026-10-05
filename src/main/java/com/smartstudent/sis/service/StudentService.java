package com.smartstudent.sis.service;

import com.smartstudent.sis.dto.StudentRequestDto;
import com.smartstudent.sis.dto.StudentResponseDto;
import com.smartstudent.sis.dto.StudentResultDto;

import java.util.List;

/**
 * Business Service interface defining operations for Student management.
 */
public interface StudentService {

    StudentResponseDto createStudent(StudentRequestDto requestDto);

    List<StudentResponseDto> getAllStudents();

    StudentResponseDto getStudentById(Long id);

    StudentResponseDto updateStudent(Long id, StudentRequestDto requestDto);

    void deleteStudent(Long id);

    List<StudentResponseDto> getStudentsByDepartment(String department);

    List<StudentResultDto> getStudentResults(Long studentId);
}
