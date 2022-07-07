package kg.peaksoft.bilingualb4.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb4.api.payload.QuestionRequest;
import kg.peaksoft.bilingualb4.api.payload.QuestionResponse;
import kg.peaksoft.bilingualb4.model.enums.QuestionType;
import kg.peaksoft.bilingualb4.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/question")
@CrossOrigin(origins = "*",maxAge = 3600)
@Tag(name = "Question: ", description = "Quote CRUD operations")
public class QuestionApi {

    private final QuestionService questionService;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the Question",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = QuestionApi.class)))})})
    @Operation(
            summary = "Get a list of entity: workshop Question",
            description = "Returns a map of status codes to quantities:")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public List<QuestionResponse> findAll(@RequestParam QuestionType questionType) {
        return questionService.findAll(questionType);
    }

    @Operation(summary = "Creates the new entity: workshop Question",
            description = "The method expects a JSON with entity object in the request body." +
                    "The entity object may contain references to other entities")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/{testId}")
    public QuestionResponse save(@RequestBody QuestionRequest questionRequest, @PathVariable("testId") Long id) {
        return questionService.save(id, questionRequest);
    }

    @Operation(
            summary = "Gets a single entity by identifier: workshop$Question",
            description = "For valid response try integer IDs with value >= 1 and:")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("{id}")
    public QuestionResponse findByIdAndName(@PathVariable Long id) {
        return questionService.findById(id);
    }

    @Operation(
            summary = "Deletes the entity: workshop$Question",
            description = "Deletes an endpoint and all its child entities.")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public QuestionResponse deleteById(@PathVariable Long id) {
        return questionService.deleteById(id);
    }

    @Operation(summary = "Updates the entities : workshop$Question",
            description = "updates the Question object by the id")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("{id}")
    public QuestionResponse updateById(@PathVariable Long id,
                                       @RequestBody QuestionRequest questionRequest) {
        return questionService.updateById(id, questionRequest);
    }
}
