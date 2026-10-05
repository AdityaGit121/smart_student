package com.smartstudent.sis.soap.endpoint;

import com.smartstudent.sis.entity.Student;
import com.smartstudent.sis.entity.StudentResult;
import com.smartstudent.sis.exception.StudentNotFoundException;
import com.smartstudent.sis.repository.StudentRepository;
import com.smartstudent.sis.repository.StudentResultRepository;
import com.smartstudent.sis.soap.model.GetStudentResultRequest;
import com.smartstudent.sis.soap.model.GetStudentResultResponse;
import com.smartstudent.sis.soap.model.ResultItemWsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

/**
 * Spring Web Services (SOAP) Endpoint for querying student academic scorecards.
 * 
 * Demonstrates:
 * - Contract-First SOAP web service architecture.
 * - @Endpoint: Marks this component as an XML message handling endpoint.
 * - @PayloadRoot: Dispatches incoming XML payloads matching the namespace and localPart.
 * - @RequestPayload / @ResponsePayload: Unmarshals request XML into JAXB Java object and marshals response back to XML.
 */
@Endpoint
public class StudentResultEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(StudentResultEndpoint.class);
    public static final String NAMESPACE_URI = "http://www.smartstudent.com/sis/soap";

    private final StudentRepository studentRepository;
    private final StudentResultRepository studentResultRepository;

    // Constructor Injection
    public StudentResultEndpoint(StudentRepository studentRepository,
                                 StudentResultRepository studentResultRepository) {
        this.studentRepository = studentRepository;
        this.studentResultRepository = studentResultRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStudentResultRequest")
    @ResponsePayload
    public GetStudentResultResponse getStudentResult(@RequestPayload GetStudentResultRequest request) {
        long studentId = request.getStudentId();
        logger.info("[SOAP SERVICE] Received getStudentResultRequest for student ID: {}", studentId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("SOAP Error: Student not found with ID: " + studentId));

        List<StudentResult> results = studentResultRepository.findByStudentId(studentId);

        GetStudentResultResponse response = new GetStudentResultResponse();
        response.setStudentId(student.getId());
        response.setStudentName(student.getName());
        response.setDepartment(student.getDepartment());

        double totalMarks = 0.0;
        int semester = 1;

        for (StudentResult result : results) {
            semester = result.getSemester();
            totalMarks += result.getMarks();
            ResultItemWsDto item = new ResultItemWsDto(
                    result.getSubject(),
                    result.getMarks(),
                    result.getGrade()
            );
            response.getResults().add(item);
        }

        response.setSemester(semester);
        response.setTotalMarks(totalMarks);

        // Compute percentage and pass/fail status
        int count = results.size();
        double percentage = count > 0 ? (totalMarks / (count * 100.0)) * 100.0 : 0.0;
        response.setPercentage(Math.round(percentage * 100.0) / 100.0);
        response.setStatus(percentage >= 40.0 ? "PASSED" : "FAILED / INCOMPLETE");

        logger.info("[SOAP SERVICE] Successfully generated result response for Student ID: {}, Total Marks: {}, Percentage: {}%",
                studentId, totalMarks, response.getPercentage());

        return response;
    }
}
