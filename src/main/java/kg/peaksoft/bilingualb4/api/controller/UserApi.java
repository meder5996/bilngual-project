package kg.peaksoft.bilingualb4.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb4.api.payload.UserRequest;
import kg.peaksoft.bilingualb4.api.payload.UserResponse;
import kg.peaksoft.bilingualb4.model.entity.User;
import kg.peaksoft.bilingualb4.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/client")
@CrossOrigin(origins = "*",maxAge = 3600)
@Tag(name = "Client:", description = "Quote CRUD operations")
public class UserApi {

    private final UserService userService;

    @Operation(summary = "Gets a list of users: workshop$Client",
            description = "Returns a map of status codes to quantities:")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the users", content =
                    {@Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = UserApi.class)))})})
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @Operation(summary = "Deletes the entity: workshop$Client",
            description = "Deletes an endpoint and all its child entities.")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public void deleteBy(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }

    @Operation(summary = "Gets a single entity by identifier: workshop$Client",
            description = "For valid response try integer IDs with value >= 1 and:")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("{id}")
    public Optional<User> findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @Operation(summary = "Updates the entity: workshop$Client",
            description =
                    "Updates the details of an endpoint.")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService.update(id, userRequest);
    }
}
