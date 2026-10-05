package com.smartstudent.sis.soap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;

/**
 * JAXB Response Model for getStudentResult SOAP operation.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "studentId",
        "studentName",
        "department",
        "semester",
        "results",
        "totalMarks",
        "percentage",
        "status"
})
@XmlRootElement(name = "getStudentResultResponse", namespace = "http://www.smartstudent.com/sis/soap")
public class GetStudentResultResponse {

    @XmlElement(namespace = "http://www.smartstudent.com/sis/soap")
    protected long studentId;

    @XmlElement(namespace = "http://www.smartstudent.com/sis/soap", required = true)
    protected String studentName;

    @XmlElement(namespace = "http://www.smartstudent.com/sis/soap", required = true)
    protected String department;

    @XmlElement(namespace = "http://www.smartstudent.com/sis/soap")
    protected int semester;

    @XmlElement(namespace = "http://www.smartstudent.com/sis/soap")
    protected List<ResultItemWsDto> results;

    @XmlElement(namespace = "http://www.smartstudent.com/sis/soap")
    protected double totalMarks;

    @XmlElement(namespace = "http://www.smartstudent.com/sis/soap")
    protected double percentage;

    @XmlElement(namespace = "http://www.smartstudent.com/sis/soap", required = true)
    protected String status;

    public GetStudentResultResponse() {
        this.results = new ArrayList<>();
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long value) {
        this.studentId = value;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String value) {
        this.studentName = value;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String value) {
        this.department = value;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int value) {
        this.semester = value;
    }

    public List<ResultItemWsDto> getResults() {
        if (results == null) {
            results = new ArrayList<>();
        }
        return this.results;
    }

    public void setResults(List<ResultItemWsDto> results) {
        this.results = results;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double value) {
        this.totalMarks = value;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double value) {
        this.percentage = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = value;
    }
}
