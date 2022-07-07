package kg.peaksoft.bilingualb4.model.mappers;

import kg.peaksoft.bilingualb4.api.payload.TestResultResponse;
import kg.peaksoft.bilingualb4.model.entity.MyResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestResultMapper {

    public TestResultResponse mapToResponse(MyResult myResult){
        return TestResultResponse.builder()
                .id(myResult.getId())
                .userName(myResult.getUser().getUserName())
                .dateOfSubmission(myResult.getDateOfSubmission())
                .testName(myResult.getTestName())
                .status(myResult.getStatus())
                .score(myResult.getScore())
                .build();
    }

    public List<TestResultResponse> mapToResponse(List<MyResult>myResults){
        List<TestResultResponse> testResultResponses = new ArrayList<>();
        for (MyResult myResult: myResults){
            testResultResponses.add(mapToResponse(myResult));
        }
        return testResultResponses;
    }

}
