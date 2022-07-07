package kg.peaksoft.bilingualb4.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb4.api.payload.AuthInfoRequest;
import kg.peaksoft.bilingualb4.api.payload.AuthInfoResponse;
import kg.peaksoft.bilingualb4.api.payload.UserRequest;
import kg.peaksoft.bilingualb4.api.payload.UserResponse;
import kg.peaksoft.bilingualb4.services.AuthInfoService;
import kg.peaksoft.bilingualb4.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("api/public")
@Tag(name = "Authentication:", description = "Client authentication")
public class  AuthInfoApi {

    private final AuthInfoService authInfoService;
    private final UserService userService;

    @Operation(summary = "Retrieve Authentication Token",
            description = "This entrypoint returns a JWT auth_token for authenticating further requests to the API.")
    @PostMapping("/login")
    public AuthInfoResponse save(@RequestBody AuthInfoRequest authInfoRequest) {
        return authInfoService.returnToken(authInfoRequest);
    }

    @Operation(summary = "Creates new entity: workshop$Client",
            description =
                    "The method expects a JSON with entity object in the request body." +
                            "The entity object may contain references to other entities.")

    @PostMapping("/registration")
    public UserResponse registration(@RequestBody UserRequest userRequest) {
        return userService.registration(userRequest);
    }

}
