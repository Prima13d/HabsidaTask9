package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.dao.UserDao;
import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserServiceImpl(UserDao userDao,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void saveUser(String userFirstName,
                         String userFamilyName,
                         String username,
                         String userPassword,
                         String roleName) {

        if (userDao.existsByUsername(username)) {
            throw new IllegalArgumentException("User with username '" + username + "' already exists");
        }

        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Role not found: " + roleName);
        }

        User user = new User(
                userFirstName,
                userFamilyName,
                username,
                passwordEncoder.encode(userPassword)
        );

        user.setRoles(new HashSet<>());
        user.getRoles().add(role);

        try {
            userDao.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Username already exists", e);
        }
    }

    @Override
    @Transactional
    public void updateUser(long id,
                           String firstName,
                           String familyName,
                           String password,
                           String roleName) {

        User user = entityManager.find(User.class, id);
        if (user == null) {
            return;
        }

        user.setUserFirstName(firstName);
        user.setUserFamilyName(familyName);

        if (password != null && !password.isBlank()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Role not found: " + roleName);
        }

        user.getRoles().clear();
        user.getRoles().add(role);
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
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userDao.existsByUsername(username);
    }

    @Override
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
