package habsida.spring.boot_security.demo.repository;

import habsida.spring.boot_security.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);

    Role findByName(String name);

    @Query("SELECT r FROM Role r WHERE r.name = :name")
    List<Role> findAllByName(@Param("name") String name);
}

