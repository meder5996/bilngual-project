package kg.peaksoft.bilingualb4.services.impl;

import kg.peaksoft.bilingualb4.api.payload.EvaluateResponse;
import kg.peaksoft.bilingualb4.api.payload.MyResultResponse;
import kg.peaksoft.bilingualb4.api.payload.UsersAnswerRequest;
import kg.peaksoft.bilingualb4.exception.BadRequestException;
import kg.peaksoft.bilingualb4.exception.NotFoundException;
import kg.peaksoft.bilingualb4.model.entity.*;
import kg.peaksoft.bilingualb4.model.enums.QuestionType;
import kg.peaksoft.bilingualb4.model.mappers.OptionMapper;
import kg.peaksoft.bilingualb4.repository.*;
import kg.peaksoft.bilingualb4.services.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static kg.peaksoft.bilingualb4.model.enums.Status.EVALUATE;
import static kg.peaksoft.bilingualb4.model.enums.Status.NOT_EVALUATE;

@Service
@AllArgsConstructor
class AnswerServiceImpl implements AnswerService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final QuestionResultRepository questionResultRepository;
    private final MyResultRepository myResultRepository;
    private final OptionMapper optionMapper;
    private final TestResultRepository testResultRepository;

    @Override
    public EvaluateResponse save(Long id, UsersAnswerRequest usersAnswerRequest, Principal principal) {
        Question question = questionRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Object 'question' with %d id not found", id)));

        if (question.getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORD ||
                question.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD ||
                question.getQuestionType() == QuestionType.SELECT_MAIN_IDEA ||
                question.getQuestionType() == QuestionType.SELECT_THE_BEST_TITLE) {
            return autoCheck(id, usersAnswerRequest, principal);
        } else if (question.getQuestionType() == QuestionType.HIGHLIGHTS_THE_ANSWER) {
            return checkerForHighlightType(id, usersAnswerRequest, principal);
        } else if (question.getQuestionType() == QuestionType.RESPOND_IN_AT_LEAST_N_WORDS) {
            String[] userAnswer = usersAnswerRequest.getSomeText().split(" ");
            if (question.getWordCounter() > userAnswer.length) {
                throw new BadRequestException(String.format("Your answer should be minimum %d words",question.getWordCounter()));
            } else {
                return manualCheck(id, usersAnswerRequest, principal);
            }
        }
        else {
            return manualCheck(id, usersAnswerRequest, principal);
        }
    }

    public EvaluateResponse autoCheck(Long questionId, UsersAnswerRequest usersAnswerRequest, Principal principal) {

        if (usersAnswerRequest.getOptionsList().isEmpty()) {
            throw new BadRequestException("Answer is empty");
        }
        User user = userRepository.findByEmail(principal.getName());
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException(String.format("Object 'question' with %d id not found", questionId)));

        EvaluateResponse evaluateResponse = new EvaluateResponse();
        evaluateResponse.setUserName(user.getUserName());
        evaluateResponse.setTestName(question.getTest().getTitle());
        evaluateResponse.setQuestionName(question.getName());
        evaluateResponse.setDuration(question.getDuration());
        evaluateResponse.setQuestionType(question.getQuestionType());
        evaluateResponse.setOptions(optionMapper.mapToResponse(question.getOptionsList()));
        evaluateResponse.setScore(0);
        evaluateResponse.setUserAnswer(usersAnswerRequest);
        evaluateResponse.setMinimumNumberOfWords(question.getWordCounter());
        evaluateResponse.setCorrectAnswer(question.getCorrectAnswer());
        evaluateResponse.setPlayAudio(question.getPlay());
        evaluateResponse.setEnteredStatement(usersAnswerRequest.getSomeText());
        evaluateResponse.setNumberOfPlays(question.getNumberOfReplays());
        evaluateResponse.setStatement(question.getQuestionStatement());
        evaluateResponse.setQuestionStatement(question.getQuestionStatement());
        evaluateResponse.setNumberOfWords(question.getWordCounter());
        evaluateResponse.setPassage(question.getPassage());
        boolean exist;
        boolean exists;
        int correctOption = 0;
        int userRightAnswer = 0;

        for (Options options : question.getOptionsList()) {
            if (options.isCorrect()) {
                correctOption++;
            }
        }

        List<Long> uniqueList = usersAnswerRequest.getOptionsList().stream().distinct().collect(Collectors.toList());
        if (correctOption < usersAnswerRequest.getOptionsList().size()) {
            evaluateResponse.setScore(0);
        } else {
            for (int j = 0; j < question.getOptionsList().size(); j++) {
                for (Long aLong : uniqueList) {
                    if (Objects.equals(aLong,
                            question.getOptionsList().get(j).getId())
                            && question.getOptionsList().get(j).isCorrect()) {
                        userRightAnswer++;
                    }
                }
            }
        }

        int score = (userRightAnswer * 100 / correctOption) / 10;
        double finalScore = 0;
        evaluateResponse.setScore(Math.round(score));
        finalScore += score;

        QuestionResult questionResult = new QuestionResult();
        questionResult.setUserName(evaluateResponse.getUserName());
        questionResult.setTestName(evaluateResponse.getTestName());
        questionResult.setQuestionName(evaluateResponse.getQuestionName());
        questionResult.setDateOfSubmission(LocalDateTime.now());
        questionResult.setScore(evaluateResponse.getScore());
        questionResult.setStatus(EVALUATE);
        questionResult.setFinalScore(finalScore/100);
        if (questionResult.getFinalScore() != 0) {
            questionResult.setFinalStatus(EVALUATE);
        }
        questionResult.setQuestion(question);
        List<QuestionResult> questionResultList = new ArrayList<>();
        questionResultList.add(questionResult);

        exist = myResultRepository.existsByTestName(user.getId());
        exists = testResultRepository.existsTestResultByUserName(user.getUserName());
        TestResult testResult;
        if (!exists) {
            testResult = new TestResult();
        } else {
            testResult = testResultRepository.findByUserName(user.getUserName()).orElseThrow(() ->
                    new NotFoundException(String.format("Object 'testResult' with %d id not found", questionResult.getTestResult().getId())));
        }
        testResult.setUserName(questionResult.getUserName());
        testResult.setDateOfSubmission(questionResult.getDateOfSubmission());
        testResult.setTestName(questionResult.getTestName());
        testResult.setStatus(questionResult.getFinalStatus());
        testResult.setScore(questionResult.getFinalScore());
        testResult.setQuestionResults(questionResultList);
        testResult.setTest(question.getTest());
        questionResult.setTestResult(testResult);

        MyResult myResult;
        if (!exist) {
            myResult = new MyResult();
            myResult.setDateOfSubmission(LocalDateTime.now());
            myResult.setTestName(questionResult.getTestName());
            myResult.setStatus(questionResult.getFinalStatus());
            myResult.setScore(questionResult.getFinalScore());
            myResult.setUser(user);
        } else {
            myResult = myResultRepository.findByUserId(user.getId());
            myResult.setScore(questionResult.getFinalScore());
            myResult.setStatus(questionResult.getFinalStatus());
        }
        questionResult.setMyResult(myResult);
        myResultRepository.save(myResult);
        questionResultRepository.save(questionResult);

        return evaluateResponse;
    }

    @Override
    public EvaluateResponse manualCheck(Long questionId, UsersAnswerRequest usersAnswerRequest, Principal principal) {
        if (usersAnswerRequest.getSomeText().isEmpty()) {
            throw new BadRequestException("Answer is empty");
        }
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new NotFoundException(String.format("Object 'question' with %d id not found!", questionId)));
        User user = userRepository.findByEmail(principal.getName());

        EvaluateResponse evaluateResponse = new EvaluateResponse();
        evaluateResponse.setUserName(user.getUserName());
        evaluateResponse.setTestName(question.getTest().getTitle());
        evaluateResponse.setQuestionName(question.getName());
        evaluateResponse.setDuration(question.getDuration());
        evaluateResponse.setQuestionType(question.getQuestionType());
        evaluateResponse.setOptions(optionMapper.mapToResponse(question.getOptionsList()));
        evaluateResponse.setScore(0);
        evaluateResponse.setUserAnswer(usersAnswerRequest);
        evaluateResponse.setMinimumNumberOfWords(question.getWordCounter());
        evaluateResponse.setCorrectAnswer(question.getCorrectAnswer());
        evaluateResponse.setPlayAudio(question.getPlay());
        evaluateResponse.setEnteredStatement(usersAnswerRequest.getSomeText());
        evaluateResponse.setNumberOfPlays(question.getNumberOfReplays());
        evaluateResponse.setStatement(question.getQuestionStatement());
        evaluateResponse.setQuestionStatement(question.getQuestionStatement());
        evaluateResponse.setNumberOfWords(question.getWordCounter());
        evaluateResponse.setPassage(question.getPassage());

        boolean exist;
        boolean exists;

        QuestionResult questionResult = new QuestionResult();
        questionResult.setUserName(evaluateResponse.getUserName());
        questionResult.setTestName(evaluateResponse.getTestName());
        questionResult.setQuestionName(evaluateResponse.getQuestionName());
        questionResult.setDateOfSubmission(LocalDateTime.now());
        questionResult.setScore(evaluateResponse.getScore());
        questionResult.setStatus(NOT_EVALUATE);
        questionResult.setFinalScore(0);
        questionResult.setFinalStatus(NOT_EVALUATE);
        questionResult.setQuestion(question);
        List<QuestionResult> questionResultList = new ArrayList<>();
        questionResultList.add(questionResult);

        exists = testResultRepository.existsTestResultByUserName(user.getUserName());
        TestResult testResult;
        if (!exists) {
            testResult = new TestResult();
        } else {
            testResult = testResultRepository.findByUserName(user.getUserName()).orElseThrow(() ->
                    new NotFoundException(String.format("Object 'testResult' with %d id not found", questionResult.getTestResult().getId())));
        }
        testResult.setUserName(questionResult.getUserName());
        testResult.setDateOfSubmission(questionResult.getDateOfSubmission());
        testResult.setTestName(questionResult.getTestName());
        testResult.setStatus(questionResult.getFinalStatus());
        testResult.setScore(questionResult.getFinalScore());
        testResult.setQuestionResults(questionResultList);
        testResult.setTest(question.getTest());
        questionResult.setTestResult(testResult);

        exist = myResultRepository.existsByTestName(user.getId());
        MyResult myResult;
        if (!exist) {
            myResult = new MyResult();
            myResult.setDateOfSubmission(LocalDateTime.now());
            myResult.setTestName(questionResult.getTestName());
            myResult.setStatus(questionResult.getFinalStatus());
            myResult.setScore(questionResult.getFinalScore());
            myResult.setUser(user);
        } else {
            myResult = myResultRepository.findByUserId(user.getId());
            myResult.setScore(questionResult.getFinalScore());
            myResult.setStatus(questionResult.getFinalStatus());
        }
        questionResult.setMyResult(myResult);
        myResultRepository.save(myResult);
        questionResultRepository.save(questionResult);

        return evaluateResponse;
    }

    public EvaluateResponse checkerForHighlightType(Long questionId, UsersAnswerRequest usersAnswerRequest, Principal principal) {

        if (usersAnswerRequest.getSomeText().isEmpty()) {
            throw new BadRequestException("Answer is empty");
        }
        User user = userRepository.findByEmail(principal.getName());
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException(
                String.format("Object 'question' with %d id not found!", questionId)));

        List<String> userAnswer = List.of(usersAnswerRequest.getSomeText().split(" "));
        List<String> correctAnswer = List.of(question.getHighlightCorrectAnswer().split(" "));

        int userCorrectAnswer = 0;
        for (int i = 0; i < userAnswer.size(); i++) {
            for (int j = 0; j < correctAnswer.size(); j++) {
                if (userAnswer.get(i).equals(correctAnswer.get(j))
                        && userAnswer.indexOf(userAnswer.get(i))
                        == correctAnswer.indexOf(correctAnswer.get(j))) {
                    userCorrectAnswer++;
                }
            }
        }

        int score = ((userCorrectAnswer * 100) / (correctAnswer.size())) / 10;

        EvaluateResponse evaluateResponse = new EvaluateResponse();
        evaluateResponse.setUserName(user.getUserName());
        evaluateResponse.setTestName(question.getTest().getTitle());
        evaluateResponse.setQuestionName(question.getName());
        evaluateResponse.setDuration(question.getDuration());
        evaluateResponse.setQuestionType(question.getQuestionType());
        evaluateResponse.setOptions(optionMapper.mapToResponse(question.getOptionsList()));
        evaluateResponse.setScore(score);
        evaluateResponse.setUserAnswer(usersAnswerRequest);
        evaluateResponse.setMinimumNumberOfWords(question.getWordCounter());
        evaluateResponse.setCorrectAnswer(question.getCorrectAnswer());
        evaluateResponse.setPlayAudio(question.getPlay());
        evaluateResponse.setEnteredStatement(usersAnswerRequest.getSomeText());
        evaluateResponse.setNumberOfPlays(question.getNumberOfReplays());
        evaluateResponse.setStatement(question.getQuestionStatement());
        evaluateResponse.setQuestionStatement(question.getQuestionStatement());
        evaluateResponse.setNumberOfWords(question.getWordCounter());
        evaluateResponse.setPassage(question.getPassage());

        boolean exist;
        boolean exists;
        double finalScore = 0;
        finalScore += score;

        QuestionResult questionResult = new QuestionResult();
        questionResult.setUserName(evaluateResponse.getUserName());
        questionResult.setTestName(evaluateResponse.getTestName());
        questionResult.setQuestionName(evaluateResponse.getQuestionName());
        questionResult.setDateOfSubmission(LocalDateTime.now());
        questionResult.setScore(evaluateResponse.getScore());
        questionResult.setStatus(EVALUATE);
        questionResult.setFinalScore(finalScore/100);
        if (questionResult.getFinalScore() != 0) {
            questionResult.setFinalStatus(EVALUATE);
        }
        questionResult.setQuestion(question);
        List<QuestionResult> questionResultList = new ArrayList<>();
        questionResultList.add(questionResult);

        exists = testResultRepository.existsTestResultByUserName(user.getUserName());
        TestResult testResult;
        if (!exists) {
            testResult = new TestResult();
        } else {
            testResult = testResultRepository.findByUserName(user.getUserName()).orElseThrow(() ->
                    new NotFoundException(String.format("Object 'testResult' with %d id not found", questionResult.getTestResult().getId())));
        }
        testResult.setUserName(questionResult.getUserName());
        testResult.setDateOfSubmission(questionResult.getDateOfSubmission());
        testResult.setTestName(questionResult.getTestName());
        testResult.setStatus(questionResult.getFinalStatus());
        testResult.setScore(questionResult.getFinalScore());
        testResult.setQuestionResults(questionResultList);
        testResult.setTest(question.getTest());
        questionResult.setTestResult(testResult);

        exist = myResultRepository.existsByTestName(user.getId());
        MyResult myResult;
        if (!exist) {
            myResult = new MyResult();
            myResult.setDateOfSubmission(LocalDateTime.now());
            myResult.setTestName(questionResult.getTestName());
            myResult.setStatus(questionResult.getFinalStatus());
            myResult.setScore(questionResult.getFinalScore());
            myResult.setUser(user);
        } else {
            myResult = myResultRepository.findByUserId(user.getId());
            myResult.setScore(questionResult.getFinalScore());
            myResult.setStatus(questionResult.getFinalStatus());
        }
        questionResult.setMyResult(myResult);
        myResultRepository.save(myResult);
        questionResultRepository.save(questionResult);

        return evaluateResponse;
    }
    @Override
    public void deleteUserAnswer(Long id) {
        List<QuestionResult> questionResultList = questionResultRepository.findAllByQuestionId(id);
        questionResultRepository.deleteAll(questionResultList);

    }
}
