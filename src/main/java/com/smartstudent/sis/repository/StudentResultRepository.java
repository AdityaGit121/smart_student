package com.smartstudent.sis.repository;

import com.smartstudent.sis.entity.StudentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Spring Data JPA Repository for StudentResult entity.
 * 
 * Demonstrates:
 * - Querying child records by parent foreign key (findByStudentId).
 * - Multi-criteria derived queries (findByStudentIdAndSemester).
 */
public interface StudentResultRepository extends JpaRepository<StudentResult, Long> {

    /**
     * Retrieve all examination results for a specific student ID.
     */
    List<StudentResult> findByStudentId(Long studentId);

    /**
     * Retrieve exam results for a specific student and semester.
     */
    List<StudentResult> findByStudentIdAndSemester(Long studentId, Integer semester);
}
