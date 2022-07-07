package kg.peaksoft.bilingualb4.api.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthInfoResponse {

    private String email;
    private String token;
    private String userName;
    private String role;

}
