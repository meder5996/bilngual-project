package kg.peaksoft.bilingualb4.repository;

import kg.peaksoft.bilingualb4.model.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    @Query("select case when count(c) > 0 then true else false end from Test c where c.title =?1")
    boolean existsByName(String title);

    @Query("from Test c where c.isActive = true")
    List<Test> findAllByActive();

    @Query("from Test c where c.isActive =?1 order by c.createdOn")
    List<Test> findAllWithSortByDate(boolean active);
}
