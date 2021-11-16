package sg.edu.smu.cs203.pandanews.service.storage;

import org.springframework.web.multipart.MultipartFile;
import sg.edu.smu.cs203.pandanews.model.storage.UploadFileResponse;

import java.io.IOException;

public interface FileUploadService {
    UploadFileResponse uploadFile(MultipartFile multipartFile);

    byte[] getImageWithMediaType(String fileName) throws IOException;
}
