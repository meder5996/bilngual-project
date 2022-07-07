package kg.peaksoft.bilingualb4.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "options")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "option_sequence")
    @SequenceGenerator(name = "option_sequence", sequenceName = "option_seq", allocationSize = 1)
    @JsonIgnore
    private Long id;
    private String optionName;
    private String file;
    private boolean isCorrect = false;

}