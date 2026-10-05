package com.smartstudent.sis.dto;

/**
 * Data Transfer Object for Student Academic Results.
 */
public class StudentResultDto {
    private Long id;
    private String subject;
    private Double marks;
    private String grade;
    private Integer semester;

    public StudentResultDto() {
    }

    public StudentResultDto(Long id, String subject, Double marks, String grade, Integer semester) {
        this.id = id;
        this.subject = subject;
        this.marks = marks;
        this.grade = grade;
        this.semester = semester;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String subject;
        private Double marks;
        private String grade;
        private Integer semester;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder marks(Double marks) {
            this.marks = marks;
            return this;
        }

        public Builder grade(String grade) {
            this.grade = grade;
            return this;
        }

        public Builder semester(Integer semester) {
            this.semester = semester;
            return this;
        }

        public StudentResultDto build() {
            return new StudentResultDto(id, subject, marks, grade, semester);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
