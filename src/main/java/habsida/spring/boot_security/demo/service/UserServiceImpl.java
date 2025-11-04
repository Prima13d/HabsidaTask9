package habsida.spring.boot_security.demo.service;


import habsida.spring.boot_security.demo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import habsida.spring.boot_security.demo.dao.UserDao;
import habsida.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void saveUser(String userFirstName, String userFamilyName,
                         String username, String userPassword, Role role) {
        String encodedPassword = passwordEncoder.encode(userPassword);
        User user = new User(userFirstName, userFamilyName, username, encodedPassword, role);
        userDao.saveUser(user);
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
}
