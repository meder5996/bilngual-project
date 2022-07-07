package kg.peaksoft.bilingualb4.api.payload;

import jdk.jfr.BooleanFlag;
import kg.peaksoft.bilingualb4.model.enums.QuestionType;
import kg.peaksoft.bilingualb4.model.enums.SingleAndMultiType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class QuestionResponseForClient {
    private String id;
    private String name;
    private SingleAndMultiType singleAndMultiType;
    private List<OptionResponseForClient> optionResponseList;
    private String audio;
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
