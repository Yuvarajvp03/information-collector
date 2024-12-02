package com.avirantEnterprises.information_collector.controller.personal;

import com.avirantEnterprises.information_collector.model.personal.User;
import com.avirantEnterprises.information_collector.service.personal.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserProfileListController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/profileList")
    public String listUserProfiles(Model model) {
        List<User> users = registrationService.getAllUsers();
        model.addAttribute("users", users);
        return "personal/profile_list";
    }
}
