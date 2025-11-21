package habsida.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import habsida.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoHibernateImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("FROM User", User.class)
                .getResultList();
    }

    @Override
    public void cleanUsersTable() {
        entityManager.createQuery("DELETE FROM User")
                .executeUpdate();
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(long id, String firstName, String familyName) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setUserFirstName(firstName);
            user.setUserFamilyName(familyName);
            entityManager.merge(user);
        }
    }

    @Override
    public User findByUsername(String username) {
        return entityManager.createQuery("FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean existsByUsername(String username) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }


}
