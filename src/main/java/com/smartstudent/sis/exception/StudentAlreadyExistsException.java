package com.smartstudent.sis.exception;

/**
 * Custom runtime exception thrown when attempting to create a Student with an existing unique identifier (e.g. duplicate email).
 */
public class StudentAlreadyExistsException extends RuntimeException {

    public StudentAlreadyExistsException(String message) {
        super(message);
    }
}
