package habsida.spring.boot_security.demo.controller;

import  org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class AdminPageController {

    private final UserService userService;

    @Autowired
    public AdminPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/add")
    public String showAddUserForm() {
        return "add_user";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam("userFirstName") String userFirstName,
                          @RequestParam("userFamilyName") String userFamilyName,
                          @RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam("role") String role) {
        userService.saveUser(userFirstName, userFamilyName, username, password, role);
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUserById (id);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit_user";
    }

    @PostMapping("/edit")
    public String updateUser(@RequestParam("id") Long id,
                             @RequestParam String userFirstName,
                             @RequestParam String userFamilyName) {
        userService.updateUser(id, userFirstName, userFamilyName);
        return "redirect:/admin/users";
    }



}
