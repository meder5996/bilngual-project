package kg.peaksoft.bilingualb4.services.impl;

import kg.peaksoft.bilingualb4.api.payload.TestResultResponse;
import kg.peaksoft.bilingualb4.exception.BadRequestException;
import kg.peaksoft.bilingualb4.exception.NotFoundException;
import kg.peaksoft.bilingualb4.model.entity.MyResult;
import kg.peaksoft.bilingualb4.model.enums.Status;
import kg.peaksoft.bilingualb4.model.mappers.TestResultMapper;
import kg.peaksoft.bilingualb4.repository.MyResultRepository;
import kg.peaksoft.bilingualb4.services.TestResultService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TestResultServiceImpl implements TestResultService {

    private final MyResultRepository myResultRepository;
    private final TestResultMapper testResultMapper;

    @Override
    public TestResultResponse findById(Long id) {
        return testResultMapper.mapToResponse(myResultRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(
                        "Object 'myResult' with %d id not found", id
                ))
        ));
    }

    @Override
    public List<TestResultResponse> findAll() {
        List<MyResult> sortedList = new ArrayList<>();
        List<MyResult> inActiveList = myResultRepository.findAllByActive(Status.NOT_EVALUATE);
        List<MyResult> activeList = myResultRepository.findAllByActive(Status.EVALUATE);
        sortedList.addAll(inActiveList);
        sortedList.addAll(activeList);
        return testResultMapper.mapToResponse(sortedList);
    }

    @Override
    public TestResultResponse deleteById(Long id) {
        MyResult myResult = myResultRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Object 'myResult with %d id not found!'", id)

        ));
        myResultRepository.deleteById(id);
        return testResultMapper.mapToResponse(myResult);
    }
}
