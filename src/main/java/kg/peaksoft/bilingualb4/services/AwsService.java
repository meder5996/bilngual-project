package kg.peaksoft.bilingualb4.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AwsService {
    String uploadFile(MultipartFile file) throws IOException;

    String delete(String file);
}


