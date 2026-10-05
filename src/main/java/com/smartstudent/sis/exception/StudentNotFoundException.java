package com.smartstudent.sis.exception;

/**
 * Custom runtime exception thrown when a requested Student is not found in the database.
 */
public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String message) {
        super(message);
    }

    public StudentNotFoundException(Long id) {
        super("Student not found with ID: " + id);
    }
}
