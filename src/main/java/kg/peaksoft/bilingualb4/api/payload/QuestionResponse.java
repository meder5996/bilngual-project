package kg.peaksoft.bilingualb4.api.payload;

import kg.peaksoft.bilingualb4.model.enums.QuestionType;
import kg.peaksoft.bilingualb4.model.enums.SingleAndMultiType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class QuestionResponse {

    private String id;
    private String name;
    private SingleAndMultiType singleAndMultiType;
    private List<OptionResponse> optionsList;
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
    private String passage;
    private String highlightCorrectAnswer;
    private QuestionType questionType;
    private int duration;
}
