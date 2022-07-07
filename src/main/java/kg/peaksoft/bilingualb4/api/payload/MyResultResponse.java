package kg.peaksoft.bilingualb4.api.payload;

import kg.peaksoft.bilingualb4.model.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MyResultResponse {

    private Long id;
    private LocalDateTime dateOfSubmission;
    private String testName;
    private Status status;
    private double score;
}
