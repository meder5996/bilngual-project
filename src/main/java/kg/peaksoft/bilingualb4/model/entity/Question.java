package kg.peaksoft.bilingualb4.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.peaksoft.bilingualb4.model.enums.QuestionType;
import kg.peaksoft.bilingualb4.model.enums.SingleAndMultiType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "questions")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_sequence")
    @SequenceGenerator(name = "question_sequence", sequenceName = "question_seq", allocationSize = 1)
    private Long id;

    private String name;
    @Enumerated(EnumType.STRING)
    private SingleAndMultiType singleAndMultiType;

    @OneToMany(cascade = {MERGE, DETACH, REFRESH, REMOVE, PERSIST}, fetch = FetchType.EAGER)
    private List<Options> optionsList;
    private String audio;
    private boolean isActive;
    private String fileName;
    private int numberOfReplays;
    private String upload;
    private String play;
    private String correctAnswer;
    private String record;
    private String uploadImage;
    private String questionStatement;
    private int wordCounter;
    private String questionToThePassage;
    @Column(length = 1000000)
    private String passage;
    private String highlightCorrectAnswer;
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    private int duration;

    @ManyToOne(cascade = {REFRESH, DETACH, MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Test test;

    @OneToMany(cascade = ALL)
    private List<QuestionResult>questionResultList;

}
