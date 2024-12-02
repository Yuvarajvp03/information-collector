package com.avirantEnterprises.information_collector.controller.personal;

import com.avirantEnterprises.information_collector.service.personal.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserProfileDeleteController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/profile/delete/{id}")
    public String deleteUserProfile(@PathVariable Long id) {
        registrationService.deleteUserById(id);
        return "redirect:/personal/profileList"; // Redirect to a list of profiles or another appropriate page
    }
}
