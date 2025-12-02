package habsida.spring.boot_security.demo.service;


import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import habsida.spring.boot_security.demo.dao.UserDao;
import habsida.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserDao userDao,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void saveUser(String userFirstName, String userFamilyName,
                         String username, String userPassword, String roleName) {

        if (userDao.existsByUsername(username)) {
            throw new IllegalArgumentException("User with username '" + username + "' already exists");
        }

        String dbRoleName = "ROLE_" + roleName;

        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Role not found: " + dbRoleName);
        }

        String encodedPassword = passwordEncoder.encode(userPassword);

        User user = new User(userFirstName, userFamilyName, username, encodedPassword, role);

        user.setRoles(Set.of(role));

        try {
            userDao.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Username already exists", e);
        }
    }



    @Override
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }

    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional
    public void updateUser(long id, String firstName, String familyName) {
        userDao.updateUser(id, firstName, familyName);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userDao.existsByUsername(username);
    }

}
