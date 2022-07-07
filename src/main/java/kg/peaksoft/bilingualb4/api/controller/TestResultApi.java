package kg.peaksoft.bilingualb4.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb4.api.payload.TestResultResponse;
import kg.peaksoft.bilingualb4.services.TestResultService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/submitted_result")
@CrossOrigin(origins = "*",maxAge = 3600)
@Tag(name = "TestResult: ", description = "Quote CRUD operations")
public class TestResultApi {

    private final TestResultService testResultService;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the TestResult",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TestResultApi.class)))})})
    @Operation(
            summary = "Get a list of entity: workshop TestResult",
            description = "Returns a map of status codes to quantities:")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public List<TestResultResponse> findALl(){
        return testResultService.findAll();
    }

    @Operation(summary = "Gets a single entity by identifier: workshop$TestResult",
            description = "For valid response try integer IDs with value >= 1 and:")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("{id}")
    public TestResultResponse findById(@PathVariable Long id){
        return testResultService.findById(id);
    }

    @Operation(summary = "Deletes the entity: workshop$TestResult",
            description = "Deletes an endpoint and all its child entities.")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public TestResultResponse deleteById(@PathVariable Long id){
        return testResultService.deleteById(id);
    }
}
