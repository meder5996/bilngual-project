package kg.peaksoft.bilingualb4.model.mappers;

import kg.peaksoft.bilingualb4.api.payload.QuestionRequest;
import kg.peaksoft.bilingualb4.api.payload.QuestionResponse;
import kg.peaksoft.bilingualb4.api.payload.QuestionResponseForClient;
import kg.peaksoft.bilingualb4.exception.NotFoundException;
import kg.peaksoft.bilingualb4.model.entity.Question;
import kg.peaksoft.bilingualb4.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class QuestionMapper {

    private final TestRepository testRepository;
    private final OptionMapper optionMapper;

   public List<QuestionResponse> mapToResponse(List<Question> questions){
       List<QuestionResponse> responses = new ArrayList<>();
       for (Question question : questions) {
           responses.add(mapToResponse(question));
       }
       return responses;
   }

    public Question mapToEntity(Long id,Long tesId, QuestionRequest questionRequest) {
        return Question.builder()
                .id(id)
                .name(questionRequest.getName())
                .singleAndMultiType(questionRequest.getSingleAndMultiType())
                .optionsList(optionMapper.mapToEntity(questionRequest.getOptionsList()))
                .audio(questionRequest.getAudio())
                .isActive(questionRequest.isActive())
                .fileName(questionRequest.getFileName())
                .numberOfReplays(questionRequest.getNumberOfReplays())
                .upload(questionRequest.getUpload())
                .play(questionRequest.getPlay())
                .correctAnswer(questionRequest.getCorrectAnswer())
                .record(questionRequest.getRecord())
                .uploadImage(questionRequest.getUploadImage())
                .questionStatement(questionRequest.getQuestionStatement())
                .wordCounter(questionRequest.getWordCounter())
                .questionToThePassage(questionRequest.getQuestionToThePassage())
                .passage(questionRequest.getPassage())
                .highlightCorrectAnswer(questionRequest.getHighlightCorrectAnswer())
                .questionType(questionRequest.getQuestionType())
                .duration(questionRequest.getDuration())
                .test(testRepository.findById(tesId).orElseThrow(()-> new NotFoundException(
                        String.format("Object 'test' with %d id not found!",id)
                )))
                .build();
    }
    public QuestionResponse mapToResponse(Question question){
        if (question ==null){
            return null;
        }
        return QuestionResponse.builder()
                .id(String.valueOf(question.getId()))
                .name(question.getName())
                .singleAndMultiType(question.getSingleAndMultiType())
                .optionsList(optionMapper.mapToResponse(question.getOptionsList()))
                .audio(question.getAudio())
                .isActive(question.isActive())
                .fileName(question.getFileName())
                .numberOfReplays(question.getNumberOfReplays())
                .upload(question.getUpload())
                .play(question.getPlay())
                .correctAnswer(question.getCorrectAnswer())
                .record(question.getRecord())
                .uploadImage(question.getUploadImage())
                .questionStatement(question.getQuestionStatement())
                .wordCounter(question.getWordCounter())
                .questionToThePassage(question.getQuestionToThePassage())
                .passage(question.getPassage())
                .highlightCorrectAnswer(question.getHighlightCorrectAnswer())
                .questionType(question.getQuestionType())
                .duration(question.getDuration())
                .build();
    }

    public QuestionResponseForClient mapToResponseForClient(Question question){
        if (question ==null){
            return null;
        }
        return QuestionResponseForClient.builder()
                .id(String.valueOf(question.getId()))
                .name(question.getName())
                .singleAndMultiType(question.getSingleAndMultiType())
                .optionResponseList(optionMapper.mapToResponseForClient(question.getOptionsList()))
                .audio(question.getAudio())
                .fileName(question.getFileName())
                .numberOfReplays(question.getNumberOfReplays())
                .upload(question.getUpload())
                .play(question.getPlay())
                .correctAnswer(question.getCorrectAnswer())
                .record(question.getRecord())
                .uploadImage(question.getUploadImage())
                .questionStatement(question.getQuestionStatement())
                .wordCounter(question.getWordCounter())
                .questionToThePassage(question.getQuestionToThePassage())
                .passage(question.getPassage())
                .highlightCorrectAnswer(question.getHighlightCorrectAnswer())
                .questionType(question.getQuestionType())
                .duration(question.getDuration())
                .build();
    }

    public List<QuestionResponseForClient> mapToResponseForClient(List<Question> questions){
        List<QuestionResponseForClient> responses = new ArrayList<>();
        for (Question question : questions) {
            responses.add(mapToResponseForClient(question));
        }
        return responses;
    }
}