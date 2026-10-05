package com.smartstudent.sis.soap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * JAXB Model for individual subject result item in SOAP response.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resultItem", namespace = "http://www.smartstudent.com/sis/soap", propOrder = {
        "subject",
        "marks",
        "grade"
})
public class ResultItemWsDto {

    @XmlElement(namespace = "http://www.smartstudent.com/sis/soap", required = true)
    protected String subject;

    @XmlElement(namespace = "http://www.smartstudent.com/sis/soap")
    protected double marks;

    @XmlElement(namespace = "http://www.smartstudent.com/sis/soap", required = true)
    protected String grade;

    public ResultItemWsDto() {
    }

    public ResultItemWsDto(String subject, double marks, String grade) {
        this.subject = subject;
        this.marks = marks;
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
