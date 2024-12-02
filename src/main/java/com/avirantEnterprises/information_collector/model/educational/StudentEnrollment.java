package com.avirantEnterprises.information_collector.model.educational;

import org.springframework.web.multipart.MultipartFile;

public class StudentEnrollment {
    private String studentName;
    private String email;
    private String course;
    private String message;
    private MultipartFile idDocument;

    // Getters and Setters
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MultipartFile getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(MultipartFile idDocument) {
        this.idDocument = idDocument;
    }
}
