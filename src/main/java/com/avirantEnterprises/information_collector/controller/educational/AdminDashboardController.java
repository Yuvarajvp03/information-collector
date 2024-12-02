package com.avirantEnterprises.information_collector.controller.educational;

import com.avirantEnterprises.information_collector.model.educational.CourseEnrollment;
import com.avirantEnterprises.information_collector.repository.educational.AssignmentRepository;
import com.avirantEnterprises.information_collector.repository.educational.CourseEnrollmentRepository;
import com.avirantEnterprises.information_collector.repository.educational.TrainingFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminDashboardController {

    @Autowired
    private CourseEnrollmentRepository courseEnrollmentRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private TrainingFeedbackRepository trainingFeedbackRepository;

    // Admin Dashboard View
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "educational/adminDashboard"; // Points to the Thymeleaf template "adminDashboard.html"
    }

    // View Enrollments
    @GetMapping("/manage-enrollments")
    public String viewEnrollments(Model model) {
        model.addAttribute("enrollments", courseEnrollmentRepository.findAll());
        return "educational/viewCourseEnrollments"; // Points to the Thymeleaf template "viewEnrollments.html"
    }

    // View Assignments with Submission Statistics
    @GetMapping("/view-assignments")
    public String viewAssignments(Model model) {
        model.addAttribute("assignments", assignmentRepository.findAll());
        return "educational/viewAssignments"; // Points to viewAssignments.html
    }

    // Handle submission statistics calculation
    @PostMapping("/calculate-submissions")
    public String calculateSubmissions(@RequestParam("totalStudents") Long totalStudents, Model model) {
        long submittedCount = assignmentRepository.count();
        long notSubmittedCount = totalStudents - submittedCount;

        // Debugging logs
        System.out.println("Total Students: " + totalStudents);
        System.out.println("Submitted Count: " + submittedCount);
        System.out.println("Not Submitted Count: " + notSubmittedCount);

        // Pass values to the model
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("submittedCount", submittedCount);
        model.addAttribute("notSubmittedCount", notSubmittedCount);
        model.addAttribute("assignments", assignmentRepository.findAll());

        return "educational/viewAssignments";
    }


    @GetMapping("/analyze-feedback")
    public String viewFeedbacks(Model model) {
        model.addAttribute("feedbacks", trainingFeedbackRepository.findAll());
        return "educational/viewFeedbacks"; // Points to viewFeedbacks.html
    }
}