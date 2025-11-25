package com.usta.sigebex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

@Controller
public class ContactController {
    private static final Logger log = Logger.getLogger(ContactController.class.getName());


    @GetMapping("/contact")
    public String showContactPage(Model model) {
        return "sigebex/contact";
    }

    @PostMapping("/contact")
    public String submitContactForm(@RequestParam String subject,
                                    @RequestParam String type,
                                    @RequestParam String message,
                                    @RequestParam String name,
                                    @RequestParam String phone,
                                    @RequestParam String email,
                                    Model model) {

        model.addAttribute("success", true);
        return "sigebex/contact";
    }

}
