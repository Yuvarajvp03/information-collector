package com.avirantEnterprises.information_collector.controller.personal;

import com.avirantEnterprises.information_collector.model.personal.User;
import com.avirantEnterprises.information_collector.service.personal.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserProfileController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/profile/{id}")
    public String viewUserProfile(@PathVariable Long id, Model model) {
        User user = registrationService.getUserById(id);
        System.out.println("Profile Picture Path: " + user.getProfilePicPath());
        model.addAttribute("user", user);
        return "personal/profile_view"; // View template for displaying the user profile
    }
}
