package habsida.spring.boot_security.demo.service;


import habsida.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {

    void saveUser(String userFirstName,
                  String userFamilyName,
                  String username,
                  String userPassword,
                  String roleName);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();

    User getUserById(long id);

    void updateUser(long id,
                    String firstName,
                    String familyName,
                    String password,
                    String roleName);

    boolean existsByUsername(String username);
}
