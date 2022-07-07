package kg.peaksoft.bilingualb4.services;

import kg.peaksoft.bilingualb4.api.payload.QuestionRequest;
import kg.peaksoft.bilingualb4.api.payload.QuestionResponse;
import kg.peaksoft.bilingualb4.model.enums.QuestionType;

import java.util.List;

public interface QuestionService {

    List<QuestionResponse> findAll(QuestionType questionType);

    QuestionResponse save(Long id, QuestionRequest questionRequest);

    QuestionResponse findById(Long id);

    QuestionResponse deleteById(Long id);

    QuestionResponse updateById(Long id, QuestionRequest questionRequest);

}
