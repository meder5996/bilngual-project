package kg.peaksoft.bilingualb4.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb4.api.payload.EvaluateResponse;
import kg.peaksoft.bilingualb4.api.payload.UsersAnswerRequest;
import kg.peaksoft.bilingualb4.services.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/answer")
@CrossOrigin(origins = "*",maxAge = 3600)
@Tag(name = "UserAnswer:", description = "Makes it possible to keep the user result")
public class UsersAnswerApi {

    private final AnswerService answerService;

    @Operation(summary = "Create the new entity: workshop ClientsAnswer, Role 'CLIENT'",
            description =
                    "The method expects a JSON with entity object in the request body." +
                            "The entity object may contain references to other entities.")
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @PostMapping("{questionId}")
    public EvaluateResponse save(@PathVariable Long questionId,
                                 @RequestBody UsersAnswerRequest usersAnswerRequest,
                                 Principal principal) {
        return answerService.save(questionId, usersAnswerRequest, principal);

    }
    @Operation(summary = "Deletes the entity: workshop$Results",
            description = "Deletes an endpoint and all its child entities.")
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @DeleteMapping("{questionId}")
    public void deleteUserAnswer(@PathVariable Long questionId){
        answerService.deleteUserAnswer(questionId);
    }

}
