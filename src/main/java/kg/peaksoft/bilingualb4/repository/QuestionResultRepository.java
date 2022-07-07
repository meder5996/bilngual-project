package kg.peaksoft.bilingualb4.repository;

import kg.peaksoft.bilingualb4.model.entity.QuestionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionResultRepository extends JpaRepository<QuestionResult, Long> {

    List<QuestionResult> findAllByMyResultId(Long id);

    @Query("select case when count(c) > 0 then true else false end from QuestionResult c where c.id =?1")
    boolean existsById(Long id);

    @Query("select c from QuestionResult c where c.question.id=:id")
    List<QuestionResult> findAllByQuestionId(Long id);

    @Query("select c from QuestionResult c where c.myResult.user.authInfo.email = ?1")
    List<QuestionResult> existsByEmail(String email);
}