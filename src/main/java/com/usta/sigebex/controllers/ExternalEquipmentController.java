package com.usta.sigebex.controllers;

import com.usta.sigebex.entities.ExternalEquipmentEntity;
import com.usta.sigebex.models.services.ExternalEquipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ExternalEquipmentController {

    @Autowired
    private ExternalEquipmentService externalEquipmentService;

    @GetMapping("/equipment/new")
    public String showEquipmentForm(Model model) {
        model.addAttribute("equipment", new ExternalEquipmentEntity());
        model.addAttribute("title", "Add New Equipment");
        return "externalEquipment/formEquipment";
    }

    @PostMapping("/equipment/new")
    public String saveEquipment(
            @Valid @ModelAttribute("equipment") ExternalEquipmentEntity equipment,
            BindingResult result,
            RedirectAttributes flash,
            Model model) {
        try {
            externalEquipmentService.save(equipment);
            flash.addFlashAttribute("success", "Equipment created successfully!");
            return "redirect:/equipment";

        } catch (Exception e) {
            model.addAttribute("error", "Error creating equipment: " + e.getMessage());
            model.addAttribute("title", "Add New Equipment");
            return "externalEquipment/formEquipment";
        }
    }

    @GetMapping("/equipment/edit/{id}")
    public String editEquipment(@PathVariable Long id, Model model) {
        ExternalEquipmentEntity equipment = externalEquipmentService.findById(id);
        if (equipment == null) {
            return "redirect:/equipment";
        }
        model.addAttribute("equipment", equipment);
        model.addAttribute("title", "Edit Equipment");
        return "externalEquipment/formEquipment";
    }

    @PostMapping("/equipment/edit/{id}")
    public String updateEquipment(
            @PathVariable Long id,
            @Valid @ModelAttribute("equipment") ExternalEquipmentEntity equipment,
            BindingResult result,
            RedirectAttributes flash,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("title", "Edit Equipment");
            return "externalEquipment/formEquipment";
        }

        try {
            equipment.setId(id);
            externalEquipmentService.save(equipment);
            flash.addFlashAttribute("success", "Equipment updated successfully!");
            return "redirect:/equipment";

        } catch (Exception e) {
            model.addAttribute("error", "Error updating equipment: " + e.getMessage());
            model.addAttribute("title", "Edit Equipment");
            return "externalEquipment/formEquipment";
        }
    }

    @PostMapping("/equipment/delete/{id}")
    public String deleteEquipment(@PathVariable Long id, RedirectAttributes flash) {
        try {
            externalEquipmentService.deleteById(id);
            flash.addFlashAttribute("success", "Equipment deleted successfully!");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error deleting equipment: " + e.getMessage());
        }
        return "redirect:/equipment";
    }

    @GetMapping("/equipment")
    public String equipmentList(Model model,
                                @RequestParam(value = "type", required = false) String type,
                                @RequestParam(value = "brand", required = false) String brand) {

        List<ExternalEquipmentEntity> equipment;

        if ((type == null || type.isEmpty()) &&
                (brand == null || brand.isEmpty())) {
            equipment = externalEquipmentService.findAll();
        } else {
            equipment = externalEquipmentService.search(type, brand);
        }

        model.addAttribute("equipment", equipment);
        model.addAttribute("type", type);
        model.addAttribute("brand", brand);
        model.addAttribute("title", "Equipment List");

        return "externalEquipment/equipmentList";
    }
}