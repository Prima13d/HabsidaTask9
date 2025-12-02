package habsida.spring.boot_security.demo.dao;
import habsida.spring.boot_security.demo.model.Role;

public interface RoleDao {
    Role findByName(String name);
}

