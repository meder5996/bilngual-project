package kg.peaksoft.bilingualb4.services.impl;

import kg.peaksoft.bilingualb4.api.payload.AuthInfoRequest;
import kg.peaksoft.bilingualb4.api.payload.AuthInfoResponse;
import kg.peaksoft.bilingualb4.config.jwt.JwtUtils;
import kg.peaksoft.bilingualb4.model.entity.Role;
import kg.peaksoft.bilingualb4.model.entity.User;
import kg.peaksoft.bilingualb4.repository.UserRepository;
import kg.peaksoft.bilingualb4.services.AuthInfoService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthInfoServiceImpl implements AuthInfoService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Override
    public AuthInfoResponse returnToken(AuthInfoRequest authInfoRequest) {
        Authentication authentication;
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authInfoRequest.getEmail(),
                authInfoRequest.getPassword()));

        String generatedToken = jwtUtils.generateToken(authentication);

        User userByEmail = userRepository.findByEmail(authInfoRequest.getEmail());
        List<Role> roles = userByEmail.getAuthInfo().getRoles();
        String role = String.valueOf(roles.get(0));


        return AuthInfoResponse.builder()
                .email(authInfoRequest.getEmail())
                .token(generatedToken)
                .userName(userByEmail.getUserName())
                .role(role)
                .build();
    }
}
