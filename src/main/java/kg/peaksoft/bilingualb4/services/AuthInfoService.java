package kg.peaksoft.bilingualb4.services;

import kg.peaksoft.bilingualb4.api.payload.AuthInfoRequest;
import kg.peaksoft.bilingualb4.api.payload.AuthInfoResponse;

public interface AuthInfoService {

    AuthInfoResponse returnToken(AuthInfoRequest authInfoRequest);

}
