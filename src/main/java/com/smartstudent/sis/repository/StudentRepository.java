package com.smartstudent.sis.repository;

import com.smartstudent.sis.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Student entity.
 * 
 * Demonstrates:
 * - Spring Data JPA interface-driven data access (no boilerplate SQL/JDBC code).
 * - Automatic query generation from method names (e.g., findByDepartmentIgnoreCase).
 * - Built-in CRUD methods inherited from JpaRepository (save, findById, findAll, deleteById, count).
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Check if a student with the given email already exists.
     */
    boolean existsByEmail(String email);

    /**
     * Check if an email belongs to any other student (used when updating a student).
     */
    boolean existsByEmailAndIdNot(String email, Long id);

    /**
     * Find all students enrolled in a specific academic department (case-insensitive).
     */
    List<Student> findByDepartmentIgnoreCase(String department);

    /**
     * Retrieve student by unique email address.
     */
    Optional<Student> findByEmail(String email);
}
