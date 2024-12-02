package com.avirantEnterprises.information_collector.controller.educational;

import com.avirantEnterprises.information_collector.model.educational.TrainingFeedback;
import com.avirantEnterprises.information_collector.repository.educational.TrainingFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class TrainingFeedbackController {

    @Autowired
    private TrainingFeedbackRepository trainingFeedbackRepository;

    // Show the feedback form page
    @GetMapping("/training-feedback")
    public String showFeedbackFormPage() {
        return "educational/feadback"; // Maps to trainingFeedback.html
    }



    // Submit feedback form
    @PostMapping("/submit-feedback")
    public String submitFeedback(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("session") String session,
            @RequestParam("feedback") String feedbackText,
            @RequestParam("report") MultipartFile reportFile) {

        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Session: " + session);
        System.out.println("Feedback: " + feedbackText);
        System.out.println("File Name: " + reportFile.getOriginalFilename());

        TrainingFeedback feedback = new TrainingFeedback();
        feedback.setName(name);
        feedback.setEmail(email);
        feedback.setSession(session);
        feedback.setFeedbackText(feedbackText);

        if (!reportFile.isEmpty()) {
            String fileName = reportFile.getOriginalFilename();
            feedback.setReportFileName(fileName);

            try {
                // Save the uploaded report file
                Path uploadDir = Paths.get("feedback_uploads");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }
                Path filePath = uploadDir.resolve(fileName);
                reportFile.transferTo(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        trainingFeedbackRepository.save(feedback);
        return "redirect:/view-feedbacks";
    }

    // View all feedback submissions
    @GetMapping("/view-feedbacks")
    public String viewFeedbacks(Model model) {
        model.addAttribute("feedbacks", trainingFeedbackRepository.findAll());
        return "educational/viewFeedbacks"; // This will map to viewFeedbacks.html
    }


    // Download report file
    @GetMapping("/download-report/{fileName}")
    public ResponseEntity<Resource> downloadReportFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get("feedback_uploads").resolve(fileName).normalize();
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

    // Delete a feedback submission
    @PostMapping("/delete-feedback")
    public String deleteFeedback(@RequestParam("id") Long id) {
        trainingFeedbackRepository.deleteById(id);
        return "redirect:/view-feedbacks";
    }
}
