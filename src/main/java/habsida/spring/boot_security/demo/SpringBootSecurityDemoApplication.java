package habsida.spring.boot_security.demo;

import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.repository.RoleRepository;
import habsida.spring.boot_security.demo.repository.UserRepository;
import habsida.spring.boot_security.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService, RoleRepository roleRepository, UserRepository userRepository) {
		return args -> {
			List<Role> adminRoles = roleRepository.findAllByName("ADMIN");
			Role adminRole;
			if (adminRoles.isEmpty()) {
				adminRole = new Role("ADMIN");
				roleRepository.save(adminRole);
			} else {
				adminRole = adminRoles.get(0);
			}

			if (!userRepository.existsByUsername("prima")) {
				userService.saveUser("Alexey", "Kim",
						"prima", "12345", adminRole);
			}
		};
	}




}
