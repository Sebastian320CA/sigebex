package com.usta.sigebex.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SigebexController {
    @GetMapping(value = "/viewDetail")
    public String viewMoreSigebex(Model model) {

        return "sigebex/viewDetail";
    }
}
