package kg.peaksoft.bilingualb4.services;

import kg.peaksoft.bilingualb4.api.payload.EvaluateResponse;
import kg.peaksoft.bilingualb4.api.payload.UsersAnswerRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;

public interface AnswerService {
    EvaluateResponse save(Long id, UsersAnswerRequest usersAnswerRequest, Principal principal);

    EvaluateResponse manualCheck(Long questionId, UsersAnswerRequest usersAnswerRequest, Principal principal);

    void deleteUserAnswer(Long id);
}
