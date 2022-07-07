package kg.peaksoft.bilingualb4.model.mappers;

import kg.peaksoft.bilingualb4.api.payload.MyResultRequest;
import kg.peaksoft.bilingualb4.api.payload.MyResultResponse;
import kg.peaksoft.bilingualb4.model.entity.MyResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyResultMapper {

    public MyResult mapToEntity (MyResultRequest myResultRequest){
        return MyResult.builder()
                .testName(myResultRequest.getTestName())
                .dateOfSubmission(myResultRequest.getDateOfSubmission())
                .status(myResultRequest.getStatus())
                .score(myResultRequest.getScore())
                .build();
    }

    public MyResultResponse mapToResponse (MyResult myResult){
        return MyResultResponse.builder()
                .id(myResult.getId())
                .testName(myResult.getTestName())
                .dateOfSubmission(myResult.getDateOfSubmission())
                .status(myResult.getStatus())
                .score(myResult.getScore())
                .build();
    }

    public List<MyResultResponse> mapToResponse(List<MyResult>myResultList){
        List<MyResultResponse>myResultResponses = new ArrayList<>();
        for (MyResult result: myResultList){
            myResultResponses.add(mapToResponse(result));
        }
        return myResultResponses;
    }
}
