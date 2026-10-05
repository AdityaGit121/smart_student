package com.smartstudent.sis;

import com.smartstudent.sis.dto.StudentRequestDto;
import com.smartstudent.sis.dto.StudentResponseDto;
import com.smartstudent.sis.entity.Student;
import com.smartstudent.sis.exception.StudentNotFoundException;
import com.smartstudent.sis.repository.StudentRepository;
import com.smartstudent.sis.repository.StudentResultRepository;
import com.smartstudent.sis.service.NotificationService;
import com.smartstudent.sis.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for StudentServiceImpl demonstrating Mockito mocking
 * and business logic verification without requiring an active MySQL connection.
 */
@ExtendWith(MockitoExtension.class)
public class SmartStudentInformationSystemApplicationTests {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentResultRepository studentResultRepository;

    @Mock
    private NotificationService notificationService;

    private StudentServiceImpl studentService;

    private void initializeStudentService() {
        // Demonstrate Constructor Injection in unit testing
        studentService = new StudentServiceImpl(studentRepository, studentResultRepository);
        // Demonstrate Setter Injection in unit testing
        studentService.setNotificationService(notificationService);
    }

    @Test
    @DisplayName("Should successfully create a student and dispatch notification")
    void testCreateStudentSuccess() {
        initializeStudentService();

        StudentRequestDto requestDto = StudentRequestDto.builder()
                .name("Aarav Sharma")
                .email("aarav@university.edu")
                .phone("9876543210")
                .department("Computer Science")
                .year(3)
                .dateOfBirth(LocalDate.of(2003, 5, 14))
                .build();

        Student savedStudent = new Student(
                "Aarav Sharma",
                "aarav@university.edu",
                "9876543210",
                "Computer Science",
                3,
                LocalDate.of(2003, 5, 14)
        );
        savedStudent.setId(1L);

        when(studentRepository.existsByEmail("aarav@university.edu")).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        StudentResponseDto response = studentService.createStudent(requestDto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Aarav Sharma", response.getName());
        verify(studentRepository, times(1)).save(any(Student.class));
        verify(notificationService, times(1)).sendStudentNotification(any(), any(), any());
    }

    @Test
    @DisplayName("Should throw StudentNotFoundException when fetching invalid ID")
    void testGetStudentByIdNotFound() {
        initializeStudentService();
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(StudentNotFoundException.class,
                () -> studentService.getStudentById(999L));
        assertNotNull(throwable);
    }
}
