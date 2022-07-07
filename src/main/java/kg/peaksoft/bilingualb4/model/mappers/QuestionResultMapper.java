package kg.peaksoft.bilingualb4.model.mappers;

import kg.peaksoft.bilingualb4.api.payload.QuestionResultResponse;
import kg.peaksoft.bilingualb4.model.entity.QuestionResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionResultMapper {

    public QuestionResultResponse mapToResponse(QuestionResult questionResult) {
        return QuestionResultResponse.builder()
                .id(questionResult.getId())
                .userName(questionResult.getUserName())
                .testName(questionResult.getTestName())
                .dateOfSubmission(questionResult.getDateOfSubmission())
                .questionName(questionResult.getQuestionName())
                .score(questionResult.getScore())
                .status(questionResult.getStatus())
                .finalScore(questionResult.getFinalScore())
                .finalStatus(questionResult.getFinalStatus())
                .build();
    }

    public List<QuestionResultResponse> mapToResponse(List<QuestionResult> questionResultList) {
        List<QuestionResultResponse> responseList = new ArrayList<>();
        for (QuestionResult questionResult : questionResultList) {
            responseList.add(mapToResponse(questionResult));
        }
        return responseList;
    }
}
