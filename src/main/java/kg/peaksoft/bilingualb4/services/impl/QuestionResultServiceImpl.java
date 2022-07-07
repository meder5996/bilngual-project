package kg.peaksoft.bilingualb4.services.impl;

import kg.peaksoft.bilingualb4.api.payload.EvaluateResponse;
import kg.peaksoft.bilingualb4.api.payload.QuestionResultRequest;
import kg.peaksoft.bilingualb4.api.payload.QuestionResultResponse;
import kg.peaksoft.bilingualb4.exception.BadRequestException;
import kg.peaksoft.bilingualb4.exception.NotFoundException;
import kg.peaksoft.bilingualb4.model.entity.MyResult;
import kg.peaksoft.bilingualb4.model.entity.QuestionResult;
import kg.peaksoft.bilingualb4.model.entity.TestResult;
import kg.peaksoft.bilingualb4.model.enums.Status;
import kg.peaksoft.bilingualb4.model.mappers.OptionMapper;
import kg.peaksoft.bilingualb4.model.mappers.QuestionResultMapper;
import kg.peaksoft.bilingualb4.repository.MyResultRepository;
import kg.peaksoft.bilingualb4.repository.QuestionResultRepository;
import kg.peaksoft.bilingualb4.repository.TestResultRepository;
import kg.peaksoft.bilingualb4.services.QuestionResultService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class QuestionResultServiceImpl implements QuestionResultService {

    private final QuestionResultMapper questionResultMapper;
    private final QuestionResultRepository questionResultRepository;
    private final EmailService emailService;
    private final OptionMapper optionMapper;
    private final MyResultRepository myResultRepository;
    private final TestResultRepository testResultRepository;

    @Override
    public List<QuestionResultResponse> findAll() {
        List<QuestionResult> questionResultList = questionResultRepository.findAll();
        List<QuestionResult> evaluateQuestionResult = new ArrayList<>();
        List<QuestionResult> notEvaluateQuestionResult = new ArrayList<>();
        List<QuestionResult> sortedList = new ArrayList<>();
        for (QuestionResult questionResult: questionResultList) {
            if (questionResult.getStatus() == Status.EVALUATE) {
                evaluateQuestionResult.add(questionResult);
                Arrays.sort(new List[]{evaluateQuestionResult});
            } else {
                notEvaluateQuestionResult.add(questionResult);
                Arrays.sort(new List[]{notEvaluateQuestionResult});
            }
        }
        sortedList.addAll(notEvaluateQuestionResult);
        sortedList.addAll(evaluateQuestionResult);
        return questionResultMapper.mapToResponse(sortedList);
    }

    @Override
    public EvaluateResponse findById(Long id) {
         QuestionResult questionResult = questionResultRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Object 'questionResult' with %d id not found!", id)
         ));
         EvaluateResponse evaluateResponse = new EvaluateResponse();
         evaluateResponse.setUserName(questionResult.getUserName());
         evaluateResponse.setTestName(questionResult.getTestName());
         evaluateResponse.setQuestionName(questionResult.getQuestionName());
         evaluateResponse.setDuration();
         evaluateResponse.setQuestionType(questionResult.getQuestion().getQuestionType());
         evaluateResponse.setOptions(optionMapper.mapToResponse(questionResult.getQuestion().getOptionsList()));
         evaluateResponse.setScore(questionResult.getScore());
         evaluateResponse.setUserAnswer();
    }

    @Override
    public QuestionResultResponse updateById(Long id, QuestionResultRequest questionResultRequest) {
        QuestionResult questionResult = questionResultRepository.findById(id).orElseThrow(()->
                new NotFoundException("Object 'questionResult' with %d id not found!"));
        if(questionResult.getStatus() == Status.EVALUATE){
            throw new BadRequestException("This questionResult is already checked!");
        }
        int score;
        score = questionResultRequest.getScore();
        questionResult.setScore(score);
        questionResult.setStatus(Status.EVALUATE);
        TestResult testResult = questionResult.getTestResult();
        double sum = 0;
        for (QuestionResult questionResult1: testResult.getQuestionResults()){
            sum+=questionResult1.getScore();
        }
        questionResult.setFinalScore(sum/100);
        questionResult.setFinalStatus(Status.EVALUATE);
        questionResultRepository.save(questionResult);

        testResult.setScore(sum/100);
        testResult.setStatus(Status.EVALUATE);
        testResultRepository.save(testResult);

        MyResult myResult = questionResult.getMyResult();
        myResult.setScore(sum/100);
        myResult.setStatus(Status.EVALUATE);
        myResultRepository.save(myResult);

        return questionResultMapper.mapToResponse(questionResult);
    }

    @Override
    public void sendResultsToUser(Long id) {
        MyResult myResult = myResultRepository.findById(id).orElseThrow(() ->
                new NotFoundException(
                        String.format("Result with %d id not found!", id)));

        String userEmail;
        if (myResult.getStatus() != Status.EVALUATE) {
            throw new BadRequestException("This user's answers are not yet fully verified");
        }else {
            userEmail = myResult.getUser().getEmail();

            String userName = myResult.getUser().getUserName().toUpperCase(Locale.ROOT);
            String testName = myResult.getTestName();
            LocalDateTime date = myResult.getDateOfSubmission();
            double score  = myResult.getScore();

            String message =
                    "\nDear " + userName +
                            ", \nYour test: "+testName+
                            ", \nYou finished at: " + date +
                            ", \nYour score: " + score;

            emailService.send(userEmail, message);
        }
    }
}
