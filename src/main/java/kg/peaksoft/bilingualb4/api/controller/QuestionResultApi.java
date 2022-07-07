package kg.peaksoft.bilingualb4.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb4.api.payload.EvaluateResponse;
import kg.peaksoft.bilingualb4.api.payload.QuestionResultRequest;
import kg.peaksoft.bilingualb4.api.payload.QuestionResultResponse;
import kg.peaksoft.bilingualb4.services.QuestionResultService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/question_result")
@CrossOrigin(origins = "*",maxAge = 3600)
@Tag(name = "QuestionResult: ", description = "Quote CRUD operations")
public class QuestionResultApi {

    private final QuestionResultService questionResultService;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the QuestionResult",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = QuestionResultApi.class)))})})
    @Operation(
            summary = "Get a list of entity: workshop QuestionResult",
            description = "Returns a map of status codes to quantities:")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public List<QuestionResultResponse> findAll(){
        return questionResultService.findAll();
    }

    @Operation(summary = "Gets a single entity by identifier: workshop$QuestionResult",
            description = "For valid response try integer IDs with value >= 1 and:")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("{id}")
    public QuestionResultResponse findById(@PathVariable Long id){
        return questionResultService.findById(id);
    }

    @Operation(summary = "Updates the entities : workshop$QuestionResult",
            description = "updates the QuestionResult object by the id")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("{id}")
    public QuestionResultResponse updateById(@PathVariable Long id, @RequestBody QuestionResultRequest questionResultRequest){
        return questionResultService.updateById(id, questionResultRequest);
    }

    @Operation(summary = "Send the result to user : workshop$QuestionResult",
            description = "First this method finds the user's result and send to owner")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/sendMessage/{myResultId}")
    public void sendResultsToUsers(@PathVariable Long myResultId){
        questionResultService.sendResultsToUser(myResultId);
    }
}
