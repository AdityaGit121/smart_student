-- Sample Data Initialization for Smart Student Information System
INSERT IGNORE INTO students (id, name, email, phone, department, year, date_of_birth) VALUES
(1, 'Aarav Sharma', 'aarav.sharma@university.edu', '9876543210', 'Computer Science', 3, '2003-05-14'),
(2, 'Diya Patel', 'diya.patel@university.edu', '9812345678', 'Information Technology', 2, '2004-09-22'),
(3, 'Rohan Verma', 'rohan.verma@university.edu', '9765432109', 'Computer Science', 4, '2002-12-05'),
(4, 'Ananya Iyer', 'ananya.iyer@university.edu', '9654321098', 'Electronics and Communication', 1, '2005-03-18'),
(5, 'Vikram Malhotra', 'vikram.malhotra@university.edu', '9543210987', 'Mechanical Engineering', 3, '2003-07-29');

INSERT IGNORE INTO student_results (id, student_id, subject, marks, grade, semester) VALUES
(1, 1, 'Data Structures & Algorithms', 88.5, 'A+', 5),
(2, 1, 'Database Management Systems', 92.0, 'O', 5),
(3, 1, 'Computer Networks', 79.5, 'A', 5),
(4, 1, 'Software Engineering', 85.0, 'A+', 5),
(5, 2, 'Object Oriented Programming with Java', 95.0, 'O', 3),
(6, 2, 'Discrete Mathematics', 82.0, 'A+', 3),
(7, 2, 'Computer Organization & Architecture', 76.0, 'A', 3),
(8, 3, 'Cloud Computing', 90.0, 'O', 7),
(9, 3, 'Machine Learning', 87.5, 'A+', 7),
(10, 3, 'Distributed Systems', 84.0, 'A+', 7),
(11, 4, 'Engineering Physics', 74.0, 'B+', 1),
(12, 4, 'Basic Electrical Engineering', 80.0, 'A', 1),
(13, 4, 'Mathematics I', 91.0, 'O', 1),
(14, 5, 'Thermodynamics', 78.0, 'A', 5),
(15, 5, 'Fluid Mechanics', 81.5, 'A+', 5),
(16, 5, 'Kinematics of Machinery', 70.0, 'B+', 5);
