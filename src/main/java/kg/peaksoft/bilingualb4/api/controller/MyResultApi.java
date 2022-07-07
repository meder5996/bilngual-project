package kg.peaksoft.bilingualb4.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb4.api.payload.MyResultResponse;
import kg.peaksoft.bilingualb4.services.MyResultService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/my_result")
@CrossOrigin(origins = "*",maxAge = 3600)
@Tag(name = "MyResult: ", description = "Quote CRUD operations")
public class MyResultApi {

    private final MyResultService myResultService;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the MyResult",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MyResultApi.class)))})})
    @Operation(
            summary = "Get a list of entity by {userId}: workshop MyResult",
            description = "Returns a map of status codes to quantities:")
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @GetMapping("")
    public List<MyResultResponse> findAllById(Principal principal) {
        return myResultService.findAll(principal);
    }

    @Operation(summary = "Gets a single entity by identifier: workshop$MyResult",
            description = "For valid response try integer IDs with value >= 1 and:")
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @GetMapping("{id}")
    public MyResultResponse findById(@PathVariable Long id) {
        return myResultService.findById(id);
    }

    @Operation(summary = "Deletes the entity: workshop$MyResult",
            description = "Deletes an endpoint and all its child entities.")
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @DeleteMapping("{id}")
    public MyResultResponse delete(@PathVariable Long id) {
        return myResultService.deleteById(id);
    }

}
