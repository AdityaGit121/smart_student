package com.smartstudent.sis.soap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * JAXB Request Model for getStudentResult SOAP operation.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "studentId"
})
@XmlRootElement(name = "getStudentResultRequest", namespace = "http://www.smartstudent.com/sis/soap")
public class GetStudentResultRequest {

    @XmlElement(namespace = "http://www.smartstudent.com/sis/soap", required = true)
    protected long studentId;

    public GetStudentResultRequest() {
    }

    public GetStudentResultRequest(long studentId) {
        this.studentId = studentId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long value) {
        this.studentId = value;
    }
}
