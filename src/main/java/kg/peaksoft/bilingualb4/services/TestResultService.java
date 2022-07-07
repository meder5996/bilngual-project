package kg.peaksoft.bilingualb4.services;

import kg.peaksoft.bilingualb4.api.payload.TestResultResponse;
import kg.peaksoft.bilingualb4.model.entity.MyResult;

import java.util.List;

public interface TestResultService {

    TestResultResponse findById(Long id);

    List<TestResultResponse> findAll();

    TestResultResponse deleteById(Long id);

}
