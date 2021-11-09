package sg.edu.smu.cs203.pandanews.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sg.edu.smu.cs203.pandanews.model.storage.UploadFileResponse;
import sg.edu.smu.cs203.pandanews.service.storage.FileStorageService;
import sg.edu.smu.cs203.pandanews.service.storage.FileUploadServiceImpl;


@Controller
public class FileUploadController {

    @Autowired
    FileUploadServiceImpl fileUploadService;

    @PostMapping("/image")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(fileUploadService.uploadFile(file));
    }

    @GetMapping(
            value = "image/{imageName:.+}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public @ResponseBody
    byte[] getImageWithMediaType(@PathVariable(name = "imageName") String fileName) throws IOException {
        return fileUploadService.getImageWithMediaType(fileName);
    }


}