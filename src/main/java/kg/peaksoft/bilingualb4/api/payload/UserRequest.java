package kg.peaksoft.bilingualb4.api.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserRequest {

    @NotBlank
    private String userName;
    @Email
    private String email;
    private String password;
}
