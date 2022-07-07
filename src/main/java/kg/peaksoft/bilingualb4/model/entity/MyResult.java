package kg.peaksoft.bilingualb4.model.entity;

import kg.peaksoft.bilingualb4.model.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.awt.geom.RectangularShape;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "my_results")
public class MyResult {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_result_sequence")
    @SequenceGenerator(name = "my_result_sequence", sequenceName = "my_result_seq", allocationSize = 1)
    private Long id;

    private LocalDateTime dateOfSubmission;

    private String testName;

    @Enumerated(EnumType.STRING)
    private Status status;

    private double score;

    @ManyToOne(cascade = {REFRESH, MERGE, DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(cascade = ALL, mappedBy = "myResult")
    private List<QuestionResult> questionResultList;
}
