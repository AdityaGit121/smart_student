package com.smartstudent.sis.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for Student API responses.
 *
 * Demonstrates:
 * - Hiding database-specific structures and sensitive relationships.
 * - Providing clean, tailored JSON responses to REST consumers.
 */
public class StudentResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String department;
    private Integer year;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private List<StudentResultDto> results = new ArrayList<>();

    public StudentResponseDto() {
    }

    public StudentResponseDto(Long id, String name, String email, String phone, String department, Integer year,
                             LocalDate dateOfBirth, List<StudentResultDto> results) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.year = year;
        this.dateOfBirth = dateOfBirth;
        this.results = results == null ? new ArrayList<>() : results;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private String phone;
        private String department;
        private Integer year;
        private LocalDate dateOfBirth;
        private List<StudentResultDto> results = new ArrayList<>();

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder department(String department) {
            this.department = department;
            return this;
        }

        public Builder year(Integer year) {
            this.year = year;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder results(List<StudentResultDto> results) {
            this.results = results == null ? new ArrayList<>() : results;
            return this;
        }

        public StudentResponseDto build() {
            return new StudentResponseDto(id, name, email, phone, department, year, dateOfBirth, results);
        }
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

    public List<StudentResultDto> getResults() {
        return results;
    }

    public void setResults(List<StudentResultDto> results) {
        this.results = results == null ? new ArrayList<>() : results;
    }
}
