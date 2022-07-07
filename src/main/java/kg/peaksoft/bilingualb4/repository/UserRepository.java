package kg.peaksoft.bilingualb4.repository;

import kg.peaksoft.bilingualb4.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select case when count(c) > 0 then true else false end" +
            " from User c where c.authInfo.email = :email")
    boolean existsByEmail(String email);

    User findByEmail(String email);

    User findByUserName(String name);
}
