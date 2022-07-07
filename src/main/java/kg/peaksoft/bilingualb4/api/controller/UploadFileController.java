package kg.peaksoft.bilingualb4.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.bilingualb4.services.AwsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/file")
@AllArgsConstructor
@CrossOrigin(origins = "*",maxAge = 3600)
@Tag(name = "Aws:", description = "Upload and delete operations")
public class UploadFileController {

    private AwsService awsService;

    @Operation(summary = "Uploading files",
            description = "Method uploads photo, audio, file.")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestBody MultipartFile file) throws IOException {
        String publicURL = awsService.uploadFile(file);
        Map<String, String> response = new HashMap<>();
        response.put("publicURL", publicURL);
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED );
    }

    @Operation(summary = "Deleting files",
            description = "Method delete files by name.")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete")
    public String downloadFile(@RequestParam String file){
         return awsService.delete(file);
    }
}