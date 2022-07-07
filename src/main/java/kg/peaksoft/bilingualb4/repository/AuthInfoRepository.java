package kg.peaksoft.bilingualb4.repository;

import kg.peaksoft.bilingualb4.model.entity.AuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthInfoRepository extends JpaRepository<AuthInfo,Long> {

    Optional<AuthInfo> findByEmail(String email);
}
