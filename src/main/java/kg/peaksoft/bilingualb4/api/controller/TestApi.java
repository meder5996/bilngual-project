package kg.peaksoft.bilingualb4.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb4.api.payload.TestRequest;
import kg.peaksoft.bilingualb4.api.payload.TestResponse;
import kg.peaksoft.bilingualb4.model.entity.Test;
import kg.peaksoft.bilingualb4.services.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Test:", description = "Quote CRUD operations")
public class TestApi {

    private final TestService testService;

    @Operation(summary = "Gets a list of test: workshop$Test",
            description = "Returns a map of status codes to quantities:")
    @PreAuthorize("hasAnyAuthority('ADMIN','CLIENT')")
    @GetMapping
    public List<?> findAll(Principal principal) {
        return testService.findAll(principal);
    }

    @Operation(summary = "Creates new entity: workshop$Test",
            description =
                    "The method expects a JSON with entity object in the request body." +
                            "The entity object may contain references to other entities")

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public TestResponse save(@RequestBody TestRequest testRequest) {
        return testService.save(testRequest);
    }

    @Operation(summary = "Deletes the entity: workshop$Test",
            description = "Deletes an endpoint and all its child entities.")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public TestResponse deleteBy(@PathVariable("id") Long id) {
        return testService.deleteById(id);
    }

    @Operation(summary = "Gets a single entity by identifier: workshop$Test",
            description = "For valid response try integer IDs with value >= 1 and:")
    @PreAuthorize("hasAnyAuthority('ADMIN','CLIENT')")
    @GetMapping("{id}")
    public Object findById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        return testService.findById(userDetails, id);
    }

    //        @Operation(summary = "Updates the entity: workshop$Test", description = """
//            Updates the details of an endpoint.
//
//             You can provide following fields with this request:
//
//             title (String)
//
//             short_description (String)
//
//            """)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("{id}")
    public TestResponse update(@PathVariable Long id, @RequestBody TestRequest testRequest) {
        return testService.updateById(id, testRequest);
    }
}