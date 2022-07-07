package kg.peaksoft.bilingualb4.services;

import kg.peaksoft.bilingualb4.api.payload.TestRequest;
import kg.peaksoft.bilingualb4.api.payload.TestResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.List;

public interface TestService {

    List<?> findAll(Principal principal);

    TestResponse save(TestRequest testRequest);

    Object findById(UserDetails userDetails, Long id);

    TestResponse deleteById(Long id);

    TestResponse updateById(Long id, TestRequest testRequest);
}
