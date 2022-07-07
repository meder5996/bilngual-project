package kg.peaksoft.bilingualb4.model.entity;

import kg.peaksoft.bilingualb4.model.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "test_results")
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_result_sequence")
    @SequenceGenerator(name = "test_result_sequence", sequenceName = "test_result_seq", allocationSize = 1)
    private Long id;

    private String userName;
    private LocalDateTime dateOfSubmission;
    private String testName;
    @Enumerated(EnumType.STRING)
    private Status status;
    private double score;

    @OneToMany(cascade = ALL, fetch = FetchType.EAGER,mappedBy = "testResult")
    private List<QuestionResult> questionResults;

    @ManyToOne(cascade = {REFRESH, MERGE, DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Test test;
}

