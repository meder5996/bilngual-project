package kg.peaksoft.bilingualb4.repository;

import kg.peaksoft.bilingualb4.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role getByName(String name);
}
