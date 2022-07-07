package kg.peaksoft.bilingualb4.api.payload;

import kg.peaksoft.bilingualb4.model.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyResultRequest {

    private String testName;
    private LocalDateTime dateOfSubmission;
    private Status status;
    private int score;
}
