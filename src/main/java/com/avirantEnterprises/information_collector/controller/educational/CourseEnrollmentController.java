package com.avirantEnterprises.information_collector.controller.educational;

import com.avirantEnterprises.information_collector.model.educational.CourseEnrollment;
import com.avirantEnterprises.information_collector.repository.educational.CourseEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class CourseEnrollmentController {

    @Autowired
    private CourseEnrollmentRepository courseEnrollmentRepository;

    // Show the course enrollment page
    @GetMapping("/course-enrollment")
    public String showCourseEnrollmentPage() {
        return "educational/courseEnrollment";
    }

    // Submit the course enrollment form
    @PostMapping("/submit-enrollment")
    public String submitEnrollment(
            @RequestParam("studentName") String studentName,
            @RequestParam("email") String email,
            @RequestParam("contactNumber") String contactNumber,
            @RequestParam("courseName") String courseName,
            @RequestParam("idDocument") MultipartFile idDocument) {

        System.out.println("Student Name: " + studentName);
        System.out.println("Email: " + email);
        System.out.println("Contact Number: " + contactNumber);
        System.out.println("Course Name: " + courseName);
        System.out.println("File Name: " + idDocument.getOriginalFilename());

        CourseEnrollment enrollment = new CourseEnrollment();
        enrollment.setStudentName(studentName);
        enrollment.setEmail(email);
        enrollment.setContactNumber(contactNumber);
        enrollment.setCourseName(courseName);

        if (!idDocument.isEmpty()) {
            String fileName = idDocument.getOriginalFilename();
            enrollment.setIdDocumentName(fileName);

            try {
                // Save the uploaded ID document
                Path uploadDir = Paths.get("enrollment_uploads");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }
                Path filePath = uploadDir.resolve(fileName);
                idDocument.transferTo(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        courseEnrollmentRepository.save(enrollment);
        return "redirect:/view-enrollments";
    }

    // View all course enrollments
    @GetMapping("/view-enrollments")
    public String viewEnrollments(Model model) {
        model.addAttribute("enrollments", courseEnrollmentRepository.findAll());
        return "educational/viewCourseEnrollments"; // Maps to viewCourseEnrollments.html
    }

    // View ID Document
    @GetMapping("/view-id/{fileName}")
    public ResponseEntity<Resource> viewIdDocument(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get("enrollment_uploads").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Delete a course enrollment
    @PostMapping("/delete-enrollment")
    public String deleteEnrollment(@RequestParam("id") Long id) {
        courseEnrollmentRepository.deleteById(id);
        return "redirect:/view-enrollments";
    }
}
