package com.usta.sigebex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccessController {
    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model
    ) {

        model.addAttribute("title", "Login");

        if (error != null) {
            model.addAttribute("error",
                    "Incorrect credentials. Please check your email and password.");
        }

        if (logout != null) {
            model.addAttribute("success", "You have successfully logged out.");
        }

        return "login";
    }
}
