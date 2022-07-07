package kg.peaksoft.bilingualb4.repository;

import kg.peaksoft.bilingualb4.model.entity.Question;
import kg.peaksoft.bilingualb4.model.enums.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select c from Question c where c.name =?1")
    Optional<Question> findByName(String name);

    @Query("select  q from Question q where q.questionType =?1")
    List<Question> findAllByQuestionType(QuestionType questionType);

}