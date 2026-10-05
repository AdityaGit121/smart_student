package com.smartstudent.sis.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * StudentResult JPA Entity.
 *
 * Demonstrates:
 * - Many-to-One relationship mapping foreign key 'student_id'.
 * - Representation of academic course results per semester.
 */
@Entity
@Table(name = "student_results")
public class StudentResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "subject", nullable = false, length = 100)
    private String subject;

    @Column(name = "marks", nullable = false)
    private Double marks;

    @Column(name = "grade", nullable = false, length = 10)
    private String grade;

    @Column(name = "semester", nullable = false)
    private Integer semester;

    public StudentResult() {
    }

    public StudentResult(Student student, String subject, Double marks, String grade, Integer semester) {
        this.student = student;
        this.subject = subject;
        this.marks = marks;
        this.grade = grade;
        this.semester = semester;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }
}
