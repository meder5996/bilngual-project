package kg.peaksoft.bilingualb4.api.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TestResponseForClient {

    private String id;
    private String title;
    private String shortDescription;
    private boolean isActive;
    private int duration;
    private List<QuestionResponseForClient> questionResponseList;
}
