package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sg.edu.smu.cs203.pandanews.service.storage.FileUploadServiceImpl;

import java.io.IOException;


@Controller
public class FileUploadController {

    @Autowired
    FileUploadServiceImpl fileUploadService;

    /**
     * take in multi-part image
     * @param file
     * @return saved directory and download url
     */
    @PostMapping("/image")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(fileUploadService.uploadFile(file));
    }

    /**
     * get the image in byte format
     * @param fileName
     * @return
     * @throws IOException
     */
    @GetMapping(
            value = "image/{imageName:.+}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public @ResponseBody
    byte[] getImageWithMediaType(@PathVariable(name = "imageName") String fileName) throws IOException {
        return fileUploadService.getImageWithMediaType(fileName);
    }


}