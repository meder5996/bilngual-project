package kg.peaksoft.bilingualb4.services.impl;

import kg.peaksoft.bilingualb4.api.payload.MyResultResponse;
import kg.peaksoft.bilingualb4.exception.NotFoundException;
import kg.peaksoft.bilingualb4.model.entity.MyResult;
import kg.peaksoft.bilingualb4.model.entity.User;
import kg.peaksoft.bilingualb4.model.enums.Status;
import kg.peaksoft.bilingualb4.model.mappers.MyResultMapper;
import kg.peaksoft.bilingualb4.repository.MyResultRepository;
import kg.peaksoft.bilingualb4.repository.UserRepository;
import kg.peaksoft.bilingualb4.services.MyResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyResultServiceImpl implements MyResultService {

    private final MyResultRepository myResultRepository;
    private final MyResultMapper myResultMapper;
    private final UserRepository userRepository;

    @Override
    public MyResultResponse findById(Long id) {
        return myResultMapper.mapToResponse(myResultRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Object 'myResult with %d id not found!", id)
        )));
    }

    @Override
    public MyResultResponse deleteById(Long id) {
        MyResult myResult = myResultRepository.findById(id).orElseThrow(() ->
                new NotFoundException(
                        String.format("Result with %d id not found!", id)));
        myResultRepository.deleteById(id);
        return myResultMapper.mapToResponse(myResult);
    }

    @Override
    public List<MyResultResponse> findAll(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        List<MyResult> sortedList = new ArrayList<>();
        List<MyResult> inActiveList = myResultRepository.findAllByUserId(user.getId(), Status.NOT_EVALUATE);
        List<MyResult> activeList = myResultRepository.findAllByUserId(user.getId(), Status.EVALUATE);
        sortedList.addAll(inActiveList);
        sortedList.addAll(activeList);
        return myResultMapper.mapToResponse(sortedList);
    }
}
