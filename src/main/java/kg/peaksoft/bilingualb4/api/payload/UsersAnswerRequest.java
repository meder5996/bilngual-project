package kg.peaksoft.bilingualb4.api.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsersAnswerRequest {

    private List<Long> optionsList;
    private String someText;
    private int counterOfWord;
}
