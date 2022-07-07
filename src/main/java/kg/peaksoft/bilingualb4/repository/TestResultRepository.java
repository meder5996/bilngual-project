package kg.peaksoft.bilingualb4.repository;

import kg.peaksoft.bilingualb4.model.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    @Query("select case when count(c) > 0 then true else false end from TestResult c where c.userName =?1")
    boolean existsTestResultByUserName(String userName);

    @Query("from TestResult c where c.userName =?1")
    Optional<TestResult> findByUserName(String userName);
}