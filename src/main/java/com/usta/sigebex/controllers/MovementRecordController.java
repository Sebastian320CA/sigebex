package com.usta.sigebex.controllers;

import com.usta.sigebex.entities.ExternalEquipmentEntity;
import com.usta.sigebex.entities.MovementRecordEntity;
import com.usta.sigebex.models.services.ExternalEquipmentService;
import com.usta.sigebex.models.services.MovementRecordService;
import com.usta.sigebex.models.services.UserService;

import jakarta.validation.Valid;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MovementRecordController {

    @Autowired
    private MovementRecordService movementRecordService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExternalEquipmentService equipmentService;


    @GetMapping("/movementRecord/new")
    public String newMovementRecord(
            @RequestParam(value = "equipmentId", required = false) Long equipmentId,
            Model model) {

        MovementRecordEntity record = new MovementRecordEntity();

        if (equipmentId != null) {
            ExternalEquipmentEntity equipment = equipmentService.findById(equipmentId);
            if (equipment != null) {
                record.setTeam(equipment);
            }
        }
        model.addAttribute("title", "New movement record");
        model.addAttribute("record", record);
        model.addAttribute("equipmentList", equipmentService.findAll());
        model.addAttribute("users", userService.findAll());

        return "movementRecord/formMovement";
    }

    @PostMapping("/movementRecord")
    @Transactional
    public String saveMovementRecord(

            @Valid @ModelAttribute("record") MovementRecordEntity record,
            BindingResult result,
            @RequestParam(value = "photoFile", required = false) MultipartFile photoFile,
            RedirectAttributes flash,
            Model model
    ) {
        if (result.hasErrors()) {
            System.out.println("Validation errors in MovementRecord");
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));


            model.addAttribute("title", "New movement record");
            model.addAttribute("equipmentList", equipmentService.findAll());
            model.addAttribute("users", userService.findAll());
            return "movementRecord/formMovement";
        }

        if (photoFile != null && !photoFile.isEmpty()) {
            String photoUrl = uploadImage(photoFile);
            record.setPhoto(photoUrl);
        }
        if(record.getEntryDate() == null)
            record.setEntryDate(LocalDate.now());
        movementRecordService.save(record);


        flash.addFlashAttribute("success", "Movement record successfully created");
        return "redirect:/movementRecord";
    }

    @GetMapping("/movementRecord")
    public String listMovementRecord(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long equipmentId,
            Model model
    ){
        List<MovementRecordEntity> records;

        if(userId != null){
            records = movementRecordService.findByUserId(userId);
        } else if (equipmentId != null) {
            records = movementRecordService.findByEquipmentId(equipmentId);
        }else{
            records = movementRecordService.findAll();
        }
        model.addAttribute("title", "Movement Records List");
        model.addAttribute("records", records);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("equipmentList", equipmentService.findAll());

        return  "movementRecord/movementList";
    }

    private String uploadImage(MultipartFile image) {
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost uploadRequest = new HttpPost("https://api.imgbb.com/1/upload");

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("key", "e2906abfdb82140c3c2b66ccfd336ab8" , ContentType.TEXT_PLAIN);

            builder.addBinaryBody(
                    "image",
                    image.getInputStream(),
                    ContentType.DEFAULT_BINARY,
                    image.getOriginalFilename()
            );

            HttpEntity multipart = builder.build();
            uploadRequest.setEntity(multipart);

            HttpResponse response = httpClient.execute(uploadRequest);
            HttpEntity responseEntity = response.getEntity();

            if (response.getStatusLine().getStatusCode() == 200){
                String responseString = EntityUtils.toString(responseEntity);
                JSONObject jsonResponse = new JSONObject(responseString);
                boolean success = jsonResponse.getBoolean("success");
                if(success){
                    JSONObject data = jsonResponse.getJSONObject("data");
                    return data.getString("url");
                }else{
                    System.out.println("Error loading image: "
                            + jsonResponse.optString("error", "Unknown error"));
                }
            }
        }catch(Exception eX){
            eX.printStackTrace(System.err);
        }
        return null;
    }
    @GetMapping("/movementRecord/edit/{id}")
    public String editMovementRecord(@PathVariable("id") Long id, Model model, RedirectAttributes flash) {
        MovementRecordEntity record = movementRecordService.findById(id);

        if (record == null) {
            flash.addFlashAttribute("error", "Movement record not found");
            return "redirect:/movementRecord";
        }

        model.addAttribute("title", "Edit movement record");
        model.addAttribute("record", record);
        model.addAttribute("equipmentList", equipmentService.findAll());
        model.addAttribute("users", userService.findAll());

        return "movementRecord/formMovement";
    }

    @PostMapping("/movementRecord/edit/{id}")
    @Transactional
    public String updateMovementRecord(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("record") MovementRecordEntity record,
            BindingResult result,
            @RequestParam(value = "photoFile", required = false) MultipartFile photoFile,
            RedirectAttributes flash,
            Model model
    ) {
        if (result.hasErrors()) {
            System.out.println("Validation errors in MovementRecord edit");
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));

            model.addAttribute("title", "Edit movement record");
            model.addAttribute("equipmentList", equipmentService.findAll());
            model.addAttribute("users", userService.findAll());
            return "movementRecord/formMovement";
        }

        record.setId(id);

        if (photoFile != null && !photoFile.isEmpty()) {
            String photoUrl = uploadImage(photoFile);
            record.setPhoto(photoUrl);
        }

        movementRecordService.save(record);
        flash.addFlashAttribute("success", "Movement record successfully updated");
        return "redirect:/movementRecord";
    }
    @GetMapping("/movementRecord/exit/{id}")
    public String registerExit(@PathVariable("id") Long id, RedirectAttributes flash) {
        MovementRecordEntity record = movementRecordService.findById(id);

        if (record == null) {
            flash.addFlashAttribute("error", "Movement record not found");
            return "redirect:/movementRecord";
        }

        record.setExitDate(LocalDate.now());
        record.setState("Fuera");
        movementRecordService.save(record);

        flash.addFlashAttribute("success", "Exit registered successfully");
        return "redirect:/movementRecord";
    }
}
