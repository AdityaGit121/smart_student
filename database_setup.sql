-- =====================================================================
-- SMART STUDENT INFORMATION SYSTEM - DATABASE SETUP SCRIPT
-- Database Engine: MySQL 8.x
-- =====================================================================

-- Step 1: Create Database if not exists
CREATE DATABASE IF NOT EXISTS student_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE student_db;

-- Step 2: Drop existing tables if re-initializing
DROP TABLE IF EXISTS student_results;
DROP TABLE IF EXISTS students;

-- Step 3: Create 'students' Table
-- Maps to: com.smartstudent.sis.entity.Student
CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    department VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    date_of_birth DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Step 4: Create 'student_results' Table
-- Maps to: com.smartstudent.sis.entity.StudentResult
CREATE TABLE student_results (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    subject VARCHAR(100) NOT NULL,
    marks DOUBLE NOT NULL,
    grade VARCHAR(10) NOT NULL,
    semester INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_student_results_student
        FOREIGN KEY (student_id) REFERENCES students(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Step 5: Insert Sample Student Data
INSERT INTO students (id, name, email, phone, department, year, date_of_birth) VALUES
(1, 'Aarav Sharma', 'aarav.sharma@university.edu', '9876543210', 'Computer Science', 3, '2003-05-14'),
(2, 'Diya Patel', 'diya.patel@university.edu', '9812345678', 'Information Technology', 2, '2004-09-22'),
(3, 'Rohan Verma', 'rohan.verma@university.edu', '9765432109', 'Computer Science', 4, '2002-12-05'),
(4, 'Ananya Iyer', 'ananya.iyer@university.edu', '9654321098', 'Electronics and Communication', 1, '2005-03-18'),
(5, 'Vikram Malhotra', 'vikram.malhotra@university.edu', '9543210987', 'Mechanical Engineering', 3, '2003-07-29');

-- Step 6: Insert Sample Student Results Data
INSERT INTO student_results (student_id, subject, marks, grade, semester) VALUES
-- Results for Aarav Sharma (ID: 1)
(1, 'Data Structures & Algorithms', 88.5, 'A+', 5),
(1, 'Database Management Systems', 92.0, 'O', 5),
(1, 'Computer Networks', 79.5, 'A', 5),
(1, 'Software Engineering', 85.0, 'A+', 5),

-- Results for Diya Patel (ID: 2)
(2, 'Object Oriented Programming with Java', 95.0, 'O', 3),
(2, 'Discrete Mathematics', 82.0, 'A+', 3),
(2, 'Computer Organization & Architecture', 76.0, 'A', 3),

-- Results for Rohan Verma (ID: 3)
(3, 'Cloud Computing', 90.0, 'O', 7),
(3, 'Machine Learning', 87.5, 'A+', 7),
(3, 'Distributed Systems', 84.0, 'A+', 7),

-- Results for Ananya Iyer (ID: 4)
(4, 'Engineering Physics', 74.0, 'B+', 1),
(4, 'Basic Electrical Engineering', 80.0, 'A', 1),
(4, 'Mathematics I', 91.0, 'O', 1),

-- Results for Vikram Malhotra (ID: 5)
(5, 'Thermodynamics', 78.0, 'A', 5),
(5, 'Fluid Mechanics', 81.5, 'A+', 5),
(5, 'Kinematics of Machinery', 70.0, 'B+', 5);

-- Verify records
SELECT * FROM students;
SELECT * FROM student_results;
