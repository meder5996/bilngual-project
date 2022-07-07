package kg.peaksoft.bilingualb4.api.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthInfoRequest {

    private String email;

    private String password;
}
