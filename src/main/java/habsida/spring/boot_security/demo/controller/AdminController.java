package habsida.spring.boot_security.demo.controller;

import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users_panel";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam String userFirstName,
                          @RequestParam String userFamilyName,
                          @RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String role) {

        userService.saveUser(userFirstName, userFamilyName, username, password, role);
        return "redirect:/admin/users";
    }



    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/edit")
    public String updateUser(@RequestParam long id,
                             @RequestParam String userFirstName,
                             @RequestParam String userFamilyName) {

        userService.updateUser(id, userFirstName, userFamilyName);
        return "redirect:/admin/users";
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public User getUserJson(@PathVariable long id) {
        return userService.getUserById(id);
    }

}
