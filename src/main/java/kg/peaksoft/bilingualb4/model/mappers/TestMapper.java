package kg.peaksoft.bilingualb4.model.mappers;

import kg.peaksoft.bilingualb4.api.payload.TestRequest;
import kg.peaksoft.bilingualb4.api.payload.TestResponse;
import kg.peaksoft.bilingualb4.api.payload.TestResponseForClient;
import kg.peaksoft.bilingualb4.exception.NotFoundException;
import kg.peaksoft.bilingualb4.model.entity.Question;
import kg.peaksoft.bilingualb4.model.entity.Test;
import kg.peaksoft.bilingualb4.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class TestMapper {

    private final TestRepository testRepository;
    private final QuestionMapper questionMapper;

    public Test mapToEntity(Long id, TestRequest testRequest) {
        if (testRequest == null) {
            return null;
        }
        if(id == null) {
            return Test.builder()
                    .id(id)
                    .title(testRequest.getTitle())
                    .shortDescription(testRequest.getShortDescription())
                    .isActive(testRequest.isActive())
                    .build();
        }else {
            Test test = testRepository.findById(id).orElseThrow(()->
                    new NotFoundException(String.format("Object 'test' with $%d id not found!", id)));
            return Test.builder()
                    .id(id)
                    .title(testRequest.getTitle())
                    .shortDescription(testRequest.getShortDescription())
                    .isActive(testRequest.isActive())
                    .createdOn(test.getCreatedOn())
                    .build();
        }
    }

    public List<TestResponse> mapToResponse(List<Test> testList) {
        List<TestResponse> responses = new ArrayList<>();
        for (Test test : testList) {
            responses.add(mapToResponse(test));
        }
        return responses;
    }

    public TestResponse mapToResponse(Test test) {
        int duration = 0;
        if (test.getUserList() != null) {
            for (Question question : test.getQuestionList()) {
                duration += question.getDuration();
            }
        }
        if (test.getQuestionList() == null) {
            return TestResponse.builder()
                    .id(String.valueOf(test.getId()))
                    .title(test.getTitle())
                    .shortDescription(test.getShortDescription())
                    .isActive(test.isActive())
                    .duration(duration)
                    .build();
        } else {
            return TestResponse.builder()
                    .id(String.valueOf(test.getId()))
                    .title(test.getTitle())
                    .shortDescription(test.getShortDescription())
                    .isActive(test.isActive())
                    .duration(duration)
                    .questionResponseList(questionMapper.mapToResponse(test.getQuestionList()))
                    .build();
        }
    }

    public TestResponseForClient mapToResponseForClient(Test test) {
        int duration = 0;
        if (test.getUserList() != null) {
            for (Question question : test.getQuestionList()) {
                duration += question.getDuration();
            }
        }
        if (test.getQuestionList() == null) {
            return TestResponseForClient.builder()
                    .id(String.valueOf(test.getId()))
                    .title(test.getTitle())
                    .shortDescription(test.getShortDescription())
                    .isActive(test.isActive())
                    .duration(duration)
                    .build();
        } else {
            return TestResponseForClient.builder()
                    .id(String.valueOf(test.getId()))
                    .title(test.getTitle())
                    .shortDescription(test.getShortDescription())
                    .isActive(test.isActive())
                    .duration(duration)
                    .questionResponseList(questionMapper.mapToResponseForClient(test.getQuestionList()))
                    .build();
        }
    }

    public List<TestResponseForClient> mapToResponseForClient(List<Test> testList) {
        List<TestResponseForClient> responses = new ArrayList<>();
        for (Test test : testList) {
            responses.add(mapToResponseForClient(test));
        }
        return responses;
    }
}
