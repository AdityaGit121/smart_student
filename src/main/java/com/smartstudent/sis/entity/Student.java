package com.smartstudent.sis.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Student JPA Entity.
 *
 * Demonstrates:
 * - Object-Relational Mapping (ORM) between a Java class and the MySQL 'students' table.
 * - @Entity & @Table: Designates this class as a persistent database entity.
 * - Primary Key generation using MySQL AUTO_INCREMENT (@GeneratedValue IDENTITY).
 * - One-to-Many bidirectional association with StudentResult.
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "department", nullable = false, length = 50)
    private String department;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    /**
     * Bidirectional 1-to-N relationship: One student has multiple semester exam results.
     * cascade = CascadeType.ALL ensures child results are persisted/deleted along with the student.
     * orphanRemoval = true removes any result detached from this student collection.
     */
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StudentResult> results = new ArrayList<>();

    public Student() {
    }

    // Custom constructor for convenient instantiation
    public Student(String name, String email, String phone, String department, Integer year, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.year = year;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<StudentResult> getResults() {
        return results;
    }

    public void setResults(List<StudentResult> results) {
        this.results = results == null ? new ArrayList<>() : results;
    }

    // Helper methods to maintain bidirectional synchronization
    public void addResult(StudentResult result) {
        results.add(result);
        result.setStudent(this);
    }

    public void removeResult(StudentResult result) {
        results.remove(result);
        result.setStudent(null);
    }
}
