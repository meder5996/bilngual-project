package kg.peaksoft.bilingualb4.api.payload;

import kg.peaksoft.bilingualb4.model.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TestResultRequest {

    private String userName;
    private LocalDateTime dateOfSubmission;
    private String testName;
    private Status status;
    private int score;
}
