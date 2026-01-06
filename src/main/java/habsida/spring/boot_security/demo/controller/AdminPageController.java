package habsida.spring.boot_security.demo.controller;

import  org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @GetMapping("/users")
    public String showAdminPage() {
        return "users";
    }

}
