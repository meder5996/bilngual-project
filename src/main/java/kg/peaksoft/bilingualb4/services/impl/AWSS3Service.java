package kg.peaksoft.bilingualb4.services.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kg.peaksoft.bilingualb4.services.AwsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AWSS3Service implements AwsService {


    private AmazonS3Client awsS3Client;

    @Override
    public String uploadFile(MultipartFile file) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String key = UUID.randomUUID().toString() + "." + extension;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            awsS3Client.putObject("bilingual-4", key, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occured while uploading the file");
        }
        awsS3Client.setObjectAcl("bilingual-4", key, CannedAccessControlList.PublicRead);

        return awsS3Client.getResourceUrl("bilingual-4", key);
    }

    @Override
    public String delete(String file) {
        if (file != null && !file.equals("")) {
            String[] fileName = file.split("/");
            awsS3Client.deleteObject("bilingual-4", fileName[fileName.length - 1]);
            return "Deleted file: " + file;
        }
        return "exists";
    }
}
