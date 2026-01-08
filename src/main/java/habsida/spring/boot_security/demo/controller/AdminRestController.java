package habsida.spring.boot_security.demo.controller;

import habsida.spring.boot_security.demo.dto.UserCreateDto;
import habsida.spring.boot_security.demo.dto.UserUpdateDto;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public void addUser(@RequestBody UserCreateDto dto) {
        userService.saveUser(
                dto.getUserFirstName(),
                dto.getUserFamilyName(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getRole()
        );
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id,
                           @RequestBody UserUpdateDto dto) {

        userService.updateUser(
                id,
                dto.getUserFirstName(),
                dto.getUserFamilyName(),
                dto.getPassword(),
                dto.getRole()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.removeUserById(id);
    }
}
