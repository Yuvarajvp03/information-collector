package com.avirantEnterprises.information_collector.controller.educational;

import com.avirantEnterprises.information_collector.model.educational.Assignment;
import com.avirantEnterprises.information_collector.repository.educational.AssignmentRepository;
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
public class HomeController {

    @Autowired
    private AssignmentRepository assignmentRepository;

    // Home page mapping
    @GetMapping("/")
    public String showHomePage() {
        return "educational/home"; // Ensure this points to your home.html template
    }

    // User Dashboard mapping
    @GetMapping("/userDashboard")
    public String showUserDashboard() {
        return "educational/userDashboard"; // Points to userDashboard.html
    }

    // Assignment submission page mapping
    @GetMapping("/assignment-submission")
    public String showAssignmentSubmissionPage() {
        return "educational/assignmentSubmission"; // Points to assignmentSubmission.html
    }

    // View PDF file
    @GetMapping("/view-pdf/{fileName}")
    public ResponseEntity<Resource> viewPdf(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get("uploads").resolve(fileName).normalize();
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

    // Delete assignment
    @PostMapping("/delete-assignment")
    public String deleteAssignment(@RequestParam("id") Long id) {
        assignmentRepository.deleteById(id);
        return "redirect:/view-assignments"; // Redirect to assignments view after deletion
    }

    // Submit assignment
    @PostMapping("/submit-assignment")
    public String submitAssignment(
            @RequestParam("studentName") String studentName,
            @RequestParam("studentId") String studentId,
            @RequestParam("courseName") String courseName,
            @RequestParam("assignmentFile") MultipartFile assignmentFile) {

        Assignment assignment = new Assignment();
        assignment.setStudentName(studentName);
        assignment.setStudentId(studentId);
        assignment.setCourseName(courseName);

        if (!assignmentFile.isEmpty()) {
            String fileName = assignmentFile.getOriginalFilename();
            assignment.setAssignmentFileName(fileName);

            try {
                // Save the file
                Path uploadDir = Paths.get("uploads");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }
                Path filePath = uploadDir.resolve(fileName);
                assignmentFile.transferTo(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        assignmentRepository.save(assignment);
        return "redirect:/view-assignments"; // Redirect after saving the assignment
    }

    // View assignments    @GetMapping("/view-assignments")
    public String viewAssignments(Model model) {
        model.addAttribute("assignments", assignmentRepository.findAll());
        return "educational/viewAssignments"; // Maps to viewAssignments.html
    }
}