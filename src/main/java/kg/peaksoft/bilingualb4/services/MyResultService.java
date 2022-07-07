package kg.peaksoft.bilingualb4.services;

import kg.peaksoft.bilingualb4.api.payload.MyResultResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.List;

public interface MyResultService {


    MyResultResponse findById(Long id);

    MyResultResponse deleteById(Long id);

    List<MyResultResponse> findAll(Principal principal);

}
