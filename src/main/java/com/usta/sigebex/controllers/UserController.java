package com.usta.sigebex.controllers;

import com.usta.sigebex.entities.UserEntity;
import com.usta.sigebex.models.services.RolService;
import com.usta.sigebex.models.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RolService rolService;

    @GetMapping("/users")
    public String getUser(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "lastName", required = false) String lastName,
            Model model
    ){
        List<UserEntity> users = userService.listFiltered(name, lastName);
        model.addAttribute("users", users);
        model.addAttribute("name", name);
        model.addAttribute("lastName", lastName);
        model.addAttribute("title", "User List");
        return "users/userList";
    }
    @GetMapping("/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserEntity());
        model.addAttribute("roles", rolService.findAll());
        model.addAttribute("title", "New User");
        return "users/formUser";
    }

    @PostMapping("/users/new")
    public String saveUser(
            @Valid @ModelAttribute("user") UserEntity user,
            BindingResult result,
            RedirectAttributes flash,
            Model model
    ){
        if (result.hasErrors()) {
            model.addAttribute("roles", rolService.findAll());
            return "users/formUser";
        }
        userService.save(user);
        flash.addFlashAttribute("success", "User saved successfully");
        return "redirect:/users";
    }
    @GetMapping("/users/edit")
    public String editUser(
            @RequestParam("id") Long id,
            RedirectAttributes flash,
            Model model
    ){
        UserEntity user = userService.findById(id);

        if(user == null){
            flash.addFlashAttribute("error", "User not found");
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        model.addAttribute("roles", rolService.findAll());
        model.addAttribute("title", "Edit User");
        return  "/users/formUser";
    }
    @GetMapping("/users/togleState")
    public String toggleState(
            @RequestParam("id") Long id,
            RedirectAttributes flash,
            Model model
    ){
        UserEntity user = userService.findById(id);
        if(user == null){
            flash.addFlashAttribute("error", "User not found");
            return "redirect:/users";
        }
        user.setUserState(!user.isUserState());
        userService.updateUser(user);

        flash.addFlashAttribute("success", "User state updated");
        return "redirect:/users";
    }
    @GetMapping("/users/delete")
    public String deleteUser(
            @RequestParam("id") Long id,
            RedirectAttributes flash
    ) {
        UserEntity user = userService.findById(id);

        if (user == null) {
            flash.addFlashAttribute("error", "User not found");
            return "redirect:/users";
        }

        // Obtener usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = auth.getName();

        // Evitar que un usuario se elimine a sí mismo
        if (user.getEmail().equals(currentEmail)) {
            flash.addFlashAttribute("error", "You cannot delete your own account.");
            return "redirect:/users";
        }

        userService.deleteById(id);
        flash.addFlashAttribute("success", "User deleted successfully");
        return "redirect:/users";
    }

    @PostMapping("/users/edit")
    public String updateUser(
            @Valid @ModelAttribute("user") UserEntity user,
            BindingResult result,
            RedirectAttributes flash,
            Model model
    ) {

        if (result.hasErrors()) {
            model.addAttribute("roles", rolService.findAll());
            return "users/formUser";
        }

        UserEntity existing = userService.findById(user.getId());

        if (existing == null) {
            flash.addFlashAttribute("error", "User not found");
            return "redirect:/users";
        }

        existing.setUserName(user.getUserName());
        existing.setUserLastName(user.getUserLastName());
        existing.setEmail(user.getEmail());
        existing.setRol(user.getRol());
        existing.setUserState(user.isUserState());

        existing.setPassword(existing.getPassword());

        userService.save(existing);

        flash.addFlashAttribute("success", "User updated successfully");
        return "redirect:/users";
    }

}
